package com.revshop.test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import com.revshop.dao.ProductDAO;
import com.revshop.model.Product;
import com.revshop.service.BuyerService;

@RunWith(MockitoJUnitRunner.class)
public class BuyerServiceTest {

    @Mock
    private ProductDAO productDAO;

    @InjectMocks
    private BuyerService buyerService;

    @Test
    public void testViewProducts() {

        List<Product> products = Arrays.asList(
            new Product(1, "Phone", "Electronics", 10000, 10, 5),
            new Product(2, "Laptop", "Electronics", 50000, 5, 3)
        );

        when(productDAO.getAll()).thenReturn(products);

        int productCount = productDAO.getAll().size();

        assertEquals(2, productCount);
    }
}