package co.com.hbt.koba.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.hbt.koba.objeto.ObjetosKoba;

public class ControllerUtil {

    private By objetoToAction = By.xpath("/html/body");
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerUtil.class);
    static PropertiesLoader properties = PropertiesLoader.getInstance();
    private WebDriver driver;
    private DriverManagerFactory page;

    public ControllerUtil( WebDriver driver,DriverManagerFactory page) {
        this.driver=driver;
        this.page=page;
        if (ActionsUtil.objetosIsEmpty()) {
            LOGGER.info("Inicialización de objetos");
            new ObjetosKoba();
        }
    }

    public By getObjetoToAction() {
        return objetoToAction;
    }

    public void setObjetoToAction(By objetoToCliked) {
        objetoToAction = objetoToCliked;
    }

    public void sharedObjet(String opcion) {
        String nombreObjeto = (ActionsUtil.textoMinusculasSinEspacios(opcion));
        By byObjeto = ActionsUtil.getObjeto(nombreObjeto);
        setObjetoToAction(byObjeto);
    }

    public void irA(String url) {
    	url = properties.getProperty(url);
        String urlActualizada = ActionsUtil.updateUrlWithBaseUrlIfDefined(url);
        ActionsUtil.goToWebSide(driver, urlActualizada);
    }

    public void irA(String url, String puerto) {
        url = properties.getProperty(url);
        puerto = properties.getProperty(puerto);
        String urlActualizada = ActionsUtil.updateUrlWithBaseUrlIfDefined(url + ":" + puerto);
        ActionsUtil.goToWebSide(driver, urlActualizada);
    }

    public void irA(String url, String puerto, String path) {
        url = properties.getProperty(url);
        puerto = properties.getProperty(puerto);
        path = properties.getProperty(path);
        String urlActualizada = ActionsUtil.updateUrlWithBaseUrlIfDefined(url + ":" + puerto + path);
        ActionsUtil.goToWebSide(driver, urlActualizada);
    }

    public void esperarSegundos(String string) {
        ActionsUtil.sleepSeconds(Integer.parseInt(string));
     }
    
    public void ingresarTexto(String objeto, String texto) {
        sharedObjet(objeto);
        ActionsUtil.setTextField(driver, getObjetoToAction(), texto);
    }

    public void ingresarTextoProperties(String objeto, String texto) {
        texto = properties.getProperty(texto);
        sharedObjet(objeto);
        ActionsUtil.setTextField(driver, getObjetoToAction(), texto);
    }

    public void clic(String objeto) {
        sharedObjet(objeto);
        ActionsUtil.clic(driver, getObjetoToAction());
    }

    public void clicForzado(String objeto) {
        sharedObjet(objeto);
        ActionsUtil.ejecutarScript(driver, "arguments[0].click();", getObjetoToAction());
    }
    public void clicLinkTexto(String texto) {
    	 ActionsUtil.clickLinkTexto(driver, texto);
     }
    
    public void clicBotonTexto(String texto) {
        texto = properties.getProperty(texto);
        By boton = By.xpath("//button[text()='" + texto + "']");
        ActionsUtil.esperarPoderClicBy(driver, boton);
        ActionsUtil.clic(driver, boton);
    }
    
    public void validarUrl(String urlIp, String puerto, String path) {
        urlIp = properties.getProperty(urlIp);
        puerto = properties.getProperty(puerto);
        path = properties.getProperty(path);
        String urlActualizada = ActionsUtil.updateUrlWithBaseUrlIfDefined(urlIp + ":" + puerto + path);
        String urlActual = driver.getCurrentUrl();
        if (!urlActualizada.equals(urlActual)) {
            ActionsUtil.goToWebSide(driver, urlActualizada);
        }

    }
    public void esperarCargaPantalla() {
        By cargando = By.xpath("//span[@class=\"nc-loading-text\"]");
        ActionsUtil.invisibleListaBy(driver, cargando);
        ActionsUtil.invisibleListaBy(driver, cargando);
    }
    public void validarCargando() {
        ActionsUtil.cargandoFrameInterno(driver);

    }
    public void tomarFoto(String nombreFoto) {
        ActionsUtil.takeSnapShot(driver, nombreFoto);
        
    }
    public void cambiarPestania(String xpath) {
        WebElement elemento=driver.findElement(By.xpath(xpath));
        elemento.click();
        
    }
    public void irAUrl(String url) {
            ActionsUtil.goToWebSide(driver, url);
    }
    
    public void ingresarPanelIzquierdo(String nombrePanel) {
		publicarXpahtClic(nombrePanel);
		
	}

	public void ingresarProcesoGestiones(String nombrePanel) {
		publicarXpahtClic(nombrePanel);
	}

	private void publicarXpahtClic(String nombrePanel) {
		sharedObjet(nombrePanel);
	    ActionsUtil.clic(driver, getObjetoToAction());
	}

    
}

