package com.company;

public class ArithmeticCalculator {

    //
    public double x;
    public double y;
    public Operation type;

    public ArithmeticCalculator(int x, int y, Operation type) {
        this.x = x;
        this.y = y;
        this.type = type;
        calculate();

    }

    public void calculate() {
        switch (type) {
            case ADD -> System.out.println(x + " + " + y + " = " + " " + (x + y));
            case SUBTRACT -> System.out.println(x + " - " + y + " = " + " " + (x - y));
            case MULTYPLY -> System.out.println(x + " * " + y + " = " + " " + (x * y));
        }


    }
}