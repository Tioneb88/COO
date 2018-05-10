package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import lsinf1225.mini_poll.MiniPollApp;
import lsinf1225.mini_poll.R;
import lsinf1225.mini_poll.model.User;

public class ChangeUsernameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_username);
    }

    /**
     * Vérifie si le mot de passe est correct, vérifie ensuite si le nouvel identifiant est
     * disponible puis procède au remplacement si possible et redirige l'utilisateur vers le
     * menu principal.
     * Si le mot de passe est incorrect ou si le nouvel identifiant n'est pas disponible, on affiche
     * un message d'erreur.
     *
     * @param v Une vue quelconque.
     */
    public void confirm(View v) {
        // On récupère le nouvel identifiant
        EditText usernameEditText = findViewById(R.id.change_username);
        String newUsername = usernameEditText.getText().toString();

        // On récupère le mot de passe
        EditText passwordEditText = findViewById(R.id.change_username_confirm);
        String password = passwordEditText.getText().toString();

        int hint = User.getConnectedUser().updateUsername(newUsername,password);
        if(hint == -2) {
            // Le mot de passe est incorrect.
            MiniPollApp.notifyShort(R.string.change_username_password_wrong);
        }
        else if(hint == -1) {
            // L'identifiant choisi est déjà utilisé par un autre utilisateur.
            MiniPollApp.notifyShort(R.string.change_username_username_error);
        }
        else if(hint == 0) {
            // Tout s'est bien passé.
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {
            // Une erreur inattendue s'est produite.
            MiniPollApp.notifyShort(R.string.app_error);
        }
    }
}