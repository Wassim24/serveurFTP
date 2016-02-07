import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;
    private InetAddress address;

    public Client(int port) {

        try {

            this.address = InetAddress.getByName("localhost");
            this.socket = new Socket(this.address, port);
            this.out = new PrintWriter(this.socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

        } catch (Exception e) {e.printStackTrace();}

    }

    public void send(String request) {

        this.out.println(request);
    }

    public String receive() {

        String response = "";

        try {response = this.in.readLine();}
        catch (IOException e) {e.printStackTrace();}

        return response;
    }

    public void close() {

        try {
            this.out.close();
            this.in.close();
            socket.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}
