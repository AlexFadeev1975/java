package com.company;

public class Main {

    public static void main(String[] args) {

        ArithmeticCalculator math1 = new ArithmeticCalculator(3, 3);
        math1.calculate(Operation.MULTIPLY);
        math1.calculate(Operation.ADD);
        math1.calculate(Operation.SUBTRACT);

    }
}