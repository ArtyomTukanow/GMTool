package gmtool.bitdot.com.gmtool.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import data.Resource;
import engine.net.URLReader;
import engine.utils.Utils;
import gmtool.bitdot.com.gmtool.R;

public class ResourceActivity extends AppCompatActivity {

    private ImageView _imageView;
    private EditText _editTextImageUrl;
    private EditText _editTextName;
    private EditText _editTextDescription;
    private EditText _editTextMin;
    private EditText _editTextMax;
    private Button _buttonCancel;
    private Button _buttonOk;

    private Resource _currentResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource);

        _imageView = findViewById(R.id.resActivityImage);
        _editTextImageUrl = findViewById(R.id.resActivityImageUrl);
        _editTextName = findViewById(R.id.resActivityName);
        _editTextDescription = findViewById(R.id.resActivityDescription);
        _editTextMin = findViewById(R.id.resActivityMin);
        _editTextMax = findViewById(R.id.resActivityMax);
        _buttonCancel = findViewById(R.id.resActivityButtonCancel);
        _buttonOk = findViewById(R.id.resActivityButtonOk);

        int id = getIntent().getIntExtra(Resource.RESOURCE_ID, -1);
        Resource.GetResourceById(id, resource -> {
            _currentResource = (resource.isNull()) ? new Resource() : resource;
            update();
        });
    }

    private void acceptChange() {
        _currentResource.setData(Resource.NAME, _editTextName.getText());
        _currentResource.setData(Resource.DESCRIPTION, _editTextDescription.getText());
        _currentResource.setData(Resource.IMAGE, _editTextImageUrl.getText());
        _currentResource.setData(Resource.MIN_VALUE, _editTextMin.getText());
        _currentResource.setData(Resource.MAX_VALUE, _editTextMax.getText());
    }

    private void update() {
        if(_currentResource.has(Resource.RESOURCE_ID)) {
            _editTextName.setText(_currentResource.getString(Resource.NAME));
            _editTextMin.setText(_currentResource.getString(Resource.MIN_VALUE));
            _editTextMax.setText(_currentResource.getString(Resource.MAX_VALUE));

            if(_currentResource.has(Resource.DESCRIPTION))
                _editTextDescription.setText(_currentResource.getString(Resource.DESCRIPTION));

            if(_currentResource.has(Resource.IMAGE)) {
                String url = _currentResource.getString(Resource.IMAGE);
                _editTextImageUrl.setText(url);
                URLReader.getBitmapAsynh(url, image -> {
                    _imageView.setImageBitmap(image);
                });
            }
        }
    }

    public void onButtonOk(View view) {
        acceptChange();
        if(_currentResource.has(Resource.RESOURCE_ID)) {
            Resource.UpdateResource(_currentResource, result -> {
                Utils.log("Resources updated: " + result);
                finish();
            });
        } else {
            Resource.AddResource(_currentResource, result -> {
                Utils.log("Resources added: " + result);
                finish();
            });
        }
    }

    public void onButtonCancel(View view) {
        finish();
    }
}
