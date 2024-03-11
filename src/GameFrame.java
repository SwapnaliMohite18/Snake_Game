import javax.swing.JFrame;//using create GUI applications with Java Swing that have windows, buttons, menus, and other interactive components.

public class GameFrame extends JFrame {

	GameFrame(){
		
		this.add(new GamePanel());
		this.setTitle("Snake");
		// indicates that the application should exit and terminate when the JFrame is closed by the user
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// JFrame cannot be resized by the user.
		this.setResizable(true);
		//It causes the window to be sized to fit the preferred size of its components and layout.
		this.pack();
		// make a graphical component, such as a JFrame, visible on the screen.
		this.setVisible(true);
		// it positions the frame at the center of the screen, regardless of the screen's resolution or size.
		this.setLocationRelativeTo(null);
		
	}
}
