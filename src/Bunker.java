import java.awt.Color;
import java.awt.Graphics;

public class Bunker implements Drawable {
	private int health = 2;
	private int positionX;
	private int positionY;
	
	public Bunker(int X, int Y) {
		positionX = X;
		positionY = Y;
	}
	
	@Override
	public void draw(Graphics g) {
		switch (health) {
			case 2:
				g.setColor(Color.CYAN);
			case 1:
				g.setColor(Color.RED);
		}
		
		g.fillRect(positionX, positionY, 30, 30);
	}
}
