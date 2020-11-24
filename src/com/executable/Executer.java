package com.executable;

import java.util.ArrayList;

public class Executer {

    // this method runs the program
    public void execute(ArrayList<String> list){
        // creates a new object from operation class
        Operation o = new Operation();
        // uses the insAllocation method from operation class
        // this method allocates the instructions to memory
        o.insAllocate(list);
        while(true){
            // this method brings instruction each time the operation has been executed
            String [] instruction = o.fetchInstruction();
            // first 6 bits of the instruction
            String opCode = instruction[0];
            // 7 and 8. bit of the instruction
            String addressMode = instruction[1];
            // remaining 16 bits of the instruction
            String operand = instruction[2];
            // swtich case checks opcodes to execute the corresponding operation.
            switch (opCode){
                case "000001":
                    o.halt();
                case "000010":
                    o.load(opCode, addressMode, operand);
                    break;
                case "000011":
                    o.store(opCode, addressMode, operand);
                    break;
                case "000100":
                    o.add(opCode, addressMode, operand);
                    break;
                case "000101":
                    o.sub(opCode, addressMode, operand);
                    break;
                case "000110":
                    o.inc(opCode, addressMode, operand);
                    break;
                case "000111":
                    o.dec(opCode, addressMode, operand);
                    break;
                case "001000":
                    o.mul(opCode, addressMode, operand);
                    break;
                case "001001":
                    o.div(opCode, addressMode, operand);
                    break;
                case "001010":
                    o.xor(addressMode, operand);
                    break;
                case "0001011":
                    o.and(addressMode, operand);
                    break;
                case "001100":
                    o.or(addressMode, operand);
                    break;
                case "001101":
                    o.not(addressMode, operand);
                    break;
                case "001110":
                    o.shiftL(operand);
                    break;
                case "001111":
                    o.shiftR(operand);
                    break;
                case "010000":
                    o.nop();
                    break;
                case "010001":
                    o.push(operand);
                    break;
                case "010010":
                    o.pop(operand);
                    break;
                case "010011":
                    o.cmp(addressMode, operand);
                    break;
                case "010100":
                    o.jmp(operand);
                    break;
                case "010101":
                    o.jz(operand);
                    break;
                case "010110":
                    o.jnz(operand);
                    break;
                case "010111":
                    o.jc(operand);
                    break;
                case "011000":
                    o.jnc(operand);
                    break;
                case "011001":
                    o.ja(operand);
                    break;
                case "100000":
                    o.jae(operand);
                    break;
                case "100001":
                    o.jb(operand);
                    break;
                case "100010":
                    o.jbe(operand);
                    break;
                case "100011":
                    o.read(addressMode, operand);
                    break;
                case "100100":
                    o.print(addressMode, operand);
                    break;

            }
        }

    }
}
