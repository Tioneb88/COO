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
        //User.userSparseArray.put(userId, this);
        //User.userSparseArray.put(userMail, this);
    }
    /**
     * Fournit la liste des amis.
     */
    public static ArrayList<String> getIdAmis() {
        // Récupération du  SQLiteHelper et de la base de données. On ne récupère pas la photo et le
        // meilleur ami de l'utilisateur car ce n'est pas ce qui le caratérise le mieux.
        SQLiteDatabase db = MySQLiteHelper.get().getReadableDatabase();
        // Colonnes à récupérer
        String[] colonnes = {COL_EMET,COL_RECEPT,COL_REL};

        // Requête de selection (SELECT)
        Cursor cursor = db.query(BDD_TABLE, colonnes, null, null, null, null, null);

        // Placement du curseur sur la première ligne.
        cursor.moveToFirst();

        // Initialisation la liste des utilisateurs.
       // ArrayList<Ami> amis = new ArrayList<>();
        ArrayList<String> listamis = new ArrayList<>();

        // Tant qu'il y a des lignes.
        while (!cursor.isAfterLast()) {
            // Récupération des informations de l'utilisateur pour chaque ligne.
            String amiEmet = cursor.getString(0);
            String amiRecept = cursor.getString(1);
            int amiRel = cursor.getInt(2);
            if (amiRel==1){
                if (amiEmet==User.getConnectedUser().getId()){
                    listamis.add(amiRecept);
                }
                else {
                    listamis.add(amiEmet);
                }
            }
            // Ajout de l'utilisateur à la liste.


            // Passe à la ligne suivante.
            cursor.moveToNext();
        }

        // Fermeture du curseur et de la base de données.
        cursor.close();
        db.close();
        return listamis;
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
