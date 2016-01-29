import java.io.PrintWriter;

public class FtpRequest {

    public FtpRequest() {}

    public String processUSER (PrintWriter inputRequest){ return "TRUE"; }
    public String processPASS (PrintWriter inputRequest){ return "TRUE"; }
    public String processRETR (PrintWriter inputRequest){ return "TRUE"; }
    public String processSTOR (PrintWriter inputRequest){ return "TRUE"; }
    public String processLIST (PrintWriter inputRequest){ return "TRUE"; }
    public String processQUIT (PrintWriter inputRequest){ return "TRUE"; }
}
