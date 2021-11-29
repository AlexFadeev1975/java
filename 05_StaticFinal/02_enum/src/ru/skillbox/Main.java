package com.company;

public class Main {

    public static void main(String[] args) {

        ArithmeticCalculator math1 = new ArithmeticCalculator(3, 3, Operation.MULTYPLY);
        ArithmeticCalculator math2 = new ArithmeticCalculator(5, 3, Operation.ADD);
        ArithmeticCalculator math3 = new ArithmeticCalculator(26, 8, Operation.SUBTRACT);
    }
}