package ru.skillbox;

public class Processor {
    private final int frequency;
    private final int coreNumber;
    private final String vendor;
    private final double weight;

    public Processor(int frequency, int coreNumber, String vendor, double weight) {
        this.frequency = frequency;
        this.coreNumber = coreNumber;
        this.vendor = vendor;
        this.weight = weight;
    }


    public Processor setFrequency(int frequency) {
        return new Processor(frequency, coreNumber, vendor, weight);
    }

    public Processor setCoreNumber(int coreNumber) {
        return new Processor(frequency, coreNumber, vendor, weight);
    }

    public Processor setVendor(String vendor) {
        return new Processor(frequency, coreNumber, vendor, weight);
    }

    public Processor setWeight(double weight) {
        return new Processor(frequency, coreNumber, vendor, weight);
    }

    public int getFrequency() {
        return frequency;
    }

    public int getCoreNumber() {
        return coreNumber;
    }

    public String getVendor() {
        return vendor;
    }

    public double getWeight() {
        return weight;
    }

    public String toString() {
        return "\n" + "     Частота: " + frequency + " МГц" + "  Количество ядер: " + coreNumber + "  Производитель: " + vendor;
    }
}