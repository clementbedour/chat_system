import java.net.*;
import java.io.*;
import java.util.*;

public class Pseudo {
    // 1. hashset pour éviter le doublon de pseudo
    private static HashSet<String> pseudos = new HashSet<>();
    public String pseudo;

    // 2. Le constructeur
    public Pseudo(String pseudo) throws Exception {
        // On vérifie d'abord si ce pseudo est déjà dans notre mémoire globale
        if (pseudos.contains(pseudo)) {
            throw new Exception("Erreur : le pseudo '" + pseudo + "' existe déjà dans la liste !");
        }

        // Si tout est ok, on enregistre le pseudo
        this.pseudo = pseudo;
        pseudos.add(pseudo); // On l'ajoute à la liste globale pour bloquer les prochains
    }

    // test
    public static void main(String[] args) {
        System.out.println("Création des utilisateurs : ");

        try {
            // Création du 1er ami
            Pseudo p1 = new Pseudo("Alice");
            System.out.println("Créé : " + p1.pseudo);

            // Création du 2ème ami
            Pseudo p2 = new Pseudo("Bob"); 
            System.out.println("Créé : " + p2.pseudo);

            // Tentative de créer un doublon
            System.out.println("\nTentative de création d'un autre Bob...");
            Pseudo p3 = new Pseudo("Bob"); // Va déclencher l'exception

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}



