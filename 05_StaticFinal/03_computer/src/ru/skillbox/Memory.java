package ru.skillbox;

public class Memory {

    private final String type;
    private final int capacity;
    private final double weight;

    public Memory(String type, int capacity, double weight) {
        this.type = type;
        this.capacity = capacity;
        this.weight = weight;
    }

    public Memory setType(String type) {
        return new Memory(type, capacity, weight);
    }

    public Memory setCapacity(int capacity) {
        return new Memory(type, capacity, weight);
    }

    public Memory setWeight(double weight) {
        return new Memory(type, capacity, weight);
    }

    public String getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getWeight() {
        return weight;
    }

    public String toString() {
        return "\n" + "   Тип памяти:  " + type + "   Объем:  " + capacity + "  GB";
    }


}
