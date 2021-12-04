package ru.skillbox;

public class HardDisk {

    private final HardDiskType type;
    private final int capacity;
    private final double weight;

    public enum HardDiskType {

        HDD, SSD
    }

    public HardDisk(HardDiskType type, int capacity) {
        this.type = type;
        this.capacity = capacity;
        weight = 800;
    }

    public HardDisk setType(HardDiskType type) {
        return new HardDisk(type, capacity);
    }

    public HardDisk setCapacity(int capacity) {
        return new HardDisk(type, capacity);
    }


    public HardDiskType getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getWeight() {
        return weight;
    }

    public String toString() {
        return "\n" + "  Тип: " + type + "  Объем памяти: " + capacity + "ТБ";
    }


}
