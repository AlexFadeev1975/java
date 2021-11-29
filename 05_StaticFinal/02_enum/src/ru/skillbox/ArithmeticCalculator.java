package com.company;

public class ArithmeticCalculator {

    public double x;
    public double y;
    public Operation type;

    public ArithmeticCalculator(int x, int y) {
        this.x = x;
        this.y = y;

    }

    public void calculate(Operation type) {
        this.type = type;
        switch (type) {
            case ADD -> System.out.println(x + " + " + y + " = " + " " + (x + y));
            case SUBTRACT -> System.out.println(x + " - " + y + " = " + " " + (x - y));
            case MULTIPLY -> System.out.println(x + " * " + y + " = " + " " + (x * y));
        }


    }
}