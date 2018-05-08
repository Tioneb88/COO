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

public class ConsulterProfilActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_profil);

        //Affichage de la photo
        String photoFile = User.getConnectedUser().getPhoto();

        if(photoFile != null) {
            // Récupérer l'AssetManager
            AssetManager manager = getAssets();

            // lire un Bitmap depuis Assets
            InputStream open = null;
            try {
                open = manager.open(photoFile);
                Bitmap bitmap = BitmapFactory.decodeStream(open);
                // Assigner le bitmap à une ImageView dans cette mise en page
                ImageView view = (ImageView) findViewById(R.id.my_profile_picture);
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

        TextView username = findViewById(R.id.username);
        username.setText(getString(R.string.app_user_username) + " : " + User.getConnectedUser().getId());

        TextView surname = findViewById(R.id.surname);
        surname.setText(getString(R.string.app_user_surname) + " : " + User.getConnectedUser().getNom());

        TextView firstname = findViewById(R.id.firstname);
        firstname.setText(getString(R.string.app_user_firstname) + " : " + User.getConnectedUser().getPrenom());

        TextView mail = findViewById(R.id.mail);
        mail.setText(getString(R.string.app_user_mail) + " : " + User.getConnectedUser().getMail());

        //Affichage du meilleur ami s'il y en a un sinon, on met une barre (/).
        if(User.getConnectedUser().getBff() == null)
        {
            TextView bff = findViewById(R.id.bff);
            bff.setText(getString(R.string.app_user_bff) + " : /");
        }
        else
        {
            TextView bff = findViewById(R.id.bff);
            bff.setText(getString(R.string.app_user_bff) + " : " + User.getConnectedUser().getBff());
        }

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