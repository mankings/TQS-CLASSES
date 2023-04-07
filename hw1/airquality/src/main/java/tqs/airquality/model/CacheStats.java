package tqs.airquality.model;

public class CacheStats {
    private long total;
    private long hits;
    private long noCacheTime; 
    private long cacheTime;


    public CacheStats(long total, long hits, long noCacheTime, long cacheTime) {
        this.total = total;
        this.hits = hits;
        this.noCacheTime = noCacheTime;
        this.cacheTime = cacheTime;
    }

    public long getTotal() {
        return total;
    }

    public long getHits() {
        return hits;
    }

    public long getNoCacheTime() {
        return noCacheTime;
    }

    public long getCacheTime() {
        return cacheTime;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setHits(long hits) {
        this.hits = hits;
    }

    public void setNoCacheTime(long noCacheTime) {
        this.noCacheTime = noCacheTime;
    }

    public void setCacheTime(long cacheTime) {
        this.cacheTime = cacheTime;
    }

    @Override
    public String toString() {
        return "CacheStats [total="+ total + ", hits=" + hits + ", noCacheTime=" + noCacheTime + ", cacheTime=" + cacheTime + "]";
    }
}
