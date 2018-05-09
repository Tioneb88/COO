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
import lsinf1225.mini_poll.model.Questionnaire;

import java.util.ArrayList;

/**
 * Created by margauxgerard on 7/05/18.
 */

public class ShowQuestActivity extends Activity {

    private Questionnaire current;
    private ArrayList<String> propositions;
    private int nbrePossibilites;
    private Button[] allScores;
    private TextView[] allPropositions;

    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_quest);

        int nQuest = getIntent().getIntExtra("nQuest", -1);
        current = Questionnaire.get(nQuest);

        TextView description = findViewById(R.id.descrQuest);
        description.setText(current.getDescription());


        TextView option1 = findViewById(R.id.descrQuest);

        Button value1 = findViewById(R.id.Bouton1);
        Button value2 = findViewById(R.id.Bouton2);
        Button value3 = findViewById(R.id.Bouton3);
        Button value4 = findViewById(R.id.Bouton4);

        allScores = new Button[]{value1, value2, value3, value4};
        allPropositions = new TextView[] {option1};

        propositions = Questionnaire.loadPropositionsQuest(nQuest);
        nbrePossibilites = propositions.size();

        for (int i = 0; i<nbrePossibilites; i++) {
            allPropositions[i].setText(propositions.remove(0));
        }

    }
}
