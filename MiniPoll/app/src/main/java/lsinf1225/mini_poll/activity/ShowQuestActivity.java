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
import android.widget.AdapterView;

import lsinf1225.mini_poll.MiniPollApp;
import lsinf1225.mini_poll.MySQLiteHelper;
import lsinf1225.mini_poll.R;
import lsinf1225.mini_poll.model.Option;
import lsinf1225.mini_poll.model.Question;
import lsinf1225.mini_poll.model.ReponseQuest;
import lsinf1225.mini_poll.model.User;

import java.util.ArrayList;

/**
 * Created by margauxgerard on 7/05/18.
 */

public class ShowQuestActivity extends Activity implements AdapterView.OnItemClickListener {

    private Question current;
    private ArrayList<String> propositions;
    private int nbrePossibilites;
    private TextView[] allScores;
    private Button[] allPropositions;
    private int nclique=0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_quest);

        int nQuest = getIntent().getIntExtra("nQuestion", -1);
        current = Question.get(nQuest);

        TextView description = findViewById(R.id.descrQuest);
        description.setText(current.get_descr(nQuest));


        //TextView option1 = findViewById(R.id.descrQuest);

        Button value1 = findViewById(R.id.Bouton1);
        Button value2 = findViewById(R.id.Bouton2);
        Button value3 = findViewById(R.id.Bouton3);
        Button value4 = findViewById(R.id.Bouton4);

        //allScores = new TextView[]{description};
        allPropositions = new Button[]{value1, value2, value3, value4};
        propositions = Question.loadPropositionsQuest(nQuest);
        for (int i = 0; i < 4; i++) {
            allPropositions[i].setText(propositions.get(i));
        }
    }

        //==========================================================================
        //Remarque Arnaud: cela m'empêchait de compiler donc j'ai mis en commentaires
        //==========================================================================
        /*
        propositions = Question.loadPropositionsQuest(Option.get(noptions));
>>>>>>> b7e3c3ff0f162a21ca84f7705deff85eb7b0fd4c
        //nbrePossibilites = propositions.size();
        for (int i=0; i<4;i++){
            allPropositions[i].setText(propositions.get(i));
        }
        */


    /**
     * Lance l'activité de réponse s'il y a un click
     *
     * @param position Position de l'élément dans la liste.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        boolean answered = ReponseQuest.isAnswered(User.getConnectedUser().getId(),Question.get(position).getNquestions());

        /**
         * Si l'utilisateur a déjà répondu, il est renvoyé vers les résultats du sondage en cours
         * Sinon vers l'interface pour répondre au sondage.
         */

        if (!answered) {
            Intent intent = new Intent(this, ReponseQuestActivity.class);
            // L'id de l'élément de collection est passé en argument afin que la vue de détails puisse
            // récupérer celui-ci.
            intent.putExtra("nquestions", Question.get(position).getNquestions());
            intent.putExtra("noptions",Option.get(position).getNoptions());
            intent.putExtra("nclique",nclique);
            startActivity(intent);
        }

        else {
            MiniPollApp.notifyLong(R.string.deja_repondu_a_cette_question);
        }

    }
}
