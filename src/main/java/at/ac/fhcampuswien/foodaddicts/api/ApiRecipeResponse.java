package at.ac.fhcampuswien.foodaddicts.api;

public class ApiRecipeResponse {
    /*
    * "calories": 210,
        "carbs": "43g",
        "fat": "3g",
        "id": 90629,
        "image": "https://spoonacular.com/recipeImages/90629-312x231.jpg",
        "imageType": "jpg",
        "protein": "1g",
        "title": "Baked Apples in White Wine"
    * */

    private long id;
    private String title;
    private double calories;

    public ApiRecipeResponse() {
    }

    public ApiRecipeResponse(long id, String title, double calories) {
        this.id = id;
        this.title = title;
        this.calories = calories;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "ApiRecipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", calories=" + calories +
                '}';
    }
}
