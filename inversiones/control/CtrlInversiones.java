package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import modelo.DtosConfiguracion;
import vista.Botones;
import vista.Listado;

public class CtrlInversiones implements ActionListener {
	
	private Botones ventana;
	private Listado ventanaMercadoValores;
	private Listado ventanaPropiedades;
	private Listado ventanaCripto;
	private Listado ventanaFiat;
	private Listado ventanaResumen;

	public CtrlInversiones(Botones vista) {
		
		this.ventana = vista;
		this.ventana.btn1A.addActionListener(this);
		this.ventana.btn1B.addActionListener(this);
		this.ventana.btn1C.addActionListener(this);
		this.ventana.btn2A.addActionListener(this);
		this.ventana.btn2B.addActionListener(this);
		this.ventana.btnSalir.addActionListener(this);
	}
	
	public void iniciar() {

		ventana.lbl1A.setText("Mercado de valores");
		ventana.btn1A.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Bolsa.png"));
		ventana.btn1A.setVisible(true);
		ventana.lbl1B.setText("Cripto monedas");
		ventana.btn1B.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Cripto.png"));
		ventana.btn1B.setVisible(true);
		ventana.lbl1C.setText("Fiat");
		ventana.btn1C.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Fiat.png"));
		ventana.btn1C.setVisible(true);
		ventana.lbl2A.setText("Inmobiliario");
		ventana.btn2A.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Inmobiliario.png"));
		ventana.btn2A.setVisible(true);
		ventana.lbl2B.setText("Resumen");
		ventana.btn2B.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Resumen.png"));
		ventana.btn2B.setVisible(true);
		ventana.btnSalir.setIcon(new ImageIcon(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Volver.png"));
		ventana.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.btn1A) {
			
			mercadoValores();
		}
		
		if(e.getSource() == ventana.btn1B) {
			
			cripto();
		}	

		if(e.getSource() == ventana.btn1C) {
			
			fiat();
		}
		
		if(e.getSource() == ventana.btn2A) {
			
			inmobiliario();
		}
		
		if(e.getSource() == ventana.btn2B) {
			
			resumen();
		}
		
		if(e.getSource() == ventana.btnSalir) {
			
			if(ventanaMercadoValores != null)
				ventanaMercadoValores.dispose();
			
			if(ventanaPropiedades != null)
				ventanaPropiedades.dispose();
			
			if(ventanaCripto != null)
				ventanaCripto.dispose();
			
			if(ventanaFiat != null)
				ventanaFiat.dispose();
			
			if(ventanaResumen != null)
				ventanaResumen.dispose();
			ventana.dispose();
		}
	}

	private void mercadoValores() {
		
		if(ventanaMercadoValores != null)
			ventanaMercadoValores.dispose();
		ventanaMercadoValores = new Listado("Gestión de inversiones", ventana.getX(), ventana.getY());
		CtrlMercadoValores ctrlMercadoValores = new CtrlMercadoValores(ventanaMercadoValores);
		ctrlMercadoValores.iniciar();
	}

	private void cripto() {
		
		
	}
	
	private void fiat() {
		
		
	}
	
	private void inmobiliario() {
		
	
	}

	private void resumen() {
		
		
	}
}