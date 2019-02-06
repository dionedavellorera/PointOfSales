package nerdvana.com.pointofsales;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {

    private static final String APP_SETTINGS = "APP_SETTINGS";
    private static SharedPreferences sharedPreferences;
    public SharedPreferenceManager(Context context) {
        SharedPreferenceManager.sharedPreferences = context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);
    }


    public static String getString(Context context, String key) {
        return SharedPreferenceManager.sharedPreferences.getString(key , "");
    }

    public static void saveString(Context context, String newValue, String key) {
        final SharedPreferences.Editor editor = SharedPreferenceManager.sharedPreferences.edit();
        editor.putString(key , newValue);
        editor.apply();
    }

    public static void clearPreference(Context context) {
        final SharedPreferences.Editor editor = SharedPreferenceManager.sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
