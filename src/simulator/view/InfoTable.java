package simulator.view;

import javax.swing.JPanel;
import javax.swing.table.TableModel;

public class InfoTable extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String _title;
	TableModel _tableModel;
	
	InfoTable (String title, TableModel tableModel){
		_title = title;
		_tableModel = tableModel;
		initGUI();
	}
	
	private void initGUI() {
		// TODO cambiar el layout del panel a BorderLayout()
		// TODO a�adir un borde con t�tulo al JPanel, con el texto _title
		// TODO a�adir un JTable (con barra de desplazamiento vertical) que use
		// _tableModel
	}
}
