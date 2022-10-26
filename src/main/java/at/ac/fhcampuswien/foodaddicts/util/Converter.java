package at.ac.fhcampuswien.foodaddicts.util;

import at.ac.fhcampuswien.foodaddicts.api.ApiIngredient;
import at.ac.fhcampuswien.foodaddicts.api.ApiRecipe;
import at.ac.fhcampuswien.foodaddicts.dto.RecipeDto;
import at.ac.fhcampuswien.foodaddicts.model.Ingredient;
import at.ac.fhcampuswien.foodaddicts.model.Recipe;
import at.ac.fhcampuswien.foodaddicts.model.Unit;

import java.util.*;

public class Converter {

    private Converter(){}

    public static Recipe toRecipe(RecipeDto recipeDto){
        Recipe recipe = new Recipe();
        recipe.setRecipeId(recipeDto.getRecipeId());
        recipe.setName(recipeDto.getName());
        recipe.setReadyInMinutes(recipeDto.getReadyInMinutes());
        recipe.setCalories(recipeDto.getCalories());
        recipe.setPortion(recipeDto.getPortion());
        recipe.setPreparation(recipeDto.getPreparation());
        recipe.setIngredients(recipeDto.getIngredients());
        recipe.setImage(recipeDto.getImage());
        return recipe;
    }

    public static RecipeDto toRecipeDto(Recipe recipe){
        RecipeDto recipeDto = new RecipeDto();
        recipeDto.setRecipeId(recipe.getRecipeId());
        recipeDto.setName(recipe.getName());
        recipeDto.setReadyInMinutes(recipe.getReadyInMinutes());
        recipeDto.setCalories(recipe.getCalories());
        recipeDto.setPortion(recipe.getPortion());
        recipeDto.setPreparation(recipe.getPreparation());
        recipeDto.setIngredients(recipe.getIngredients());
        recipeDto.setImage(recipe.getImage());
        return recipeDto;
    }

    public static List<RecipeDto> toRecipesDto(List<Recipe> recipes){
        List<RecipeDto> recipesDto = new ArrayList<>();
        for (Recipe recipe : recipes){
            recipesDto.add(toRecipeDto(recipe));
        }
        return recipesDto;
    }

    public static List<Recipe> toRecipes(List<RecipeDto> recipeDtos){
        List<Recipe> recipes = new ArrayList<>();
        for (RecipeDto recipeDto : recipeDtos){
            recipes.add(toRecipe(recipeDto));
        }
        return recipes;
    }

    public static RecipeDto apiRecipeToRecipeDto(ApiRecipe apiRecipe){
        RecipeDto recipeDto = new RecipeDto();
        Set<Ingredient> ingredients = new HashSet<>();
        recipeDto.setName(apiRecipe.getTitle());
        recipeDto.setCalories(apiRecipe.getCalories());
        recipeDto.setPortion(apiRecipe.getServings());
        recipeDto.setPreparation(apiRecipe.getInstructions());
        recipeDto.setReadyInMinutes(apiRecipe.getReadyInMinutes());
        for (ApiIngredient apiIngredient : apiRecipe.getExtendedIngredients()){
            Ingredient ingredient = new Ingredient();
            ingredient.setName(apiIngredient.getName());
            ingredient.setAmount(apiIngredient.getAmount());
            ingredient.setUnit(apiIngredient.getUnit());
            ingredients.add(ingredient);
        }
        recipeDto.setIngredients(ingredients);
        recipeDto.setImage(apiRecipe.getImage());
        return recipeDto;
    }
}
