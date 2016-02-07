import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import static org.junit.Assert.*;

public class FtpRequestTest {

    protected static Server server;
    protected Client client;

    @BeforeClass
    public static void beforeSetUp() throws Exception {
        server = new Server();
        server.initialize(1050);
        server.start();
    }

    @AfterClass
    public static void afterTearDown() throws Exception {
        server.stop();
    }

    @Before
    public void setUp() throws Exception {
        client = new Client(1050);
    }

    @After
    public void tearDown() throws Exception {
        this.client.close();
    }
}