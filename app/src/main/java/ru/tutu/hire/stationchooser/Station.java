package ru.tutu.hire.stationchooser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wazzy on 06.11.16.
 */


public class Station {
    private String countryTitle;

    public String getCountryTitle() {
        return countryTitle;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getDistrictTitle() {
        return districtTitle;
    }

    public int getCityId() {
        return cityId;
    }

    public String getCityTitle() {
        return cityTitle;
    }

    public String getRegionTitle() {
        return regionTitle;
    }

    public int getStationId() {
        return stationId;
    }

    public String getStationTitle() {
        return stationTitle;
    }

    private double longitude;
    private double latitude;

    private String districtTitle;
    private int cityId;
    private String cityTitle;
    private String regionTitle;

    private int stationId;
    private String stationTitle;

    Station(JSONObject json) throws JSONException {
        countryTitle = json.getString("countryTitle");

        longitude = json.getJSONObject("point").getDouble("longitude");
        latitude = json.getJSONObject("point").getDouble("latitude");

        districtTitle = json.getString("districtTitle");
        cityId = json.getInt("cityId");
        cityTitle = json.getString("cityTitle");
        regionTitle = json.getString("regionTitle");

        stationId = json.getInt("stationId");
        stationTitle = json.getString("stationTitle");
    }


}
