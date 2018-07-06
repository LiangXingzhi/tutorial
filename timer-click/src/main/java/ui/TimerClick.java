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
				frame.setVisible(true);
				//如果窗口被最小化，那么重新打开窗口
				// frame.setState(java.awt.Frame.ICONIFIED);
				frame.setState(java.awt.Frame.NORMAL);
				// 将窗口最大化
				frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
				// 将窗口作为当前窗口
				frame.toFront();
				int randomX = Math.abs(new Random().nextInt() % (frame.getWidth() - 31));
				randomX = randomX < 17 ? randomX + 17 : randomX;

				int randomY = Math.abs(new Random().nextInt() % (frame.getHeight() - 47));
				randomY = randomY < 41 ? randomY + 41 : randomY;
				robot.mouseMove(frame.getX() + randomX, frame.getY() + randomY);
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
		frame.setBounds(0, 0, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		textArea = new JTextArea();
		frame.getContentPane().add(textArea, BorderLayout.CENTER);
	}

}
