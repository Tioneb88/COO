package lsinf1225.mini_poll.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;
import android.util.Log;
import lsinf1225.mini_poll.model.User;

import java.util.ArrayList;

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
 * @date 25 avril 2018
 */
public class Aide {

    //colonne commune
    private static final String COL_NAIDE = "Naide";

    //table AIDE
    private static final String BDD_TABLE = "AIDE";
    private static final String COL_ID = "Identifiant";
    private static final String COL_DESCRIPTION = "Description";
    private static final String COL_ACTIVITE = "Activite";

    //table OPTIONA
    private static final String BDD_TABLE_OPTIONS = "OPTIONA";
    private static final String COL_NOPTIONSA = "NoptionsA";
    private static final String COL_TEXTE = "Texte";
    private static final String COL_IMAGE = "Image";

    /**
     * Contient les instances déjà existantes des questionnaires afin d'éviter de créer deux instances
     * du même questionnaire.
     */
    private static SparseArray<Aide> aideSparseArray = new SparseArray<>();

    /**
     * Numéro de l'aide qui a été créée. Correspond à Naide dans la base de données.
     */
    private final int naide;
    /**
     * Identifiant unique de l'utilisateur qui a créé l'aide. Correspond à Identifiant dans la base de données.
     */
    private String id;
    /**
     * Description de l'aide qui a été créé. Correspond à Description dans la base de données.
     */
    private String description;
    /**
     * Statut de l'activité (0 ou 1). Correspond à Activite dans la base de données.
     */
    private int activite;

    /**
     * Constructeur de l'aide. Initialise une instance de l'aide présent dans la base
     * de données.
     *
     * @note Ce constructeur est privé (donc utilisable uniquement depuis cette classe). Cela permet
     * d'éviter d'avoir deux instances différentes d'un même sondage.
     */
    private Aide(int nAide, String userId, String sDesc, int sActi) {

        this.id = userId;
        this.naide = nAide;
        this.description = sDesc;
        this.activite = sActi;
        Aide.aideSparseArray.put(nAide, this);
    }

    /**
     * Fournit la liste des aides.
     */
    public static ArrayList<Aide> getAides() {
        // Récupération du  SQLiteHelper et de la base de données.
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();

        // Colonnes à récupérer
        String[] colonnes = {COL_NAIDE, COL_ID,COL_DESCRIPTION, COL_ACTIVITE};

        // Requête de selection (SELECT)
        Cursor cursor = db.query(BDD_TABLE, colonnes, null, null, null, null, null);

        // Placement du curseur sur la première ligne.
        cursor.moveToFirst();

        // Initialisation la liste des aides.
        ArrayList<Aide> aides = new ArrayList<>();

        // Tant qu'il y a des lignes.
        while (!cursor.isAfterLast()) {
            // Récupération des informations de l'aide pour chaque ligne.
            int nAide = cursor.getInt(0);
            String userId = cursor.getString(1);
            String sDesc = cursor.getString(2);
            int sActi = cursor.getInt(3);

            // Vérification pour savoir s'il y a déjà une instance de cette aide.
            Aide aide = Aide.aideSparseArray.get(nAide);
            if (aide == null) {
                // Si pas encore d'instance, création d'une nouvelle instance.
                aide = new Aide(nAide, userId, sDesc, sActi);
            }

            // Ajout de l'aide à la liste.
            aides.add(aide);

            // Passe à la ligne suivante.
            cursor.moveToNext();
        }

        // Fermeture du curseur et de la base de données.
        cursor.close();
        db.close();

        return aides;
    }

    /**
     * Retourne true si l'utilisateur a repondu au sondage, false sinon
     */
    public static boolean isAnswered (int naide) {
        // Récupération du  SQLiteHelper et de la base de données.
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();
        String connectedUser = User.getConnectedUser().getId();
        Cursor cursor = db.rawQuery("SELECT count(S.Noptionsa) "+
                "FROM OPTIONA P, LIKE_LIKE S "+
                "WHERE P.Noptionsa = S.Noptionsa AND S.Identifiant=\'"+connectedUser+"\' AND P.Naide = \'"+naide+"\'",null);
        // Placement du curseur sur la première ligne.
        cursor.moveToFirst();

        // Tant qu'il y a des lignes.
        int answers=0;
        while (!cursor.isAfterLast()) {
            answers = cursor.getInt(0);
            cursor.moveToNext();
        }

        // Fermeture du curseur et de la base de données.
        cursor.close();
        db.close();

        if (answers >0) {
            return true;
        }
        return false;

    }


    public static ArrayList<String> loadOptions() {
        // Récupération du  SQLiteHelper et de la base de données.
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();
        String connectedUser = User.getConnectedUser().getId();
        //Cursor cursor = db.rawQuery("SELECT Description FROM OPTIONA S, AIDE O WHERE S.Naide = O.Naide AND O.Identifiant=\'" + connectedUser + "\'", null);
        Cursor cursor = db.rawQuery("SELECT Description FROM PARTICIPANTS_AIDE S, AIDE O WHERE S.Naide = O.Naide AND S.Identifiant=\'" + connectedUser + "\'", null);
        // Placement du curseur sur la première ligne.
        cursor.moveToFirst();

        // Initialisation la liste des sondages.
        ArrayList<String> options = new ArrayList<String>();

        // Tant qu'il y a des lignes.
        while (!cursor.isAfterLast()) {
            // Récupération des informations du sondage pour chaque ligne.
            String prop = cursor.getString(0);
            options.add(prop);
            Log.d("tagText",prop);
            // Passe à la ligne suivante.
            cursor.moveToNext();
        }

        // Fermeture du curseur et de la base de données.
        cursor.close();
        db.close();

        // Retourne un arrayList de String qui contient toutes les descriptions des aides auxquelles l'utilisateur a acces
        return options;

    }

    /**
     * Renvoie les propositions d'un sondage
     */
    public static ArrayList<String> loadPropositions(int nAide) {
        // Récupération du  SQLiteHelper et de la base de données.
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();
        Log.d("tagText",Integer.toString(nAide));
        Cursor cursor = db.rawQuery("SELECT Texte "+
                "FROM AIDE P, OPTIONA S "+
                "WHERE S.nAide = P.nAide AND S.nAide = \'"+nAide+"\'", null);

        // Placement du curseur sur la première ligne.
        cursor.moveToFirst();

        // Initialisation la liste des sondages.
        ArrayList<String> possibilites = new ArrayList<String>();

        // Tant qu'il y a des lignes.
        while (!cursor.isAfterLast()) {
            // Récupération des informations du sondage pour chaque ligne.
            String prop = cursor.getString(0);
            possibilites.add(prop);
            Log.d("tagText",prop);
            // Passe à la ligne suivante.
            cursor.moveToNext();
        }

        // Fermeture du curseur et de la base de données.
        cursor.close();
        db.close();

        return possibilites;

    }
    /**
     * Fournit le numéro de l'aide.
     */
    public int getNaide() {

        return naide;
    }
    /**
     * Fournit l'identifiant de l'utilisateur courant qui a créé l'aide.
     */
    public String getId() {

        return id;
    }

    /**
     * Fournit la description de l'aide.
     */
    public String getDescription() {

        return description;
    }

    /**
     * Fournit l'activité de l'aide pour savoir si il est ouvert ou fermé.
     */
    public int getActivite() {

        return activite;
    }

    /**
     * Fournit une représentation textuelle de l'aide. (Ici la description de l'aide)
     */
    public String toString() {

        return getDescription();
    }

    /**
     * Fournit l'instance d'un élément de collection présent dans la base de données. Si l'élément
     * de collection n'est pas encore instancié, une instance est créée.
     *
     * @return L'instance de l'élément de collection.
     *
     * @pre L'élément correspondant à l'id donné doit exister dans la base de données.
     */
    public static Aide get(int nAide) {
        Aide s = Aide.aideSparseArray.get(nAide);

        if (s != null) {
            return s;
        }
        return new Aide(nAide,null,null,0);
    }

    /**
     * Va chercher le dernier numéro d'identification unique de demande d'aide.
     */
    public static int nextAideId() {
        // Récupération du  SQLiteHelper et de la base de données.
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();

        // On va chercher tous les identifiants de l'application.
        Cursor cursor = db.rawQuery("SELECT Naide FROM AIDE ORDER BY Naide DESC", null );

        // Placement du curseur sur la première ligne.
        cursor.moveToFirst();
        int Naide = cursor.getInt(0);

        cursor.close();
        db.close();
        return Naide+1;
    }

    /**
     * Va chercher le dernier numéro d'identification unique d'option de demande d'aide.
     */
    public static int nextOptionId() {
        // Récupération du  SQLiteHelper et de la base de données.
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();

        // On va chercher tous les identifiants de l'application.
        Cursor cursor = db.rawQuery("SELECT NoptionsA FROM OPTIONA ORDER BY NoptionsA DESC", null );

        // Placement du curseur sur la première ligne.
        cursor.moveToFirst();
        int NoptionA = cursor.getInt(0);

        cursor.close();
        db.close();
        return NoptionA+1;
    }

    /**
     * Ajoute un utilisateur et ses informations dans la base de données. (pour la création de compte)
     */
    public static void createHelp(String id, String description, String proposal, String proposal2, String friend) {
        // Récupération du  SQLiteHelper et de la base de données.
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();

        // Récupération des identifiants uniques de demandes d'aide et d'options de demande d'aide.
        int nextAideId = nextAideId();
        int nextOptionId = nextOptionId();

        // Nouvelles informations
        ContentValues aide = new ContentValues();
        aide.put(COL_NAIDE, nextAideId);
        aide.put(COL_ID, id);
        aide.put(COL_DESCRIPTION, description);
        aide.put(COL_ACTIVITE, 1);

        ContentValues optionA = new ContentValues();
        optionA.put(COL_NOPTIONSA, nextOptionId);
        optionA.put(COL_NAIDE, nextAideId);
        optionA.put(COL_TEXTE, proposal);

        ContentValues optionB = new ContentValues();
        optionB.put(COL_NOPTIONSA, nextOptionId+1);
        optionB.put(COL_NAIDE, nextAideId);
        optionB.put(COL_TEXTE, proposal2);

        // Insertion dans la base de données
        db.insert(BDD_TABLE, null, aide);
        db.insert(BDD_TABLE_OPTIONS, null, optionA);
        db.insert(BDD_TABLE_OPTIONS, null, optionB);
    }
}

