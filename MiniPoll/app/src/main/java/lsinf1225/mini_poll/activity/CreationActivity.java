package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

/**
 * Created by margauxgerard on 30/04/18.
 */

public class CreationActivity extends Activity {

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
