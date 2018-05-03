package lsinf1225.mini_poll.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

/**
 * Created by margauxgerard on 30/04/18.
 */



/**
 * Gère la création de sondage ainsi que l'affichage la liste des sondages possible
 * @author felix de Patoul
 * @version 1
 */
public class CreationSondageActivity extends Activity implements AdapterView.OnItemClickListener {

    ImageButton Acc;
    ImageButton Quest;
    ImageButton Choix;

   // private Song currentSong;
    private Handler myHandler = new Handler();
    //private TextView totalTimeTextView;
   // private TextView currentTimeTextView;
    private ListView creationsondageListView;
  //  private ArrayAdapter<Song> playlistAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_sondage);

        Acc = findViewById(R.id.creation_sondage_acc);
        Quest = findViewById(R.id.creation_sondage_quest);
        Choix = findViewById(R.id.creation_sondage_choix);





        // De plus, il faut définir le "choice mode" sur ListView.CHOICE_MODE_SINGLE
        ListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Indique que le clic d'un élément de la liste doit appeler la méthode onItemClick d
        // cette classe (this).
        creationsondageListView.setOnItemClickListener(this);






    /**
     * Lors du clic sur un élément de la liste de lecture, Le morceau correspondant doit être joué.
     */
    @Override
    public void onItemClick(ImageButton button, View view) {
        //passer à une autre page
    }


}

