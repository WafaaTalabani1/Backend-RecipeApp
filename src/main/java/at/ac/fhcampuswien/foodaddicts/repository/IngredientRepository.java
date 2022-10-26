package at.ac.fhcampuswien.foodaddicts.repository;

import at.ac.fhcampuswien.foodaddicts.model.Ingredient;
import at.ac.fhcampuswien.foodaddicts.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findIngredientByAmountAndNameAndUnit(double amount, String name, String unit);
    default boolean ingredientExists(Ingredient ingredient){
        List<Ingredient> ingredients = findIngredientByAmountAndNameAndUnit(ingredient.getAmount(), ingredient.getName(), ingredient.getUnit());
        if(ingredients == null)
            return false;
        return !ingredients.isEmpty();
    }
}
