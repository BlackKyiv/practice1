package ua.edu.ukma.tkachenko;

import lombok.Data;

import java.nio.ByteBuffer;

@Data
public class Message {

    private byte[] message;
    private Integer cType;
    private Integer userId;

    public Message() {
    }

    public Message(Integer userId, Integer cType, String message) {
        this.cType = cType;
        this.userId = userId;
        this.message = message.getBytes();
    }

    public void encode(){
        message = Encyptor.encrypt(message);
    }

    public void decode(){
        message = Encyptor.decrypt(message);
    }

    public byte[] getBytes() {
        return ByteBuffer.allocate(getBytesLength())
                .putInt(cType)
                .putInt(userId)
                .put(message)
                .array();
    }

    public Integer getBytesLength(){
        return Integer.BYTES + Integer.BYTES + message.length;
    }


}
