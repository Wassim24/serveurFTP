import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class ServerTest {

    @Test
    public void testRunningServer() {
        Server server = new Server();
        server.initialize(1050);
        assertNotNull(server.getServerSocket());
        assertNotNull(server.getSocket());
        assertEquals(server.getServerSocket().getLocalPort(), 1050);
    }
}