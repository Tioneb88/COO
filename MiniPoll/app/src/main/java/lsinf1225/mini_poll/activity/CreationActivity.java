package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by margauxgerard on 30/04/18.
 */

public class CreationActivity extends Activity implements TextView.OnEditorActionListener {

    public void next(View v) {
        Intent intent = new Intent(this, CreationActivity.class);
        startActivity(intent);
    }

    public void photo(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void create(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
