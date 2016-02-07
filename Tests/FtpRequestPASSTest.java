import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FtpRequestPASSTest extends FtpRequestTest {

    @Test
    public void PASSTestSuccess() {
        assertEquals(this.client.receive(), "220 Connection established on 1050, login please.");
        this.client.send("USER user");
        assertEquals(this.client.receive(), "331 User name okay, need password.");
        this.client.send("PASS pass");
        assertEquals(this.client.receive(), "230 User logged in, proceed.");

    }

    @Test
    public void PASSTestFail() {
        assertEquals(this.client.receive(), "220 Connection established on 1050, login please.");
        this.client.send("USER user");
        assertEquals(this.client.receive(), "331 User name okay, need password.");
        this.client.send("PASS fakepass");
        assertEquals(this.client.receive(), "530 Error in password.");

    }
}
