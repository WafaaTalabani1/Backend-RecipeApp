package at.ac.fhcampuswien.foodaddicts.service;

import at.ac.fhcampuswien.foodaddicts.api.ApiRecipe;
import at.ac.fhcampuswien.foodaddicts.api.FoodApi;
import at.ac.fhcampuswien.foodaddicts.api.FoodApiException;
import at.ac.fhcampuswien.foodaddicts.dto.RecipeDto;
import at.ac.fhcampuswien.foodaddicts.exception.FoodAddictException;
import at.ac.fhcampuswien.foodaddicts.model.Ingredient;
import at.ac.fhcampuswien.foodaddicts.model.Recipe;
import at.ac.fhcampuswien.foodaddicts.model.Unit;
import at.ac.fhcampuswien.foodaddicts.repository.IngredientRepository;
import at.ac.fhcampuswien.foodaddicts.repository.RecipeRepository;
import at.ac.fhcampuswien.foodaddicts.util.Converter;
import at.ac.fhcampuswien.foodaddicts.util.S3Util;
import com.amazonaws.util.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@SuppressWarnings("ALL")
@Service
@AllArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final S3Util s3Util;

    @Transactional
    public RecipeDto addRecipe(RecipeDto recipeDto, MultipartFile imageFile) throws FoodAddictException {
        boolean recipeExists = recipeRepository.findByName(recipeDto.getName().trim().toLowerCase()).isPresent();
        if (recipeExists) {
            throw new FoodAddictException("Recipe " + recipeDto.getName() + " already exists!");
        }
        for (Ingredient ingredient : recipeDto.getIngredients()) {
            if (!ingredientRepository.ingredientExists(ingredient)) {
                ingredientRepository.save(ingredient);
            }
        }
        recipeDto.setName(recipeDto.getName().trim().toLowerCase());
        if (StringUtils.isNullOrEmpty(recipeDto.getImage()) && imageFile != null){
            String imageUrl = uploadImage(imageFile);
            recipeDto.setImage(imageUrl);
        }
        System.out.println(recipeDto);
        return Converter.toRecipeDto(recipeRepository.save(Converter.toRecipe(recipeDto)));
    }

    public RecipeDto getRecipeById(long recipeId, Optional<Integer> portion) throws FoodAddictException {
        return getRecipe(recipeId, portion, Long.class);
    }

    public RecipeDto getRecipeByName(String name, Optional<Integer> portion) throws FoodAddictException {
        return getRecipe(name, portion, String.class);
    }

    public List<RecipeDto> getRecipes() {
        return Converter.toRecipesDto(recipeRepository.findAll());
    }

    public void deleteRecipe(long id) {
        Recipe recipe = recipeRepository.findById(id).orElseThrow();
        recipeRepository.delete(recipe);
    }

    @Transactional
    public List<RecipeDto> getRecipesWithCal(double minCal, double maxCal) throws FoodAddictException {
        if (minCal > maxCal)
            throw new FoodAddictException("Max calories amount can not be smaller than the minimum amount");
        List<RecipeDto> recipes = null;
        List<ApiRecipe> apiRecipes = null;
        try {
            recipes = Converter.toRecipesDto(recipeRepository.findByMinMaxCal(minCal, maxCal));
            if (recipes != null && !recipes.isEmpty())
                return recipes;
            apiRecipes = FoodApi.getRecipesByCalories(minCal, maxCal);
            for (ApiRecipe apiRecipe : apiRecipes) {
                if (recipeRepository.findByName(apiRecipe.getTitle()).isEmpty()) {
                    RecipeDto recipeDto = Converter.apiRecipeToRecipeDto(apiRecipe);
                    recipes.add(addRecipe(recipeDto, null));
                }
            }
            return recipes;
        } catch (FoodApiException e) {
            if (apiRecipes.isEmpty() && recipes == null || recipes.isEmpty()) {
                throw new FoodAddictException("Could not retrieve recipes from the external food API and no recipes are locally available", e);
            }
            throw new FoodAddictException("Something went wrong ... ", e);
        } catch (FoodAddictException e) {
            throw new FoodAddictException("Could not persist the new found recipes", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FoodAddictException("Something went wrong!", e);
        }
    }

    public RecipeDto updateRecipe(RecipeDto recipe) {
        return Converter.toRecipeDto(recipeRepository.save(Converter.toRecipe(recipe)));
    }

    private RecipeDto getRecipe(Object queryParam, Optional<Integer> portion, Class<?> paramType) throws FoodAddictException {
        RecipeDto recipe = null;
        try {
            if (paramType == long.class || paramType == Long.class) {
                recipe = Converter.toRecipeDto(recipeRepository.findById((Long) queryParam).orElseThrow());
            } else {
                recipe = Converter.toRecipeDto(recipeRepository.findByName(((String) queryParam).toLowerCase()).orElseThrow());
            }
            recipe.setName(capitalizeName(recipe.getName()));
            if (portion.isPresent() && portion.get() != recipe.getPortion()) {
                double newPortion = (portion.get() / (recipe.getPortion() * 1.0));
                recipe.setPortion(portion.get());
                return updatePortion(recipe, newPortion);
            }
        } catch (NoSuchElementException exception) {
            throw new FoodAddictException("No such recipe could be found!", exception);
        }

        return recipe;
    }

    private RecipeDto updatePortion(RecipeDto recipe, double newPortion) {
        double newAmount;
        for (Ingredient ingredient : recipe.getIngredients()) {
            newAmount = ingredient.getAmount() * newPortion;
            ingredient.setAmount(newAmount);
        }
        return recipe;
    }

    private String capitalizeName(String name) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : name.split(" ")) {
            stringBuilder.append(string.substring(0, 1).toUpperCase()).append(string.substring(1));
        }
        return stringBuilder.toString();
    }

    private String uploadImage(MultipartFile imageFile) throws FoodAddictException {
        try {
            if (imageFile == null)
                throw new IllegalArgumentException("Image file can not be null!");
            String fileName = imageFile.getOriginalFilename();
            System.out.println("filename: " + fileName);
            BufferedImage bufferedImage = ImageIO.read(imageFile.getInputStream());
            if (bufferedImage == null)
                throw new FoodAddictException("Uploaded file is not an image!");
            return s3Util.uploadFile(imageFile.getOriginalFilename(), imageFile.getInputStream());
        } catch (IOException e) {
            throw new FoodAddictException("Could not read image." + e.getMessage(), e);
        }
    }

}
