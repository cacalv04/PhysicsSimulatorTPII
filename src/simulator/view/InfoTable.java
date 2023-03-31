package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
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
		setLayout(new BorderLayout());
		
		// TODO a�adir un borde con t�tulo al JPanel, con el texto _title
		setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.black, 2),
				_title,
				TitledBorder.LEFT, TitledBorder.TOP));
		
		// TODO a�adir un JTable (con barra de desplazamiento vertical) que use
		// _tableModel
		JTable tm = new JTable(_tableModel);
		JScrollPane scroll = new JScrollPane(tm, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(scroll);
	}
}
