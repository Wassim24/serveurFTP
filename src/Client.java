import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

public class Client {

    public static void main(String[] zero) {

        Socket socket;

        try {

            socket = new Socket(InetAddress.getLocalHost(),1050);

            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println("LIST");
            out.flush();


        } catch (IOException e) {e.printStackTrace();}
    }

}