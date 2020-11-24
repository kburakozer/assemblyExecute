package com.executable;

public class Converter {

    public static int binToDecimal(String s){
        // creates a variable to be used as exponent
        int j = 0;
        // variable is used for holding the result of the operation
        int result = 0;
        for(int i=s.length()-1; i>=0; i--) {
            // parses each element starting from least significant bit to integer
            int n = Integer.parseInt(""+s.charAt(i));
            // takes the exponent of the element according to its place
            int tmp = n * (int) Math.pow(2, j);
            result += tmp;
            j++;
        }
        return result;
    }


    public static String decimalToBinary(int decimal){
        // copies the valus of decimal to another valuable number
        int number = decimal;
        // holds the resulting value in string
        String s = "";
        // makes the conversion
        while (number != 1) {
            int num = number % 2;
            s = num + s;
            number /= 2;
        }
        if (number == 1) {
            s = number + s;
        }
        // returns the string.
        return s;

       }


}
