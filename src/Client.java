import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {

    public static void main(String[] zero) {

        Socket socket;

        try {

            socket = new Socket(InetAddress.getLocalHost(),1050);

            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println("QUIT wassim");
            out.flush();

            socket.close();

        }catch (UnknownHostException e) {

            e.printStackTrace();
        }catch (IOException e) {

            e.printStackTrace();
        }
    }

}