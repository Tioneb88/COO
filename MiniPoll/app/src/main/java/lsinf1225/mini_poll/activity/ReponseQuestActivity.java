package lsinf1225.mini_poll.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.app.Activity;


import java.util.ArrayList;

import lsinf1225.mini_poll.MySQLiteHelper;
import lsinf1225.mini_poll.R;
import lsinf1225.mini_poll.model.Question;
import lsinf1225.mini_poll.model.ReponseQuest;
import lsinf1225.mini_poll.model.User;
import lsinf1225.mini_poll.model.Questionnaire;


/**
 * Classe non utilisée dans l'apk.
 * Classe permettant la création d'une réponse à un questionnaire
 * Created by margauxgerard on 30/04/18.
 */

public class ReponseQuestActivity extends Activity{

    boolean enregistrer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list_question);

        //Recupère les valeur passéee en arguments:
        // On récupère les informations de l'activité précédente.
        Intent prev = getIntent();
        int nquestions = prev.getIntExtra("nquestions",-1);
        int noptions = prev.getIntExtra("nOptions", -1);
        int nclique= prev.getIntExtra("nclique",0);

        //Recupère les argument passés avec un Bundle

        //Intent intent1 = getIntent();
        //Bundle extras1 = intent1.getExtras();
        //int nquestions = extras1.getInt("nquestions");

        //Intent intent2 = getIntent();
        //Bundle extras2 = intent2.getExtras();
        //int noptions = extras2.getInt("noptions");

        //Intent intent3 = getIntent();
        //Bundle extras3 = intent3.getExtras();
        //int nclique = extras3.getInt("nclique");


        // Met l'options choisie dans le base de donnée

        enregistrer=ReponseQuest.mettredansbd(nquestions,noptions);

        if(nclique<=5){
            Intent intent = new Intent(this, ShowListQuestionActivity.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, ShowResultQuestionnaireActivity.class);
            startActivity(intent);
        }
    }


}
