package ua.edu.ukma.tkachenko;

import com.github.snksoft.crc.CRC;
import com.google.common.primitives.UnsignedLong;
import lombok.Data;

import java.nio.ByteBuffer;

import static com.github.snksoft.crc.CRC.Parameters.CRC16;

@Data
public class Package {
    private static final int part1Length = Byte.BYTES + Byte.BYTES + Long.BYTES + Integer.BYTES;

    private Message message;

    private Byte bSrc;
    private UnsignedLong bPktId;
    private Integer wLen;

    private Short wCrc16_1;
    private Short wCrc16_2;

    private static final Byte bMagic = 0x13;


    public Package(Byte bSrc, UnsignedLong bPktId, Message message) {
        this.bSrc = bSrc;
        this.bPktId = bPktId;
        this.message = message;
        this.wLen = message.getMessage().length;
    }

    public Package(byte[] bytes) throws Exception {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        Byte magicExp = byteBuffer.get();
        if (!magicExp.equals(bMagic)) throw new Exception("Unexpected magic byte");

        bSrc = byteBuffer.get();
        bPktId = UnsignedLong.fromLongBits(byteBuffer.getLong());
        wLen = byteBuffer.getInt();

        wCrc16_1 = byteBuffer.getShort();

        short wCrc16_1_test = (short) CRC.calculateCRC(CRC16, ByteBuffer.allocate(part1Length)
                .put(bMagic)
                .put(bSrc)
                .putLong(bPktId.longValue())
                .putInt(wLen)
                .array());

        if (wCrc16_1_test != wCrc16_1) {
            throw new Exception("Wrong wCrc16_1!");
        }

        message = new Message();
        message.setCType(byteBuffer.getInt());
        message.setUserId(byteBuffer.getInt());
        byte[] messageBody = new byte[wLen];
        byteBuffer.get(messageBody);
        message.setMessage(messageBody);

        wCrc16_2 = byteBuffer.getShort();

        short wCrc16_2_test = (short) CRC.calculateCRC(CRC16, ByteBuffer.allocate(message.getBytesLength())
                .put(message.getBytes())
                .array());

        if (wCrc16_2_test != wCrc16_2) {
            throw new Exception("Wrong wCrc16_2!");
        }

        message.decode();
    }


    public byte[] getBytes() {
        message.encode();
        wLen = message.getMessage().length;

        byte[] part1 = ByteBuffer.allocate(part1Length)
                .put(bMagic)
                .put(bSrc)
                .putLong(bPktId.longValue())
                .putInt(wLen)
                .array();

        wCrc16_1 = (short) CRC.calculateCRC(CRC16, part1);

        int part2Length = message.getBytesLength();
        byte[] part2 = ByteBuffer.allocate(part2Length)
                .put(message.getBytes())
                .array();

        wCrc16_2 = (short) CRC.calculateCRC(CRC16, part2);

        message.decode();

        return ByteBuffer.allocate(part1Length + Short.BYTES + part2Length + Short.BYTES)
                .put(part1)
                .putShort(wCrc16_1)
                .put(part2)
                .putShort(wCrc16_2)
                .array();
    }
}
