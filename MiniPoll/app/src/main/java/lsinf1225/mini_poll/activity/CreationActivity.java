package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by margauxgerard on 30/04/18.
 */

public class CreationActivity extends Activity {

    public void aide(View v) {
        Intent intent = new Intent(this, CreationAideActivity.class);
        startActivity(intent);
    }

    public void sondage(View v) {
        Intent intent = new Intent(this, CreationSondageActivity.class);
        startActivity(intent);
    }

    public void questionnaire(View v) {
        Intent intent = new Intent(this, CreationQuestActivity.class);
        startActivity(intent);
    }
}
