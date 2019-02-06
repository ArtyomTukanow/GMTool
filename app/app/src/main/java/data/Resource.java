package data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import engine.net.ICompleteRunnable;
import engine.net.SQLQuery;
import engine.utils.Utils;

public class Resource extends DataBasement {
    public static final String RESOURCE = "Resource";
    public static final String RESOURCE_ID = "resource_id";
    public static final String IMAGE = "image";
    public static final String NAME = "name";
    public static final String DESCRIPTION = "description";
    public static final String MIN_VALUE = "min_value";
    public static final String MAX_VALUE = "max_value";

    public Resource() { super(); }
    public Resource(JSONObject object) { super(object); }


    public static void GetResourceByName(String name, final ICompleteRunnable<Resource> callback) {
        try {
            JSONObject mapParams = new JSONObject() {{
                put(NAME, name);
            }};
            SQLQuery.startQuery(mapParams, SQLQuery.QUERY_GET_RESOURCE_BY_NAME, (result) -> callback.onComplete(new Resource(result)));
        } catch (JSONException e) { }
    }

    public static void GetResourceById(int id, final ICompleteRunnable<Resource> callback) {
        try {
            JSONObject mapParams = new JSONObject() {{
                put(RESOURCE_ID, id);
            }};
            SQLQuery.startQuery(mapParams, SQLQuery.QUERY_GET_RESOURCE_BY_ID, (result) -> callback.onComplete(new Resource(result)));
        } catch (JSONException e) { }
    }

    public static void AddResource(Resource resource,  final ICompleteRunnable<Boolean> callback) {
        try {
            JSONObject mapParams = new JSONObject() {{
                put(NAME,           resource.get(Resource.NAME));
                put(DESCRIPTION,    resource.get(Resource.DESCRIPTION));
                put(IMAGE,          resource.get(Resource.IMAGE));
                put(MIN_VALUE,      resource.get(Resource.MIN_VALUE));
                put(MAX_VALUE,      resource.get(Resource.MAX_VALUE));
            }};
            SQLQuery.startQuery(mapParams, SQLQuery.QUERY_ADD_RESOURCE, (jsonResult) ->
                    callback.onComplete(SQLQuery.isTrueSQLResult(jsonResult))
            );
        } catch (JSONException e) { }
    }

    public static void UpdateResource(Resource resource,  final ICompleteRunnable<Boolean> callback) {

        try {
            JSONObject mapParams = new JSONObject() {{
                put(RESOURCE_ID,    resource.get(Resource.RESOURCE_ID));
                put(NAME,           resource.get(Resource.NAME));
                put(DESCRIPTION,    resource.get(Resource.DESCRIPTION));
                put(IMAGE,          resource.get(Resource.IMAGE));
                put(MIN_VALUE,      resource.get(Resource.MIN_VALUE));
                put(MAX_VALUE,      resource.get(Resource.MAX_VALUE));
            }};
            SQLQuery.startLoggableQuery(mapParams, SQLQuery.QUERY_UPDATE_RESOURCE, (jsonResult) ->
                    callback.onComplete(SQLQuery.isTrueSQLResult(jsonResult))
            );
        } catch (JSONException e) { }
    }

    public static void GetAllResources(final ICompleteRunnable<Resource[]> callback) {
        SQLQuery.startQueryArray(null, SQLQuery.QUERY_GET_ALL_RESOURCES, (result) -> {

            Resource[] resources = new Resource[result.length()];
            for(int i = 0; i < result.length(); i ++) {
                try {
                    resources[i] = new Resource(result.getJSONObject(i));
                } catch (JSONException e) {
                    Utils.log(e.getMessage());
                }
            }
            callback.onComplete(resources);
        });
    }
}
