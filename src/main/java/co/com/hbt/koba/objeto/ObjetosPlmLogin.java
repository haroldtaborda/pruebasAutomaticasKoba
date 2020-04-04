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
public class ObjetosPlmLogin {
    
    /**
     * Constructor
     */
    public ObjetosPlmLogin() {
    	/* pagina de login*/
        ActionsUtil.objetosPut("username", By.id("username"));
        ActionsUtil.objetosPut("password", By.id("password"));
        ActionsUtil.objetosPut("botonIngresar", By.xpath("//button[@class='btn btn-primary text-uppercase mb-3 mb-sm-4']"));

    }
}

