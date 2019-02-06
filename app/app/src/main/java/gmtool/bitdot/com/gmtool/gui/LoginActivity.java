package gmtool.bitdot.com.gmtool.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import data.User;
import engine.managers.ActivityManager;
import engine.managers.SharedPreferencesManager;
import engine.net.ICompleteRunnable;
import gmtool.bitdot.com.gmtool.R;


public class LoginActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPassword;
    private TextView textViewOutput;

    public static final String USER_NAME_PUT_EXTRA = "userName";
    public static final String USER_PASSWORD_PUT_EXTRA = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextName = findViewById(R.id.editTextNameLogin);
        editTextPassword = findViewById(R.id.editTextPasswordLogin);
        textViewOutput = findViewById(R.id.textViewOutput);
        String name = SharedPreferencesManager.getUserName();
        String password = SharedPreferencesManager.getUserPassword();
        if(!(name.equals("") || password.equals(""))) {
            editTextName.setText(name);
            editTextPassword.setText(password);
        }
    }

    /**
     * Переходит на окно регистрации приложения, передав значения editTextName и editTextPassword
     */
    public void registerClick(View view) {
        ActivityManager.instance().startRegisterActivity(
                this,
                editTextName.getText().toString(),
                editTextPassword.getText().toString());
        finish();
    }

    public void loginClick(View view) {
        login();
    }

    private void login() {
        final String name = editTextName.getText().toString();
        final String password = editTextPassword.getText().toString();
        final LoginActivity me = this;
        User.LoginUser(name, password, new ICompleteRunnable<User>() {
            @Override
            public void onComplete(User user) {
                if(user.getString(User.NAME) == null) {
                    textViewOutput.setText("Введенный вами логин или пароль неверны");
                } else {
                    SharedPreferencesManager.loginUser(user.getInt(User.USER_ID), user.getString(User.NAME), password);
                    ActivityManager.instance().startMainActivity(me);
                    me.finish();
                }
//                else if(result.length() == 0) {
//                    textViewOutput.setText("Ошибка!");
//                }
            }
        });
    }
}