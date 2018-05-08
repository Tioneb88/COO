package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.util.Log;
import java.util.ArrayList;

import lsinf1225.mini_poll.MiniPollApp;
import lsinf1225.mini_poll.R;
import lsinf1225.mini_poll.activity.adapter.MyQuestListViewAdapter;
import lsinf1225.mini_poll.model.Questionnaire;

//import static lsinf1225.mini_poll.model.Questionnaire.loadQuestionnaires;


/**
 * Gère l'affichage des sondages auxquels doit répondre l'utilisateur connecté
 *
 * @author Margaux
 * @version 1
 */
public class ShowListQuestActivity extends Activity implements AdapterView.OnItemClickListener {

    private ArrayList<Questionnaire> questionnaires;
    private MyQuestListViewAdapter myQuestListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_list_quest);

        // Chargement des éléments à afficher dans la variable de classe songs
        loadQuestionnaires();


        ListView myListView = findViewById(R.id.show_listViewQuest);

        // Création de l'adapter pour faire la liaison entre les données (songs) et
        // l'affichage de chaque ligne de la liste.
        myQuestListViewAdapter = new MyQuestListViewAdapter(this, questionnaires);
        myListView.setAdapter(myQuestListViewAdapter);

        // Indique que le clic d'un élément de la liste doit appeler la méthode onItemClick d
        // cette classe (this).
        myListView.setOnItemClickListener(this);



    }

    /**
     * Charge la liste des éléments de collection dans la variables de classe songs.
     * <p>
     * Charge la liste des éléments de la collection de l'utilisateur connecté et si une requête de
     * recherche est passée lors du lancement de l'activité, effectue la recherche et charge la
     * liste des résultats.
     *
     * !!!!!!!!!!!!!AJOUTER METHODE SEARCHSONGS (QUERY)
     */
    private void loadQuestionnaires() {

        // Récupération de la requête de recherche.
        // Si aucune requête n'a été passée lors de la création de l'activité, searchQuery sera null.
        String searchQuery = getIntent().getStringExtra("searchQuery");

        if (searchQuery == null) {
            questionnaires = Questionnaire.getQuestConnected();
        } else {

        }

        // S'il n'y a aucun éléments dans la liste, il faut afficher un message. Ce message est différent
        // s'il y avait une requête de recherche (message du type "Aucun résultat trouvé") ou si
        // l'utilisateur vient directement du menu principal et veut tout afficher (message du type
        // "Aucun élément n'est présent dans votre collection).
        if (questionnaires.isEmpty()) {
            if (searchQuery == null) {
                MiniPollApp.notifyShort(R.string.nothing_to_show);
            } else {
                MiniPollApp.notifyShort(R.string.nothing_to_show);
            }
            // Cloture de l'activité d'affichage de la liste (car liste vide). Retour à l'écran
            // précédent.
            finish();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        // La liste des éléments est ici rechargées car en cas de modification d'un élément, l'ordre
        // a peut-être changé.

        loadQuestionnaires();

        myQuestListViewAdapter.setQuest(questionnaires);
    }

    /**
     * Lance l'activité de vue des détails d'un élément de collection lors du clic sur un élément de
     * la liste.
     *
     * @param position Position de l'élément dans la liste.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //boolean answered = Questionnaire.isAnswered(questionnaires.get(position).getNquest());

        /**
         * Si l'utilisateur a déjà répondu, il est renvoyé vers les résultats du sondage en cours
         * Sinon vers l'interface pour répondre au sondage.
         */

        Intent intent = new Intent(this, ShowQuestActivity.class);
        // L'id de l'élément de collection est passé en argument afin que la vue de détails puisse
        // récupérer celui-ci.
        intent.putExtra("nQuest", questionnaires.get(position).getNquest());
        startActivity(intent);


    }

}