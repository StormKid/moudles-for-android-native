package com.stormkid.map;

import java.io.Serializable;

/**
 * @author ke_li
 * @date 2018/6/8
 */
public class MapProperties implements Serializable {
    private String cityCode ;
    private String cityName ;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
