package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import modelo.DtosConfiguracion;
import vista.Listado;
import vista.Botones;

public class CtrlPrincipal implements ActionListener {
	
	private Botones ventana;
	private Listado ventanaEgresos;
	private Listado ventanaIngresos;
	private Listado ventanaInversiones;

	public CtrlPrincipal(Botones vista) {
		
		this.ventana = vista;
		this.ventana.btn1A.addActionListener(this);
		this.ventana.btn1B.addActionListener(this);
		this.ventana.btn1C.addActionListener(this);
		this.ventana.btn2A.addActionListener(this);
		this.ventana.btnSalir.addActionListener(this);
	}
	
	public void iniciar() {

		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.lbl1A.setText("Egresos");
		ventana.btn1A.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Compras.png"));
		ventana.lbl1B.setText("Ingresos");
		ventana.btn1B.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Pagos.png"));
		ventana.lbl1C.setText("Inversiones");
		ventana.btn1C.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Estadisticas.png"));
		ventana.lbl2A.setText("Análisis");
		ventana.btn2A.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Admin.png"));
		ventana.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.btn1A) {
			
			egresos();
		}
		
		if(e.getSource() == ventana.btn1B) {
			
			ingresos();
		}	

		if(e.getSource() == ventana.btn1C) {
			
			inversiones();
		}
		
		if(e.getSource() == ventana.btn2A) {
			
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
		
		if(ventanaIngresos != null) {
			
			ventanaIngresos.setVisible(true);
			return;
		}
		ventanaIngresos = new Listado("Gestión de ingresos", ventana.getX(), ventana.getY());
		CtrlIngresos ctrlIngresos = new CtrlIngresos(ventanaIngresos);
		ctrlIngresos.iniciar();		
	}
	
	private void inversiones() {
		
		if(ventanaInversiones != null) {
			
			ventanaInversiones.setVisible(true);
			return;
		}
		ventanaInversiones = new Listado("Gestión de inversiones", ventana.getX(), ventana.getY());
		CtrlInversiones ctrlInversiones = new CtrlInversiones(ventanaInversiones);
		ctrlInversiones.iniciar();	
	}
	
	private void analisis() {
		
	}
}