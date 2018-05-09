package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lsinf1225.mini_poll.R;
import lsinf1225.mini_poll.model.User;

public class CreationSondageActivityFriends extends Activity {

    private ListView lv;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_sondage_friends);
        List<String> friends = User.getConnectedUser().getFriends();
        String[] friendsArray = new String[friends.size()];
        friendsArray = friends.toArray(friendsArray);
        lv = findViewById(R.id.show_listViewFriends);
        lv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,friendsArray));
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    public void toSummary(View v) {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        SparseBooleanArray checked = lv.getCheckedItemPositions();
        ArrayList<String> usersSelected = new ArrayList<String>();
        for (int i = 0; i<checked.size(); i++) {
            if (checked.valueAt(i)==true) {
                String user = ((TextView)lv.getChildAt(i)).getText().toString();
                usersSelected.add(user);
            }
        }

        extras.putStringArrayList("participants",usersSelected);

        Intent nextIntent = new Intent(this, CreationSondageActivitySummary.class);
        nextIntent.putExtras(extras);
        startActivity(nextIntent);

    }
}
