package lsinf1225.mini_poll.model;

        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.util.Log;
        import android.util.SparseArray;

        import java.util.ArrayList;

        import lsinf1225.mini_poll.MySQLiteHelper;

/**
 * Created by margauxgerard on 3/05/18.
 */

public class Ami {
    public static final String COL_EMET = "Emetteur";
    public static final String COL_RECEPT = "Recepteur";
    public static final String COL_REL = "Relation";
    public static final String BDD_TABLE = "RELATION";

    /**
     * Contient les instances déjà existantes des utilisateurs afin d'éviter de créer deux instances
     * du même utilisateur.
     */
    public static SparseArray<Ami> amiSparseArray = new SparseArray<>();
    /**
     * Nom de colonne sur laquelle le tri est effectué
     */
    public static String order_by = COL_EMET;
    /**
     * Ordre de tri : ASC pour croissant et DESC pour décroissant
     */
    public static String order = "DESC";
    /**
     * Identifiant unique de l'utilisateur courant. Correspond à Identifiant dans la base de données.
     */
    private  String emetteur;
    /**
     * Nom (unique) de l'utilisateur courant. Correspond à Nom dans la base de données.
     */
    private String recepteur;
    /**
     * Mot de passe de l'utilisateur courant. Correspond à MDP dans la base de données.
     */
    private int relation;

    /**
     * Constructeur de l'utilisateur. Initialise une instance de l'utilisateur présent dans la base
     * de données.
     *
     * @note Ce constructeur est privé (donc utilisable uniquement depuis cette classe). Cela permet
     * d'éviter d'avoir deux instances différentes d'un même utilisateur.
     */
    private Ami(String amiEmet, String amiRecept, int amiRel) {

        this.emetteur = amiEmet;
        this.recepteur = amiRecept;
        this.relation= amiRel;
    }


    public static ArrayList<Ami> getAmiConnected() {
        // Récupération du  SQLiteHelper et de la base de données.
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();

        // Colonnes à récupérer
        String[] colonnes = {COL_EMET,COL_RECEPT,COL_REL};

        // Requête de selection (SELECT)
        //Cursor cursor = db.query(BDD_TABLE, colonnes, null, null, null, null, null);
        String connectedUser = User.getConnectedUser().getId();
        //Cursor cursor = db.query(BDD_TABLE, colonnes, null, null, null, null, null);
        Cursor cursor = db.rawQuery("SELECT Emetteur AS Amis FROM RELATION WHERE Recepteur =\'"+connectedUser+ "\' AND Relation=1 UNION SELECT Recepteur AS AMIS FROM RELATION WHERE Emetteur =\'" + connectedUser + "\' AND Relation=1",null);
        // Placement du curseur sur la première ligne.
        cursor.moveToFirst();

        // Initialisation la liste des sondages.
        ArrayList<Ami> amis = new ArrayList<>();

        // Tant qu'il y a des lignes.
        while (!cursor.isAfterLast()) {
            // Récupération des informations du sondage pour chaque ligne.
            int amiRel = cursor.getInt(2);
            Log.d("tagCursor",Integer.toString(amiRel));
            String amiEmet = cursor.getString(0);
            Log.d("tagCursor",amiEmet);
            String amiRecept = cursor.getString(1);
            Log.d("tagCursor",amiRecept);
            // Vérification pour savoir s'il y a déjà une instance de ce sondage.
            Ami ami = Ami.amiSparseArray.get(amiRel);
            if (ami == null) {
                // Si pas encore d'instance, création d'une nouvelle instance.
                ami = new Ami(amiEmet, amiRecept, amiRel);
            }
            // Ajout de le questionnaire à la liste.
            amis.add(ami);
            // Passe à la ligne suivante.
            cursor.moveToNext();
        }
        // Fermeture du curseur et de la base de données.
        cursor.close();
        db.close();

        return amis;
    }

    public static void reverseOrder() {
        if (Ami.order.equals("ASC")) {
            Ami.order = "DESC";
        } else {
            Ami.order = "ASC";
        }
    }

    /**
     * Fournit l'identifiant de l'utilisateur courant.
     */
    public String getEmet() {

        return emetteur;
    }

    /**
     * Fournit le nom de l'utilisateur courant.
     */
    public String getRecept() {

        return recepteur;
    }

    /**
     * Fournit le prénom de l'utilisateur courant.
     */
    public int getRel() {

        return relation;
    }

}
