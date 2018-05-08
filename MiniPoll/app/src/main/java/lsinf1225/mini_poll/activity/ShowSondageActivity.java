package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import lsinf1225.mini_poll.MiniPollApp;
import lsinf1225.mini_poll.MySQLiteHelper;
import lsinf1225.mini_poll.R;
import lsinf1225.mini_poll.model.Score;
import lsinf1225.mini_poll.model.Sondage;

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
    private int nbrePossibilites;
    private EditText[] allScores;
    private TextView[] allPropositions;

    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_sondage);

        int nSondage = getIntent().getIntExtra("nSondage", -1);
        current = Sondage.get(nSondage);

        TextView description = findViewById(R.id.sondageDescription);
        description.setText(current.getDescription());


        TextView option1 = findViewById(R.id.option1);
        TextView option2 = findViewById(R.id.option2);
        TextView option3 = findViewById(R.id.option3);
        TextView option4 = findViewById(R.id.option4);
        TextView option5 = findViewById(R.id.option5);
        TextView option6 = findViewById(R.id.option6);

        EditText value1 = findViewById(R.id.editText1);
        EditText value2 = findViewById(R.id.editText2);
        EditText value3 = findViewById(R.id.editText3);
        EditText value4 = findViewById(R.id.editText4);
        EditText value5 = findViewById(R.id.editText5);
        EditText value6 = findViewById(R.id.editText6);

        allScores = new EditText[]{value1, value2, value3, value4, value5, value6};
        allPropositions = new TextView[] {option1, option2, option3,option4, option5, option6};

        propositions = Sondage.loadPropositions(nSondage);
        nbrePossibilites = propositions.size();

        for (int i = 0; i<nbrePossibilites; i++) {
            allPropositions[i].setVisibility(View.VISIBLE);
            allPropositions[i].setText(propositions.remove(0));
            allScores[i].setVisibility(View.VISIBLE);
        }



    }
    public void saveScores (View v) {

        boolean incorrectValue = false;
        if (incorrectValue) {
            MiniPollApp.notifyShort(R.string.surveys_manage_invalid_value);
        }

        else {
         int[] computedScores = new int[allScores.length];

         //Verification des valeurs et calcul des scores
         for (int i = 0; i<nbrePossibilites; i++){
         int value = Integer.parseInt(allScores[i].getText().toString());
         if (value > nbrePossibilites) {
         incorrectValue = true;
         }
         int score = (nbrePossibilites+1)-value;
         computedScores[i] = score;
         }

         ArrayList<Integer> nPropositions = Sondage.loadNumPropositions(current.getNsondage());

         Score.create(nPropositions.get(3),computedScores[3]);
         Intent intent = new Intent(this, ShowResultSondageActivity.class);
            // L'id de l'élément de collection est passé en argument afin que la vue de détails puisse
            // récupérer celui-ci.
            intent.putExtra("nSondage", current.getNsondage());
            startActivity(intent);
        }


    }
}
