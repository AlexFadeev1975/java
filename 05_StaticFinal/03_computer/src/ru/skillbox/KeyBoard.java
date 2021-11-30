package ru.skillbox;

public class KeyBoard {

    public String keyBoardType;
    public HaveLightning haveLighting;
    public double weight;

    public enum HaveLightning {YES, NO}

    public KeyBoard(String keyBoardType, HaveLightning haveLighting, double weight) {
        this.keyBoardType = keyBoardType;
        this.haveLighting = haveLighting;
        this.weight = weight;
    }

    public KeyBoard setKeyBoardType(String keyBoardType) {
        return new KeyBoard(keyBoardType, haveLighting, weight);
    }

    public KeyBoard setHaveLighting(HaveLightning haveLighting) {
        return new KeyBoard(keyBoardType, haveLighting, weight);
    }

    public KeyBoard setWeight(double weight) {
        return new KeyBoard(keyBoardType, haveLighting, weight);
    }

    public String getKeyBoardType() {
        return keyBoardType;
    }

    //public boolean isHaveLighting() {
    // return haveLighting;
    //}

    public double getWeight() {
        return weight;
    }

    public String toString() {
        return "\n" + "  Тип: " + keyBoardType + "  Подсветка: " + haveLighting;
    }


}
