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
 * Created by margauxgerard on 30/04/18.
 */

public class ConsulterProfilUserActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_profil_user);

        Intent intent = getIntent();
        String userIdentifiant = intent.getStringExtra("userId");
        User currentUser = User.get(userIdentifiant);
        //Affichage de la photo
        String photoFile = currentUser.getPhoto();

        if(photoFile != null) {
            // Récupérer l'AssetManager
            AssetManager manager = getAssets();

            // lire un Bitmap depuis Assets
            InputStream open = null;
            try {
                open = manager.open(photoFile);
                Bitmap bitmap = BitmapFactory.decodeStream(open);
                // Assigner le bitmap à une ImageView dans cette mise en page
                ImageView view = (ImageView) findViewById(R.id.my_profile_picture_autre);
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
        }

        TextView username = findViewById(R.id.username_autre);
        username.setText(getString(R.string.app_user_username) + currentUser.getId());

        TextView surname = findViewById(R.id.surname_autre);
        surname.setText(getString(R.string.app_user_surname) + currentUser.getNom());

        TextView firstname = findViewById(R.id.firstname_autre);
        firstname.setText(getString(R.string.app_user_firstname) + currentUser.getPrenom());

        TextView mail = findViewById(R.id.mail_autre);
        mail.setText(getString(R.string.app_user_mail) + currentUser.getMail());

        //Affichage du meilleur ami s'il y en a un sinon, on met une barre (/).
        if(User.getConnectedUser().getBff() == null)
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