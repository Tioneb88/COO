package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import lsinf1225.mini_poll.MiniPollApp;
import lsinf1225.mini_poll.R;
import lsinf1225.mini_poll.model.Aide;
import lsinf1225.mini_poll.model.User;
import android.widget.RatingBar;

import java.util.ArrayList;

/**
 * Created by margauxgerard on 6/05/18.
 *  Gère l'affichage de l'interface de participation à une aide pour l'utilisateur connecté
 */

public class ShowAideActivity extends Activity {

    private Aide current;
    private ArrayList<String> propositions;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_aide);
        int nAide = getIntent().getIntExtra("nAide", -1);


        TextView description = findViewById(R.id.sondageDescription);
        TextView option1 = findViewById(R.id.option1);
        TextView option2 = findViewById(R.id.option2);
        EditText value1 = findViewById(R.id.editText1);
        EditText value2 = findViewById(R.id.editText2);

        propositions = Aide.loadOptions(nAide);

        option1.setText(propositions.get(0));
        option2.setText(propositions.get(1));



    }
}
