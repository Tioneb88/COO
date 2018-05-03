package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import lsinf1225.mini_poll.MiniPollApp;
import lsinf1225.mini_poll.R;
import lsinf1225.mini_poll.model.User;
import android.util.Log;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
/**
 * Created by margauxgerard on 30/04/18.
 */

public class ConsulterProfilActivity extends Activity {

    private String identifiant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_profil);

        // Récupération de l'id du morceau de musique ou si rien n'est trouvé, -1 est la valeur
        // par défaut.
        // Lire http://d.android.com/training/basics/firstapp/starting-activity.html#ReceiveIntent
        String id = getIntent().getStringExtra("id");

        // Récupération du morceau de musique
        identifiant = id;

        // Complétition des différents champs avec les donnée.
        TextView title = findViewById(R.id.textView);
        title.setText(identifiant);

    }

}
