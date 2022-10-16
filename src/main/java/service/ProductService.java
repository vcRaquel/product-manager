package service;

import components.HandleString;
import customExceptions.ProductAlreadRegisteredException;
import customExceptions.ProductNotRegisteredException;
import model.Category;
import model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.CategoryRepository;
import repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryService categoryservice;
    @Autowired
    HandleString handleString;

    public boolean productExistsName(String productName) {
        String handledProductName = handleString.handleString(productName);

        Optional<Product> productOptional = productRepository.searchProductByNameHandled(handledProductName);

        if (!productOptional.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    private void percorrerListaCategoriasAdicionandoProdutoAListaDeProdutos(List<Category> categories, Product product){
        for (Category referencia : categories){
            categoryservice.addProductInProducts(referencia,product);
        }
    }

    public Product productSave(Product product) {
        String handledProductName = handleString.handleString(product.getProductName());

        if (productExistsName(handledProductName)) {
            throw new ProductAlreadRegisteredException("Produto já cadastrado");
        }
        product.setHandledProductName(handledProductName);
        percorrerListaCategoriasAdicionandoProdutoAListaDeProdutos(product.getCategories(),product);
        productRepository.save(product);

        return product;
    }

    private boolean categoriaExisteNaListaDeCategoriasDeUmProduto(Category category, Product product) {
        List<Category> categories = product.getCategories();
        String handledCategoryName = handleString.handleString(category.getCategoryName());
        for (Category referencia : categories) {

            if (referencia.getHandledCategoryName().equals(handledCategoryName)) {
                return true;
            }
        }
        return false;
    }

    private List<Category> addCategoryInCategories(Category category, Product product) {
        if (!categoriaExisteNaListaDeCategoriasDeUmProduto(category, product)){
            List<Category>categories = product.getCategories();
            categories.add(category);
            productRepository.save(product);
        }
        return product.getCategories();
    }

    private List<Category> addCategoriesInCategoriesList(List<Category> categoriesToUpdate, Product product){
        for (Category referencia : categoriesToUpdate) {
            if (!categoriaExisteNaListaDeCategoriasDeUmProduto(referencia,product)){
                categoriesToUpdate.add(referencia);
            }
        }
        return categoriesToUpdate;
    }

    public Product productUpdate(String productName, Product product) {
        String handledProductName = handleString.handleString(productName);
        Optional<Product> productOptional = productRepository.searchProductByNameHandled(handledProductName);

        if (!productOptional.isEmpty()) {
            Product productToUpdate = productOptional.get();
            productToUpdate.setProductName(product.getProductName());
            String handledProductNameToUpdate = handleString.handleString(product.getProductName());
            productToUpdate.setHandledProductName(handledProductNameToUpdate);
            addCategoriesInCategoriesList(product.getCategories(),productToUpdate);
            percorrerListaCategoriasAdicionandoProdutoAListaDeProdutos(productToUpdate.getCategories(),product); //?
            productRepository.save(productToUpdate);
            return productToUpdate;
        } else {
            throw new ProductNotRegisteredException("Produto não cadastrado");
        }
    }

}
