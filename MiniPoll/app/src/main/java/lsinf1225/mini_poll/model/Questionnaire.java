package lsinf1225.mini_poll.model;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.SparseArray;

import java.util.ArrayList;

import be.uclouvain.lsinf1225.musicplayer.MySQLiteHelper;


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
     * Contient les instances déjà existantes des utilisateurs afin d'éviter de créer deux instances
     * du même utilisateur.
     */
    private static SparseArray<Questionnaire> userSparseArray = new SparseArray<>();

    /**
     * Identifiant unique de l'utilisateur qui a créé le sondage. Correspond à Identifiant dans la base de données.
     */
    private final int id;
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
     */
    private User(int userId, String userNom, String userPassword, String userPrenom, String userMail, String userPhoto, String userBff) {

        this.id = userId;
        this.nom = userNom;
        this.password = userPassword;
        this.prenom = userPrenom;
        this.mail = userMail;
        this.photo = userPhoto;
        this.bff = userBff;
        User.userSparseArray.put(userId, this);
    }

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
            int userId = cursor.getInt(0);
            String userNom = cursor.getString(1);
            String userPrenom = cursor.getString(2);
            String userPassword = cursor.getString(3);
            String userMail = cursor.getString(4);
            String userPhoto = cursor.getString(5);
            String userBff = cursor.getString(6);

            // Vérification pour savoir s'il y a déjà une instance de cet utilisateur.
            User user = User.userSparseArray.get(userId);
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
     * Fournit l'identifiant de l'utilisateur courant.
     */
    public int getId() {

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

        return getNom();
    }

}

