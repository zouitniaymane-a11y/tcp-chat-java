import java.io.*;
import java.net.*;

public class ChatServeur {
    public static void main(String[] args) throws Exception {
        int port = 8080;
        BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));

        try (ServerSocket serveur = new ServerSocket(port)) {
            while (true) {
                System.out.println("En attente d'un client sur le port " + port + "...");
                Socket client = serveur.accept();
                System.out.println("Client connecte : " + client.getInetAddress().getHostAddress());

                BufferedReader entree = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter sortie = new PrintWriter(client.getOutputStream(), true);

                // Thread qui affiche les messages recus du telephone
                Thread reception = new Thread(() -> {
                    try {
                        String ligne;
                        while ((ligne = entree.readLine()) != null) {
                            System.out.println("Telephone : " + ligne);
                        }
                    } catch (IOException e) {
                    }
                });
                reception.start();

                // Tant que le client est connecte, ce que tu tapes lui est envoye
                while (reception.isAlive()) {
                    if (clavier.ready()) {
                        sortie.println(clavier.readLine());
                    } else {
                        Thread.sleep(100);
                    }
                }

                System.out.println("Client deconnecte.");
                client.close();
            }
        }
    }
}
