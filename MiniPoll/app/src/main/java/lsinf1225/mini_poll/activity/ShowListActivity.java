package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import lsinf1225.mini_poll.MiniPollApp;
import lsinf1225.mini_poll.R;
import lsinf1225.mini_poll.activity.adapter.MyListViewAdapter;
import lsinf1225.mini_poll.model.User;

/**
 * Gère l'affichage sous forme de liste des éléments de la collection de l'utilisateur en cours. Si
 * une requête de recherche est passée dans l'Intent, la recherche est effectuée et la liste des
 * éléments affichés sera la liste des résultats.
 *
 * @author Damien Mercier
 * @version 1
 */
public class ShowListActivity extends Activity implements OnItemClickListener {

    private ArrayList<User> users;
    private MyListViewAdapter myListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_list);

        // Chargement des éléments à afficher dans la variable de classe
        //loadSongs();


        ListView myListView = findViewById(R.id.show_listView);

        // Création de l'adapter pour faire la liaison entre les données et
        // l'affichage de chaque ligne de la liste.
        myListViewAdapter = new MyListViewAdapter(this, users);
        myListView.setAdapter(myListViewAdapter);

        // Indique que le clic d'un élément de la liste doit appeler la méthode onItemClick d
        // cette classe (this).
        myListView.setOnItemClickListener(this);


        // Mise à jour des icones de tri afin de correspondre au tri actuel. (les options de tri
        // sont gardées en mémoire dans la classe Song tout au long de l'exécution de
        // l'application)
        updateDrawableOrder();
    }

    /**
     * Charge la liste des éléments de collection dans la variables de classe songs.
     * <p>
     * Charge la liste des éléments de la collection de l'utilisateur connecté et si une requête de
     * recherche est passée lors du lancement de l'activité, effectue la recherche et charge la
     * liste des résultats.
     *
    private void loadSongs() {

        // Récupération de la requête de recherche.
        // Si aucune requête n'a été passée lors de la création de l'activité, searchQuery sera null.
        String searchQuery = getIntent().getStringExtra("searchQuery");

        if (searchQuery == null) {
            users = User.getId();
        } else {
            users = User.searchUser(searchQuery); // A ajouter dans User
        }

        // S'il n'y a aucun éléments dans la liste, il faut afficher un message. Ce message est différent
        // s'il y avait une requête de recherche (message du type "Aucun résultat trouvé") ou si
        // l'utilisateur vient directement du menu principal et veut tout afficher (message du type
        // "Aucun élément n'est présent dans votre collection).
        if (users.isEmpty()) {
            if (searchQuery == null) {
                MiniPollApp.notifyShort(R.string.friends_error_no_item);
            } else {
                MiniPollApp.notifyShort(R.string.friends_no_result);
            }
            // Cloture de l'activité d'affichage de la liste (car liste vide). Retour à l'écran
            // précédent.
            finish();
        }

    }*/

    @Override
    public void onResume() {
        super.onResume();
        // La liste des éléments est ici rechargées car en cas de modification d'un élément, l'ordre
        // a peut-être changé.

        //loadSongs();

        myListViewAdapter.setSongs(users);
    }

    /**
     * Lance l'activité de vue des détails d'un élément de collection lors du clic sur un élément de
     * la liste.
     *
     * @param position Position de l'élément dans la liste.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ShowDetailsActivity.class);
        // L'id de l'élément de collection est passé en argument afin que la vue de détails puisse
        // récupérer celui-ci.
        intent.putExtra("s_id", users.get(position).getId());
        startActivity(intent);
    }

    /**
     * Gère le changement du tri sur la liste.
     * <p>
     * Cette méthode est appelée grâce à l'arttribut onClick présent dans le fichier xml de layout.
     *
     * @param view Vue sur laquelle l'utilisateur a cliqué.
     */
    public void change_order(View view) {
        // Détermine si le clic a été fait sur la colonne de nom (name) ou de note (rating).
        switch (view.getId()) {
            case R.id.show_list_name:
                if (User.order_by.equals(User.COL_NOM)) {
                    // Si le tri est déjà effectué sur les noms, il faut juste inverser l'ordre.
                    User.reverseOrder();
                } else {
                    // Sinon il faut indiquer que le tri se fait sur les noms par ordre alphabétique (croissant)
                    User.order_by = User.COL_NOM;
                    User.order = "ASC";
                }
                break;
            case R.id.show_list_id:
                if (User.order_by.equals(User.COL_ID)) {
                    // Si le tri est déjà effectué sur les notes, il faut juste inverser l'ordre
                    User.reverseOrder();
                } else {
                    // Sinon il faut indiquer que le tri se fait sur les notes par ordre décroissant
                    // (la meilleure note d'abord)
                    User.order_by = User.COL_ID;
                    User.order = "DESC";
                }
                break;
        }

        // Mise à jour des icônes de tri.
        updateDrawableOrder();

        // Re-chargement de la liste des éléments de collection pour prendre en compte le nouveau tri.
       // loadSongs();

        // Mise à jour de la liste des éléments dans l'adapter pour que l'affichage soit modifié.
        myListViewAdapter.setSongs(users);
    }


    /**
     * Met à jour les icônes de tri afin qu'elles correspondent au tri actuellement en cours.
     *
     * @pre Les valeurs de Song.order et de Song.order_by sont correctement
     * définies.
     * @post Les icônes de tri sont mises à jour et correspondent aux valeurs de Song.order
     * et de Song.order_by.
     */
    private void updateDrawableOrder() {
        TextView ratingTitle = findViewById(R.id.show_list_id);
        TextView nameTitle = findViewById(R.id.show_list_name);

        /*
         * Remise à zéro des images de tri.
         * @note : Attention, le tri par défaut pour les noms est croissant
         * (up) et celui pour les notes est décroissant (down). Il faut que cela correspondent dans
         * le comportement de la méthode change_order.
         */
        nameTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_up_inactive, 0, 0, 0);
        ratingTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_down_inactive, 0, 0, 0);


        // Détermination de la colonne sur laquelle le tri est effectué.
        TextView orderTitle;
        boolean orderByRating = User.order_by.equals(User.COL_ID);
        if (orderByRating) {
            orderTitle = ratingTitle;
        } else {
            orderTitle = nameTitle;
        }

        // Détermination de l'ordre de tri.
        boolean orderDesc = User.order.equals("DESC");

        // Placement de l'icône en fonction de l'ordre de tri.
        if (orderDesc) {
            orderTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_down_active, 0, 0, 0);
        } else {
            orderTitle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_up_active, 0, 0, 0);
        }
    }

}
