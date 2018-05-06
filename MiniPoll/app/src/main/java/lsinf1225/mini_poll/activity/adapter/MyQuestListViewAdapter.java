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
import lsinf1225.mini_poll.model.Questionnaire;

/**
 * Created by margauxgerard on 6/05/18.
 */

public class MyQuestListViewAdapter extends  BaseAdapter{
    /**
     * Permet d'instancier un fichier xml de layout dans une vue.
     */
    private final LayoutInflater mInflater;

    /**
     * Liste des éléments de collection à mettre dans la liste.
     */
    private ArrayList<Questionnaire> questionnaires;

    /**
     * Constructeur.
     *
     * @param context        Contexte de l'application.
     * @param questionnaires Liste des éléments de collection à placer dans la liste.
     */
    public MyQuestListViewAdapter(Context context, ArrayList<Questionnaire> questionnaires) {
        mInflater = LayoutInflater.from(context);
        this.questionnaires = questionnaires;
    }

    @Override
    public int getCount() {

        return questionnaires.size();
    }

    @Override
    public Object getItem(int position) {

        return questionnaires.get(position);
    }

    @Override
    public long getItemId(int position) {

        return questionnaires.get(position).getNquest();
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
        Questionnaire questionnaire = questionnaires.get(position);
        nameTextView.setText(questionnaire.getDescription());
        authorTextView.setText("De "+questionnaire.getId());
        //Log.e("MySondageListViewAdapter", "Rating of song " + song.getTitle() + " is " + song.getRating());
        //Log.e("MySondageListViewAdapter", "stepsize " + ratingBar.getStepSize() + " rating " + ratingBar.getRating() + " num " + ratingBar.getNumStars());

        return convertView;
    }

    /**
     * Change la liste des éléments de collection affichée.
     * <p>
     * Permet de changer complètement la liste des éléments affichés dans la liste.
     *
     * @param newQuest nouvelle liste des éléments de collection à afficher.
     */
    public void setSongs(ArrayList<Questionnaire> newQuest) {
        this.questionnaires = newQuest;
        notifyDataSetChanged();
    }
}

