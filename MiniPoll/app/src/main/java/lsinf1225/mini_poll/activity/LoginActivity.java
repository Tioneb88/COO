package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import lsinf1225.mini_poll.MiniPollApp;
import lsinf1225.mini_poll.R;
import lsinf1225.mini_poll.model.User;

/**
 * Gère l'écran de connexion des utilisateurs à l'application.
 *
 * @author Margaux GERARD, Loïc QUINET, Félix DE PATOUL, Benoît MICHEL, Arnaud CLAES
 * @version 1
 * 26 avril 2018
 */
public class LoginActivity extends Activity implements TextView.OnEditorActionListener {

    //private Spinner userSpinner;
    private AutoCompleteTextView autoComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Récupérer l'AssetManager
        AssetManager manager = getAssets();

        // lire un Bitmap depuis Assets
        InputStream open = null;
        try {
            open = manager.open("logo.jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(open);
            // Assigner le bitmap à une ImageView dans cette mise en page
            ImageView view = (ImageView) findViewById(R.id.login_logo);
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

        /*
         @note : Le titre de l'activité de lancement donné dans l'AndroidManifest.xml est repris
         comme nom du lanceur de l'application par Android. Pour ce premier écran, on va donc
         utiliser la méthode setTitle afin de définir le titre de l'activité (s'il est différent du
         titre de l'application).
         */

        setTitle(R.string.login_title);

        autoComplete = findViewById(R.id.autoCompleteTextView);

        //userSpinner = findViewById(R.id.login_username);

        // Obtention de la liste des utilisateurs.
        ArrayList<User> users = User.getUtilisateurs();

        // Création d'un ArrayAdapter en utilisant la liste des utilisateurs et un layout pour le spinner existant dans Android.
        ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, users);
        // On lie l'adapter au spinner.
        //userSpinner.setAdapter(adapter);

        autoComplete.setAdapter(adapter);


        // On indique qu'il faut appeler onEditorAction de cette classe lorsqu'une action (valider ici)
        // est faite depuis le clavier lorsqu'on est en train de remplir le mot de passe.
        EditText passwordEditText = findViewById(R.id.login_password);
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
     * Vérifie le mot de passe et connecte l'utilisateur.
     * <p>
     * Cette méthode vérifie le mot de passe saisi. Si celui-ci est bon, connecte l'utilisateur et
     * affiche le menu principal, sinon un message est affiché à l'utilisateur.
     * <p>
     * Cette méthode est appelée grâce à l'attribut onClick indiqué dans le fichier xml de layout
     * sur le bouton de connexion. Elle peut également être appelée depuis la méthode
     * "onEditorAction" de cette classe.
     *
     * @param v Une vue quelconque (n'est pas utilisé ici, mais requis par le onClick)
     */
    public void login(View v) {
        // Lorsqu'on clique sur le bouton "Se connecter" on qu'on valide depuis le clavier.
        //User user = (User) userSpinner.getSelectedItem();
        String userId = autoComplete.getText().toString();
        User user = User.get(userId);
        EditText passwordEditText = findViewById(R.id.login_password);
        String password = passwordEditText.getText().toString();

        if (user.login(password)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            MiniPollApp.notifyLong(R.string.login_password_wrong);
    }
    }

    /**
     * Lance l'activité de création d'un nouveau compte.
     */
    public void newUser(View v) {
        Intent intent = new Intent(this, CreationCompteActivity.class);
        startActivity(intent);
    }

    /**
     * Affiche le message d'information pour l'utilisateur ayant oublié son mot de passe.
     */
    public void forgot(View v) {

        MiniPollApp.notifyShort(R.string.login_password_forgotten_notification);
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
            login(v);
            return true;
        }
        return false;
    }

    /**
     * Désactive le bouton de retour. Désactive le retour à l'activité précédente (donc l'écran de
     * connexion dans ce cas-ci) et affiche un message indiquant qu'il faut se déconnecter.
     */
    @Override
    public void onBackPressed() {

        //MiniPollApp.notifyShort(R.string.main_back_button_disable);
        finish();
    }
}
