package org.example.ecommerce;

public class RandomStr{
    public static String RandomString(int length){
        String pool = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < length; i++) {
            int idx = random.nextInt(pool.length());
            sb.append(pool.charAt(idx));
        }
        return sb.toString();
    }
}