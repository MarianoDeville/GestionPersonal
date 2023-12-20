package vista;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import modelo.DtosConfiguracion;

public abstract class VentanaModelo extends JFrame {

	private static final long serialVersionUID = 1L;

	public VentanaModelo(String nombreVentana) {
		
		setTitle(nombreVentana);
		setIconImage(Toolkit.getDefaultToolkit().getImage(DtosConfiguracion.getDirectorio() + "\\Imagenes\\Gestion.png"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(10, 10, 800, 600);
		setMinimumSize(new Dimension(640, 480));
	}
}