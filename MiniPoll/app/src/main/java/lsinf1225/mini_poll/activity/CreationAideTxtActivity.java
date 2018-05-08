package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import lsinf1225.mini_poll.R;

public class CreationAideTxtActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_help_txt);
    }

    public void next(View v) {

        Intent intent = new Intent(this, CreationAideTxtResumeActivity.class);
        startActivity(intent);
    }
}
