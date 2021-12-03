package ru.skillbox;

public class Display {

    private final double screenSize;
    private final DisplayType displayType;
    private final int weight;

    public enum DisplayType {

        IPS, TN, VA
    }

    public Display(double screenSize, DisplayType displayType, int weight) {
        this.screenSize = screenSize;
        this.displayType = displayType;
        this.weight = weight;
    }

    public Display setScreenSize(double screenSize) {
        return new Display(screenSize, displayType, weight);
    }

    public Display setDisplayType(DisplayType displayType) {
        return new Display(screenSize, displayType, weight);
    }

    public Display setWeight(int weight) {

        return new Display(screenSize, displayType, weight);
    }

    public double getScreenSize() {
        return screenSize;
    }

    public DisplayType getDisplayType() {
        return displayType;
    }

    public int getWeight() {
        return weight;
    }

    public String toString() {
        return "\n" + "  Размер экрана: " + screenSize + "''" + "  Тип матрицы: " + displayType;
    }


}
