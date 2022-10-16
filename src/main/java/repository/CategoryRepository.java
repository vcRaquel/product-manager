package repository;

import model.Category;
import model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

    @Query(value = "select * from categories  WHERE handled_Category_Name = :categoryName",
            nativeQuery = true)
    Optional<Category> searchCategoryByNameHandled(String categoryName);
}
