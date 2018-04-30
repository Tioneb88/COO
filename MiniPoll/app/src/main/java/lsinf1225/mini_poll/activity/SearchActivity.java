package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.SearchView;

import lsinf1225.mini_poll.R;
import lsinf1225.mini_poll.activity.ShowListActivity;

/**
 * Gère l'affichage de la fonction de recherche d'éléments.
 *
 * @author Margaux GERARD, Loïc QUINET, Félix DE PATOUL, Benoît MICHEL, Arnaud CLAES
 * @version 1
 * 30 avril 2018
 */
public class SearchActivity extends Activity implements SearchView.OnQueryTextListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        /*
         * Lorsqu'une requête est soumise, on ouvre l'activité d'affichage de la liste qui affichera
         * les résultats. (voir ShowListActivity.java pour la suite de la recherche).
         */
        Intent intent = new Intent(this, ShowListActivity.class);
        intent.putExtra("searchQuery", query);
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        return false;
    }
}
