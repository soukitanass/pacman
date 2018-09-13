package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ghost {

	@SerializedName("id")
	@Expose
	private Integer id;
	@SerializedName("start_pos")
	@Expose
	private StartPos startPos;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public StartPos getStartPos() {
		return startPos;
	}

	public void setStartPos(StartPos startPos) {
		this.startPos = startPos;
	}

}