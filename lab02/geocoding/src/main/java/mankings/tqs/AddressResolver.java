package mankings.tqs;

import java.util.Optional;

public class AddressResolver {
    ISimpleHttpClient httpClient;

    public AddressResolver(ISimpleHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public Optional<Address> findAddressForLocation(double lat, double lon) {
        return null;
    }
}
