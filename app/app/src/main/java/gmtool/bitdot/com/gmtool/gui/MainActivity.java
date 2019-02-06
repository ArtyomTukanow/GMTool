package gmtool.bitdot.com.gmtool.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import engine.managers.ActivityManager;
import engine.managers.SharedPreferencesManager;
import gmtool.bitdot.com.gmtool.R;

public class MainActivity extends AppCompatActivity {

    private TextView _textViewWelcome;

    private static boolean firstStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(firstStart) {
            firstStart = false;
            SharedPreferencesManager.initSharedPreferences(this);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _textViewWelcome = findViewById(R.id.textViewWelcome);

        refresh();
    }

    private void refresh() {
        if(SharedPreferencesManager.getUserId() <= 0) {
            ActivityManager.instance().startLoginActivity(this, "","");
            finish();
        } else {
            _textViewWelcome.setText("Добро пожаловать, " + SharedPreferencesManager.getUserName());
        }
    }


    public void lobbyClick(View view) {

    }

    public void editorClick(View view) {
        ActivityManager.instance().startEditorActivity(this);
    }

    public void logoutClick(View view) {
        SharedPreferencesManager.logoutUser();
        refresh();
    }
}
