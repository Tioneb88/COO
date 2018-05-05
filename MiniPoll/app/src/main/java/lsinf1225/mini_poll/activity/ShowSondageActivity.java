package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import lsinf1225.mini_poll.MiniPollApp;
import lsinf1225.mini_poll.R;
import lsinf1225.mini_poll.model.Sondage;
import lsinf1225.mini_poll.model.User;
import android.widget.RatingBar;

import java.util.ArrayList;

/**
 * Gère l'affichage de l'interface de participation à un sondage pour l'utilisateur connecté
 *
 * @author Arnaud CLAES
 * @version 1
 */
public class ShowSondageActivity extends Activity {

    private Sondage current;
    private ArrayList<String> propositions;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_sondage);
        int nSondage = getIntent().getIntExtra("nSondage", -1);


        TextView description = findViewById(R.id.sondageDescription);
        TextView option1 = findViewById(R.id.option1);
        TextView option2 = findViewById(R.id.option2);
        TextView option3 = findViewById(R.id.option3);
        EditText value1 = findViewById(R.id.editText1);
        EditText value2 = findViewById(R.id.editText2);
        EditText value3 = findViewById(R.id.editText3);

        propositions = Sondage.loadPropositions(nSondage);

        option1.setText(propositions.get(0));
        option2.setText(propositions.get(1));
        option3.setText(propositions.get(2));



    }
}
