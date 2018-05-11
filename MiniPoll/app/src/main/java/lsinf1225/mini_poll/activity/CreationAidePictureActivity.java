package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import lsinf1225.mini_poll.R;

/**
 * Pas utilisé dans l'.apk
 * Gère la création d'une demande d'aide qui a spécifié un format de type Image
 */

public class CreationAidePictureActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_help_picture);
    }

    public void next(View v) {

        Intent intent = new Intent(this, CreationAidePictureResumeActivity.class);
        startActivity(intent);
    }
}
