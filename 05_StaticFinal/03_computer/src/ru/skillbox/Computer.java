package ru.skillbox;

public class Computer {

    private Processor processor;
    private Memory memory;
    private HardDisk hardDisk;
    private Display display;
    private KeyBoard keyboard;
    private double totalWeight;
    private final String vendor;
    private final String computerName;

    public Computer(String vendor, String computerName, Processor processor, Memory memory, HardDisk hardDisk, Display display, KeyBoard keyboard) {
        this.vendor = vendor;
        this.computerName = computerName;
        this.processor = processor;
        this.memory = memory;
        this.hardDisk = hardDisk;
        this.display = display;
        this.keyboard = keyboard;
        totalweight();

    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }

    public void setHardDisk(HardDisk hardDisk) {
        this.hardDisk = hardDisk;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public void setKeyboard(KeyBoard keyboard) {
        this.keyboard = keyboard;
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


    public void totalweight() {
        totalWeight = processor.getWeight() + hardDisk.getWeight() + display.getWeight() + keyboard.getWeight() + memory.getWeight();
    }

    public String toString() {

        return "Производитель: " + vendor + "    Наименование: " + computerName + "\n" +
                "Процессор: " + processor + "\n" + "Память: " + memory + "\n" +
                "Накопитель: " + hardDisk + "\n" + "Дисплей: " + display + "\n" +
                "Клавиатура: " + keyboard + "\n" + "Общий вес: " + totalWeight;
    }


}


