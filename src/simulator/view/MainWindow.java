package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

import simulator.control.Controller;

public class MainWindow extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller _ctrl;
	
	public MainWindow(Controller ctrl) {
		super("Physics Simulator");
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		
		// TODO crear ControlPanel y a�adirlo en PAGE_START de mainPanel
		JPanel ControlPanel = new JPanel();
		mainPanel.add(ControlPanel, BorderLayout.PAGE_START);
		
		// TODO crear StatusBar y a�adirlo en PAGE_END de mainPanel
		JPanel StatusBar = new JPanel();
		mainPanel.add(StatusBar, BorderLayout.PAGE_END);
		
		// Definici�n del panel de tablas (usa un BoxLayout vertical)
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		mainPanel.add(contentPanel, BorderLayout.CENTER);
		
		// TODO crear la tabla de grupos y a�adirla a contentPanel.
		// Usa setPreferredSize(new Dimension(500, 250)) para fijar su tama�o
		InfoTable groupTable = new InfoTable("Groups", new GroupsTableModel(_ctrl));
		groupTable.setPreferredSize(new Dimension(500, 250));
		contentPanel.add(groupTable);
		
		// TODO crear la tabla de cuerpos y a�adirla a contentPanel.
		// Usa setPreferredSize(new Dimension(500, 250)) para fijar su tama�o
		InfoTable bodiesTable = new InfoTable("Bodies", new BodiesTableModel(_ctrl));
		bodiesTable.setPreferredSize(new Dimension(500, 200));
		contentPanel.add(bodiesTable);
		
		// TODO llama a Utils.quit(MainWindow.this) en el m�todo windowClosing
		//addWindowListener( � );
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		pack();
		setVisible(true);
	}
}
