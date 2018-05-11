package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import lsinf1225.mini_poll.R;

/**
 * Pas utilisé dans l'.apk
 * Gère l'affichage d'un résumé lors de la création d'une demande d'aide avec un format image
 */
public class CreationAidePictureResumeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_help_picture_resume);
    }

    public void confirm(View v) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void modify(View v) {

        Intent intent = new Intent(this, CreationAidePictureActivity.class);
        startActivity(intent);
    }
}
