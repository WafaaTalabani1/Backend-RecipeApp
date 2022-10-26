package at.ac.fhcampuswien.foodaddicts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;
import java.util.*;

@Getter
@Entity
public class Recipe {
    @SequenceGenerator(
            name = "recipe_sequence",
            sequenceName = "recipe_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "recipe_sequence"
    )
    private Long recipe_id;
    private String name;
    private int readyInMinutes;
    private double calories;

    private int portion;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "recipe_ingredients",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private Set<Ingredient> ingredients = new HashSet<>();
    @Lob
    private String preparation;
    private String image;

    public Recipe(){
    }

    public Long getRecipeId() {
        return recipe_id;
    }

    public void setRecipeId(Long id) {
        this.recipe_id = id;
    }

    public void addIngredient(Ingredient ingredient){
        this.ingredients.add(ingredient);
        ingredient.getRecipes().add(this);
    }
    public void removeIngredient(long ingredientId){
        Ingredient ingredient = this.ingredients.stream()
                .filter(element -> element.getId() == ingredientId)
                .findFirst()
                .orElse(null);
        if (ingredient != null){
            this.ingredients.remove(ingredient);
            ingredient.getRecipes().remove(this);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getReadyInMinutes() {
        return readyInMinutes;
    }

    public void setReadyInMinutes(int readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return readyInMinutes == recipe.readyInMinutes  && calories == recipe.calories && Objects.equals(recipe_id, recipe.recipe_id) && Objects.equals(name, recipe.name) && Objects.equals(ingredients, recipe.ingredients) && Objects.equals(preparation, recipe.preparation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipe_id, name, readyInMinutes, calories, ingredients, preparation);
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "recipe_id=" + recipe_id +
                ", name='" + name + '\'' +
                ", readyInMinutes=" + readyInMinutes +
                ", calories=" + calories +
                ", portion=" + portion +
                ", ingredients=" + ingredients +
                ", preparation=" + preparation +
                '}';
    }
}
