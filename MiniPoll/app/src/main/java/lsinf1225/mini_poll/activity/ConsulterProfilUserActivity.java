package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import lsinf1225.mini_poll.R;
import lsinf1225.mini_poll.model.User;
import android.content.Intent;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

/**
 * Cette classe permet d'afficher le profil d'un utilisateur dont l'identifiant est fourni dans l'intent
 * @author Claes Arnaud
 */

public class ConsulterProfilUserActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_profil_user);

        //Recuperation de l'user
        Intent intent = getIntent();
        String userIdentifiant = intent.getStringExtra("userId");
        User currentUser = User.get(userIdentifiant);

        //Affichage de la photo
        String photoFile = currentUser.getPhoto();

        if(photoFile == null) {
            photoFile = "default.jpg";
        }
        // Récupérer l'AssetManager
        AssetManager manager = getAssets();

        // lire un Bitmap depuis Assets
        InputStream open = null;
        try {
            open = manager.open(photoFile);
            Bitmap bitmap = BitmapFactory.decodeStream(open);
            // Assigner le bitmap à une ImageView dans cette mise en page
            ImageView view = (ImageView) findViewById(R.id.profile_picture);
            view.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (open != null) {
                try {
                    open.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        TextView username = findViewById(R.id.username_autre);
        username.setText(getString(R.string.my_profile_id) + currentUser.getId());

        TextView surname = findViewById(R.id.surname_autre);
        surname.setText(getString(R.string.my_profile_nom) + currentUser.getNom());

        TextView firstname = findViewById(R.id.firstname_autre);
        firstname.setText(getString(R.string.my_profile_prenom) + currentUser.getPrenom());

        TextView mail = findViewById(R.id.mail_autre);
        mail.setText(getString(R.string.my_profile_mail) + currentUser.getMail());

        //Affichage du meilleur ami s'il y en a un sinon, on met une barre (/).
        if(currentUser.getBff() == null)
        {
            TextView bff = findViewById(R.id.bff_autre);
            bff.setText(getString(R.string.app_user_bff) + " : /");
        }
        else
        {
            TextView bff = findViewById(R.id.bff_autre);
            bff.setText(getString(R.string.app_user_bff) + " : " + currentUser.getBff());
        }
    }
}
