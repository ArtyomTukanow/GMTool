package gmtool.bitdot.com.gmtool.gui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import data.User;
import engine.managers.ActivityManager;
import engine.managers.LocalStorageManager;
import engine.managers.SharedPreferencesManager;
import engine.net.ICompleteRunnable;
import engine.utils.Utils;
import gmtool.bitdot.com.gmtool.R;

import static engine.managers.LocalStorageManager.READ_REQUEST_CODE;

public class RegisterActivity extends AppCompatActivity {

    private EditText _editTextName;
    private EditText _editTextPassword;
    private TextView _textViewOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        _editTextName = findViewById(R.id.editTextNameRegister);
        _editTextPassword = findViewById(R.id.editTextPasswordRegister);
        _textViewOutput = findViewById(R.id.textViewOutput);
        //get data from login activity
        _editTextName.setText(this.getIntent().getStringExtra(USER_NAME_PUT_EXTRA));
        _editTextPassword.setText(this.getIntent().getStringExtra(USER_PASSWORD_PUT_EXTRA));
    }

    public static final String USER_NAME_PUT_EXTRA = "userName";
    public static final String USER_PASSWORD_PUT_EXTRA = "password";

    public void loginClick(View view) {
        ActivityManager.instance().startLoginActivity(
                this,
                _editTextName.getText().toString(),
                _editTextPassword.getText().toString());
        finish();
    }

    public void registerClick(View view) {
        final String name = _editTextName.getText().toString();
        final String password = _editTextPassword.getText().toString();
        String avatar = "";
        User.AddUser(name, password, avatar, new ICompleteRunnable<User>() {
            @Override
            public void onComplete(User result) {
                if(result.getString(User.NAME) == null) {//пришел пустой результат
                    //проверяем есть ли такой пользователь
                    User.GetUserByName(name, new ICompleteRunnable<User>() {
                        @Override
                        public void onComplete(User user) {
                            if(user.getString(User.NAME) == null)
                                _textViewOutput.setText("Ошибка!");
                            else
                                _textViewOutput.setText("Пользователь с таким ником уже существует");
                        }
                    });
                } else {
                    addUserToStorage(name, password);
                }
            }
        });
    }

    /**
     * После успешного входа обновляем данные о юзере
     */
    private void addUserToStorage(String name, String password) {
        final String __password = password;
        final RegisterActivity me = this;
        User.GetUserByName(name, new ICompleteRunnable<User>() {
            @Override
            public void onComplete(User user) {
                SharedPreferencesManager.loginUser(user.getInt(User.USER_ID), user.getString(User.NAME), __password);
                ActivityManager.instance().startMainActivity(me);
                me.finish();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                Utils.log("Uri: " + uri.toString());
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                } catch (Exception e) {
                    Utils.log("Bitmap " + uri + " not found!");
                }
                ImageView avatar = findViewById(R.id.avatarImageView);
                avatar.setImageBitmap(bitmap);
            }
        }
    }


    public void chooseAvatarClick(View view) {
        LocalStorageManager.performFileSearch(this);
    }
}
