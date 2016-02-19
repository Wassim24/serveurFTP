import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class FtpRequestLISTTest extends  FtpRequestTest{

    @Test
    public void LISTTestSuccess() {
        assertEquals(this.client.receive(), "220 Connection established on 1050, login please.");
        this.client.send("USER user");
        assertEquals(this.client.receive(), "331 User name okay, need password.");
        this.client.send("PASS pass");
        assertEquals(this.client.receive(), "230 User logged in, proceed.");
        this.client.send("LIST");
        assertEquals(this.client.receive(), "150 Files OK, open Data Connection in ASCII.");
    }


    @Test
    public void LISTTestFail() {
        assertEquals(this.client.receive(), "220 Connection established on 1050, login please.");
        this.client.send("LIST");
        assertEquals(this.client.receive(), "530 Not logged in.");
    }

}
