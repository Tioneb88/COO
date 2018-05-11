package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import lsinf1225.mini_poll.R;
import lsinf1225.mini_poll.model.Aide;
import lsinf1225.mini_poll.model.User;

/**
 *
 * Gère l'affichage d'un résumé lors de la création d'une demande d'aide avec un format text
 */
public class CreationAideTxtResumeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_help_txt_resume);

        // Récupération du contenu de l'activité précédente.
        Intent prev = getIntent();
        String chosenFriend = prev.getStringExtra("chosenFriend");
        String chosenDescription = prev.getStringExtra("description");
        String chosenProposal = prev.getStringExtra("proposal");
        String chosenProposal2 = prev.getStringExtra("proposal2");

        // Affichage des informations.
        TextView friend = findViewById(R.id.create_help_txt_resume_friend);
        friend.setText(getString(R.string.create_help_friend_chosen) + " : " + chosenFriend);

        TextView description = findViewById(R.id.create_help_txt_resume_description);
        description.setText(getString(R.string.create_description) + " : " + chosenDescription);

        TextView proposal = findViewById(R.id.create_help_txt_resume_proposal);
        proposal.setText(getString(R.string.create_help_answers_proposal) + " : " + chosenProposal);

        TextView proposal2 = findViewById(R.id.create_help_txt_resume_proposal2);
        proposal2.setText(getString(R.string.create_help_answers_proposal2) + " : " + chosenProposal2);
    }

    public void create(View v) {
        // Récupération du contenu de l'activité précédente.
        Intent prev = getIntent();
        String chosenFriend = prev.getStringExtra("chosenFriend");
        String chosenDescription = prev.getStringExtra("description");
        String chosenProposal = prev.getStringExtra("proposal");
        String chosenProposal2 = prev.getStringExtra("proposal2");

        int error = Aide.createHelp(User.getConnectedUser().getId(), chosenDescription, chosenProposal, chosenProposal2, chosenFriend);
        if(error == -4)
        {
            Log.d("creationAide","Erreur lors de la creation de la deuxième proposition.");
        }
        else if(error == -3)
        {
            Log.d("creationAide","Erreur lors de la creation de la première proposition.");
        }
        else if(error == -2)
        {
            Log.d("creationAide","Erreur lors de la creation du participant.");
        }
        else if(error == -1)
        {
            Log.d("creationAide","Erreur lors de la creation de la demande d'aide.");
        }
        else
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void modificate(View v) {
        // Récupération du contenu de l'activité précédente.
        Intent prev = getIntent();
        String chosenFriend = prev.getStringExtra("chosenFriend");

        Intent intent = new Intent(this, CreationAideTxtActivity.class);
        intent.putExtra("chosenFriend",chosenFriend);
        startActivity(intent);
    }
}
