package lsinf1225.mini_poll.model;

import android.content.ContentValues;
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
     * Possibilites du sondage
     */
    private ArrayList<String> possibilites;


    /**
     * Constructeur de notre élément de collection. Initialise une instance de l'élément présent
     * dans la base de données.
     *
     * @note Ce constructeur est privé (donc utilisable uniquement depuis cette classe). Cela permet
     * d'éviter d'avoir deux instances différentes d'un même élément dans la base de données, nous
     * utiliserons la méthode statique get(ciId) pour obtenir une instance d'un élément de notre
     * collection.
     */
    private Sondage(int nSondage) {

        // On enregistre l'id dans la variable d'instance.
        this.nsondage = nSondage;
        // On enregistre l'instance de l'élément de collection courant dans la hashMap.
        Sondage.sondSparseArray.put(nSondage, this);

        // On charge les données depuis la base de données.
        loadData();
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
     * Fournit l'activité du sondage pour savoir si il est ouvert ou fermé.
     */
    public int getActivite() {

        return activite;
    }

    /**
     * Fournit les propositions du sondage
     */
    public ArrayList<String> getPossibilites() {
        return this.possibilites;
    }

    /**
     * Fournit une représentation textuelle du sondage. (Ici la description du sondage)
     */
    public String toString() {

        return getDescription();
    }


    //========================================================================
    //========================================================================
    // METHODES DE "CALCUL" SUR BASE DE LA BDD
    //  - Calcul des utilisateurs n'ayant pas encore répondu au sondage
    //  - Calcul des scores pour chaque sondage
    //========================================================================
    //========================================================================

    /**
     * Retourne la liste des utilisateurs n'ayant pas encore répondu au sondage
     * @param nSondage
     * @return ArrayList<String> contenant les utilisateurs en question
     */
    public static ArrayList<String> loadUsersNotAnsweredYet(int nSondage) {
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();
        Log.d("tagText", Integer.toString(nSondage));
        ArrayList<String> users = new ArrayList<String>();
        //recuperation de tous les scores du sondage


        Cursor cursor = db.rawQuery("SELECT P.Identifiant "+
                "FROM PARTICIPANTS_SONDAGE P "+
                "WHERE P.Nsondage = \'"+nSondage+"\' AND P.Identifiant NOT IN ( "+
                "SELECT SC.Identifiant "+
                "FROM SCORE SC, POSSIBILITE P "+
                "WHERE SC.Npossibilites = P.Npossibilites AND P.Nsondage = \'"+nSondage+"\')",null);


        // Placement du curseur sur la première ligne.
        cursor.moveToFirst();


        // Tant qu'il y a des lignes.
        while (!cursor.isAfterLast()) {
            // Récupération des informations du sondage pour chaque ligne.
            String user = cursor.getString(0);
            users.add(user);
            // Passe à la ligne suivante.
            cursor.moveToNext();
        }

        return users;
    }

    /**
     * Méthode d'accès aux scores d'un sondage
     * @param nSondage
     * @param user
     * @return Retourne sous forme d'une arraylist d'entier les scores relatifs à un sondage.
     * Si un utilisateur est spécifié, ne renvoie que les scores relatifs à cet utilisateur.
     * Sinon renvoie les scores pour tous les utilisateurs agrégés par possibilité de réponse
     */
    public static ArrayList<Integer> getScores(int nSondage, User user) {
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();
        ArrayList<Integer> scores = new ArrayList<Integer>();
        //recuperation de tous les scores du sondage
        if (user == null)
        {
            Cursor cursor = db.rawQuery(
                    "SELECT sum(SC.Score) AS Score "+
                            "FROM POSSIBILITE P, SCORE SC "+
                            "WHERE P.Nsondage = \'"+nSondage+"\'  AND P.Npossibilites = SC.Npossibilites "+
                            "GROUP BY P.Texte", null);

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
                    "SELECT sum(SC.Score) AS Score "+
                            "FROM POSSIBILITE P, SCORE SC "+
                            "WHERE P.Nsondage = \'"+nSondage+"\' AND SC.Identifiant = \'"+userId+"\'  AND P.Npossibilites = SC.Npossibilites "+
                            "GROUP BY P.Texte", null);


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


    //========================================================================
    //========================================================================
    // METHODES D'ACCES A LA BASE DE DONNEES
    //========================================================================
    //========================================================================

    /**
     * Fournit l'instance d'un élément de collection présent dans la base de données. Si l'élément
     * de collection n'est pas encore instancié, une instance est créée.
     *
     * @return L'instance de l'élément de collection.
     *
     * @pre L'élément correspondant à l'id donné doit exister dans la base de données.
     */
    public static Sondage get(int nSondage) {
        Sondage s = Sondage.sondSparseArray.get(nSondage);
        if (s != null) {
            return s;
        }
        return new Sondage(nSondage);
    }


    /**
     * (Re)charge les informations depuis la base de données.
     *
     * @pre L'id de l'élément est indiqué dans this.id et l'élément existe dans la base de données.
     * @post Les informations de l'élément sont chargées dans les variables d'instance de la
     * classe.
     */
    private void loadData() {
        // Récupération de la base de données en mode "lecture".
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();

        // Colonnes pour lesquelles il nous faut les données.
        String[] columns = new String[]{COL_ID,COL_NBRECHOIX, COL_DESCRIPTION, COL_ACTIVITE};

        // Critères de sélection de la ligne :
        String selection = COL_NSONDAGE + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(nsondage)};

        // Requête SELECT à la base de données.
        Cursor c = db.query(BDD_TABLE, columns, selection, selectionArgs, null, null, null);

        // Placement du curseur sur le  premier résultat (ici le seul puisque l'objet est unique).
        c.moveToFirst();

        // Copie des données de la ligne vers les variables d'instance de l'objet courant.
        this.id = c.getString(0);
        this.nbrechoix = c.getInt(1);
        this.description = c.getString(2);
        this.activite = c.getInt(3);
        // Fermeture du curseur
        c.close();

    }





    /**
     * Retourne la liste des utilisateurs participant au sondage
     * @param nSondage
     * @return ArrayList<String> contenant les utilisateurs en question
     */
    public static ArrayList<String> loadUsers(int nSondage) {
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();
        Log.d("tagText", Integer.toString(nSondage));
        ArrayList<String> users = new ArrayList<String>();
        //recuperation de tous les scores du sondage


        Cursor cursor = db.rawQuery("SELECT P.Identifiant "+
                "FROM PARTICIPANTS_SONDAGE P "+
                "WHERE P.Nsondage = \'"+nSondage+"\'",null);


        // Placement du curseur sur la première ligne.
        cursor.moveToFirst();


        // Tant qu'il y a des lignes.
        while (!cursor.isAfterLast()) {
            // Récupération des informations du sondage pour chaque ligne.
            String user = cursor.getString(0);
            users.add(user);
            // Passe à la ligne suivante.
            cursor.moveToNext();
        }

        return users;
    }




    /**
     * Methode d'accès aux propositions d'un sondage.
     * @param
     * @return Retourne sous forme d'un ArrayList de String les propositions d'un sondage.
     */
    public void loadPropositions() {
        int nSondage = this.getNsondage();
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

        this.possibilites = possibilites;

    }

    /**
     * Methode d'accès aux propositions d'un sondage (bis)
     * @param nSondage
     * @return Retourne sous forme d'un ArrayList d'entier les identifiants des propositions d'un sondage
     */
    public static ArrayList<Integer> loadNumPropositions(int nSondage) {
        // Récupération du  SQLiteHelper et de la base de données.
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();
        Log.d("tagText",Integer.toString(nSondage));
        Cursor cursor = db.rawQuery("SELECT P.nPossibilites "+
                "FROM POSSIBILITE P, SONDAGE S "+
                "WHERE S.nSondage = P.nSondage AND S.nSondage = \'"+nSondage+"\'", null);

        // Placement du curseur sur la première ligne.
        cursor.moveToFirst();

        // Initialisation la liste des sondages.
        ArrayList<Integer> possibilites = new ArrayList<Integer>();

        // Tant qu'il y a des lignes.
        while (!cursor.isAfterLast()) {
            // Récupération des informations du sondage pour chaque ligne.
            int prop = cursor.getInt(0);
            Log.d("createTag",Integer.toString(prop));
            possibilites.add(prop);
            // Passe à la ligne suivante.
            cursor.moveToNext();
        }

        // Fermeture du curseur et de la base de données.
        cursor.close();
        db.close();

        return possibilites;
    }

    /**
     * Methode d'accès aux sondages de l'application
     * @return Retourne la liste de tous les sondages sous forme d'un arraylist d'objets Sondage
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
            //String userId = cursor.getString(1);
            //int sNbreChoix = cursor.getInt(2);
            //String sDesc = cursor.getString(3);
            //int sActi = cursor.getInt(4);

            // Vérification pour savoir s'il y a déjà une instance de ce sondage.
            Sondage sondage = Sondage.sondSparseArray.get(nSondage);
            if (sondage == null) {
                // Si pas encore d'instance, création d'une nouvelle instance.
                sondage = Sondage.get(nSondage);
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
     * Methode d'accès aux sondages pour l'utilisateur connecté
     * @return Retourne la liste des sondages pour lesquels l'utilisateur connecté participe sous forme d'un arraylist d'objets Sondage
     */
    public static ArrayList<Sondage> getSondagesConnected() {
        // Récupération du  SQLiteHelper et de la base de données.
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();

        // Colonnes à récupérer
        String[] colonnes = {COL_NSONDAGE, COL_ID,COL_NBRECHOIX, COL_DESCRIPTION, COL_ACTIVITE};

        // Requête de selection (SELECT)
        //Cursor cursor = db.query(BDD_TABLE, colonnes, null, null, null, null, null);
        String connectedUser = User.getConnectedUser().getId();
        Cursor cursor = db.rawQuery("SELECT S.Nsondage, S.Identifiant, S.Nbrechoix, S.Description, S.Activite  FROM PARTICIPANTS_SONDAGE PS, SONDAGE S " + "WHERE PS.Nsondage = S.Nsondage AND PS.Identifiant =\'"+connectedUser+"\' AND Activite='1'",null);
        // Placement du curseur sur la première ligne.
        cursor.moveToFirst();

        // Initialisation la liste des sondages.
        ArrayList<Sondage> sondages = new ArrayList<>();

        // Tant qu'il y a des lignes.
        while (!cursor.isAfterLast()) {
            // Récupération des informations du sondage pour chaque ligne.
            int nSondage = cursor.getInt(0);

            // Vérification pour savoir s'il y a déjà une instance de ce sondage.
            Sondage sondage = Sondage.sondSparseArray.get(nSondage);
            if (sondage == null) {
                // Si pas encore d'instance, création d'une nouvelle instance.
                sondage = Sondage.get(nSondage);
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
     * Methode permettant de savoir si un utilisateur a répondu a un sondage ou non.
     * @param nSondage
     * @return True si répondu, false sinon
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
     * Crée un nouvel élément Sondage dans la base de données
     * @param nbreChoix       nombre de choix à faire par participant
     * @param description     question du sondage
     *
     *
     * @return l'identifiant du sondage créé
     *
     * @post Enregistre le nouvel objet dans la base de données.
     */
    public static int create(int nbreChoix, String description) {

        int nSondage = getLatestSondage() + 1;
        //int nSondage =5;
        // Récupération de la base de données.
        SQLiteDatabase db = MySQLiteHelper.get().getWritableDatabase();

        ContentValues cv = new ContentValues();

        Log.d("errorDB", "numero Sondage :"+Integer.toString(nSondage));
        // Définition des valeurs pour le nouvel élément dans la table "Sondage".
        cv.put(COL_NSONDAGE, nSondage);
        cv.put(COL_ID, User.getConnectedUser().getId());
        cv.put(COL_DESCRIPTION, description);
        cv.put(COL_NBRECHOIX, nbreChoix);
        cv.put(COL_ACTIVITE, 1);

        int result = (int) db.insert(BDD_TABLE, null, cv);

        if (result == -1) {
            return 0;
        }
        return nSondage;
    }

    /**
     * Crée un nouvel élément Sondage dans la base de données
     * @param nSondage       identifiant du sondage
     * @param options       options à intégrer
     *
     *
     * @return l'identifiant du sondage créé
     *
     * @post Enregistre le nouvel objet dans la base de données.
     */
    public static boolean addPossibilites(int nSondage, String[] options) {

        int nPossibilite = getLatestPossibilite();
        boolean creationIsOk = true;
        // Récupération de la base de données.
        SQLiteDatabase db = MySQLiteHelper.get().getWritableDatabase();

        for (int i = 0; i<options.length;i++) {
            ContentValues cv = new ContentValues();
            nPossibilite++;
            Log.d("errorDB", "numero possibilite :"+Integer.toString(nPossibilite));
            // Définition des valeurs pour le nouvel élément dans la table "Sondage".
            cv.put("Npossibilites", nPossibilite);
            cv.put("Nsondage",nSondage);
            cv.put("texte", options[i]);
            cv.put("ordre", i);

            int result = (int) db.insert("POSSIBILITE", null, cv);

            if (result == -1) {
                creationIsOk = false;
            }
        }
         return creationIsOk;
    }

    /**
     * Crée un nouvel élément dans la table Participants Sondage pour chaque participant au sondage
     * @param nSondage       identifiant du sondage
     * @param participants   participants du sondage
     *
     *
     * @return l'identifiant du sondage créé
     *
     * @post Enregistre le nouvel objet dans la base de données.
     */
    public static boolean addParticipants(int nSondage, ArrayList<String> participants) {

        boolean creationIsOk = true;
        // Récupération de la base de données.
        SQLiteDatabase db = MySQLiteHelper.get().getWritableDatabase();

        //ajout du créateur du sondage dans la liste des participants
        participants.add(User.getConnectedUser().getId());
        for (int i = 0; i<participants.size();i++) {
            ContentValues cv = new ContentValues();
            // Définition des valeurs pour le nouvel élément dans la table "Sondage".
            cv.put("Identifiant", participants.get(i));
            cv.put("Nsondage",nSondage);

            int result = (int) db.insert("PARTICIPANTS_SONDAGE", null, cv);

            if (result == -1) {
                creationIsOk = false;
            }
        }
        return creationIsOk;
    }

    /**
     * Retourne l'identifiant du dernier sondage actuellement dans la bd.
     * @return l'identifiant unique du dernier sondage créé.
     */
    public static int getLatestSondage(){
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT S.Nsondage "+
                "FROM Sondage S "+
                "ORDER BY S.Nsondage DESC "+
                "LIMIT 1",null);
        // Placement du curseur sur la première ligne.
        cursor.moveToFirst();
        // Récupération de l'identifiant
        int nSondage=cursor.getInt(0);

        // Fermeture du curseur et de la base de données.
        cursor.close();
        db.close();
        return nSondage;
    }

    /**
    * Retourne l'identifiant de la derniere possibilite actuellement dans la bd.
    * @return l'identifiant unique de la dernière possibilité créée.
    */
    public static int getLatestPossibilite(){
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT P.Npossibilites "+
                "FROM POSSIBILITE P "+
                "ORDER BY P.Npossibilites DESC "+
                "LIMIT 1",null);
        // Placement du curseur sur la première ligne.
        cursor.moveToFirst();
        // Récupération de l'identifiant
        int nPoss=cursor.getInt(0);

        // Fermeture du curseur et de la base de données.
        cursor.close();
        db.close();
        return nPoss;
    }



}

