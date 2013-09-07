package tests;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;

import application.VideoField;

public class MonitorDetectionAndDisplay {
	
	static JComboBox cMonitorList;
	
	public static void main(String[] args) {
		GraphicsDevice[] monitorArray = GraphicsEnvironment	.getLocalGraphicsEnvironment().getScreenDevices(); //gets a list of all monitors
		JFrame gui = new JFrame("Chooser");
		cMonitorList = new JComboBox(monitorArray);
		JButton bDisplay = new JButton("Display");
		
		gui.setLayout(new GridLayout(2,0));
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		gui.add(cMonitorList);
		
		bDisplay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(cMonitorList.getSelectedItem());
				createFullscreen((GraphicsDevice)cMonitorList.getSelectedItem());
			}
		});
		
		gui.add(bDisplay);
		gui.pack();
		gui.setVisible(true);
	}
	
	static void createFullscreen(GraphicsDevice screen){
		JFrame frame = new JFrame("Fullscreen Display");		
		frame.setLocation(screen.getDefaultConfiguration().getBounds().getLocation()); //shoves the frame to the selected monitor
		frame.setUndecorated(true); //hides the window-borders and that stuff
		frame.setExtendedState(frame.MAXIMIZED_BOTH); //maximises the whole thing
		VideoField out = new VideoField( frame.getContentPane().getSize() );
		frame.getContentPane().add( out );
		frame.setVisible(true);
	}

}
