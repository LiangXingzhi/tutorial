package e2j;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

public class E2J extends JPanel implements ActionListener, PropertyChangeListener {

	private JProgressBar progressBar;
	private JButton startButton;
	private JTextArea taskOutput;
	private Task task;
	private JTextField textFieldExcelPath;
	private JTextField textFieldJsonPath;
	private JRadioButton rdbtnVim;
	private JRadioButton rdbtnVnf;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	class Task extends SwingWorker<Void, Void> {
		/*
		 * Main task. Executed in background thread.
		 */
		@Override
		public Void doInBackground() {
			// Initialize progress property.
			setProgress(0);

			String excelPath = textFieldExcelPath.getText();
			File excel = new File(excelPath);
			
			if (!excel.exists()) {
				taskOutput.append("Excel file ["+excelPath+"] not exist! \n");
				return null;
			}
			setProgress(10);
			String jsonPath = textFieldJsonPath.getText();
			File json = new File(jsonPath);
			if (json.exists()) {
				json.delete();
			}
			setProgress(20);
			boolean jsonCreated = false;
			try {
				jsonCreated = json.createNewFile();
			} catch (IOException e1) {
				taskOutput.append("Error create json file!\n");
				return null;
			}
			if (!jsonCreated) {
				taskOutput.append("Json file can not be created! \n");
			}
			
			setProgress(30);
			Map<String, Object> map = new LinkedHashMap<String, Object>();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	        String suffix = formatter.format(LocalDateTime.now());
			map.put("vim_zone_id", "vim" + suffix);
			map.put("vim_name", "vim-" + suffix);
			setProgress(40);
			try {
				map.put("design_parameters", ExcelUtil.readExcel(excel));
			} catch (Throwable e1) {
				e1.printStackTrace();
				taskOutput.append("Error reading excel file!\n");
				return null;
			}
			setProgress(80);

			JsonUtil.fromObject(map, json);
			setProgress(90);
			
			setProgress(100);
			return null;
		}

		/*
		 * Executed in event dispatching thread
		 */
		@Override
		public void done() {
			Toolkit.getDefaultToolkit().beep();
			startButton.setEnabled(true);
			setCursor(null); // turn off the wait cursor
			taskOutput.append("Done!\n");
		}
	}

	public void getPath() {
		Path currentWorkingDir = Paths.get("inventory2.xlsx").toAbsolutePath();
		taskOutput.append("url:"+currentWorkingDir.normalize().toString()+"\n");
	}
	public E2J() {
		super(new BorderLayout());

		Box box1, box2, boxBase, box3;
		boxBase = Box.createHorizontalBox();
		box1 = Box.createVerticalBox();
		JLabel label = new JLabel("Inventory Type");
		Dimension labelDimension = new Dimension(90,30);
		label.setMaximumSize(labelDimension);
		label.setMinimumSize(labelDimension);
		box1.add(label);
		box1.add(Box.createVerticalStrut(10));
		label=new JLabel("Excel Path");
		label.setMaximumSize(labelDimension);
		label.setMinimumSize(labelDimension);
		box1.add(label);
		box1.add(Box.createVerticalStrut(10));
		label = new JLabel("Json Path");
		label.setMaximumSize(labelDimension);
		label.setMinimumSize(labelDimension);
		box1.add(label);
		box1.add(Box.createVerticalStrut(10));

		// Create the demo's UI.
		startButton = new JButton("Start");
		startButton.setActionCommand("start");
		startButton.addActionListener(this);

		box1.add(startButton);

		box2 = Box.createVerticalBox();
		box3 = Box.createHorizontalBox();
		box3.setAlignmentX(BoxLayout.LINE_AXIS);
		box3.setMaximumSize(new Dimension(360,30));
		box3.setMinimumSize(new Dimension(360,30));
		rdbtnVim = new JRadioButton("VIM");
		rdbtnVim.setMaximumSize(new Dimension(150,30));
		rdbtnVim.setMinimumSize(new Dimension(150,30));
		
		rdbtnVim.setSelected(true);
		rdbtnVnf = new JRadioButton("VNF");
		rdbtnVnf.setMaximumSize(new Dimension(150,30));
		rdbtnVnf.setMinimumSize(new Dimension(150,30));
		box3.add(rdbtnVim);
		buttonGroup.add(rdbtnVim);
		box3.add(rdbtnVnf);
		buttonGroup.add(rdbtnVnf);
		box2.add(box3);
		textFieldExcelPath = new JTextField();
		textFieldExcelPath.setMaximumSize(new Dimension(550,30));
		textFieldExcelPath.setMinimumSize(new Dimension(550,30));
		
		box2.add(textFieldExcelPath);
		box2.add(Box.createVerticalStrut(10));
		textFieldJsonPath = new JTextField();
		textFieldJsonPath.setMaximumSize(new Dimension(360,30));
		textFieldJsonPath.setMinimumSize(new Dimension(360,30));
		box2.add(textFieldJsonPath);
		box2.add(Box.createVerticalStrut(10));

		progressBar = new JProgressBar(0, 100);
		progressBar.setValue(0);
		progressBar.setStringPainted(true);
		progressBar.setMaximumSize(new Dimension(360,30));
		progressBar.setMinimumSize(new Dimension(360,30));
		box2.add(progressBar);
		
		box2.add(Box.createVerticalStrut(10));
		box1.add(Box.createVerticalStrut(10));

		boxBase.add(box1);
		boxBase.add(box2);

		taskOutput = new JTextArea(5, 20);
		taskOutput.setMargin(new Insets(5, 5, 5, 5));
		taskOutput.setEditable(false);

		add(boxBase, BorderLayout.CENTER);

		add(new JScrollPane(taskOutput), BorderLayout.PAGE_END);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

	}

	/**
	 * Invoked when the user presses the start button.
	 */
	public void actionPerformed(ActionEvent evt) {
		startButton.setEnabled(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		// Instances of javax.swing.SwingWorker are not reusuable, so
		// we create new instances as needed.
		task = new Task();
		task.addPropertyChangeListener(this);
		task.execute();
	}

	/**
	 * Invoked when task's progress property changes.
	 */
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress" == evt.getPropertyName()) {
			int progress = (Integer) evt.getNewValue();
			progressBar.setValue(progress);
			taskOutput.append(String.format("Completed %d%% of task.\n", task.getProgress()));
		}
	}

	/**
	 * Create the GUI and show it. As with all GUI code, this must run on the
	 * event-dispatching thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("Excel 2 JSON");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMaximumSize(new Dimension(480, 320));
		frame.setMinimumSize(new Dimension(480, 320));
		frame.setSize(new Dimension(480, 320));
		frame.setLocation(500, 400);
		frame.setResizable(false);
		// Create and set up the content pane.
		JComponent newContentPane = new E2J();
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}