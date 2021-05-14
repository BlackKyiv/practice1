package ua.edu.ukma.ipz;

import org.apache.commons.codec.digest.DigestUtils;

public class App {

    public static void main(String[] args) {



        System.out.println(Integer.BYTES);

    }

    public static String sha256hex(String input) {
        return DigestUtils.sha256Hex(input);
    }

}