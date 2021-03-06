package lsinf1225.mini_poll.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;

import lsinf1225.mini_poll.R;
import lsinf1225.mini_poll.model.Question;

/**
 * Gère l'affichage personnalisé d'objets question
 *
 * @author Margaux
 * @version 1
 * @see <a href="http://d.android.com/reference/android/widget/Adapter.html">Adapter</a>
 * @see <a href="http://d.android.com/reference/android/widget/BaseAdapter.html">BaseAdapter</a>
 */
public class MyQuestionListViewAdapter extends BaseAdapter {
    /**
     * Permet d'instancier un fichier xml de layout dans une vue.
     */
    private final LayoutInflater mInflater;

    /**
     * Liste des éléments de collection à mettre dans la liste.
     */
    private ArrayList<Question> questions;

    /**
     * Constructeur.
     *
     * @param context        Contexte de l'application.
     * @param questions Liste des éléments de collection à placer dans la liste.
     */
    public MyQuestionListViewAdapter(Context context, ArrayList<Question> questions) {
        mInflater = LayoutInflater.from(context);
        this.questions = questions;
    }

    @Override
    public int getCount() {

        return questions.size();
    }

    @Override
    public Object getItem(int position) {

        return questions.get(position);
    }

    @Override
    public long getItemId(int position) {

        return questions.get(position).getNquestions();
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

        if (convertView == null) {
            // Création d'un nouvelle vue avec le layout correspondant au fichier xml
            convertView = mInflater.inflate(R.layout.collected_sondage_row, parent, false);
        }

        // Récupération des deux éléments de notre vue dans le but d'y placer les données.
        TextView nameTextView = convertView.findViewById(R.id.user_id_row);

        // Récupération et placement des données.
        Question question = questions.get(position);
        nameTextView.setText(Question.get_descr(question.getNquestions()));

        return convertView;
    }

    /**
     * Change la liste des éléments de collection affichée.
     * <p>
     * Permet de changer complètement la liste des éléments affichés dans la liste.
     *
     * @param newQuest La nouvelle liste des éléments de collection à afficher.
     */
    public void setQuestions(ArrayList<Question> newQuest) {
        this.questions = newQuest;
        notifyDataSetChanged();
    }

}
