package tqs.airquality.cache;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AirCacheTest {
    private AirCache cache;
    private long test_expiracy = 2;

    @BeforeEach
    public void setup() {
        this.cache = new AirCache();
        this.cache.setExpiracy(test_expiracy);
    }

    @Test
    public void expiracy() throws InterruptedException {
        this.cache.put("key", "value");
        Thread.sleep((test_expiracy + 1) * 1000);
        assertTrue(this.cache.get("key").isEmpty());

        this.cache.put("key", "value");
        Thread.sleep((test_expiracy/2) * 1000);
        assertFalse(this.cache.get("key").isEmpty());
    }

    @Test
    public void noMatch() {
        this.cache.get("quem");
        assertTrue(this.cache.get("key").isEmpty());
    }
}
