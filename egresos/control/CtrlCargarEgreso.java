package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultComboBoxModel;
import modelo.DtosEgresos;
import vista.Cargar;

public class CtrlCargarEgreso implements ActionListener {

	private Cargar ventana;
	private DtosEgresos dtosEgreso;
	private int elemento = -1;


	public CtrlCargarEgreso(Cargar vista) {
		
		this.ventana = vista;
		this.dtosEgreso = new DtosEgresos();
		this.ventana.comboBoxPago.addActionListener(this);
		this.ventana.comboBoxTipo.addActionListener(this);
		this.ventana.comboBoxTipo.addActionListener(this);
		this.ventana.comboBoxPago.addActionListener(this);
		this.ventana.btnGuardar.addActionListener(this);
		this.ventana.btnNuevo.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
		this.ventana.tabla.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if(e.getClickCount() == 2) {
		        	
		        	elemento = ventana.tabla.getSelectedRow();
		        	
		        }
		    }
		});
	}
	
	public void iniciar() {

		
		ventana.comboBoxPago.setModel(new DefaultComboBoxModel<String>(dtosEgreso.getFormasPago()));
		ventana.comboBoxPago.setSelectedIndex(0);
		actualizar();
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnGuardar) {
			
			
			return;
		}
		
		if(e.getSource() == ventana.btnNuevo) {
			
			
			return;
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
			return;
		}
		
		if(ventana.isVisible())
			actualizar();
	}
	
	private void actualizar() {

		
	}
}
