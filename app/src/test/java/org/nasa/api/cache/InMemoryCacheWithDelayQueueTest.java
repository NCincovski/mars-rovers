package org.nasa.api.cache;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class InMemoryCacheWithDelayQueueTest {
    public static final String TEST = "TEST";
    public static final long CACHE_INVALIDATION_PERIOD = 5000L;

    static InMemoryCacheWithDelayQueue cache = new InMemoryCacheWithDelayQueue();

    @BeforeClass
    public static void beforeClass() {
        cache.initialize();
    }

    @After
    public void tearDown() {
        cache.clear();
    }

    @Test
    public void testAdd() {
        addEntry();

        sleep(1000L);
        verifyValidEntry();

        sleep(CACHE_INVALIDATION_PERIOD + 1);
        assertNull(cache.get(TEST));
    }

    @Test
    public void testAdd_SameEntryTwice() {
        final long firstTimeMillis = System.currentTimeMillis();
        System.out.println("First time: " + firstTimeMillis);
        addEntry();
        sleep(1000L);
        verifyValidEntry();
        final long secondTimeMillis = System.currentTimeMillis();
        System.out.println("Second time: " + secondTimeMillis + ". Passed (ms): " + (secondTimeMillis - firstTimeMillis));
        addEntry();
        assertEquals(1, cache.size());
        sleep(CACHE_INVALIDATION_PERIOD - 1000L);
        final long firstCheckTimeMillis = System.currentTimeMillis();
        System.out.println("First check: " + firstCheckTimeMillis + ". Passed (ms): " + (firstCheckTimeMillis - firstTimeMillis));
        assertEquals(1, cache.size());

        sleep(1000L);
        final long secondCheckTimeMillis = System.currentTimeMillis();
        System.out.println("Second check: " + secondCheckTimeMillis + ". Passed (ms): " + (secondCheckTimeMillis - firstTimeMillis));
        assertNull(cache.get(TEST));
    }

    @Test
    public void testRemove() {
        addEntry();
        assertNotNull(cache.get(TEST));

        cache.remove(TEST);
        assertNull(cache.get(TEST));
        assertEquals(0, cache.size());
    }

    @Test
    public void testGet() {
        addEntry();

        sleep(1000L);
        verifyValidEntry();
    }

    @Test
    public void testClear() {
        addEntry();
        verifyValidEntry();

        cache.clear();
        assertEquals(0, cache.size());
    }

    @Test
    public void testSize() {
        addEntry();
        assertEquals(1, cache.size());
        sleep(CACHE_INVALIDATION_PERIOD + 500L);
        assertEquals(0, cache.size());
        addEntry();
        cache.add(TEST + TEST, TEST, CACHE_INVALIDATION_PERIOD);
        assertEquals(2, cache.size());
    }

    private void verifyValidEntry() {
        final Object value = cache.get(TEST);
        assertNotNull(value);
        assertTrue(value instanceof String);
        assertEquals(TEST, value);
    }

    private void addEntry() {
        cache.add(TEST, TEST, CACHE_INVALIDATION_PERIOD);
    }

    private void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}