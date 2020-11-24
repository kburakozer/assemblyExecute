package com.executable;

public class BitwiseOperations {

    public static int xor(String register, String operand) {
        // assigns the length of string register to variable named size
        int size = register.length();
        // int variable "i" will be used as index in xor operation
        int i = 0;
        // a new variable is initialized to hold the final value of the xor operation
        String newOperand = "";
        // makes the operation
        while (i < size) {
            if ((register.charAt(i) == '0' && operand.charAt(i) == '1') ||
                    register.charAt(i) == '1' && operand.charAt(i) == '0') {
                newOperand = newOperand + 1;
            } else {
                newOperand = newOperand + 0;
            }
            i++;
        }
        // converts the string value to integer value
        int newNum = Converter.binToDecimal(newOperand);
        // returns the integer value.
        return newNum;
    }


    public static int and(String register, String operand) {
        // assigns the length of string register to variable named size
        int size = register.length();
        // int variable "i" will be used as index in and operation
        int i = 0;
        // a new variable is initialized to hold the final value of the and operation
        String newOperand = "";
        // makes the operation
        while (i < size) {
            if ((register.charAt(i) == '1' && operand.charAt(i) == '1')) {
                newOperand = newOperand + 1;
            } else {
                newOperand = newOperand + 0;
            }
            i++;
        }
        // converts the string value to integer value
        int newNum = Converter.binToDecimal(newOperand);
        // returns the integer value.
        return newNum;
    }


    public static int or(String register, String operand) {
        // assigns the length of string register to variable named size
        int size = register.length();
        // int variable "i" will be used as index in or operation
        int i = 0;
        // a new variable is initialized to hold the final value of the or operation
        String newOperand = "";
        // makes the operation
        while (i < size) {
            if ((register.charAt(i) == '0' && operand.charAt(i) == '0')) {
                newOperand = newOperand + 0;
            } else {
                newOperand = newOperand + 1;
            }
            i++;
        }
        // converts the string value to integer value
        int newNum = Converter.binToDecimal(newOperand);
        // returns the integer value.
        return newNum;

    }


    public static int not(String operand) {
        // a new variable is initialized to hold the final value of the not operation
        String newOperand = "";
        // for loop is used to take complement of each element in the binary string
        for(int i=0; i<operand.length(); i++){
            char c = operand.charAt(i);
            if(c == '0'){
                newOperand = newOperand + 1;
            } else{
                newOperand = newOperand + 0;
            }
        }
        // converts the string value to integer value
        int result = Converter.binToDecimal(newOperand);
        // returns the integer value.
        return result;

    }


    public static int shiftL(String operand) {
        // a new variable is initialized to hold the final value of the shift left operation
        String newOperand = "0";
        // assigns the length of string operand to variable named size
        int size = operand.length();
        // for loop is used to make shift left operation
        for (int i = size - 1; i > 0; i--) {
            char tmp = operand.charAt(i);
            newOperand = tmp + newOperand;
        }
        // converts the string value to integer value
        int result = Converter.binToDecimal(newOperand);
        // returns the integer value.
        return result;
    }


    public static int shiftR(String operand) {
        // a new variable is initialized to hold the final value of the shift left operation
        String newOperand = "0";
        // assigns the length of string operand to variable named size
        int size = operand.length();
        // for loop is used to make shift left operation
        for (int i = 0 ; i < size - 1; i++) {
            char tmp = operand.charAt(i);
            newOperand =  newOperand+ tmp;
        }
        // converts the string value to integer value
        int result = Converter.binToDecimal(newOperand);
        // returns the integer value.
        return result;
    }


    public static int cmp(String register, String operand) {
        int regNum = Converter.binToDecimal(register);
        int opNum = Converter.binToDecimal(operand);
        return regNum - opNum;

    }



}
