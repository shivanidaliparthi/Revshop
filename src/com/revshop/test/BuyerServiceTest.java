package com.revshop.test;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import com.revshop.service.BuyerService;

public class BuyerServiceTest {

    private BuyerService buyerService;

    @Before
    public void setUp() {
        buyerService = new BuyerService();
    }

    @Test
    public void testCartInitiallyEmpty() {
        int size = buyerService.getCartSize();
        assertEquals(0, size);
    }
}