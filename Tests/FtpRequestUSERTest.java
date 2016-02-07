import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FtpRequestUSERTest extends FtpRequestTest {

    @Test
    public void USERTestSuccess() {
        assertEquals(this.client.receive(), "220 Connection established on 1050, login please.");
        this.client.send("USER user");
        assertEquals(this.client.receive(), "331 User name okay, need password.");
    }

    @Test
    public void USERTestFail() {
        assertEquals(this.client.receive(), "220 Connection established on 1050, login please.");
        this.client.send("USER fakeuser");
        assertEquals(this.client.receive(), "530 Username is incorrect.");
    }
}
