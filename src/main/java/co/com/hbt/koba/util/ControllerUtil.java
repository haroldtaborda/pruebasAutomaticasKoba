package co.com.hbt.koba.util;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
//import com.ibm.icu.text.SimpleDateFormat;

import co.com.hbt.koba.constans.ConsultasDinamicasConstans;
import co.com.hbt.koba.constans.FormatConstans;
import co.com.hbt.koba.dto.AsignarParametrosSalidaInDTO;
import co.com.hbt.koba.enums.ProcesadoEnum;
import co.com.hbt.koba.enums.TablaEnum;
import co.com.hbt.koba.objeto.ObjetosPlmLogin;

public class ControllerUtil {

    private By objetoToAction = By.xpath("/html/body");
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerUtil.class);
    static PropertiesLoader properties = PropertiesLoader.getInstance();
    private WebDriver driver;
    private DriverManagerFactory page;
    private Boolean esPrepago;
    private String numeroOrdenVenta; // Extracción orden de venta al crearse
    private String SubelementOrdenVenta; // Extracción orden de venta consulta final
    private String url; // Extracción orden de venta consulta final
    private String planActual;// para cambio de plan
    private String tipoCambioPlan; // para cambio de plan
    private static final String CASO_POS_PRE = "POS_PRE";// para cambio de plan
    private static final String CASO_PRE_POS = "PRE_POS";// para cambio de plan
    /** Número de teléfono asociado al proceso.*/
    private String bdNumeroTelefono; 
    /** Número de cuenta facturación asociado al proceso.*/
    /**Categoria asociado al proceso.*/
    private String bdCategoria;
    private String tipo_cuenta;
    private String bdNombre;
    private String ciclo;
    private String cuentaFacturacion;
    /** Nombre cliente asociado al proceso.*/
    private String nombreCliente;
    private String parametrosJson; //parametros con lso que fue ejecutado el flujo
    private static String fechaSegundosMilisegundos;
    private static List<String> listaIdsProcesados;
    private String estadoOrden;
    private String tipoProceso;
    private String procesadoCuando;
    private String bdClientePrepago;
    private String bdClientePospago;
    private boolean indicadorInAdvance=false;
    private String bdClientePospagoInAdvance;
    private String bdNumeroCuentaCliente;
    private String bdPlan;
    private String urlVisionGeneral;
    private Date fechaCreacion;
    /**
     * interno o externo
     */
    private String bdTipoEquipo;
    static OperacionesBD operacionesBD = new OperacionesBD();

    private String numeroAntiguo; // Extracción número de telefono
    private String numeroNuevo; // Extracción número de telefono nuevo

    private String simCardInicial;

    private String nombreDelEquipoInterno;
    private String imeiDelEquipoIterno;
    private String nombreDelEquipoRenovacion;
    private String imeiDelEquipoRenovacion;

    private String simcardVieja;
    private String simcardNueva;
    private String sloActivados = "";
    private String sloDesactivados = "";
    private String nombrePlan = "";

    private String planNuevo;
    private String bdNumeroDocumento;
    private String disputaGenerada = "";
    private String bodegaDefecto;
    private String fechaEnvio;
    private String canalDistribucion;
    private String confirmadoPor;
    private String urlError;
    private String facturaDisputa;
    private String urlNotaCredio;
    private String urlDisputa;
    private String urlDocumentoNotaCredito;
    private String facturaNotaCredito;
    private Map<String,WebElement> tablaMapa;
    private String urlUsuario = "";
    

    /**
     * Indica si ya ingreso algún criterio de búsqueda..
     */
    boolean ingresoCriterioBusqueda=false;
    private Boolean esPrevioPago=false;
    

    public ControllerUtil( WebDriver driver,DriverManagerFactory page) {
        this.driver=driver;
        this.page=page;
        this.fechaCreacion=Calendar.getInstance().getTime();
        if (ActionsUtil.objetosIsEmpty()) {
            LOGGER.info("Inicialización de objetos");
            new ObjetosPlmLogin();
        }
        if(fechaSegundosMilisegundos==null){
        Calendar fechaActual= Calendar.getInstance();
        SimpleDateFormat format= new SimpleDateFormat(FormatConstans.FORMATO_FECHA_HORA_PEGADO);
        fechaSegundosMilisegundos=format.format(fechaActual.getTime());
        fechaSegundosMilisegundos=fechaSegundosMilisegundos.replace(":","");
        //fechaSegundosMilisegundos="1111111";
        ReportesUtil.setFechaSegundosMiligesundos(fechaSegundosMilisegundos);
        ActionsUtil.setFechaSegundosMiligesundos(fechaSegundosMilisegundos);
        ActionsUtil.setFechaSegundosMiligesundos(fechaSegundosMilisegundos);
        ActionsUtil.setEjecucion(fechaSegundosMilisegundos);
        }
        if(listaIdsProcesados==null){
            listaIdsProcesados= new ArrayList<>();
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

    public void cambiarIframe(String posicion) {

        int indexTab;
        try {
            indexTab = Integer.parseInt(posicion);
        } catch (Exception e) {
            ActionsUtil.guardarExceptionControlada(e);
            indexTab = 0;
        }
        if (indexTab == 0) {
            switch (ActionsUtil.textoMinusculasSinEspacios(posicion)) {
            case "principal":
                cambiarIframePrincipal();
                return;
            case "cero":
                indexTab = 0;
                break;
            case "uno":
                indexTab = 1;
                break;
            case "dos":
                indexTab = 2;
                break;
            default:
                indexTab = 0;
                break;
            }
        }
        if (indexTab != 0) {
            ActionsUtil.switchFrame(driver, indexTab);
        } else {
            sharedObjet(posicion);
            By element = getObjetoToAction();
            ActionsUtil.switchFrame(driver, element);
        }
    }

    public void cambiarIframePrincipal() {
        driver.switchTo().parentFrame();
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

    public Object getValuesForGivenKey(String jsonArrayStr, String key) {
        JSONArray jsonArray = new JSONArray(jsonArrayStr);
        Object x = jsonArray.query("/0/b");
        return x;
    }

    public void esperarDisplayed(String objeto) {
        sharedObjet(objeto);
        ActionsUtil.existsElement(driver, getObjetoToAction());
    }

    public void ingresoValorCombo(String objeto, String texto) {
        sharedObjet(objeto);
        ActionsUtil.selectText(driver, getObjetoToAction(), texto);

    }

    public void clicBotonTexto(String texto) {
        texto = properties.getProperty(texto);
        // TODO mover a la clase ObjetosPlmLogin.java
        By boton = By.xpath("//button[text()='" + texto + "']");
        ActionsUtil.esperarPoderClicBy(driver, boton);
        ActionsUtil.clic(driver, boton);
    }

    public void clicBotonNuevaCuentaFacturacionTexto(String textoBoton) throws Exception {
        
        ActionsUtil.espererarDesparecerBy(driver, By.xpath(
                "//div[@class='ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix ui-draggable-handle']"));
        ActionsUtil.espererarDesparecerCargando(driver);
        ActionsUtil.espererarDesparecerCargando(driver);
        ActionsUtil.espererarDesparecerCargando(driver);
        if ("boton.nueva.cuenta.facturacion.pospagoAdvance".equals(textoBoton)) {
          //ya se debe hacer creado la cuenta pos y pre
            By cuentaPosadvance = By.xpath("//tr[@__gwt_row='1']//td[2]//div[@class='nc-field-text-readonly']");
            ActionsUtil.esperarVisibleBy(driver, cuentaPosadvance);  
        } else if("boton.nueva.cuenta.facturacion.prepago".equals(textoBoton)){
            //se debe haber creado la cuenta pos
            By cuentaPos = By.xpath("//tr[@__gwt_row='0']//td[2]//div[@class='nc-field-text-readonly']");
            ActionsUtil.esperarVisibleBy(driver, cuentaPos);
        }
        
        clicBotonTexto(textoBoton);
        ActionsUtil.espererarDesparecerCargando(driver);
        ActionsUtil.espererarDesparecerCargando(driver);
        ActionsUtil.espererarDesparecerCargando(driver);
        if ("boton.nueva.cuenta.facturacion.pospago".equals(textoBoton)) {
            bdClientePospago = obtenerNumeroCuenta();
        } else if ("boton.nueva.cuenta.facturacion.prepago".equals(textoBoton)) {
            bdClientePrepago = obtenerNumeroCuenta();
        } else {
            asignarSubtipoCuenta("In Advance (Por Adelantado)");
            bdClientePospagoInAdvance = obtenerNumeroCuenta();
        }
        
        
    }

    public void clicLinkTexto(String texto) {
       ActionsUtil.clickLinkTexto(driver, texto);
    }

    public void ingresarInformacionModal(String numeroDocumento, String nombre, String apellido, String correo) {

        // TODO mover a la clase ObjetosPlmLogin.java

        List<WebElement> filaTabla = driver.findElements(By.xpath(
                "//td[@class=\"nc-composite-layout-item-horizontal\"]//tr[@class=\"gwt-row nc-row-rolled-down\"]"));
        List<WebElement> filaTablaTextArea = driver.findElements(By.xpath(
                "//td[@class=\"nc-composite-layout-item-horizontal\"]//tr[@class=\"gwt-row nc-row-rolled-down gwt-bottom-line\"]"));
        WebElement label = null;
        bdNumeroDocumento=numeroDocumento;
        int contador = 0;
        String MENSAJE = "No se encontró ";

        String MENSAJE_INPUT_CATEGORIA_CLIENTE = "Categoría de cliente";
        boolean indicadorCategoriaCliente = false;

        String MENSAJE_INPUT_TIPO_DOCUMENTO = "Tipo de documento";
        boolean indicadorTipoDocumento = false;

        String MENSAJE_INPUT_NUMERO_DOCUMENTO = "Número de Documento";
        boolean indicadorNumeroDocumento = false;

        String MENSAJE_INPUT_NOMBRE = "Nombre";
        boolean indicadorNombre = false;

        String MENSAJE_INPUT_APELLIDO = "Apellido";
        boolean indicadorApellido = false;

        String MENSAJE_INPUT_METODO_CONTACTO = "Métodos de contacto";
        boolean indicadorMetodoContacto = false;

        String MENSAJE_INPUT_DIRECCION_CLIENTE = "Dirección de cliente";
        boolean indicadorDireccionCliente = false;

        for (WebElement item_web_element : filaTabla) {
            label = item_web_element
                    .findElement(By.xpath(".//td[@class=\"nc-parctrl-cell-label nc-parctrl-cell-label-0\"]/div"));
            if (label.isDisplayed() && MENSAJE_INPUT_CATEGORIA_CLIENTE.equals(label.getText())) {
                contador++;
                WebElement input = item_web_element.findElement(
                        By.xpath(".//td[@class=\"nc-parctrl-cell nc-parctrl-last-cell\"]//input[@type=\"text\"]"));
                if (input.isEnabled()) {
                    input.clear();
                    input.sendKeys("Individual");
                    buscarResultado();
                    indicadorCategoriaCliente = true;
                }
            }
            if (label.isDisplayed() && MENSAJE_INPUT_TIPO_DOCUMENTO.equals(label.getText())) {
                contador++;
                WebElement elementoSelect = item_web_element
                        .findElement(By.xpath(".//td[@class=\"nc-parctrl-cell nc-parctrl-last-cell\"]//select"));
                if (elementoSelect.isEnabled()) {
                    Select selectTipoDocumento = new Select(elementoSelect);
                    selectTipoDocumento.selectByVisibleText("Pasaporte");
                }
            }
            if (label.isDisplayed() && MENSAJE_INPUT_NUMERO_DOCUMENTO.equals(label.getText())) {
                contador++;
                WebElement input = item_web_element.findElement(
                        By.xpath(".//td[@class=\"nc-parctrl-cell nc-parctrl-last-cell\"]//input[@type=\"text\"]"));
                if (input.isEnabled()) {
                    input.clear();
                    input.sendKeys(numeroDocumento);// NUMERO_DOCUMENTO
                    indicadorNumeroDocumento = true;
                }
            }
            if (label.isDisplayed() && MENSAJE_INPUT_NOMBRE.equals(label.getText())) {
                contador++;
                WebElement input = item_web_element.findElement(
                        By.xpath(".//td[@class=\"nc-parctrl-cell nc-parctrl-last-cell\"]//input[@type=\"text\"]"));
                if (input.isEnabled()) {
                    input.clear();
                    input.sendKeys(nombre);// NOMBRE
                    indicadorNombre = true;
                }
            }
            if (label.isDisplayed() && MENSAJE_INPUT_APELLIDO.equals(label.getText())) {
                contador++;
                WebElement input = item_web_element.findElement(
                        By.xpath(".//td[@class=\"nc-parctrl-cell nc-parctrl-last-cell\"]//input[@type=\"text\"]"));
                if (input.isEnabled()) {
                    input.clear();
                    input.sendKeys(apellido);// APELLIDO
                    indicadorApellido = true;
                }
            }
            if (label.isDisplayed() && MENSAJE_INPUT_DIRECCION_CLIENTE.equals(label.getText())) {
                contador++;
                WebElement input = item_web_element.findElement(
                        By.xpath(".//td[@class=\"nc-parctrl-cell nc-parctrl-last-cell\"]//input[@type=\"text\"]"));
                if (input.isEnabled()) {
                    input.clear();
                    input.sendKeys("rep");
                    buscarResultado();
                    indicadorDireccionCliente = true;
                }
            }
            if (contador == 6) {
                break;
            }
        }
        for (WebElement selenideElement : filaTablaTextArea) {
            label = selenideElement
                    .findElement(By.xpath(".//td[@class=\"nc-parctrl-cell-label nc-parctrl-cell-label-0\"]/div"));
            if (label.isEnabled() && MENSAJE_INPUT_METODO_CONTACTO.equals(label.getText())) {
                contador++;
                WebElement input = selenideElement.findElement(By.xpath(
                        ".//td[@class=\"nc-parctrl-cell nc-parctrl-last-cell\"]"
                        + "//input[@class=\"new_multiple_base_text_box\"]"));
                if (input.isEnabled()) {
                    input.clear();
                    input.sendKeys(correo);// CORREO
                    By boton = By.xpath("//img[@class='gwt-Image nc-field-add-button']");
                    ActionsUtil.esperarPoderClicBy(driver, boton);
                    ActionsUtil.ejecutarScript(driver, "arguments[0].click();", boton);

//                  ActionsUtil.espererarDesparecerCargando(driver);
//                  
//                  ActionsUtil.espererarDesparecerCargando(driver);
//                  ActionsUtil.clic(driver, boton);
                    indicadorMetodoContacto = true;

                }
            }
        }

    }

    private void buscarResultado() {
        By primerResultado = By.xpath("//div[@class='refsel_table']//div[@class='refsel_name']");
        WebElement resultadoBusqueda = driver.findElement(primerResultado);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(resultadoBusqueda));
        resultadoBusqueda.click();
    }

    /**
     * 
     * Método responsable de asignar 
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 6/02/2020
     * @version 1.0
     * @param textoBoton
     * @throws Exception
     */
    public void asignarSubtipoCuenta(String textoBoton) throws Exception {
        asignarValorCuentaFacturacionPospago("Subtipo de cuenta",textoBoton);
        indicadorInAdvance=true;

    }

    public void extraerNumeroCuenta() {
        By cuentaPos = By.xpath("//tr[@__gwt_row='1']//td[2]//div[@class='nc-field-text-readonly']");
        By cuentaPosadvance = By.xpath("//tr[@__gwt_row='2']//td[2]//div[@class='nc-field-text-readonly']");
        By cuentaPre = By.xpath("//tr[@__gwt_row='0']//td[2]//div[@class='nc-field-text-readonly']");
        ActionsUtil.esperarVisibleBy(driver, cuentaPre);
        ActionsUtil.esperarVisibleBy(driver, cuentaPos);
        ActionsUtil.esperarVisibleBy(driver, cuentaPosadvance);

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
    
    public void irAUrl(String url) {
            ActionsUtil.goToWebSide(driver, url);
    }
    // Alta Lineas Pospago/Prepago

    public void busqueda_numero_de_cuenta_de_facturacion_e_ingresar(String numeroCuentaFacturacion) {
        // TODO mover a la clase ObjetosPlmLogin.java
        By inputCuentaFacturacion = By.xpath(
                "//table[@class=\"column nc-parctrl-sub-columns-1 column-filled\"]/tbody/tr[6]/td[2]/div/div[2]/div/table/tbody/tr/td/input");
        ActionsUtil.esperarVisibleBy(driver, inputCuentaFacturacion);
        ActionsUtil.clic(driver, inputCuentaFacturacion);
        ((WebElement) inputCuentaFacturacion).sendKeys(numeroCuentaFacturacion);

        By botonBusqueda = By.xpath(
                "//button[@class=\"gwt-Button button_action_id_9138004618113289307_9137007409413676336 TableCtrl-button nc-search-button parctrl-submit-form-button\"]");
        ActionsUtil.esperarVisibleBy(driver, botonBusqueda);
        ActionsUtil.clic(driver, botonBusqueda);

    }

    public void ingresarCampoClienteResidencial(String valor, String nombre) {
        if(!ingresoCriterioBusqueda) {
            if("campo.numero.cuenta.facturacion".equals(nombre)) {
                cuentaFacturacion=valor;
            }
            if("campo.numero.telefono".equals(nombre)) {
                bdNumeroTelefono=valor;
                numeroAntiguo=bdNumeroTelefono;
            }
            if("campo.id.cuenta.cliente".equals(nombre)) {
                //bdIdCuentaCliente=valor;
            }
            nombre = properties.getProperty(nombre);
            By campo = By.xpath(
                    "//tr[@class='gwt-row nc-row-rolled-down']//div[contains(text(),'" + nombre + "')]/../..//input");
            ActionsUtil.setTextField(driver, campo, valor);
            ingresoCriterioBusqueda=true;
        }
    }

    private List<WebElement> obtenerResultadoBusquedaClienteResidencial() {
        ActionsUtil.esperarInvisibleNoResultado(driver);
        // TODO mover a la clase ObjetosPlmLogin.java
        By tablaResultado = By.xpath("//div[@class='nc-field-text-readonly nc-field-reference-readonly']");
        ActionsUtil.esperarVisibleBy(driver, tablaResultado);
        return driver.findElements(tablaResultado);

    }

    /**
     * 
     * Método responsable de desplazar el cursor a la parte final de la pagina, para
     * evitar que se pierdan resultados de la resultados de la búsqueda
     *
     * @uthor hataborda <br>
     *        Diana Carolina Farfán <br>
     *        dfarfan@indracompany.com
     *
     * @date 07/01/2020
     * @version 1.0
     */
    public void desplazarScrollPaginaBusqueda() {
        WebElement itemPor = driver.findElement(By.xpath("//div[@class='ui-pager-gwt-container-page-number']/a"));
        // if (itemPor.isDisplayed()) {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].scrollIntoView(true);", itemPor);
        // }
    }

    public void clicNombreCuentacliente() {
        desplazarScrollPaginaBusqueda();
        List<WebElement> lista = obtenerResultadoBusquedaClienteResidencial();
        WebElement linkNombre = lista.get(0).findElement(By.xpath(".//a"));
        linkNombre.click();
    }

    public void ingresarCanalDistribucion(String valor) throws InterruptedException {
        // TODO mover a la clase ObjetosPlmLogin.java
        By canalDistribucion = By.xpath(
                "//td[@class=\"nc-composite-layout-item-horizontal\"]//tr[@class=\"gwt-row nc-row-rolled-down\"][1]//div[@class=\"refsel_single\"]//input[@type=\"text\"]");
        By ubicacion = By.xpath("//input[@id=\"id_refsel591865409_input\"]");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        ActionsUtil.espererarDesparecerCargando(driver);
        ActionsUtil.sleepSeconds(1);
        wait.until(ExpectedConditions.elementToBeClickable(canalDistribucion));
        ActionsUtil.invisibleListaBy(driver, ubicacion);

        ActionsUtil.setTextField(driver, canalDistribucion, valor);
        buscarPrimerResultado(true);
        urlVisionGeneral=driver.getCurrentUrl();
    }

    private void buscarPrimerResultado(boolean hacerClic) {
        // TODO mover a la clase ObjetosPlmLogin.java
        ActionsUtil.sleepSeconds(1);
        By primerResultado = By.xpath("//*[@id=\"nc_refsel_list_row_0\"]/*[@class=\"refsel_name\"]");
        WebElement resultadoBusqueda = driver.findElement(primerResultado);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        ActionsUtil.espererarDesparecerCargando(driver);
        wait.until(ExpectedConditions.elementToBeClickable(resultadoBusqueda));
        if (hacerClic) {
            resultadoBusqueda.click();
        }
    }
    
    
        // Fin Alta Lineas Pospago/Prepago

    public void selecionarComboOrdenVenta(String valor, String telefono) {
        valor = properties.getProperty(valor);
        String ID = properties.getProperty("combo.orden.venta.id");

        By radio = By.xpath("//span[text()='" + valor + "']/..//input[@type=\"radio\"]");
        ActionsUtil.esperarPoderClicBy(driver, radio);
        ActionsUtil.clic(driver, radio);

        if (ID.equals(valor)) {
            // TODO completar para cuando es por id

            if (ID.equals(valor)) {
                By productosFiltrados = By.xpath("//img[@id=\"button_9150048146013085219\"]");
                ActionsUtil.esperarPoderClicBy(driver, productosFiltrados);
                ActionsUtil.clic(driver, productosFiltrados);

                // Ingresar el número de telefono en Filtros
                By filtros = By.xpath("//textarea[@class=\"nc-memo-field\"]");
                ActionsUtil.esperarPoderClicBy(driver, filtros);
                ActionsUtil.setTextField(driver, filtros, telefono);

                // Realizar clic en el botón Buscar Productos
                By buscarProductos = By.xpath(
                        "//button[@class=\"gwt-Button button_action_id_9150323480013185188_9150323595013185242_compositepopup_2 TableCtrl-button ParCtrl-editButton\"]");
                ActionsUtil.esperarPoderClicBy(driver, buscarProductos);
                ActionsUtil.clic(driver, buscarProductos);

                // Realizar clic en el botón Cerrar
                esperarCargaPantalla();
                By botonCerrar = By.xpath(
                        "//button[@class=\"gwt-Button button_action_id_9150323480013185188_9150323595013185248_compositepopup_2 TableCtrl-button cancel-button\"]");
                ActionsUtil.esperarPoderClicBy(driver, botonCerrar);
                ActionsUtil.clic(driver, botonCerrar);

            }
        }
    }

    public void esperarCargaPantalla() {
        By cargando = By.xpath("//span[@class=\"nc-loading-text\"]");
        ActionsUtil.invisibleListaBy(driver, cargando);
        ActionsUtil.invisibleListaBy(driver, cargando);
    }

    public void clicNumeroTelefonoestado(String numeroTelefono, String estado) throws Exception {
    List<WebElement> filas = driver
                .findElements(By.xpath("//*[@class=\"nc-field-text-readonly nc-field-reference-readonly\"]"));
    WebElement referencia = null;    
    estado = properties.getProperty(estado);
    for (WebElement selenideElement : filas) {
      if (selenideElement.getText().contains(numeroTelefono) && selenideElement.getText().contains(estado)) {
        referencia = selenideElement.findElement(By.xpath(".//a[contains(text(),'" + numeroTelefono + "')]"));
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].click();", referencia);
        return;
            }
        }
    throw new Exception("El número " + numeroTelefono + " No esta " + estado);
    }

    public WebDriver getDriver() {
    return driver;
    }

    
    
    /**
     * 
     * Método responsable de seleccionar un SLO
     *
     * @uthor hataborda <br>
     *        Marybel Infante Ayala <br>
     *        minfante@indracompany.com
     *
     * @date 17/01/2020
     * @version 1.0
     * @param tipo
     * @param valor
     * @throws Exception
     */
    public void seleccionarSLO(String valorSLO, String valorDesactivarSLO) throws Exception {
        ActionsUtil.cargandoFrameInterno(driver);
        esperarCargaPantalla();
        String delimiter = ",";
        String[] tipovalor;
        String[] tipoValorDesactivar;
        tipovalor = valorSLO.split(delimiter,0);
        tipoValorDesactivar = valorDesactivarSLO.split(delimiter,0);
        int flag = 0;
          for(int i =0; i < tipovalor.length ; i++) {
              String delimiter2 = "-";
              String[] valor;
              if (flag == 0)
              {
                for (int j=0;j < tipoValorDesactivar.length;j++)
                {
            	  String[] valorDesactivar; 
            	  valorDesactivar= tipoValorDesactivar[j].split(delimiter2);
            	  List<WebElement> elementDriverDesactivar = driver
                          .findElements(By.xpath("//*[@id=\"true\"]/ul//*[starts-with(text(), '" + valorDesactivar[0] + "')]"));
            	  
            	  if (!elementDriverDesactivar.isEmpty()) {
                      JavascriptExecutor executor = (JavascriptExecutor) getDriver();
                      executor.executeScript("arguments[0].scrollIntoView(true);",  elementDriverDesactivar.get(0));
                      
                    WebElement pestaniaDesactivar = elementDriverDesactivar.get(0);
                    if (pestaniaDesactivar.isDisplayed()) {
                    	pestaniaDesactivar.click();
                      esperarCargaPantalla();
                      if (pestaniaDesactivar.isDisplayed()) {
                          WebElement tipoSloDesactivar = pestaniaDesactivar.findElement(By.xpath(
                                        "//div[@class=\"additional-offerings__table\"]//span[starts-with(text(),'" + valorDesactivar[1] + "')]"));                      
                          if (tipoSloDesactivar.isDisplayed()) {
                            WebElement contenedorDesactivar = tipoSloDesactivar.findElement(By.xpath("../../.."));
                            if (contenedorDesactivar.isDisplayed()) {
                              WebElement inputDesactivar = contenedorDesactivar.findElement(By.xpath("input"));
                              if (inputDesactivar.isEnabled()) {
                                //JavascriptExecutor 
                                executor = (JavascriptExecutor) getDriver();
                                executor.executeScript("arguments[0].click();", inputDesactivar);
                                sloDesactivados = sloDesactivados + " , "+valorDesactivar[1];
                                esperarCargaPantalla();
                                        }
                                    } else {
                              String mensaje="No se encontro contendor de tipo slo";
                              ActionsUtil.guardarExceptionControlada(mensaje);
                              throw new Exception(mensaje);
                                    }
                                } else {
                            String mensaje="No se encontro slo " + valorDesactivar[1];
                            ActionsUtil.guardarExceptionControlada(mensaje);
                            throw new Exception(mensaje);
                                }
                            } else {
                          String mensaje="No se encontro slo " + valorDesactivar[1];
                          ActionsUtil.guardarExceptionControlada(mensaje);
                          
                          throw new Exception(mensaje);
                            }
                        } else 
                        {
                      throw new Exception("No se encontro el tipo slo " + valorDesactivar[0]);
                        }
                        } else {
                      throw new Exception("No se encontro el tipo slo " + valorDesactivar[0]);
                        }
            	  esperarCargaPantalla();
              }
                flag = 1;
              }
              
              esperarCargaPantalla();
              valor = tipovalor[i].split(delimiter2);
                         
              List<WebElement> elementDriver = driver
                      .findElements(By.xpath("//*[@id=\"true\"]/ul//*[starts-with(text(), '" + valor[0] + "')]"));
              
              
              
              
              if (!elementDriver.isEmpty()) {
                  JavascriptExecutor executor = (JavascriptExecutor) getDriver();
                  executor.executeScript("arguments[0].scrollIntoView(true);",  elementDriver.get(0));
                  
                WebElement pestania = elementDriver.get(0);
                if (pestania.isDisplayed()) {
                  pestania.click();
                  esperarCargaPantalla();
                  if (pestania.isDisplayed()) {
                      WebElement tipoSlo = pestania.findElement(By.xpath(
                                    "//div[@class=\"additional-offerings__table\"]//span[starts-with(text(),'" + valor[1] + "')]"));                      
                      if (tipoSlo.isDisplayed()) {
                        WebElement contenedor = tipoSlo.findElement(By.xpath("../../.."));
                        if (contenedor.isDisplayed()) {
                          WebElement input = contenedor.findElement(By.xpath("input"));
                          if (input.isEnabled()) {
                            //JavascriptExecutor 
                            executor = (JavascriptExecutor) getDriver();
                            executor.executeScript("arguments[0].click();", input);
                            sloActivados = sloActivados + " , "+valor[1];
                            esperarCargaPantalla();
                                    }
                                } else {
                          String mensaje="No se encontro contendor de tipo slo";
                          ActionsUtil.guardarExceptionControlada(mensaje);
                          throw new Exception(mensaje);
                                }
                            } else {
                        String mensaje="No se encontro slo " + valor[1];
                        ActionsUtil.guardarExceptionControlada(mensaje);
                        throw new Exception(mensaje);
                            }
                        } else {
                      String mensaje="No se encontro slo " + valor[1];
                      ActionsUtil.guardarExceptionControlada(mensaje);
                      
                      throw new Exception(mensaje);
                        }
                    } else 
                    {
                  throw new Exception("No se encontro el tipo slo " + valor[0]);
                    }
                    } else {
                  throw new Exception("No se encontro el tipo slo " + valor[0]);
                    }
              
              
              esperarCargaPantalla();
              ActionsUtil.sleepSeconds(NumeroConstans.TRES);
        }
          
          
          esperarCargaPantalla();
          ActionsUtil.sleepSeconds(NumeroConstans.TRES);
          migaPanRevision();
          esperarCargaPantalla();
          ActionsUtil.sleepSeconds(NumeroConstans.UNO);
          By botonEnviar = By.xpath("//button[@class=\"mobile-contracts__submit-button\"]");
          ActionsUtil.esperarPoderClicBy(driver, botonEnviar);
          ActionsUtil.clic(driver, botonEnviar);
    }
    
    
    
    /**
     * 
     * Método responsable
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 17/01/2020
     * @version 1.0
     * @param tipo
     * @param valor
     * @throws Exception
     */
    /*public void seleccionarSLO(String tipo, String valor) throws Exception {
        
        ActionsUtil.cargandoFrameInterno(driver);
        esperarCargaPantalla();
        
        String delimiter = "-";
        String[] tipoFinal;
        tipoFinal = tipo.split(delimiter,0);

        String[] valorFinal;
        valorFinal = valor.split(delimiter,0);
        

        for(int i =0; i < tipoFinal.length ; i++) {
       
            List<WebElement> elementDriver = driver
                    .findElements(By.xpath("//div[@id='true']/ul//*[starts-with(text(), '" + tipoFinal[i] + "')][not(contains(text(),'Telemetria'))]"));
                    //.findElements(By.xpath("//*[@id=\"true\"]/ul//*[starts-with(text(), '" + tipoFinal[i] + "')]"));
            
            if (!elementDriver.isEmpty()) {
                WebElement pestania = elementDriver.get(0);
                if (pestania.isDisplayed()) {
                    pestania.click();
                    esperarCargaPantalla();
                    obtenerSelecion(pestania, valorFinal[0]);
                } else {
                    throw new Exception("No se encontro el tipo slo " + tipoFinal[i]);
                }
    
            } else {
                throw new Exception("No se encontro el tipo slo " + tipoFinal[i]);
            }

        }
        esperarCargaPantalla();
        ActionsUtil.sleepSeconds(NumeroConstans.TRES);
        migaPanRevision();
        esperarCargaPantalla();
        ActionsUtil.sleepSeconds(1);
        By botonEnviar = By.xpath("//button[@class=\"mobile-contracts__submit-button\"]");
        ActionsUtil.esperarPoderClicBy(driver, botonEnviar);
        ActionsUtil.clic(driver, botonEnviar);
    }*/
    
    /**
     * 
     * Método responsable
     *
     * @author HATABORDA <br>
     *        Marybel Infante Ayala <br>
     *        minfante@indracompany.com
     *
     * @date 20/01/2020
     * @version 1.0
     * @param pestania
     * @param busqueda
     * @throws Exception
     */

    private void obtenerSelecion(WebElement pestania, String busqueda) throws Exception {
    if (pestania.isDisplayed()) {
      WebElement tipoSlo = pestania.findElement(By.xpath(
                    "//div[@class=\"additional-offerings__table\"]//span[starts-with(text(),'" + busqueda + "')]"));
      if (tipoSlo.isDisplayed()) {
        WebElement contenedor = tipoSlo.findElement(By.xpath("../../.."));
        if (contenedor.isDisplayed()) {
          WebElement input = contenedor.findElement(By.xpath("input"));
          if (input.isEnabled()) {
            JavascriptExecutor executor = (JavascriptExecutor) getDriver();
            executor.executeScript("arguments[0].click();", input);
            esperarCargaPantalla();
                    }
                } else {
          String mensaje="No se encontro contendor de tipo slo";
          ActionsUtil.guardarExceptionControlada(mensaje);
          throw new Exception(mensaje);
                }
            } else {
        String mensaje="No se encontro tipo slo " + busqueda;
        ActionsUtil.guardarExceptionControlada(mensaje);
        throw new Exception(mensaje);
            }
        } else {
      String mensaje="No se encontro tipo slo " + busqueda;
      ActionsUtil.guardarExceptionControlada(mensaje);
      throw new Exception(mensaje);
        }
    } 

    /**
     * Método responsable
     *
     * @author HATABORDA <br>
     *         Marybel Infante Ayala <br>
     *         minfante@indracompany.com
     *
     * @date 30/12/2019
     * @version 1.0
     * @throws Exception
     */

    public void extraerNumeroordendeventa() throws Exception {
    By orndeVenta = By.xpath("//div[@class=\"roe-widget-header\"]//h1[contains(text(),'La orden de venta')]");
    ActionsUtil.esperarVisibleBy(driver, orndeVenta);
    String ordenVenta = driver.findElement(orndeVenta).getText();
    LOGGER.info(ordenVenta);
    numeroOrdenVenta = ordenVenta.substring(19, 29);
    LOGGER.info(numeroOrdenVenta);
    driver.switchTo().defaultContent();
    By ocultarBorradorTicket = By.xpath("//button[@class=\"gwt-Button ticket-panel-collapse-button expanded\"]");
    ActionsUtil.clic(driver, ocultarBorradorTicket);
    }

    /**
     * Método responsable
     *
     * @author HATABORDA <br>
     *         Marybel Infante Ayala <br>
     *         minfante@indracompany.com
     *
     * @date 30/12/2019
     * @version 1.0
     * @throws InterruptedException
     */
    public void volveralaOrden() {
    ActionsUtil.sleepSeconds(1);
    cambiarFrameInterno();
    ActionsUtil.sleepSeconds(1);
    By optionVolverOrden = By.xpath("//a[contains(text(),\"Volver a la orden\")]");
    ActionsUtil.esperarPoderClicBy(driver, optionVolverOrden);
    ActionsUtil.clic(driver, optionVolverOrden);
    ActionsUtil.sleepSeconds(1);
    }

    /**
     * Método responsable de ingresar a Entrada Orden de la cuenta después de
     * abrir el panel izquierdo.
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 14/02/2020
     * @version 1.0
     * @throws InterruptedException
     */
    public void entradadeOrden() throws InterruptedException {
        abrirPanelIzquierdo();
        sharedObjet("panelizquierdoentradaorden");
        ActionsUtil.esperarPoderClicBy(driver, getObjetoToAction());
        ActionsUtil.clic(driver, getObjetoToAction());
        ActionsUtil.sleepSeconds(1);
    }

    /**
     * Método responsable
     *
     * @author HATABORDA <br>
     *         Marybel Infante Ayala <br>
     *         minfante@indracompany.com
     *
     * @date 30/12/2019
     * @version 1.0
     * @throws Exception
     */
    public void seleccionarlaOrdenAnterior() throws Exception {
    List<WebElement> items = driver
                .findElements(By.xpath("//div[@class=\"ui-pager-gwt\"]//div[@class=\"gwt-HTML\"]"));
    if (!items.isEmpty()) {
      JavascriptExecutor executor = (JavascriptExecutor) getDriver();
      executor.executeScript("arguments[0].scrollIntoView(true);", items.get(0));
        }

    List<WebElement> filas = driver.findElements(By.xpath("//table[@class=\"GF5QP-ADOM TableCtrl\"]"));
    WebElement referencia = null;
    for (WebElement selenideElement : filas) {
      if (selenideElement.getText().contains(numeroOrdenVenta)) {
        referencia = selenideElement.findElement(By.xpath("//a[contains(text(),'" + numeroOrdenVenta + "')]"));
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        ActionsUtil.sleepSeconds(1);
        executor.executeScript("arguments[0].click();", referencia);
        return;
            }
        }
        throw new Exception("El numero " + numeroOrdenVenta + " No esta ");
    }

    /**
     * Método responsable
     *
     * @author HATABORDA <br>
     *         Marybel Infante Ayala <br>
     *         minfante@indracompany.com
     *
     * @date 30/12/2019
     * @version 1.0
     * @throws Exception
     */
    public void verificarNumerodeCuentayGuardarURL() throws Exception {
        String elementOrdenVenta = driver.findElement(By.xpath("//div[@class=\"gwt-Label nc-uip-page-caption\"]"))
                .getText();
        SubelementOrdenVenta = elementOrdenVenta.substring(16, 26);
        System.out.println(SubelementOrdenVenta);

        if (numeroOrdenVenta.equals(SubelementOrdenVenta)) {
            url = driver.getCurrentUrl();
            System.out.println(url);
        } else {
            throw new Exception("La orden de venta no es igual");
        }
    }

    public void cambiarFrameInterno() {
        ActionsUtil.switchFrameRoe(driver);

    }

    public void esperarHabilitarServicioCaracteristica() {
        ActionsUtil.esperarHabilitarServicioCaracteristica(driver);

    }

    /**
     * 
     * Método responsable de ingresar la palabra equipo
     *
     * @uthor hataborda <br>
     *        Diana Carolina Farfán <br>
     *        dfarfan@indracompany.com
     *
     * @date 8/01/2020
     * @version 1.0
     * @param tipoEquipo: " Equipo "
     */
    public void ingresarPlanServicioEquipo(String tipoEquipo) {
        WebElement inputBuscar = driver
                .findElement(By.xpath("//input[@class=\"search-filter__search-input__overview\"]"));

        if (inputBuscar.isDisplayed()) {
            inputBuscar.sendKeys("Equipo");
            List<WebElement> equipos = driver.findElements(By.xpath(
                    "//div[@class=\"roe-widget-content wrapper_selection\"]//tr[@class=\"roe-table-row\"]//td[@class=\"roe-table-cell name\"]"));
            for (WebElement equipo : equipos) {
                if (equipo.isDisplayed() && equipo.getText().contains(tipoEquipo)) {
                    equipo.click();
                    ActionsUtil.cargandoFrameInterno(driver);
                    return;
                }
            }
        }
    }
//        else {
//            throw new Exception(ProcesoConstantes.MENSAJE_ERROR_NO_VISIBLE + "Servicio de búsqueda");
//        }
//        throw new Exception("No fue posible localizar el plan " + tipoEquipo);

    public void planRecienAnyadido() {
        By by = By.xpath("//label[@class='top-tree__input']");
        ActionsUtil.clic(driver, by);
        ActionsUtil.cargandoFrameInterno(driver);
    }

    public void ingresarPlanServicioBusqueda(String plan) {
        ActionsUtil.cargandoFrameInterno(driver);
        By input = By.xpath("//input[@class='search-filter__search-input__overview']");
        ActionsUtil.esperarVisibleBy(driver, input);
        ActionsUtil.setTextField(driver, input, plan.split(" ")[0]);

        List<WebElement> filas = driver.findElements(By.xpath(
                "//div[@class='roe-widget-content wrapper_selection']//td[@class='roe-table-cell name']/span/span"));
        for (WebElement item_fila : filas) {
            if (item_fila.isDisplayed() && item_fila.getText().startsWith(plan)) {
                item_fila.click();
                ActionsUtil.cargandoFrameInterno(driver);
                break;
            }
        }
        bdPlan=plan;
    }

    public void validarPlanSelecionarChipPrepago(String plan, String nombreChip) {
        esPrepago = ActionsUtil.validarPlanPrepago(plan);
        
        if (esPrepago) {
            clicTexto(nombreChip, false);
            ActionsUtil.cargandoFrameInterno(driver);
        }else {
            esPrevioPago=ActionsUtil.validarPlanPrevioPago(plan);
        }
    }

    public void clicTexto(String texto, boolean cargarPropiedad) {
        if (cargarPropiedad) {
            texto = properties.getProperty(texto);
        }
        // TODO mover a la clase ObjetosPlmLogin.java
        By boton = By.xpath("//span[contains(text(),'" + texto + "')]");
        ActionsUtil.esperarPoderClicBy(driver, boton);
        ActionsUtil.clic(driver, boton);
    }

    public void selecionarContratoICCDNumeroTelefono(String contrato, String numeroSim, String numeroTelefono) {

        By filas = By.xpath("//tr[@class='characteristic-item force_enabled']");
        ActionsUtil.esperarVisibleListaBySegundo(driver, filas, 120);

        List<WebElement> filaElemento = driver.findElements(filas);
        String duracionContrato = properties.getProperty("texto.duracion.contrato");
        String sim = properties.getProperty("texto.iccid");
        String telefono = properties.getProperty("texto.numero.telefono");

        for (WebElement webElement : filaElemento) {
            if (!esPrepago) {
                if (webElement.getText().contains(duracionContrato)) {
                    Select selectContrato = new Select(webElement.findElement(By.xpath(".//select")));
                    selectContrato.selectByVisibleText(contrato);
                }
            }
            if (webElement.getText().contains(sim)) {
                WebElement input = webElement.findElement(By.xpath(".//input[@class='refsel_input']"));
                input.clear();
                input.sendKeys(numeroSim);
                buscarPrimerResultado(true);
                ActionsUtil.cargandoFrameInterno(driver);
                simCardInicial=input.getAttribute("value");
            }
            if (webElement.getText().contains(telefono)) {
                WebElement input = webElement.findElement(By.xpath(".//input[@class='refsel_input']"));
                if(numeroTelefono==null || numeroTelefono.isEmpty()) {
                    numeroTelefono=input.getAttribute("value");
                }else {
                    input.clear();
                    ActionsUtil.cargandoFrameInterno(driver);
                    input.sendKeys(numeroTelefono);
                    buscarPrimerResultado(true);
                    ActionsUtil.cargandoFrameInterno(driver);
                }
                this.bdNumeroTelefono=numeroTelefono;
            }
        }
        boolean existeICCD = false;
        boolean existeNumeroTelefono = false;
        boolean existeDuracionContrato = false;

    }


    /**
     * 
     * Método responsable validar el equipo e invocar el método adecuado
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 10/02/2020
     * @version 1.0
     * @param nombreEquipo nombre del equipo
     * @param imei identificador del teléfono
     * @param numeroCuota numero de cuotas
     * @throws Exception
     */
    public void validarEquipoIngresarImeiNumeroCuotas(String nombreEquipo, String imei, String numeroCuota) throws Exception {
        nombreDelEquipoRenovacion = nombreEquipo;
        imeiDelEquipoRenovacion = imei;

        String equipoExterno = properties.getProperty("texto.equipo.externo");
        nombreDelEquipoInterno=nombreEquipo;
        if (equipoExterno.equals(nombreEquipo)) {
            bdTipoEquipo=equipoExterno;
            // TODO mejorar xphat
            By agregar = By.xpath("//*[@id=\"Celulares\"]/div/div[1]/div/table/tbody/tr[1]/td[3]/a");
            ActionsUtil.esperarPoderClicBy(driver, agregar);
            ActionsUtil.clic(driver, agregar);
            ActionsUtil.cargandoFrameInterno(driver);
            validarIMEIExterno(imei);
        } else {
            bdTipoEquipo=nombreEquipo;
            equipoInterno(nombreEquipo, false);
            validarIMEIInterno(nombreEquipo,imei);
            if (!esPrepago && !"0".equals(numeroCuota)) {
                localizarPagoCuotasInterno(numeroCuota);
            }
        }
    }

    private void equipoInterno(String nombreEquipo, Boolean selecionarCantidadCopias) {
        WebElement inputModelo = driver.findElement(By.xpath("//div[@class=\"search-filter__type\"]/input"));
        inputModelo.clear();
        inputModelo.sendKeys(nombreEquipo);
        WebElement botonSiguiente = driver
                .findElement(By.xpath("//*[@id=\"Celulares\"]/div/div[4]/table/tbody/tr/td[4]/img"));

        String parar = null;
        do {
            parar = botonSiguiente.getAttribute("aria-disabled");
            List<WebElement> filas = driver.findElements(By.xpath("//*[@id=\"Celulares\"]/div/div"));
            for (WebElement selenideElement : filas) {
                if (selenideElement.isDisplayed() && selenideElement.getText().contains(nombreEquipo)) {
                    List<WebElement> filas2 = selenideElement.findElements(By.xpath("./div/table/tbody/tr"));
                    for (WebElement selenideElement1 : filas2) {
                        if (selenideElement1.isDisplayed() && selenideElement1.getText().contains(nombreEquipo)) {
                            (selenideElement1.findElement(By.xpath("./td[3]/a"))).click();
                            ActionsUtil.cargandoFrameInterno(driver);
                            if (selecionarCantidadCopias) {
                                By botonOk = By.xpath("//span[@class='ui-button-text'][contains(text(),'OK')]");
                                ActionsUtil.esperarPoderClicBy(driver, botonOk);
                                driver.findElement(botonOk).click();
                            }
                            return;
                        }
                    }
                }
            }
            botonSiguiente.click();
        } while ("false".equals(parar));
    }

    private void validarIMEIExterno(String imei) {
        WebElement label;
        List<WebElement> filas = driver.findElements(By.xpath("//div[@class='roe-widget-area selected-hardware-offers']"
                + "//*[@class='characteristic-item force_enabled']"));
        String texto = properties.getProperty("texto.imei.equipo.externo");
        for (WebElement selenideElement : filas) {
            label = selenideElement.findElement(By.xpath(".//span"));
            if (texto.equals(label.getText())) {
                WebElement input = selenideElement.findElement(By.xpath(".//input"));
                input.clear();
                if(imei==null || imei.isEmpty()) {
                    imei=obtenerImeiExterno();
                }
                imeiDelEquipoIterno=imei;
                input.sendKeys(imei);
                WebElement boton = selenideElement
                        .findElement(By.xpath(".//a[@class='bt_bt tfnecu-validate-imei-button']"));
                boton.click();
                ActionsUtil.cargandoFrameInterno(driver);
                break;
            }
        }
    }

    /**
     * 
     * Método responsable de realizar la valiacion unicamente de equipos internos
     *
     * @uthor hataborda <br>
     *        Diana Carolina Farfán <br>
     *        dfarfan@indracompany.com
     *
     * @date 13/01/2020
     * @version 1.0
     * @param nombreEquipo
     * @param imei
     * @param numeroCuota
     * @param copias
     * @throws Exception 
     */
    public void validarEquipoInterno(String nombreEquipo, String imei, String numeroCuota) throws Exception {

        equipoInterno(nombreEquipo, true);
        validarIMEIInterno(nombreEquipo,imei);
    }

    /**
     * 
     * Método responsable de asignar el valor al IMEI de equipo interno
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 10/02/2020
     * @version 1.0
     * @param nombreEquipo nombre del equipo
     * @param imei identificador del teléfono
     * @throws Exception
     */
    public void validarIMEIInterno(String nombreEquipo, String imei) throws Exception {
        String textoIMEI = properties.getProperty("texto.imei");
        WebElement input = obtenerElementoEspecificoTabla("tablaequipointerno","inputgenerico",textoIMEI+"*");
        
        if (imei == null || imei.isEmpty()) {
            imei = obtenerImeiInterno(nombreEquipo);
        }
        input.clear();
        input.sendKeys(imei);
        ActionsUtil.cargandoFrameInterno(driver);
        buscarPrimerResultado(Boolean.TRUE);
        ActionsUtil.cargandoFrameInterno(driver);
        imeiDelEquipoIterno = imei;

    }
    

    private void localizarPagoCuotasInterno(String coutas) {
        String texto = properties.getProperty("texto.pago.cuotas");
        WebElement checkCuotas = driver.findElement(By.xpath(
                "//div[@class=\"roe-widget-area selected-hardware-offers\"]//table[@class=\"parameters\"]//span[contains(text(),'"
                        + texto + "')]"));
        WebElement parent = ActionsUtil.parent(checkCuotas);
        WebElement inputCuotas = parent.findElement(By.xpath("./input[@class='checkbox']"));
        inputCuotas.click();
        ActionsUtil.cargandoFrameInterno(driver);
        WebElement elementCuota = driver.findElement(By.xpath(
                "//div[@class=\"roe-widget-area selected-hardware-offers\"]//table[@class=\"parameters\"]//tr[@class=\"characteristic-item force_enabled\"]//select"));

        Select selectCuota = new Select(elementCuota);
        selectCuota.selectByVisibleText(coutas);
        ActionsUtil.cargandoFrameInterno(driver);
    }

    public void validarCrearCliente(String cliente) throws Exception {
        switch (cliente) {
        case "0":
            break;
        case "1":
            /* pestaña - Facturación y pago */
            migaPanFacturacionPago();
            existeCuentaFacturacion();
            break;
        default:
            // throw new Exception("no esta contemplada la opcion de cliente: " + cliente);
        }

    }

    public void migaPanRevision() {
        String texto = properties.getProperty("miga.pan.revision");
        localizarMigaDePan(texto);
    }

    public void migaPanFacturacionPago() {
        String texto = properties.getProperty("miga.pan.facturacion.pago");
        localizarMigaDePan(texto);
    }

    private void localizarMigaDePan(String migaPan) {
        List<WebElement> pestania = driver.findElements(By.xpath("//a[@class=\"gwt-InlineHyperlink roe-pathList\"]"));
        for (WebElement selenideElement : pestania) {
            if (migaPan.equals(selenideElement.getText())) {
                selenideElement.click();
                ActionsUtil.cargandoFrameInterno(driver);
                if (!"act".equals(ActionsUtil.parent(selenideElement).getAttribute("class"))) {
                    selenideElement.click();
                    ActionsUtil.cargandoFrameInterno(driver);
                }
                return;
            }
        }
    }

    public void existeCuentaFacturacion() throws Exception {
        ActionsUtil.sleepSeconds(2);
        By selectError = By.xpath("//select[@class='input-errorHighlight']");
        WebElement selectElement = ActionsUtil.validacionInmediata(driver, selectError);
        if (selectElement != null) {
            botonNuevaCuentaFacturacion();
            System.out.println("datos del select");
            ActionsUtil.cargandoFrameInterno(driver);
            selectElement = driver.findElement(selectError);
            Select select = new Select(selectElement);
            for (WebElement elemento : select.getAllSelectedOptions()) {
                System.out.println(elemento.getText());
            }
            ActionsUtil.cargandoFrameInterno(driver);
            select.selectByIndex(1);
            ActionsUtil.cargandoFrameInterno(driver);
        }
    }

    public void botonNuevaCuentaFacturacion() throws Exception {

        String texto = properties.getProperty("boton.crear");
        String xpahtBotonCrear = "//div[@class='ui-widget-overlay-under-wrapper']//button[contains(text(),'" + texto
                + "')]";
        String botonNuevaCuentaFacturacion = null;
        if (esPrepago) {
            botonNuevaCuentaFacturacion = "boton.nueva.cuenta.facturacion.prepago";
        } else {
            botonNuevaCuentaFacturacion = "boton.nueva.cuenta.facturacion.pospago";
        }

        clicBotonTexto(botonNuevaCuentaFacturacion);
        ActionsUtil.cargandoFrameInterno(driver);
        By boton = By.xpath(xpahtBotonCrear);
        ActionsUtil.esperarPoderClicBy(driver, boton);
        /*obtener la cuenta*/
        sharedObjet("tablasuperiorcuentafacturacionpago");
        WebElement filaCuentaFacturacion = ActionsUtil.obtenerElementoReferenciaTabla(driver,getObjetoToAction(),"Nombre de cuenta de facturación");
        sharedObjet("textreadonly");
        WebElement elementoCuentaFacturacion=filaCuentaFacturacion.findElement(getObjetoToAction());
        cuentaFacturacion = elementoCuentaFacturacion.getText().replace("Pospago", "").replace("Prepago", "").replace(":", "").trim();
        /**/
        if(esPrevioPago) {
            sharedObjet("tablainferiorcuentafacturacionpago");
            WebElement elemento = ActionsUtil.obtenerElementoReferenciaTabla(driver,getObjetoToAction(),"Subtipo de cuenta");
            sharedObjet("selectgenerico");
            Select elementoSelect = new Select(elemento.findElement(getObjetoToAction()));
            elementoSelect.selectByVisibleText("In Advance (Por Adelantado)");
        }
        ActionsUtil.clic(driver, boton);
        ActionsUtil.cargandoFrameInterno(driver);

    }

    public void clicBotonFinalProcesoReservarActivo() {
        String texto = properties.getProperty("boton.final.reservar.activos");
        clicBotonFinalProceso(texto);
        ActionsUtil.cargandoFrameInterno(driver);
    }

    public void clicBotonFinalProcesoConfigurarContrato() {
        String texto = properties.getProperty("boton.final.configurar.contrato");
        if (!esPrepago) {
            clicBotonFinalProceso(texto);
            /* Modo de gestión de contratos */
            ActionsUtil.botonOkConfirmar(driver);
            ActionsUtil.cargandoFrameInterno(driver);
        }
    }

    public void clicBotonFinalProcesoGenerarFactura() {
        String texto = properties.getProperty("boton.final.generar.factura");
        clicBotonFinalProceso(texto);
        ActionsUtil.cargandoFrameInterno(driver);
        /* boton ok */
        ActionsUtil.botonOkConfirmar(driver);
        ActionsUtil.cargandoFrameInterno(driver);
        /* confirmar */
        ActionsUtil.botonOkConfirmar(driver);
        ActionsUtil.cargandoFrameInterno(driver);
    }

    public void clicBotonFinalEnviar() {

        By botonEnviar = By.xpath("//button[@class='mobile-contracts__submit-button']");
        ActionsUtil.esperarPoderClicBy(driver, botonEnviar);
        ActionsUtil.clic(driver, botonEnviar);
        ActionsUtil.cargandoFrameInterno(driver);
        ActionsUtil.botonOkConfirmar(driver);
        ActionsUtil.cargandoFrameInterno(driver);

        WebElement elemento = ActionsUtil.validacionInmediata(driver, botonEnviar);
        if (elemento != null) {
            ActionsUtil.sleepSeconds(8);
            elemento.click();
            ActionsUtil.cargandoFrameInterno(driver);
            ActionsUtil.botonOkConfirmar(driver);
            ActionsUtil.cargandoFrameInterno(driver);
        }
    }

    public void clicBotonFinalProceso(String nombreBoton) {
        WebElement botonFinalProceso = botonFinalesProceso(nombreBoton);
        if (botonFinalProceso != null && !botonFinalProceso.getAttribute("class").contains("disabled")) {
            botonFinalProceso.click();
        } else {
//           System.out.println();
            System.err.println("no encontro el boton:" + nombreBoton);
        }

    }

    public WebElement botonFinalesProceso(String botonFinalReservarActivos) {
        By listaBotones = By.xpath(
                "//li[@class='mobile-contracts__list-item']//div[@class='contracts__button-wrap'][not(contains(@style, 'display: none'))]//button");

        List<WebElement> elemento = driver.findElements(listaBotones);

        if (!elemento.isEmpty()) {
            ActionsUtil.esperarVisibleBy(driver, listaBotones);
        }

        for (WebElement selenideElement : elemento) {
            if (botonFinalReservarActivos.equals(selenideElement.getText())) {
                return selenideElement;
            }
        }
        // sino existe el boton con el texto
        return null;
    }

    // Cambio Sim

    /**
     * Método responsable
     *
     * @author HATABORDA <br>
     *         Marybel Infante Ayala <br>
     *         minfante@indracompany.com
     *
     * @date 7/01/2020
     * @version 1.0
     * @param tipo
     * @throws Exception
     */
    public void validarChipUsim(String tipoPago) throws Exception {
    esperarCargaPantalla();
    ActionsUtil.espererarDesparecerCargando(driver);
    List<WebElement> filasTabla = driver.findElements(
                By.xpath("//*[@class=\"offer_group_wrapper\"]//*[@class=\"roe-table-body offer_tree_tbody\"]"));

    if ("0".equals(tipoPago)) {
      validarElementoUnaVez(null, "//span[.='CHIP PREPAGO REPOSICION']", "CHIP PREPAGO REPOSICION");
      ActionsUtil.sleepSeconds(1);
      } else if ("1".equals(tipoPago) || "2".equals(tipoPago)) {
      validarElementoUnaVez(null, "//span[.='CHIP POSPAGO REPOSICION']", "CHIP POSPAGO REPOSICION");
      ActionsUtil.sleepSeconds(1);
      if ("1".equals(tipoPago)) {
        for (WebElement webElement : filasTabla) {
          if (webElement.getText().contains("CHIP POSPAGO REPOSICION")) {
            selecionarPagoEnCuotas(webElement);
            ActionsUtil.sleepSeconds(1);
            selecionarNumeroCuotas(webElement);
            ActionsUtil.sleepSeconds(1);
            return;
                    }
                }
                //throw new Exception(
                //"No se encontro el panel:" +
                //ProcesoConstantes.ENCABEZADO_SERVICIOS_DATOS_FIJOS_DIGITALES);
            }
        } else {
            // throw new ExcepcionTecnica("El parametro no es correcto");
        }
    }

    /**
     * Método responsable
     *
     * @author HATABORDA <br>
     *         Marybel Infante Ayala <br>
     *         minfante@indracompany.com
     *
     * @date 7/01/2020
     * @version 1.0
     * @param string
     * @param object
     * @param string
     * @param string2
     */
    private void validarElementoUnaVez(WebElement elementoH1Titulo, String elemento, String string) {
    esperarCargaPantalla();
    ActionsUtil.espererarDesparecerCargando(driver);
    WebElement pre = null;
    if (elementoH1Titulo == null) {
      pre = driver.findElement(By.xpath(elemento));
      } else {
      pre = elementoH1Titulo.findElement(By.xpath(elemento));
      }
    if (pre.isEnabled()) {
      pre.click();
      } else {
            // throw new ExcepcionTecnica("No se encontro el elemento: " + mensaje);
        }
    ActionsUtil.sleepSeconds(1);
    }

    /**
     * Método responsable
     *
     * @author HATABORDA <br>
     *         Marybel Infante Ayala <br>
     *         minfante@indracompany.com
     *
     * @date 7/01/2020
     * @version 1.0
     * @param webElement
     * @throws Exception
     */
    private void selecionarPagoEnCuotas(WebElement webElement) throws Exception {
    esperarCargaPantalla();
    ActionsUtil.espererarDesparecerCargando(driver);
    JavascriptExecutor executor = (JavascriptExecutor) getDriver();

    for (WebElement webElement2 : webElement.findElements(By.xpath("//span[.=' Pago en cuotas ']"))) {
      if (webElement2.isDisplayed()) {
        WebElement parent = ActionsUtil.parent(webElement2);
        WebElement inputPagoEnCuotas = parent.findElement(By.xpath("./input[@class='checkbox']"));
        executor.executeScript("arguments[0].click();", inputPagoEnCuotas);
        return;
            }
        }
    throw new Exception("No se encontro el elemento: " + "Pago en cuotas");

    }

    /**
     * Método responsable
     *
     * @author HATABORDA <br>
     *         Marybel Infante Ayala <br>
     *         minfante@indracompany.com
     *
     * @date 7/01/2020
     * @version 1.0
     * @param webElement
     * @throws Exception
     */
    private void selecionarNumeroCuotas(WebElement webElement) throws Exception {
    for (WebElement webElement2 : webElement
                .findElements(By.xpath("//*[@class=\"characteristic-item force_enabled\"]"))) {
      if (webElement2.isDisplayed() && webElement2.getText().contains("NumeroConstans de cuotas")) {
        WebElement selectNumeroCuotas = webElement2.findElement(By.xpath(
                        "*[@class=\"cell value\"]/*[@class=\"characteristicValues characteristic-invalid\"]/div/select"));
        esperarCargaPantalla();
        ActionsUtil.sleepSeconds(2);
        selectNumeroCuotas.sendKeys("1");
        return;
            }
        }
        throw new Exception("No se encontro el elemento: " + "Select NumeroConstans Cuotas");
    }

    /**
     * Método responsable
     *
     * @author HATABORDA <br>
     *         Marybel Infante Ayala <br>
     *         minfante@indracompany.com
     *
     * @date 7/01/2020
     * @version 1.0
     * @param tipoPago
     * @throws Exception
     */
    public void seleccionarICCD(String iccid) throws Exception {
    esperarCargaPantalla();
    ActionsUtil.espererarDesparecerCargando(driver);
    for (WebElement webElement2 : driver
                .findElements(By.xpath("//tr[@class=\"characteristic-item force_enabled\"]"))) {
      if (webElement2.isDisplayed() && webElement2.getText().contains("ICCID")) {
        WebElement inputs = webElement2.findElement(By.xpath(".//input[@class=\"refsel_input\"]"));
        if (!inputs.isDisplayed()) {
          throw new Exception("No se encontro el elemento: input Select ICCID");
                }
        WebElement simVIeja=driver.findElement(By.id("gwt-uid-1838_input"));
        simcardVieja=simVIeja.getAttribute("value");
        ActionsUtil.sleepSeconds(1);
        inputs.sendKeys(iccid);
        ActionsUtil.sleepSeconds(1);
        validarPrimerResultado(inputs, iccid, "ICCD");
        return;
            }
        }
    throw new Exception("No se encontro el elemento: " + "Select ICCID");
    }

    /**
     * Método responsable
     *
     * @author HATABORDA <br>
     *         Marybel Infante Ayala <br>
     *         minfante@indracompany.com
     *
     * @date 8/01/2020
     * @version 1.0
     * @param inputs
     * @param iccid
     * @param string
     * @throws Exception
     */
    private void validarPrimerResultado(WebElement inputs, String iccid, String string) throws Exception {
    ActionsUtil.sleepSeconds(2);
    String codigoPrimerResultado = "//*[@id=\"nc_refsel_list_row_0\"]/*[@class=\"refsel_name\"]";
    WebElement primerResultado = driver.findElement(By.xpath(codigoPrimerResultado));
    String codigoMensajeError = "//*[@id=\"nc_refsel_list\"]/*[@class=\"refsel_error\"]/span";
    if (primerResultado.isDisplayed()) {
      primerResultado.click();
      ActionsUtil.sleepSeconds(1);
      WebElement simVIeja=driver.findElement(By.id("gwt-uid-1780_input"));
      simcardNueva=simVIeja.getAttribute("value");
      } else {
      inputs.sendKeys(iccid);
      if (!inputs.isDisplayed()) {
        throw new Exception("No se encontro el elemento: input " + string);
        }
      ActionsUtil.sleepSeconds(2);
      primerResultado = driver.findElement(By.xpath(codigoPrimerResultado));
      if (primerResultado.isDisplayed()) {
        primerResultado.click();
        ActionsUtil.sleepSeconds(1);
        WebElement simVIeja=driver.findElement(By.id("gwt-uid-1780_input"));
        simcardNueva=simVIeja.getAttribute("value");
        } else {
        WebElement mensajeError = driver.findElement(By.xpath(codigoMensajeError));
        if (mensajeError.isDisplayed()) {
          throw new Exception(mensajeError.getText().replaceAll("\n", "-"));
          } else {
          throw new Exception("No se encontro Mensaje de error: " + string);
                }
            }
        }

    }

    // Suspensión Reanudar
    /**
     * Método responsable
     *
     * @author HATABORDA <br>
     *         Marybel Infante Ayala <br>
     *         minfante@indracompany.com
     *
     * @date 10/01/2020
     * @version 1.0
     * @param boton
     */
    public void clicEnBotonPorTextoEnServiciosyCaracteristicas(String boton) {
    boton = properties.getProperty(boton);
    ActionsUtil.sleepSeconds(2);
    ActionsUtil.cargandoFrameInterno(driver);
    WebDriverWait wait = new WebDriverWait(driver, 150);
    By botonTexto = By.xpath("//a[text()='" + boton + "']");
    ActionsUtil.esperarPoderClicBy(driver, botonTexto);
    ActionsUtil.clic(driver, botonTexto);
    }

    /**
     * Método responsable
     *
     * @author HATABORDA <br>
     *         Marybel Infante Ayala <br>
     *         minfante@indracompany.com
     *
     * @date 9/01/2020
     * @version 1.0
     * @param string
     * @throws Exception
     */
    public void asignarRazonDelProceso(String razon, String boton, String equipo) throws Exception {
    boolean encontrado = false;
    Select elemen = new Select(getDriver().findElement(By.xpath("//*[@id=\"ui-id-1\"]/div/div/select")));
    try {
      elemen.selectByVisibleText(razon);
        } catch (Exception e) {
      String mensaje="Elemento no valido para la selección de motivo de suspensión: " + razon;
      ActionsUtil.guardarExceptionControlada(mensaje,e);
      throw new Exception(mensaje);
        }

    ActionsUtil.sleepSeconds(1);

        // si la bandera de quitar árbol de elementos está activa(1) se
        // desactiva el elemento Equipo

    if ("1".equals(equipo) && "Robo".equals(razon)) {
      encontrado = false;
      List<WebElement> listCheCkboxes = getDriver().findElements(By.className("order-items-tree-item-mobile"));
      boolean esElementoInterno = false;
      for (WebElement l : listCheCkboxes) {
        // tomo los hijos de cada elemento
        List<WebElement> lc = l.findElements(By.tagName("input"));
        for (WebElement c : lc) {
          String type = c.getAttribute("type");
          if ("checkbox".equals(type)) {
            if (esElementoInterno) {
              c.click();
              encontrado = true;
              break;
              } else {
              esElementoInterno = true;
                        }
                    }
                }

        if (encontrado) {
          break;
                }
            }

      if (!encontrado) {
        throw new Exception("No se encontro checkbox de plan");
            }
        }
        // Select button Suspender or Reanudar
    boton = properties.getProperty(boton);
    encontrado = false;
    List<WebElement> listBotonSuspender2 = getDriver().findElements(By.className("ui-button-text"));
    for (WebElement l : listBotonSuspender2) {
      String s = l.getText();
      if (s.equals(boton)) {
        l.click();
        ActionsUtil.sleepSeconds(1);
        encontrado = true;
        break;
            }
        }

    if (!encontrado) {
      throw new Exception("No se encontro el modal");
        }

    ActionsUtil.cargandoFrameInterno(driver);
    }

    /**
     * Método responsable
     * 
     * Incluir quien reporta el robo
     * Click en notificaciones
     * 
     * @author HATABORDA <br>
     *         Marybel Infante Ayala <br>
     *         minfante@indracompany.com
     *
     * @date 9/01/2020
     * @version 1.0
     * @param string
     * @throws Exception
     */
    public void validarRazonDelProceso(String razon, String motivo) throws Exception {
    boolean encontrado = false;
    //encontrado = false;
    if ("Robo".equals(razon)) {
      encontrado = false;
      ActionsUtil.sleepSeconds(1);
      List<WebElement> notificaciones = getDriver().findElements(By.className("notifications-block__sh-button"));
      for (WebElement l : notificaciones) {
        encontrado = true;
        l.click();
        }
      if (!encontrado) {
        throw new Exception("No se encuentran notificaciones");
          }
      ActionsUtil.sleepSeconds(1);

      // Encuentro el texto "Quién informó sobre el robo"
      encontrado = false;
      List<WebElement> texto = getDriver().findElements(By.className("tfnecu_notification_text-box"));
      WebElement ultimoTexto = null;
      ActionsUtil.sleepSeconds(1);
      for (WebElement l : texto) {
        encontrado = true;
        ultimoTexto = l;
            }
      ActionsUtil.sleepSeconds(1);
      if (ultimoTexto != null) {
        ultimoTexto.sendKeys(motivo);
        ultimoTexto.sendKeys(Keys.ENTER);
        ActionsUtil.sleepSeconds(3);
            }
      if (!encontrado) {
        throw new Exception("No se encuentra texto de robo");
            }
        }
    ActionsUtil.sleepSeconds(1);
    }

    // Inicio renovación equipo
    /**
     * 
     * Método responsable de localizar y dar clic en el enlace Desconectar
     *
     * @uthor hataborda <br>
     *        Jhon Edisson Hurtado <br>
     *        jehocampo@indracompany.com
     *
     * @date 10/01/2020
     * @version 1.0
     */
    public void darClicLabelDesconectar() {
        ActionsUtil.cargandoFrameInterno(driver);
        By button = By.xpath("//div[@class=\"roe-widget-area selected-hardware-offers\"]//a[.='Desconectar']");
        ActionsUtil.esperarVisibleBy(driver, button);
        ActionsUtil.clic(driver, button);
    }

    /**
     * 
     * Método responsable de asignar "Cambio de equipo" como razón
     * de desconexión
     *
     * @uthor hataborda <br>
     *        Jhon Edisson Hurtado <br>
     *        jehocampo@indracompany.com
     *
     * @date 10/01/2020
     * @version 1.0
     * @throws Exception
     */
    public void asignarCambioEquipoRazonDesconexion() throws Exception {
        String valorSelect = "Cambio de equipo";
        /* combo Razón de desconexión */
        WebElement webElementselect = driver.findElement(By.xpath(
                "//div[@class=\"ui-dialog-content ui-widget-content\"]//div[@class=\"information_box_popup\"]//select"));
        if (webElementselect.isDisplayed()) {
            if (webElementselect.getText().contains(valorSelect)) {
                return;
            } else {
                List<WebElement> opciones = webElementselect.findElements(By.xpath(".//option"));
                for (int i = 0; i < opciones.size(); i++) {
                    if (valorSelect.equals(opciones.get(i).getText())) {
                        Select select = new Select(webElementselect);
                        select.selectByVisibleText(valorSelect);
                        ActionsUtil.cargandoFrameInterno(driver);
                        return;
                    }
                }
            }

            throw new Exception("No se encontro Cambio de equipo en los valores del select");
        } else {
            throw new Exception("No se encontro el select Razón de desconexión");
        }

    }

    /**
     * 
     * Método responsable de localizar y hacer clic en el botón "Desconectar"
     * que se muestra en la ventana modal
     *
     * @uthor hataborda <br>
     *        Jhon Edisson Hurtado <br>
     *        jehocampo@indracompany.com
     *
     * @date 10/01/2020
     * @version 1.0
     * @throws Exception
     */
    public void darClicEnBotonModalDesconectar() throws Exception {
        WebElement parentSpan = driver
                .findElement(By.xpath("//../div[@class=\"ui-dialog-buttonset\"]//span[.='Desconectar']"));
        if (parentSpan.isEnabled()) {
            parentSpan.click();
            ActionsUtil.cargandoFrameInterno(driver);
        } else {
            throw new Exception("No se encontro el boton Desconectar del modal");
        }
    }

    /**
     * 
     * Método responsable de cambiar al FramePrincipal
     *
     * @uthor hataborda <br>
     *        Jhon Edisson Hurtado <br>
     *        jehocampo@indracompany.com
     *
     * @date 10/01/2020
     * @version 1.0
     * @throws Exception
     */
    public void cambiarFramePrincipal() {
        ActionsUtil.switchToDefaultContent(driver);

    }

    /**
     * 
     * Método responsable de inicializar la variable booleana esPrepago. Esta
     * variable permite identificar si el numero usado para la prueba esta asociado
     * a un plan prepago o pospago. El método también captura el nombre del plan 
     * 
     * @uthor hataborda <br>
     *        Jhon Edisson Hurtado <br>
     *        jehocampo@indracompany.com
     *
     * @date 9/01/2020
     * @version 1.0
     */
    public void identificarPlanActual() {
        esPrepago = ActionsUtil.validarPlanPrepago(ActionsUtil.planActual(driver));
        nombrePlan = ActionsUtil.nombrePlanActual(driver);
    }

    /**
     * 
     * Método responsable
     *
     * @uthor hataborda <br>
     *        Jhon Edisson Hurtado <br>
     *        jehocampo@indracompany.com
     *
     * @date 9/01/2020
     * @version 1.0
     * @param terminalOrigen
     */
    public void identificarProcedenciaTerminalActual() {
        ActionsUtil
                .esTerminalActualExterno(ActionsUtil.consultarProcedenciaTermninalActual(driver));
    }

    // Fin renovación equipo
    /**
     * 
     * Método responsable
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 7/01/2020
     * @version 1.0
     * @param plan
     * @throws Exception
     */
    public void validarMismoPlan(String nuevoPlan) throws Exception {
        planActual = ActionsUtil.planActual(driver);
        planNuevo=nuevoPlan;
        if (planActual.contains(nuevoPlan)) {
            throw new Exception("es el mismo plan");
        }

    }

    /**
     * 
     * Método responsable
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 7/01/2020
     * @version 1.0
     */
    public void abrirMenuHorizontal() {
        ActionsUtil.cargandoFrameInterno(driver);
        By elemento = By.xpath("//*[@class='slide_changeto arrow_right']");
        ActionsUtil.esperarPoderClicBy(driver, elemento);
        ActionsUtil.sleepSeconds(2);
        ActionsUtil.clic(driver, elemento);
    }

    /**
     * 
     * Método responsable
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 7/01/2020
     * @version 1.0
     */
    public void clicIconoLupa() {
        By elemento = By.xpath("//*[@class='search-filter__search-magnifier']");
        ActionsUtil.clic(driver, elemento);

    }

    /**
     * 
     * Método responsable
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 7/01/2020
     * @version 1.0
     * @param plan
     */
    public void ingresarLupa(String plan) {
        By elemento = By.xpath("//*[@class='search-filter__search-input']");
        ActionsUtil.setTextField(driver, elemento, plan);
        ActionsUtil.cargandoFrameInterno(driver);
    }

    /**
     * 
     * Método responsable
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 8/01/2020
     * @version 1.0
     * @param planNuevo
     */
    public void validarTipoCambioPlan(String planNuevo) {
        Boolean planActualEsPrepago = ActionsUtil.validarPlanPrepago(planActual);
        Boolean planNuevoEsPrepago = ActionsUtil.validarPlanPrepago(planNuevo);
        StringBuilder tipoCambioPlanBuilder = new StringBuilder("");
        if (planActualEsPrepago) {
            tipoCambioPlanBuilder.append("PRE_");
        } else {
            tipoCambioPlanBuilder.append("POS_");
        }
        if (planNuevoEsPrepago) {
            tipoCambioPlanBuilder.append("PRE");
            esPrepago = Boolean.TRUE;
        } else {
            tipoCambioPlanBuilder.append("POS");
            esPrepago = Boolean.FALSE;
        }

        tipoCambioPlan = tipoCambioPlanBuilder.toString();

    }

    /**
     * 
     * Método responsable
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 9/01/2020
     * @version 1.0
     */
    public void validarAdendum() {

        if (tipoCambioPlan == null || CASO_POS_PRE.equals(tipoCambioPlan)) {
            List<WebElement> listaPanelIzquierdo = driver
                    .findElements(By.xpath("//span[@class='bulkTreeItem bulkTreeItem MobileRoeTreeItem']"));
            for (WebElement itemPanelIzquierdo : listaPanelIzquierdo) {
                if (itemPanelIzquierdo.getText().contains("Adendum")) {
                    itemPanelIzquierdo.click();
                    ActionsUtil.cargandoFrameInterno(driver);
//                    Selenide.sleep(getParametroTiempoFrameInternoCargar() * 500);
                    /* pago en cutoas */
                    WebElement pagocuotas = driver.findElement(By.xpath("//span[contains(text(),'Pago en cuotas')]"));
                    WebElement contenedorElement = ActionsUtil.parent(pagocuotas);
//                            JavascriptExecutor executor = (JavascriptExecutor) driver;
//                            executor.executeScript("arguments[0].click();", input.$(By.xpath("./input")));
                    contenedorElement.findElement(By.xpath("./input")).click();
                    ActionsUtil.cargandoFrameInterno(driver);

                    WebElement numeroCuotas = contenedorElement
                            .findElement(By.xpath(".//td[text()='NumeroConstans de cuotas']"));
                    WebElement selectElement = ActionsUtil.parent(numeroCuotas).findElement(By.xpath(".//select"));
                    Select select = new Select(selectElement);
                    select.selectByVisibleText("1");

                }
            }
        }

    }

    /**
     * 
     * Método responsable
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 9/01/2020
     * @version 1.0
     * @param numeroTelefono
     */
    public void selecionarNuevoPlan(String numeroTelefono) {
        boolean exito = Boolean.FALSE;
        if (CASO_POS_PRE.equals(tipoCambioPlan) || CASO_PRE_POS.equals(tipoCambioPlan)) {
            List<WebElement> listaPanelIzquierdo = driver
                    .findElements(By.xpath("//span[@class='bulkTreeItem bulkTreeItem MobileRoeTreeItem']"));
            for (WebElement itemPanelIzquierdo : listaPanelIzquierdo) {
                if (itemPanelIzquierdo.getText().contains(numeroTelefono)) {
                    itemPanelIzquierdo.click();
                    /* se coloca tiempo final porque pude durar mas que el normal */
                    ActionsUtil.cargandoFrameInterno(driver);
                    exito = Boolean.TRUE;
                    break;
                }
            }
        }
        if (!exito) {
            System.err.println("no se encontro numero telefono del plan actual");
        }

    }

    /**
     * 
     * Método responsable
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 9/01/2020
     * @version 1.0
     * @throws Exception 
     */
    public void validarCrearClienteCambioPlan() throws Exception {
        if (CASO_POS_PRE.equals(tipoCambioPlan) || CASO_PRE_POS.equals(tipoCambioPlan)) {
            validarCrearCliente("1");
        }

    }

    /**
     * 
     * Método responsable
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 9/01/2020
     * @version 1.0
     * @param plan
     */
    public void selecionarPlanCambio(String plan) {
        boolean exito = Boolean.FALSE;
        List<WebElement> filas = driver.findElements(By.xpath("//span[@class=\"list-changeto__item-text\"]"));
        for (WebElement combo : filas) {
            if (combo.getText().equals(plan)) {
                combo.click();
                ActionsUtil.cargandoFrameInterno(driver);
                exito = Boolean.TRUE;
                break;
            }
        }
        if (!exito) {
            System.err.println("no se encontro numero telefono del plan actual");
        }

    }

    public void clicLinkTextoEspera(String textoBoton, String espera) {
        // TODO mover a la clase ObjetosPlmLogin.java
        ActionsUtil.cargandoFrameInterno(driver);
        ActionsUtil.sleepSeconds(Integer.parseInt(espera));
        By link = By.xpath("//a[contains(text(), '" + textoBoton + "')]");
        ActionsUtil.esperarPoderClicBy(driver, link);
        ActionsUtil.clic(driver, link);
        System.out.println("Clic fin");

    }

    /**
     * 
     * Método responsable
     *
     * @uthor hataborda hataborda@heinsohn.com.co
     *
     * @date 14/01/2020
     * @version 1.0
     * @param descripcion
     */
    public void validarCargando() {
        ActionsUtil.cargandoFrameInterno(driver);

    }

    /**
     * 
     * Método responsable
     *
     * @uthor hataborda hataborda@heinsohn.com.co
     *
     * @date 14/01/2020
     * @version 1.0
     * @param descripcion
     */
    public void clicBotonFinalProcesoConfigurarContratoSinPlan() {
        String texto = properties.getProperty("boton.final.configurar.contrato");
        clicBotonFinalProceso(texto);
        /* Modo de gestión de contratos */
        ActionsUtil.botonOkConfirmar(driver);
        ActionsUtil.cargandoFrameInterno(driver);

    }

    /**
     * 
     * Método responsable
     *
     * @uthor hataborda hataborda@heinsohn.com.co
     *
     * @date 14/01/2020
     * @version 1.0
     * @param descripcion
     */
    public void ingresarInformacionModalTransferencia(String cuentaDestino, String ubicacion, String facturacion,
            String plan, String boton) throws Exception {
        // TODO mover a la clase ObjetosPlmLogin.java
        List<WebElement> internos = null;
        WebElement input = null;
        String CUENTA_DESTINO = "Cuenta del cliente destino *:";
        String UBICACION_DESTINO = "Ubicación del cliente destino *:";
        String CUENTA_FACTURACION = "Cuenta de facturación destino *:";
        String CONVERTIR = "Convertir a:";
        int contador = 0;
        internos = driver
                .findElements(By.xpath("//div[@class='ui-widget-overlay-under-wrapper']//tr[@class='roe-table-row']"));
        for (WebElement webElement : internos) {
            ActionsUtil.sleepSeconds(2);
            if (webElement.isDisplayed() && CUENTA_DESTINO.equals(webElement.getText())) {
                input = webElement.findElement(By.xpath(".//input[@type='text']"));
                if (input.isEnabled()) {
                    input.clear();
                    input.sendKeys(cuentaDestino);
                    buscarResultado();
                    contador++;
                }
            } else if (webElement.isDisplayed() && UBICACION_DESTINO.equals(webElement.getText())) {
                input = webElement.findElement(By.xpath(".//input[@type='text']"));
                if (input.isEnabled()) {
                    input.clear();
                    input.sendKeys(ubicacion);
                    buscarResultado();
                    contador++;
                }
            } else if (webElement.isDisplayed() && CUENTA_FACTURACION.equals(webElement.getText())) {
                input = webElement.findElement(By.xpath(".//input[@type='text']"));
                if (input.isEnabled()) {
                    input.clear();
                    input.sendKeys(facturacion);
                    buscarResultado();
                    contador++;
                }
            } else if (webElement.isDisplayed() && CONVERTIR.equals(webElement.getText())) {
                input = webElement.findElement(By.xpath(".//input[@type='text']"));
                if (input.isEnabled()) {
                    input.clear();
                    input.sendKeys(plan);
                    buscarResultado();
                    contador++;
                }
            }
            if (contador == 4) {
                break;
            }

        }
        clicBotonModal(boton);
        // el texto con l avadiación ya existe, se debe esperar
        ActionsUtil.sleepSeconds(3);
        // valida cargando
        ActionsUtil.cargandoFrameInterno(driver);
        // validar mensaje error
        By warning = By.className("warning_text");
        Boolean validacion = ActionsUtil.validarMensajeErrorModal(driver, warning);
        if (validacion != null && validacion) {
            throw new Exception("Existen observaciones en los seteo, no se puede completar el flujo");
        }

    }

    /**
     * 
     * Método responsable
     *
     * @uthor hataborda hataborda@heinsohn.com.co
     *
     * @date 14/01/2020
     * @version 1.0
     * @param descripcion
     */
    private void clicBotonModal(String texto) {
        texto = properties.getProperty(texto);
        // TODO mover a la clase ObjetosPlmLogin.java
        WebDriverWait wait = new WebDriverWait(driver, 150);
        // button[@class='ok-button ui-button ui-widget ui-state-default ui-corner-all
        // ui-button-text-only disabled']
        By boton = By.xpath(
                "//button[@class='ok-button ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only']");
        ActionsUtil.esperarPoderClicBy(driver, boton);
        ActionsUtil.clic(driver, boton);

    }

    /**
     * 
     * Método responsable
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 14/01/2020
     * @version 1.0
     * @param razon
     * @throws Exception
     */
    public void asignarRazonDesconexion(String razon) throws Exception {
        /* combo Razón de desconexión */
        WebElement webElmentSelect = driver.findElement(By.xpath(
                "//div[@class=\"ui-dialog-content ui-widget-content\"]//div[@class=\"information_box_popup\"]//select"));
        if (webElmentSelect.isDisplayed()) {

            List<WebElement> opciones = driver.findElements(By.xpath(
                    "//div[@class=\"ui-dialog-content ui-widget-content\"]//div[@class=\"information_box_popup\"]//select/option"));
            for (int i = 0; i < opciones.size(); i++) {
                if (razon.equals(opciones.get(i).getText())) {
                    Select select = new Select(webElmentSelect);
                    select.selectByVisibleText(razon);
                    ActionsUtil.cargandoFrameInterno(driver);
                    return;
                }
            }
            throw new Exception("El parámetro motivo ingresado es incorrecto " + razon);
        } else {
            throw new Exception("No se encontro el select Razón de desconexión");
        }
    }

    /**
     * 
     * Método responsable
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 14/01/2020
     * @version 1.0
     * @param descripcion
     * @throws Exception
     */
    public void asignarDescripcionRazon(String descripcion) throws Exception {
        WebElement textArea = driver.findElement(By.xpath(
                "//div[@class=\"ui-dialog-content ui-widget-content\"]//div[@class=\"information_box_popup\"]//textarea[@class=\"reason_description\"]"));
        if (textArea.isDisplayed()) {
            textArea.sendKeys(descripcion);
            ActionsUtil.cargandoFrameInterno(driver);
        } else {
            throw new Exception("No se encontro el textArea Descripción de razón");
        }
    }

    /**
     * 
     * Método responsable de localizar y hacer clic en el botón "Desconectar"
     *
     * @uthor hataborda <br>
     *        Jhon Edisson Hurtado <br>
     *        jehocampo@indracompany.com
     *
     * @date 14/01/2020
     * @version 1.0
     */
    public void clicBotonDesconectar() {

        ActionsUtil.cargandoFrameInterno(driver);
        By button = By.xpath("//div[@class='activ_box roe-widget']//a[.='Desconectar']");
        ActionsUtil.esperarVisibleBy(driver, button);
        ActionsUtil.clic(driver, button);
    }

    public void activarOro(String oro) {
        /* Se seleciona si es oro o no */
        if (oro.toLowerCase().equals("si")) {
            WebElement elemento = driver.findElement(By.xpath("//span[.='Cambio de NumeroConstans a ORO']"));
            elemento.click();
            ActionsUtil.cargandoFrameInterno(driver);
        } else if (oro.toLowerCase().equals("no")) {
            WebElement elemento = driver.findElement(By.xpath("//span[.='Cambio de Número']"));
            elemento.click();
            ActionsUtil.cargandoFrameInterno(driver);
        } else {
            // throw new Exception("Parametro ORO no valido: " + lineaArchivo[3]);
        }

    }

    public void validarCuotas(String valcuo) {
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        List<WebElement> filasTabla = driver.findElements(By.xpath(
                "//*[@class='offer_group_tbody']//*[@class='offering-editor-generic-gwidget']/*[@class='offer_tree_wrapper']/*[@class='offer_tree_table']/*[@class='roe-table-body offer_tree_tbody']//*[@class='expandable']//*[@class='parameters']/tbody/tr"));
        Boolean indicadorPagoCuotas = Boolean.FALSE, indicadorNumeroCuotas = Boolean.FALSE;
        for (WebElement WebElement : filasTabla) {
            if (WebElement.getText().contains("Pago en cuotas")) {
                WebElement inputPago = WebElement.findElement(By.xpath("./td/input"));
                inputPago.click();
                indicadorPagoCuotas = Boolean.TRUE;
                ActionsUtil.cargandoFrameInterno(driver);
            }
            if (WebElement.getText().contains("NumeroConstans de cuotas")) {
                WebElement selectNumeroCuotas = WebElement.findElement(By.xpath("./td/div/div/select "));
                Select select = new Select(selectNumeroCuotas);
                select.selectByVisibleText("1");
                indicadorNumeroCuotas = Boolean.TRUE;
                ActionsUtil.cargandoFrameInterno(driver);
            }

            if (indicadorPagoCuotas && indicadorNumeroCuotas) {
                break;
            }
        }
        if (!indicadorPagoCuotas) {
            // throw new ExcepcionTecnica("No se econtro el elemento: Pago en cuotas");

        }
        if (!indicadorNumeroCuotas) {
            // throw new ExcepcionTecnica("No se econtro el elemento: NumeroConstans de cuotas");

        }

    }

    private void buscarSegundoResultado(boolean hacerClic) {
        // TODO mover a la clase ObjetosPlmLogin.java
        ActionsUtil.sleepSeconds(1);
        By segundoResultado = By.xpath("//*[@id=\"nc_refsel_list_row_2\"]/*[@class=\"refsel_name\"]");
        WebElement resultadoBusqueda = driver.findElement(segundoResultado);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        ActionsUtil.espererarDesparecerCargando(driver);
        wait.until(ExpectedConditions.elementToBeClickable(resultadoBusqueda));
        if (hacerClic) {
            resultadoBusqueda.click();
        }
    }

    public void asignarNumeroNuevo(String nuevoNumero) {
        List<WebElement> filasTabla = driver
                .findElements(By.xpath("//*[@class='offer_group_tbody']//*[@class='offering-editor-generic-gwidget']"
                        + "/*[@class='offer_tree_wrapper']/*[@class='offer_tree_table']/*[@class='roe-table-body offer_tree_tbody']"
                        + "//*[@class='expandable']//*[@class='offering-editor-characteristics']//*[@class='characteristic-item force_enabled']"));
        for (WebElement WebElement : filasTabla) {
            if (WebElement.getText().contains("Número de Teléfono")) {
                WebElement inputNumeroNuevo = WebElement.findElement(By.xpath(
                        ".//*[@class='cell value']//*[@class='phoneCharacteristic']//*[@class='refsel_single']/input"));
                inputNumeroNuevo.clear();
                ActionsUtil.cargandoFrameInterno(driver);
                inputNumeroNuevo.sendKeys(nuevoNumero);
                ActionsUtil.cargandoFrameInterno(driver);

                /*
                 * WebElement selectNumeroCuotas =
                 * WebElement.findElement(By.xpath("//div[@id='nc_refsel_list_row_2']")); Select
                 * select = new Select(selectNumeroCuotas); select.selectByIndex(2);
                 */

                buscarSegundoResultado(Boolean.TRUE);
                ActionsUtil.cargandoFrameInterno(driver);
                // validarPrimerResultado(inputNumeroNuevo, nuevoNumero, "Número de Teléfono");
                // return;
            }

        }
        // throw new ExcepcionTecnica("No se econtro el elemento: Número de Teléfono");

    }


    /**
     * Método responsable de validar que el SLO agregado se activó para el número
     *
     * @author HATABORDA <br>
     *        Marybel Infante Ayala <br>
     *        minfante@indracompany.com
     *
     * @date 28/01/2020
     * @version 1.0
     * @throws Exception 
     */
    public void validarQueEsteActivoElSLO(String valorSLO) throws Exception {
        String delimiter = ",";
        String[] tipovalor;
        tipovalor = valorSLO.split(delimiter, 0);
        for (int i = 0; i < tipovalor.length; i++) {
            String delimiter2 = "-";
            String[] valor;
            valor = tipovalor[i].split(delimiter2);

            cambiarFramePrincipal();
            List<WebElement> items = driver.findElements(By.xpath("//div[@class='products-tree-item']"));
          
            if (!items.isEmpty()) {
                WebElement referencia = null;

                for (WebElement selenideElement : items) {

                    if (selenideElement.getText().contains(valor[1])) {
                        referencia = selenideElement.findElement(By.xpath("//a[contains(text(),'" + valor[1] + "')]"));
                        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
                        executor.executeScript("arguments[0].scrollIntoView(true);", referencia);
                        ActionsUtil.takeSnapShot(driver, "ValidarSloActivo.png");
                        return;

                    }
                }

                throw new Exception("El SLO " + valor[1] + " No está ");
            }

        }
    }
    
    
    /**
     * Método responsable de validar que el SLO agregado se desactivó para el número
     *
     * @author HATABORDA <br>
     *        Diego Armando Ramírez <br>
     *        daramirezar@indracompany.com
     *
     * @date 14/02/2020
     * @version 1.0
     * @throws Exception 
     */
    public void validarQueSeDesactivoElSLO(String valorSLO) throws Exception {
        String delimiter = ",";
        String[] tipovalor;
        tipovalor = valorSLO.split(delimiter, 0);
        for (int i = 0; i < tipovalor.length; i++) {
            String delimiter2 = "-";
            String[] valor;
            valor = tipovalor[i].split(delimiter2);

            cambiarFramePrincipal();
            List<WebElement> items = driver.findElements(By.xpath("//div[@class='products-tree-item']"));
          
            if (!items.isEmpty()) {
                //WebElement referencia = null;

                for (WebElement selenideElement : items) {

                    if (!selenideElement.getText().contains(valor[1])) {
                        //referencia = selenideElement.findElement(By.xpath("//a[contains(text(),'" + valor[1] + "')]"));
                        //JavascriptExecutor executor = (JavascriptExecutor) getDriver();
                        //executor.executeScript("arguments[0].scrollIntoView(true);", referencia);
                        ActionsUtil.takeSnapShot(driver, "ValidarDesactivacionSlo.png");
                        return;

                    }
                }

                throw new Exception("El SLO " + valor[1] + " No se desactivó ");
            }

        }
    }
          


    public void clicAgregarFacturaMiscelanea(String registro) {
    
        WebElement inputModelo = driver.findElement(By.xpath("//div[@class=\"search-filter__type\"]/input"));
        inputModelo.clear();
        inputModelo.sendKeys(registro);
        WebElement botonSiguiente = driver
                .findElement(By.xpath("//*[@id=\"Misceláneos\"]/div/div[4]/table/tbody/tr/td[4]/img"));

        String parar = null;
        do {
            parar = botonSiguiente.getAttribute("aria-disabled");
            List<WebElement> filas = driver.findElements(By.xpath("//*[@id=\"Misceláneos\"]/div/div"));
            for (WebElement selenideElement : filas) {
                if (selenideElement.isDisplayed() && selenideElement.getText().contains(registro)) {
                    List<WebElement> filas2 = selenideElement.findElements(By.xpath("./div/table/tbody/tr"));
                    for (WebElement selenideElement1 : filas2) {
                        if (selenideElement1.isDisplayed() && selenideElement1.getText().contains(registro)) {
                            (selenideElement1.findElement(By.xpath("./td/a[@class='roe-table-cell add']"))).click();
                            ActionsUtil.cargandoFrameInterno(driver);
                            return;
                        }
                    }
                }
            }
            botonSiguiente.click();
        } while ("false".equals(parar));      
    }

    public void darClicEnBotonModalFacturacionMiscelanea() throws Exception {
        WebElement parentSpan = driver.findElement(By.xpath("//../div[@class=\"ui-dialog-buttonset\"]//span[.='OK']"));
        if (parentSpan.isEnabled()) {
            parentSpan.click();
            ActionsUtil.cargandoFrameInterno(driver);
        } else {
            throw new Exception("No se encontro el boton OK (facturación miscelánea) del modal");
        }

    }

    public void asignarConceptoFacturaMiscelanea(String concepto) throws Exception {     
        WebElement webElmentSelect = driver.findElement(By.xpath(
                "//div[@class='roe-widget-area selected-hardware-offers']//"
                        + "div[@class='characteristicValues characteristic-invalid']//select"));
        if (webElmentSelect.isDisplayed()) {

            List<WebElement> opciones = driver
                    .findElements(By.xpath("//div[@class='roe-widget-area selected-hardware-offers']//"
                            + "div[@class='characteristicValues characteristic-invalid']//select/option"));
            for (int i = 0; i < opciones.size(); i++) {
                if (concepto.equals(opciones.get(i).getText())) {
                    Select select = new Select(webElmentSelect);
                    select.selectByVisibleText(concepto);
                    ActionsUtil.cargandoFrameInterno(driver);
                    return;
                }
            }
            throw new Exception("El concepto ingresado es incorrecto " + concepto);
        } else {
            throw new Exception("No se encontro el select Lista de Conceptos");
        }
    }
    
    
    public void darClicEnDivFacturaMiscelanea() {
        By xpath = By
                .xpath("(//div[@class='GEGUAMFDIE GEGUAMFDKE GEGUAMFDCF']//div[@class='GEGUAMFDJE GEGUAMFDBF'])[3]");
        ActionsUtil.esperarVisibleBy(driver, xpath);
        ActionsUtil.clic(driver, xpath);
        ActionsUtil.cargandoFrameInterno(driver);    
    }

    public void darClicEnFacturaMiscelanea(String subtipo_miscelanea) {

        ActionsUtil.cargandoFrameInterno(driver);
        ActionsUtil.switchToDefaultContent(driver);

        By link = By.xpath("(//div[@class='csrd-popup-value']//a)[1]");
        WebElement elementMiscelanea = driver.findElement(link);
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].scrollIntoView(true);", elementMiscelanea);
        ActionsUtil.abriNuevaPestanya(driver, elementMiscelanea.getAttribute("href"));

        ActionsUtil.cargandoFrameInterno(driver);
        ActionsUtil.sleepSeconds(NumeroConstans.DOS);

        link = By.xpath("//a[contains(text(), '"
                + (subtipo_miscelanea.substring(NumeroConstans.DIECISEIS, 
                        subtipo_miscelanea.length() - 1)) + "')]");
        elementMiscelanea = driver.findElement(link);
        executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].scrollIntoView(true);", elementMiscelanea);
        ActionsUtil.takeSnapShot(driver, "facturaMiscelanea.png");
        ActionsUtil.cerrarUltimaRegrearPrimeraPestanya(driver);
        
    }

    /**
     * 
     * Método responsable de validar el operador si no cambiarlo
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 22/01/2020
     * @version 1.0
     * @param usuario nombre usuario en sesión
     * @throws Exception
     */
    public void validarOperador(String usuario) throws Exception {
        if("true".equals(properties.getProperty("isAsignarUser"))) {
            String operador = properties.getProperty("nombre_operador");
            ActionsUtil.clicNavegarEscritorioUsuario(driver,usuario);
            sharedObjet("tablagestionordencliente");
            ActionsUtil.cargandoAtributosAsincronos(driver);
            WebElement valorCampo = ActionsUtil.obtenerElementoReferenciaTabla(driver, getObjetoToAction(),
                    "Primary Operator (Business Unit)");
            if (!operador.equals(valorCampo.getText())) {
                ActionsUtil.clic(driver, By.xpath("//a[@id='pcEdit']"));
                ActionsUtil.cargandoAtributosAsincronos(driver);
                valorCampo = ActionsUtil.obtenerElementoReferenciaTabla(driver, getObjetoToAction(),
                        "Primary Operator (Business Unit)");
                WebElement inputOperador = valorCampo.findElement(By.xpath(".//input[@type='text']"));
                inputOperador.clear();
                inputOperador.clear();
                inputOperador.sendKeys(operador);
                ActionsUtil.clic(driver, By.xpath("//div[@class='refsel_table']//div[@number='0']"));
                ActionsUtil.clic(driver, By.xpath("//a[@class='IconButton'][contains(text(),'Actualizar')]"));
            }
            ActionsUtil.cerrarUltimaRegrearPrimeraPestanya(driver);
        }
    }

   
     /** Método responsable
     *
     * @uthor hataborda <br>
     *        Harold Taborda<br>
     *        hataborda@heinsohn.com.co
     *
     * @date 21/01/2020
     * @version 1.0
     * @param nombreFlujo
     */
    public void guardarResultadoFlujo(String nombreFlujo, String idFLujoTabla) {
      //se guarda por BD
        ActionsUtil.setIdEjecucion(idFLujoTabla);
        operacionesBD.abrirConexionBD();
        List<Object> parametros= asignarParametrosResultadoFlujo(nombreFlujo, idFLujoTabla);
        operacionesBD.insertarRegistroAutomatizacion(parametros);
        listaIdsProcesados.add(idFLujoTabla);
        ReportesUtil.setListaIdsProcesados(listaIdsProcesados);
    }
    
    public void actualizarResultadoFlujo(String nombreFlujo, String idFLujoTabla) {
        //se guarda por BD
          operacionesBD.abrirConexionBD();
          List<Object> parametros= asignarParametrosResultadoFlujo(nombreFlujo, idFLujoTabla);
          parametros.add(idFLujoTabla);
          parametros.add(fechaSegundosMilisegundos);
          operacionesBD.actualizarRegistroAutomatizacion(parametros);
          actualizarRegistroProcesado(nombreFlujo, idFLujoTabla);
          
      }

    private void actualizarRegistroProcesado(String nombreFlujo, String idFLujoTabla) {
        operacionesBD.abrirConexionBD();
        String consulta=null;
        if(nombreFlujo.equals("SUSPENCION_LINEA")){
            consulta=ConsultasDinamicasConstans.UPDATE_OTC_T_SUSPENDER_REANUDAR_ID;
        }
        else if(nombreFlujo.equals("TRANSFERENCIA_BENEFICIARIO")){
            consulta=ConsultasDinamicasConstans.UPDATE_OTC_T_TRASNFERENCIA_BENEFICIARIO_ID;
        }
        else if(nombreFlujo.equals("ACTIVACION_SLO")){
            consulta=ConsultasDinamicasConstans.UPDATE_OTC_T_ACTIVACION_SLO_ID;
        }
        else if(nombreFlujo.equals("ALTAS_LINEAS")){
            consulta=ConsultasDinamicasConstans.UPDATE_OTC_T_ALTAS_LINEAS_ID;
        }
        else if(nombreFlujo.equals("BAJA_ABONADO")){
            consulta=ConsultasDinamicasConstans.UPDATE_OTC_T_BAJA;
        }
        else if(nombreFlujo.equals("CAMBIO_NUMERO")){
            consulta=ConsultasDinamicasConstans.UPDATE_OTC_T_CAMBIO_NUMERO_ID;
        }
        else if(nombreFlujo.equals("CAMBIO_PLAN")){
            consulta=ConsultasDinamicasConstans.UPDATE_OTC_T_CAMBIO_PLAN_ID;
        }
        else if(nombreFlujo.equals("CAMBIO_SIM")){
            consulta=ConsultasDinamicasConstans.UPDATE_OTC_T_CABMIO_SIM;
        }
        else if(nombreFlujo.equals("CREAR_CLIENTE")){
            consulta=ConsultasDinamicasConstans.UPDATE_OTC_T_CREAR_CLIENTE_ID;
        }
        else if(nombreFlujo.equals("FAC_EQUIPO_SIN_LINEA")){
            consulta=ConsultasDinamicasConstans.UPDATE_OTC_T_FAC_EQUIPO_SIN_LINEA_ID;
        }
        else if(nombreFlujo.equals("RENOVACION_EQUIPO")){
            consulta=ConsultasDinamicasConstans.UPDATE_OTC_T_RENOVACION_EQUIPO_ID;
        }
        else if(nombreFlujo.equals("FACTURAS_MISCELANEAS")){
            consulta=ConsultasDinamicasConstans.UPDATE_OTC_T_FACTURAS_MISCELANEAS_ID;
        }
        else if(nombreFlujo.equals("GENERACION_DISPUTAS_Y_NOTACREDITO")){
            consulta=ConsultasDinamicasConstans.UPDATE_OTC_T_DISPUTAS_Y_NOTACREDITO_ID;
        }
        else if(nombreFlujo.equals("CREAR_CLIENTE_EMPRESARIAL")){
            consulta=ConsultasDinamicasConstans.UPDATE_OTC_T_CLIENTE_EMPRESARIAL_ID;
        }
        operacionesBD.updateTablasEntrada(consulta,ProcesadoEnum.PROCESADO.name(), Long.parseLong(idFLujoTabla));
        
        
    }

    /**
     * 
     * Método responsable
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 22/01/2020
     * @version 1.0
     * @param nombreFlujo
     * @return
     */
    private List<Object> asignarParametrosResultadoFlujo(String nombreFlujo, String idFLujoTabla) {
        AsignarParametrosSalidaInDTO inDTO = new AsignarParametrosSalidaInDTO();
        inDTO.setUrl(url);
        inDTO.setEstadoOrden(estadoOrden);
        inDTO.setCuentaFacturacion(cuentaFacturacion);
        inDTO.setBdNumeroTelefono(bdNumeroTelefono);
        inDTO.setSimCardInicial(simCardInicial);// sim
        inDTO.setProcesadoCuando(procesadoCuando);
        inDTO.setNombreCliente(nombreCliente);
        inDTO.setNombreFlujo(nombreFlujo);
        inDTO.setParametrosJson(parametrosJson);
        inDTO.setBdPlan(bdPlan);// plan actual
        inDTO.setBdTipoEquipo(bdTipoEquipo);// tipo equipop
        inDTO.setNombreDelEquipoInterno(nombreDelEquipoInterno);
        inDTO.setNombreDelEquipoInterno(nombreDelEquipoInterno);
        inDTO.setImeiDelEquipoIterno(imeiDelEquipoIterno);
        inDTO.setSloActivados(sloActivados);
        inDTO.setSloDesactivados(sloDesactivados);
        inDTO.setPlanActual(planActual);
        inDTO.setPlanNuevo(planNuevo);
        inDTO.setSimcardVieja(simcardVieja);
        inDTO.setSimcardNueva(simcardNueva);
        inDTO.setNumeroAntiguo(numeroAntiguo);
        inDTO.setNumeroNuevo(numeroNuevo);
        inDTO.setNombreDelEquipoRenovacion(nombreDelEquipoRenovacion);
        inDTO.setNombreDelEquipoRenovacion(nombreDelEquipoRenovacion);
        inDTO.setImeiDelEquipoRenovacion(imeiDelEquipoRenovacion);
        inDTO.setNombreFlujo(nombreFlujo);// FLUJO
        inDTO.setIdFLujoTabla(idFLujoTabla);
        inDTO.setFechaSegundosMilisegundos(fechaSegundosMilisegundos);
        inDTO.setBdNumeroCuentaCliente(bdNumeroCuentaCliente);
        inDTO.setBdClientePospago(bdClientePospago);
        inDTO.setBdClientePospago(bdClientePospago);
        inDTO.setBdClientePospagoInAdvance(bdClientePospagoInAdvance);
        inDTO.setCategoria(bdCategoria);
        inDTO.setBdNumeroDocumento(bdNumeroDocumento);// "identificación"
        inDTO.setBdNombre(bdNombre);// "nombre completo"
        inDTO.setTipo_cuenta(tipo_cuenta);// "tipo cuenta"
        inDTO.setCiclo(ciclo);// "ciclo"
        inDTO.setFechaCreacion(fechaCreacion);
        inDTO.setPlataforma("Janus");        
        inDTO.setDisputaGenerada(disputaGenerada);
        inDTO.setFacturaDisputa(facturaDisputa);
        inDTO.setFacturaNotaCredito(facturaNotaCredito);
        inDTO.setUrlDisputa(urlDisputa);
        inDTO.setUrlNotaCredio(urlNotaCredio);
        inDTO.setUrlDocumentoNotaCredito(urlDocumentoNotaCredito);
        inDTO.setPlataforma("Janus");
        return ActionsUtil.asignarParametrosSalida(inDTO);
    }

    /**
     * 
     * Método responsable de obtener el nombre del cliente y la cuenta de
     * facturación si no se ha ingresado.
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 27/01/2020
     * @version 1.0
     */
    public void obtenerDatosNombreClienteCuentaFacturacionTelefono() {
        By nombreClienteBy = By.xpath("//div[@class='customer-header__cell customer-status-name']//a");
        ActionsUtil.esperarVisibleBy(driver, nombreClienteBy);
        nombreCliente = driver.findElement(nombreClienteBy).getText();

        if (cuentaFacturacion == null && bdNumeroTelefono!=null) {
            By cuentaFacturacionBy = By
                    .xpath("//div[@class='context-element csrd-status-active with-status with-stamp']"
                            + "//div[@class='gwt-Hyperlink']/a");
            String cuentaFacturacionParcial = driver.findElement(cuentaFacturacionBy).getText();
            cuentaFacturacion = cuentaFacturacionParcial.replace("Pospago:", "").replace("Prepago: ", "").trim();
        }

    }

    
    /**
     * Método responsable
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 21/01/2020
     * @version 1.0
     * @param mapaEntradaBD
     */
    public void asignoParametros(Map<String, String> mapaEntradaBD) {
        try {
            Gson gson = new Gson(); 
            parametrosJson=gson.toJson(mapaEntradaBD);
       }catch (Exception err){
          LOGGER.error("Se presento un error al castear el string a json", err);
       }
        
    }

    /**
     * 
     * Método responsable
     *
     * @uthor hataborda <br>
     *        Harold Taborda<br>
     *        hataborda@heinsohn.com.co
     *
     * @date 23/01/2020
     * @version 1.0
     * @param tabla
     * @param id
     * @param nombreFlujo 
     */
    public Map<String, String> consultarTablaEntradaFlujo(String tabla, String id, String nombreFlujo) {
        Map<String, String> mapaEntradaBD= new HashMap<>();
        operacionesBD.abrirConexionBD();
        String consulta=null;
        List<Object> params= new ArrayList<>();
        params.add(Long.parseLong(id));
        if(TablaEnum.OTC_T_TRANSFERENCIA_BENEFICIARIO.name().equals(tabla)){
            consulta=ConsultasDinamicasConstans.SELECT_TRASFERENCIA_BENEFICIARIO_ID;
        }
        else if(TablaEnum.OTC_T_ACTIVACION_SLO.name().equals(tabla)){
            consulta=ConsultasDinamicasConstans.SELECT_OTC_T_ACTIVACION_SLO_ID;
        }
        else if(TablaEnum.OTC_T_ALTAS_LINEAS.name().equals(tabla)){
            consulta=ConsultasDinamicasConstans.SELECT_OTC_T_ALTAS_LINEAS_ID;
        }
        else if(TablaEnum.OTC_T_BAJA.name().equals(tabla)){
            consulta=ConsultasDinamicasConstans.SELECT_OTC_T_BAJA_ID;
        }
        else if(TablaEnum.OTC_T_CABMIO_SIM.name().equals(tabla)){
            consulta=ConsultasDinamicasConstans.SELECT_OTC_T_CABMIO_SIM_ID;
        }
        else if(TablaEnum.OTC_T_CAMBIO_NUMERO.name().equals(tabla)){
            consulta=ConsultasDinamicasConstans.SELECT_OTC_T_CAMBIO_NUMERO_ID;
        }
        else if(TablaEnum.OTC_T_CAMBIO_PLAN.name().equals(tabla)){
            consulta=ConsultasDinamicasConstans.SELECT_OTC_T_CAMBIO_PLAN_ID;
        }
        else if(TablaEnum.OTC_T_CREAR_CLIENTE.name().equals(tabla)){
            consulta=ConsultasDinamicasConstans.SELECT_OTC_T_CREAR_CLIENTE_ID;
        }
        else if(TablaEnum.OTC_T_FAC_EQUIPO_SIN_LINEA.name().equals(tabla)){
            consulta=ConsultasDinamicasConstans.SELECT_OTC_T_FAC_EQUIPO_SIN_LINEA_ID;
        }
        else if(TablaEnum.OTC_T_RENOVACION_EQUIPO.name().equals(tabla)){
            consulta=ConsultasDinamicasConstans.SELECT_OTC_T_RENOVACION_EQUIPO_ID;
        }
        else if(TablaEnum.OTC_T_SUSPENDER_REANUDAR.name().equals(tabla)){
            consulta=ConsultasDinamicasConstans.SELECT_OTC_T_SUSPENDER_REANUDAR_ID;
        }
        else if(TablaEnum.OTC_T_FACTURAS_MISCELANEAS.name().equals(tabla)){
            consulta=ConsultasDinamicasConstans.SELECT_OTC_T_FACTURAS_MISCELANEAS_ID;
        }
        else if(TablaEnum.OTC_T_DISPUTAS_Y_NOTACREDITO.name().equals(tabla)){
            consulta=ConsultasDinamicasConstans.SELECT_OTC_T_DISPUTAS_Y_NOTACREDITO_ID;
        }
         else if(TablaEnum.OTC_T_CLIENTE_EMPRESARIAL.name().equals(tabla)){
            consulta=ConsultasDinamicasConstans.SELECT_OTC_T_CLIENTE_EMPRESARIAL_ID;
        }

        ResultSet rs = operacionesBD.ejecutarConsulta(consulta, params);
        try {
             if (rs != null && rs.next()) {
                 llenarMapa(rs,mapaEntradaBD);
                }
             else{
                 operacionesBD.cerrarStatement();
                 cerrarNavegador();
             }
        } catch (SQLException e) {
            LOGGER.error("Se presento un error al consultar las propiedades", e);
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (SQLException e) {
                    LOGGER.error("Error cerrando el resultSet al init", e);
                }
        }
        operacionesBD.cerrarStatement();
        String procesado=mapaEntradaBD.get("procesado");
        properties.setProperty("isProcesado", "false");
        if(procesado!=null && procesado.equalsIgnoreCase(ProcesadoEnum.PROCESADO.name())){
            LOGGER.info("Se finaliza el flujo ya que el registro ya fue procesado");
            properties.setProperty("isProcesado", "true");
            cerrarNavegador();
        }
        //se guarda el resultado
        guardarResultadoFlujo(nombreFlujo, mapaEntradaBD.get("id"));
        return mapaEntradaBD;
        
    }
   
   
   
    /**
     * 
     * Método responsable el mapa con el resultado de la consulta
     *
     * @uthor hataborda <br>
     *        Harold Tabord <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 27/01/2020
     * @version 1.0
     * @param rs
     * @param mapaEntradaBD
     */
    private void llenarMapa(ResultSet rs, Map<String, String> mapaEntradaBD) {
        try {
            
            ResultSetMetaData rsmd = rs.getMetaData();
            String nombreColumna=null;
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                nombreColumna=rsmd.getColumnName(i);
                mapaEntradaBD.put(nombreColumna, rs.getString(nombreColumna));
             }
            // asigno parametros
            asignoParametros(mapaEntradaBD);
        } catch (SQLException e) {
            LOGGER.error("Se presento un error al llenar el mapa", e);
        }

    }
    
    /**
     * Metodo encargado de finalizar el navegador
     */
    public void cerrarNavegador() {
      driver.quit();
    }

    /**
     * 
     * Método responsable tomar una foto del navegador
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 24/01/2020
     * @version 1.0
     * @param nombreFoto
     */
    public void tomarFoto(String nombreFoto) {
        ActionsUtil.takeSnapShot(driver, nombreFoto);
        
    }

    /**
     * 
     * Método responsable cambiar de pestania
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 27/01/2020
     * @version 1.0
     */
    public void cambiarPestania() {
        WebElement elemento=driver.findElement(By.xpath("//div[contains(text(),'Elementos de orden')]"));
        elemento.click();
        
    }
    public void cambiarPestaniaPrincipal() {
        WebElement elemento=driver.findElement(By.xpath("//div[contains(text(),'Resumen')]"));
        elemento.click();
        
    }

    /**
     * 
     * Método responsable sacar los campos finales de la pantalla resumen
     *
     * @uthor hataborda <br>
     *        Harold Tabord <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 27/01/2020
     * @version 1.0
     * @param idEjecucion
     * @param ejecucion
     */
    public void sacoCampsoPantallaFinal(String idEjecucion, String ejecucion) {
        ActionsUtil.setIdEjecucion(idEjecucion);
        ActionsUtil.setEjecucion(ejecucion);
        sharedObjet("tabladatosfinales");
        WebElement valorCampo;
        try {
            valorCampo = ActionsUtil.obtenerElementoReferenciaTabla(driver, getObjetoToAction(),
                    "Procesado cuando");
        procesadoCuando = valorCampo.getText();
        valorCampo = ActionsUtil.obtenerElementoReferenciaTabla(driver, getObjetoToAction(), "Estado de orden de venta");
        estadoOrden = valorCampo.getText();
        valorCampo = ActionsUtil.obtenerElementoReferenciaTabla(driver, getObjetoToAction(), "Tipo de proceso");
        tipoProceso = valorCampo.getText();
        WebElement a= driver.findElement(By.xpath("//div[contains(text(),'Confirmado por')]"));
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].scrollIntoView(true);", a);
        ActionsUtil.sleepSeconds(1);
        valorCampo = ActionsUtil.obtenerElementoReferenciaTabla(driver, getObjetoToAction(), "Confirmado por");
        confirmadoPor = valorCampo.getText();
        valorCampo = ActionsUtil.obtenerElementoReferenciaTabla(driver, getObjetoToAction(), "Bodega por defecto");
        bodegaDefecto = valorCampo.getText();
        valorCampo = ActionsUtil.obtenerElementoReferenciaTabla(driver, getObjetoToAction(), "Canal de distribución");
        canalDistribucion = valorCampo.getText();
        valorCampo = ActionsUtil.obtenerElementoReferenciaTabla(driver, getObjetoToAction(), "Fecha de envío");
        fechaEnvio = valorCampo.getText();
        operacionesBD.updateTablasSalida(procesadoCuando, estadoOrden,bodegaDefecto,canalDistribucion,fechaEnvio,confirmadoPor, idEjecucion,ejecucion);
        } catch (Exception e) {
            LOGGER.error("Se presento un error al actualizar la tabla de salida ", e);
        }
    
        
    }
    
    public void mostarOperador() {
        // TODO elimiar al final de la evidencias
        String usuario = properties.getProperty("nc.user");
        By usuarioBy = By.xpath("//li[@class='separate']/a[contains(text(),'" + usuario + "')]");
        ActionsUtil.clic(driver, usuarioBy);
    }
    
    /**
     * 
     * Método responsable abrir la pestaña donde se genera IMEI para equipos
     * Externos
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 29/01/2020
     * @version 1.0
     * @param nombreEquipo 
     * @return IMEI generado
     */
    private String obtenerImeiExterno() {
        cambiarFramePrincipal();
        String urlGeneradorImei = properties.getProperty("paht.generador.imei");
        ActionsUtil.abriNuevaPestanya(driver, urlGeneradorImei);
        
        By botonBy = By.xpath("//input[@type='button']");
        ActionsUtil.clic(driver, botonBy);
        By textBy = By.xpath("//input[@id='imei_num']");
        WebElement textElemento = driver.findElement(textBy);
        String imeiValor=textElemento.getAttribute("value");
        ActionsUtil.cerrarUltimaRegrearPrimeraPestanya(driver);
        cambiarFrameInterno();
        
        return imeiValor;
    }

    public void validarBodega(String bodega) throws Exception {
        if("true".equals(properties.getProperty("isAsignarBodeja"))) {
            ActionsUtil.clicNavegarEscritorioUsuario(driver,"nc.user");
            ActionsUtil.cargandoAtributosAsincronos(driver);
            //obtenerElementoATexto(driver,"Ubicación Actual");
            sharedObjet("tablainformaciongestionventas");
            WebElement elemento = ActionsUtil.obtenerElementoReferenciaTabla(driver,getObjetoToAction(),"Ubicación Actual");
            String bodegaActual =elemento.findElement(By.xpath(".//a[@class='commonReferenceLink']")).getText();
            bodega = properties.getProperty(bodega);
            // es necesario cambiar la bodega
            //
            if(!bodegaActual.equals(bodega)) {
                ActionsUtil.asignarUsuarioBodega(driver,bodega,"nc.user");
                ActionsUtil.clicNavegarEscritorioUsuario(driver,"nc.user");
                ActionsUtil.clic(driver, By.xpath("//a[@id='pcEdit']"));
                ActionsUtil.cargandoAtributosAsincronos(driver);
                elemento = ActionsUtil.obtenerElementoReferenciaTabla(driver,getObjetoToAction(),"Ubicación Actual");
                WebElement input =elemento.findElement(By.xpath(".//input[@type='text']"));
                input.clear();
                input.clear();
                input.sendKeys(bodega);
                ActionsUtil.buscarPrimerResultadoBodega(driver);
                ActionsUtil.clicElementoATexto(driver,"Actualizar");
            }
            ActionsUtil.cerrarUltimaRegrearPrimeraPestanya(driver);
        }
    }

    private String obtenerImeiInterno(String equipo) throws Exception {
        cambiarFramePrincipal();
        WebElement label = ActionsUtil.obtenerElementoSpanTexto(driver,"Bodegas");
        String linkBodegas=ActionsUtil.obteberLinkElemento(ActionsUtil.parent(label));
        ActionsUtil.abriNuevaPestanya(driver, linkBodegas);
        
        String nombreBodegaCentral = properties.getProperty("nombre.bodega.central");
        String nombreBodegaActual = properties.getProperty("nombre.bodega.actual");
        
        By modalInputBy=By.xpath("//div[@class='ui-dialog-content ui-widget-content']//input[@type='text']");

        ActionsUtil.filtrarIrBodega(driver, nombreBodegaActual);
        String imeiInterno=ActionsUtil.filtrarEquipoObtenerImeiInterno(driver,modalInputBy,equipo);
        
        if(imeiInterno==null) {
            driver.get(linkBodegas);
            ActionsUtil.filtrarIrBodega(driver, nombreBodegaCentral);
            imeiInterno=ActionsUtil.filtarEquipoObtenerImeiMoverEquipo(driver,modalInputBy,equipo,nombreBodegaActual);
        }
        
        ActionsUtil.cerrarUltimaRegrearPrimeraPestanya(driver);
        cambiarFrameInterno();
        return imeiInterno;
    }

    public void cargarArchivo(String ruta, String campo) {
        //TODO
        //FleBody_0
        //me cambio al frame
       //scro
        WebElement itemPor = driver.findElement(By.xpath("//button[@class='gwt-Button button_action_id_9144829123513615277_9141295712013349951 TableCtrl-button TableCtrl-button-icon NewAttachment']"));
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].scrollIntoView(true);", itemPor);
        ActionsUtil.sleepSeconds(1);
        itemPor.click();
        ActionsUtil.cargandoFrameInterno(driver);
        ActionsUtil.selectText(driver, By.xpath("//table[@class='nc-field-container nc-field-container-referencelist']//select[@class='gwt-ListBox nc-field-list-value']"), "Nombramiento");
        
        cambiarIframePrincipal();
        ActionsUtil.switchFrameFile(driver);
        
        WebElement elemen= driver.findElement(By.xpath("//input[@name='file0']"));
        elemen.sendKeys(ruta);
        cambiarIframePrincipal();
        By boton = By.xpath("//button[text()='Create']");
        ActionsUtil.esperarPoderClicBy(driver, boton);
        ActionsUtil.clic(driver, boton);
        ActionsUtil.sleepSeconds(5);
        System.out.println("Debug");
    }
    
    public void cargarArchivoElemento(String ruta, WebElement element) {
        ActionsUtil.sleepSeconds(3);
        element.sendKeys(ruta);
    }
    
    public void limiteCredito(String credito) {
        sharedObjet("recalcular");
        WebElement linkRecalcular=null;
        try {
            linkRecalcular = driver.findElement(getObjetoToAction());
        }catch (Exception e) {
        }
        if(linkRecalcular!=null) {
            cambiarFramePrincipal();
            ActionsUtil.abriNuevaPestanya(driver, urlVisionGeneral);
            ActionsUtil.clicPanelIzquiedoExpandir(driver);
            By optionEntradaOrden = By.xpath("//div[@title=' Resumen']");
            WebElement element=driver.findElement(optionEntradaOrden);
            JavascriptExecutor executor = (JavascriptExecutor) getDriver();
            executor.executeScript("arguments[0].click();", element);
            ActionsUtil.sleepSeconds(1);
            ActionsUtil.cilcElementoBottonTexto(driver, "Editar");
            WebElement input = driver
                    .findElement(By.xpath("//tr[@class='gwt-row nc-row-rolled-down gwt-last-row gwt-bottom-line']"
                            + "//td[@class='nc-table-ingrid-cell-edit-available nc-parctrl-cell']//input[@type='text']"));
            executor.executeScript("arguments[0].scrollIntoView(true);", input);
            input.clear();
            input.sendKeys(credito);
            ActionsUtil.clic(driver,
                    By.xpath("//div[@class='nc-toolbar-bottom']//div[@class='nc-toolbar-part nc-toolbar-part-left']"
                            + "//button[text()='Guardar']"));
            ActionsUtil.visibleElementoBottonTexto(driver, "Editar");
            ActionsUtil.cerrarUltimaRegrearPrimeraPestanya(driver);
            cambiarFrameInterno();
            ActionsUtil.sleepSeconds(2);
            linkRecalcular=driver.findElement(getObjetoToAction());
            linkRecalcular.click();
            ActionsUtil.sleepSeconds(2);
        }
            
        
    }

    public void generarDocumento() {
        if (!esPrepago) {
            ActionsUtil.clicElementoATexto(driver, "Generar documento");
            ActionsUtil.cargandoFrameInterno(driver);
            
            // se comentra mientras se alla una solucion al firmar
            /*
            ActionsUtil.clicElementoATexto(driver, "Firmar");
            ActionsUtil.cargandoFrameInterno(driver);
            WebElement elemento = driver
                    .findElement(By.xpath("//select[@class='gwt-ListBox nc-field-list-value nc-field-container']"));
            Select select = new Select(elemento);
            select.selectByVisibleText("Manual");
            WebElement input=driver.findElement(By.xpath("//input[@class='gwt-TextBox nc-field-text-input gwt-TextBox-readonly']"));
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].setAttribute('type', 'file')",input);
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].removeAttribute('readonly','readonly')",input);
            cargarArchivoElemento("D:\\Desktop\\eliminar\\Pdf_prueba.pdf",input);
            ActionsUtil.clic(driver, By.xpath("//button[@type='button'][contains(text(),'Guardar')]"));
            // update files
            ActionsUtil.espererarDesparecerBy(driver, By.xpath(
                    "//div[@class='ui-dialog ui-widget ui-widget-content ui-corner-all ui-front no-close JQPopup ui-draggable']"));
                    */
        }
    }

    /**
     * 
     * Método responsable de obtener el numero de telefono antiguo y nuevo
     * facturación si no se ha ingresado.
     *
     * @uthor hataborda <br>
     *        Guillermo Cifuentes <br>
     *        gcifuentesg@indracompany.com
     *
     * @date 28/01/2020
     * @version 1.0
     */
    public void obtenerDatosNumeroAntiguoNumeroNuevo() {
        By nuevoNumeroBy = By.xpath("//div[contains(@class,'GEGUAMFDLE')]//b[contains(text(),'[Modific')]");
        //ActionsUtil.esperarVisibleBy(driver, nuevoNumeroBy);
        List<WebElement> elemntos;
        
       elemntos =driver.findElements(nuevoNumeroBy);
       if(!elemntos.isEmpty()) {
           numeroNuevo = elemntos.get(0).getText();
           numeroNuevo = getNumeros(numeroNuevo);
       }
               

        }
    
    public static String getNumeros(String cadena) {
        char[] cadena_div = cadena.toCharArray();
        String numeroNuevo="";
        for (int i=0; i < cadena_div.length; i++){
            if (Character.isDigit(cadena_div[i])){
                numeroNuevo+=cadena_div[i];
            }
        }
        return numeroNuevo;
    }

 /**
  * 
  * Método responsable
  *
  * @uthor hataborda <br>
  *        Diana Carolina Farfán <br>
  *        dfarfan@indracompany.com
  *
  * @date 3/02/2020
  * @version 1.0
  */


   public void obtenerCategoriaCreacionCliente() {
        By categoriaBy = By.xpath("//div[@class='customer-header__cell customer-category']");
   
        //ActionsUtil.esperarVisibleBy(driver, nuevoNumeroBy);
        WebElement elemnto;

        elemnto =driver.findElement(categoriaBy);
        
        bdCategoria=  elemnto.getText();
 
        
        System.out.println(bdCategoria+"prueba ok");
   
       // numeroNuevo = getNumeros(numeroNuevo);
        
        }
     

    /**
     * 
     * Método responsable de identificar la simCard asociada al número.
     * Se guarda el valor en la variable simCardInicial
     *
     * @uthor hataborda <br>

     *        Jhon Edisson Hurtado <br>
     *        jehocampo@indracompany.com
     *
     * @date 3/02/2020
     * @version 1.0
     */


    public void identificarSimCardInicial() {
        WebElement itemPor = driver.findElement(By.xpath("//div[@class='gwt-HTML csrd-equipment-layout-value-name']/div"));
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].scrollIntoView(true);", itemPor);
        simCardInicial = ActionsUtil.identificarSimCardInicial(driver);
        
    }

    /**
     * 
     * Método responsable de identificar el nombre del equipo asociada al número.
     * Se guarda el valor en la variable nombreDelEquipoInterno
     *
     * @uthor hataborda <br>
     *        Jhon Edisson Hurtado <br>
     *        jehocampo@indracompany.com
     *
     * @date 3/02/2020
     * @version 1.0
     */
    public void identificarNombreDelEquipo() {
        WebElement itemPor = driver.findElement(By.xpath("//div[@class='gwt-HTML csrd-equipment-layout-value-name']/div"));
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].scrollIntoView(true);", itemPor);
        nombreDelEquipoInterno = ActionsUtil.identificarNombreDelEquipo(driver);
        
    }

    /**
     * 
     * Método responsable de identificar el imei del equipo
     * Se guarda el valor en la variable imeiDelEquipoIterno
     *
     * @uthor hataborda <br>
     *        Jhon Edisson Hurtado <br>
     *        jehocampo@indracompany.com
     *
     * @date 3/02/2020
     * @version 1.0
     */
    public void identificarImediDelEquipo() {
        WebElement itemPor = driver.findElement(By.xpath("//div[@class='gwt-HTML csrd-equipment-layout-value-name']/div"));
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].scrollIntoView(true);", itemPor);
        imeiDelEquipoIterno = ActionsUtil.identificarImeiDelEquipo(driver);
      

    }

    public void ingresarNumerCuentaFacturacionClienteResidencial(String cuenta) {
       String nombre = properties.getProperty("campo.numero.cuenta.facturacion");
       
       By campo = By.xpath(
               "//tr[@class=\"gwt-row nc-row-rolled-down\"]//div[contains(text(),'" + nombre + "')]/../..//input");
       ActionsUtil.setTextField(driver, campo, cuenta);
        
    }
    
    private String obtenerNumeroCuenta() throws Exception {
        WebElement elemento = ActionsUtil.obtenerElementoReferenciaTabla(driver,
                By.xpath("//tr[@class='gwt-row nc-row-rolled-down']/td/div"), "Nombre de cuenta de facturación");
        String cuenta=elemento.findElement(By.xpath(".//div[@class='nc-field-text-readonly']")).getText();
        return cuenta.split(":")[1].trim();
    }


    public void obtenerNumeroCuentaCliente() throws Exception {
        WebElement elemento = ActionsUtil.obtenerElementoReferenciaTabla(driver,
                By.xpath("//div[@class='title_parameter']/div"), "Número de cuenta del cliente");
        bdNumeroCuentaCliente=elemento.findElement(By.xpath(".//a")).getText();
        
    }

    public void esperarMinuto(String tiempo) {
        Integer tiemp=new Integer(tiempo);
        ActionsUtil.sleepMinutes(tiemp);
        
    }
    



    public void clicNombreCliente() {    

        By nombreClienteBy = By.xpath("//div[@class='customer-header__cell customer-status-name']//a");
        ActionsUtil.esperarVisibleBy(driver, nombreClienteBy);        
        ActionsUtil.abriNuevaPestanya(driver, (driver.findElement(nombreClienteBy).getAttribute("href")));
        ActionsUtil.cargandoFrameInterno(driver);
    }

    public void clicMenuCuentaDeFacturacion() {
        
//        ActionsUtil.sleepSeconds(1);
        driver.switchTo().defaultContent();  
        ActionsUtil.cargandoFrameInterno(driver);       
        ActionsUtil.ocultarMensajeNotificacionAutomatico(driver);

        By botonExpandir = By.xpath("//button[@class=\"gwt-Button left-panel-collapse-button collapsed\" "
                    + "or @class=\"left-panel-collapse-button collapsed gwt-Button\" "
                    + "or @class=\"collapsed left-panel-collapse-button gwt-Button\"]");
        ActionsUtil.esperarPoderClicBy(driver, botonExpandir);
        
        ActionsUtil.ocultarMensajeNotificacionAutomatico(driver);
        ActionsUtil.clic(driver, botonExpandir);
//        ActionsUtil.sleepSeconds(1);
        ActionsUtil.ocultarMensajeNotificacionAutomatico(driver);

        By optionCuentasFacturacion = By.xpath("//div[@title=\"Cuentas de facturación\"]");
        ActionsUtil.esperarPoderClicBy(driver, optionCuentasFacturacion);
        
        ActionsUtil.ocultarMensajeNotificacionAutomatico(driver);
        ActionsUtil.clic(driver, optionCuentasFacturacion);
        ActionsUtil.sleepSeconds(1);      
    }

    public void clicCuentaDeFacturacion(String cuenta) {
        
        ActionsUtil.sleepSeconds(1);
        ActionsUtil.switchToDefaultContent(driver);
        ActionsUtil.cargandoFrameInterno(driver);
        ActionsUtil.ocultarMensajeNotificacionAutomatico(driver);
        
        By byCuenta = By.xpath("((//*[contains(text(), 'Nueva cuenta de facturación de pospago')])"
                + "/ancestor::div[contains(@class, 'nc-widget-state nc-control-tablectrl-not-highlighted-odd-row "
                + "nc-control nc-control-baiptablectrl')])/table[@class='GF5QP-ADOM TableCtrl']//a[contains(text(), '" + cuenta + "')]");
        
        WebElement elemento = driver.findElement(byCuenta);
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].scrollIntoView(true);", elemento);        
        ActionsUtil.esperarPoderClicBy(driver, byCuenta);
        ActionsUtil.ocultarMensajeNotificacionAutomatico(driver);

        ActionsUtil.clic(driver, byCuenta);
    }

    public void clicTabDisputasyAjustes() {
        
        ActionsUtil.sleepSeconds(1);
        By byDisputasAjustes = By.xpath("//div[@class='gwt-TabBarItem']//div[contains(text(), 'Disputas y ajustes')]");
        ActionsUtil.esperarVisibleBy(driver, byDisputasAjustes);
        driver.findElement(byDisputasAjustes).click();    
        ActionsUtil.cargandoFrameInterno(driver);        
    }

    public void clicBotonModalNuevaDisputa(String boton) {
        
        ActionsUtil.cargandoFrameInterno(driver);        
        boton = properties.getProperty(boton);
        By byNombreBoton = By.xpath("//*[contains(@class,'nc-control-generaldisputeparameterspopup')]//button[text()='" + boton + "']");
        
        WebElement elemento = driver.findElement(byNombreBoton);
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].scrollIntoView(true);", elemento);
        
        ActionsUtil.esperarPoderClicBy(driver, byNombreBoton);
        ActionsUtil.clic(driver, byNombreBoton);        
        ActionsUtil.cargandoFrameInterno(driver);        
    }

    public void elegirNumeroFactura(String numeroFactura) {
                
        By byInputElemento = By.xpath("((//*[contains(@class,'nc-control-compositepopup')]//*[contains(text(),'Número de factura')])/..)/following-sibling::*//input[@class='refsel_input']");
        WebElement element = driver.findElement(byInputElemento);
        element.click();                              
        ActionsUtil.sleepSeconds(1);
        
        
        try {
            
            if((numeroFactura != null) && (!numeroFactura.trim().contentEquals(""))) {
                element.sendKeys(numeroFactura);
            }
            
            ActionsUtil.sleepSeconds(1);                
            By byElemento = By.xpath("//div[@id='nc_refsel_list_row_0']");        
            element = driver.findElement(byElemento);
            facturaDisputa = element.getText();
//            facturaDisputa += " value:"+element.getAttribute("value");
            
            element.click();  
            
//            By byInputElemento = By.xpath(
//                    "(((//*[contains(@class,'nc-control-compositepopup')]//*[contains(text(),'Número de factura')])/..)/following-sibling::*//input[@class='refsel_input'])/following-sibling::a");
//            facturaDisputa = (driver.findElement(byInputElemento)).getAttribute("value");
            
        } catch (Exception e) {
            System.out.println("La lista de facturas está vacía o no existe la factura que se intentó elegir");
        }
        ActionsUtil.cargandoFrameInterno(driver);
 
    }

    public void crearElementoEnDisputa(String boton) {
        boton = properties.getProperty(boton);
        // TODO mover a la clase ObjetosPlmLogin.java
        By byNombreBoton = By.xpath("((//span[text()='Agregar elemento de disputa']/..)/following-sibling::*)"
                + "//*[@class='nc-toolbars-bottom']//button[text()='" + boton + "']");
        ActionsUtil.esperarPoderClicBy(driver, byNombreBoton);
        ActionsUtil.clic(driver, byNombreBoton);        
        ActionsUtil.cargandoFrameInterno(driver);         
    }

    /**
     * 
     * Método responsable de obtener el campo tipoCuenta del flujo CreacionCliente
     *
     * @uthor hataborda <br>
     *        Diana Carolina Farfán <br>
     *        dfarfan@indracompany.com
     *
     * @date 4/02/2020
     * @version 1.0
     */

    public void obtenerTipoCuentaCreacionCliente() {
        By TipoCuetaBy = By.xpath("//div[@class='nc-drop-header-title']");
   
        //ActionsUtil.esperarVisibleBy(driver, nuevoNumeroBy);
        WebElement elemnto;

        elemnto =driver.findElement(TipoCuetaBy);
        
        tipo_cuenta=  elemnto.getText();
       // tipo_cuenta.split(":")[1].trim();
        
      tipo_cuenta = getCuenta(tipo_cuenta);
 
        
        System.out.println(tipo_cuenta +"pruebaok");
   
      
        
        }
    /**
     * 
     * Método responsable de tomar solo las letras del xpath detipo de cuenta
     *
     * @uthor hataborda <br>
     *        Diana Carolina Farfán <br>
     *        dfarfan@indracompany.com
     *
     * @date 4/02/2020
     * @version 1.0
     * @param cadena
     * @return
     */
    public static String getCuenta(String cadena) 
    {
        char[] cadena_div = cadena.toCharArray();
        String tipo_cuenta="";
        for (int i=0; i < cadena_div.length; i++){
        if (Character.isLetter(cadena_div[i])){
            tipo_cuenta+=cadena_div[i];
           }
        }
        return tipo_cuenta;
        }
    
    /**
     * 
     * Método responsable de obtener el campo nombre del flujo CreacionCliente
     *
     * @uthor hataborda <br>
     *        Diana Carolina Farfán <br>
     *        dfarfan@indracompany.com
     *
     * @date 4/02/2020
     * @version 1.0
     */
    public void obtenerNombreCreacionCliente() {
        By nombreBy = By.xpath("//div[@class='contact-name']");
   
        //ActionsUtil.esperarVisibleBy(driver, nuevoNumeroBy);
        WebElement elemnto;

        elemnto =driver.findElement(nombreBy);
        
        bdNombre=  elemnto.getText();
 
        
        System.out.println(bdNombre+"prueba ok");
   
       
        
        }
    
    /**
     * 
     * Método responsable de obtener el campo Ciclo del flujo CreacionCliente
     *
     * @uthor hataborda <br>
     *        Diana Carolina Farfán <br>
     *        dfarfan@indracompany.com
     *
     * @date 4/02/2020
     * @version 1.0
     */
    public void obtenerCicloCreacionCliente() {
        By cicloBy = By.xpath("//div[@class='csrd-next-bill-date']");
   
        //ActionsUtil.esperarVisibleBy(driver, nuevoNumeroBy);
        WebElement elemnto;

        elemnto =driver.findElement(cicloBy);
        
        ciclo=  elemnto.getText();
        ciclo=ciclo.substring(0, 2);
 
        
        System.out.println(ciclo+"prueba ok");
   
       
        
        }
    

    public void guardarBd(String idEjecucion, String ejecucion) {
        try {
            ActionsUtil.setIdEjecucion(idEjecucion);
            ActionsUtil.setEjecucion(ejecucion);
            operacionesBD.updateTablasSalidaCliente(bdCategoria,bdNombre,tipo_cuenta,ciclo,idEjecucion,ejecucion);
        }catch (Exception e) {
            LOGGER.error("Se presento un error al guardar la tabla de salida ", e);
        }
        
    }

    public void esperarCargaBotonProducto() {
        String texto = properties.getProperty("boton.productos");
        // TODO mover a la clase ObjetosPlmLogin.java
        By boton = By.xpath("//button[text()='" + texto + "']");
        ActionsUtil.esperarPoderClicBy(driver, boton);
    }
    
    public void darClicEnCerrarEnOrdenDeVentaEnviada() {
//      By link = By.xpath("//div[@class='box_bottom close']/a");
      //ActionsUtil.esperarPoderClicBy(driver, link);
//      ActionsUtil.clic(driver, link);
      
      ActionsUtil.sleepSeconds(1);
      cambiarFrameInterno();
      ActionsUtil.sleepSeconds(1);
      By link = By.xpath("//div[@class='box_bottom close']/a");
//      ActionsUtil.esperarPoderClicBy(driver, link);
      ActionsUtil.clic(driver, link);
      //ActionsUtil.sleepSeconds(1);
      //ActionsUtil.cargandoFrameInterno(driver);

  }

  public void darClicEnFacturaMiscelanea() {
      //ActionsUtil.sleepSeconds(6);
      ActionsUtil.cargandoFrameInterno(driver);
      cambiarFramePrincipal();
      ActionsUtil.cargandoFrameInterno(driver);
      By link = By.xpath("(//div[@class='csrd-popup-value']//a)[1]");

      //ActionsUtil.esperarVisibleBy(driver, link);
      ActionsUtil.esperarPoderClicBy(driver, link);
      ActionsUtil.clic(driver, link);                               
  }


    public void elegirValorGrupoInicial(String grupoInicial) {
        
//        ActionsUtil.cargandoFrameInterno(driver);
        ActionsUtil.sleepSeconds(1);
        By byInputGrupos = By.xpath(
                "((//*[text()='Grupo inicial']/..)/following-sibling::td[1])//input[contains(@class,'refsel_input')]");
        ActionsUtil.esperarVisibleBy(driver, byInputGrupos);
        driver.findElement(byInputGrupos).sendKeys(grupoInicial);
        ActionsUtil.sleepSeconds(2);
                                
        By byElemento = By.xpath("//div[@id='nc_refsel_list_row_0']");
        WebElement element = driver.findElement(byElemento);
        element.click();
        

        ActionsUtil.sleepSeconds(1);
        ActionsUtil.cargandoFrameInterno(driver);

    }

    public void elegirTipoDisputa(String tipoDisputa) {
        
//        ActionsUtil.cargandoFrameInterno(driver);
        ActionsUtil.sleepSeconds(1);
        By byInputGrupos = By.xpath("((//*[text()='Tipo de la Disputa']/..)/following-sibling::td[1])//input[contains(@class,'refsel_input')]");
        ActionsUtil.esperarVisibleBy(driver, byInputGrupos);
        driver.findElement(byInputGrupos).sendKeys(tipoDisputa);;
        ActionsUtil.sleepSeconds(2);
                        
        By byElemento = By.xpath("//div[@id='nc_refsel_list_row_0']");
        WebElement element = driver.findElement(byElemento);
        element.click();
        
        ActionsUtil.cargandoFrameInterno(driver);
       
    }

    public void ingresarDescripcionCR(String descripcionCR) {

        By byInputGrupos = By.xpath("((//*[text()='Descripción CR']/..)/following-sibling::td[1])//textarea");
        ActionsUtil.esperarVisibleBy(driver, byInputGrupos);
        WebElement element = driver.findElement(byInputGrupos);
        element.sendKeys(descripcionCR);
        ActionsUtil.sleepSeconds(1);

    }

    public void cambiarValorCampoMonto(String monto) {
        
        ActionsUtil.cargandoFrameInterno(driver);
        ActionsUtil.sleepSeconds(1);
        
        if((monto != null) && (!monto.trim().equals(""))) {
            By byInputMontoFacturado = By.xpath("(//*[@class='start-edit']/*[@class='cell-datagrid']//table//tr[1]/td/input)[2]");
            WebElement elementMontoFacturado = driver.findElement(byInputMontoFacturado);
            float valorMontoFacturado = Float.parseFloat((elementMontoFacturado.getAttribute("value")).replace(",", "."));                        
                    
            By byInputMonto = By.xpath("((//*[text()='Tipo de elemento']/../../..)//tbody[not(contains(@style, 'display: none'))]//tr/td/input)[4]");
            ActionsUtil.esperarVisibleBy(driver, byInputMonto);
            WebElement elementMonto = driver.findElement(byInputMonto);
            
            float valorMonto = Float.parseFloat(monto);            
            float valorMontoCalculado = 0;
            valorMontoCalculado = valorMontoFacturado - ((valorMontoFacturado * (valorMonto / 100)));
            System.out.println("VALORES: "+valorMonto +", " + valorMontoFacturado);
            
            int longitudCadena = (elementMonto.getAttribute("value")).length();
            elementMonto.click();
            
            for (int i = 0; i <= longitudCadena; i++) {
                elementMonto.sendKeys(Keys.ARROW_RIGHT);
            }
            for (int i = 0; i <= longitudCadena; i++) {
                elementMonto.sendKeys(Keys.BACK_SPACE);
            }

            elementMonto.sendKeys((String.valueOf(valorMontoCalculado).replace(".", ",")));
            ActionsUtil.sleepSeconds(1);
        }
        tomarFoto("facturaDisputa.png");

    }

    public void clicCajaSeleccionFactura() {

        ActionsUtil.cargandoFrameInterno(driver);
        ActionsUtil.sleepSeconds(2);        

        By bylistCheckbox = By.xpath("//label[text()='Cargo de producto']/../../../table//tr/td//input");
        //ActionsUtil.esperarVisibleBy(driver, bylistCheckbox);
        List<WebElement> elementos = driver.findElements(bylistCheckbox);
        int i = 0;
        
        try {
            if (!elementos.isEmpty()) {

                for (WebElement webElement : elementos) {
                    if(i == 0) {
                        webElement.click();
                        break;
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("No existen productos asociados a la factura elegida");
        }
        
        
//        element.click();         
        
        
        
//        bylistFacturas  = By.xpath("//label[text()='Cargo de producto']/../../../table//tr/td//span[@class='checkbox__icon']");
////      ActionsUtil.esperarVisibleBy(driver, bylistFacturas);
//      listFacturas = driver.findElements(bylistFacturas);
//      
//      try {
//          if (!listFacturas.isEmpty()) {
//              
////              byElemento = By.xpath(".//span[@class='checkbox__icon']");
////              element = driver.findElement(byElemento);
////              element.click();
//              for (WebElement webElement : listFacturas) {
//                  webElement.click();
//              }
////               element = listFacturas.get(0);
////              (element.findElement(By.xpath(".//span[@class='checkbox__icon']"))).click();
////              ActionsUtil.cargandoFrameInterno(driver);
//          }
//      }catch (Exception e) {
//          System.out.println("No se agregó la factura elegida a la tabla");
//      }
    }

    public void clicUltimaDisputaCreada() {
        
        ActionsUtil.cargandoFrameInterno(driver);
        ActionsUtil.sleepSeconds(3);
        
        By byTexto = By.xpath("(((((//*[text()='Nombre de la Disputa']/ancestor::thead)/following-sibling::tbody)[1])/tr)[1])//*[contains(text(),'Disputa')]");
        ActionsUtil.esperarVisibleBy(driver, byTexto);
        WebElement element = driver.findElement(byTexto);
        disputaGenerada = element.getText();        
        element.click();
        
        ActionsUtil.cargandoFrameInterno(driver);

//        String nombreDisputa = element.getText();             
//        element.click();          
//        By byText = By.xpath("//*[text()='Fecha de disputa']");
//        ActionsUtil.esperarVisibleBy(driver, byText);
//        WebElement element = driver.findElement(byText);
//        element.click();              
                
    }

    public void clicEnNombreDeUsuario(String nombreUsuario) {
        
        ActionsUtil.abriNuevaPestanya(driver, urlUsuario);
        ActionsUtil.cargandoFrameInterno(driver);                
    }

    public void seleccionarAjusteDeDisputa() {
        
        ActionsUtil.cargandoFrameInterno(driver);
        ActionsUtil.sleepSeconds(2);
        By byTexto = By.xpath("((//*[text()='Disputa Relacionada'])[1])/following-sibling::span");
        ActionsUtil.esperarVisibleBy(driver, byTexto);
        WebElement element = driver.findElement(byTexto);
        
//        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
//        executor.executeScript("arguments[0].scrollIntoView(true);", element);        
        element.click();                
        ActionsUtil.cargandoFrameInterno(driver);
        
        byTexto = By.xpath("//*[@class='gwt-TextBox nc-field-text-input']");
        ActionsUtil.esperarVisibleBy(driver, byTexto);
        element = driver.findElement(byTexto);
        element.clear();
        element.sendKeys(disputaGenerada);
        ActionsUtil.sleepSeconds(1);
        
        byTexto = By.xpath("(//label[contains(text(), '" + disputaGenerada + "')])/preceding-sibling::input");
        ActionsUtil.esperarVisibleBy(driver, byTexto);
        element = driver.findElement(byTexto);
        element.click();
        
        byTexto = By.xpath("//button[text()='Aplicar'] | //button[text()='Apply']");
        element = driver.findElement(byTexto);
        element.click();
        ActionsUtil.cargandoFrameInterno(driver);
        ActionsUtil.sleepSeconds(5);

        byTexto = By.xpath("((((//*[text()='Nombre del ajuste']/ancestor::thead)/..)//tbody/tr/td)[1])//input");
        ActionsUtil.esperarVisibleBy(driver, byTexto);
        element = driver.findElement(byTexto);
        element.click();
        ActionsUtil.cargandoFrameInterno(driver);
        
    }

    public void aprobarTareaDisputa() {
        
        ActionsUtil.sleepSeconds(1);
        ActionsUtil.cargandoFrameInterno(driver);
        By byTexto = By.xpath("((//td/a[text()='Proceso']/..)/following-sibling::td)/div");
        ActionsUtil.esperarVisibleBy(driver, byTexto);
        WebElement element = driver.findElement(byTexto);
        element.click();        
        ActionsUtil.cargandoFrameInterno(driver);
        
        byTexto = By.xpath("//*[@class='filteringContent']/input[@class='inputs']");
        ActionsUtil.esperarVisibleBy(driver, byTexto);
        element = driver.findElement(byTexto);
        element.sendKeys(disputaGenerada);
        ActionsUtil.cargandoFrameInterno(driver);
        
        //seleccionar checkbox
        byTexto = By.xpath("//div[@id='scroll']//input");
        ActionsUtil.esperarVisibleBy(driver, byTexto);
        element = driver.findElement(byTexto);
        element.click();
        ActionsUtil.cargandoFrameInterno(driver);
        
        byTexto = By.xpath("//a[text()='Aplicar'] | //a[text()='Apply']");
        element = driver.findElement(byTexto);
        element.click();
        ActionsUtil.cargandoFrameInterno(driver);
        ActionsUtil.sleepSeconds(5);

       // ActionsUtil.switchToDefaultContent(driver);
        byTexto = By.xpath("//*[@class='refImage commonReferenceLink']");
//        byTexto = By.xpath("((//table//span[contains(text(), 'Approval Task for Ejecutivos BO')])/..)[1]");
        ActionsUtil.esperarVisibleBy(driver, byTexto);
        element = driver.findElement(byTexto);
        element.click();
        ActionsUtil.cargandoFrameInterno(driver);  
        
//        
//      ActionsUtil.switchToDefaultContent(driver);
//    By byTexto = By.xpath("(((//table//*[contains(text(), '13. Disputa 17/02/2020 16:53:50')])/..)/preceding-sibling::td)/a");
//    ActionsUtil.esperarVisibleBy(driver, byTexto);
//    Welement.click();
//    ActionsUtil.cargandoFrameInterno(driver);
        
        
    }

    public void tomarEvidenciaDisputaCerrada() {
        ActionsUtil.cargandoFrameInterno(driver);
        WebElement itemPor = driver.findElement(By.xpath("(((((//*[text()='Nombre de la Disputa']/ancestor::thead)/following-sibling::tbody)[1])/tr)[1])//*[contains(text(),'Disputa')]"));
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].scrollIntoView(true);", itemPor);
        tomarFoto("DisputaCerrada.png");
        ActionsUtil.sleepSeconds(1);

    }

    public void escribirObservacionNotaCredito(String observacion, String boton) {
        ActionsUtil.cargandoFrameInterno(driver);       
        ActionsUtil.sleepSeconds(1);
        driver.findElement(By.xpath("//textarea[@class='nc-memo-field']")).sendKeys(observacion);
        ActionsUtil.sleepSeconds(1);
        clicBotonTexto(boton);        
        ActionsUtil.cargandoFrameInterno(driver);        
    }

    
    public void actualizarGruposDeUsuario(String gruposAgregados, String gruposEliminados, String usuario) throws Exception {
        
        //se guarda url asociada al nombre del usuario. Esta url será usada para abrir una nueva ventana en un paso más
        //adelante del proceso.
        By byNombreUsuario = By.xpath("//*[@class='top']//ul/li//*[contains(text(),'" + properties.getProperty(usuario) + "')]");        
        WebElement elementUsuario = driver.findElement(byNombreUsuario);
        urlUsuario = elementUsuario.getAttribute("href");
        
        //se abre el formulario de edición de grupos y se hace un desplazamiento vertical (scroll) hasta el elemento
        ActionsUtil.clicNavegarEscritorioUsuario(driver,usuario);
        sharedObjet("tablagestionordencliente");          
        ActionsUtil.clic(driver, By.xpath("//a[@id='pcEdit']"));
        ActionsUtil.cargandoAtributosAsincronos(driver);
        WebElement valorCampo = ActionsUtil.obtenerElementoReferenciaTabla(driver, getObjetoToAction(),
                "el Papel *");
        WebElement inputOperador = valorCampo
                .findElement(By.xpath("../..//textarea[@class='refsel_textarea']"));
        
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].scrollIntoView(true);", inputOperador);                
        ActionsUtil.sleepSeconds(1);

        //se obtiene los grupos actuales del textarea y se agregan a la lista listaTextArea
        List<String> listaTextArea = new ArrayList<>();
        String valorTextArea = inputOperador.getAttribute("value");                
        String[] arrayTextArea = valorTextArea.split("\\R");
        for (String string : arrayTextArea) {
            listaTextArea.add(string);
        }

        // se agregan los nuevos grupos a la lista listaTextArea
        String delimitador = ",";
        boolean editarGrupos = false;
        String[] listaGruposNuevos = gruposAgregados.split(delimitador, 0);
        for (String string : listaGruposNuevos) {
            
            if (!listaTextArea.contains(string)) {
                listaTextArea.add(string);
                if (!editarGrupos) {
                    editarGrupos = true;
                }
            }
        }

        // se eliminan los grupos correspondientes de la lista listaTextArea
        String[] listaGruposEliminados = gruposEliminados.split(delimitador, 0);
        for (String string : listaGruposEliminados) {
            if (listaTextArea.contains(string)) {
                listaTextArea.remove(string);
                if(!editarGrupos) {
                    editarGrupos = true;
                }
            }
        }

        //Si hay diferencias entre los grupos en pantalla y los grupos de base de datos, se actualizan los grupos
        //en pantalla
        if(editarGrupos) {
            // se limpia el textarea de grupos. Se deja sin grupos
            inputOperador.click();
            inputOperador.clear();

            By byPrimeraOpcion = By.xpath("//div[@id='nc_refsel_list_row_0']");
            WebElement primerElmento = null; 
            // se recorre la lista listaTextArea para agregar los nuevos grupos al textarea.
            for (String string : listaTextArea) {
                inputOperador.sendKeys(string);
                ActionsUtil.sleepSeconds(1);
                primerElmento = driver.findElement(byPrimeraOpcion);
                primerElmento.click();
                ActionsUtil.cargandoAtributosAsincronos(driver);  
//                ActionsUtil.sleepSeconds(1);
            }
        }        
        //ActionsUtil.cargandoAtributosAsincronos(driver);  
        
        //se cierra el formulario de edición
        ActionsUtil.clic(driver, By.xpath("//a[@class='IconButton'][contains(text(),'Actualizar')]"));
        
        //se muestran los grupos ya actualizados
        valorCampo = ActionsUtil.obtenerElementoReferenciaTabla(driver, getObjetoToAction(), "el Papel");
        executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].scrollIntoView(true);", valorCampo);
        ActionsUtil.sleepSeconds(1);
        //ActionsUtil.cerrarUltimaRegrearPrimeraPestanya(driver);   

    }

    public void ingresarObservacionDisputa(String observacion) {
        ActionsUtil.cargandoFrameInterno(driver);        
        driver.findElement(By.xpath("//textarea")).sendKeys(observacion);  
        ActionsUtil.sleepSeconds(1);
    }

    public void sacarUrlErrorPantallaFinal(String idEjecucion, String ejecucion) {
        sharedObjet("tabladatosfinales");
        WebElement valorCampo;
        try {
        if(estadoOrden==null || tipoProceso == null || estadoOrden.isEmpty() || tipoProceso.isEmpty() ||
                !estadoOrden.equalsIgnoreCase("Procesado") || !tipoProceso.equalsIgnoreCase("Completado")){
        valorCampo = ActionsUtil.obtenerElementoReferenciaTabla(driver, getObjetoToAction(), "Proceso");
        WebElement link = valorCampo.findElement(By.xpath(".//a"));
        String linkBodegas=ActionsUtil.obteberLinkElemento(link);
        driver.get(linkBodegas);
        ActionsUtil.sleepSeconds(1);
        esperarCargaPantalla();
        ActionsUtil.cargandoFrameInterno(driver);
        ActionsUtil.switchFrameObject(driver);
        clicLinkTexto("Errores");
        esperarCargaPantalla();
        ActionsUtil.cargandoFrameInterno(driver);
        ActionsUtil.takeSnapShot(driver, "ErrorPosterior.png");
        urlError=driver.getCurrentUrl();
        }
        else{
            urlError="N/A";
        }
      //se actualizan
        operacionesBD.updateTablasSalida(urlError, idEjecucion,ejecucion);
        
        } catch (Exception e) {
            LOGGER.error("Se presento un error al actualizar la tabla de salida ", e);
        }
    }

    /**
     * Método responsable de asignar el ciclo de facturación al crear la cuenta
     * de facturación pospago.
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 6/02/2020
     * @version 1.0
     * @param cicloFactura valor a asignar
     * @throws Exception
     */
    public void ingresarClicloFacturacion(final String cicloFactura)
            throws Exception {
        if (cicloFactura != null && !cicloFactura.isEmpty()) {
            asignarValorCuentaFacturacionPospago("Ciclo de factura",
                    cicloFactura);
            esperarCargaPantalla();
            
        }
    }

    /**
     * Método responsable de asignarle un valor al select asociado a un campo en
     * pantalla.
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 6/02/2020
     * @version 1.0
     * @param referencia campo en pantalla para la referencia.
     * @param valor      valor a asignar
     * @throws Exception
     */
    private void asignarValorCuentaFacturacionPospago(final String referencia,
            final String valor) throws Exception {
        sharedObjet("tablanuevacuentafacturacionpospago");
        WebElement elementoPadre = ActionsUtil.obtenerElementoReferenciaTabla(
                driver, getObjetoToAction(), referencia);
        // ActionsUtil.esperarPoderClicBy(driver, elemento);
        sharedObjet("selectgenericocliente");
        WebElement elemento = elementoPadre.findElement(getObjetoToAction());
        Select elementoSelect = new Select(elemento);
        elementoSelect.selectByVisibleText(valor);
    }

    /**
     * Método responsable de cambiar la renovación del equipo y asignar la cuota
     * inicial.
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 10/02/2020
     * @version 1.0
     * @param cuotas          numero de cuotas
     * @param valorRenovacion valor a asignar a la renovación del equipo
     * @param cuotaInicial    cuota inicial a asignar
     * @throws Exception
     */
    public void validarRenovacionDownpayment(final String cuotas,
            final String valorRenovacion, final String cuotaInicial,
            final String nombreEquipo) throws Exception {
        String equipoExterno = properties.getProperty("texto.equipo.externo");
        if (!equipoExterno.equals(nombreEquipo)) {
            if (valorRenovacion != null && !valorRenovacion.isEmpty()) {
                String textoRenovacion = properties.getProperty("texto.renovacion");
                WebElement elemento = obtenerElementoEspecificoTabla(
                        "tablaequipointerno", "selectgenerico",
                        textoRenovacion + "*");
                Select elementoSelect = new Select(elemento);
                elementoSelect.selectByVisibleText(valorRenovacion);
                ActionsUtil.cargandoFrameInterno(driver);
            }

            if (!esPrepago && !"0".equals(cuotas)) {
                String numeroCuotaHabilitar = properties
                        .getProperty("numero.cuota.habilita.inicial");
                if (cuotaInicial != null && !cuotaInicial.isEmpty()
                        && (numeroCuotaHabilitar.contains(cuotas))) {
                    sharedObjet("cuotainicial");
                    WebElement input = driver.findElement(getObjetoToAction());
                    input.sendKeys(cuotaInicial);
                    input.sendKeys(Keys.TAB);
                    ActionsUtil.cargandoFrameInterno(driver);
                }
            }
        }
        
    }

    /**
     * Método responsable de agrupar pasos para obtener un elemento especifico
     * en una tabla.
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 10/02/2020
     * @version 1.0
     * @param nombreByTabla    nombre dado al objeto en ObjetosPlmLogin que
     *                         representa la tabla
     * @param nombreByElemento nombre dado al objeto en ObjetosPlmLogin que
     *                         representa el elemento
     * @param referencia       nombre visto en pantalla para la referencia
     * @return WebElement
     * @throws Exception
     */
    public WebElement obtenerElementoEspecificoTabla(final String nombreByTabla,
            final String nombreByElemento, final String referencia)
            throws Exception {
        sharedObjet(nombreByTabla);
        WebElement filaColumna = ActionsUtil.obtenerElementoReferenciaTabla(
                driver, getObjetoToAction(), referencia);
        sharedObjet(nombreByElemento);
        return filaColumna.findElement(getObjetoToAction());
    }

    public void esperarSegundos(String string) {
       ActionsUtil.sleepSeconds(Integer.parseInt(string));
        
    }


    public void finalizarCreacionNotaCredito() {
        
        ActionsUtil.cargandoFrameInterno(driver);
        ActionsUtil.sleepSeconds(1);
        
        //url nota crédito
        By byTexto = By.xpath("((((//*[text()='Nombre del ajuste']/ancestor::thead)/..)//tbody/tr/td)[2])//a");
        urlNotaCredio = (driver.findElement(byTexto)).getAttribute("href");
        
        //url disputa relacionada
        byTexto = By.xpath("((((//*[text()='Nombre del ajuste']/ancestor::thead)/..)//tbody/tr/td)[9])//a");
        urlDisputa = (driver.findElement(byTexto)).getAttribute("href");

        //url documento generado
        byTexto = By.xpath("((((//*[text()='Nombre del ajuste']/ancestor::thead)/..)//tbody/tr/td)[12])//a");
        WebElement element = driver.findElement(byTexto);
        urlDocumentoNotaCredito = element.getAttribute("href");                
        
        tomarFoto("NotaCredito.png");

        
        //numero factura nota crédito
        ActionsUtil.esperarVisibleBy(driver, byTexto);
        element.click();
        ActionsUtil.cargandoFrameInterno(driver);
        
        byTexto = By.xpath("((//*[text()='Número de la factura'])/..)/following-sibling::td");
        facturaNotaCredito = driver.findElement(byTexto).getText();
        tomarFoto("FacturaNotaCredito.png");
    }

    public void guardarBdDisputasNotaCredito(String idEjecucion, String ejecucion) {
        try {
            ActionsUtil.setIdEjecucion(idEjecucion);
            ActionsUtil.setEjecucion(ejecucion);
            operacionesBD.updateTablasSalidaDisputasNotaCredito(facturaDisputa,disputaGenerada, facturaNotaCredito, 
                    urlDisputa, urlNotaCredio, urlDocumentoNotaCredito, idEjecucion,ejecucion);
        }catch (Exception e) {
            LOGGER.error("Se presento un error al guardar la tabla de salida ", e);
        }
    }
    /**
     * Método responsable de asignar los valores al formulario Nuevo Cliente
     * Empresarial.
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 13/02/2020
     * @version 1.0
     * @param rucEmpresarial RUC de la empresa
     * @param nombreLegal    nombre legal de la empresa
     * @param direccionLegal dirección legal de la empresa
     * @param numeroTelefono número de teléfono de la empresa
     * @throws Exception
     */
    public void ingresarInformacionClienteEmpresarial(
            final String rucEmpresarial, final String nombreLegal,
            final String direccionLegal, final String numeroTelefono)
            throws Exception {
        sharedObjet("tablaclienteempresarial");
        tablaMapa = ActionsUtil.obtenerMapaTabla(driver, getObjetoToAction());

        obtenerElementoAsiganarValorTablaMapa(tablaMapa, "Categoría de cliente",
                "inputtypetext", "Empresa");
        primerResultadoClienteEmpresarial();
        ActionsUtil.esperarCargar(driver);
        obtenerElementoAsiganarValorTablaMapa(tablaMapa, "RUC", "inputtypetext",
                rucEmpresarial);
        bdNumeroDocumento=rucEmpresarial;
        obtenerElementoAsiganarValorTablaMapa(tablaMapa, "Nombre legal",
                "inputtypetext", nombreLegal);
        obtenerElementoAsiganarValorTablaMapa(tablaMapa, "Dirección legal",
                "inputtypetext", direccionLegal);
        primerResultadoClienteEmpresarial();
        ActionsUtil.esperarCargar(driver);
        obtenerElementoAsiganarValorTablaMapa(tablaMapa, "Número de teléfono",
                "inputtypetext", numeroTelefono);
    }

    /**
     * Método responsable de asignar los valores al formulario Nuevo contacto
     * principal.
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 13/02/2020
     * @version 1.0
     * @param numeroDocumento Número de Documento
     * @param nombre          Nombre
     * @param apellido        Apellido
     * @param correo          Métodos de contacto correo
     * @throws Exception
     */
    public void ingresarInformacionContactoPrincipal(
            final String numeroDocumento, final String nombre,
            final String apellido, final String correo) throws Exception {
      
        sharedObjet("botonmascontactoprimario");
        driver.findElement(getObjetoToAction()).click();
        ActionsUtil.esperarVisibleBy(driver, By
                .xpath("//span[contains(text(),'Nuevo contacto principal')]"));
        sharedObjet("tablaclienteempresarialcontactoprincipal");
        tablaMapa = ActionsUtil.obtenerMapaTabla(driver, getObjetoToAction());

        obtenerElementoAsiganarValorTablaMapa(tablaMapa, "Tipo de documento",
                "selectgenericocliente", "Pasaporte");
        obtenerElementoAsiganarValorTablaMapa(tablaMapa, "Número de Documento",
                "inputtypetext", numeroDocumento);
        obtenerElementoAsiganarValorTablaMapa(tablaMapa, "Nombre",
                "inputtypetext", nombre);
        obtenerElementoAsiganarValorTablaMapa(tablaMapa, "Apellido",
                "inputtypetext", apellido);
        obtenerElementoAsiganarValorTablaMapa(tablaMapa, "Métodos de contacto",
                "inputtypetext", correo);
        obtenerElementoAsiganarValorTablaMapa(tablaMapa, "Métodos de contacto",
                "botonmastabla", null).click();
        ActionsUtil.sleepSeconds(2);
    }

    /**
     * Método responsable de asignar los valores al formulario Representante
     * legal.
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 14/02/2020
     * @version 1.0
     * @param tipoDocumentoRepresentativo     tipo de documento del represéntate
     *                                        legal
     * @param numeroDocumentoRepresentativo   numero de documento del
     *                                        represéntate legal
     * @param nombreCompletoRepresentativo    nombre completo del represéntate
     *                                        legal
     * @param correoElectronicoRepresentativo correo electrónico del
     *                                        represéntate legal
     * @throws Exception
     */
    public void ingresarInformacionRepresentanteLegal(
            final String tipoDocumentoRepresentativo,
            final String numeroDocumentoRepresentativo,
            final String nombreCompletoRepresentativo,
            final String correoElectronicoRepresentativo) throws Exception {
        if (tablaMapa == null) {
            sharedObjet("tablaclienteempresarial");
            tablaMapa = ActionsUtil.obtenerMapaTabla(driver,
                    getObjetoToAction());
        }
        obtenerElementoAsiganarValorTablaMapa(tablaMapa,
                "Tipo de documento representativo", "selectgenericocliente",
                tipoDocumentoRepresentativo);
        obtenerElementoAsiganarValorTablaMapa(tablaMapa,
                "Número de documento representativo", "inputtypetext",
                numeroDocumentoRepresentativo);
        obtenerElementoAsiganarValorTablaMapa(tablaMapa, "Nombre completo",
                "inputtypetext", nombreCompletoRepresentativo);
        WebElement elemento = obtenerElementoEspecificoTabla(
                "tablaclienteempresarialultimafila", "inputtypetext",
                "Correo electrónico");
        elemento.sendKeys(correoElectronicoRepresentativo);
    }

    /**
     * Método responsable de obtener el elemento de un mapa y completar su xpath
     * para obtener el elemento, para asignarle un valor dependiendo del tipo.
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 13/02/2020
     * @version 1.0
     * @param tablaMapaLocal mapa con los objetos de una tabla
     * @param referencia     llave del mapa que representa campo en pantalla
     * @param nombreObjeto   objeto para completar el xpath principal
     * @param valor          valor a asignar
     * @return elemento especifico
     * @throws Exception
     */
    private WebElement obtenerElementoAsiganarValorTablaMapa(
            final Map<String, WebElement> tablaMapaLocal,
            final String referencia, final String nombreObjeto,
            final String valor) throws Exception {
        WebElement fila = tablaMapaLocal.get(referencia);
        WebElement elementoFila = null;
        if (fila != null) {
            sharedObjet(nombreObjeto);
            elementoFila = fila.findElement(getObjetoToAction());
            if (valor != null) {
                if ("inputtypetext".equals(nombreObjeto)) {
                    elementoFila.sendKeys(valor);
                } else if ("selectgenericocliente".equals(nombreObjeto)) {
                    Select select = new Select(elementoFila);
                    select.selectByVisibleText(valor);
                } else if ("botonmastabla".equals(nombreObjeto)) {
                    elementoFila.click();
                }
            }
        } else {
            throw new Exception("No se encontro elemento: " + referencia);
        }
        return elementoFila;
    }

    /**
     * Método responsable de seleccionar el primer resultado de la búsqueda en
     * select/input en la tabla empresarial.
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 14/02/2020
     * @version 1.0
     */
    private void primerResultadoClienteEmpresarial() {
        sharedObjet("primerresultadoclienteempresarial");
        WebElement resultadoBusqueda = driver.findElement(getObjetoToAction());
        resultadoBusqueda.click();
    }
    
    public void clicLinkTexto(String texto, boolean fromProperties) {

        ActionsUtil.cargandoFrameInterno(driver);
        ActionsUtil.sleepSeconds(1);
        ActionsUtil.cambiarUltimaPestanya(driver);

        if(fromProperties) {
            texto = properties.getProperty(texto);
        }
        
        By link = By.xpath("//a[contains(text(), '" + texto + "')]");
//        WebElement element = driver.findElement(link);
//        element.click();
        
        ActionsUtil.esperarPoderClicBy(driver, link);
        ActionsUtil.clic(driver, link);     
        }
    
    public void clicBotonTexto(String texto, boolean fromProperties) {
        ActionsUtil.ocultarMensajeNotificacionAutomatico(driver);


        texto = properties.getProperty(texto);
        // TODO mover a la clase ObjetosPlmLogin.java
        
        By boton = By.xpath("//button[text()='" + texto + "']");
        
        WebElement elemento = driver.findElement(boton);
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].scrollIntoView(true);", elemento);
        
        ActionsUtil.esperarPoderClicBy(driver, boton);
        ActionsUtil.clic(driver, boton);
    }

    public void finalizarAprobacionDIsputa(String boton) {
        ActionsUtil.cargandoFrameInterno(driver);
        ActionsUtil.sleepSeconds(1);
        clicBotonTexto(boton, true);
        
        ActionsUtil.sleepSeconds(4);        
        ActionsUtil.cerrarUltimaRegrearPrimeraPestanya(driver);        
    }

    
    

    /**
     * Método responsable de ingresar a Resumen de la cuenta después de abrir el
     * panel izquierdo.
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 14/02/2020
     * @version 1.0
     */
    public void panelIzquierdoResumen() {
        abrirPanelIzquierdo();
        sharedObjet("panelizquierdoresumen");
        ActionsUtil.esperarPoderClicBy(driver, getObjetoToAction());
        ActionsUtil.clic(driver, getObjetoToAction());
        ActionsUtil.sleepSeconds(1);

    }

    /**
     * Método responsable de abrir el panel izquierdo.
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 14/02/2020
     * @version 1.0
     */
    private void abrirPanelIzquierdo() {
        ActionsUtil.sleepSeconds(1);
        driver.switchTo().defaultContent();
        ActionsUtil.sleepSeconds(1);
        ActionsUtil.ocultarMensajeNotificacionAutomatico(driver);
        sharedObjet("panelizquierdo");
        ActionsUtil.esperarPoderClicBy(driver, getObjetoToAction());
        ActionsUtil.clic(driver, getObjetoToAction());
        ActionsUtil.sleepSeconds(1);
    }

    /**
     * Método responsable de obtener los campos de salida del proceso cliente
     * empresarial e invocar la lógica para almacenar los en base de datos.
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 14/02/2020
     * @version 1.0
     * @param idEjecucion identificador del registro de la tabla de entrada
     * @param ejecucion   número de ejecución de la tabla de salida
     * @return 
     * @throws Exception 
     */
    public void obtenerCamposPantallaResumenClienteEmpresarial(
            final String idEjecucion, final String ejecucion) throws Exception {
        
        By boton = By.xpath("//button[text()='Editar']");
        ActionsUtil.esperarPoderClicBy(driver, boton);
        
        sharedObjet("tabladatosfinales");
        tablaMapa = ActionsUtil.obtenerMapaTabla(driver,
                getObjetoToAction());
        
        bdNumeroCuentaCliente = obtenerValorTablaMapa(tablaMapa,"Número de cuenta del cliente");
        bdCategoria = obtenerValorTablaMapa(tablaMapa,"Categoría de cliente");
        bdNombre = obtenerValorTablaMapa(tablaMapa,"Nombre legal");
        
        try {
            
            ActionsUtil.setIdEjecucion(idEjecucion);
            ActionsUtil.setEjecucion(ejecucion);
            operacionesBD.updateTablaSalidaClienteEmpresarial(
                    bdNumeroCuentaCliente, bdCategoria, bdNombre, idEjecucion,
                    ejecucion);
        } catch (Exception e) {
            LOGGER.error(
                    "Se presento un error al actualizar la tabla de salida ",
                    e);
        }
    }
        
    private String obtenerValorTablaMapa(final Map<String, WebElement> tablaMapa,
            final String referencia) throws Exception {
        WebElement elemento = tablaMapa.get(referencia);
        if (elemento != null) {
            return elemento.getText();
        } else {
            throw new Exception("No se encontro el elemento: "+referencia);
        }

    }

    public void esperarVerResumenCuentaClienteEmpresarial() {
        ActionsUtil.esperarVisibleBy(driver, By.xpath(
                "//div[@class='nc-drop-header-title']/div/label[contains(text(),'Cuenta de Cliente Empresarial')]"));
    }
    
    public void clicBotonTextoFocoEnObjeto(String texto) {
        
        ActionsUtil.ocultarMensajeNotificacionAutomatico(driver);
        texto = properties.getProperty(texto);        
        By boton = By.xpath("//button[text()='" + texto + "']");
        
        WebElement elemento = driver.findElement(boton);
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0].scrollIntoView(true);", elemento);
        
        ActionsUtil.esperarPoderClicBy(driver, boton);
        ActionsUtil.clic(driver, boton);
    }

}

