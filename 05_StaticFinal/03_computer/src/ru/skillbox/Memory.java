package ru.skillbox;

public class Memory {

    private final String type;
    private final int capacity;
    private final double weight;

    public Memory(String type, int capacity) {
        this.type = type;
        this.capacity = capacity;
        weight = 200;
    }

    public Memory setType(String type) {
        return new Memory(type, capacity);
    }

    public Memory setCapacity(int capacity) {
        return new Memory(type, capacity);
    }


    public double getWeight() {
        return weight;
    }

    public String getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }

    public String toString() {
        return "\n" + "   Тип памяти:  " + type + "   Объем:  " + capacity + "  GB";
    }


}
