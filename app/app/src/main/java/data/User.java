package data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import engine.net.ICompleteRunnable;
import engine.net.SQLQuery;

public class User extends DataBasement {
    public static final String USER = "User";
    public static final String USER_ID = "user_id";
    public static final String NAME = "name";
    public static final String PASSWORD = "password";
    public static final String AVATAR = "avatar";

    public User(JSONObject object) { super(object); }

    public static void GetUserByName(String name, final ICompleteRunnable<User> callback) {
        try {
            JSONObject mapParams = new JSONObject() {{
                put(NAME, name);
            }};
            SQLQuery.startQuery(mapParams, SQLQuery.QUERY_GET_USER_BY_NAME, (result) -> callback.onComplete(new User(result)));
        } catch (JSONException e) { }
    }

    public static void LoginUser(String name, String password, final ICompleteRunnable<User> callback) {
        try {
            JSONObject mapParams = new JSONObject() {{
                put(NAME, name);
                put(PASSWORD, password);
            }};
            SQLQuery.startQuery(mapParams, SQLQuery.QUERY_LOGIN_USER, (result) -> callback.onComplete(new User(result)));
        } catch (JSONException e) { }
    }

    public static void AddUser(String name, String password, String avatar, final ICompleteRunnable<User> callback) {
        try {
            JSONObject mapParams = new JSONObject() {{
                put(NAME, name);
                put(PASSWORD, password);
                put(AVATAR, avatar);
            }};
            SQLQuery.startQuery(mapParams, SQLQuery.QUERY_ADD_USER, (result) -> callback.onComplete(new User(result)));
        } catch (JSONException e) { }
    }
}
