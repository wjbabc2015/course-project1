package player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import java.io.*;

import sound.SequencePlayer;

/**
 * Main entry point of your application.
 */
public class Main {

	public Main(){

	}
	/**
	 * Plays the input file using Java MIDI API and displays
	 * header information to the standard output stream.
	 * 
	 * <p>Your code <b>should not</b> exit the application abnormally using
	 * System.exit()</p>
	 * 
	 * @param file the name of input abc file
	 */
	public static void play(String file) {
		// YOUR CODE HERE
		String[] args = null;
		SequencePlayer.main(args);
	}

	public static void main(String[] args){
		mainFrame mf = new mainFrame();
		mf.setVisible(true);
	}
}

class mainFrame extends JFrame implements ActionListener{

	JLabel title;

	JFileChooser fc;
	JPanel p1, p2;

	TextArea info;
	JButton open, play;
	
	String ABCcontent;

	private final String filePath = "../course-project1";
	public mainFrame(){

		p1 = new JPanel();
		p2 = new JPanel();

		title = new JLabel ("MIT Class 6.005 ---- Project 1");
		title.setFont(new Font("Serif", Font.ITALIC, 30));
		p1.add(title, BorderLayout.CENTER);

		fc = new JFileChooser();
		fc.setCurrentDirectory(new File(filePath, "sample_abc"));

		info = new TextArea ();
		info.setEditable(false);

		open = new JButton("Open ABC File");
		play = new JButton("Play Music");
		p2.add(open, BorderLayout.LINE_START);
		p2.add(play, BorderLayout.LINE_END);

		this.setTitle("ABC music player");
		this.setSize(600, 500);
		this.setLocation(300, 200);
		this.setVisible(true);

		this.add(p1, BorderLayout.NORTH);
		this.add(info, BorderLayout.CENTER);
		this.add(p2, BorderLayout.SOUTH);

		open.addActionListener(this);
		play.addActionListener(this);
		this.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent arg0){
				System.exit(0);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==open){
			ABCcontent = fileReader();
			JOptionPane.showMessageDialog(null, "Sucessfully open");
			info.setText(ABCcontent);
		}

		if (e.getSource()==play){
			Main.play(null);
		}
	}
	
	private String fileReader(){
		StringBuilder content = new StringBuilder();
		FileReader fr = null;
		
		int returnVal = fc.showOpenDialog(this);
		
		if (returnVal == JFileChooser.APPROVE_OPTION){
			File file = fc.getSelectedFile();
			try {
				fr = new FileReader(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		
			BufferedReader reader = new BufferedReader (fr);
			String line = "";
			try {	
				while ((line = reader.readLine()) != null){
					content.append(line + "\n");
				}
				
				reader.close();
				fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return content.toString();
		
	}
}
