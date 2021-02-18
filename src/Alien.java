import java.awt.Color;
import java.awt.Graphics;

public class Alien implements Drawable {
	private int positionX;
	private int positionY;
	
	public Alien(int X, int Y) {
		positionX = X;
		positionY = Y;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.ORANGE);
		g.drawRect(positionX, positionY, 20, 20);
	}
	
	public void moveLeft() {
		positionX -= 10;
	}
	
	public void moveRight() {
		positionX += 10;
	}
	
	public void moveDown() {
		positionY += 10;
	}
	
	public boolean collidesWithSprite(Sprite sprite) {
		double distance = Math.pow(sprite.getX() - positionX, 2) + Math.pow(sprite.getY() - positionY, 2);
		return Math.pow(distance, 1f/2f) < (sprite.getWidth() + 20);
	}
}
