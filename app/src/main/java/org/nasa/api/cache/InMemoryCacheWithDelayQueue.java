package org.nasa.api.cache;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import java.lang.ref.SoftReference;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simple in-memory cache implementation using {@link ConcurrentHashMap} and {@link DelayQueue}
 *
 * @author Nenad Cincovski
 */
@Singleton
@Startup
public class InMemoryCacheWithDelayQueue {
    public static final Logger LOGGER = Logger.getLogger(InMemoryCacheWithDelayQueue.class.getName());

    private final ConcurrentHashMap<String, SoftReference<Object>> cache = new ConcurrentHashMap<>();
    private final DelayQueue<DelayedCacheObject> cleaningUpQueue = new DelayQueue<>();

    /**
     * Initialization method where demon thread is created which responsibility is to clean the cache
     */
    @PostConstruct
    public void initialize() {
        Thread cleanerThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    DelayedCacheObject delayedCacheObject = cleaningUpQueue.take();
                    cache.remove(delayedCacheObject.getKey(), delayedCacheObject.getReference());
                    LOGGER.log(Level.INFO, "Removed cache entry [key: {0}]", delayedCacheObject.getKey());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        cleanerThread.setDaemon(true);
        cleanerThread.start();
    }

    /**
     * Method to add new pair of key-value in the cache along with the expiration time
     *
     * @param key            the key
     * @param value          the value
     * @param periodInMillis time in milliseconds the entry will remain in the cache before removed
     */
    public void add(String key, Object value, long periodInMillis) {
        if (key == null) {
            return;
        }
        if (value == null) {
            cache.remove(key);
        } else {
            long expiryTime = System.currentTimeMillis() + periodInMillis;
            SoftReference<Object> reference = new SoftReference<>(value);
            cache.put(key, reference);
            cleaningUpQueue.put(new DelayedCacheObject(key, reference, expiryTime));
            LOGGER.log(Level.INFO, "Added new cache entry [key: {0}, expire time: {1}]", //wrap
                    new Object[]{key, expiryTime});
        }
    }

    /**
     * Method to manually remove the key from the cache
     *
     * @param key the key to be removed
     */
    public void remove(String key) {
        cache.remove(key);
    }

    /**
     * Method to get the value, if any, for given key, otherwise null
     *
     * @param key the key
     * @return the value stored for given key, or null if no such value
     */
    public Object get(String key) {
        return Optional.ofNullable(cache.get(key)).map(SoftReference::get).orElse(null);
    }

    /**
     * Operation to clean all cache
     */
    public void clear() {
        cache.clear();
    }

    /**
     * Operation for the size of the cache
     *
     * @return size of the cache
     */
    public long size() {
        return cache.size();
    }

    private static class DelayedCacheObject implements Delayed {
        private final String key;
        private final SoftReference<Object> reference;
        private final long expiryTime;

        public DelayedCacheObject(String key, SoftReference<Object> reference, long expiryTime) {
            this.key = key;
            this.reference = reference;
            this.expiryTime = expiryTime;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(expiryTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return Long.compare(expiryTime, ((DelayedCacheObject) o).expiryTime);
        }

        public String getKey() {
            return key;
        }

        public SoftReference<Object> getReference() {
            return reference;
        }
    }
}
