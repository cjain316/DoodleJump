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
	
	public void paint(Graphics g, boolean hitboxes) {
		Graphics2D g2 = (Graphics2D) g;
		
		tx = AffineTransform.getTranslateInstance(x, y);
		Sprite = getImage("Resources\\\\platformBreakable.png");
		g2.drawImage(Sprite, tx, null);
		
		if (hitboxes) {
			g.setColor(new Color(255,0,0)); //hitboxes
			g.drawRect(x,y,70,10);
		}
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
