package at.ac.fhcampuswien.foodaddicts.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ApiRecipe {
    private int id;
    private String title;
    private int readyInMinutes;
    private String instructions;
    private int servings;
    private double calories;
    private List<ApiIngredient> extendedIngredients = new ArrayList<>();
    private String image;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public void setReadyInMinutes(int readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public List<ApiIngredient> getExtendedIngredients() {
        return extendedIngredients;
    }

    public void setExtendedIngredients(List<ApiIngredient> extendedIngredients) {
        this.extendedIngredients = extendedIngredients;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiRecipe apiRecipe = (ApiRecipe) o;
        return id == apiRecipe.id && readyInMinutes == apiRecipe.readyInMinutes && servings == apiRecipe.servings && Double.compare(apiRecipe.calories, calories) == 0 && Objects.equals(title, apiRecipe.title) && Objects.equals(instructions, apiRecipe.instructions) && Objects.equals(extendedIngredients, apiRecipe.extendedIngredients) && Objects.equals(image, apiRecipe.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, readyInMinutes, instructions, servings, calories, extendedIngredients, image);
    }

    @Override
    public String toString() {
        return "ApiRecipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", readyInMinutes=" + readyInMinutes +
                ", instructions='" + instructions + '\'' +
                ", servings=" + servings +
                ", calories=" + calories +
                ", ingredients=" + extendedIngredients +
                '}';
    }
}
