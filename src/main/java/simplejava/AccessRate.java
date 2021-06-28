package simplejava;

public class AccessRate implements Comparable<AccessRate>{

    private Integer key;
    private Integer hitCount;
    private Long lastTime;

    public void incrementHitCount() {
        this.hitCount += 1;
    }

    /**
     * class to get access rate of an entry in cache
     * @param key entry key
     * @param hitCount access rate
     * @param lastTime the value was accessed in cache
     */
    public AccessRate(Integer key, Integer hitCount, Long lastTime) {
        this.key = key;
        this.hitCount = hitCount;
        this.lastTime = lastTime;
    }

    public void setLastTime(Long lastTime) {
        this.lastTime = lastTime;
    }

    public Integer getKey() {
        return key;
    }

    public int compareTo(AccessRate o) {
        int hr = hitCount.compareTo(o.hitCount);
        return hr != 0 ? hr : lastTime.compareTo(o.lastTime);
    }

}
