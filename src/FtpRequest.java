import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

public class FtpRequest implements Runnable{

    private String command;
    private String data;
    private String user;
    private String pass;
    private String rootPath;
    private String currentPath;
    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;
    private Socket dataSocket;
    private boolean auth;
    private boolean finishedCom;
    private String address;
    private int port;
    private boolean passive;
    private ServerSocket passiveMode;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;

    public FtpRequest(Socket socket)
    {
        this.socket = socket;
        this.auth = false;
        this.finishedCom = false;
        this.address = this.socket.getLocalAddress().getHostAddress();
        this.port = 1050;

        try {

            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.out = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()), true);
            this.out.println("220 Connection established on 1050, login please.");

        } catch (IOException e) {e.printStackTrace();}

        this.user = "user";
        this.pass = "pass";
        this.currentPath = this.rootPath = System.getProperty("user.home");
    }

    public void processRequest(){

        try {

            for (String request = this.in.readLine(); request != null; request = this.in.readLine()) {

                if (request.split(" ").length > 1) {
                    this.command = request.split(" ")[0];
                    this.data = request.split(" ")[1];
                } else {
                    this.command = request.split(" ")[0];
                    this.data = "";
                }

                switch (command) {

                    case "USER":
                        processUSER();
                        break;
                    case "PASS":
                        processPASS();
                        break;
                    case "RETR":
                        processRETR();
                        break;
                    case "STOR":
                        processSTOR();
                        break;
                    case "LIST":
                        processLIST();
                        break;
                    case "PWD":
                        processPWD();
                        break;
                    case "TYPE":
                        processTYPE();
                        break;
                    case "PORT":
                        processPORT();
                        break;
                    case "PASV":
                        processPASV();
                        break;
                    case "SYST":
                        processSYST();
                        break;
                    case "CWD":
                        processCWD();
                        break;
                    case "CDUP":
                        processCDUP();
                        break;

                    default:
                        this.out.println("502 Unrecognized command.");
                        break;
                }

                if(this.command.equals("QUIT")) {
                    finishedCom = true;
                    break;
                }
            }
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void processCDUP() {

        Path tempPath = Paths.get(this.currentPath);

        if(tempPath.getParent().equals(Paths.get(this.rootPath).getParent()))
            this.out.println("550 Access Denied !");

        this.currentPath  = tempPath.getParent().toString();
        processPWD();
    }

    private void processCWD() {

        Path tempPath = Paths.get(this.currentPath);

        if(!tempPath.resolve(this.data).startsWith(this.rootPath))
            this.out.println("550 Access Denied.");

        if (!Files.exists(tempPath.resolve(this.data)))
            this.out.println("550 Directory or file doest not exist.");

        this.currentPath = tempPath.resolve(this.data).toString();
        processPWD();
    }

    private void processSYST() { this.out.println("215 UNIX Type: L8."); }

    private void processUSER (){

        if( data.equals(this.user) && !this.auth )
        {
            this.auth = true;
            this.out.println("331 User name okay, need password.");
        }
        else
            this.out.println("530 Username is incorrect.");

    }

    private void processPASS (){

        if( data.equals(this.pass) && this.auth )
            this.out.println("230 User logged in, proceed.");
        else
            this.out.println("530 Error in password.");
    }

    private void processLIST () {

        File folder = new File(this.currentPath);
        File[] listFiles = folder.listFiles();
        String currentFile = "";

        try {

            if(this.passive)
                this.dataSocket = this.passiveMode.accept();
            else
                this.dataSocket = new Socket(InetAddress.getByName(this.address), this.port);

            this.dataIn = new DataInputStream(this.dataSocket.getInputStream());
            this.dataOut = new DataOutputStream(this.dataSocket.getOutputStream());

            if(this.auth) {
                this.out.println("150 Files OK, open Data Connection in ASCII.");

                for (int i = 0; i < listFiles.length; i++) {

                    if (!listFiles[i].isHidden())
                        if (listFiles[i].isFile())
                            currentFile = "+s" + listFiles[i].length()+",m"+listFiles[i].lastModified()/1000+",\011"+listFiles[i].getName()+"\015\012";
                        else if (listFiles[i].isDirectory())
                            currentFile = "+/,m"+listFiles[i].lastModified()/1000+",\011"+listFiles[i].getName()+"\015\012";

                    this.dataOut.writeBytes(currentFile);
                    this.dataOut.flush();
                    currentFile = "";
                }

                this.dataSocket.close();
                this.out.println("226 Closing data connection. Requested file action successful");
            }
            else this.out.println("530 Not logged in.");
        } catch (IOException e) {
            this.out.println("425 Can't open data connection.");
            e.printStackTrace();
        }

    }

    private void processPWD (){
        this.out.println("257 "+this.currentPath);
    }

    private void processRETR (){

        Path fileToTransfer = Paths.get(this.currentPath);

        try {
            if (this.auth) {
                byte[] dataArray = Files.readAllBytes(fileToTransfer.resolve(this.data));

                this.dataSocket = new Socket(InetAddress.getByName(this.address), this.port);

                this.dataIn = new DataInputStream(this.dataSocket.getInputStream());
                this.dataOut = new DataOutputStream(this.dataSocket.getOutputStream());

                this.out.println("150 Files OK, open Data Connection in ASCII.");

                for (int i = 0; i < dataArray.length; i++) {
                    this.dataOut.writeByte(dataArray[i]);
                    this.dataOut.flush();
                }
                this.dataSocket.close();
                this.out.println("226 Closing data connection. Requested file action successful.");
            }
            else
                this.out.println("530 Not logged in.");

            } catch (IOException e) {this.out.println("550 Can't read file or Can't open data connection.");}
    }

    private void processSTOR (){
        Path fileToTransfer = Paths.get(this.currentPath);

        try {
            if (this.auth) {

                this.dataSocket = new Socket(InetAddress.getByName(this.address), this.port);

                this.dataIn = new DataInputStream(this.dataSocket.getInputStream());
                this.dataOut = new DataOutputStream(this.dataSocket.getOutputStream());

                this.out.println("150 Files OK, open Data Connection in ASCII.");

                System.out.println(fileToTransfer.resolve(this.data).toString());
                FileOutputStream fos = new FileOutputStream(fileToTransfer.resolve(this.data).toString());
                int dataRead;

                while( (dataRead = this.dataIn.read()) != -1)
                {
                    fos.write(dataRead);
                }

                fos.close();
                this.dataIn.close();
                this.dataSocket.close();
                this.out.println("226 Closing data connection. Requested file action successful.");
            }
            else
                this.out.println("530 Not logged in.");

        } catch (IOException e) {this.out.println("550 Can't read file or Can't open data connection.");}

    }

    private void processTYPE (){
        this.out.println("200 Command Okay");
    }

    private void processPORT() {

        String newHostPort[] = this.data.split(",");

        this.address = newHostPort[0] + "." + newHostPort[1] + "." + newHostPort[2] + "." + newHostPort[3];
        this.port = Integer.parseInt(newHostPort[4]) * 256 + Integer.parseInt(newHostPort[5]);

        this.passive = false;

        this.out.println("200 Host and Port setup.");

    }

    private void processPASV() throws IOException {

        this.passive = true;

        this.passiveMode = new ServerSocket(0);

        String[] ip = this.socket.getLocalAddress().getHostAddress().split("\\.");
        String args = ip[0] + "," + ip[1] + "," + ip[2] + "," + ip[3] + "," + (this.port / 256) + "," + (this.port % 256);

        this.out.println("227 Entering passive mode " + args);
    }

    private void processQUIT (){
        try {this.socket.close(); this.in.close(); this.out.close();} catch (IOException e) {e.printStackTrace();}
    }


    @Override
    public void run() {
        while(true)
        {
            if(finishedCom)
                break;
            this.processRequest();
        }
        processQUIT();
    }
}
