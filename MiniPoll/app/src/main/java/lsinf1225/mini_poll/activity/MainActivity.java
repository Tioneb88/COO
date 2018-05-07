package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
        welcomeTxt.setText(getString(R.string.main_activity_welcome_partie1) + " " + User.getConnectedUser().getPrenom());
    }


    /*
     * @note Les méthodes show, search, add et logout sont appelées lors d'un clic sur les boutons
     * correspondant grâce à l'attribut onClick présent dans les fichiers de layout.
     *
     * Lire http://developer.android.com/reference/android/R.attr.html#onClick
     */


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
        Intent intent = new Intent(this, ShowListActivity.class);
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
    public void answerQuestionnary(View v) {
        Intent intent = new Intent(this, ShowListQuestActivity.class);
        startActivity(intent);
    }

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
        finish();
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

    /**
     * Lance l'activité de vue des détails d'un élément de collection lors du clic sur un élément de
     * la liste.
     *
     * @param position Position de l'élément dans la liste.
     */
    /*
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ConsulterProfilActivity.class);
        // L'id de l'élément de collection est passé en argument afin que la vue de détails puisse
        // récupérer celui-ci.
        intent.putExtra("s_id", User.getConnectedUser().getId());
        startActivity(intent);
    }
    */

}
