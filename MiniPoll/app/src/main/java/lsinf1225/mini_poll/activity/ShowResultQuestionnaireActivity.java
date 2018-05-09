package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import lsinf1225.mini_poll.MiniPollApp;
import lsinf1225.mini_poll.R;
import lsinf1225.mini_poll.model.Questionnaire;
import lsinf1225.mini_poll.model.Question;
import lsinf1225.mini_poll.model.User;

public class ShowResultQuestionnaireActivity extends Activity{
    /**
     * Gère l'affichage des résultats d'un sondage pour l'utilisateur connecté s'il a déjà répondu
     *
     * @author Margaux GERARD
     * @version 1
     */
        private Questionnaire currentQuest;
        private ArrayList<String> propositions;
        private ArrayList<Integer> yourScores;
        private ArrayList<Integer> totalScores;
        private int nbrePossibilites;
        private TextView[] allYou;
        private TextView[] allTotal;
        private TextView[] allPropositions;

        protected void onCreate(Bundle savedInstanceState)  {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.show_quest_result);

            int nQuest = getIntent().getIntExtra("nQuest", -1);
            currentQuest = Questionnaire.get(nQuest);

            TextView description = findViewById(R.id.questDescription);
            description.setText(currentQuest.getDescription());


            TextView option1 = findViewById(R.id.option1);
            TextView option2 = findViewById(R.id.option2);
            TextView option3 = findViewById(R.id.option3);
            TextView option4 = findViewById(R.id.option4);
            TextView option5 = findViewById(R.id.option5);
            TextView option6 = findViewById(R.id.option6);

            TextView you1 = findViewById(R.id.you1);
            TextView you2 = findViewById(R.id.you2);
            TextView you3 = findViewById(R.id.you3);
            TextView you4 = findViewById(R.id.you4);
            TextView you5 = findViewById(R.id.you5);
            TextView you6 = findViewById(R.id.you6);

            TextView total1 = findViewById(R.id.total1);
            TextView total2 = findViewById(R.id.total2);
            TextView total3 = findViewById(R.id.total3);
            TextView total4 = findViewById(R.id.total4);
            TextView total5 = findViewById(R.id.total5);
            TextView total6 = findViewById(R.id.total6);

            propositions = Question.loadPropositionsQuest(nQuest);
            yourScores = Questionnaire.loadScoresQuest(nQuest, User.getConnectedUser());
            totalScores = Questionnaire.loadScoresQuest(nQuest, null);
            nbrePossibilites = propositions.size();

            allPropositions = new TextView[] {option1, option2, option3,option4, option5, option6};
            allYou = new TextView[] {you1, you2, you3, you4, you5, you6};
            allTotal = new TextView[] {total1, total2, total3,total4, total5, total6};

            for (int i = 0; i<nbrePossibilites; i++) {
                allPropositions[i].setVisibility(View.VISIBLE);
                allPropositions[i].setText(propositions.remove(0));
                allYou[i].setVisibility(View.VISIBLE);
                Log.d("tagdebug",Integer.toString(yourScores.get(0)));
                allYou[i].setText(Integer.toString(yourScores.remove(0)));
                allTotal[i].setVisibility(View.VISIBLE);
                allTotal[i].setText(Integer.toString(totalScores.remove(0)));
            }


        }

        /**
         * Désactive le bouton de retour. Désactive le retour à l'activité précédente (donc l'écran de
         * connexion dans ce cas-ci) et affiche un message indiquant qu'il faut se déconnecter.
         */
        @Override
        public void onBackPressed() {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
