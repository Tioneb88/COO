package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class CreationCompteActivity extends Activity{

    public void next(View v) {
        Intent intent = new Intent(this, CreationProfilActivity.class);
        startActivity(intent);
    }
}
