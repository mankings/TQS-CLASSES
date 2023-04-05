package tqs.airquality.cache;

import org.springframework.stereotype.Service;

import tqs.airquality.model.CacheStats;

@Service
public class CacheTracker {
    private long totalRequests, hits;
    private long avgFetchTime, avgCacheTime;

    public CacheTracker() {
        this.totalRequests = 0;
        this.hits = 0;
        this.avgFetchTime = 0;
        this.avgCacheTime = 0;
    }

    public void requestTrack(boolean cached, long requesttime) {
        this.totalRequests += 1;
        if(cached) {
            this.hits += 1;
            this.avgCacheTime = (this.avgCacheTime * (this.hits - 1) + requesttime) / this.hits;
        } else {
            this.avgFetchTime = (this.avgCacheTime * (this.totalRequests - this.hits - 1) + requesttime) / this.totalRequests - this.hits;
        }
    }

    public long getTotalRequests() {
        return totalRequests;
    }

    public long getHits() {
        return hits;
    }

    public long getAvgFetchTime() {
        return avgFetchTime;
    }

    public long getAvgCacheTime() {
        return avgCacheTime;
    }

    public CacheStats getStats() {
        return new CacheStats(totalRequests, hits, avgFetchTime, avgCacheTime);
    }

    @Override
    public String toString() {
        return String.format("[total: %d; hits: %d; avgfetch: %d; avgcache: %d]", totalRequests, hits, avgFetchTime, avgCacheTime);
    }

}
