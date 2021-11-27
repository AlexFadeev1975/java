package ru.skillbox;

public class Main {

    public static void main(String[] args) {

        Dimensions dimCargo1 = new Dimensions(2, 2, 2);

        dimCargo1.setLeight(8);

        Cargo cargo = new Cargo(dimCargo1, 5, "kirov", true, true);


        cargo.setDiliveryAddress("Moskow");


        System.out.println(cargo);


    }
}
