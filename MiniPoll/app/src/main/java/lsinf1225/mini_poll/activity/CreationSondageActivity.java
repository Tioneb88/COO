package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import lsinf1225.mini_poll.MiniPollApp;
import lsinf1225.mini_poll.R;

/**
 * Classe qui gère la première activité de sondage permettant l'ajout d'une question, d'un nbre de choix à faire
 * ainsi que des options de réponse.
 * @author Claes Arnaud
 * @version 2
 */

public class CreationSondageActivity extends Activity {
    private EditText[] allOptions;
    private EditText description;
    private EditText nbreChoix;
    private Button[] allButtons;
    private Button[] removeButtons;
    private int optionsCount=2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_sondage);

        description = findViewById(R.id.text_description);
        nbreChoix = findViewById(R.id.nbre_choix);

        //Champs d'input pour chaque option
        EditText option1 = findViewById(R.id.option1);
        EditText option2 = findViewById(R.id.option2);
        EditText option3 = findViewById(R.id.option3);
        EditText option4 = findViewById(R.id.option4);
        EditText option5 = findViewById(R.id.option5);
        EditText option6 = findViewById(R.id.option6);

        allOptions = new EditText[]{option1, option2, option3, option4, option5, option6};

        //Boutons pour ajouter un champ input
        Button bt2 = findViewById(R.id.button2);
        Button bt3 = findViewById(R.id.button3);
        Button bt4 = findViewById(R.id.button4);
        Button bt5 = findViewById(R.id.button5);

        allButtons = new Button[] {bt2, bt3, bt4, bt5};

        //Boutons pour enlever un champ input
        Button btR1 = findViewById(R.id.buttonRemove);
        Button btR2 = findViewById(R.id.buttonRemove1);
        Button btR3 = findViewById(R.id.buttonRemove2);
        Button btR4 = findViewById(R.id.buttonRemove3);

        removeButtons = new Button[] {btR1, btR2, btR3, btR4};
    }

    //Méthodes d'interface d'apparition/disparition des champs
    public void next2 (View v) {
        allOptions[2].setVisibility(View.VISIBLE);
        allButtons[0].setVisibility(View.INVISIBLE);
        allButtons[1].setVisibility(View.VISIBLE);
        removeButtons[0].setVisibility(View.VISIBLE);
        optionsCount++;

    }
    public void next3 (View v) {
        allOptions[3].setVisibility(View.VISIBLE);
        allButtons[1].setVisibility(View.INVISIBLE);
        allButtons[2].setVisibility(View.VISIBLE);
        removeButtons[0].setVisibility(View.INVISIBLE);
        removeButtons[1].setVisibility(View.VISIBLE);
        optionsCount++;
    }
    public void next4 (View v) {
        allOptions[4].setVisibility(View.VISIBLE);
        allButtons[2].setVisibility(View.INVISIBLE);
        allButtons[3].setVisibility(View.VISIBLE);
        removeButtons[1].setVisibility(View.INVISIBLE);
        removeButtons[2].setVisibility(View.VISIBLE);
        optionsCount++;
    }
    public void next5 (View v) {
        allOptions[5].setVisibility(View.VISIBLE);
        allButtons[3].setVisibility(View.INVISIBLE);
        removeButtons[2].setVisibility(View.INVISIBLE);
        removeButtons[3].setVisibility(View.VISIBLE);
        optionsCount++;
    }

    public void previous1 (View v) {
        allOptions[2].setVisibility(View.INVISIBLE);
        allButtons[1].setVisibility(View.INVISIBLE);
        allButtons[0].setVisibility(View.VISIBLE);
        removeButtons[0].setVisibility(View.INVISIBLE);
        optionsCount--;
    }

    public void previous2 (View v) {
        removeButtons[1].setVisibility(View.INVISIBLE);
        removeButtons[0].setVisibility(View.VISIBLE);
        allButtons[2].setVisibility(View.INVISIBLE);
        allButtons[1].setVisibility(View.VISIBLE);
        allOptions[3].setVisibility(View.INVISIBLE);
        optionsCount--;

    }

    public void previous3 (View v) {
        removeButtons[2].setVisibility(View.INVISIBLE);
        removeButtons[1].setVisibility(View.VISIBLE);
        allButtons[3].setVisibility(View.INVISIBLE);
        allButtons[2].setVisibility(View.VISIBLE);
        allOptions[4].setVisibility(View.INVISIBLE);
        optionsCount--;

    }

    public void previous4 (View v) {
        removeButtons[3].setVisibility(View.INVISIBLE);
        removeButtons[2].setVisibility(View.VISIBLE);
        allButtons[3].setVisibility(View.VISIBLE);
        allOptions[5].setVisibility(View.INVISIBLE);
        optionsCount--;
    }


    /**
     * Méthode d'accès à l'activité suivante qui demande d'ajouter des amis.
     * Les paramètres actuellement enregistrés sont fournis à l'activité suivante par l'intermédiaire d'un bundle dans l'intent
     * @param v
     */
    public void toAddFriend (View v) {
        boolean incorrectValue = false;
        boolean duplicateValue = false;

        //recuperation des options
        String[] optionsSubmitted = new String[optionsCount];
        for (int i =0; i<optionsCount; i++) {
            String text = allOptions[i].getText().toString();
            if (text.equals("")) {
                incorrectValue=true;
            }
            for (int j = i+1; j<optionsCount; j++) {
                String nextText = allOptions[j].getText().toString();
                if (text.equals(nextText)) {
                    duplicateValue = true;
                }
            }
            optionsSubmitted[i] = text;
        }

        //Recuperation de la description
        String descriptionText = description.getText().toString();
        if (descriptionText.equals("")) {
            incorrectValue = true;
        }

        //Recuperation du nombre de choix à faire
        String nbreChoixText = nbreChoix.getText().toString();
        int nbreChoixNumber=0;
        if (nbreChoixText.equals("")) {
            incorrectValue = true;
        }
        else {
            nbreChoixNumber = Integer.parseInt(nbreChoixText);
        }
        if (nbreChoixNumber <= 1 || nbreChoixNumber > optionsCount) {
            incorrectValue = true;
        }

        //Validation du nombre de choix à faire
        if (incorrectValue) {
            MiniPollApp.notifyShort(R.string.create_survey_error);
        }
        else if (duplicateValue) {
            MiniPollApp.notifyShort(R.string.create_survey_error_duplicate);
        }
        else {
            Intent intent = new Intent(this, CreationSondageActivityFriends.class);
            Bundle extraBundle = new Bundle();
            extraBundle.putString("description",descriptionText);
            extraBundle.putInt("nbreChoix",nbreChoixNumber);
            extraBundle.putStringArray("options",optionsSubmitted);
            intent.putExtras(extraBundle);
            startActivity(intent);
        }
    }



}

