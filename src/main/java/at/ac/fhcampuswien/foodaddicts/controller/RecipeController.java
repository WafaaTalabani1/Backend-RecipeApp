package at.ac.fhcampuswien.foodaddicts.controller;

import at.ac.fhcampuswien.foodaddicts.dto.RecipeDto;
import at.ac.fhcampuswien.foodaddicts.exception.FoodAddictException;
import at.ac.fhcampuswien.foodaddicts.service.RecipeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@SuppressWarnings("ALL")
@CrossOrigin("*")
@RestController
@RequestMapping(path = "api/v1/recipe")
@AllArgsConstructor
public class RecipeController {
    private RecipeService recipeService;

    @PutMapping("/add")
    public ResponseEntity<?> add(@RequestBody RecipeDto recipe) {
        try {
            System.out.println(recipe);
            return ResponseEntity.ok(recipeService.addRecipe(recipe, null));
        } catch (FoodAddictException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/id")
    public ResponseEntity<?> get(@RequestParam("id") long id,
                                 @RequestParam(name = "portion", required = false) Optional<Integer> portion) {
        try {
            return ResponseEntity.ok(recipeService.getRecipeById(id, portion));
        } catch (FoodAddictException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/name")
    public ResponseEntity<?> getByName(@RequestParam("name") String name,
                                       @RequestParam(name = "portion", required = false) Optional<Integer> portion) {
        try {
            return ResponseEntity.ok(recipeService.getRecipeByName(name, portion));
        } catch (FoodAddictException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
   /* @GetMapping("/cuisine")
    public ResponseEntity<?> getCuisine(@RequestParam("cuisine") String name,
                                       @RequestParam(name = "portion", required = false) Optional<Integer> portion){
        try {
            return ResponseEntity.ok( recipeService.getRecipeByName(cuisine(), portion));
        } catch (FoodAddictException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
*/

    @GetMapping()
    public ResponseEntity<?> getByCalories(@RequestParam(value = "minCal", required = false) Optional<Double> minCal,
                                           @RequestParam(name = "maxCal", required = false) Optional<Double> maxCal) {
        double minCalValue = minCal.isPresent() ? minCal.get() : 0;
        double maxCalValue = maxCal.isPresent() ? maxCal.get() : 0;
        try {
            if (minCalValue == 0 && maxCalValue == 0)
                return ResponseEntity.ok(recipeService.getRecipes());
            return ResponseEntity.ok(recipeService.getRecipesWithCal(minCalValue, maxCalValue));
        } catch (FoodAddictException e) {
            return ResponseEntity.badRequest().body((e.getMessage() + "\n" + e.getCause().getMessage()));
        }

    }

    @DeleteMapping()
    public ResponseEntity<?> deleteRecipe(@RequestParam("id") long id) {
        try {
            recipeService.deleteRecipe(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping()
    public RecipeDto update(@RequestBody RecipeDto recipe) {
        return recipeService.updateRecipe(recipe);
    }
}

