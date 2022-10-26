package at.ac.fhcampuswien.foodaddicts.repository;

import at.ac.fhcampuswien.foodaddicts.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;



@Repository
@Transactional(readOnly = true)
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findByName(String name);

    @Query(value = "SELECT r FROM Recipe r WHERE r.calories >= ?1 AND r.calories <= ?2")
    List<Recipe> findByMinMaxCal(double minCal, double maxCal);
}
