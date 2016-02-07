import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class Server extends Thread{

    private ServerSocket server;
    private Socket client;

    public void initialize(int port) {

        try {
            server = new ServerSocket(port);
            client = new Socket();
        }
        catch (IOException e) { e.printStackTrace(); }
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

    public ServerSocket getServerSocket() {return server;}

    public void setServerSocket(ServerSocket server) {this.server = server;}

    public Socket getSocket() {return client;}

    public void setSocket(Socket client) {this.client = client;}
}