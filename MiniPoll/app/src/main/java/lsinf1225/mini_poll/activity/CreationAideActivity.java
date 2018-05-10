package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import lsinf1225.mini_poll.R;
import lsinf1225.mini_poll.model.User;

/**
 * Gère la création de demande d'aide.
 * @author Groupe 5
 * @version 2
 */

public class CreationAideActivity extends Activity {

    private Spinner friendSpinner;
    private Spinner formatSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_help);

        // On récupère les spinner du layout.
        friendSpinner = findViewById(R.id.create_help_friend_spinner);
        formatSpinner = findViewById(R.id.create_help_format_spinner);

        // Obtention de la liste des amis de l'utilisateur courant.
        ArrayList<String> friends = User.getConnectedUser().getFriends();

        // On crée et on remplit une liste des différents formats.
        ArrayList<String> formats = new ArrayList<String>();
        formats.add(getString(R.string.create_format_text));
        formats.add(getString(R.string.create_format_picture));

        // Création des ArrayAdapter en utilisant la liste des amis et des formats et un layout pour le spinner existant dans Android.
        ArrayAdapter<String> adapterFriend = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, friends);
        ArrayAdapter<String> adapterFormat = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, formats);

        // On lie les adapter au spinner.
        friendSpinner.setAdapter(adapterFriend);
        formatSpinner.setAdapter(adapterFormat);
    }

    public void next(View v) {

        // On récupère le format choisi par l'utilisateur.
        Spinner formatSpinner = findViewById(R.id.create_help_format_spinner);
        String chosenFormat = formatSpinner.getSelectedItem().toString();

        // On récupère l'ami choisi par l'utilisateur.
        Spinner friendSpinner = findViewById(R.id.create_help_friend_spinner);
        String chosenFriend = friendSpinner.getSelectedItem().toString();

        if(chosenFormat.equals(getString(R.string.create_format_text)))
        {
            // Format texte
            Intent intent = new Intent(this, CreationAideTxtActivity.class);
            intent.putExtra("chosenFriend", chosenFriend);
            startActivity(intent);
        }
        else
        {
            // Format image
            Intent intent = new Intent(this, CreationAidePictureActivity.class);
            intent.putExtra("chosenFriend", chosenFriend);
            startActivity(intent);
        }
    }
}
