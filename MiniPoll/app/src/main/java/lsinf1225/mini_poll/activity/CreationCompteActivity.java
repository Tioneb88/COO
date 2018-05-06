package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import lsinf1225.mini_poll.MiniPollApp;
import lsinf1225.mini_poll.R;
import lsinf1225.mini_poll.model.User;

public class CreationCompteActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void next(View v) {
        // On récupère l'identifiant
        EditText usernameEditText = findViewById(R.id.create_username);
        String username = usernameEditText.getText().toString();

        // On récupère le mot de passe
        EditText passwordEditText = findViewById(R.id.create_password);
        String password = passwordEditText.getText().toString();

        // On récupère la confirmation du mot de passe
        EditText confirmEditText = findViewById(R.id.create_confirm);
        String confirm = confirmEditText.getText().toString();

        // On vérifie que le mot de passe et la confirmation sont les mêmes.
        if(!password.equals(confirm)) {
            MiniPollApp.notifyShort(R.string.newAccount_notification);
        }
        else {
            // On essaie d'ajouter l'utilisateur et son mot de passe à la base de données.
            User user = new User(username,null, "John", "password", "invalidmail@false.com", null, null);
            if(!user.addUser(username, password))
            {
                MiniPollApp.notifyShort(R.string.newAccount_error);
            }
            else
            {
                Intent intent = new Intent(this, CreationProfilActivity.class);
                startActivity(intent);
            }
        }
    }
}