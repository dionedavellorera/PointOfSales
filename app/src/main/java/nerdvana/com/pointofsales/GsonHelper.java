package nerdvana.com.pointofsales;

import com.google.gson.Gson;

public class GsonHelper {
    private static Gson gson;
    public GsonHelper() {
        gson = new Gson();
    }

    public static Gson getGson() {
        return gson;
    }
}
