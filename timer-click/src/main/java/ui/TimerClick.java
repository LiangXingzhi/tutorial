package ui;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.apache.commons.lang3.StringUtils;

public class TimerClick {

	protected static final long TIMER_SCHEDULE = 1000 * 10;
	private JFrame frame;
	private JTextArea textArea;
	private JPanel jpanel;
	private JButton startButton;
	private JButton stopButton;
	private Timer timer = new Timer();
	private volatile boolean started = false;
	TimerTask task = new CustomTimerTask(this);
	
	static class CustomTimerTask extends TimerTask {
		private static int count = 0;
		TimerClick timerClick;
		public CustomTimerTask(TimerClick timerClick) {
			this.timerClick = timerClick;
		}

		@Override
		public void run() {
			try {
				if (timerClick.started) {
					Robot robot = new Robot();
					// MouseInfo.getPointerInfo().getLocation()
					// + new Random().nextInt() % frame.getHeight()
					timerClick.frame.setVisible(true);
					// 如果窗口被最小化，那么重新打开窗口
					// frame.setState(java.awt.Frame.ICONIFIED);
					timerClick.frame.setState(java.awt.Frame.NORMAL);
					// 将窗口最大化
					// frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
					// 将窗口作为当前窗口
					timerClick.frame.toFront();
					int randomX = Math.abs(new Random().nextInt() % (timerClick.frame.getWidth() - 31));
					randomX = randomX < 17 ? randomX + 17 : randomX;

					int randomY = Math.abs(new Random().nextInt() % (timerClick.frame.getHeight() - 47));
					randomY = randomY < 41 ? randomY + 41 : randomY;
					robot.mouseMove(timerClick.frame.getX() + randomX, timerClick.frame.getY() + randomY);
					robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
					robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
					int matches = StringUtils.countMatches(timerClick.textArea.getText(), "\n");
					if(matches == 10) {
						timerClick.textArea.setText("clear screen after 10 times click");
					}
					if(StringUtils.isEmpty(timerClick.textArea.getText())) {
						timerClick.textArea.setText("Auto click started...");
					}
					String text = timerClick.textArea.getText() + "\n" + LocalDateTime.now().toString() + "\t" + ++count + " times auto click";
					timerClick.textArea.setText(text);
				}
			} catch (AWTException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		System.out.println(TimerClick.class.getClassLoader().getResource(""));
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TimerClick window = new TimerClick();
					window.frame.setVisible(true);
					window.timer.schedule(window.task, 0, TIMER_SCHEDULE);
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
		jpanel = new JPanel();
		frame.getContentPane().add(textArea, BorderLayout.CENTER);
		frame.getContentPane().add(jpanel, BorderLayout.SOUTH);
		jpanel.setLayout(new GridLayout(1, 2, 5, 5));
		startButton = new JButton("Start");
		stopButton = new JButton("Stop");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (started) {
					return;
				}
				started = true;
				startButton.setEnabled(false);
				stopButton.setEnabled(true);
				timer.schedule(new CustomTimerTask(TimerClick.this), 0);
			}
		});
		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!started) {
					return;
				}
				started = false;
				startButton.setEnabled(true);
				stopButton.setEnabled(false);
			}

		});
		jpanel.add(startButton);
		jpanel.add(stopButton);
		stopButton.setEnabled(false);
	}

}
