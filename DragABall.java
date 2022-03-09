
// going to be lazy about imports in these examples...
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * A program to demonstrate a simple dragging operation. Improved.
 * 
 * @author Jim Teresco
 * @version Spring 2022
 */

public class DragABall extends MouseAdapter implements Runnable {

	// ball size
	public static final int SIZE = 50;

	// current coordinates of the upper left corner of the circle
	private Point upperLeft = new Point(50, 50);

	// instead of a boolean to remember if we are dragging, we have
	// a variable that says where the mouse last was so we can move
	// the ball relative to that position, and this will be null
	// if the mouse is dragging but was not pressed on the circle
	private Point lastMouse;

	private JPanel panel;

	/**
	 * The run method to set up the graphical user interface
	 */
	@Override
	public void run() {

		// set up the GUI "look and feel" which should match
		// the OS on which we are running
		JFrame.setDefaultLookAndFeelDecorated(true);

		// create a JFrame in which we will build our very
		// tiny GUI, and give the window a name
		JFrame frame = new JFrame("DragABall");
		frame.setPreferredSize(new Dimension(500, 500));

		// tell the JFrame that when someone closes the
		// window, the application should terminate
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// JPanel with a paintComponent method
		panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {

				// first, we should call the paintComponent method we are
				// overriding in JPanel
				super.paintComponent(g);

				// redraw our circle at its current position
				g.fillOval(upperLeft.x, upperLeft.y, SIZE, SIZE);
			}
		};
		frame.add(panel);
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);

		// display the window we've created
		frame.pack();
		frame.setVisible(true);
	}

	// a method to determine if the given point is within the
	// circle as currently drawn
	protected boolean circleContains(Point p) {

		Point circleCenter = new Point(upperLeft.x + SIZE / 2, upperLeft.y + SIZE / 2);
		return circleCenter.distance(p) <= SIZE / 2;
	}

	@Override
	public void mousePressed(MouseEvent e) {

		// if we pressed within the circle, set up for dragging
		if (circleContains(e.getPoint())) {
			lastMouse = e.getPoint();
		} else {
			lastMouse = null;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		// if we are dragging (mouse press was in the circle), update
		// its position by the amount the mouse has moved since the
		// last press or drag event
		if (lastMouse != null) {
			int dx = e.getPoint().x - lastMouse.x;
			int dy = e.getPoint().y - lastMouse.y;
			upperLeft.translate(dx, dy);
			panel.repaint();
		}

		// think: why do we not need to set lastMouse to false here?
	}

	@Override
	public void mouseDragged(MouseEvent e) {

		// if we are dragging (mouse press was in the circle), update
		// its position by the amount the mouse has moved since the
		// last press or drag event
		if (lastMouse != null) {
			int dx = e.getPoint().x - lastMouse.x;
			int dy = e.getPoint().y - lastMouse.y;
			upperLeft.translate(dx, dy);
			lastMouse = e.getPoint();
			panel.repaint();
		}
	}

	public static void main(String args[]) {

		// The main method is responsible for creating a thread (more
		// about those later) that will construct and show the graphical
		// user interface.
		javax.swing.SwingUtilities.invokeLater(new DragABall());
	}
}
