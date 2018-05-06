package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import lsinf1225.mini_poll.R;
import lsinf1225.mini_poll.model.User;
import android.content.Intent;
import android.view.View;
/**
 * Created by margauxgerard on 30/04/18.
 */

public class ConsulterProfilActivity extends Activity {

    // private User identifiant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_profil);

        // Complétion des différents champs avec les données.

        /*if(identifiant.getPhoto()!=null){
            ImageView photo= findViewById(R.id.imageView2);
            //Bitmap image=(Bitmap) identifiant.getPhoto();
            photo.setImage(identifiant.getPhoto());
        }*/

        TextView username = findViewById(R.id.username);
        username.setText(getString(R.string.app_user_username) + " : " + User.getConnectedUser().getId());

        TextView surname = findViewById(R.id.surname);
        surname.setText(getString(R.string.app_user_surname) + " : " + User.getConnectedUser().getNom());

        TextView firstname = findViewById(R.id.firstname);
        firstname.setText(getString(R.string.app_user_firstname) + " : " + User.getConnectedUser().getPrenom());

        //TextView mdp = findViewById(R.id.textView);
        //mdp.setText(identifiant.getPassword());

        TextView mail = findViewById(R.id.mail);
        mail.setText(getString(R.string.app_user_mail) + " : " + User.getConnectedUser().getMail());

    }

    /**
     * Lance l'activité de modification du nom d'utilisateur.
     */
    public void changeUsername(View v) {
        Intent intent = new Intent(this, ChangeUsernameActivity.class);
        startActivity(intent);
    }

    /**
     * Lance l'activité de changement de mot de passe.
     */
    public void changePassword(View v) {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }

}