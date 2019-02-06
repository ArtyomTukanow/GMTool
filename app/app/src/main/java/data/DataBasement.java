package data;

import org.json.JSONException;
import org.json.JSONObject;

import engine.utils.Utils;

public class DataBasement {

    protected JSONObject _jsonObject;
    public DataBasement() { _jsonObject = new JSONObject(); }
    public DataBasement(JSONObject object) { _jsonObject = object; }

    public Boolean has(String key) {
        try {
            return _jsonObject.has(key)
                    && !_jsonObject.get(key).equals(JSONObject.NULL);
        } catch (JSONException e) {
            Utils.log(e.getMessage());
            return false;
        }
    }

    public Object get(String key) {
        try {
            return _jsonObject.get(key);
        } catch (JSONException e) {
            Utils.log(e.getMessage());
            return null;
        }
    }

    public String getString(String key) {
        try {
            return (String)_jsonObject.get(key);
        } catch (JSONException e) {
            Utils.log(e.getMessage());
            return null;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public double getDouble(String key) {
        try {
            return Double.valueOf((String)_jsonObject.get(key));
        } catch (JSONException e) {
            Utils.log(e.getMessage());
            return 0;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public int getInt(String key) {
        try {
            return Integer.valueOf((String)_jsonObject.get(key));
        } catch (JSONException e) {
            Utils.log(e.getMessage());
            return 0;
        } catch (NullPointerException e) {
            return 0;
        }
    }

    public void setData(String key, Object value) {
        try {
            _jsonObject.put(key, value);
        } catch (JSONException e) {
            Utils.log(e.getMessage());
        }
    }

    public Boolean isNull() {
        return _jsonObject == null;
    }
}
