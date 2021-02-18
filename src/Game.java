import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Game extends JFrame {
	private static final long serialVersionUID = -7803629994015778818L;

	public Game() {
		super("Space Invaders");
		
		this.setSize(800, 800);
		this.setResizable(false);
		this.setVisible(true);
		
		GameWindow gameWindow = new GameWindow();
		this.getContentPane().add(gameWindow);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Game();
			}
		});
	}
}