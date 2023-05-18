package com.Millennium.InMemoryCache.services;

import com.Millennium.InMemoryCache.bl.CacheService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class CacheServiceTest {

	@Autowired
	private CacheService cacheService;

	@Test
	public void testPutAndGet() {
		String key = "Key";
		String value = "Value";

		cacheService.put(key, value);
		Object retrievedValue = cacheService.get(key);

		assertEquals(value, retrievedValue);
	}

	@Test
	public void testPutSameKeyDifferentValue() {
		String key = "Key";
		String value1 = "Value1";
		String value2 = "Value2";

		cacheService.put(key, value1);
		cacheService.put(key, value2);
		Object retrievedValue = cacheService.get(key);

		assertEquals(value2, retrievedValue);
	}

	@Test
	public void testConcurrentRequests() throws InterruptedException {
		CacheService<String, Integer> cacheService = new CacheService<>();
		int numRequests = 1000;

		ExecutorService executorService = Executors.newFixedThreadPool(numRequests);

		for (int i = 0; i < numRequests; i++) {
			final int requestNumber = i;
			executorService.submit(() -> {
				String key = "Key" + requestNumber;
				Integer value = requestNumber;
				cacheService.put(key, value);
				Integer retrievedValue = cacheService.get(key);
				assertEquals(value, retrievedValue);
			});
		}

		executorService.shutdown();
		executorService.awaitTermination(1, TimeUnit.MINUTES);

		// Verify the expected behavior
		verify(cacheService, times(numRequests)).put(anyString(), any(Integer.class));
		verify(cacheService, times(numRequests)).get(anyString());
	}
}
