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
import com.revshop.service.SellerService;

@RunWith(MockitoJUnitRunner.class)
public class SellerServiceTest {

    @Mock
    private ProductDAO productDAO;

    @InjectMocks
    private SellerService sellerService;

    @Test
    public void testViewProducts() {

        List<Product> products = Arrays.asList(
            new Product(1, "TV", "Electronics", 30000, 5, 10),
            new Product(2, "AC", "Electronics", 40000, 10, 4)
        );

        when(productDAO.getAll()).thenReturn(products);

        int size = productDAO.getAll().size();

        assertEquals(2, size);
    }
}
