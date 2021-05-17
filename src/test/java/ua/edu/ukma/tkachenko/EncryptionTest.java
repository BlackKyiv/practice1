package ua.edu.ukma.tkachenko;


import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class EncryptionTest {
    private static final String ORIGINAL = "Some random text with 1234 &";
    private static final byte[] ENCRYPTED_MESSAGE = {112, -60, 6, -23, -124, 78, -113, -85, -117, 80, 65, 40, 49, 104, 76, 48, -78, -127, -71, -12, -41, -94, 21, 22, -40, -32, -76, 106, -92, -14, 41, 17};


    @Test
    public void testEncryption() {
        byte[] encryptedRes = Encyptor.encrypt(ORIGINAL.getBytes());
        Assert.assertFalse(Arrays.equals(new String(encryptedRes).getBytes(), ORIGINAL.getBytes()));
    }

    @Test
    public void testDecryption() {
        Assert.assertFalse(Arrays.equals(ENCRYPTED_MESSAGE, ORIGINAL.getBytes()));
        byte[] decrypted = Encyptor.decrypt(ENCRYPTED_MESSAGE);
        Assert.assertArrayEquals(ORIGINAL.getBytes(), decrypted);
        Assert.assertEquals(ORIGINAL, new String(decrypted));
    }

    @Test
    public void selfTest() {
        Assert.assertEquals(ORIGINAL, new String(Encyptor.decrypt(Encyptor.encrypt(ORIGINAL.getBytes()))));
    }
}
