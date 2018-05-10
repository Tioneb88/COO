package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import lsinf1225.mini_poll.R;
import lsinf1225.mini_poll.model.Sondage;
import lsinf1225.mini_poll.model.User;

public class CreationSondageActivitySummary extends Activity {

    private String description;
    private int nbreChoix;
    private String[] options;
    private ArrayList<String> participants;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_sondage_summary);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        description = extras.getString("description");
        nbreChoix = extras.getInt("nbreChoix");
        participants = extras.getStringArrayList("participants");
        options = extras.getStringArray("options");
        ListView lv = findViewById(R.id.options_summary);
        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,options));
        TextView descriptionView = findViewById(R.id.description_summary);
        descriptionView.setText(description);
        TextView nbreChoixView = findViewById(R.id.choix_summary);
        nbreChoixView.setText("Choix à faire :"+nbreChoix);




    }
    public void sendSurvey(View v){
        boolean possibiliteOk = false;
        boolean participantsOk = false;
        int nSondage = Sondage.create(nbreChoix, description);

        //DEBUG
        Sondage s = Sondage.get(nSondage);
        Log.d("creationSondage",s.getDescription());
        Log.d("creationSondage",Integer.toString(nSondage));



        if (nSondage !=0) {
            possibiliteOk = Sondage.addPossibilites(nSondage, options);
            participantsOk = Sondage.addParticipants(nSondage,participants);

            ArrayList<Sondage> sondages = Sondage.getSondagesConnected();
            for (int i = 0;i<sondages.size();i++) {
                Log.d("creationSondage","sondage "+i+" :"+sondages.get(i).getDescription());
            }
            ArrayList<String> users = Sondage.loadUsersNotAnsweredYet(nSondage);
            for (int i = 0;i<users.size();i++) {
                Log.d("creationSondage",users.get(i));
            }
        }
        if (nSondage == 0 || !possibiliteOk || !participantsOk) {
            Log.d("creationSondage","Erreur lors de la creation du sondage");
        }
        else {
            Intent intent = new Intent(this, ShowListSondageActivity.class);
            startActivity(intent);
        }

    }
}
