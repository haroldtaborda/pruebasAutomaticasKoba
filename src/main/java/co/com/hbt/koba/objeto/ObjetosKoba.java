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
    }
}

