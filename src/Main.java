
public class Main {

    public static void main(String[] args) {

        Server server = new Server();
        server.initialize(1050);
        server.start();
        System.out.println("Server started listening on port : 1050");
    }
}
