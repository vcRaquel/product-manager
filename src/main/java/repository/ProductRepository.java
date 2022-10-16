package repository;

import model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Integer> {

    @Query(value = "select * from products  WHERE handled_Product_Name = :productName",
            nativeQuery = true)
    Optional<Product> searchProductByNameHandled(String productName);
}
