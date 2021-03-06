package pl.sportywarsaw.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pl.sportywarsaw.enums.SportsFacilityType;

/**
 * Created by Jan Kierzkowski on 04.01.2016.
 */
public class SportFacilityPlusModel implements Serializable{
    private int id;
    private String street;
    private String number;
    private String description ;
    private String district;
    private String phoneNumber;
    private String website;
    private List<String> emails = new ArrayList<>();
    private PositionModel position;

    private SportsFacilityType type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<String> getEmails() {
        return emails;
    }

    public SportsFacilityType getType() {
        return type;
    }

    public void setType(SportsFacilityType type) {
        this.type = type;
    }

    public PositionModel getPosition() {
        return position;
    }
}
