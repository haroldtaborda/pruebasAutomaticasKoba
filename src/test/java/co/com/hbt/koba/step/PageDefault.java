package co.com.hbt.koba.step;

import org.openqa.selenium.WebDriver;

import co.com.hbt.koba.util.ActionsUtil;
import co.com.hbt.koba.util.DriverManagerFactory;
import co.com.hbt.koba.util.PropertiesLoader;
import cucumber.api.java.After;
import cucumber.api.java.Before;

/**
 * 
 * Clase responsable de instanciar el driver 
 *
 * @uthor hataborda <br>
 *        Harold Taborda <br>
 *        hataborda@heinsohn.com.co
 *
 * @date 17/01/2020
 * @version 1.0
 */
public class PageDefault {

    static PropertiesLoader properties = PropertiesLoader.getInstance();
	static WebDriver driver;
    DriverManagerFactory page;
    
    public PageDefault() {
    }
    
    public void ini(){
        page = new DriverManagerFactory();
        driver = DriverManagerFactory.getDriver();
    }

    /**
     * 
     * Método responsable de instanaciar el drvier y abrir el navegador
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 17/01/2020
     * @version 1.0
     */
    @Before
    public void beforeScenario() {
    	page = new DriverManagerFactory();
		driver = DriverManagerFactory.getDriver();
    }

    /**
     * 
     * Método responsable tomar la captura y finalizar el navegador
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 17/01/2020
     * @version 1.0
     */
    //@After
    public void afterScenario() {
        cerrarNavegador();
    }

    /**
     * Metodo encargado de finalizar el navegador
     */
    public void cerrarNavegador() {
        page.cerrarNavegador();
    }

    /**
     * Método que obtiene el valor de driver
     * 
     * @return the driver
     */
    public WebDriver getDriver() {
        return driver;
    }

    /**
     * Método que obtiene el valor de page
     * 
     * @return the page
     */
    public DriverManagerFactory getPage() {
        return page;
    }

    /**
     * Método que establece el valor de page
     * 
     * @param page el page a establecer
     */
    public void setPage(DriverManagerFactory page) {
        this.page = page;
    }

}
