package at.ac.fhcampuswien.foodaddicts.util;

import at.ac.fhcampuswien.foodaddicts.model.Ingredient;
import at.ac.fhcampuswien.foodaddicts.model.Recipe;
import at.ac.fhcampuswien.foodaddicts.model.Unit;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {
        Ingredient ingredient = new Ingredient();
        Ingredient ingredient2 = new Ingredient();
        Set<Ingredient> ingredients = new HashSet<>();
        List<String> preparation = new ArrayList<>();
        ingredient.setId(443L);
        ingredient.setName("Sugar");
        ingredient.setAmount(2);
        ingredient.setUnit(Unit.SPOON.toString());

        ingredient2.setId(444L);
        ingredient2.setName("Salt");
        ingredient2.setAmount(1);
        ingredient2.setUnit(Unit.SMALL_SPOON.toString());

        ingredients.add(ingredient);
        ingredients.add(ingredient2);

        preparation.add("Mix ingredients");
        preparation.add("Add water");
        preparation.add("Bake for 2 hours");

        Recipe recipe = new Recipe();
        recipe.setRecipeId(223L);
        recipe.setName("Cake");
        recipe.setCalories(500);
        recipe.setIngredients(ingredients);
        recipe.setPreparation(String.join(",", preparation));
        recipe.setReadyInMinutes(20);

        // gson oder jackson
    }

    /*
* {
    "id": 3,
    "name": "Cake",
    "preparationTimeMinutes": 20,
    "workingTimeMinutes": 50,
    "calories": 500,
    "portion":2,
    "ingredients": [
        {
            "id": 3,
            "name": "Sugar",
            "amount": 2.0,
            "unit": "SPOON"
        },
        {
            "id": 4,
            "name": "Salt",
            "amount": 1.0,
            "unit": "SMALL_SPOON"
        }
    ],
    "preparation": [
        "Mix ingredients",
        "Add water",
        "Bake for 2 hours"
    ]
}
* */
}
