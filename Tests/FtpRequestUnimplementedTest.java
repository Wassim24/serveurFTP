import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class FtpRequestUnimplementedTest extends FtpRequestTest {

    @Test
    public void unimplementedSuccess() {
        assertEquals(this.client.receive(), "220 Connection established on 1050, login please.");
        this.client.send("FAKECOMMAND fakedata");
        assertEquals(this.client.receive(), "502 Unrecognized command.");
    }

    @Test
    public void unimplementedFail() {
        assertEquals(this.client.receive(), "220 Connection established on 1050, login please.");
        this.client.send("USER user");
        assertNotEquals(this.client.receive(), "502 Unrecognized command.");
    }
}
