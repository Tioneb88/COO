package lsinf1225.mini_poll.activity.adapter;

        import android.content.Context;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.RatingBar;
        import android.widget.TextView;

        import java.util.ArrayList;

        import lsinf1225.mini_poll.MiniPollApp;
        import lsinf1225.mini_poll.R;
        import lsinf1225.mini_poll.model.Aide;

/**
 * Created by margauxgerard on 6/05/18.
 */

public class MyAideListViewAdapter extends BaseAdapter {
    /**
     * Permet d'instancier un fichier xml de layout dans une vue.
     */
    private final LayoutInflater mInflater;

    /**
     * Liste des éléments de collection à mettre dans la liste.
     */
    private ArrayList<Aide> aides;

    /**
     * Constructeur.
     *
     * @param context        Contexte de l'application.
     * @param aides Liste des éléments de collection à placer dans la liste.
     */
    public MyAideListViewAdapter(Context context, ArrayList<Aide> aides) {
        mInflater = LayoutInflater.from(context);
        this.aides = aides;
    }

    @Override
    public int getCount() {

        return aides.size();
    }

    @Override
    public Object getItem(int position) {

        return aides.get(position);
    }

    @Override
    public long getItemId(int position) {

        return aides.get(position).getNaide();
    }

    /**
     * Remplit chaque ligne de la liste avec un layout particulier.
     * <p>
     * Cette méthode est appelée par Android pour construire la vue de la liste (lors de la
     * construction de listview).
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Si la vue n'a pas encore été créé (typiquement lors du première affichage de la liste).
        // Android recycle en effet les layout déjà chargés des éléments de la liste (par exemple
        // lors du changement de l'ordre dans la liste.)


        // Récupération des deux éléments de notre vue dans le but d'y placer les données.
        TextView nameTextView = convertView.findViewById(R.id.show_row_name);
        TextView authorTextView = convertView.findViewById(R.id.author_name);

        // Récupération et placement des données.
        Aide aide = aides.get(position);
        nameTextView.setText(aide.getDescription());
        authorTextView.setText("De "+aide.getId());
        //Log.e("MySondageListViewAdapter", "Rating of song " + song.getTitle() + " is " + song.getRating());
        //Log.e("MySondageListViewAdapter", "stepsize " + ratingBar.getStepSize() + " rating " + ratingBar.getRating() + " num " + ratingBar.getNumStars());

        return convertView;
    }

    /**
     * Change la liste des éléments de collection affichée.
     * <p>
     * Permet de changer complètement la liste des éléments affichés dans la liste.
     *
     * @param newAide La nouvelle liste des éléments de collection à afficher.
     */
    public void setSongs(ArrayList<Aide> newAide) {
        this.aides = newAide;
        notifyDataSetChanged();
    }
}
