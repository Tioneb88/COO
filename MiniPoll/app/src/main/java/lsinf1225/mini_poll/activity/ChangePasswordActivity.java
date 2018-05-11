package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import lsinf1225.mini_poll.MiniPollApp;
import lsinf1225.mini_poll.R;
import lsinf1225.mini_poll.model.User;

/**
 * Classe permettant le changement du mot de passe
 */


public class ChangePasswordActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
    }

    /**
     * Vérifie si le nouveau mot de passe et la confirmation sont identiques sinon, un message d'erreur
     * s'affiche.
     * Ensuite, procède au remplacement de l'ancien mot de passe par le nouveau si l'ancien mot de
     * passe est correct (par l'intermédiaire de la fonction setPassword).
     *
     * Si l'ancien mot de passe est incorrect, on affiche un message d'erreur.
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
            int hint = User.getConnectedUser().updatePassword(oldPassword,newPassword);
            if(hint == -1) {
                // L'ancien mot de passe est incorrect.
                MiniPollApp.notifyShort(R.string.change_username_password_wrong);
            }
            else if(hint == 0) {
                // Tout s'est bien passé.
                finish();
            }
            else {
                // Une erreur inattendue s'est produite.
                MiniPollApp.notifyShort(R.string.app_error);
            }
        }
    }
}
