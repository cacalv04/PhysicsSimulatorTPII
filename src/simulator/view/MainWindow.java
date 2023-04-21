package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import simulator.control.Controller;

public class MainWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller _ctrl;
	private ControlPanel controlPanel;
	private StatusBar statusBar;
	
	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		
		// TODO crear ControlPanel y añadirlo en PAGE_START de mainPanel
		controlPanel = new ControlPanel(_ctrl);
		mainPanel.add(controlPanel, BorderLayout.PAGE_START);
		
		// TODO crear StatusBar y añadirlo en PAGE_END de mainPanel
		statusBar = new StatusBar(_ctrl);
		mainPanel.add(statusBar, BorderLayout.PAGE_END);
		
		// Definición del panel de tablas (usa un BoxLayout vertical)
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		mainPanel.add(contentPanel, BorderLayout.CENTER);
		
		// TODO crear la tabla de grupos y añadirla a contentPanel.
		// Usa setPreferredSize(new Dimension(500, 250)) para fijar su tamaño
		InfoTable groupTable = new InfoTable("Groups", new GroupsTableModel(_ctrl));
		groupTable.setPreferredSize(new Dimension(500, 250));
		contentPanel.add(groupTable);
		
		// TODO crear la tabla de cuerpos y añadirla a contentPanel.
		// Usa setPreferredSize(new Dimension(500, 250)) para fijar su tamaño
		InfoTable bodiesTable = new InfoTable("Bodies", new BodiesTableModel(_ctrl));
		bodiesTable.setPreferredSize(new Dimension(500, 250));
		contentPanel.add(bodiesTable);
		
		// TODO llama a Utils.quit(MainWindow.this) en el método windowClosing
		addWindowListener( new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				Utils.quit(MainWindow.this);
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}});
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
