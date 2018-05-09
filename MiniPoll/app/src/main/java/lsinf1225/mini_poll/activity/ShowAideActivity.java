package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import lsinf1225.mini_poll.MiniPollApp;
import lsinf1225.mini_poll.R;
import lsinf1225.mini_poll.model.Aide;
import lsinf1225.mini_poll.model.Sondage;
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
    private Button[] allScores;
    private int nbrePossibilites = 2;
    private TextView[] allPropositions;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_aide);
        int nAide = getIntent().getIntExtra("nAide", -1);
        current = Aide.get(nAide);

        TextView description = findViewById(R.id.descrAide);
        description.setText(current.getDescription());

        TextView option1 = findViewById(R.id.option1);
        TextView option2 = findViewById(R.id.option2);
        Button value1 = findViewById(R.id.editText1);
        Button value2 = findViewById(R.id.editText2);

        allScores = new Button[]{value1, value2};
        allPropositions = new TextView[]{option1, option2};

        propositions = Aide.loadOptions();


        propositions = Aide.loadPropositions(nAide);
        nbrePossibilites = propositions.size();

        for (int i = 0; i<nbrePossibilites; i++) {
            allPropositions[i].setVisibility(View.VISIBLE);
            allPropositions[i].setText(propositions.remove(0));
            allScores[i].setVisibility(View.VISIBLE);
        }

        // option1.setText(propositions.get(0));
        // option2.setText(propositions.get(1));
        //allPropositions[0].setText(propositions.get(0));
        //allPropositions[1].setText(propositions.get(1));
       // allPropositions[0].setText(option1);
        //allPropositions[1].setText(option2);


    }

}

