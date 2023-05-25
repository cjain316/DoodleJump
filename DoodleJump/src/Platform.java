import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Platform {
	private int x,y;
	private Rectangle hitbox;
	
	private Image Sprite;
	private AffineTransform tx;
	
	private boolean hasSpring;
	
	public Platform(int x, int y, boolean spring) {
		this.x = x;
		this.y = y;
		hitbox = new Rectangle(x,y,70,10);
		hasSpring = spring;
	}
	
	public void setX(int x) {this.x = x;}
	public void setY(int y) {this.y = y;}
	
	public int getX() {return x;}
	public int getY() {return y;}
	public Rectangle getHitbox() {return hitbox;}
	public boolean getSpring() {return hasSpring;}
	
	public void update(Player p) {
		hitbox.setLocation(x,y+p.maxHeight+600);
	}
	
	public void paint(Graphics g, boolean hitboxes, Player p) {
		Graphics2D g2 = (Graphics2D) g;
		update(p);
		tx = AffineTransform.getTranslateInstance(x, y+p.maxHeight+600);
		Sprite = getImage("Resources\\\\platformBreakable.png");
		g2.drawImage(Sprite, tx, null);
		
		if (hasSpring) {
			tx = AffineTransform.getTranslateInstance(x+22, y+p.maxHeight+600-24);
			Sprite = getImage("Resources\\\\spring.png");
			g2.drawImage(Sprite, tx, null);
		}
		
		if (hitboxes) {
			g.setColor(new Color(255,0,0)); //hitboxes
			g.drawRect((int)hitbox.getX(),(int)(hitbox.getY()),70,10);
		}
		update(p);
	}
	
	protected Image getImage(String path) {

        Image tempImage = null;
        try {
            URL imageURL = Background.class.getResource(path);
            tempImage    = Toolkit.getDefaultToolkit().getImage(imageURL);
        } catch (Exception e) {e.printStackTrace();}
        return tempImage;
    }
}
