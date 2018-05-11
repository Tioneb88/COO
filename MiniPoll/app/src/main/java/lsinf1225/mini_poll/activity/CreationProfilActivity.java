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
 * Gère la création d'un nouveau compte utilisateur (étape 2)
 */
public class CreationProfilActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
    }

    public void create(View v) {
        // On récupère le nom
        EditText nameEditText = findViewById(R.id.create_name);
        String name = nameEditText.getText().toString();

        // On récupère le prénom
        EditText firstnameEditText = findViewById(R.id.create_firstname);
        String firstname = firstnameEditText.getText().toString();

        // On récupère l'adresse mail
        EditText mailEditText = findViewById(R.id.create_mail);
        String mail = mailEditText.getText().toString();

        // On récupère les informations de l'activité précédente.
        Intent prev = getIntent();
        String username = prev.getStringExtra("username");
        String password = prev.getStringExtra("password");

        if(name.isEmpty() || firstname.isEmpty() || mail.isEmpty())
        {
            MiniPollApp.notifyShort(R.string.create_error);
        }
        else
        {
            User.addUser(username, password, name, firstname, mail);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }


}
