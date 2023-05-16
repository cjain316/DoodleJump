import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.net.URL;
import java.awt.Rectangle;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.util.ArrayList;

public class Main extends JPanel implements KeyListener, ActionListener, MouseListener {
	private Player player = new Player();
	private int prevX;
	private ArrayList<Platform> platforms = new ArrayList<Platform>();
	
	private Image Sprite;
	private AffineTransform tx;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main f = new Main();
		
	}
	
	public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        prevX = player.getX();
        PointerInfo p = MouseInfo.getPointerInfo();
        Point point = p.getLocation();
        SwingUtilities.convertPointFromScreen(point, getFocusCycleRootAncestor());
        
        point.setLocation(point.getX()-35,point.getY()-30);
        
        if (point.getX() < 525 && point.getX() > 0) player.setX((int)point.getX());
        else if (point.getX() < 0) player.setX(0);
        else player.setX(525);
        
        player.setVy(player.getVy()-1);
        player.update();
        
        
        if (player.getX() > prevX) player.setFacing("Right");
        if (player.getX() < prevX) player.setFacing("Left");
        
        if (player.getVy() < 20) {
        	tx = AffineTransform.getTranslateInstance(player.getX(), 600);
        	if (player.getFacing().equals("Left")) {Sprite = getImage("Resources\\\\jumperFacingLeft.png");}
            else {Sprite = getImage("Resources\\\\jumperFacingRight.png");}
    		g2.drawImage(Sprite, tx, null);
        }
		System.out.println(player.getVy());
        
        
		for (int i = 0; i < platforms.size();i++) {
			g.setColor(new Color(0,0,0));
			platforms.get(i).update(player);
			
			g.fillRect(platforms.get(i).getX(), 600+platforms.get(i).getY()+player.getY(),70,10);
			
			if (colliding(platforms.get(i),player) && player.getVy() < 0) player.setVy(25);
			
			g.setColor(new Color(255,0,0)); //hitboxes
			g.drawRect(platforms.get(i).getX(),(int)platforms.get(i).getHitbox().getY(),70,10);
			g.drawRect(player.getX(),(int)player.getHitbox().getY(),60,60);
		}
		
		
	}
	
	
	public boolean colliding(Platform b, Player p) {
    	return b.getHitbox().intersects(p.getHitbox());
    }
	
	
	
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	Timer t;
	String[] JFrameNames = {"Doodle Jump","Doodle Jumpero"};
	
	
	public Main() {
    	
        JFrame f = new JFrame(JFrameNames[(int) (Math.random()*JFrameNames.length)]);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(600,1000);

        f.add(this);
        f.addMouseListener(this);
        f.addKeyListener(this);
        f.setResizable(false);
        //f.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        
        t = new Timer(1, this);
        t.start();
        f.setVisible(true);
        
        for (int i = 0; i < 1000; i ++) {
        	platforms.add(new Platform((int)(Math.random()*550),(int)(Math.random()*-35000)));
        }
        player.setY(0);
        
    }
	
	private void update() {

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

class Background {
	
}
