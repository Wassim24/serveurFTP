import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FtpRequestCWDTest extends FtpRequestTest {

    @Test
    public void CWDTestSucces()
    {
        assertEquals(this.client.receive(), "220 Connection established on 1050, login please.");
        this.client.send("USER user");
        assertEquals(this.client.receive(), "331 User name okay, need password.");
        this.client.send("PASS pass");
        assertEquals(this.client.receive(), "230 User logged in, proceed.");
        this.client.send("CWD Desktop");
        assertEquals(this.client.receive(), "257 /home/wassim/Desktop");
    }

    @Test
    public void CWDTestFail()
    {
        assertEquals(this.client.receive(), "220 Connection established on 1050, login please.");
        this.client.send("USER user");
        assertEquals(this.client.receive(), "331 User name okay, need password.");
        this.client.send("PASS pass");
        assertEquals(this.client.receive(), "230 User logged in, proceed.");
        this.client.send("CWD /");
        assertEquals(this.client.receive(), "550 Access Denied.");
    }
}
