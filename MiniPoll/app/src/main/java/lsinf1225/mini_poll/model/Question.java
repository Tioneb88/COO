package lsinf1225.mini_poll.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.SparseArray;

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
public class Question {

    private static final String COL_NQUESTIONS = "Nquestions";
    private static final String COL_NQUESTIONNAIRE = "Nquestionnaire";
    private static final String COL_TEXTE = "Texte";
    private static final String COL_ORDRE = "Ordre";
    private static final String BDD_TABLE = "QUESTION";

    /**
     * Contient les instances déjà existantes des questionnaires afin d'éviter de créer deux instances
     * du même questionnaire.
     */
    private static SparseArray<Question> questionSparseArray = new SparseArray<>();

    /**
     * Numéro de la question. Correspond à Nquestions dans la base de données.
     */
    private final int nquestions;
    /**
     * Numéro questionnaire auquel la question se réfère. Correspond à Nquestionnaire dans la base de données.
     */
    private int nquestionnaire;
    /**
     * Description de la question qui a été crééz. Correspond à Texte dans la base de données.
     */
    private String texte;
    /**
     * Ordre dans lequel les réponses sont classées. Correspond à Ordre dans la base de données.
     */
    private int ordre;

    /**
     * Constructeur de la question. Initialise une instance de la question présente dans la base
     * de données.
     *
     * @note Ce constructeur est privé (donc utilisable uniquement depuis cette classe). Cela permet
     * d'éviter d'avoir deux instances différentes d'une même question.
     */
    private Question(int nQuestions, int nQuestionnaire, String txt, int qOrdre) {

        this.nquestions = nQuestions;
        this.nquestionnaire = nQuestionnaire;
        this.texte = txt;
        this.ordre= qOrdre;
        Question.questionSparseArray.put(nquestions, this);
    }

    /**
     * Fournit la liste des questions qui composent les questionnaires.
     */
    public static ArrayList<Question> getQuestions() {
        // Récupération du  SQLiteHelper et de la base de données.
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();

        // Colonnes à récupérer
        String[] colonnes = {COL_NQUESTIONS, COL_NQUESTIONNAIRE,COL_TEXTE, COL_ORDRE};

        // Requête de selection (SELECT)
        Cursor cursor = db.query(BDD_TABLE, colonnes, null, null, null, null, null);

        // Placement du curseur sur la première ligne.
        cursor.moveToFirst();

        // Initialisation la liste des questions.
        ArrayList<Question> questions = new ArrayList<>();

        // Tant qu'il y a des lignes.
        while (!cursor.isAfterLast()) {
            // Récupération des informations de la question pour chaque ligne.
            int nQuestions = cursor.getInt(0);
            int nQuestionnaire = cursor.getInt(1);
            String txt = cursor.getString(2);
            int qOrdre = cursor.getInt(3);

            // Vérification pour savoir s'il y a déjà une instance de cette question.
            Question question = Question.questionSparseArray.get(nQuestions);
            if (question == null) {
                // Si pas encore d'instance, création d'une nouvelle instance.
                question = new Question(nQuestions, nQuestionnaire, txt,qOrdre);
            }

            // Ajout de la question à la liste.
            questions.add(question);

            // Passe à la ligne suivante.
            cursor.moveToNext();
        }

        // Fermeture du curseur et de la base de données.
        cursor.close();
        db.close();

        return questions;
    }

    /**
     * Fournit la liste des sondages pour l'utilisateur connecté
     */
    public static ArrayList<Question> getQuestionConnected() {
        // Récupération du  SQLiteHelper et de la base de données.
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();

        // Colonnes à récupérer
        String[] colonnes = {COL_NQUESTIONS, COL_NQUESTIONNAIRE,COL_TEXTE, COL_ORDRE};

        // Requête de selection (SELECT)
        //Cursor cursor = db.query(BDD_TABLE, colonnes, null, null, null, null, null);
        String connectedUser = User.getConnectedUser().getId();
        Cursor cursor = db.rawQuery("SELECT A.Nquestions, A.Nquestionnaire, A.Texte, A.Ordre FROM PARTICIPANTS_QUESTIONNAIRE S, QUESTIONNAIRE Q, QUESTION A WHERE Q.Nquestionnaire = A.Nquestionnaire AND S.Nquestionnaire = Q.Nquestionnaire AND S.Identifiant=\'" + connectedUser + "\'",null);
        // Cursor cursor = db.rawQuery("SELECT PQ.Nquestionnaire, Q.Description"+"FROM PARTICIPANTS_QUESTIONNAIRE PQ, QUESTIONNAIRE Q"+"WHERE Q.Nquestionnaire = PQ.Nquestionnaire AND PQ.Identifiant =  AND Activite = 0",null);

        // Placement du curseur sur la première ligne.
        cursor.moveToFirst();

        // Initialisation la liste des sondages.
        ArrayList<Question> questions = new ArrayList<>();

        // Tant qu'il y a des lignes.
        while (!cursor.isAfterLast()) {
            // Récupération des informations du sondage pour chaque ligne.
            int nQuestion = cursor.getInt(0);
            int nQuest = cursor.getInt(1);
            String texte = cursor.getString(2);
            int ordre = cursor.getInt(3);

            // Vérification pour savoir s'il y a déjà une instance de ce sondage.
            Question question = Question.questionSparseArray.get(nQuestion);
            if (question == null) {
                // Si pas encore d'instance, création d'une nouvelle instance.
                question = Question.get(nQuestion);
            }
            Question que = new Question(nQuestion,nQuest,texte,ordre);
            // Ajout de le questionnaire à la liste.
            questions.add(que);

            // Passe à la ligne suivante.
            cursor.moveToNext();
        }

        // Fermeture du curseur et de la base de données.
        cursor.close();
        db.close();

        return questions;
    }


    /**
     * Fournit la liste des sondages pour l'utilisateur connecté
     */
    public static ArrayList<Question> getQuestionConnected1(int Nquest) {
        // Récupération du  SQLiteHelper et de la base de données.
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();

        // Colonnes à récupérer
        String[] colonnes = {COL_NQUESTIONS, COL_NQUESTIONNAIRE,COL_TEXTE, COL_ORDRE};

        // Requête de selection (SELECT)
        //Cursor cursor = db.query(BDD_TABLE, colonnes, null, null, null, null, null);
        String connectedUser = User.getConnectedUser().getId();
        Cursor cursor = db.rawQuery("SELECT A.Nquestions, A.Nquestionnaire, A.Texte, A.Ordre FROM PARTICIPANTS_QUESTIONNAIRE S, QUESTIONNAIRE Q, QUESTION A WHERE  A.Nquestionnaire =\'"+Nquest+"\' AND S.Nquestionnaire = Q.Nquestionnaire AND S.Identifiant=\'" + connectedUser + "\'",null);
        // Cursor cursor = db.rawQuery("SELECT PQ.Nquestionnaire, Q.Description"+"FROM PARTICIPANTS_QUESTIONNAIRE PQ, QUESTIONNAIRE Q"+"WHERE Q.Nquestionnaire = PQ.Nquestionnaire AND PQ.Identifiant =  AND Activite = 0",null);

        // Placement du curseur sur la première ligne.
        cursor.moveToFirst();

        // Initialisation la liste des sondages.
        ArrayList<Question> questions = new ArrayList<>();

        // Tant qu'il y a des lignes.
        while (!cursor.isAfterLast()) {
            // Récupération des informations du sondage pour chaque ligne.
            int nQuestion = cursor.getInt(0);
            int nQuest = cursor.getInt(1);
            String texte = cursor.getString(2);
            int ordre = cursor.getInt(3);

            // Vérification pour savoir s'il y a déjà une instance de ce sondage.
            Question question = Question.questionSparseArray.get(nQuestion);
            if (question == null) {
                // Si pas encore d'instance, création d'une nouvelle instance.
                question = Question.get(nQuestion);
            }
            Question que = new Question(nQuestion,nQuest,texte,ordre);
            // Ajout de le questionnaire à la liste.
            questions.add(que);

            // Passe à la ligne suivante.
            cursor.moveToNext();
        }

        // Fermeture du curseur et de la base de données.
        cursor.close();
        db.close();

        return questions;
    }

    /**
     * Renvoie les propositions d'un sondage
     */
    public static ArrayList<String> loadPropositionsQuest(int nQuest) {
        // Récupération du  SQLiteHelper et de la base de données.
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();
        Log.d("tagText",Integer.toString(nQuest));
        Cursor cursor = db.rawQuery("SELECT P.Texte "+
                "FROM OPTION P, QUESTION Q, PARTICIPANTS_QUESTIONNAIRE Q WHERE Q.Nquestions = P.Nquestions AND Q.Identifiant = \'" + User.getConnectedUser().getId() + "\'", null);

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
    public static String get_descr (int Nquestion){
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();

        // Requête de selection (SELECT)
        Cursor cursor = db.rawQuery("SELECT Texte FROM QUESTION WHERE Nquestions =\'"+Nquestion+ "\'",null);
        // Placement du curseur sur la première ligne.
        cursor.moveToFirst();
        String id = null;

        // Tant qu'il y a des lignes.
        while (!cursor.isAfterLast()) {
            // Récupération des informations du sondage pour chaque ligne.
            id = cursor.getString(0);
            // Log.d("tagCursor",id);
            // Passe à la ligne suivante.
            cursor.moveToNext();
        }
        // Fermeture du curseur et de la base de données.
        cursor.close();
        db.close();

        return id;
    }

    public static ArrayList<String> get_descr2 (int Nquestion){
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();

        // Requête de selection (SELECT)
        Cursor cursor = db.rawQuery("SELECT Texte FROM QUESTION WHERE Nquestions =\'"+Nquestion+ "\'",null);
        // Placement du curseur sur la première ligne.
        cursor.moveToFirst();

        // Initialisation la liste des sondages.
        ArrayList<String> bffs = new ArrayList<>();

        // Tant qu'il y a des lignes.
        while (!cursor.isAfterLast()) {
            // Récupération des informations du sondage pour chaque ligne.
            String bff = cursor.getString(0);
            //Log.d("tagCursor",bff);
            bffs.add(bff);
            // Passe à la ligne suivante.
            cursor.moveToNext();
        }
        // Fermeture du curseur et de la base de données.
        cursor.close();
        db.close();

        return bffs;
    }
    /**
     * Fournit le numéro de la réponse.
     */
    public int getNquestions() {

        return nquestions;
    }

    /**
     * Fournit le numéro du questionnaire auquel la question se rapporte.
     */
    public int getNquestionnaire() {

        return nquestionnaire;
    }
    /**
     * Fournit la description des questions.
     */
    public String getTexte() {

        return texte;
    }

    /**
     * Fournit l'ordre de la question.
     */
    public int getOrdre() {

        return ordre;
    }

    /**
     * Fournit une représentation textuelle da la question. (Ici la description de la question)
     */
    public String toString() {

        return getTexte();
    }
    /**
     * Fournit l'instance d'un élément de collection présent dans la base de données. Si l'élément
     * de collection n'est pas encore instancié, une instance est créée.
     *
     * @return L'instance de l'élément de collection.
     *
     * @pre L'élément correspondant à l'id donné doit exister dans la base de données.
     */
    public static Question get(int nquestion) {
        Question s = Question.questionSparseArray.get(nquestion);

        if (s != null) {
            return s;
        }
        return new Question(nquestion,0,null,0);
    }


}

