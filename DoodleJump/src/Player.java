
public class Player {
	private int x,y;
	private String facing;
	
	public Player() {
		x = 0;
		y = 0;
		facing = "Right";
	}
	
	public void setX(int x) {this.x = x;}
	public void setY(int y) {this.y = y;}
	public void setFacing(String s) {facing = s;}
	
	public int getX() {return x;}
	public int getY() {return y;}
	public String getFacing() {return facing;}
}
