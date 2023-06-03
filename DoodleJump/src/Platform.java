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
	private boolean hasTrampoline;
	private boolean hasJetpack;
	private boolean hasProphat;
	
	public Platform(int x, int y, boolean spring,boolean trampoline,boolean jetpack,boolean prophat) {
		this.x = x;
		this.y = y;
		hitbox = new Rectangle(x,y,70,10);
		hasSpring = spring;
		hasTrampoline = trampoline;
		if (hasSpring && hasTrampoline) {
			hasSpring = false;
			hasTrampoline = true;
		}
		if (jetpack) {
			hasSpring = false;
			hasTrampoline = false;
			hasJetpack = true;
		}
		if (prophat) {
			hasSpring = false;
			hasTrampoline = false;
			hasJetpack = false;
			hasProphat = true;
		}
	}
	
	public void setX(int x) {this.x = x;}
	public void setY(int y) {this.y = y;}
	public void setJetpack(boolean x) {hasJetpack = x;}
	public void setProphat(boolean x) {hasProphat = x;}
	
	public int getX() {return x;}
	public int getY() {return y;}
	public Rectangle getHitbox() {return hitbox;}
	public boolean getSpring() {return hasSpring;}
	public boolean getTrampoline() {return hasTrampoline;}
	public boolean hasAttribute() {return hasSpring||hasTrampoline||hasJetpack;}
	public boolean getJetpack() {return hasJetpack;}
	public boolean getProphat() {return hasProphat;}
	
	public void update(Player p) {
		hitbox.setLocation(x,p.maxHeight-y+600);
	}
	
	public void paint(Graphics g, boolean hitboxes, Player p) {
		Graphics2D g2 = (Graphics2D) g;
		update(p);
		tx = AffineTransform.getTranslateInstance(x, p.maxHeight-y+600);
		Sprite = getImage("Resources\\\\platformBreakable.png");
		g2.drawImage(Sprite, tx, null);
		
		if (hasSpring) {
			tx = AffineTransform.getTranslateInstance(x+22, p.maxHeight-y+600-24);
			Sprite = getImage("Resources\\\\spring.png");
			g2.drawImage(Sprite, tx, null);
		}
		
		if (hasTrampoline) {
			tx = AffineTransform.getTranslateInstance(x, p.maxHeight-y+600-16);
			Sprite = getImage("Resources\\\\trampoline.png");
			g2.drawImage(Sprite, tx, null);
		}
		
		if (hasJetpack) {
			tx = AffineTransform.getTranslateInstance(x+31, p.maxHeight-y+600-42);
			Sprite = getImage("Resources\\\\jetpack_Item.png");
			g2.drawImage(Sprite, tx, null);
		}
		
		if (hasProphat) {
			tx = AffineTransform.getTranslateInstance(x+20, p.maxHeight-y+600-30);
			Sprite = getImage("Resources\\\\prophat.png");
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
            URL imageURL = Platform.class.getResource(path);
            tempImage    = Toolkit.getDefaultToolkit().getImage(imageURL);
        } catch (Exception e) {e.printStackTrace();}
        return tempImage;
    }
}
