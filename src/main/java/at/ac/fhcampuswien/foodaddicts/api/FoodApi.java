package at.ac.fhcampuswien.foodaddicts.api;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FoodApi {
    private static final String API_KEY = "147258c1ba98434ba93352b6fe6d2b91";
    private static final String BASE_URL = "https://api.spoonacular.com/recipes/";
    private static final Gson gson = new Gson();

    public static List<ApiRecipe> getRecipesByCalories(double minCal, double maxCal) throws FoodApiException {
        String url = BASE_URL + "findByNutrients?minCalories=" + minCal +"&maxCalories=" + maxCal + "&number=4" + "&apiKey=" + API_KEY;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        List<ApiRecipeResponse> recipes  = null;
        List<ApiRecipe> apiRecipes = new ArrayList<>();
        try (Response response = client.newCall(request).execute()) {
            String json = Objects.requireNonNull(response.body()).string();
            Type collectionType = new TypeToken<List<ApiRecipeResponse>>(){}.getType();
            recipes = gson.fromJson( json , collectionType);
            for (ApiRecipeResponse recipeResponse : recipes){
                url = BASE_URL + recipeResponse.getId() + "/information?apiKey=" + API_KEY;
                System.out.println(url);
                request = new Request.Builder().url(url).build();
                try(Response response1 = client.newCall(request).execute()) {
                    json = Objects.requireNonNull(response1.body()).string();
                    System.out.println(json);
                    ApiRecipe apiRecipe = gson.fromJson(json, ApiRecipe.class);
                    apiRecipe.setCalories(recipeResponse.getCalories());
                    for (ApiIngredient apiIngredient : apiRecipe.getExtendedIngredients()) {
                        if (!apiIngredient.getUnit().matches("g...")){
                            //ingredientName=flour&sourceAmount=2.5&sourceUnit=cups&targetUnit=grams
                            url = BASE_URL + "convert/?apiKey=" + API_KEY + "&ingredientName=" + apiIngredient.getName()
                                    + "&sourceAmount=" + apiIngredient.getAmount()
                                    + "&sourceUnit=" + apiIngredient.getUnit() + "&targetUnit=grams";
                            System.out.println(url);
                            request = new Request.Builder().url(url).build();
                            try(Response response2 = client.newCall(request).execute()) {
                                json = Objects.requireNonNull(response2.body()).string();
                                System.out.println(json);
                                ApiIngredientResponse ingredientResponse =  gson.fromJson(json, ApiIngredientResponse.class);
                                apiIngredient.setAmount(ingredientResponse.getTargetAmount());
                                apiIngredient.setUnit("g");
                            }
                        }
                    }
                    apiRecipes.add(apiRecipe);
                }
            }
        } catch (IOException e) {
            throw new FoodApiException("Could not parse the response!", e);
        }
        return apiRecipes;
    }

    public static void main(String[] args) {
        try {
            List<ApiRecipe> recipes = getRecipesByCalories(10, 1000);
            for (ApiRecipe recipe : recipes){
                System.out.println(recipe);
            }
        } catch (FoodApiException e) {
            throw new RuntimeException(e);
        }
    }
}
