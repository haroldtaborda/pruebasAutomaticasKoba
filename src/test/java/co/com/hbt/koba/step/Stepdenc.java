package co.com.hbt.koba.step;

import java.util.Map;

import co.com.hbt.koba.util.ControllerUtil;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;

/**
 * 
 * Clase responsable de la comunicacion con el feature
 *
 * @uthor hataborda <br>
 *        Harold Taborda <br>
 *        hataborda@heinsohn.com.co
 *
 * @date 17/01/2020
 * @version 1.0
 */
public class Stepdenc {
    ControllerUtil controllerUtil;
    PageDefault page;
    Map<String, String> mapaParametros=null;
    /**
     * 
     * Método responsable de inicializar las utilidades usadas
     *
     * @uthor hataborda <br>
     *        Harold Taborda<br>
     *        hataborda@heinsohn.com.co
     *
     * @date 17/01/2020
     * @version 1.0
     */
    @Given("Init")
    public void init() {
        // instancio el default
    	page=new PageDefault();
        controllerUtil= new ControllerUtil(page.getDriver(),page.getPage());
        mapaParametros=null;
        
    }
    
    @Given("Ingreso a {string}")
    public void ingresoA(String url){
        controllerUtil.irA(url);
    }
    /**
     * 
     * Método responsable inicializar las variables para el flujo final
     *
     * @uthor hataborda <br>
     *        Harold Taborda<br>
     *        hataborda@heinsohn.com.co
     *
     * @date 27/01/2020
     * @version 1.0
     */
    @Given("Init interno")
    public void initInterno() {
        // instancio el default
        page=new PageDefault();
        page.ini();
        controllerUtil= new ControllerUtil(page.getDriver(),page.getPage());
        mapaParametros=null;
        
    }
    

    /**
     * 
     * Método responsable hacer el login con NC
     *
     * @uthor hataborda <br>
     *        Harold Taborda<br>
     *        hataborda@heinsohn.com.co
     *
     * @date 17/01/2020
     * @version 1.0
     * @param urlIp
     * @param puerto
     * @param path
     * @param usuario
     * @param campoUsuario
     * @param contrasenia
     * @param campoContrasenia
     * @param clic
     */
    @Given("Login en {string} con user {string} en {string} pass {string} en {string} clic en {string}")
    public void login(String url, String usuario, String campoUsuario, String contrasenia,
            String campoContrasenia, String clic) {
        // voy a la url login
        controllerUtil.irA(url);
        //esperar
        controllerUtil.esperarSegundos("2");
        // ingreso el usuario
        controllerUtil.ingresarTextoProperties(campoUsuario, usuario);
        // ingreso la contrasenia
        controllerUtil.ingresarTextoProperties(campoContrasenia, contrasenia);
        // clic en boton
        controllerUtil.clic(clic);
    }

    /**
     * 
     * Método responsable de dar clic en un boton por un texto
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 17/01/2020
     * @version 1.0
     * @param textoBoton
     */
    @When("Clic en boton por texto {string}")
    public void clicBotonTexto(String textoBoton) {
        controllerUtil.clicBotonTexto(textoBoton);
    }
    
    

    
    /**
     * 
     * Método responsable de ir a una url determinada
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 17/01/2020
     * @version 1.0
     * @param urlIp
     * @param puerto
     * @param path
     */
    @When("estoy en la url {string} {string} {string}")
    public void validarUrl(String urlIp, String puerto, String path) {
        controllerUtil.validarUrl(urlIp, puerto, path);
    }

    @When("estoy en la url {string}")
    public void irAUrl(String url) {
        controllerUtil.irA(url);
    }

    /**
     * 
     * Método responsable de dar clic en un link por un texto
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 17/01/2020
     * @version 1.0
     * @param textoBoton
     */
    @When("Clic en link por texto {string}")
    public void clicLinkTexto(String textoBoton) {
        controllerUtil.clicLinkTexto(textoBoton);
    }

   
    /**
     * 
     * Método responsable de esperar que cargue la pantalla
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 17/01/2020
     * @version 1.0
     */
    @When("esperar carga pantalla")
    public void esperarCargaPantalla() {
        controllerUtil.esperarCargaPantalla();

    }
    @When("esperar tiempo {string}")
    public void esperar_tiempo(String string) {
        controllerUtil.esperarSegundos(string);
    }

    @Given("Tomo foto {string}")
    public void tomarFoto(String nombreFoto) {
       controllerUtil.tomarFoto(nombreFoto);
        
    }
    @Given("Cambio de pestania")
    public void cambiarPestania() {
        controllerUtil.cambiarPestania();
        
    }
    @Given("Validar cargando")
    public void cargando() {
        controllerUtil.validarCargando();
        
    }
    
    @When("Ingreso panel izquierdo {string}")
    public void ingresarPanelIzquierdo(String nombrePanel) {
        controllerUtil.ingresarPanelIzquierdo(nombrePanel);
    }
    
    @When("Ingresar proceso de gestiones {string}")
    public void ingresarProcesoGestiones(String nombrePanel) {
        controllerUtil.ingresarProcesoGestiones(nombrePanel);
    }
    
}