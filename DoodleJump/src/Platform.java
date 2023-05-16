import java.awt.Rectangle;

public class Platform {
	private int x,y;
	private Rectangle hitbox;
	
	public Platform(int x, int y) {
		this.x = x;
		this.y = y;
		hitbox = new Rectangle(x,y,70,10);
	}
	
	public void setX(int x) {this.x = x;}
	public void setY(int y) {this.y = y;}
	
	public int getX() {return x;}
	public int getY() {return y;}
	public Rectangle getHitbox() {return hitbox;}
	
	public void update(Player p) {
		hitbox.setLocation(x,600+y+p.getY());
	}
}
