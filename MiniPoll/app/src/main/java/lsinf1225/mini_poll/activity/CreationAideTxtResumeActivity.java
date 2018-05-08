package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import lsinf1225.mini_poll.R;

public class CreationAideTxtResumeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_help_txt_resume);
    }

    public void confirm(View v) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void modify(View v) {

        Intent intent = new Intent(this, CreationAideTxtActivity.class);
        startActivity(intent);
    }
}
