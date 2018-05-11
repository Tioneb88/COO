package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import lsinf1225.mini_poll.MiniPollApp;
import lsinf1225.mini_poll.R;
import lsinf1225.mini_poll.model.User;

/**
 * Gère l'affichage du menu principal de l'application.
 *
 * @author Margaux GERARD, Loïc QUINET, Félix DE PATOUL, Benoît MICHEL, Arnaud CLAES
 * @version 2
 * 1 mai 2018
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Affichage du message de bienvenue.
        TextView welcomeTxt = findViewById(R.id.welcomeTxt);
        welcomeTxt.setText(getString(R.string.main_activity_welcome_partie1) + " " + User.getConnectedUser().getId() + " " + getString(R.string.main_activity_welcome_partie2));

        //Affichage de la photo
        String photoFile = User.getConnectedUser().getPhoto();

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
            ImageView view = (ImageView) findViewById(R.id.main_picture);
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



    //=====================================================
    // Méthodes d'accès aux différentes activités du menu
    //=====================================================

    /**
     * Lance l'activité de consultation et de modification du profil.
     */
    public void seeProfile(View v) {
        Intent intent = new Intent(this, ConsulterProfilActivity.class);
        // L'id de l'élément de collection est passé en argument afin que la vue de détails puisse
        // récupérer celui-ci.
        intent.putExtra("s_id", User.getConnectedUser().getId());
        startActivity(intent);
    }

    /**
     * Lance l'activité de consultation et de modification de la liste d'amis.
     */
    public void seeFriends(View v) {
        Intent intent = new Intent(this, ShowListUsersActivity.class);
        ArrayList<String> friends = User.getConnectedUser().getFriends();
        intent.putExtra("usersIds",friends);
        startActivity(intent);
    }

    /**
     * Lance l'activité de réponse aux sondages par accord.
     */
    public void answerAgreement(View v) {
        Intent intent = new Intent(this, ShowListSondageActivity.class);
        startActivity(intent);
    }

    /**
     * Lance l'activité de réponse aux questionnaires.
     */
    /**
    public void answerQuestionnary(View v) {
        Intent intent = new Intent(this, ShowListQuestActivity.class);
        startActivity(intent);
    }
     */

    /**
     * Lance l'activité de réponse aux demandes d'aide.
     */

    public void answerHelp(View v) {
        Intent intent = new Intent(this, ShowListAideActivity.class);
        startActivity(intent);
    }


    /**
     * Lance l'activité de création de sondage.
     */
    public void create_poll(View v) {
        Intent intent = new Intent(this, CreationActivity.class);
        startActivity(intent);
    }

    /**
     * Déconnecte l'utilisateur actuellement connecté et retourne vers l'écran de connexion.
     */
    public void logout(View v) {
        User.logout();
        //Intent intent = new Intent(this, LoginActivity.class);
        //startActivity(intent);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /**
     * Désactive le bouton de retour. Désactive le retour à l'activité précédente (donc l'écran de
     * connexion dans ce cas-ci) et affiche un message indiquant qu'il faut se déconnecter.
     */
    @Override
    public void onBackPressed() {
        // On désactive le retour (car on se trouve au menu principal) en ne faisant
        // rien dans cette méthode si ce n'est afficher un message à l'utilisateur.
        MiniPollApp.notifyShort(R.string.main_back_button_disable);
    }

}
