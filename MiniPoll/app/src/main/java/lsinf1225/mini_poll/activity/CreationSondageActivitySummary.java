package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import lsinf1225.mini_poll.R;

public class CreationSondageActivitySummary extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_sondage_summary);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String description = extras.getString("description");

        TextView descriptionView = findViewById(R.id.description_summary);
        descriptionView.setText(description);

    }
}
