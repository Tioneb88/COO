package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import lsinf1225.mini_poll.R;

/**
 * Created by margauxgerard on 30/04/18.
 */

public class CreationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
    }

    /**
     * Lance l'activité de création de sondage par accord.
     */
    public void createAgreement(View v) {
        Intent intent = new Intent(this, CreationSondageActivity.class);
        startActivity(intent);
    }

    /**
     * Lance l'activité de création de questionnaire.
     */
    public void createQuestionnary(View v) {
        Intent intent = new Intent(this, CreationQuestActivity.class);
        startActivity(intent);
    }

    /**
     * Lance l'activité de création de demande d'aide à un ami.
     */
    public void createHelp(View v) {
        Intent intent = new Intent(this, CreationAideActivity.class);
        startActivity(intent);
    }
}
