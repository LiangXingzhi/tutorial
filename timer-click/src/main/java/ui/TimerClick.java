package ui;


import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.MouseInfo;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class TimerClick {

	private JFrame frame;
	private JTextArea textArea;
	private final Timer timer = new Timer();
	TimerTask task = new TimerTask() {
		@Override
		public void run() {
			try {
				Robot robot = new Robot();
				// MouseInfo.getPointerInfo().getLocation()
				// + new Random().nextInt() % frame.getHeight()
				robot.mouseMove(frame.getX() + 30 + Math.abs(new Random().nextInt() % 211),
						frame.getY() + 30 + Math.abs(new Random().nextInt() % 211));
				robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
				robot.delay(1000);
				robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
				textArea.setText(LocalDateTime.now().toString() + "\n" + MouseInfo.getPointerInfo().getLocation() + "\n"
						+ frame.getBounds());
			} catch (AWTException e) {
				e.printStackTrace();
			}

		}
	};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TimerClick window = new TimerClick();
					window.frame.setVisible(true);
					window.timer.schedule(window.task, 10, 1000 * 5);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TimerClick() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		textArea = new JTextArea();
		frame.getContentPane().add(textArea, BorderLayout.CENTER);
	}

}
