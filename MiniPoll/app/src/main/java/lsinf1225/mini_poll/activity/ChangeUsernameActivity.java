package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import lsinf1225.mini_poll.R;

public class ChangeUsernameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username);
    }

    public void confirm(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
