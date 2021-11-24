package ru.skillbox;

public class Hammer {

    String typeHammer;
    int weightHammer;
    boolean haveClaw;


    public Hammer(String typeHammer, int weightHammer) {
        this.typeHammer = typeHammer;
        this.weightHammer = weightHammer;
    }

    public void setTypeHammer(String typeHammer) {
        this.typeHammer = typeHammer;
    }

    public void setWeightHammer(int weightHammer) {
        this.weightHammer = weightHammer;
    }

    public void setHaveClaw(boolean haveClaw) {
        this.haveClaw = haveClaw;
    }

    public String getTypeHammer() {
        return typeHammer;
    }

    public int getWeightHammer() {
        return weightHammer;
    }

    public boolean isHaveClaw() {
        return haveClaw;
    }


}
