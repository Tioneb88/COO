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
public class User {

    public static final String COL_NOM = "Nom";
    public static final String COL_ID = "Identifiant";
    public static final String COL_PRENOM = "Prénom";
    public static final String COL_MDP = "MDP";
    public static final String COL_MAIL = "Mail";
    public static final String COL_PHOTO = "Photo";
    public static final String COL_BFF = "Meilleur_ami";
    public static final String BDD_TABLE = "UTILISATEUR";

    /**
     * Contient les instances déjà existantes des utilisateurs afin d'éviter de créer deux instances
     * du même utilisateur.
     */
    public static SparseArray<User> userSparseArray = new SparseArray<>();
    /**
     * Utilisateur actuellement connecté à l'application. Correspond à null si aucun utilisateur
     * n'est connecté.
     */
    public static User connectedUser = null;
    /**
     * Nom de colonne sur laquelle le tri est effectué
     */
    public static String order_by = COL_NOM;
    /**
     * Ordre de tri : ASC pour croissant et DESC pour décroissant
     */
    public static String order = "ASC";
    /**
     * Identifiant unique de l'utilisateur courant. Correspond à Identifiant dans la base de données.
     */
    private  String id;
    /**
     * Nom (unique) de l'utilisateur courant. Correspond à Nom dans la base de données.
     */
    private String nom;
    /**
     * Mot de passe de l'utilisateur courant. Correspond à MDP dans la base de données.
     */
    private String password;
    /**
     * Prénom de l'utilisateur courant. Correspond à Prénom dans la base de données.
     */
    private String prenom;
    /**
     * Addresse e-mail de l'utilisateur courant. Correspond à Mail dans la base de données.
     */
    private String mail;
    /**
     * Chemin menant à la photo de l'utilisateur courant. Correspond à Photo dans la base de données.
     */
    private String photo;
    /**
     * Identifiant du meilleur ami de l'utilisateur courant. Correspond à Meilleur_ami dans la base de données.
     */
    private String bff;

    /**
     * Constructeur de l'utilisateur. Initialise une instance de l'utilisateur présent dans la base
     * de données.
     *
     * @note Ce constructeur est privé (donc utilisable uniquement depuis cette classe). Cela permet
     * d'éviter d'avoir deux instances différentes d'un même utilisateur.
     * => modifié pour pouvoir créer des nouveaux utilisateurs.
     */
    public User(String userId, String userNom, String userPrenom, String userPassword, String userMail, String userPhoto, String userBff) {

        this.id = userId;
        this.nom = userNom;
        this.password = userPassword;
        this.prenom = userPrenom;
        this.mail = userMail;
        this.photo = userPhoto;
        this.bff = userBff;
        //User.userSparseArray.put(userId, this);
        //User.userSparseArray.put(userMail, this);
    }

     /**
     * Constructeur de notre élément de collection. Initialise une instance de l'élément présent
     * dans la base de données.
     *
     * @note Ce constructeur est privé (donc utilisable uniquement depuis cette classe). Cela permet
     * d'éviter d'avoir deux instances différentes d'un même élément dans la base de données, nous
     * utiliserons la méthode statique get(ciId) pour obtenir une instance d'un élément de notre
     * collection.
     */


    /**
     * Fournit l'utilisateur actuellement connecté.
     */
    public static User getConnectedUser() {

        return User.connectedUser;
    }

    /**
     * Déconnecte l'utilisateur actuellement connecté à l'application.
     */
    public static void logout() {

        User.connectedUser = null;
    }

    /**
     * Fournit la liste des utilisateurs.
     */
    public static ArrayList<User> getUtilisateurs() {
        // Récupération du  SQLiteHelper et de la base de données. On ne récupère pas la photo et le
        // meilleur ami de l'utilisateur car ce n'est pas ce qui le caratérise le mieux.
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();
        // Colonnes à récupérer
        String[] colonnes = {COL_ID, COL_NOM, COL_PRENOM, COL_MDP, COL_MAIL, COL_PHOTO, COL_BFF};

        // Requête de selection (SELECT)
        Cursor cursor = db.query(BDD_TABLE, colonnes, null, null, null, null, null);

        // Placement du curseur sur la première ligne.
        cursor.moveToFirst();

        // Initialisation la liste des utilisateurs.
        ArrayList<User> users = new ArrayList<>();

        // Tant qu'il y a des lignes.
        while (!cursor.isAfterLast()) {
            // Récupération des informations de l'utilisateur pour chaque ligne.
            String userId = cursor.getString(0);
            String userNom = cursor.getString(1);
            String userPrenom = cursor.getString(2);
            String userPassword = cursor.getString(3);
            String userMail = cursor.getString(4);
            String userPhoto = cursor.getString(5);
            String userBff = cursor.getString(6);

            // Vérification pour savoir s'il y a déjà une instance de cet utilisateur.
            //User user = User.userSparseArray.get(userId);
            User user=null;
            if (user == null) {
                // Si pas encore d'instance, création d'une nouvelle instance.
                user = new User(userId, userNom, userPrenom,userPassword, userMail,userPhoto, userBff);
            }

            // Ajout de l'utilisateur à la liste.
            users.add(user);

            // Passe à la ligne suivante.
            cursor.moveToNext();
        }

        // Fermeture du curseur et de la base de données.
        cursor.close();
        db.close();
        return users;
    }

    /**

     * Fournit l'instance d'un élément de collection présent dans la base de données. Si l'élément
     * de collection n'est pas encore instancié, une instance est créée.
     *
     * @param ciId Id de l'élément de collection.
     *
     * @return L'instance de l'élément de collection.
     *
     * @pre L'élément correspondant à l'id donné doit exister dans la base de données.
     */
    public static User get(String ciId) {
        /*User ci = User.userSparseArray.get(Integer.parseInt(ciId));
        if (ci != null) {
            return ci;
        }*/
        String name= searchString(ciId, "Nom");
        String prenom= searchString(ciId, "Prénom");
        String mail= searchString(ciId, "Mail");
        String mdp= searchString(ciId, "MDP");
        String bff= searchString(ciId, "Meilleur_ami");
        String photo= searchString(ciId, "Photo");
        return new User( ciId, name, prenom, mdp, mail, photo ,bff);
    }

    public static String searchString(String Id, String searchQuery){
        // Critères de sélection (partie WHERE) : appartiennent à l'utilisateur courant et ont un nom
        // correspondant à la requête de recherche.
        String selection = COL_ID + " = ? AND " + COL_NOM + " LIKE ?";
        String[] selectionArgs = new String[]{String.valueOf(Id), "%" + searchQuery + "%"};

        return getUsersString(Id, selection, selectionArgs);
    }

    public static String getUsersString( String Id, String selection, String[] selectionArgs) {
         // Critère de sélection : appartient à l'utilisateur courant.
        selection = COL_ID + " = ?";
        selectionArgs = new String[]{String.valueOf(Id)};

        // Le critère de sélection est passé à la sous-méthode de récupération des éléments.
        return getUsersString(Id, selection, selectionArgs);
    }
    /*
     * Inverse l'ordre de tri (ASC pour ascendant et DESC pour descendant).
     */

    public static void reverseOrder() {
        if (User.order.equals("ASC")) {
            User.order = "DESC";
        } else {
            User.order = "ASC";
        }
    }

    public static ArrayList<User> getUsers(String selection, String[] selectionArgs) {
        // Récupération de l'ID de l'utilisateur courant.
        String userId = User.getConnectedUser().getId();

        // Critère de sélection : appartient à l'utilisateur courant.
        selection = COL_ID + " = ?";
        selectionArgs = new String[]{String.valueOf(userId)};

        // Le critère de sélection est passé à la sous-méthode de récupération des éléments.
        return getUsers(selection, selectionArgs);
    }

    public static ArrayList<User> searchUser(String searchQuery) {
        // Récupération de l'id de l'utilisateur courant.
        String userId = User.getConnectedUser().getId();

        // Critères de sélection (partie WHERE) : appartiennent à l'utilisateur courant et ont un nom
        // correspondant à la requête de recherche.
        String selection = COL_ID + " = ? AND " + COL_NOM + " LIKE ?";
        String[] selectionArgs = new String[]{String.valueOf(userId), "%" + searchQuery + "%"};

        // Les critères de selection sont passés à la sous-méthode de récupération des éléments.
        return getUsers(selection, selectionArgs);
    }

    /**
     * Renvoie les amis de l'utilisateurs courant.
     *
     */
    public ArrayList<String> getFriends() {
        // Récupération du  SQLiteHelper et de la base de données.
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();

        // Requête de selection (SELECT)
        String currentUser = this.getId();
        Cursor cursor = db.rawQuery("SELECT Emetteur AS Amis FROM RELATION WHERE Recepteur =\'"+connectedUser+ "\' AND Relation=1 UNION SELECT Recepteur AS AMIS FROM RELATION WHERE Emetteur =\'" + connectedUser + "\' AND Relation=1",null);
        // Placement du curseur sur la première ligne.
        cursor.moveToFirst();

        // Initialisation de la liste des amis.
        ArrayList<String> friends = new ArrayList<>();

        // Tant qu'il y a des lignes.
        while (!cursor.isAfterLast()) {
            // Récupération des informations du sondage pour chaque ligne.
            String friend = cursor.getString(0);
            Log.d("tagCursor",friend);
            friends.add(friend);
            // Passe à la ligne suivante.
            cursor.moveToNext();
        }
        // Fermeture du curseur et de la base de données.
        cursor.close();
        db.close();

        return friends;
    }

    /**
     * Ajoute un utilisateur et son mot de passe dans la base de données. (pour la création de compte)
     */
    public boolean addUser(String id, String mdp) {
        // Récupération du  SQLiteHelper et de la base de données.
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();

        // On va chercher tous les identifiants de l'application.
        Cursor cursor = db.rawQuery("SELECT Identifiant FROM UTILISATEUR", null);
        // Placement du curseur sur la première ligne.
        cursor.moveToFirst();

        // On vérifie que l'identifiant n'est pas déjà utilisé.
        while (!cursor.isAfterLast()) {
            String identifiant = cursor.getString(0);
            if(identifiant.equals(id))
            {
                cursor.close();
                db.close();
                return false;
            }
            // Passe à la ligne suivante.
            cursor.moveToNext();
        }
        // On ajoute l'identifiant et le mot de passe dans la base de données.
        db.execSQL("INSERT INTO UTILISATEUR (Identifiant, MDP) VALUES (id, mdp)");
        cursor.close();
        db.close();
        User.connectedUser = this;
        return true;
    }

    /**
     * Fournit l'identifiant de l'utilisateur courant.
     */
    public String getId() {

        return id;
    }

    /**
     * Fournit le nom de l'utilisateur courant.
     */
    public String getNom() {

        return nom;
    }

    /**
     * Fournit le prénom de l'utilisateur courant.
     */
    public String getPrenom() {

        return prenom;
    }

    /**
     * Fournit le mot de passe de l'utilisateur courant.
     */
    public String getPassword() {

        return password;
    }

    /**
     * Fournit le mail de l'utilisateur courant.
     */
    public String getMail() {

        return mail;
    }

    /**
     * Fournit le chemin de la photo de l'utilisateur courant.
     */
    public String getPhoto() {

        return photo;
    }

    /**
     * Fournit l'identifiant du meilleur de l'utilisateur courant.
     */
    public String getBff() {

        return bff;
    }

    /**
     * Connecte l'utilisateur courant.
     *
     * @param passwordToTry le mot de passe entré.
     *
     * @return Vrai (true) si l'utilisateur à l'autorisation de se connecter, false sinon.
     */
    public boolean login(String passwordToTry) {
        if (this.password.equals(passwordToTry)) {
            // Si le mot de passe est correct, modification de l'utilisateur connecté.
            User.connectedUser = this;
            return true;
        }
        return false;
    }

    /**
     * Fournit une représentation textuelle de l'utilisateur courant. (Ici le nom)
     *
     * @note Cette méthode est utilisée par l'adaptateur ArrayAdapter afin d'afficher la liste des
     * utilisateurs. (Voir LoginActivity).
     */
    public String toString() {

        return getId();
    }

    /**
     * change l'identifiant de l'utilisateur courant.
     *
     * @param newUsername le nouveau nom d'utilisateur entré.
     *
     * @return Vrai (true) si ce nom d'utilisateur n'est pas encore utilisé (et alors le changement
     * est effectué, false sinon.
     */
    public int setUsername(String newUsername, String password) {
        // On vérifie le mot de passe.
        if(!password.equals(this.getPassword()))
        {
            return -2;
        }
        // Récupération du  SQLiteHelper et de la base de données en lecture.
        SQLiteDatabase dbR = MySQLiteHelper.get().getReadableDatabase();

        // On va chercher tous les identifiants de l'application.
        Cursor cursor = dbR.rawQuery("SELECT Identifiant FROM UTILISATEUR", null);
        // Placement du curseur sur la première ligne.
        cursor.moveToFirst();

        // On vérifie que l'identifiant n'est pas déjà utilisé.
        while (!cursor.isAfterLast()) {
            String identifiant = cursor.getString(0);
            if(identifiant.equals(newUsername))
            {
                cursor.close();
                dbR.close();
                return -1;
            }
            // Passe à la ligne suivante.
            cursor.moveToNext();
        }
        cursor.close();
        dbR.close();

        // Récupération du  SQLiteHelper et de la base de données en écriture.
        SQLiteDatabase dbW = MySQLiteHelper.get().getWritableDatabase();

        //dbW.execSQL("UPDATE UTILISATEUR SET Identifiant = " + newUsername +" WHERE Identifiant = " + this.getId());

       /* Nouvelle idée !!
        // New value for one column
        String title = newUsername;
        ContentValues values = new ContentValues();
        values.put(FeedEntry.COLUMN_NAME_TITLE, title);

        // Which row to update, based on the title
        String selection = FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
        String[] selectionArgs = { this.getId() };

        int count = db.update(
                MySQLiteHelper.FeedEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        //Encore une autre idée !!
        dbW.update();
        */

        dbW.close();
        this.id = newUsername;
        return 0;
    }

    /**
     * change le password de l'utilisateur courant.
     */
    public int setPassword(String oldPassword, String newPassword) {
        if(password.equals(oldPassword))
        {
            // Récupération du  SQLiteHelper et de la base de données en écriture.
            SQLiteDatabase dbW = MySQLiteHelper.get().getWritableDatabase();

            //dbW.execSQL("UPDATE UTILISATEUR SET MDP = " + newPassword +" WHERE MDP = " + this.getPassword());

            dbW.close();
            this.password = newPassword;
            return 0;
        }
        else
        {
            return -1;
        }
    }

}

