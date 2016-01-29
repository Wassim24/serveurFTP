import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {

        /** Le serveur ecoute sur le port 1024 **/
        ServerSocket server = new ServerSocket(1050);
        System.out.println("Le serveur a démarré et écoute sur 1050");


        /** Le serveur attend de recevoir une connexion et l'accepte **/
        System.out.println("Le serveur attend une connexion...");
        Socket client = server.accept();

        /**  Connexion d'un client, réception de la requete, et traitements **/
        System.out.println("Un client s'est connecté");
        BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream()));
        String request = in.readLine();

        FtpRequest handler = new FtpRequest();
        handler.processRequest(request);
    }
}
