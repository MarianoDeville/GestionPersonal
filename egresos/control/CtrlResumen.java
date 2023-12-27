package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import modelo.DtosEgresos;
import vista.Resumen;

public class CtrlResumen implements ActionListener {
	
	private Resumen ventana;
	private DtosEgresos dtosEgreso;

	public CtrlResumen(Resumen vista) {
		
		this.ventana = vista;
		this.dtosEgreso = new DtosEgresos();
		this.ventana.btnImprimir.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
	}
	
	public void iniciar() {

		DefaultTableCellRenderer derecha = new DefaultTableCellRenderer();
		derecha.setHorizontalAlignment(JLabel.RIGHT);
		ventana.tabla.setModel(dtosEgreso.getResumen());
		ventana.tabla.getColumnModel().getColumn(0).setMinWidth(70);
		ventana.tabla.getColumnModel().getColumn(0).setMaxWidth(500);
		ventana.tabla.getColumnModel().getColumn(0).setPreferredWidth(350);
		ventana.tabla.getColumnModel().getColumn(1).setMinWidth(80);
		ventana.tabla.getColumnModel().getColumn(1).setMaxWidth(150);
		ventana.tabla.getColumnModel().getColumn(1).setPreferredWidth(90);
		ventana.tabla.getColumnModel().getColumn(1).setCellRenderer(derecha);
		ventana.tabla.setDefaultEditor(Object.class, null);
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.btnImprimir) {
			
			try {
				
				ventana.tabla.print();
			} catch (PrinterException f) {
				
				f.printStackTrace();
			}
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
}