import java.awt.Color;
import java.awt.Font;
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
	private Font font = new Font(Font.DIALOG_INPUT, Font.BOLD, 32);
	
	private int frames;
	private int prevScore = calculateScore();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Main f = new Main();
		
	}
	
	public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        updatePlatforms();
 
        frames++;
        drawBackground(g);
        
        prevX = player.getX();
        PointerInfo p = MouseInfo.getPointerInfo();
        Point point = p.getLocation();
        SwingUtilities.convertPointFromScreen(point, getFocusCycleRootAncestor());
        
        point.setLocation(point.getX()-35,point.getY()-30);
        
        //if (point.getX() < 525 && point.getX() > 0) player.setX((int)point.getX());
        //else if (point.getX() < 0) player.setX(0);
        //else player.setX(525);
        
        player.setVy(player.getVy()-1);
        player.update(point);
        
        
        if (player.getX() > prevX) player.setFacing("Right");
        if (player.getX() < prevX) player.setFacing("Left");
        
        if (player.getVy() < 20) {
        	tx = AffineTransform.getTranslateInstance(player.getX(), 600);
        	if (player.getFacing().equals("Left")) {Sprite = getImage("Resources\\\\jumperFacingLeft.png");}
            else {Sprite = getImage("Resources\\\\jumperFacingRight.png");}
    		g2.drawImage(Sprite, tx, null);
        }
		//System.out.println(player.getVy());
        
        
		for (int i = 0; i < platforms.size();i++) { //platforms
			platforms.get(i).paint(g,false,player);
			
			if (colliding(platforms.get(i),player) && player.getVy() < 0) player.setVy(25);
		}
		
		drawOverlay(g);
		
		g.setFont(font);
		
		System.out.println(platforms.size());
		if (frames > 1000) frames = 0;
		g.setColor(new Color(255,255,255));
		int a = calculateScore();
		g.drawString(a+"", 10,30);
		prevScore = a;
	}
	
	public void drawBackground(Graphics g) {
		g.setColor(new Color(209, 197, 33));
		g.fillRect(0,0,2000,2000);
		g.setColor(new Color(13, 104, 189));
		for (int i = 0; i < 200; i++) {
			g.drawLine(0,i*20,2000,i*20);
		}
		g.setColor(new Color(194, 53, 35));
		g.drawLine(75,0,75,2000);
	}
	
	public void drawOverlay(Graphics g) {
		g.setColor(new Color(145, 42, 29));
		g.fillRect(0,0,2000,61);
	}
	
	public boolean colliding(Platform b, Player p) {
    	return b.getHitbox().intersects(p.getHitbox());
    }
	
	public int calculateScore() {
		int score = player.getY()/10; 
		
		
		if (prevScore < score) return score;
		else return prevScore;
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
	
	
	//*************************************
	//*        Platform Generation        *
	//*************************************
	
	
	public void unloadUnusedPlatformsL() {
		int i = 0;
		while (i < platforms.size()) {
			if (600+platforms.get(i).getY()+player.getY() > 1500) {
				platforms.remove(i);
			} else {
				i++;
			}
		}
	}
	
	public void unloadUnusedPlatformsU() {
		int i = 0;
		while (i < platforms.size()) {
			if (platforms.get(i).getY()*-1-(player.getY()+1000) > 1500) {
				platforms.remove(i);
			} else {
				i++;
			}
		}
	}
	
	public int getHighestPlatform() { // returns the INDEX of the highest platform
		if (platforms.size() == 0) return 0;
		Platform highest = platforms.get(0);
		int index = 0;
		for (int i = 0; i < platforms.size();i++) {
			if (platforms.get(i).getY() < highest.getY()) {
				highest = platforms.get(i);
				index = i;
			}
		}
		return index;
	}
	
	public int getLowestPlatform() {
		Platform lowest = platforms.get(0);
		int index = 0;
		for (int i = 0; i > platforms.size();i++) {
			if (platforms.get(i).getY() < lowest.getY()) {
				lowest = platforms.get(i);
				index = i;
			}
		}
		return index;
	}
	
	public void generatePlatforms() {
		if (platforms.size() == 0) platforms.add(new Platform(-10,300));
		int y = player.getY()+600;
		int h = getHighestPlatform();
		System.out.println("Highest: " + platforms.get(h).getY() + "\nPlayer: " + y);
		if (platforms.get(h).getY() < (y+1000)) {
			platforms.add(new Platform((int)(Math.random()*530),platforms.get(h).getY()-100));
		}
	}
	
	
	
	public void updatePlatforms() {
		if (frames%10==0) {
			generatePlatforms();
			unloadUnusedPlatformsL();
			unloadUnusedPlatformsU();
		}
	}
	
	
	
	
	
	
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
        
        player.setY(0);
        platforms.add(new Platform(-10,300));
        
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
