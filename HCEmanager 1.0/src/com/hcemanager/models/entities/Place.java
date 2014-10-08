package com.hcemanager.models.entities;

/**
 * A subtype of Entity representing a bounded physical place or site with its contained structures, if any.
 *
 * Examples: A field, lake, city, county, state, country, lot (land), building, pipeline, power line, playground, ship, truck.
 *
 * Constraints: Place may be natural or man-made. The geographic position of a place may or may not be constant.
 *
 * Discussion: Places may be work facilities (where relevant acts occur), homes (where people live) or offices
 * (where people work). Places may contain sub-places (floor, room, booth, bed). Places may also be sites that are
 * investigated in the context of health care, social work, public health administration (e.g., buildings, picnic grounds,
 * day care centers, prisons, counties, states, and other focuses of epidemiological events).
 *
 * @author daniel .
 */
public class Place extends Entity {

    private boolean mobileInd;
    private String addr;
    private String directionsText;
    private String positionText;
    private String gpsText;
    private String idPlace;

    public Place() {
    }

    public boolean isMobileInd() {
        return mobileInd;
    }

    public void setMobileInd(boolean mobileInd) {
        this.mobileInd = mobileInd;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getDirectionsText() {
        return directionsText;
    }

    public void setDirectionsText(String directionsText) {
        this.directionsText = directionsText;
    }

    public String getPositionText() {
        return positionText;
    }

    public void setPositionText(String positionText) {
        this.positionText = positionText;
    }

    public String getGpsText() {
        return gpsText;
    }

    public void setGpsText(String gpsText) {
        this.gpsText = gpsText;
    }

    public String getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(String idPlace) {
        this.idPlace = idPlace;
    }
}
