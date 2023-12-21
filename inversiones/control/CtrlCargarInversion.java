package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.DtosInversiones;
import vista.Cargar;

public class CtrlCargarInversion implements ActionListener {
	
	private Cargar ventana;
	private DtosInversiones dtosInversiones;


	public CtrlCargarInversion(Cargar vista) {
		
		this.ventana = vista;
		this.dtosInversiones = new DtosInversiones();
		this.ventana.btnNuevo.addActionListener(this);
		this.ventana.btnGuardar.addActionListener(this);
		this.ventana.btnVolver.addActionListener(this);
	}
	
	public void iniciar() {

		ventana.scrollTabla.setVisible(false);
		
		
		
		
		
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == ventana.btnNuevo) {
			
			
		}
		
		if(e.getSource() == ventana.btnGuardar) {
			
			guardarCotizaciones();
		}
		
		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
	
	private void guardarCotizaciones() {
		
		
		
	}
}