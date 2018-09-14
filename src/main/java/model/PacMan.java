package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PacMan {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("speed")
    @Expose
    private Integer speed;
    @SerializedName("start_pos")
    @Expose
    private Position position;
    private Direction direction;
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public void setPosition(Position position) {
    	this.position = position;
    }
    
    public Position getPosition() {
    	return this.position;
    }
    
    public void setDirection(Direction direction) {
    	this.direction = direction;
    }
    
    public Direction getDirection() {
    	return this.direction;
    }
    
    public void updatePosition(int levelWidth, int levelHeight) {
    	int x = position.getX();
    	int y = position.getY();
    	
    	switch(this.direction) {
    		case RIGHT:
    			if (x < levelWidth) {
    				this.setPosition(new Position(++x, y));
    			} else {
    				this.setPosition(new Position(0, y));
    			}
    			break;
    		case LEFT:
    			if (x > 0) {
    				this.setPosition(new Position(--x, y));
    			} else {
    				this.setPosition(new Position(levelWidth, y));
    			}
    			break;
    		case UP:
    			if (y > 0) {
    				this.setPosition(new Position(x, --y));
    			} else {
    				this.setPosition(new Position(x, levelHeight));
    			}
    			break;
    		case DOWN:
    			if (y < levelHeight) {
    				this.setPosition(new Position(x, ++y));
    			} else {
    				this.setPosition(new Position(x, 0));
    			}
    			break;
    		default:
    			break;
    	}
    }
}
