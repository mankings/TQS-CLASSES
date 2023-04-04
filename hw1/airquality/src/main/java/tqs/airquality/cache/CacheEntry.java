package tqs.airquality.cache;

import java.util.Date;

public class CacheEntry {
    private Object value;
    private Date ts;

    public CacheEntry(Object val) {
        this.value = val;
        this.ts = new Date();
    }

    public Object getValue() {
        return value;
    }

    public Date getTs() {
        return ts;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setTs(Date ts) {
        this.ts = ts;
    }
}
