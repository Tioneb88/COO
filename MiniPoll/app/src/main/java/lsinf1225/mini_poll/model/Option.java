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
public class Option {

    private static final String COL_TEXTE = "Texte";
    private static final String COL_IMAGE = "Image";
    private static final String COL_VERACITE = "Veracite";
    private static final String COL_NQUESTIONS = "Nquestions";
    private static final String COL_OPTIONS = "Options";
    private static final String BDD_TABLE = "OPTION";

    /**
     * Contient les instances déjà existantes des questionnaires afin d'éviter de créer deux instances
     * du même questionnaire.
     */
    private static SparseArray<Option> optionSparseArray = new SparseArray<>();

    /**
     * Numéro de la question. Correspond à Nquestions dans la base de données.
     */
    private final int nquestions;
    /**
     * Numéro questionnaire auquel la question se réfère. Correspond à Nquestionnaire dans la base de données.
     */
    private String texte;
    /**
     * Description de la question qui a été crééz. Correspond à Texte dans la base de données.
     */
    private String image;
    /**
     * Ordre dans lequel les réponses sont classées. Correspond à Ordre dans la base de données.
     */
    private int veracite;

    private int noptions;

    /**
     * Constructeur de la question. Initialise une instance de la question présente dans la base
     * de données.
     *
     * @note Ce constructeur est privé (donc utilisable uniquement depuis cette classe). Cela permet
     * d'éviter d'avoir deux instances différentes d'une même question.
     */
    private Option(String nTexte,String nImage, int nVeracite, int nQuestions, int nOptions) {

        this.nquestions = nQuestions;
        this.veracite = nVeracite;
        this.texte = nTexte;
        this.image = nImage;
        this.noptions= nOptions;
        Option.optionSparseArray.put(nquestions, this);
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
    public int getNquestion() {

        return nquestions;
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
    public String getImage() {

        return image;
    }

    public int getVeracite() {

        return veracite;
    }

    public int getNoptions() {

        return noptions;
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
    public static Option get(int noption) {
        Option s = Option.optionSparseArray.get(noption);

        if (s != null) {
            return s;
        }
        return new Option(null,null,0,0,noption);
    }


}

