import java.io.PrintWriter;

public class FtpRequest {

    public FtpRequest() {}

    public void processRequest(String request){

        String command = request.substring(0, 4);

        switch (command) {
            case "USER" : processUSER(request);
                break;
            case "PASS" : processPASS(request);
                break;
            case "RETR" : processRETR(request);
                break;
            case "STOR" : processSTOR(request);
                break;
            case "LIST" : processLIST(request);
                break;
            case "QUIT" : processQUIT(request);
                break;
            default: break;
        }

    }

    private String processUSER (String request){

        System.out.println("processUSER");

        return "TRUE";
    }

    private String processPASS (String request){

        System.out.println("processPASS");

        return "TRUE";
    }

    private String processRETR (String request){

        System.out.println("processRETR");

        return "TRUE";
    }

    private String processSTOR (String request){

        System.out.println("processSTOR");

        return "TRUE";
    }

    private String processLIST (String request){

        System.out.println("processLIST");

        return "TRUE";
    }

    private String processQUIT (String request){

        System.out.println("processQUIT");

        return "TRUE";
    }

}
