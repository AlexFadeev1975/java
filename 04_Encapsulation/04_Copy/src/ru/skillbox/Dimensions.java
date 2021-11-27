package ru.skillbox;

public class Dimensions {
    private final int leight;
    private final int width;
    private final int depth;
    public int cube;


    public Dimensions(int leight, int width, int depth) {
        this.leight = leight;
        this.width = width;
        this.depth = depth;
        countCube(getLeight(), getWidth(), getDepth());

    }

    public Dimensions() {
        this.leight = 1;
        this.width = 1;
        this.depth = 1;


    }

    public Dimensions setLeight(int leight) {
        return new Dimensions(leight, width, depth);
    }

    public Dimensions setWidth(int width) {
        return new Dimensions(leight, width, depth);
    }

    public Dimensions setDepth(int depth) {
        return new Dimensions(leight, width, depth);
    }

    public void countCube(int leight, int width, int depth) {

        cube = leight * width * depth;

    }

    public int getLeight() {
        return leight;
    }

    public int getWidth() {
        return width;
    }

    public int getDepth() {
        return depth;
    }

    public String toString() {
        return "Длина:" + leight + "\n" + "Ширина: " + width + "\n" + "Высота: " + depth + "\n" + "Объем: " + cube;
    }
}






