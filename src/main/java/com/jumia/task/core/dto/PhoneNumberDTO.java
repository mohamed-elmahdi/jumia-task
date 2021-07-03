package com.jumia.task.core.dto;

public class PhoneNumberDTO {
    private String country;
    private Boolean isValid;
    private String countryCode;
    private String number;

    public PhoneNumberDTO(){}

    public PhoneNumberDTO(String country, Boolean isValid, String countryCode, String number) {
        this.country = country;
        this.isValid = isValid;
        this.countryCode = countryCode;
        this.number = number;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "PhoneNumberDAO{" +
                "country='" + country + '\'' +
                ", isValid=" + isValid +
                ", countryCode='" + countryCode + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
