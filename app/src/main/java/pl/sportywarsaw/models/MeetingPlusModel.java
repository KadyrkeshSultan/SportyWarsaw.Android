package pl.sportywarsaw.models;

import java.io.Serializable;
import java.util.Date;

import pl.sportywarsaw.enums.SportType;

/**
 * Created by Jan Kierzkowski on 04.01.2016.
 */
public class MeetingPlusModel implements Serializable {
    private int id;
    private String title;
    private int maxParticipants;
    private double cost;
    private Date startTime;
    private Date endTime;
    private SportType sportType;
    private String description;
    private String organizerName;
    private SportFacilityPlusModel sportsFacility;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SportType getSportType() {
        return sportType;
    }

    public void setSportType(SportType sportType) {
        this.sportType = sportType;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public SportFacilityPlusModel getSportsFacility() {
        return sportsFacility;
    }

    public void setSportsFacility(SportFacilityPlusModel sportsFacility) {
        sportsFacility = sportsFacility;
    }
}
