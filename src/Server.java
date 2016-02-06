import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

    public AcceptClients(ServerSocket server) {
        this.server = server;
    }

    @Override
    public void run() {

        while (true) {

            /** Le serveur attend de recevoir une connexion et l'accepte **/
            try {

                client = server.accept();
                new Thread(new FtpRequest(client)).start();

            } catch (IOException e) { e.printStackTrace(); }

        }
    }
}