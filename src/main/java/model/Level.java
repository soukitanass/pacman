package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Level {

  @SerializedName("id")
  @Expose
  private Integer id;
  @SerializedName("width")
  @Expose
  private Integer width;
  @SerializedName("height")
  @Expose
  private Integer height;
  @SerializedName("super_pac_gum")
  @Expose
  private SuperPacGum superPacGum;
  @SerializedName("pac-man")
  @Expose
  private PacMan pacMan;
  @SerializedName("ghost")
  @Expose
  private List<Ghost> ghost = null;
  @SerializedName("map")
  @Expose
  private List<List<Integer>> map = null;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getWidth() {
    return width;
  }

  public void setWidth(Integer width) {
    this.width = width;
  }

  public Integer getHeight() {
    return height;
  }

  public void setHeight(Integer height) {
    this.height = height;
  }

  public SuperPacGum getSuperPacGum() {
    return superPacGum;
  }

  public void setSuperPacGum(SuperPacGum superPacGum) {
    this.superPacGum = superPacGum;
  }

  public PacMan getPacMan() {
    return pacMan;
  }

  public void setPacMan(PacMan pacMan) {
    this.pacMan = pacMan;
  }

  public List<Ghost> getGhost() {
    return ghost;
  }

  public void setGhost(List<Ghost> ghost) {
    this.ghost = ghost;
  }

  public List<List<Integer>> getMap() {
    return map;
  }

  public void setMap(List<List<Integer>> map) {
    this.map = map;
  }

}
