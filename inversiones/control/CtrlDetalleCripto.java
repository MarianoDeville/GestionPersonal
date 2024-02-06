package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.DtosComunes;
import modelo.DtosMercadoCripto;
import vista.Cargar;

public class CtrlDetalleCripto implements ActionListener {
	
	private Cargar ventana;
	private DtosMercadoCripto dtosMercadoCripto;

	public CtrlDetalleCripto(Cargar vista) {
		
		this.ventana = vista;
		this.dtosMercadoCripto = new DtosMercadoCripto();
		this.ventana.btnVolver.addActionListener(this);
	}
	
	public void iniciar() {

		ventana.txtFecha.setEditable(false);
		ventana.txtFecha.setText(DtosComunes.getFechaActual());
		ventana.lblProv.setText("Designación:");
		ventana.txtProv.setEditable(false);

		
		
		ventana.lblFormPago.setText("Custodia:");
		ventana.cmbBxPago.setVisible(false);
		ventana.txtPago.setVisible(true);
		ventana.txtPago.setEditable(false);


		
		ventana.lblTipo.setText("Instrumento:");
		ventana.cmbBxTipo.setVisible(false);
		ventana.txtTipo.setVisible(true);
		ventana.txtTipo.setEditable(false);

		
		ventana.lblMonto.setText("Cantidad:");
		ventana.txtMonto.setEditable(false);

		
		ventana.cmbBxMoneda.setVisible(false);
		ventana.lblCotizacion.setVisible(false);
		ventana.txtCotizacion.setVisible(false);
		ventana.lblComentario.setVisible(false);
		ventana.txtComentario.setVisible(false);
		ventana.btnGuardar.setVisible(false);
		ventana.btnNuevo.setVisible(false);
		ventana.tabla.setDefaultEditor(Object.class, null);
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}

}