package player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.io.*;

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
	}
	
	public static void main(String[] args){
		mainFrame mf = new mainFrame();
		mf.setVisible(true);
	}
}

class mainFrame extends JFrame implements ActionListener{
	
	MenuBar myMenuBar = new MenuBar ();
	Menu File;
	MenuItem open, play;
	
	JPanel p;
	JFileChooser fc;
	
	TextArea upText, downText;
	
	private final String filePath = "../course-project1";
	public mainFrame(){
		File = new Menu ("File");
		open = new MenuItem ("open abc file");
		play = new MenuItem ("play music");
		File.add(open);
		File.add(play);
		
		p = new JPanel();
		fc = new JFileChooser();
		fc.setCurrentDirectory(new File(filePath, "sample_abc"));
		
		upText = new TextArea ();
		upText.setEditable(false);
		downText = new TextArea();
		downText.setEditable(false);
		
		p.add(upText, BorderLayout.NORTH);
		p.add(downText, BorderLayout.SOUTH);
		
		myMenuBar.add(File);
		
		this.setMenuBar(myMenuBar);
		this.add(p);
		
		this.setTitle("ABC music player");
		this.setSize(600, 500);
		this.setLocation(300, 200);
		this.setVisible(true);
		
		open.addActionListener(this);
		play.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==open){
			int returnVal = fc.showOpenDialog(this);
			
			if (returnVal == JFileChooser.APPROVE_OPTION){
				File file = fc.getSelectedFile();
			}
		}
		
	}
}
