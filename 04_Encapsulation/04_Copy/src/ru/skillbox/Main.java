package ru.skillbox;

public class Main {

    public static void main(String[] args) {

        Dimensions dimCargo1 = new Dimensions(2, 2, 2).setLeight(8).setDepth(6);


        Cargo cargo = new Cargo(dimCargo1, 5, "kirov", true, true).setDiliveryAddress("Moscow").setWeight(23.1);


        System.out.println(cargo);


    }
}
