package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.ImageView;
import android.graphics.Bitmap;

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

    private User identifiant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_profil);

        // Récupération de l'id du morceau de musique ou si rien n'est trouvé, -1 est la valeur
        // par défaut.
        // Lire http://d.android.com/training/basics/firstapp/starting-activity.html#ReceiveIntent
        String id = getIntent().getStringExtra("id");

        // Récupération du morceau de musique
        identifiant = User.get(id);

        // Complétition des différents champs avec les donnée.

        /*if(identifiant.getPhoto()!=null){
            ImageView photo= findViewById(R.id.imageView2);
            //Bitmap image=(Bitmap) identifiant.getPhoto();
            photo.setImage(identifiant.getPhoto());
        }
        else{

        }*/
        TextView title = findViewById(R.id.my_profile_id);
        title.setText("coucou");

        /*TextView nom = findViewById(R.id.my_profile_nom);
        nom.setText(identifiant.getNom());

        TextView prenom = findViewById(R.id.my_profile_prenom);
        prenom.setText(identifiant.getPrenom());

        //TextView mdp = findViewById(R.id.textView);
        //mdp.setText(identifiant.getPassword());

        TextView mail = findViewById(R.id.my_profile_mail);
        mail.setText(identifiant.getMail());
*/
    }

}
