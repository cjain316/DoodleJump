import java.awt.Point;
import java.awt.Rectangle;

public class Player {
	private int x,y;
	private String facing;
	private int vy;
	private Rectangle hitbox;
	int vx;
	
	public Player() {
		x = 0;
		y = 0;
		vy = 50;
		facing = "Right";
		hitbox = new Rectangle(x,y,60,60);
	}
	
	public void setX(int x) {this.x = x;}
	public void setY(int y) {this.y = y;}
	public void setFacing(String s) {facing = s;}
	public void setVy(int vy) {this.vy = vy;}
	
	public int getX() {return x;}
	public int getY() {return y;}
	public String getFacing() {return facing;}
	public Rectangle getHitbox() {return hitbox;}
	public int getVy() {return vy;}
	
	public void update(Point p) {
		vx = ((int) (p.getX()-x))/5;
		x += vx;
		y += vy;
		if (vy > 20) vy = 20;
		hitbox.setLocation(x-30,600);
	}
}
