package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SuperPacGum {

    @SerializedName("duration")
    @Expose
    private Integer duration;

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

}