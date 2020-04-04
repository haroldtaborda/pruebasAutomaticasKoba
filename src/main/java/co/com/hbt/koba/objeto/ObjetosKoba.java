package co.com.hbt.koba.objeto;

import org.openqa.selenium.By;

import co.com.hbt.koba.util.ActionsUtil;

/**
 * 
 * Clase responsable almacenar los xphat a usar
 *
 * @uthor hataborda <br>
 *        Harold Taborda <br>
 *        hataborda@heinsohn.com.co
 *
 * @date 16/01/2020
 * @version 1.0
 */
public class ObjetosKoba {
    
    /**
     * Constructor nos nombres deben ser todos en minuscula
     */
    public ObjetosKoba() {
    	/* pagina de login*/
        ActionsUtil.objetosPut("username", By.id("username"));
        ActionsUtil.objetosPut("password", By.id("password"));
        ActionsUtil.objetosPut("ingresar", By.xpath("//button[@class='btn btn-primary text-uppercase mb-3 mb-sm-4']"));
        /* panel izquierdo*/
        ActionsUtil.objetosPut("panelizquierdogestion", By.xpath("//label[contains(text(),'Gestiones')]"));
        /*procesos de gestiones perfil*/
        ActionsUtil.objetosPut("gestionperfiles", By.xpath("//b[contains(text(),'PERFILES')]"));
        /*procesos de gestiones perfil*/
        ActionsUtil.objetosPut("gestionusuario", By.xpath("//b[contains(text(),'USUARIOS')]"));
        
        //tabla primer registros tres puntos
        ActionsUtil.objetosPut("tresbotones", By.xpath("//tbody//tr[1]/td[1]//div[@class='row']"));
        //ver detalles dentro del los tres puntos
        ActionsUtil.objetosPut("verdetalles", By.xpath("//div[@class='dropdown-menu dropdown-primary fadeInDropdown']//span[@class='icon-pencil-3']"));
        // boton volver 
        ActionsUtil.objetosPut("botonvoler", By.xpath("//button[contains(text(),'Volver')]"));
        
    }
}

