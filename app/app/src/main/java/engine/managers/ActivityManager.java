package engine.managers;

import android.app.Activity;
import android.content.Intent;

import data.Resource;
import gmtool.bitdot.com.gmtool.gui.EditorActivity;
import gmtool.bitdot.com.gmtool.gui.LoginActivity;
import gmtool.bitdot.com.gmtool.gui.MainActivity;
import gmtool.bitdot.com.gmtool.gui.RegisterActivity;
import gmtool.bitdot.com.gmtool.gui.ResourceActivity;

public class ActivityManager {

    private static ActivityManager _instance;
    public static ActivityManager instance() {
        if(_instance == null) _instance = new ActivityManager();
        return  _instance;
    }
    public ActivityManager() {

    }

    public void startMainActivity(Activity currentContext) {
        Intent intent = new Intent(currentContext, MainActivity.class);
        currentContext.startActivity(intent);
    }

    public void startLoginActivity(Activity currentContext, String name, String password) {
        Intent intent = new Intent(currentContext, LoginActivity.class);
        intent.putExtra(LoginActivity.USER_NAME_PUT_EXTRA, name);
        intent.putExtra(LoginActivity.USER_PASSWORD_PUT_EXTRA, password);
        currentContext.startActivity(intent);
    }

    public void startRegisterActivity(Activity currentContext, String name, String password) {
        Intent intent = new Intent(currentContext, RegisterActivity.class);
        intent.putExtra(RegisterActivity.USER_NAME_PUT_EXTRA, name);
        intent.putExtra(RegisterActivity.USER_PASSWORD_PUT_EXTRA, password);
        currentContext.startActivity(intent);
    }

    public void startEditorActivity(Activity currentContext) {
        Intent intent = new Intent(currentContext, EditorActivity.class);
        currentContext.startActivity(intent);
    }

    public void startEditResourceActivity(Activity currentContext, Resource resource) {
        Intent intent = new Intent(currentContext, ResourceActivity.class);
        intent.putExtra(Resource.RESOURCE_ID, resource.getInt(Resource.RESOURCE_ID));
        currentContext.startActivity(intent);
    }

    public void startEditResourceActivity(Activity currentContext) {
        Intent intent = new Intent(currentContext, ResourceActivity.class);
        currentContext.startActivity(intent);
    }
}
