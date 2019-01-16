package nerdvana.com.pointofsales.custom;

public class BusProvider {
    private static com.squareup.otto.Bus bus;

    public BusProvider() {
        BusProvider.bus = new com.squareup.otto.Bus();
    }

    public static com.squareup.otto.Bus getInstance() {
        return bus;
    }
}
