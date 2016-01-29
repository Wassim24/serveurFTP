import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {

        /** Le serveur ecoute sur le port 1024 **/
        ServerSocket server;

        try {

            server = new ServerSocket(1050);
            Thread serverThread = new Thread(new AcceptClients(server));
            serverThread.start();

        } catch (IOException e) {e.printStackTrace();}

    }
}

class AcceptClients implements Runnable {

    private ServerSocket server;
    private Socket client;
    private int nbClients = 0;

    public AcceptClients(ServerSocket server) {
        this.server = server;
    }

    @Override
    public void run() {

        while (true) {

            /** Le serveur attend de recevoir une connexion et l'accepte **/
            System.out.println("Le serveur attend une connexion...");
            try {

                client = server.accept();
                nbClients++;


                /**  Connexion d'un client, réception de la requete, et traitements **/
                System.out.println("Le nombre de clients : " + nbClients);
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String request = in.readLine();

                FtpRequest handler = new FtpRequest();
                handler.processRequest(request);

            } catch (IOException e) { e.printStackTrace(); }

        }


    }
}