package ru.skillbox;

public class Cargo {


    private final Dimensions dimensions;
    private final double weight;
    private final String diliveryAddress;
    private final boolean canTurnOver;
    private final boolean breakable;

    public Cargo(Dimensions dimensions, double weight, String diliveryAddress, boolean canTurnOver, boolean breakable) {
        this.dimensions = dimensions;
        this.weight = weight;
        this.diliveryAddress = diliveryAddress;
        this.canTurnOver = canTurnOver;
        this.breakable = breakable;
    }

    public Cargo setDimensions(Dimensions dimensions) {
        return new Cargo(dimensions, weight, diliveryAddress, canTurnOver, breakable);
    }

    public Cargo setWeight(double weight) {
        return new Cargo(dimensions, weight, diliveryAddress, canTurnOver, breakable);
    }

    public Cargo setDiliveryAddress(String diliveryAddress) {
        return new Cargo(dimensions, weight, diliveryAddress, canTurnOver, breakable);
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public double getWeight() {
        return weight;
    }

    public String getDiliveryAddress() {
        return diliveryAddress;
    }

    public boolean isCanTurnOver() {
        return canTurnOver;
    }

    public boolean isBreakable() {
        return breakable;
    }


    public String toString() {
        return dimensions + "\n" + "Вес: " + weight + "\n" + "Адрес доставки: " + diliveryAddress;
    }

}



