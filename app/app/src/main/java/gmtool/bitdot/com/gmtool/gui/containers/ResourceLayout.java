package gmtool.bitdot.com.gmtool.gui.containers;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import data.Resource;
import engine.managers.ActivityManager;
import gmtool.bitdot.com.gmtool.R;

public class ResourceLayout {

    private Resource _resource;
    private LinearLayout _layout;
    private AppCompatTextView _textResourceName;
    private AppCompatTextView  _textResourceDescription;
    private Button _buttonUpdateResource;
    private Activity _activity;
    private LinearLayout _parent;
//    private Button _buttonDeleteResource;

    public void addResource(Context context, Activity activity, LinearLayout parent, Resource resource) {
        _activity = activity;
        _resource = resource;
        _parent = parent;
        _layout = new LinearLayout(context);
        _layout.setOrientation(LinearLayout.HORIZONTAL);
        _layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        _textResourceName = new AppCompatTextView (context) {{
            setText(_resource.getString(Resource.NAME));
            setTextColor(context.getResources().getColor(R.color.colorBlack));
            setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 3));
        }};

        _textResourceDescription = new AppCompatTextView (context) {{
            if(_resource.has(Resource.DESCRIPTION))
                setText(_resource.getString(Resource.DESCRIPTION));
            setTextColor(context.getResources().getColor(R.color.colorBlack));
            setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 2));
        }};

//        _buttonDeleteResource = new Button(context);
//        _buttonDeleteResource.setText("Удалить");
//        _buttonDeleteResource.setOnClickListener(this::onDeleteClick);
//        _buttonDeleteResource.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 3));

        _buttonUpdateResource = new Button(context);
        _buttonUpdateResource.setText("Изменить");
        _buttonUpdateResource.setOnClickListener(this::onUpdateClick);
        _buttonUpdateResource.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 3));

        _layout.addView(_textResourceName);
        _layout.addView(_textResourceDescription);
//        _layout.addView(_buttonDeleteResource);
        _layout.addView(_buttonUpdateResource);

        _parent.addView(_layout);


        _layout.requestLayout();
        _textResourceDescription.requestLayout();
//        _buttonDeleteResource.requestLayout();
    }

    public void dispose() {
        _parent.removeView(_layout);
    }

//    private void onDeleteClick(View view) {
//        Utils.log("Delete " + _resource.getString(Resource.NAME));
//    }
    private void onUpdateClick(View view) {
        ActivityManager.instance().startEditResourceActivity(_activity, _resource);
    }
}
