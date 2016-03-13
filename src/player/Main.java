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
	
	JFileChooser fc;
	
	TextArea upText, downText;
	
	private final String filePath = "../course-project1";
	public mainFrame(){
		File = new Menu ("File");
		open = new MenuItem ("open abc file");
		play = new MenuItem ("play music");
		File.add(open);
		File.add(play);
		
		fc = new JFileChooser();
		fc.setCurrentDirectory(new File(filePath, "sample_abc"));
		
		upText = new TextArea ();
		upText.setEditable(false);
		downText = new TextArea();
		downText.setEditable(false);
		
		this.add(upText, BorderLayout.PAGE_START);
		this.add(downText, BorderLayout.CENTER);
		
		myMenuBar.add(File);
		
		this.setMenuBar(myMenuBar);
		
		this.setTitle("ABC music player");
		this.setSize(600, 700);
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
				
				String line = null;
				
				try {
					BufferedReader readText = new BufferedReader(new FileReader(file));
					
					StringBuilder sb = new StringBuilder();
					try {
						line = readText.readLine();
						
						while (line != null){
							downText.setText(downText.getText()+ line + "\n");
							line = readText.readLine();
							
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				
			}
		}
		
	}
}
