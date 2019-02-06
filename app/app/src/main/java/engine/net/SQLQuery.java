package engine.net;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.Map;
import engine.utils.Utils;

public class SQLQuery extends URLReader {
    public static final String SQL_QUERY = URL + "sql/get_query.php/";

    public static final String QUERY_ADD_HERO = "AddHero";
    public static final String QUERY_ADD_RESOURCE = "AddResource";
    public static final String QUERY_ADD_RESOURCE_TO_INVENTORY = "AddResourceToInventory";
    public static final String QUERY_ADD_USER = "AddUser";
    public static final String QUERY_CREATE_GAME = "CreateGame";
    public static final String QUERY_GET_ALL_GAMES = "GetAllGames";
    public static final String QUERY_GET_ALL_RESOURCES = "GetAllResources";
    public static final String QUERY_GET_GAME = "GetGame";
    public static final String QUERY_GET_HERO = "GetHero";
    public static final String QUERY_GET_HEROES_BY_GAME = "GetHeroesByGame";
    public static final String QUERY_GET_HEROES_BY_USER = "GetHeroesByUser";
    public static final String QUERY_GET_RESOURCE_BY_NAME = "GetResourceByName";
    public static final String QUERY_GET_RESOURCE_BY_ID = "GetResourceById";
    public static final String QUERY_GET_USER = "GetUser";
    public static final String QUERY_GET_USER_BY_NAME = "GetUserByName";
    public static final String QUERY_LOGIN_USER = "LoginUser";
    public static final String QUERY_UPDATE_RESOURCE = "UpdateResource";

    public static final String SQL_RESULT_TRUE = "1";
    public static final String SQL_RESULT_NULL = "null";
    public static final String SQL_RESULT_CRASH = "";

//    /**
//     * Принимает значение true если результат строки равен "1" или чем-то еще (обычно, json строка)
//     * Принимает значение false при выводе пустрой строки или строки "null"
//     * Пустрая строка выводится при ошибке в самом SQL. "null" Выводится, если запрос в БД был успешным, но ничего не вывел
//     * @param result строка, которая пришла с запроса
//     */
//    public static boolean sqlSuccessfullyResult(String result) {
//        return result.equals(SQL_RESULT_TRUE) || !(result.equals(SQL_RESULT_NULL) || result.equals(SQL_RESULT_CRASH));
//    }

    public static Boolean isTrueSQLResult(JSONObject jsonObject) {
        try {
            return jsonObject.getString("result").equals("true");
        } catch (JSONException e) {
            return false;
        }
    }

    public static JSONObject sqlResultToJSON(String result) {
        result = result.replaceAll("<br>", "");
        try {
            if(result.equals(SQL_RESULT_CRASH))
                return new JSONObject();
            else if(result.equals(SQL_RESULT_NULL))
                return null;
            else if (result.equals(SQL_RESULT_TRUE))
                return new JSONObject("{\"result\":true}");
            else
                return new JSONObject(result);
        } catch (JSONException e) {
            Utils.log(e.getMessage());
        }
        return null;
    }

    public static JSONArray sqlResultToJSONArray(String result) {
        result = result.replaceAll("<br>", "");
        try {
            if(result.equals(SQL_RESULT_CRASH))
                return new JSONArray();
            else if(result.equals(SQL_RESULT_NULL))
                return null;
            else
                return new JSONArray(result);
        } catch (JSONException e) {
            Utils.log(e.getMessage());
        }
        return null;
    }

    public static String getQuery(String queryType) throws  Exception {
        return getQuery(queryType, new JSONObject());
    }

    public static String getQuery(String queryType, JSONObject params) throws  Exception {
        return getQuery(queryType, params, "");
    }

    public static String getQuery(String queryType, JSONObject params, String defParams) throws Exception {
        String strParams = params == null ? "&params={}" : "&params=" + URLEncoder.encode(params.toString(), "UTF-8");

        String url = SQL_QUERY + "?" + defParams + "query_name=" + queryType + strParams;
        Utils.log(url);
        return getData(url);
    }


    public static void startQuery(final JSONObject jsonParams, final String sqlQueryType, final ICompleteRunnable<JSONObject> runnable) {
        startQuery(jsonParams, sqlQueryType, "", runnable);
    }


    public static void startLoggableQuery(final JSONObject jsonParams, final String sqlQueryType, final ICompleteRunnable<JSONObject> runnable) {
        startQuery(jsonParams, sqlQueryType, "", runnable);
    }

    public static void startQuery(final JSONObject jsonParams, final String sqlQueryType, final String defParams, final ICompleteRunnable<JSONObject> runnable) {
        new AsyncTask<Void, Void, Void>() {
            String output = "";
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    output = SQLQuery.getQuery(sqlQueryType, jsonParams, defParams);
                } catch (Exception e) {
                    Utils.log(e.getCause() + " -- " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                runnable.onComplete(SQLQuery.sqlResultToJSON(output));
            }
        }.execute();
    }


    public static void startQueryArray(final JSONObject jsonParams, final String sqlQueryType, final ICompleteRunnable<JSONArray> runnable) {
        new AsyncTask<Void, Void, Void>() {
            String output = "";
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    output = SQLQuery.getQuery(sqlQueryType, jsonParams);
                } catch (Exception e) {
                    Utils.log(e.getCause() + " -- " + e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                runnable.onComplete(SQLQuery.sqlResultToJSONArray(output));
            }
        }.execute();
    }
}
