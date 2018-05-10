package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


import lsinf1225.mini_poll.R;

import lsinf1225.mini_poll.model.User;

public class ShowListUsersActivity extends Activity implements AdapterView.OnItemClickListener {

    private ArrayList<String> usersName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_list);

        Intent intent = getIntent();
        usersName= intent.getStringArrayListExtra("usersIds");

        ListView myListView = findViewById(R.id.show_listViewUser);
        myListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,usersName));
        myListView.setOnItemClickListener(this);

    }



    @Override
    public void onResume() {
        super.onResume();
        // La liste des éléments est ici rechargées car en cas de modification d'un élément, l'ordre
        // a peut-être changé.

        Intent intent = getIntent();
        usersName = intent.getStringArrayListExtra("usersIds");

    }

    /**
     * Lance l'activité de vue des détails d'un élément de collection lors du clic sur un élément de
     * la liste.
     *
     * @param position Position de l'élément dans la liste.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent (this, ConsulterProfilUserActivity.class);
        intent.putExtra("userId",usersName.get(position));
        startActivity(intent);


    }
}
