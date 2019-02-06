package gmtool.bitdot.com.gmtool.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import data.Resource;
import engine.managers.ActivityManager;
import gmtool.bitdot.com.gmtool.R;
import gmtool.bitdot.com.gmtool.gui.containers.ResourceLayout;

public class EditorActivity extends AppCompatActivity {

    private LinearLayout _layoutResources;

    private Resource[] _allResources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        _layoutResources = findViewById(R.id.layoutResources);
    }

    @Override
    protected void onStart() {
        super.onStart();
        update();
    }

    private ResourceLayout[] resourceLayouts;
    private void update() {
        if(resourceLayouts != null) {
            for(int i = 0; i < resourceLayouts.length; i ++) {
                resourceLayouts[i].dispose();
            }
        }

        resourceLayouts = null;

        Resource.GetAllResources((result) -> {
            _allResources = result;
            resourceLayouts = new ResourceLayout[result.length];
            for(int i = 0; i < result.length; i ++) {
                ResourceLayout res = new ResourceLayout();
                res.addResource(getApplicationContext(), this, _layoutResources, result[i]);
                resourceLayouts[i] = res;
            }
        });
    }

    public void addNewResource(View view) {
        ActivityManager.instance().startEditResourceActivity(this);
    }
}
