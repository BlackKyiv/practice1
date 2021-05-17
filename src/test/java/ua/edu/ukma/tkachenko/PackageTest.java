package ua.edu.ukma.tkachenko;

import com.google.common.primitives.UnsignedLong;
import org.junit.Assert;
import org.junit.Test;

public class PackageTest {

    private static final String TEST_MESSAGE = "Test message with some text 1244 &*()/?";
    private static final String READY_TEXT = "Ready text 1244 &*()/?";
    private static final byte[] BYTES = {19, 5, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 32, -50, 34, 0, 0, 0, 2, 0, 0, 0, 0, 79, 70, -96, 35, 94, 108, 30, -91, 57, 26, 79, -91, -117, 92, 26, -122, -97, -22, 31, 121, 114, -110, -76, -13, 58, -69, 72, -126, 72, 77, 86, -120, -115, -110};

    private static final Message PREPARED_MESSAGE = new Message(5, 4, TEST_MESSAGE);
    private static final Package PREPARED_PACKAGE = new Package((byte) 6, UnsignedLong.ONE, PREPARED_MESSAGE);


    @Test
    public void testPackage() throws Exception {
        Package pack = new Package(BYTES);
        String res = new String(pack.getMessage().getMessage());
        Assert.assertEquals(READY_TEXT, res);
    }


    @Test
    public void selfTest() throws Exception {
        Package pack = new Package(PREPARED_PACKAGE.getBytes());

        Assert.assertEquals(PREPARED_PACKAGE.getBSrc(), pack.getBSrc());
        Assert.assertEquals(PREPARED_PACKAGE.getBPktId(), pack.getBPktId());
        Assert.assertEquals(PREPARED_PACKAGE.getWLen(), pack.getWLen());

        Assert.assertEquals(PREPARED_PACKAGE.getWCrc16_1(), pack.getWCrc16_1());
        Assert.assertEquals(PREPARED_PACKAGE.getWCrc16_2(), pack.getWCrc16_2());

        Assert.assertEquals(PREPARED_MESSAGE.getCType(), pack.getMessage().getCType());
        Assert.assertEquals(PREPARED_MESSAGE.getUserId(), pack.getMessage().getUserId());
        Assert.assertArrayEquals(PREPARED_MESSAGE.getMessage(), pack.getMessage().getMessage());

        Assert.assertEquals(TEST_MESSAGE, new String(pack.getMessage().getMessage()));
    }
}
