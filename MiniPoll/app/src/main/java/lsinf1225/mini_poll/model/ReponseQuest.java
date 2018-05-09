package lsinf1225.mini_poll.model;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;

import lsinf1225.mini_poll.MySQLiteHelper;
/**
 * Représente un utilisateur et gère l'authentification de celui-ci à l'application.
 * <p>
 * Cette classe représente un utilisateur de l'application. Elle utilise pour cela la base de
 * données par l'intermédiaire du MySQLiteHelper.
 * <p>
 * Les méthodes statiques permettent de récupérer la liste des utilisateurs, récupérer l'utilisateur
 * actuellement connecté (s'il y en a un) et de déconnecter l'utilisateur.
 *
 * @author Margaux GERARD, Loïc QUINET, Félix DE PATOUL, Benoît MICHEL, Arnaud CLAES
 * @version 1
 * @date 9 mai 2018
 */
public class ReponseQuest {

    private static final String COL_ID = "Identifiant";
    private static final String COL_NQUESTIONS = "Nquestions";
    private static final String COL_NOPTIONS = "Noptions";
    private static final String BDD_TABLE = "REPONSE";


    private String id;
    /**
     * Numéro de la question qui a été créé. Correspond à Nquestion dans la base de données.
     */
    private final int nquestion;
    /**
     * Numéro unique de la réponse qui a été choisi par le type interrogé. Correspond à Noptions dans la base de donnée.
     */
    private final int noptions;

    /**
     * Constructeur du questionnaires. Initialise une instance du questionnaire présent dans la base
     * de données.
     *
     * @note Ce constructeur est privé (donc utilisable uniquement depuis cette classe). Cela permet
     * d'éviter d'avoir deux instances différentes d'un même questionnaire.
     */
    private ReponseQuest(String userid, int nquestion, int noptions) {

        this.id = userid;
        this.nquestion = nquestion;
        this.noptions = noptions;
    }

    /**
     * Fournit l'identifiant de l'utilisateur courant qui répond au questionnaire.
     */
    public String getId() {

        return id;
    }

    /**
     * Fournit le numéro du question.
     */
    public int getNquestion() {

        return nquestion;
    }

    /**
     * Fournit l'option choisi du questionnaire .
     */
    public int getNoptions() {

        return noptions;
    }

    public static boolean mettredansbd(int nquestions, int noptions) {

        // Récupération de la base de données.
        SQLiteDatabase db = MySQLiteHelper.get().getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_ID, User.getConnectedUser().getId());
        cv.put(COL_NQUESTIONS, nquestions);
        cv.put(COL_NOPTIONS, noptions);

        int result = (int) db.insert(BDD_TABLE, null, cv);

        if (result == -1) {
            return false;
        }
        return true;
    }


}
