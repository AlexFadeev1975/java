package ru.skillbox;

public class Computer {

    private final Processor processor;
    private final Memory memory;
    private final HardDisk hardDisk;
    private final Display display;
    private final KeyBoard keyboard;
    public double totalWeight;
    public final String vendor;
    public final String computerName;

    public Computer(String vendor, String computerName, Processor processor, Memory memory, HardDisk hardDisk, Display display, KeyBoard keyboard) {
        this.vendor = vendor;
        this.computerName = computerName;
        this.processor = processor;
        this.memory = memory;
        this.hardDisk = hardDisk;
        this.display = display;
        this.keyboard = keyboard;
        Totalweight();

    }


    public Computer setProcessor(Processor processor) {
        return new Computer(vendor, computerName, processor, memory, hardDisk, display, keyboard);
    }

    public Computer setMemory(Memory memory) {

        return new Computer(vendor, computerName, processor, memory, hardDisk, display, keyboard);
    }

    public Computer setHardDisk(HardDisk hardDisk) {

        return new Computer(vendor, computerName, processor, memory, hardDisk, display, keyboard);
    }

    public Computer setDisplay(Display display) {
        return new Computer(vendor, computerName, processor, memory, hardDisk, display, keyboard);
    }

    public Computer setKeyboard(KeyBoard keyboard) {
        return new Computer(vendor, computerName, processor, memory, hardDisk, display, keyboard);
    }

    public Processor getProcessor() {
        return processor;
    }

    public Memory getMemory() {
        return memory;
    }

    public HardDisk getHardDisk() {
        return hardDisk;
    }

    public Display getDisplay() {
        return display;
    }

    public KeyBoard getKeyboard() {
        return keyboard;
    }

    public void Totalweight() {
        totalWeight = processor.weight + memory.weight + hardDisk.weight + display.weight + keyboard.weight;
    }

    public String toString() {

        return "Производитель: " + vendor + "    Наименование: " + computerName + "\n" +
                "Процессор: " + processor + "\n" + "Память: " + memory + "\n" +
                "Накопитель: " + hardDisk + "\n" + "Дисплей: " + display + "\n" +
                "Клавиатура: " + keyboard + "\n" + "Общий вес: " + totalWeight;
    }


}


