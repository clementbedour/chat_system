import java.net.*;
import java.io.*;

public class Singleton{
    //Attribut statique privé
    private static Singleton instance;
    public String name;

    // 2. Constructeur
    private Singleton(String name) throws IOException {
        this.name = name;
    }


    // 3. Méthode d'accès
    public static Singleton getInstance(String name) throws IOException {
        if (instance == null) {
            instance = new Singleton(name);
        }else {
            throw new Exception("contact déja exist !!!  ");
        }
        return instance;
    }

    public static void main(String[] args) throws IOException{
        System.out.println("création d'user : ");
        Singleton s1 = Singleton.getInstance("Alice");
        Singleton s2 = Singleton.getInstance("Bob");
        System.out.println(s1.name);
        System.out.println(s2.name);

    }
}



