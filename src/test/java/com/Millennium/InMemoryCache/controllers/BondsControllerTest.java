package com.Millennium.InMemoryCache.controllers;

import com.Millennium.InMemoryCache.bl.CacheService;
import com.Millennium.InMemoryCache.model.Bond;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class BondsControllerTest {

    @Mock
    private CacheService cacheService;

    @InjectMocks
    private BondsController bondsController;

    @Test
    public void testGetBond_ValidBondId_ReturnsBond() {
        String bondId = "123";
        Bond bond = new Bond();
        bond.setBondId(bondId);
        bond.setName("Bond Name");

        when(cacheService.get(bondId)).thenReturn(bond);

        ResponseEntity<Bond> response = bondsController.getBond(bondId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(bond, response.getBody());
    }

    @Test
    public void testGetBond_InvalidBondId_ReturnsNotFound() {
        String bondId = "456";

        when(cacheService.get(bondId)).thenReturn(null);

        ResponseEntity<Bond> response = bondsController.getBond(bondId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testPostBond_ValidBond_ReturnsCreated() {
        Bond bond = new Bond();
        bond.setBondId("123");
        bond.setName("Bond Name");


        ResponseEntity<Void> response = bondsController.postBond(bond);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testDeleteBond_ValidBondId_ReturnsOk() {
        String bondId = "123";

        ResponseEntity<Void> response = bondsController.deleteBond(bondId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testConcurrentRequests() throws InterruptedException {
        int numRequests = 1000;
        ExecutorService executorService = Executors.newFixedThreadPool(numRequests);

        // Configure the mock behavior for put and get methods
        doNothing().when(cacheService).put(anyString(), any(Bond.class));
        when(cacheService.get(anyString())).thenReturn(new Bond());

        // Submit concurrent requests
        for (int i = 0; i < numRequests; i++) {
            executorService.submit(() -> {
                cacheService.put("key", new Bond());
                cacheService.get("key");
            });
        }

        // Shutdown the executor service and wait for all tasks to complete
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        verify(cacheService, times(numRequests)).put(anyString(), any(Bond.class));
        verify(cacheService, times(numRequests)).get(anyString());
    }

}
