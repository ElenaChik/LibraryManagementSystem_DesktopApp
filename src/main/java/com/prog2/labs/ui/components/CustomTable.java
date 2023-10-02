package com.prog2.labs.ui.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 * The CustomTable class.
 * 		extends JTable
 *  Customization for JTable to support colorized fields
 *
 * @author <a href="mailto:Chailikeg@yahoo.com">Olena Chailik</a>
 */
public class CustomTable extends JTable {
	
	private static final long serialVersionUID = 1L;
	
	private Color color;
	
	public CustomTable(DefaultTableModel model, Color color) {
		super(model);
		this.color = color;
	}

	/**
	 * Method prepareRenderer
	 *  implements colorization for JTable instance
	 * 
	 * @param renderer
	 * @param row
	 * @param column
	 * @return Component
	 */
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);
        if (!isRowSelected(row)) { //  Alternate row color
            c.setBackground(row % 2 == 0 ? getBackground() : color);
        }
        return c;
    }

    /**
     * Method setTableColumnInvisible
     *   makes choosen JTable column invisible
     * @param index
     */
	public void setTableColumnInvisible(int index) {
		this.getColumnModel().getColumn(index).setWidth(0);
		this.getColumnModel().getColumn(index).setMinWidth(0);
		this.getColumnModel().getColumn(index).setMaxWidth(0);
	}
}
