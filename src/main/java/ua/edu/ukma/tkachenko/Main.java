package ua.edu.ukma.tkachenko;


import java.util.Arrays;

public class Main {

    public static void main(String[] args){

        System.out.println(Arrays.toString(Encyptor.encrypt("Some random text with 1234 &".getBytes())));

    }
}
