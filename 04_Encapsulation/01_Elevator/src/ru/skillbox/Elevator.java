package ru.skillbox;

public class Elevator {

    int currentFloor = 8;
    int minFloor;
    int maxFloor;


    public Elevator(int minFloor, int maxFloor) {
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
    }


    public int getCurrentFloor() {
        return currentFloor;
    }

    public void moveDown() {

        currentFloor--;

    }

    public void moveUp() {

        currentFloor++;

    }

    public void move(int floor) {


        if (floor >= minFloor & floor <= maxFloor) {

            if (floor < currentFloor) {

                for (; (currentFloor) >= (floor); moveDown()) {

                    System.out.println("Current floor: " + currentFloor);


                }
                currentFloor += 1;

            }
            if (floor > currentFloor) {

                for (; currentFloor <= (floor); moveUp()) {

                    System.out.println("Current floor: " + currentFloor);


                }
                currentFloor -= 1;

            }


        } else {

            System.out.println("Error occurred");
        }

    }

}

