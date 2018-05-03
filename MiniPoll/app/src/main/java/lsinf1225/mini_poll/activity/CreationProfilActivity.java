package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class CreationProfilActivity extends Activity{

    public void create(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void photo(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
