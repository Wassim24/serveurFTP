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

        return "TRUE";
    }

    private String processPASS (String request){

        return "TRUE";
    }

    private String processRETR (String request){

        return "TRUE";
    }

    private String processSTOR (String request){

        return "TRUE";
    }

    private String processLIST (String request){

        return "TRUE";
    }

    private String processQUIT (String request){

        return "TRUE";
    }

}
