package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import lsinf1225.mini_poll.MiniPollApp;
import lsinf1225.mini_poll.R;
import lsinf1225.mini_poll.model.User;

/**
 * Created by margauxgerard on 30/04/18.
 * modified by felixdepatoul on 30/04/18
 */

public class ModifierProfilActivity extends Activity implements TextView.OnEditorActionListener {

        private Spinner userSpinner;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_modifier_id);

        /*
         @note : Le titre de l'activité de lancement donné dans l'AndroidManifest.xml est repris
         comme nom du lanceur de l'application par Android. Pour ce premier écran, on va donc
         utiliser la méthode setTitle afin de définir le titre de l'activité (s'il est différent du
         titre de l'application).
         */
            setTitle(R.string.change_username_title);



            /*
             * @note La liste des utilisateurs est affichées dans un Spinner, pour en savoir plus lisez
             * http://d.android.com/guide/topics/ui/controls/spinner.html
             */
            userSpinner = findViewById(R.id.login_username);

            // Obtention de la liste des utilisateurs.
            ArrayList<User> users = User.getUtilisateurs();

            // Création d'un ArrayAdapter en utilisant la liste des utilisateurs et un layout pour le spinner existant dans Android.
            ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, users);
            // On lie l'adapter au spinner.
            userSpinner.setAdapter(adapter);


            // On indique qu'il faut appeler onEditorAction de cette classe lorsqu'une action (valider ici)
            // est faite depuis le clavier lorsqu'on est en train de remplir le mot de passe.
            EditText passwordEditText = findViewById(R.id.login_password);
            EditText usernameEditText = findViewById(R.id.login_username);
            EditText newpasswordEditText = findViewById(R.id.login_username);
            newpasswordEditText.setOnEditorActionListener(this);
            usernameEditText.setOnEditorActionListener(this);
            passwordEditText.setOnEditorActionListener(this);
        }

        @Override
        protected void onResume() {
            super.onResume();

            // On efface le mot de passe qui était écrit quand on se déconnecte.
            EditText passwordEditText = findViewById(R.id.login_password);
            passwordEditText.setText("");
        }

        /**
         * Vérifie le mot de passe et change l'identifiant de l'utilisateur.
         * <p>
         * Cette méthode vérifie le mot de passe saisi. Si celui-ci est bon, change l'identifiant de l'utilisateur et
         * retourne au menu principal, sinon un message est affiché à l'utilisateur.
         * <p>
         * Cette méthode est appelée grâce à l'attribut onClick indiqué dans le fichier xml de layout
         * sur le bouton changer identifiant de la consultation de profil.
         *
         * @param v Une vue quelconque (n'est pas utilisé ici, mais requis par le onClick)
         */
        public void SetUserName(View v) {
            // Lorsqu'on clique sur le bouton "Changer d'identifiant ?" on qu'on valide depuis le clavier.
            User user = (User) userSpinner.getSelectedItem();
            EditText passwordEditText = findViewById(R.id.login_password);
            String password = passwordEditText.getText().toString();

            if (user.login(password)) {
                EditText usernameEditText = findViewById(R.id.login_username);
                String username = usernameEditText.getText().toString();
                user.setId(username);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                MiniPollApp.notifyShort(R.string.login_password_wrong);
            }
        }

    /**
     * Vérifie le mot de passe et change l'identifiant de l'utilisateur.
     * <p>
     * Cette méthode vérifie le mot de passe saisi. Si celui-ci est bon, change l'identifiant de l'utilisateur et
     * retourne au menu principal, sinon un message est affiché à l'utilisateur.
     * <p>
     * Cette méthode est appelée grâce à l'attribut onClick indiqué dans le fichier xml de layout
     * sur le bouton changer identifiant de la consultation de profil.
     *
     * @param v Une vue quelconque (n'est pas utilisé ici, mais requis par le onClick)
     */
    public void SetPassword(View v) {
        // Lorsqu'on clique sur le bouton "Changer de mot de passe ?" on qu'on valide depuis le clavier.
        User user = (User) userSpinner.getSelectedItem();
        EditText passwordEditText = findViewById(R.id.login_password);
        String password = passwordEditText.getText().toString();

        if (user.login(password)) {
            EditText newpasswordEditText = findViewById(R.id.login_username);
            String newpassword = newpasswordEditText.getText().toString();
            user.setPassword(newpassword);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            MiniPollApp.notifyShort(R.string.login_password_wrong);
        }
    }

        /**
         * Récupère les actions faites depuis le clavier.
         * <p>
         * Récupère les actions faites depuis le clavier lors de l'édition du champ du mot de passe afin
         * de permettre de se connecter depuis le bouton "Terminer" du clavier. (Cela évite à
         * l'utilisateur de devoir fermer le clavier et de cliquer sur le bouton se connecter).
         */
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            // L'attribut android:imeOptions="actionNext" est défini dans le fichier xml de layout
            // (activity_login.xml), L'actionId attendue est donc IME_ACTION_NEXT.
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                SetUserName(v);
                return true;
            }
            return false;
        }






















  //  /**
  //   * change le nom d un utilisateur
   //  */
//protected void setUserName(String id){
  //   User u = User.getConnectedUser();
    // affichage de
   // if(User.getConnectedUser()!=u){}
       // TextView modifierusername = findViewById(R.id.modifierusername);
       // modifierusername.setText(getString(R.string.change_username_title) + " de " + User.getConnectedUser().getPrenom());

   //    if(u.login(passwordToTry)){

    //}

//}
   // /**
   //  * change le prenom d un utilisateur
   //  */
   // protected void setPassword(User u){

   // }
}
