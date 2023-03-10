package ru.skillbox;

public class KeyBoard {

    private final String keyBoardType;
    private final HaveLightning haveLighting;
    private final double weight;

    public enum HaveLightning {YES, NO}

    public KeyBoard(String keyBoardType, HaveLightning haveLighting) {
        this.keyBoardType = keyBoardType;
        this.haveLighting = haveLighting;
        weight = 300;
    }

    public KeyBoard setKeyBoardType(String keyBoardType) {
        return new KeyBoard(keyBoardType, haveLighting);
    }

    public KeyBoard setHaveLighting(HaveLightning haveLighting) {
        return new KeyBoard(keyBoardType, haveLighting);
    }

    public String getKeyBoardType() {
        return keyBoardType;
    }

    public HaveLightning isHaveLighting() {
        return haveLighting;
    }

    public double getWeight() {
        return weight;
    }

    public String toString() {
        return "\n" + "  Тип: " + keyBoardType + "  Подсветка: " + haveLighting;
    }


}
