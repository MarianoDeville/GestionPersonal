package control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.DtosComunes;
import modelo.DtosMercadoValores;
import vista.Cargar;

public class CtrlDetalleValores implements ActionListener {
	
	private Cargar ventana;
	private DtosMercadoValores dtosMercadoValores;

	public CtrlDetalleValores(Cargar vista) {
		
		this.ventana = vista;
		this.dtosMercadoValores = new DtosMercadoValores();
		this.ventana.btnVolver.addActionListener(this);
	}
	
	public void iniciar() {

		ventana.txtFecha.setEditable(false);
		ventana.txtFecha.setText(DtosComunes.getFechaActual());
		ventana.lblProv.setText("Designación:");
		ventana.txtProv.setEditable(false);
		ventana.txtProv.setText(dtosMercadoValores.getNombreValor());
		ventana.lblFormPago.setText("Custodia:");
		ventana.cmbBxPago.setVisible(false);
		ventana.txtPago.setVisible(true);
		ventana.txtPago.setEditable(false);
		ventana.txtPago.setText(dtosMercadoValores.getCustodiaValor());
		ventana.lblTipo.setText("Instrumento:");
		ventana.cmbBxTipo.setVisible(false);
		ventana.txtTipo.setVisible(true);
		ventana.txtTipo.setEditable(false);
		ventana.txtTipo.setText(dtosMercadoValores.getTipoInstrumento());
		ventana.lblMonto.setText("Cantidad:");
		ventana.txtMonto.setEditable(false);
		ventana.txtMonto.setText(dtosMercadoValores.getCantValor());
		ventana.cmbBxMoneda.setVisible(false);
		ventana.lblCotizacion.setText("cotización:");
		ventana.txtCotizacion.setEditable(false);
		ventana.lblComentario.setText("Fecha");
		ventana.txtComentario.setEditable(false);
		ventana.txtComentario.setColumns(6);
		ventana.lblAux1.setVisible(true);
		ventana.lblAux1.setText("Valorización: ");
		ventana.txtAux1.setVisible(true);
		ventana.txtAux1.setEditable(false);
		ventana.btnGuardar.setVisible(false);
		ventana.btnNuevo.setVisible(false);
		ventana.tabla.setDefaultEditor(Object.class, null);
		ventana.tabla.setModel(dtosMercadoValores.getListadoOperaciones());
		ventana.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == ventana.btnVolver) {
			
			ventana.dispose();
		}
	}
}