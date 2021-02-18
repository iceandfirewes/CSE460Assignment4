import java.awt.*;
public class Spaceship implements Drawable  {
	//change this to decide where the spaceship is orginally
	private int positionX = 20;
	private int positionY = 700;
	@Override
	public void draw(Graphics g)
	{
		g.setColor(Color.MAGENTA);
		g.fillRect(positionX, positionY, 20, 20);
		if(positionX > 800) positionX = 800;
		if(positionX < 0 ) positionX = 0;
	}
	public void left()
	{
		positionX -= 15;
	}
	public void right()
	{
		positionX += 15;
	}

}
