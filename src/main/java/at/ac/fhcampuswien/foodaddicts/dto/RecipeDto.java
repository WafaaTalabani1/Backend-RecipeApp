package at.ac.fhcampuswien.foodaddicts.dto;

import at.ac.fhcampuswien.foodaddicts.model.Ingredient;

import java.util.HashSet;
import java.util.Set;

public class RecipeDto {
    private long recipeId;
    private String name;
    private int readyInMinutes;
    private double calories;
    private int portion;
    private Set<Ingredient> ingredients = new HashSet<>();
    private String preparation;
    private String image;

    public long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(long recipeId) {
        this.recipeId = recipeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public void setReadyInMinutes(int readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public int getPortion() {
        return portion;
    }

    public void setPortion(int portion) {
        this.portion = portion;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "RecipeDto{" +
                "recipeId=" + recipeId +
                ", name='" + name + '\'' +
                ", readyInMinutes=" + readyInMinutes +
                ", calories=" + calories +
                ", portion=" + portion +
                ", ingredients=" + ingredients +
                ", preparation='" + preparation + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
