package lsinf1225.mini_poll.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;
import android.util.Log;

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
public class Sondage {

    private static final String COL_NSONDAGE = "Nsondage";
    private static final String COL_ID = "Identifiant";
    private static final String COL_NBRECHOIX = "Nbrechoix";
    private static final String COL_DESCRIPTION = "Description";
    private static final String COL_ACTIVITE = "Activite";
    private static final String BDD_TABLE = "SONDAGE";

    /**
     * Contient les instances déjà existantes des questionnaires afin d'éviter de créer deux instances
     * du même questionnaire.
     */
    private static SparseArray<Sondage> sondSparseArray = new SparseArray<>();

    /**
     * Identifiant unique de l'utilisateur qui a créé le sondage. Correspond à Identifiant dans la base de données.
     */
    private String id;
    /**
     * Numéro du sondage qui a été créé. Correspond à Nsondage dans la base de données.
     */
    private final int nsondage;
    /**
     * Description du sondage qui a été créé. Correspond à Description dans la base de données.
     */
    private String description;
    /**
     * Nombre de possibillités de choix de réponse qui se trouvent dans le sondage. Correspond à Nbrechoix dans la base de données.
     */
    private int nbrechoix;
    /**
     * Statut de l'activité (0 ou 1). Correspond à Activité dans la base de données.
     */
    private int activite;

    /**
     * Constructeur du sondage. Initialise une instance du sondage présent dans la base
     * de données.
     *
     * @note Ce constructeur est privé (donc utilisable uniquement depuis cette classe). Cela permet
     * d'éviter d'avoir deux instances différentes d'un même sondage.
     */
    private Sondage(int nSondage, String userId, String sDesc, int sNbreChoix, int sActi) {

        this.id = userId;
        this.nsondage = nSondage;
        this.description = sDesc;
        this.activite = sActi;
        this.nbrechoix= sNbreChoix;
        Sondage.sondSparseArray.put(nSondage, this);
    }

    /**
     * Fournit la liste des sondages.
     */
    public static ArrayList<Sondage> getSondages() {
        // Récupération du  SQLiteHelper et de la base de données.
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();

        // Colonnes à récupérer
        String[] colonnes = {COL_NSONDAGE, COL_ID,COL_NBRECHOIX, COL_DESCRIPTION, COL_ACTIVITE};

        // Requête de selection (SELECT)
        Cursor cursor = db.query(BDD_TABLE, colonnes, null, null, null, null, null);

        // Placement du curseur sur la première ligne.
        cursor.moveToFirst();

        // Initialisation la liste des sondages.
        ArrayList<Sondage> sondages = new ArrayList<>();

        // Tant qu'il y a des lignes.
        while (!cursor.isAfterLast()) {
            // Récupération des informations du sondage pour chaque ligne.
            int nSondage = cursor.getInt(0);
            String userId = cursor.getString(1);
            int sNbreChoix = cursor.getInt(2);
            String sDesc = cursor.getString(3);
            int sActi = cursor.getInt(4);

            // Vérification pour savoir s'il y a déjà une instance de ce sondage.
            Sondage sondage = Sondage.sondSparseArray.get(nSondage);
            if (sondage == null) {
                // Si pas encore d'instance, création d'une nouvelle instance.
                sondage = new Sondage(nSondage, userId, sDesc,sNbreChoix, sActi);
            }

            // Ajout de le questionnaire à la liste.
            sondages.add(sondage);

            // Passe à la ligne suivante.
            cursor.moveToNext();
        }

        // Fermeture du curseur et de la base de données.
        cursor.close();
        db.close();

        return sondages;
    }

    /**
     * Fournit la liste des sondages pour l'utilisateur connecté
     */
    public static ArrayList<Sondage> getSondagesConnected() {
        // Récupération du  SQLiteHelper et de la base de données.
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();

        // Colonnes à récupérer
        String[] colonnes = {COL_NSONDAGE, COL_ID,COL_NBRECHOIX, COL_DESCRIPTION, COL_ACTIVITE};

        // Requête de selection (SELECT)
        //Cursor cursor = db.query(BDD_TABLE, colonnes, null, null, null, null, null);
        String connectedUser = User.getConnectedUser().getId();
        Cursor cursor = db.rawQuery("SELECT S.Nsondage, S.Identifiant, S.Nbrechoix, S.Description, S.Activite  FROM PARTICIPANTS_SONDAGE PS, SONDAGE S " + "WHERE PS.Nsondage = S.Nsondage AND PS.Identifiant =\'"+connectedUser+"\' AND Activite='0'",null);
        // Placement du curseur sur la première ligne.
        cursor.moveToFirst();

        // Initialisation la liste des sondages.
        ArrayList<Sondage> sondages = new ArrayList<>();

        // Tant qu'il y a des lignes.
        while (!cursor.isAfterLast()) {
            // Récupération des informations du sondage pour chaque ligne.
            int nSondage = cursor.getInt(0);
            String userId = cursor.getString(1);
            int sNbreChoix = cursor.getInt(2);
            String sDesc = cursor.getString(3);
            int sActi = cursor.getInt(4);

            // Vérification pour savoir s'il y a déjà une instance de ce sondage.
            Sondage sondage = Sondage.sondSparseArray.get(nSondage);
            if (sondage == null) {
                // Si pas encore d'instance, création d'une nouvelle instance.
                sondage = new Sondage(nSondage, userId, sDesc,sNbreChoix, sActi);
            }

            // Ajout de le questionnaire à la liste.
            sondages.add(sondage);

            // Passe à la ligne suivante.
            cursor.moveToNext();
        }

        // Fermeture du curseur et de la base de données.
        cursor.close();
        db.close();

        return sondages;
    }

    /**
     * Retourne true si l'utilisateur a repondu au sondage, false sinon
     */
    public static boolean isAnswered (int nSondage) {
        // Récupération du  SQLiteHelper et de la base de données.
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();
        String connectedUser = User.getConnectedUser().getId();
        Cursor cursor = db.rawQuery("SELECT count(S.Npossibilites) "+
                "FROM POSSIBILITE P, SCORE S "+
                "WHERE P.Npossibilites = S.Npossibilites AND S.Identifiant=\'"+connectedUser+"\' AND P.Nsondage = \'"+nSondage+"\'",null);
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

    /**
     * Renvoie les propositions d'un sondage
     */
    public static ArrayList<String> loadPropositions(int nSondage) {
        // Récupération du  SQLiteHelper et de la base de données.
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();
        Log.d("tagText",Integer.toString(nSondage));
        Cursor cursor = db.rawQuery("SELECT P.Texte "+
                "FROM POSSIBILITE P, SONDAGE S "+
                "WHERE S.nSondage = P.nSondage AND S.nSondage = \'"+nSondage+"\'", null);

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
     * Fournit l'identifiant de l'utilisateur courant qui a créé le sondage.
     */
    public String getId() {

        return id;
    }

    /**
     * Fournit le numéro du questionnaire.
     */
    public int getNsondage() {

        return nsondage;
    }

    /**
     * Fournit la description du questionnaire .
     */
    public String getDescription() {

        return description;
    }
    /**
     * Fournit le nombre de choix du sondage.
     */
    public int getNbreChoix() {

        return nbrechoix;
    }

    /**
     * Fournit l'activité du questionnaire pour savoir si il est ouvert ou fermé.
     */
    public int getActivite() {

        return activite;
    }

    /**
     * Fournit une représentation textuelle du sondage. (Ici la description du sondage)
     */
    public String toString() {

        return getDescription();
    }

}

