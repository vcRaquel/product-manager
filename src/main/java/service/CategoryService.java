package service;

import components.HandleString;
import customExceptions.CategoryAlreadRegisteredException;
import customExceptions.CategoryNotRegisteredException;
import model.Category;
import model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    HandleString handleString;

    public boolean categoryExistsName(String categoryName){
        String handledCategoryName = handleString.handleString(categoryName);

        Optional<Category> categoryOptional = categoryRepository.searchCategoryByNameHandled(handledCategoryName);

        if (!categoryOptional.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public Category categorySave(Category category){
        String categoryName = handleString.handleString(category.getCategoryName());

        if (categoryExistsName(categoryName)){
            throw new CategoryAlreadRegisteredException("Categoria já cadastrada");
        }
        category.setHandledCategoryName(categoryName);
        categoryRepository.save(category);
        return category;
    }

    private boolean produtoExisteNaListaDeProdutosDeUmaCategoria(Category category, Product product) {
            List<Product> products = category.getProducts();
            String handledProductName = handleString.handleString(product.getProductName());
            for (Product referencia : products) {

                if (referencia.getHandledProductName().equals(handledProductName)) {
                    return true;
                }
            }
            return false;
        }

        public List<Product> addProductInProducts(Category category, Product product) {
            if (!produtoExisteNaListaDeProdutosDeUmaCategoria(category, product)){
                List<Product>products = category.getProducts();
                products.add(product);
                categoryRepository.save(category);
            }
            return category.getProducts();
        }

    public List<Category> getAllCategories(){
        Iterable<Category> categories = categoryRepository.findAll();
        return (List<Category>) categories;
    }

    public void categoryUpdate(String categoryName){
        String handledCategoryName = handleString.handleString(categoryName);
        Optional<Category> categoryOptional = categoryRepository.searchCategoryByNameHandled(handledCategoryName);

        if (!categoryOptional.isEmpty()){
            Category categoryToUpdate = categoryOptional.get();
            categoryToUpdate.setCategoryName(categoryName);
        }else{
            throw new CategoryNotRegisteredException("Categoria não cadastrada");
        }
    }
}
