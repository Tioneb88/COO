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
import lsinf1225.mini_poll.model.Question;

import java.util.ArrayList;

/**
 * Created by margauxgerard on 7/05/18.
 */

public class ShowQuestActivity extends Activity {

    private Question current;
    private ArrayList<String> propositions;
    private int nbrePossibilites;
    private TextView[] allScores;
    private Button[] allPropositions;

    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_quest);

        int nQuest = getIntent().getIntExtra("nQuestion", -1);
        current = Question.get(nQuest);

        TextView description = findViewById(R.id.descrQuest);
        description.setText(current.getTexte());


        //TextView option1 = findViewById(R.id.descrQuest);

        Button value1 = findViewById(R.id.Bouton1);
        Button value2 = findViewById(R.id.Bouton2);
        Button value3 = findViewById(R.id.Bouton3);
        Button value4 = findViewById(R.id.Bouton4);

        //allScores = new TextView[]{description};
        allPropositions = new Button[] {value1, value2, value3, value4};

        propositions = Question.loadPropositionsQuest(nQuest);
        nbrePossibilites = propositions.size();
        for (int i=0; i<nbrePossibilites;i++){
            allPropositions[i].setText(propositions.get(i));
        }


    }
}
