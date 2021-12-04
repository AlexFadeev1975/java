package ru.skillbox;

public class Main {

    public static void main(String[] args) {

        Processor processor1 = new Processor(3700, 3, "Intel").setCoreNumber(5);
        Memory memory1 = new Memory("DDR3", 8);
        HardDisk hardDisk1 = new HardDisk(HardDisk.HardDiskType.HDD, 3).setCapacity(2);
        Display display1 = new Display(15, Display.DisplayType.IPS).setDisplayType(Display.DisplayType.VA);
        KeyBoard keyBoard1 = new KeyBoard("Механическая", KeyBoard.HaveLightning.YES);

        Computer computer = new Computer("IBM", "Home", processor1.setCoreNumber(4), memory1, hardDisk1, display1, keyBoard1);

        System.out.println(computer);


    }
}
