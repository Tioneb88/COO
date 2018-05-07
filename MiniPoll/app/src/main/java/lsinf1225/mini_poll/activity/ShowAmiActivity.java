package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import lsinf1225.mini_poll.MiniPollApp;
import lsinf1225.mini_poll.MySQLiteHelper;
import lsinf1225.mini_poll.R;
import lsinf1225.mini_poll.model.Ami;
import lsinf1225.mini_poll.model.User;

import java.util.ArrayList;

/**
 * Gère l'affichage de l'interface de participation à un sondage pour l'utilisateur connecté
 *
 * @author Felix et Margaux
 * @version 1
 */
public class ShowAmiActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String recept = getIntent().getStringExtra("Ami");


        setContentView(R.layout.activity_consulter_profil);

        TextView username = findViewById(R.id.username);
        username.setText(getString(R.string.app_user_username) + " : " + Ami.get_id(recept));

        TextView surname = findViewById(R.id.surname);
        surname.setText(getString(R.string.app_user_surname) + " : " + Ami.get_nom(recept));

        TextView firstname = findViewById(R.id.firstname);
        firstname.setText(getString(R.string.app_user_firstname) + " : " + Ami.get_prenom(recept));

        //TextView mdp = findViewById(R.id.textView);
        //mdp.setText(identifiant.getPassword());

        TextView mail = findViewById(R.id.mail);
        mail.setText(getString(R.string.app_user_mail) + " : " + Ami.get_mail(recept));

        TextView bff = findViewById(R.id.bff);
        mail.setText(getString(R.string.my_profile_bff) + " : " + Ami.get_bff(recept));

    }
}
