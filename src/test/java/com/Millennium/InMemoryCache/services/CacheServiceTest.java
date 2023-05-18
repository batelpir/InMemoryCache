package com.Millennium.InMemoryCache.services;

import com.Millennium.InMemoryCache.bl.CacheService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


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
}
