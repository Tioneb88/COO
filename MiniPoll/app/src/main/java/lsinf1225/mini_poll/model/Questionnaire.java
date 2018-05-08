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
public class Questionnaire {

    private static final String COL_NQUESTIONNAIRE = "Nquestionnaire";
    private static final String COL_ID = "Identifiant";
    private static final String COL_DESCRIPTION = "Description";
    private static final String COL_ACTIVITE = "Activité";
    private static final String BDD_TABLE = "QUESTIONNAIRE";

    /**
     * Contient les instances déjà existantes des questionnaires afin d'éviter de créer deux instances
     * du même questionnaire.
     */
    private static SparseArray<Questionnaire> questSparseArray = new SparseArray<>();

    /**
     * Identifiant unique de l'utilisateur qui a créé le sondage. Correspond à Identifiant dans la base de données.
     */
    private String id;
    /**
     * Numéro du questionnaire qui a été créé. Correspond à Nquestionnaire dans la base de données.
     */
    private final int nquest;
    /**
     * Description du questionnaire qui a été créé. Correspond à Description dans la base de données.
     */
    private String description;
    /**
     * Statut de l'activité (0 ou 1). Correspond à Activité dans la base de données.
     */
    private int activite;

    /**
     * Constructeur du questionnaires. Initialise une instance du questionnaire présent dans la base
     * de données.
     *
     * @note Ce constructeur est privé (donc utilisable uniquement depuis cette classe). Cela permet
     * d'éviter d'avoir deux instances différentes d'un même questionnaire.
     */
    private Questionnaire(int numQuest, String userId, String qDesc, int qActi) {

        this.id = userId;
        this.nquest = numQuest;
        this.description = qDesc;
        this.activite = qActi;
        Questionnaire.questSparseArray.put(numQuest, this);
    }

    /**
     * Fournit la liste des questionnaires.
     */
    public static ArrayList<Questionnaire> getQuestionnaires() {
        // Récupération du  SQLiteHelper et de la base de données.
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();

        // Colonnes à récupérer
        String[] colonnes = {COL_NQUESTIONNAIRE, COL_ID, COL_DESCRIPTION, COL_ACTIVITE};

        // Requête de selection (SELECT)
        Cursor cursor = db.query(BDD_TABLE, colonnes, null, null, null, null, null);

        // Placement du curseur sur la première ligne.
        cursor.moveToFirst();

        // Initialisation la liste des questionnaires.
        ArrayList<Questionnaire> questionnaires = new ArrayList<>();

        // Tant qu'il y a des lignes.
        while (!cursor.isAfterLast()) {
            // Récupération des informations du questionnaire pour chaque ligne.
            int numquest = cursor.getInt(0);
            String userId = cursor.getString(1);
            String qDesc = cursor.getString(2);
            int qActi = cursor.getInt(3);

            // Vérification pour savoir s'il y a déjà une instance de ce questionnaire.
            Questionnaire quest = Questionnaire.questSparseArray.get(numquest);
            if (quest == null) {
                // Si pas encore d'instance, création d'une nouvelle instance.
                quest = new Questionnaire(numquest, userId, qDesc,qActi);
            }

            // Ajout de le questionnaire à la liste.
            questionnaires.add(quest);

            // Passe à la ligne suivante.
            cursor.moveToNext();
        }

        // Fermeture du curseur et de la base de données.
        cursor.close();
        db.close();

        return questionnaires;
    }

    /**
     * Fournit la liste des sondages pour l'utilisateur connecté
     */
    public static ArrayList<Questionnaire> getQuestConnected() {
        // Récupération du  SQLiteHelper et de la base de données.
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();

        // Colonnes à récupérer
        String[] colonnes = {COL_NQUESTIONNAIRE, COL_ID, COL_DESCRIPTION, COL_ACTIVITE};



        // Requête de selection (SELECT)
        //Cursor cursor = db.query(BDD_TABLE, colonnes, null, null, null, null, null);
        String connectedUser = User.getConnectedUser().getId();
        Cursor cursor = db.rawQuery("SELECT Q.NQuestionnaire, Q.Identifiant, Q.Description, Q.Activite FROM PARTICIPANTS_QUESTIONNAIRE S, QUESTIONNAIRE Q WHERE S.Nquestionnaire = Q.Nquestionnaire AND S.Identifiant=\'" + connectedUser + "\' AND Activite='0'",null);
       // Cursor cursor = db.rawQuery("SELECT PQ.Nquestionnaire, Q.Description"+"FROM PARTICIPANTS_QUESTIONNAIRE PQ, QUESTIONNAIRE Q"+"WHERE Q.Nquestionnaire = PQ.Nquestionnaire AND PQ.Identifiant =  AND Activite = 0",null);

        // Placement du curseur sur la première ligne.
        cursor.moveToFirst();

        // Initialisation la liste des sondages.
        ArrayList<Questionnaire> questionnaires = new ArrayList<>();

        // Tant qu'il y a des lignes.
        while (!cursor.isAfterLast()) {
            // Récupération des informations du sondage pour chaque ligne.
            int nQuest = cursor.getInt(0);

            // Vérification pour savoir s'il y a déjà une instance de ce sondage.
            Questionnaire questionnaire = Questionnaire.questSparseArray.get(nQuest);
            if (questionnaire == null) {
                // Si pas encore d'instance, création d'une nouvelle instance.
                questionnaire = Questionnaire.get(nQuest);
            }

            // Ajout de le questionnaire à la liste.
            questionnaires.add(questionnaire);

            // Passe à la ligne suivante.
            cursor.moveToNext();
        }

        // Fermeture du curseur et de la base de données.
        cursor.close();
        db.close();

        return questionnaires;
    }

    /**
     * Fournit l'identifiant de l'utilisateur courant qui a créé le questionnaire.
     */
    public String getId() {

        return id;
    }

    /**
     * Fournit le numéro du questionnaire.
     */
    public int getNquest() {

        return nquest;
    }

    /**
     * Fournit la description du questionnaire .
     */
    public String getDescription() {

        return description;
    }

    /**
     * Fournit l'activité du questionnaire pour savoir si il est ouvert ou fermé.
     */
    public int getActivite() {

        return activite;
    }

    /**
     * Fournit une représentation textuelle du questionnaire. (Ici la description du questionnaire)
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
    public static Questionnaire get(int nquest) {
        Questionnaire s = Questionnaire.questSparseArray.get(nquest);

        if (s != null) {
            return s;
        }
        return new Questionnaire(nquest,null,null,0);
    }
    /**
     * Renvoie les propositions d'un sondage
     */
    public static ArrayList<String> loadPropositionsQuest(int nQuest) {
        // Récupération du  SQLiteHelper et de la base de données.
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();
        Log.d("tagText",Integer.toString(nQuest));
        Cursor cursor = db.rawQuery("SELECT Description "+
                "FROM QUESTIONNAIRE P, QUESTION S, PARTICIPANTS_QUESTIONNAIRE Q"+
                "WHERE S.Nquestionnaire = P.Nquestionnaire AND Q.Nquestionnaire = \'"+nQuest+"\'"
                + "AND P.Nquestionnaire = Q.Nquestionnaire AND Q.Identifiant = \'" + User.getConnectedUser().getId() + "\'", null);

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

    public static ArrayList<Integer> loadScoresQuest(int nQuest, User user) {
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();
        Log.d("tagText",Integer.toString(nQuest));
        ArrayList<Integer> scores = new ArrayList<Integer>();
        //recuperation de tous les scores du sondage
        if (user == null)
        {
            Cursor cursor = db.rawQuery(
                    "SELECT (count(CASE WHEN O.Veracite = 1 THEN 1 END) / count(O.Veracite))*100 " +
                            "AS PercentageFROM REPONSE R, OPTION O, QUESTION Q, QUESTIONNAIRE QR " +
                            "WHERE QR.Nquestionnaire = \'" + nQuest + "\' AND R.Noptions = O.Noptions " +
                            "AND O.Nquestions = Q.Nquestions AND Q.Nquestionnaire = QR.Nquestionnaire", null);

            // Placement du curseur sur la première ligne.
            cursor.moveToFirst();


            // Tant qu'il y a des lignes.
            while (!cursor.isAfterLast()) {
                // Récupération des informations du sondage pour chaque ligne.
                int score = cursor.getInt(0);
                scores.add(score);
                //Log.d("tagText",score);
                // Passe à la ligne suivante.
                cursor.moveToNext();
            }


            cursor.close();
        }
        //recuperation pour l'user specifié
        else {
            String userId = user.getId();

            Cursor cursor = db.rawQuery(
                    "SELECT (count(CASE WHEN O.Veracite = 1 THEN 1 END) / count(O.Veracite))*100 " +
                            "AS PercentageFROM REPONSE R,OPTION O,QUESTION Q,QUESTIONNAIRE QR " +
                            "WHERE R.Identifiant = \'" + User.getConnectedUser().getId() + "\' AND QR.Nquestionnaire = \'" + nQuest + "\' " +
                            "AND R.Noptions = O.Noptions AND O.Nquestions = Q.Nquestions AND Q.Nquestionnaire = QR.Nquestionnaire", null);

            // Placement du curseur sur la première ligne.
            cursor.moveToFirst();


            // Tant qu'il y a des lignes.
            while (!cursor.isAfterLast()) {
                // Récupération des informations du sondage pour chaque ligne.
                int score = cursor.getInt(0);
                scores.add(score);
                //Log.d("tagText",score);
                // Passe à la ligne suivante.
                cursor.moveToNext();
            }

            cursor.close();
        }


        // Fermeture de la base de données
        db.close();
        return scores;

    }

}

