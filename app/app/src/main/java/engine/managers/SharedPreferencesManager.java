package engine.managers;

import android.content.Context;
import android.content.SharedPreferences;
import engine.utils.Utils;

public class SharedPreferencesManager {

    public static final String APP_PREFERENCES = "GMToolPreference";

    public static final String USER_ID_SHARED_PREFERENCES = "userId";
    public static final String USER_NAME_SHARED_PREFERENCES = "userName";
    public static final String PASSWORD_SHARED_PREFERENCES = "password";
    public static final String TOKEN_SHARED_PREFERENCES = "token";

    public static SharedPreferences sharedPreferences;

    public static void initSharedPreferences(Context context) {
        if(sharedPreferences == null)
            SharedPreferencesManager.sharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        else
            Utils.log("SharedPreferences inited already");
    }


    public static void loginUser(int id, String name, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_ID_SHARED_PREFERENCES, id);
        editor.putString(USER_NAME_SHARED_PREFERENCES, name);
        editor.putString(PASSWORD_SHARED_PREFERENCES, password);
        editor.apply();
    }

    public static void logoutUser() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(USER_ID_SHARED_PREFERENCES);
        editor.remove(USER_NAME_SHARED_PREFERENCES);
        editor.remove(PASSWORD_SHARED_PREFERENCES);
        editor.apply();
    }

    public static void setToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_SHARED_PREFERENCES, token);
        editor.apply();
    }

    public static String getUserName() {
        return sharedPreferences.getString(USER_NAME_SHARED_PREFERENCES, "");
    }

    public static String getUserPassword() {
        return sharedPreferences.getString(PASSWORD_SHARED_PREFERENCES, "");
    }

    public static int getUserId() {
        return sharedPreferences.getInt(USER_ID_SHARED_PREFERENCES, -1);
    }

    public static String getToken() {
        return sharedPreferences.getString(TOKEN_SHARED_PREFERENCES, "");
    }
}
