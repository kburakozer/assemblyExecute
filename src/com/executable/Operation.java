package com.executable;

import java.util.ArrayList;
import java.util.Scanner;

public class Operation{
    // creates a constant to be used for array size
    final int MEMORY_SIZE = 8000;
    // creates an array for memory
    public Object [] memory = new Object[MEMORY_SIZE];
    // creates an array for register
    public Object [] register = new Object[7];
    // holds the value for program counter
    public int pc = 0;
    // holds the value for stack pointer
    public int s = 7999;
    // sign flag
    public int sf = 0;
    // zero flag
    public int zf = 0;
    // carry flag
    public int cf = 0;


    // this method takes each line and assigns them to memory locations
    public void insAllocate(ArrayList<String> list){
        int pointer = 0;
        // takes list of instructions as an argument
        for(String c:list){
            memory[pointer] = c;
            pointer += 3;

        }
    }


    // this method goes to memory and fetches instruction to be executed
    public String [] fetchInstruction(){
        String ins = (String) memory[pc];
        // it increments the pc by 3
        pc += 3;
        // creates a new array to hold instructions
        String [] instruction = new String[3];
        if(ins.length() == 24) {
            instruction[0] = ins.substring(0, 6);
            instruction[1] = ins.substring(6, 8);
            instruction[2] = ins.substring(8, 24);
        } else{
            instruction[0] = ins.substring(0, 6);
            instruction[1] = ins.substring(6, 8);
            instruction[2] = ins.substring(8, 16);
        }
        return instruction;

    }


    public void halt(){
        System.exit(0);
    }


    public void load(String opCode, String addressMode, String operand) {
        // determines the addressing mode
        if (addressMode.equals("00")) {
            if (operand.length() == 16) {
                // uses the converter class to convert string to decimal number
                int number = Converter.binToDecimal(operand);
                // assigns the number within the operand to related register
                register[1] = number;
            } else {
                // if length of the operand is not 16 it prints an error message and halts the program
                System.out.println("Invalid operand");
                System.exit(0);
            }
        } else if (addressMode.equals("01")) {
            // converts the operand to decimal
            int number = Converter.binToDecimal(operand);
            // assigns the value in the correct register to register A
            register[1] = register[number];
        } else if (addressMode.equals("10")) {
            // finds register number
            int number = Converter.binToDecimal(operand);
            // finds memory index stored in the register
            int index = (int) register[number];
            // stores the value in the related memory to register A
            register[1] = memory[index];
        } else if (addressMode.equals("11")) {
            int number = Converter.binToDecimal(operand);
            register[1] = number;
        } else {
            System.out.println("Invalid operation");
            System.exit(0);
        }
    }


    public void store(String opCode, String addressMode, String operand) {
        if (addressMode.equals("01")) {
            int number = Converter.binToDecimal(operand);
            register[number] = register[1];
        } else if (addressMode.equals("10")) {
            int number = Converter.binToDecimal(operand);
            int index = (int) register[number];
            memory[index] = register[1];
        } else if (addressMode.equals("11")) {
            int number = Converter.binToDecimal(operand);
            memory[number] = register[1];
        } else {
            System.out.println("Invalid operation");
            System.exit(0);
        }
    }


    public void add(String opCode, String addressMode, String operand) {
        // assigns the value in register A to operand 1
        int op1 = (int) register[1];
        // operand 2 is initialized to hold the value of other operand.
        int op2 = 0;
        if (addressMode.equals("00")) {
            // uses the converter class to convert string to decimal number
            int number = Converter.binToDecimal(operand);
            // assigns the immediate number to operand 2
            op2 = number;
        } else if (addressMode.equals("01")) {
            // converts the operand to decimal
            int number = Converter.binToDecimal(operand);
            // assigns the immediate number to operand 2
            op2 = (int) register[number];
        } else if (addressMode.equals("10")) {
            // converts the operand to decimal
            int number = Converter.binToDecimal(operand);
            int index = (int) register[number];
            op2 = (int) memory[index];
        } else if (addressMode.equals("11")) {
            // finds memory index stored in the register
            int number = Converter.binToDecimal(operand);
            // assigns the value in the related memory adress
            op2 = (int) memory[number];
        } else {
            System.out.println("Invalid operation");
            System.exit(0);
        }
        int result = op1 + op2;
        // checks certain conditions to set flags
        if(result > 65535){
            cf = 1;
        }
        else{
            cf = 0;
        }
        if(result == 65535){
            sf = 1;
        }else{
            sf = 0;
        }
        if(result == 0){
            zf = 1;
        } else{
            zf = 0;
        }

        if(cf != 1){
            register[1] = result;
        } else{
            System.out.println("Number  is too large to store");
            System.exit(0);
        }

    }


    public void sub(String opCode, String addressMode, String operand) {
        // assigns the value in register A to operand 1
        int op1 = (int) register[1];
        // operand 2 is initialized to hold the value of other operand.
        int op2 = 0;
        if (addressMode.equals("00")) {
            // uses the converter class to convert string to decimal number
            int number = Converter.binToDecimal(operand);
            // assigns the immediate number to operand 2
            op2 = number;
        } else if (addressMode.equals("01")) {
            // converts the operand to decimal
            int number = Converter.binToDecimal(operand);
            // assigns the immediate number to operand 2
            op2 = (int) register[number];
        } else if (addressMode.equals("10")) {
            // converts the operand to decimal
            int number = Converter.binToDecimal(operand);
            int index = (int) register[number];
            op2 = (int) memory[index];
        } else if (addressMode.equals("11")) {
            // finds memory index stored in the register
            int number = Converter.binToDecimal(operand);
            // assigns the value in the related memory adress
            op2 = (int) memory[number];
        } else {
            System.out.println("Invalid operation");
            System.exit(0);
        }
        int result = op1 - op2;
        if(result > 65535){
            cf = 1;
        }else{
            cf = 0;
        }
        if(result == 65535){
            sf = 1;
        } else{
            sf = 0;
        }
        if(result == 0){
            zf = 1;
        } else{
            zf = 0;
        }

        if(cf != 1){
            register[1] = result;
        }
    }


    public void inc(String opCode, String addressMode, String operand) {
        if (addressMode.equals("00")) {
            int n = Converter.binToDecimal(operand);
            n += 1;
            if(n > 65535){
                cf = 1;
            }else{
                cf = 0;
            }
            if(n == 65535){
                sf = 1;
            }else{
                sf = 0;
            }
            if(n == 0){
                zf = 1;
            }else{
                zf = 0;
            }
            register[1] = n;

            } else if (addressMode.equals("01")) {
            int index = Converter.binToDecimal(operand);
            int n = (int) register[index];
            n += 1;
            if(n > 65535){
                cf = 1;
            }else{
                cf = 0;
            }
            if(n == 65535){
                sf = 1;
            }else{
                sf = 0;
            }
            if(n == 0){
                zf = 1;
            }else{
                zf = 0;
            }
            register[index] = n;
        }else if (addressMode.equals("10")) {
            // finds register number
            int number = Converter.binToDecimal(operand);
            // finds memory index stored in the register
            int index = (int) register[number];
            // stores the value in the related memory to register A
            int n = (int) memory[index];
            n += 1;
            if(n > 65535){
                cf = 1;
            } else{
                cf = 0;
            }
            if(n == 65535){
                sf = 1;
            } else{
                sf = 0;
            }
            if(n == 0){
                zf = 1;
            } else{
                zf = 0;
            }
            register[index] = n;
        }else if (addressMode.equals("11")) {
            // finds register number
            int index = Converter.binToDecimal(operand);
            // stores the value in the related memory to register A
            int n = (int) memory[index];
            n += 1;
            if(n > 65535){
                cf = 1;
            }else{
                cf = 0;
            }
            if(n == 65535){
                sf = 1;
            }else{
                sf = 0;
            }
            if(n == 0){
                zf = 1;
            }else{
                zf = 0;
            }
            register[index] = n;

        }else {
            System.out.println("Invalid operation");
            System.exit(0);
        }
    }


    public void dec(String opCode, String addressMode, String operand) {
        if (addressMode.equals("00")) {
            int n = Converter.binToDecimal(operand);
            n -= 1;
            if(n > 65535){
                cf = 1;
            }else{
                cf = 0;
            }
            if(n == 65535){
                sf = 1;
            }else{
                sf = 0;
            }
            if(n == 0){
                zf = 1;
            }else{
                zf = 0;
            }
            register[1] = n;

        } else if (addressMode.equals("01")) {
            int index = Converter.binToDecimal(operand);
            int n = (int) register[index];
            n -= 1;
            if(n > 65535){
                cf = 1;
            }else{
                cf = 0;
            }
            if(n == 65535){
                sf = 1;
            }else{
                sf = 0;
            }
            if(n == 0){
                zf = 1;
            }else{
                zf = 0;
            }
            register[index] = n;
        }else if (addressMode.equals("10")) {
            // finds register number
            int number = Converter.binToDecimal(operand);
            // finds memory index stored in the register
            int index = (int) register[number];
            // stores the value in the related memory to register A
            int n = (int) memory[index];
            n -= 1;
            if(n > 65535){
                cf = 1;
            }else{
                cf = 0;
            }
            if(n == 65535){
                sf = 1;
            }else{
                sf = 0;
            }
            if(n == 0){
                zf = 1;
            }else{
                zf = 0;
            }
            register[index] = n;
        }else if (addressMode.equals("11")) {
            // finds register number
            int index = Converter.binToDecimal(operand);
            // stores the value in the related memory to register A
            int n = (int) memory[index];
            n -= 1;
            if(n > 65535){
                cf = 1;
            }else{
                cf = 0;
            }
            if(n == 65535){
                sf = 1;
            }else{
                sf = 0;
            }
            if(n == 0){
                zf = 1;
            }else{
                zf = 0;
            }
            register[index] = n;

        }else {
            System.out.println("Invalid operation");
            System.exit(0);
        }
    }


    public void mul(String opCode, String addressMode, String operand) {
        // assigns the value in register A to operand 1
        int op1 = (int) register[1];
        // operand 2 is initialized to hold the value of other operand.
        int op2 = 0;
        if (addressMode.equals("00")) {
            // uses the converter class to convert string to decimal number
            int number = Converter.binToDecimal(operand);
            // assigns the immediate number to operand 2
            op2 = number;
        } else if (addressMode.equals("01")) {
            // converts the operand to decimal
            int number = Converter.binToDecimal(operand);
            // assigns the immediate number to operand 2
            op2 = (int) register[number];
        } else if (addressMode.equals("10")) {
            // converts the operand to decimal
            int number = Converter.binToDecimal(operand);
            int index = (int) register[number];
            op2 = (int) memory[index];
        } else if (addressMode.equals("11")) {
            // finds memory index stored in the register
            int number = Converter.binToDecimal(operand);
            // assigns the value in the related memory adress
            op2 = (int) memory[number];
        } else {
            System.out.println("Invalid operation");
            System.exit(0);
        }
        int result = op1 * op2;
        if(result == 0){
            zf = 1;
        }else{
            zf = 0;
        }
        if(result > 65535){
            sf = 1;
        }else{
            sf = 0;
        }
        if(cf != 1){
            register[1] = result;
        }
    }


    public void div(String opCode, String addressMode, String operand) {
        // assigns the value in register A to operand 1
        int op1 = (int) register[1];
        // operand 2 is initialized to hold the value of other operand.
        int op2 = 0;
        if (addressMode.equals("00")) {
            // uses the converter class to convert string to decimal number
            int number = Converter.binToDecimal(operand);
            // assigns the immediate number to operand 2
            op2 = number;
        } else if (addressMode.equals("01")) {
            // converts the operand to decimal
            int number = Converter.binToDecimal(operand);
            // assigns the immediate number to operand 2
            op2 = (int) register[number];
        } else if (addressMode.equals("10")) {
            // converts the operand to decimal
            int number = Converter.binToDecimal(operand);
            int index = (int) register[number];
            op2 = (int) memory[index];
        } else if (addressMode.equals("11")) {
            // finds memory index stored in the register
            int number = Converter.binToDecimal(operand);
            // assigns the value in the related memory adress
            op2 = (int) memory[number];
        } else {
            System.out.println("Invalid operation");
            System.exit(0);
        }
        int result = op1 / op2;

        if(result == 0){
            zf = 1;
        }else{
            zf = 0;
        }
        if(result > 65535){
            sf = 1;
        }else{
            sf = 0;
        }
        if(cf != 1){
            register[1] = result;
        }
    }


    public void xor(String addressMode, String operand) {

        if (addressMode.equals("00")) {
            // assigns the number in the register A to variable regNum
            int regNum = (int) register[1];
            // converts the regNum to binary number
            String opNum = Converter.decimalToBinary(regNum);
            // if the length of binary number smaller than 16, adds 0's
            while (opNum.length() < 16) {
                opNum = 0 + opNum;
            }
            // assigns the result of operation to variable res
            int res = BitwiseOperations.xor(opNum, operand);

            // following if statements set the flag registers.
            if(res == 65535){
                sf = 1;
            }
            else{
                sf = 0;
            }
            if(res == 0){
                zf = 1;
            }
            else{
                zf = 0;
            }
            // store the result to register A
            register[1] = res;

        } else if (addressMode.equals("01")) {
            int index = Converter.binToDecimal(operand);
            // assigns the binary value of the decimal in register A to regNum variable
            String regNum = Converter.decimalToBinary((int) register[1]);
            // if the length of binary number smaller than 16, adds 0's
            while (regNum.length() < 16) {
                regNum = 0 + regNum;
            }
            // assigns the number in the corresponding register to variable regNum
            String opNum = Converter.decimalToBinary((int) register[index]);
            // if the length of binary number smaller than 16, adds 0's
            while (opNum.length() < 16) {
                opNum = 0 + opNum;
            }
            // do the operation
            int res = BitwiseOperations.xor(regNum, opNum);
            if(res == 65535){
                sf = 1;
            }else{
                sf = 0;
            }
            if(res == 0){
                zf = 1;
            }else{
                zf = 0;
            }

            register[1] = res;


        } else if (addressMode.equals("10")) {
            // convert the operand to decimal to use as register index
            int index = Converter.binToDecimal(operand);
            // assigns the value in the register to variable memIndex
            int memIndex = (int) register[index];
            // converting the decimal value in register A to binary and assign to regNum
            String regNum = Converter.decimalToBinary((int) register[1]);
            // if the length of binary number smaller than 16, adds 0's
            while (regNum.length() < 16) {
                regNum = 0 + regNum;
            }
            // convert the value in memory to binary and assign to opNum
            String opNum = Converter.decimalToBinary((int) memory[memIndex]);
            // if the length of binary number smaller than 16, adds 0's
            while (opNum.length() < 16) {
                opNum = 0 + opNum;
            }
            // do the operation
            int res = BitwiseOperations.xor(regNum, opNum);
            // set the flags
            if(res == 65535){
                sf = 1;
            }else{
                sf = 0;
            }
            if(res == 0){
                zf = 1;
            } else{
                zf = 0;
            }

            register[1] = res;


        } else if (addressMode.equals("11")) {
            // convert the operand to decimal and assign the value to memIndex
            int memIndex = Converter.binToDecimal(operand);
            // converting the decimal value in register A to binary and assign to regNum
            String regNum = Converter.decimalToBinary((int) register[1]);
            // if the length of binary number smaller than 16, adds 0's
            while (regNum.length() < 16) {
                regNum = 0 + regNum;
            }
            // convert the value in memory to binary and assign to opNum
            String opNum = Converter.decimalToBinary((int) memory[memIndex]);
            while (opNum.length() < 16) {
                opNum = 0 + opNum;
            }
            // do the operation
            int res = BitwiseOperations.xor(regNum, opNum);
            if(res == 65535){
                sf = 1;
            }else{
                sf = 0;
            }
            if(res == 0){
                zf = 1;
            }else{
                zf = 0;
            }
            register[1] = res;

        } else {
            System.out.println("Invalid operand");
            System.exit(0);
        }
    }


    public void and(String addressMode, String operand) {

        if (addressMode.equals("00")) {
            // assigns the number in the register A to variable regNum
            int regNum = (int) register[1];
            // converts the regNum to binary number
            String bNum = Converter.decimalToBinary(regNum);
            // if the length of binary number smaller than 16, adds 0's
            while (bNum.length() < 16) {
                bNum = 0 + bNum;
            }
            // assigns the result of operation to variable res
            int res = BitwiseOperations.and(bNum, operand);

            // following if statements set the flag registers.
            if(res == 65535){
                sf = 1;
            }
            else{
                sf = 0;
            }
            if(res == 0){
                zf = 1;
            }
            else{
                zf = 0;
            }
            // store the result to register A
            register[1] = res;

        } else if (addressMode.equals("01")) {
            int index = Converter.binToDecimal(operand);
            // assigns the binary value of the decimal in register A to regNum variable
            String regNum = Converter.decimalToBinary((int) register[1]);
            // if the length of binary number smaller than 16, adds 0's
            while (regNum.length() < 16) {
                regNum = 0 + regNum;
            }
            // assigns the number in the corresponding register to variable regNum
            String opNum = Converter.decimalToBinary((int) register[index]);
            // if the length of binary number smaller than 16, adds 0's
            while (opNum.length() < 16) {
                opNum = 0 + opNum;
            }
            // do the operation
            int res = BitwiseOperations.and(regNum, opNum);
            if(res == 65535){
                sf = 1;
            }else{
                sf = 0;
            }
            if(res == 0){
                zf = 1;
            }else{
                zf = 0;
            }

            register[1] = res;


        } else if (addressMode.equals("10")) {
            // convert the operand to decimal to use as register index
            int index = Converter.binToDecimal(operand);
            // assigns the value in the register to variable memIndex
            int memIndex = (int) register[index];
            // converting the decimal value in register A to binary and assign to regNum
            String regNum = Converter.decimalToBinary((int) register[1]);
            // if the length of binary number smaller than 16, adds 0's
            while (regNum.length() < 16) {
                regNum = 0 + regNum;
            }
            // convert the value in memory to binary and assign to opNum
            String opNum = Converter.decimalToBinary((int) memory[memIndex]);
            // if the length of binary number smaller than 16, adds 0's
            while (opNum.length() < 16) {
                opNum = 0 + opNum;
            }
            // do the operation
            int res = BitwiseOperations.and(regNum, opNum);
            // set the flags
            if(res == 65535){
                sf = 1;
            }else{
                sf = 0;
            }
            if(res == 0){
                zf = 1;
            } else{
                zf = 0;
            }

            register[1] = res;


        } else if (addressMode.equals("11")) {
            // convert the operand to decimal and assign the value to memIndex
            int memIndex = Converter.binToDecimal(operand);
            // converting the decimal value in register A to binary and assign to regNum
            String regNum = Converter.decimalToBinary((int) register[1]);
            // if the length of binary number smaller than 16, adds 0's
            while (regNum.length() < 16) {
                regNum = 0 + regNum;
            }
            // convert the value in memory to binary and assign to opNum
            String opNum = Converter.decimalToBinary((int) memory[memIndex]);
            while (opNum.length() < 16) {
                opNum = 0 + opNum;
            }
            // do the operation
            int res = BitwiseOperations.and(regNum, opNum);
            if(res == 65535){
                sf = 1;
            }else{
                sf = 0;
            }
            if(res == 0){
                zf = 1;
            }else{
                zf = 0;
            }
            register[1] = res;

        } else {
            System.out.println("Invalid operand");
            System.exit(0);
        }
    }


    public void or(String addressMode, String operand) {

        if (addressMode.equals("00")) {
            // assigns the number in the register A to variable regNum
            int regNum = (int) register[1];
            // converts the regNum to binary number
            String bNum = Converter.decimalToBinary(regNum);
            // if the length of binary number smaller than 16, adds 0's
            while (bNum.length() < 16) {
                bNum = 0 + bNum;
            }
            // assigns the result of operation to variable res
            int res = BitwiseOperations.or(bNum, operand);

            // following if statements set the flag registers.
            if(res == 65535){
                sf = 1;
            }
            else{
                sf = 0;
            }
            if(res == 0){
                zf = 1;
            }
            else{
                zf = 0;
            }
            // store the result to register A
            register[1] = res;

        } else if (addressMode.equals("01")) {
            int index = Converter.binToDecimal(operand);
            // assigns the binary value of the decimal in register A to regNum variable
            String regNum = Converter.decimalToBinary((int) register[1]);
            // if the length of binary number smaller than 16, adds 0's
            while (regNum.length() < 16) {
                regNum = 0 + regNum;
            }
            // assigns the number in the corresponding register to variable regNum
            String opNum = Converter.decimalToBinary((int) register[index]);
            // if the length of binary number smaller than 16, adds 0's
            while (opNum.length() < 16) {
                opNum = 0 + opNum;
            }
            // do the operation
            int res = BitwiseOperations.or(regNum, opNum);
            if(res == 65535){
                sf = 1;
            }else{
                sf = 0;
            }
            if(res == 0){
                zf = 1;
            }else{
                zf = 0;
            }

            register[1] = res;


        } else if (addressMode.equals("10")) {
            // convert the operand to decimal to use as register index
            int index = Converter.binToDecimal(operand);
            // assigns the value in the register to variable memIndex
            int memIndex = (int) register[index];
            // converting the decimal value in register A to binary and assign to regNum
            String regNum = Converter.decimalToBinary((int) register[1]);
            // if the length of binary number smaller than 16, adds 0's
            while (regNum.length() < 16) {
                regNum = 0 + regNum;
            }
            // convert the value in memory to binary and assign to opNum
            String opNum = Converter.decimalToBinary((int) memory[memIndex]);
            // if the length of binary number smaller than 16, adds 0's
            while (opNum.length() < 16) {
                opNum = 0 + opNum;
            }
            // do the operation
            int res = BitwiseOperations.or(regNum, opNum);
            // set the flags
            if(res == 65535){
                sf = 1;
            }else{
                sf = 0;
            }
            if(res == 0){
                zf = 1;
            } else{
                zf = 0;
            }

            register[1] = res;


        } else if (addressMode.equals("11")) {
            // convert the operand to decimal and assign the value to memIndex
            int memIndex = Converter.binToDecimal(operand);
            // converting the decimal value in register A to binary and assign to regNum
            String regNum = Converter.decimalToBinary((int) register[1]);
            // if the length of binary number smaller than 16, adds 0's
            while (regNum.length() < 16) {
                regNum = 0 + regNum;
            }
            // convert the value in memory to binary and assign to opNum
            String opNum = Converter.decimalToBinary((int) memory[memIndex]);
            while (opNum.length() < 16) {
                opNum = 0 + opNum;
            }
            // do the operation
            int res = BitwiseOperations.or(regNum, opNum);
            if(res == 65535){
                sf = 1;
            }else{
                sf = 0;
            }
            if(res == 0){
                zf = 1;
            }else{
                zf = 0;
            }
            register[1] = res;

        } else {
            System.out.println("Invalid operand");
            System.exit(0);
        }
    }


    public void not(String addressMode, String operand) {

        if (addressMode.equals("01")) {
            // converts the operand to decimal and assigns to index variable
            int index = Converter.binToDecimal(operand);
            // converts the value in register[index] and assings to opNum
            String opNum = Converter.decimalToBinary((int) register[index]);
            // if lenght of opNum is smaller than 16, adds 0's to the front of the binary value
            while (opNum.length() < 16) {
                opNum = 0 + opNum;
            }
            // does the operation
            int res = BitwiseOperations.not(opNum);
            if(res == 65535){
                sf = 1;
            }else{
                sf = 0;
            }
            if(res == 0){
                zf = 1;
            }else{
                zf = 0;
            }
            register[index] = res;

        } else if (addressMode.equals("10")) {
            // converts the operand to decimal to use as register index
            int index = Converter.binToDecimal(operand);
            // assigns the value in the register to variable memIndex
            int memIndex = (int) register[index];
            // convert the value in memory to binary and assign to opNum
            String opNum = Converter.decimalToBinary((int) memory[memIndex]);
            // if the length of binary number smaller than 16, adds 0's
            while (opNum.length() < 16) {
                opNum = 0 + opNum;
            }
            // does the operation
            int res = BitwiseOperations.not(opNum);
            if(res == 65535){
                sf = 1;
            }else{
                sf = 0;
            }
            if(res == 0){
                zf = 1;
            }else{
                zf = 0;
            }
            memory[memIndex] = res;

        } else if (addressMode.equals("11")) {
            // converts the value in operand to decimal and assigns to memIndex
            int memIndex = Converter.binToDecimal(operand);
            // converts the value in memory to binary and assigns to opNum
            String opNum = Converter.decimalToBinary((int) memory[memIndex]);
            // if the length of binary number smaller than 16, adds 0's
            while (opNum.length() < 16) {
                opNum = 0 + opNum;
            }
            // does the operatoin
            int res = BitwiseOperations.not(opNum);
            memory[memIndex] = res;

        } else {
            System.out.println("Invalid operand");
            System.exit(0);
        }
    }


    public void shiftL(String operand) {
        // converts the operand to decimal and assigns to index variable
        int index = Converter.binToDecimal(operand);
        // converts the value in the register to binary, assigns it to opNum
        String opNum = Converter.decimalToBinary((int) register[index]);
        // if lenght of opNum is smaller than 16, adds 0's to the front of the binary value
        while (opNum.length() < 16) {
            opNum = 0 + opNum;
        }
        // does the operation
        int res = BitwiseOperations.shiftL(opNum);
        if(res == 65535){
            sf = 1;
        }else{
            sf = 0;
        }
        if(res == 0){
            zf = 1;
        }else{
            zf = 0;
        }

        register[index] = res;

    }


    public void shiftR(String operand) {
        // converts the operand to decimal and assigns to index variable
        int index = Converter.binToDecimal(operand);
        // converts the value in the register to binary, assigns it to opNum
        String opNum = Converter.decimalToBinary((int) register[index]);
        // if lenght of opNum is smaller than 16, adds 0's to the front of the binary value
        while (opNum.length() < 16) {
            opNum = 0 + opNum;
        }
        // does the operation
        int res = BitwiseOperations.shiftR(opNum);
        if(res == 65535){
            sf = 1;
        }else{
            sf = 0;
        }
        if(res == 0){
            zf = 1;
        }else{
            zf = 0;
        }

        register[index] = res;
    }


    public void push(String operand) {
        if(memory[s] != null){
            System.out.println("Stackoverflow");
            System.exit(0);
        }
        // converts the operand to decimal and assigns to variable data
        int data = Converter.binToDecimal(operand);
        // pushes the value in data to stack address pointed by s
        memory[s] = register[data];
        // decreases the stack pointer value by 2
        s -= 2;
    }


    public void pop(String operand) {
        if(s == MEMORY_SIZE){
            System.out.println("Nothing to pop from the stack");
            System.exit(0);
        }
        // converts the operand to decimal and assigns to variable index
        int index = Converter.binToDecimal(operand);
        // assigns the valu in memory stack pointed by s
        int data = (int) memory[s];
        // assing the value in stack to register
        register[index] = data;
        // clears the value in the stack and assigns it as null
        memory[s] = null;
        // increments the stack pointer value by 2
        s += 2;


    }


    public void cmp(String addressMode, String operand) {
        // assigns the value in register A to regNum variable
        int regNum = (int) register[1];
        // initialize opNum varible. It will be used to operand number for comparison
        int opNum = 0;
        if (addressMode.equals("00")) {
            // assigns the operand value to opNum as decimal value
            opNum = Converter.binToDecimal(operand);
        } else if (addressMode.equals("01")) {
            // converts the operand to decimal and assigns it to index
            int index = Converter.binToDecimal(operand);
            // assigns the value in register to opNum
            opNum = (int) register[index];
        } else if (addressMode.equals("10")) {
            // // converts the operand to decimal and assigns it to index
            int index = Converter.binToDecimal(operand);
            // assigns the value in register to memIndex
            int memIndex = (int) register[index];
            // assigns the value in memory to opNum
            opNum = (int) memory[memIndex];
        } else if (addressMode.equals("11")) {
            // converts the operand to decimal to find the index of the memory
            int memIndex = Converter.binToDecimal(operand);
            // assigns the value in memory to opNum
            opNum = (int) memory[memIndex];
        }
        int result = regNum - opNum;
        if(result == 0){
            zf = 1;
            cf = 0;
        } else if(result < 0){
            zf = 0;
             cf = 1;
        } else{
            zf = 0;
            cf = 0;
        }

    }


    public void jmp(String operand){
        int counter = Converter.binToDecimal(operand);
        pc = counter;
    }


    public void jz(String operand){
        int counter = Converter.binToDecimal(operand);
        if(zf == 1){
            pc = counter;
        }
    }


    public void je(String operand){
        int counter = Converter.binToDecimal(operand);
        if(zf == 1){
            pc = counter;
        }
    }


    public void jnz(String operand){
        int counter = Converter.binToDecimal(operand);
        if(zf == 0){
            pc = counter;
        }
    }


    public void jne(String operand){
        int counter = Converter.binToDecimal(operand);
        if(zf == 0){
            pc = counter;
        }
    }


    public void jc(String operand){
        int counter = Converter.binToDecimal(operand);
        if(cf == 1){
            pc = counter;
        }
    }


    public void jnc(String operand){
        int counter = Converter.binToDecimal(operand);
        if(cf == 0){
            pc = counter;
        }
    }


    public void ja(String operand){
        int counter = Converter.binToDecimal(operand);
        if(cf == 0){
            pc = counter;
        }
    }


    public void jae(String operand){
        int counter = Converter.binToDecimal(operand);
        if(cf == 0){
            pc = counter;
        }
    }


    public void jb(String operand) {
        int counter = Converter.binToDecimal(operand);
        if (cf == 1) {
            pc = counter;
        }
    }


    public void jbe(String operand) {
        int counter = Converter.binToDecimal(operand);
        if (cf == 1 || zf == 1) {
            pc = counter;
        }
    }


    public void read(String addressMode, String operand){
        if(addressMode.equals("01")){
            int index = Converter.binToDecimal(operand);
            Scanner character = new Scanner(System.in);
            System.out.println("Please enter a character");
            char c = character.next().charAt(0);
            register[index] = (int) c;
        } else if(addressMode.equals("10")){
            int index = Converter.binToDecimal(operand);
            int memIndex = (int) register[index];
            Scanner character = new Scanner(System.in);
            System.out.println("Please enter a character");
            char c = character.next().charAt(0);
            memory[memIndex] = (int) c;
        } else if(addressMode.equals("11")){
            int memIndex = Converter.binToDecimal(operand);
            Scanner character = new Scanner(System.in);
            System.out.println("Please enter a character");
            char c = character.next().charAt(0);
            memory[memIndex] = (int) c;
        }
    }


    public void print(String adressMode, String operand){
        // as java did not allow to print extended ASCİİ
        // I had to find below list on the internet
        // the first item in the list represents ascii character for 128
        final char[] EXTENDED = { 0x00C7, 0x00FC, 0x00E9, 0x00E2,
                0x00E4, 0x00E0, 0x00E5, 0x00E7, 0x00EA, 0x00EB, 0x00E8, 0x00EF,
                0x00EE, 0x00EC, 0x00C4, 0x00C5, 0x00C9, 0x00E6, 0x00C6, 0x00F4,
                0x00F6, 0x00F2, 0x00FB, 0x00F9, 0x00FF, 0x00D6, 0x00DC, 0x00A2,
                0x00A3, 0x00A5, 0x20A7, 0x0192, 0x00E1, 0x00ED, 0x00F3, 0x00FA,
                0x00F1, 0x00D1, 0x00AA, 0x00BA, 0x00BF, 0x2310, 0x00AC, 0x00BD,
                0x00BC, 0x00A1, 0x00AB, 0x00BB, 0x2591, 0x2592, 0x2593, 0x2502,
                0x2524, 0x2561, 0x2562, 0x2556, 0x2555, 0x2563, 0x2551, 0x2557,
                0x255D, 0x255C, 0x255B, 0x2510, 0x2514, 0x2534, 0x252C, 0x251C,
                0x2500, 0x253C, 0x255E, 0x255F, 0x255A, 0x2554, 0x2569, 0x2566,
                0x2560, 0x2550, 0x256C, 0x2567, 0x2568, 0x2564, 0x2565, 0x2559,
                0x2558, 0x2552, 0x2553, 0x256B, 0x256A, 0x2518, 0x250C, 0x2588,
                0x2584, 0x258C, 0x2590, 0x2580, 0x03B1, 0x00DF, 0x0393, 0x03C0,
                0x03A3, 0x03C3, 0x00B5, 0x03C4, 0x03A6, 0x0398, 0x03A9, 0x03B4,
                0x221E, 0x03C6, 0x03B5, 0x2229, 0x2261, 0x00B1, 0x2265, 0x2264,
                0x2320, 0x2321, 0x00F7, 0x2248, 0x00B0, 0x2219, 0x00B7, 0x221A,
                0x207F, 0x00B2, 0x25A0, 0x00A0 };
        if(adressMode.equals("00")){
            int n = Converter.binToDecimal(operand);
            if(n>0 && n<128){
                char c = (char) n;
                System.out.println(c);
            } else if(n>127 && n <256){
                n -= 128;
                char c = EXTENDED[n];
                System.out.println(c);
            }else{
                System.out.println("The number is bigger than 255, Character can not be printed");
                System.exit(0);
            }
        } else if(adressMode.equals("01")){
            int index = Converter.binToDecimal(operand);
            int n = (int) register[index];
            if(n>0 && n<128){
                char c = (char) n;
                System.out.println(c);
            }else if(n>127 && n <256){
                n -= 128;
                char c = EXTENDED[n];
                System.out.println(c);
            }else{
                System.out.println("The number is bigger than 255, Character can not be printed");
                System.exit(0);
            }
        } else if(adressMode.equals("10")){
            int index = Converter.binToDecimal(operand);
            int memIndex = (int) register[index];
            int n = (int) memory[memIndex];
            if(n>0 && n<128){
                char c = (char) n;
                System.out.println(c);
            } else if(n>127 && n <256){
                n -= 128;
                char c = EXTENDED[n];
                System.out.println(c);
            }else{
                System.out.println("The number is bigger than 255, Character can not be printed");
                System.exit(0);
            }
        } else if(adressMode.equals("11")){
            int memIndex = Converter.binToDecimal(operand);
            int n = (int) memory[memIndex];
            if(n>0 && n<128){
                char c = (char) n;
                System.out.println(c);
            }else if(n>127 && n <256){
                n -= 128;
                char c = EXTENDED[n];
                System.out.println(c);
            }else{
                System.out.println("The number is bigger than 255, Character can not be printed");
                System.exit(0);
            }
        }
    }


    public void nop(){
        return;
    }



}