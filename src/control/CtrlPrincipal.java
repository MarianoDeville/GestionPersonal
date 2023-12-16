package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import vista.Listado;
import vista.Principal;

public class CtrlPrincipal implements ActionListener {
	
	private Principal ventana;
	private Listado ventanaEgresos;

	public CtrlPrincipal(Principal vista) {
		
		this.ventana = vista;
		this.ventana.btnEgresos.addActionListener(this);
		this.ventana.btnIngresos.addActionListener(this);
		this.ventana.btnInversiones.addActionListener(this);
		this.ventana.btnAnalisis.addActionListener(this);
		this.ventana.btnSalir.addActionListener(this);
	}
	
	public void iniciar() {

		ventana.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.btnEgresos) {
			
			egresos();
		}
		
		if(e.getSource() == ventana.btnIngresos) {
			
			ingresos();
		}	

		if(e.getSource() == ventana.btnInversiones) {
			
			inversiones();
		}
		
		if(e.getSource() == ventana.btnAnalisis) {
			
			analisis();
		}
		
		if(e.getSource() == ventana.btnSalir) {
			
			System.exit(0);
		}
	}

	private void egresos() {
		
		if(ventanaEgresos != null) {
			
			ventanaEgresos.setVisible(true);
			return;
		}
		ventanaEgresos = new Listado("Gestión de egresos", ventana.getX(), ventana.getY());
		CtrlEgresos ctrlEgresos = new CtrlEgresos(ventanaEgresos);
		ctrlEgresos.iniciar();
	}
	
	private void ingresos() {
		
	}
	
	private void inversiones() {
		
	}
	
	private void analisis() {
		
	}
}