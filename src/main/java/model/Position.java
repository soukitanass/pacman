package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Position {

  @SerializedName("x")
  @Expose
  private Integer x;
  @SerializedName("y")
  @Expose
  private Integer y;

    public Position(int x, int y) {
    	this.x = x;
    	this.y = y;
    }
    
    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

}
