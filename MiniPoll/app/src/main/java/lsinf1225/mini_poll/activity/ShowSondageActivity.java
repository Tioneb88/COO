package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import lsinf1225.mini_poll.MiniPollApp;
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

        //labels des options
        TextView option1 = findViewById(R.id.option_sond1);
        TextView option2 = findViewById(R.id.option_sond2);
        TextView option3 = findViewById(R.id.option_sond3);
        TextView option4 = findViewById(R.id.option_sond4);
        TextView option5 = findViewById(R.id.option_sond5);
        TextView option6 = findViewById(R.id.option_sond6);

        //Champs d'input
        EditText value1 = findViewById(R.id.editText1);
        EditText value2 = findViewById(R.id.editText2);
        EditText value3 = findViewById(R.id.editText3);
        EditText value4 = findViewById(R.id.editText4);
        EditText value5 = findViewById(R.id.editText5);
        EditText value6 = findViewById(R.id.editText6);

        allScores = new EditText[]{value1, value2, value3, value4, value5, value6};
        allPropositions = new TextView[] {option1, option2, option3,option4, option5, option6};

        current.loadPropositions();
        propositions = current.getPossibilites();
        nbrePossibilites = propositions.size();

        for (int i = 0; i<nbrePossibilites; i++) {
            allPropositions[i].setVisibility(View.VISIBLE);
            allPropositions[i].setText(propositions.remove(0));
            allScores[i].setVisibility(View.VISIBLE);
        }

        TextView consigne = findViewById(R.id.consigne);
        consigne.setText("Réalisez un top "+current.getNbreChoix()+" de vos options préférées");



    }

    /**
     * Méthode de création au sein de la BDD des propositions soumises par l'utilisateur pour le sondage courant
     * @param v
     */
    public void saveScores (View v) {

        boolean incorrectValue = false;
        boolean tooFewScores = false;
        int answersCount = nbrePossibilites;
        ArrayList<Integer> computedScores = new ArrayList<Integer>();

        //Verification des valeurs et calcul des scores
        for (int i = 0; i < nbrePossibilites && ( incorrectValue == false || tooFewScores == false); i++) {
            String text = allScores[i].getText().toString();
            //si valeur null, une reponse qui n'a pas été donnée
            if (text.equals("")) {
                answersCount--;
                computedScores.add(0);
                if (answersCount < current.getNbreChoix()) {
                    tooFewScores = true;
                }
            } else {
                int value = Integer.parseInt(allScores[i].getText().toString());
                if (value > nbrePossibilites) {
                    incorrectValue = true;
                }
                int score = (current.getNbreChoix() + 1) - value;
                computedScores.add(score);
            }

        }

        //Vérification des doublons
        boolean doublon = false;
        for (int i = 0; i<computedScores.size()-1 && !doublon;i++) {
            int score = computedScores.get(i);
            for (int j = i+1; j<computedScores.size() && !doublon; j++) {
                int currentScore = computedScores.get(j);
                if (score == currentScore && score != 0) {
                    doublon = true;
                }
            }
        }



        if (answersCount > current.getNbreChoix()) {
            MiniPollApp.notifyLong(R.string.surveys_manage_too_much_scores);
        }
        else if (tooFewScores) {
            MiniPollApp.notifyLong(R.string.surveys_manage_too_few_scores);
        }

        else if (incorrectValue) {
            MiniPollApp.notifyLong(R.string.surveys_manage_invalid_value);
        }
        else if (doublon) {
            MiniPollApp.notifyLong(R.string.surveys_manage_doublons);
        }

        else {
            ArrayList<Integer> nPropositions = Sondage.loadNumPropositions(current.getNsondage());

            for (int i = 0; i <nbrePossibilites; i++) {
                boolean result = Score.create(nPropositions.get(i),computedScores.get(i));
            }

            Intent intent = new Intent(this, ShowResultSondageActivity.class);
            // L'id de l'élément de collection est passé en argument afin que la vue de détails puisse
            // récupérer celui-ci.
            intent.putExtra("nSondage", current.getNsondage());
            startActivity(intent);

        }

    }
}
