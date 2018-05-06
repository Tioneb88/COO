package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import lsinf1225.mini_poll.MiniPollApp;
import lsinf1225.mini_poll.R;
import lsinf1225.mini_poll.model.User;

public class ChangePasswordActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
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
        // On récupère le nouveau mot de passe
        EditText passwordEditText = findViewById(R.id.change_password);
        String newPassword = passwordEditText.getText().toString();

        // On récupère la confirmation du nouveau mot de passe
        EditText confirmationEditText = findViewById(R.id.change_password_confirm);
        String confirmationPassword = confirmationEditText.getText().toString();

        // On récupère l'ancien mot de passe
        EditText oldEditText = findViewById(R.id.change_password_old);
        String oldPassword = oldEditText.getText().toString();

        // On vérifie que le nouveau mot de passe et la confirmation sont les mêmes.
        if(!newPassword.equals(confirmationPassword)) {
            MiniPollApp.notifyShort(R.string.change_password_confirmation_error);
        }
        else {
            // On vérifie que l'ancien mot de passe est correct.
            if(!User.getConnectedUser().getPassword().equals(oldPassword))
            {
                // Le mot de passe est incorrect.
                MiniPollApp.notifyShort(R.string.change_username_wrong);
            }
            else
            {
                User.getConnectedUser().setPassword(newPassword);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
