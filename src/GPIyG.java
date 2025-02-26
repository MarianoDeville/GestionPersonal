
/********************************************************************************/
/*			Sistema registral personal de Ingresos Egresos e Inversiones		*/
/*------------------------------------------------------------------------------*/
/*		Revisión:				1.00											*/
/*		IDE:					Eclipse IDE Ver. 2023-09 (4.29.0)				*/
/*		Lenguaje:				Java SE-20										*/
/*		Versionado:				git - GitHub.com/MarianoDeville/ERP-LECSys		*/
/*		Base de Datos:			MySQL Workbench 8.00 CE							*/
/*		Plugin:					WindowBuilder 1.9.8								*/
/*		Dependencias:			mysql-connector-java-8.0.21.jar					*/
/*		Estado:					BETA.											*/
/*		Autor:					Mariano Ariel Deville							*/
/*		Fecha creación:			15/12/2023										*/
/*		Última modificación:	26/02/2025										*/
/********************************************************************************/

import control.CtrlPrincipal;
import dao.DiscoFS;
import vista.Botones;

public class GPIyG {

	public static void main(String[] args) {

		DiscoFS.recuperarConfiguracion();		
		Botones ventana = new Botones("Sistema Registral Personal de Ingresos Egresos e Inversiones");
		CtrlPrincipal ctrlPrincipal = new CtrlPrincipal(ventana);
		ctrlPrincipal.iniciar();
	}
}