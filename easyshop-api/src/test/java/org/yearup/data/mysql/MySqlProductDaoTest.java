package org.yearup.data.mysql;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.yearup.models.Product;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MySqlProductDaoTest extends BaseDaoTestClass
{
    private MySqlProductDao dao;

    @BeforeEach
    public void setup()
    {
        dao = new MySqlProductDao(dataSource);
    }

    @Test
    public void getById_shouldReturn_theCorrectProduct()
    {
        // arrange
        int productId = 1;
        Product expected = new Product()
        {{
            setProductId(1);
            setName("Smartphone");
            setPrice(new BigDecimal("499.99"));
            setCategoryId(1);
            setDescription("A powerful and feature-rich smartphone for all your communication needs.");
            setColor("Black");
            setStock(50);
            setFeatured(false);
            setImageUrl("smartphone.jpg");
        }};

        // act
        var actual = dao.getById(productId);

        // assert
        assertEquals(expected.getPrice(), actual.getPrice(), "Because I tried to get product 1 from the database.");
    }

    @Test
    public void searchProductsByMinMaxPrice_shouldReturnTheCorrectNumberOfProducts(){

        // arrange

        BigDecimal minPrice = BigDecimal.valueOf(700);
        BigDecimal maxPrice = BigDecimal.valueOf(1500);

        // act
        List<Product> products = dao.search(-1, minPrice, maxPrice, "");

        //assert
        assertEquals(2, products.size());
    }

    @Test
    public void updateProduct_shouldUpdateProductWithoutDuplication() {
        // Arrange
        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(new BigDecimal("9.99"));
        product.setCategoryId(1);
        product.setDescription("Test Description");
        product.setColor("");
        product.setImageUrl("");
        product.setStock(1);
        product.setFeatured(true);

        int idProductUpdate = 1;
        dao.update(idProductUpdate, product);

        // Act

        var updatedProduct = dao.getById(idProductUpdate);

        // Assert
        assertEquals("Test Description", updatedProduct.getDescription());
        assertEquals(1, updatedProduct.getStock());


    }


}