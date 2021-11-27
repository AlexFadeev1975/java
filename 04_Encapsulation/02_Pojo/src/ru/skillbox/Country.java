package ru.skillbox;

public class Country {

    String nameCountry;
    int countCountryPeople;
    double areaCountry;
    String nameCapital;
    boolean haveOutletToSea;

    public void setNameCountry(String nameCountry) {
        this.nameCountry = nameCountry;
    }

    public void setCountCountryPeople(int countCountryPeople) {
        this.countCountryPeople = countCountryPeople;
    }

    public void setAreaCountry(double areaCountry) {
        this.areaCountry = areaCountry;
    }

    public void setNameCapital(String nameCapital) {
        this.nameCapital = nameCapital;
    }

    public void setHaveOutletToSea(boolean haveOutletToSea) {
        this.haveOutletToSea = haveOutletToSea;
    }

    public String getNameCountry() {
        return nameCountry;
    }

    public int getCountCountryPeople() {
        return countCountryPeople;
    }

    public double getAreaCountry() {
        return areaCountry;
    }

    public String getNameCapital() {
        return nameCapital;
    }

    public boolean isHaveOutletToSea() {
        return haveOutletToSea;
    }

    public Country(String nameCapital) {
        this.nameCapital = nameCapital;


    }

}
