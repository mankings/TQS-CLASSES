package mankings.tqs.geocoding;

public class Address {
    private String road;
    private String state;
    private String city;
    private String zio;
    private String houseNumber;

    public Address(String road, String state, String city, String zio, String houseNumber) {
        this.road = road;
        this.state = state;
        this.city = city;
        this.zio = zio;
        this.houseNumber = houseNumber;
    }

    @Override
    public String toString() {
        return "Address{" + "road=" + road + ", city=" + city + ", state=" + state + ", zio=" + zio + ", houseNumber=" + houseNumber + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(this.getClass() != obj.getClass())
            return false;
        Address other = (Address) obj;
        if(this.getRoad() != other.getRoad())
            return false;
        if(this.getState() != other.getState())
            return false;
        if(this.getCity() != other.getCity())
            return false;
        if(this.getZio() != other.getZio())
            return false;
        if(this.getHouseNumber() != other.getHouseNumber())
            return false;
        return true;
    }

    public String getRoad() {
        return road;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getZio() {
        return zio;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setRoad(String road) {
        this.road = road;
    }
    
    public void setState(String state) {
        this.state = state;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZio(String zio) {
        this.zio = zio;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
}
