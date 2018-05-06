package lsinf1225.mini_poll.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;

import java.util.ArrayList;

import lsinf1225.mini_poll.MySQLiteHelper;

public class Score {

    private static final String COL_ID = "Identifiant";
    private static final String COL_POSSIBILITE = "nPossibilites";
    private static final String COL_SCORE = "Score";
    private static final String BDD_TABLE = "SCORE";


    /**
    * Contient les instances déjà existantes des scores afin d'éviter de créer deux instances
    * du même score.
     */
    private static SparseArray<Score> scoreSparseArray = new SparseArray<>();

    private int id;
    private int nPossibilite;
    private int score;

    public Score (int id, int nPossibilite, int score) {
        this.id = id;
        this.nPossibilite = nPossibilite;
        this.score = score;
    }


    public static boolean create(int nPossibilite, int score) {

        // Récupération de la base de données.
        SQLiteDatabase db = MySQLiteHelper.get().getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COL_ID, User.getConnectedUser().getId());
        cv.put(COL_POSSIBILITE, nPossibilite);
        cv.put(COL_SCORE, score);

        int result = (int) db.insert(BDD_TABLE, null, cv);

        if (result == -1) {
            return false;
        }
        return true;
    }


}
