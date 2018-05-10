package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import lsinf1225.mini_poll.MiniPollApp;
import lsinf1225.mini_poll.R;

public class CreationAideTxtActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_help_txt);
    }

    public void next(View v) {

        // On récupère la description.
        EditText descriptionEditText = findViewById(R.id.create_help_txt_description);
        String description = descriptionEditText.getText().toString();

        // On récupère la première proposition.
        EditText proposalEditText = findViewById(R.id.create_help_txt_proposal);
        String proposal = proposalEditText.getText().toString();

        // On récupère la seconde proposition.
        EditText proposalEditText2 = findViewById(R.id.create_help_txt_proposal2);
        String proposal2 = proposalEditText2.getText().toString();

        // On récupère les informations de l'activité précédente.
        Intent prev = getIntent();
        String chosenFriend = prev.getStringExtra("chosenFriend");

        if(description.isEmpty() || proposal.isEmpty() || proposal2.isEmpty())
        {
            MiniPollApp.notifyShort(R.string.create_error);
        }
        else if(proposal.equals(proposal2))
        {
            MiniPollApp.notifyShort(R.string.create_help_proposal_error);
        }
        else
        {
            Intent intent = new Intent(this, CreationAideTxtResumeActivity.class);
            intent.putExtra("chosenFriend", chosenFriend);
            intent.putExtra("description", description);
            intent.putExtra("proposal", proposal);
            intent.putExtra("proposal2", proposal2);
            startActivity(intent);
        }
    }
}
