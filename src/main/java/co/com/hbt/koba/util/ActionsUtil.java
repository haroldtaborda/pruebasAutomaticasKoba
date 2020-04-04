package co.com.hbt.koba.util;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.hamcrest.CoreMatchers;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.hbt.koba.dto.AsignarParametrosSalidaInDTO;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;
import net.masterthought.cucumber.presentation.PresentationMode;
import net.masterthought.cucumber.sorting.SortingMethod;

public class ActionsUtil {

    private static HashMap<String, By> objetosPage = new HashMap<>();
    private static PropertiesLoader properties = PropertiesLoader.getInstance();
    private static final Logger LOGGER = LoggerFactory.getLogger(ActionsUtil.class);
    private static final long TIMEOUTS = initTimeOut();
    private static final long PAGE_LOAD_TIMEOUTS = (getProperty("webdriver.timeouts.pageLoadTimeout")) != null
            ? Long.parseLong(getProperty("webdriver.timeouts.pageLoadTimeout"))
            : 40000L;
    private static final long SET_SCRIPT_TIMEOUTS = (getProperty("webdriver.timeouts.setScriptTimeout")) != null
            ? Long.parseLong(getProperty("webdriver.timeouts.setScriptTimeout"))
            : 60000L;
    private static final long ELEMENT_TO_BE_CLICKABLE = 60L;
    private static final long MIL_MILISEGUNDOS = 1000L;
    private static final long MILISEGUNDOS_MINUTO = 60000L;
    private static final long DOS_MIL_MILISEGUNDOS = 2000L;
    private static final long CIEN_MILISEGUNDOS = 100L;
    private static final long DIEZ_MILISEGUNDOS = 10L;
    private static String baseURL;
    static String fechaSegundosMiligesundos;
    static OperacionesBD operacionesBD = new OperacionesBD();
    static String idEjecucion;
    static String ejecucion;
    
    
    private ActionsUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static long initTimeOut() {
        String timeOut = getProperty("webdriver.timeouts.implicitlywait");
        if (timeOut != null) {
            return Long.parseLong(timeOut);
        } else {
            return 2000L;
        }
    }

    public static By getObjeto(String nombreObjeto) {
        By retorno = objetosPage.get(nombreObjeto);
        String valueContains = "Objeto no mapeado";
        if (retorno == null) {
            valueContains = nombreObjeto;
        }
        assertThat("Objeto no mapeado", CoreMatchers.equalTo(valueContains));
        return retorno;
    }

    public static void objetosPut(String key, By value) {
        String validacionKey = "Objeto mapeado en objectsmap";
        By valueKey = objetosPage.get(key);
        if (valueKey != null) {
            validacionKey = "El objeto " + key + " ya fue mapeado: " + valueKey;
            objetosPage = new HashMap<>();
        }
        assertThat("Objeto mapeado en objectsmap", CoreMatchers.equalTo(validacionKey));
        objetosPage.put(key, value);
    }

    public static boolean objetosIsEmpty() {
        return objetosPage.isEmpty();
    }

    public static void setProperty(String property, String url) {
        properties.setProperty(property, url);
    }

    public static String getProperty(String property) {
        return properties.getProperty(property);
    }

    public static void highlightElement(WebDriver driver, By by) {
        driver.manage().timeouts().implicitlyWait(CIEN_MILISEGUNDOS, TimeUnit.MILLISECONDS);
        for (int second = 0; second <= 60; second++) {
            try {
                driver.findElement(by);
                if (driver.findElement(by).isDisplayed()) {
                    break;
                }
            } catch (Exception e) {
                //guardarExceptionControlada(e);
                sleepMiliseconds(1);
            }
            sleepMiliseconds(CIEN_MILISEGUNDOS);
        }
        modificarEstilo(driver, by, "border: 3px solid green;");
        driver.manage().timeouts().implicitlyWait(TIMEOUTS, TimeUnit.MILLISECONDS);
    }

    private static void modificarEstilo(WebDriver driver, By by, String modifyStyle) {
        WebElement element = driver.findElement(by);
        String originalStyle = element.getAttribute("style");
        String comandoExecute = "arguments[0].setAttribute('style', arguments[1]);";
        modifyStyle = getEstiloModificado(driver, by, modifyStyle);
        for (int i = 0; i < 2; i++) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(comandoExecute, element, modifyStyle);
            sleepMiliseconds(15);
            js.executeScript(comandoExecute, element, originalStyle);
            sleepMiliseconds(5);
        }
    }

    private static String getEstiloModificado(WebDriver driver, By by, String modifyStyle) {
        final String ATRIBUTO_ESTILO = "border:";
        WebElement element = driver.findElement(by);
        String originalStyle = element.getAttribute("style");
        if (originalStyle.contains(ATRIBUTO_ESTILO)) {
            int indexInicio = 7 + originalStyle.indexOf(ATRIBUTO_ESTILO);
            String aux = originalStyle.substring(indexInicio);
            int indexFin = indexInicio + aux.indexOf(';');
            String strInicial = originalStyle.substring(0, originalStyle.indexOf(ATRIBUTO_ESTILO));
            String strMedio = modifyStyle.replace(";", "");
            String strFinal = originalStyle.substring(indexFin);
            modifyStyle = strInicial + strMedio + strFinal;
        } else {
            modifyStyle = modifyStyle + " " + originalStyle;
        }
        return modifyStyle;
    }

    public static void goToWebSide(WebDriver driver, String text) {
        String currentURL = driver.getCurrentUrl();
        if (!text.isEmpty() && !text.equals(currentURL)) {
            driver.navigate().to(text);
        }
        driver.manage().timeouts().implicitlyWait(TIMEOUTS, TimeUnit.MILLISECONDS);
        driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUTS, TimeUnit.MILLISECONDS);
        driver.manage().timeouts().setScriptTimeout(SET_SCRIPT_TIMEOUTS, TimeUnit.MILLISECONDS);
    }

    public static void refresh(WebDriver driver) {
        driver.navigate().refresh();
    }

    public static boolean existsElement(WebDriver driver, By objeto) {
        boolean existe = false;
        try {
            driver.manage().timeouts().implicitlyWait(CIEN_MILISEGUNDOS, TimeUnit.MILLISECONDS);
            WebElement element = driver.findElement(objeto);
            if (element.isDisplayed()) {
                existe = true;
            }
        } catch (Exception e) {
            guardarExceptionControlada(e);
        } finally {
            driver.manage().timeouts().implicitlyWait(TIMEOUTS, TimeUnit.MILLISECONDS);
        }
        return existe;
    }

    public static boolean existsElement(WebElement element) {
        try {
            if (element.isDisplayed()) {
                return true;
            }

        } catch (Exception e) {
            guardarExceptionControlada(e);
            return false;
        }
        return false;
    }

    public static String textoMinusculasSinEspacios(String texto) {
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        for (int i = 0; i < original.length(); i++) {
            texto = texto.replace(original.charAt(i), ascii.charAt(i));
        }
        texto = texto.replaceAll("\t|\n| ", "");
        texto = texto.toLowerCase();
        return texto;
    }

    public static void setTextField(WebDriver driver, By by, String text) {
        if (!text.isEmpty()) {
            highlightElement(driver, by);
            WebElement element = driver.findElement(by);
            element.clear();
            element.sendKeys(text);
        }
    }

    public static String getText(WebDriver driver, By by) {
        highlightElement(driver, by);
        WebElement element = driver.findElement(by);
        return element.getText();
    }

    public static String getTextAttribute(WebDriver driver, By by) {
        highlightElement(driver, by);
        WebElement element = driver.findElement(by);
        return element.getAttribute("value");
    }

    public static String getAttribute(WebDriver driver, By by, String atributo) {
        highlightElement(driver, by);
        WebElement element = driver.findElement(by);
        String retorno = element.getAttribute(atributo);
        if (retorno == null) {
            retorno = "";
        }
        if (retorno.isEmpty()) {
            retorno = element.getCssValue(atributo);
        }
        return retorno;
    }

    public static void clicParent(SearchContext driver, By by) {
        WebElement element = driver.findElement(by);
        try {
            element.findElement(By.xpath("..")).click();
            element.findElement(By.xpath("../..")).click();
        } catch (Exception e) {
            LOGGER.error("Excepcion Clic .. o ../..:", e);
        }
    }

    public static void clic(WebDriver driver, By by) {
        highlightElement(driver, by);
        WebElement element = driver.findElement(by);
        element.click();
    }

    public static void clic(WebDriver driver) {
        WebElement currentElement = driver.switchTo().activeElement();
        currentElement.click();
    }

    public static boolean clicIfDisplayed(SearchContext driver, By by) {
        boolean wasCicked = false;
        List<WebElement> elementsToClicked = driver.findElements(by);
        for (WebElement element : elementsToClicked) {
            try {
                if (element.isDisplayed()) {
                    element.click();
                    wasCicked = true;
                    break;
                }
            } catch (Exception e) {
                sleepMiliseconds(1);
                guardarExceptionControlada(e);
            }
        }
        return wasCicked;
    }

    public static void selectText(WebDriver driver, By by, String option) {
        highlightElement(driver, by);
        WebElement element = driver.findElement(by);
        new Select(element).selectByVisibleText(option);
    }

    public static void selectIndex(WebDriver driver, By by, int indexOption) {
        highlightElement(driver, by);
        WebElement element = driver.findElement(by);
        new Select(element).selectByIndex(indexOption);
    }

    public static void selectContains(WebDriver driver, By by, String valueContains) {
        highlightElement(driver, by);
        WebElement element = driver.findElement(by);
        String valueComboBox = element.getText();
        assertThat(valueComboBox, CoreMatchers.containsString(valueContains));
        String[] values = valueComboBox.split("\n");
        int index = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i].contains(valueContains)) {
                index = i;
                break;
            }
        }

        Select select = new Select(element);
        select.selectByIndex(index);
        WebElement option = select.getFirstSelectedOption();
        String valorActual = option.getText();
        if (!valorActual.contains(valueContains)) {
            select = new Select(element);
            select.selectByIndex(index + 1);
            option = select.getFirstSelectedOption();
            valorActual = option.getText();
        }
        assertThat(valorActual, CoreMatchers.containsString(valueContains));
    }

    public static void selectValue(WebDriver driver, By by, String valueOption) {
        highlightElement(driver, by);
        WebElement element = driver.findElement(by);
        new Select(element).selectByValue(valueOption);
    }

    public static void containsText(WebDriver driver, By by, String valorEsperado) {
        String valorObtenido = getText(driver, by);
        valorEsperado = valorEsperado.replace("\\\"", "\"");
        assertThat(valorObtenido, CoreMatchers.containsString(valorEsperado));
    }

    public static void compareTextStart(WebDriver driver, By by, String textStart) {
        String valorObtenido = getText(driver, by);
        assertThat(valorObtenido, CoreMatchers.startsWith(textStart));
    }

    public static void compareTextNotEmpty(WebDriver driver, By by) {
        String valorObtenido = getText(driver, by);
        assertThat(valorObtenido, !valorObtenido.isEmpty());
    }

    public static void ejecutarScript(WebDriver driver, String script, By by) {
        WebElement element = driver.findElement(by);
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript(script, element);
    }

    public static void ejecutarScript(WebDriver driver, String script) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript(script);
    }

    public static void waitForClickable(WebDriver driver, By objeto) {
        WebDriverWait wait = new WebDriverWait(driver, (long) (TIMEOUTS / MIL_MILISEGUNDOS));
        wait.until(ExpectedConditions.elementToBeClickable(objeto));
    }

    public static void dragAndDrop(WebDriver driver, By byDraggable, By byDroppable) {
        highlightElement(driver, byDraggable);
        WebElement elementDrag = driver.findElement(byDraggable);
        highlightElement(driver, byDroppable);
        WebElement elementDrop = driver.findElement(byDroppable);
        Actions dragAndDrop = new Actions(driver);
        dragAndDrop.dragAndDrop(elementDrag, elementDrop).perform();
    }

    public static void dragAndDrop(WebDriver driver, By byElement, int x, int y) {
        highlightElement(driver, byElement);
        WebElement elementDrag = driver.findElement(byElement);
        Actions dragAndDrop = new Actions(driver);
        dragAndDrop.dragAndDropBy(elementDrag, x, y).perform();
    }

    public static void sleepSeconds(int sleep) {
        sleepMiliseconds(MIL_MILISEGUNDOS * sleep);
    }

    public static void sleepMiliseconds(long timeMiliSeconds) {
        try {
            Thread.sleep(timeMiliSeconds);
        } catch (Exception e) {
            LOGGER.error("Excepcion Sleep:", e);
        }
    }

    public static String getBaseUrl() {
        return baseURL;
    }

    public static void setBaseUrl(String base) {
        try {
            new URL(base);
            baseURL = base;
        } catch (Exception e) {
            guardarExceptionControlada(e);
            baseURL = null;
        }
    }

    public static String updateUrlWithBaseUrlIfDefined(final String startingUrl) {
        String baseUrl = baseURL;
        if ((baseUrl != null) && (!baseUrl.isEmpty())) {
            return replaceHost(startingUrl, baseUrl);
        } else {
            return startingUrl;
        }
    }

    private static String replaceHost(final String starting, final String base) {
        String updatedUrl = starting;
        try {
            URL startingUrl = new URL(starting);
            URL baseUrl = new URL(base);

            String startingHostComponent = hostComponentFrom(startingUrl.getProtocol(), startingUrl.getHost(),
                    startingUrl.getPort());
            String baseHostComponent = hostComponentFrom(baseUrl.getProtocol(), baseUrl.getHost(), baseUrl.getPort());
            updatedUrl = starting.replaceFirst(startingHostComponent, baseHostComponent);
        } catch (Exception e) {
            LOGGER.error("Failed to analyse default page URL: Starting URL: {}, Base URL: {}", starting, base);
            LOGGER.error("URL analysis failed with exception:", e);
        }
        return updatedUrl;
    }

    private static String hostComponentFrom(final String protocol, final String host, final int port) {
        StringBuilder hostComponent = new StringBuilder(protocol);
        hostComponent.append("://");
        hostComponent.append(host);
        if (port > 0) {
            hostComponent.append(":");
            hostComponent.append(port);
        }
        return hostComponent.toString();
    }

    public static void switchFrame(WebDriver driver, int indexTab) {
        driver.switchTo().frame(indexTab);
    }

    public static void switchFrame(WebDriver driver, String nameFrame) {
        driver.switchTo().frame(nameFrame);
    }

    public static void switchFrame(WebDriver driver, By objeto) {
        WebElement elemento = driver.findElement(objeto);
        driver.switchTo().frame(elemento);
    }

    public static void resolucion(WebDriver driver, String x, String y) {

        int ancho = Integer.parseInt(x);
        int alto = Integer.parseInt(y);
        Dimension d = new Dimension(ancho, alto);
        driver.manage().window().setSize(d);

    }

    public static void borrarCookies(WebDriver driver) {
        driver.manage().deleteAllCookies();
    }

    public static String getSubString(WebDriver driver, By by, int strInit, int strEnd) {
        return getText(driver, by).substring(strInit, strEnd);
    }
    public static File validarExisteEvidencia(String evidencia){
        String path = properties.getProperty("rutaArchivos") + evidencia;
        File destFile = new File(path);
        boolean valida= true;
        int cont=1;
        do{
        if(destFile.exists()){
            path=path.substring(0,path.length()-4)+cont+".png";
            destFile = new File(path);
            cont++;
        }
        else{
            valida=false;
        }
        }while(valida);
        return destFile;
    }

    public static void takeSnapShot(WebDriver webdriver, String fileWithoutPath) {
        try {
            TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
            File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
            File destFile=validarExisteEvidencia(getFechaSegundosMiligesundos()+"\\"+fileWithoutPath);
            destFile.getParentFile().mkdirs();
            FileUtils.copyFile(srcFile, destFile);
        } catch (Exception e) {
            LOGGER.error("Error tomado la foto para envio de pruebas: ", e);
        }

    }

    public static Reportable generarRepote() {

        String ruta = properties.getProperty("nc.reportes.ruta");
        File reportOutputDirectory = new File(ruta);
        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add(ruta + "/cukes.json");
        String buildNumber = properties.getProperty("nc.reportes.compilacion");
        String projectName = properties.getProperty("nc.reportes.nombre");
        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        configuration.setBuildNumber(buildNumber);
        configuration.setSortingMethod(SortingMethod.NATURAL);
        configuration.addPresentationModes(PresentationMode.EXPAND_ALL_STEPS);
        configuration.setTrendsStatsFile(new File("target/test-classes/demo-trends.json"));

        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        return reportBuilder.generateReports();
    }

    public static void esperarPoderClicBy(WebDriver driver, By xpaht) {
        WebDriverWait wait = new WebDriverWait(driver, ELEMENT_TO_BE_CLICKABLE);
        wait.until(ExpectedConditions.elementToBeClickable(xpaht));
    }

    public static void esperarPoderClicWebElement(WebDriver driver, WebElement elemento) {
        WebDriverWait wait = new WebDriverWait(driver, ELEMENT_TO_BE_CLICKABLE);
        wait.until(ExpectedConditions.elementToBeClickable(elemento));
    }

    public static void esperarVisibleWebElement(WebDriver driver, WebElement elemento) {
        WebDriverWait wait = new WebDriverWait(driver, ELEMENT_TO_BE_CLICKABLE);
        wait.until(ExpectedConditions.visibilityOf(elemento));
    }

    public static void esperarVisibleBy(WebDriver driver, By xpaht) {
        WebDriverWait wait = new WebDriverWait(driver, ELEMENT_TO_BE_CLICKABLE);
        wait.until(ExpectedConditions.visibilityOfElementLocated(xpaht));
    }

    public static void esperarVisibleListaBy(WebDriver driver, By xpaht) {
        WebDriverWait wait = new WebDriverWait(driver, ELEMENT_TO_BE_CLICKABLE);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpaht));
    }

    public static void esperarVisibleListaBySegundo(WebDriver driver, By xpaht, int segundos) {
        WebDriverWait wait = new WebDriverWait(driver, segundos);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(xpaht));
    }

    public static void espererarDesparecerCargando(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(MIL_MILISEGUNDOS, TimeUnit.MILLISECONDS);
        WebDriverWait wait = new WebDriverWait(driver, ELEMENT_TO_BE_CLICKABLE);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class=\"nc-loading-overlay\"]")));
        driver.manage().timeouts().implicitlyWait(TIMEOUTS, TimeUnit.MILLISECONDS);

    }

    public static void cargandoFrameInterno(WebDriver driver) {

        try {
            driver.manage().timeouts().implicitlyWait(DOS_MIL_MILISEGUNDOS, TimeUnit.MILLISECONDS);
            WebElement cargando = driver.findElement(By.xpath("//div[@class='gwt-PopupPanel loader']"));
            driver.manage().timeouts().implicitlyWait(TIMEOUTS, TimeUnit.MILLISECONDS);
            WebDriverWait wait = new WebDriverWait(driver, ELEMENT_TO_BE_CLICKABLE);
            wait.until(ExpectedConditions.invisibilityOf(cargando));
        } catch (Exception e) {
            //guardarExceptionControlada(e);
        }

    }

    public static void espererarDesparecerBy(WebDriver driver, By xpaht) {
        driver.manage().timeouts().implicitlyWait(MIL_MILISEGUNDOS, TimeUnit.MILLISECONDS);
        WebDriverWait wait = new WebDriverWait(driver, ELEMENT_TO_BE_CLICKABLE);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(xpaht));
        driver.manage().timeouts().implicitlyWait(TIMEOUTS, TimeUnit.MILLISECONDS);

    }

    public static void esperarInvisibleNoResultado(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(MIL_MILISEGUNDOS, TimeUnit.MILLISECONDS);
        WebDriverWait wait = new WebDriverWait(driver, ELEMENT_TO_BE_CLICKABLE);
        wait.until(ExpectedConditions
                .invisibilityOfElementLocated(By.xpath("//div[@class=\"nc-search-table-row-empty-content\"]")));
        driver.manage().timeouts().implicitlyWait(TIMEOUTS, TimeUnit.MILLISECONDS);

    }

    public static void invisibleListaBy(WebDriver driver, By cargando) {
        driver.manage().timeouts().implicitlyWait(MIL_MILISEGUNDOS, TimeUnit.MILLISECONDS);
        WebDriverWait wait = new WebDriverWait(driver, CIEN_MILISEGUNDOS);
        try {
            List<WebElement> elemento = driver.findElements(cargando);
            wait.until(ExpectedConditions.invisibilityOfAllElements(elemento));
        } catch (Exception e) {
            LOGGER.error("Se presento un error metodo invisibleListaBy ", e);
        } finally {
            driver.manage().timeouts().implicitlyWait(TIMEOUTS, TimeUnit.MILLISECONDS);
        }

    }

    public static void switchFrameRoe(WebDriver driver) {
        driver.switchTo().defaultContent();
        WebElement frameInterno = driver.findElement(By.xpath("//iframe[@name='iframe_roe']"));
        driver.switchTo().frame(frameInterno);

    }
    
    public static void switchFrameObject(WebDriver driver) {
        driver.switchTo().defaultContent();
        WebElement frameInterno = driver.findElement(By.xpath("//iframe[@name='iframe_object']"));
        driver.switchTo().frame(frameInterno);

    }
    
    public static void switchFrameFile(WebDriver driver) {
        driver.switchTo().defaultContent();
        WebElement frameInterno = driver.findElement(By.xpath("//iframe[@name='FileBody_0' or @id='FileBody_0']"));
        driver.switchTo().frame(frameInterno);

    }


    public static void esperarHabilitarServicioCaracteristica(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, ELEMENT_TO_BE_CLICKABLE);
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//a[@class='gwt-InlineHyperlink roe-pathList']")));

    }

    public static Boolean validarPlanPrepago(String plan) {
        Boolean esPrepago = Boolean.FALSE;
        String[] planesPrepago = properties.getProperty("texto.planes.prepago").split(",");
        for (String item_plan : planesPrepago) {
            if (plan.startsWith(item_plan)) {
                esPrepago = Boolean.TRUE;
                break;
            }
        }
        return esPrepago;
    }

    public static WebElement parent(SearchContext checkCuotas) {
        return checkCuotas.findElement(By.xpath(".."));
    }

    public static void botonOkConfirmar(WebDriver driver) {

        try {
            driver.manage().timeouts().implicitlyWait(DOS_MIL_MILISEGUNDOS, TimeUnit.MILLISECONDS);
            WebElement elemento = driver.findElement(By.xpath("//div[@class='ui-dialog-buttonset']//button[1]"));
            elemento.click();
            driver.manage().timeouts().implicitlyWait(TIMEOUTS, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            String message = "Exception raised while authenticating user: " + e.getMessage();
            LOGGER.warn(message);

        }

    }

    public static WebElement validacionInmediata(WebDriver driver, By botonEnviar) {
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.MILLISECONDS);
        WebElement elemento = null;
        try {
            elemento = driver.findElement(botonEnviar);
        } catch (Exception e) {
            //guardarExceptionControlada(e);
        } finally {
            driver.manage().timeouts().implicitlyWait(TIMEOUTS, TimeUnit.MILLISECONDS);
        }
        return elemento;
    }

    public static void switchToDefaultContent(WebDriver driver) {
        driver.switchTo().defaultContent();
    }

    public static boolean esEquipoExterno(String nombreEquipo) {
        return properties.getProperty("texto.equipo.externo").equals(nombreEquipo);
    }

    public static String planActual(WebDriver driver) {
        By planActual = By.xpath("//div[@class='products-tree']/div[@class='products-tree-item']"
                + "/div[@class='products-tree-link-wrapper']");
        esperarVisibleBy(driver, planActual);

        String textPlan = (driver.findElement(planActual)).getText();
        textPlan = (textPlan.substring(textPlan.indexOf(':') + 1, textPlan.length())).trim();
        return textPlan;

    }

    /**
     * 
     * Método responsable de identificar si el terminal actual es "Interno" o
     * "Externo". Devuelve true si es "Externo", false en otro caso.
     *
     * @date 9/01/2020
     * @version 1.0
     * @param terminalOrigen (String)
     * @return boolean
     */
    public static boolean esTerminalActualExterno(String terminalOrigen) {
        return properties.getProperty("texto.terminal.origen").equals(terminalOrigen);
    }

    /**
     * 
     * Método responsable de consultar la procedencia del terminal actual asociado
     * al numero telefonico. Valor a retornar: "Interno" o "Externo"
     *
     * @date 9/01/2020
     * @version 1.0
     * @param driver (WebDriver)
     * @return String
     */
    public static String consultarProcedenciaTermninalActual(WebDriver driver) {
        By terminalOrigen = By.xpath(
                "//form[@class='gwt-par']//table[@class='main-par-layout']//div[@class='gwt-HTML csrd-equipment-layout-value-terminal']");
        esperarVisibleBy(driver, terminalOrigen);

        return (driver.findElement(terminalOrigen)).getText();
    }

    public static Boolean validarMensajeErrorModal(SearchContext driver, By warning) {
        if (warning != null) {
            WebElement we = driver.findElement(warning);
            if (we != null) {
                String styleWe = we.getAttribute("style");

                // el control de warnign se mantiene oculto si la operacion es
                // exitosa
                if (styleWe != null && !properties.getProperty("style.none").equals(styleWe)
                        && !properties.getProperty("style.none2").equals(styleWe)) {
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

    static void guardarExceptionControlada(String mensaje, Exception e) {
        guardarLog(mensaje, e);
    }

    static void guardarExceptionControlada(Exception e) {
        guardarLog(null, e);
    }

    static void guardarExceptionControlada(String mensaje) {
        guardarLog(mensaje, null);
    }

    static void guardarLog(String mensaje, Exception e) {
        String mensajeFinal = "EXCEPTION_CONTROLADA: ";
        if (mensaje != null) {
            mensajeFinal = mensaje;
        }
        if (e == null) {
            LOGGER.info(mensajeFinal);
        } else {
            LOGGER.info(mensajeFinal, e);
        }

    }
    
    /**
     * Método que obtiene el valor de fechaSegundosMiligesundos
     * @return the fechaSegundosMiligesundos
     */
    public static String getFechaSegundosMiligesundos() {
        return fechaSegundosMiligesundos;
    }

    /**
     * Método que establece el valor de fechaSegundosMiligesundos
     * @param fechaSegundosMiligesundos el fechaSegundosMiligesundos a establecer
     */
    public static void setFechaSegundosMiligesundos(String fechaSegundosMiligesundos) {
        ActionsUtil.fechaSegundosMiligesundos = fechaSegundosMiligesundos;
    }

    /**
     * 
     * Método responsable de obtener un Elemento de una tabla por referencias del
     * campo que se encuentra al lado visualmente pero que esta antes en la lista
     * generada por el xpaht.
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 23/01/2020
     * @version 1.0
     * @param driver     la interfaz principal para usar en las pruebas, que
     *                   representa un navegador web.
     * @param tabla      xphat que representa la tabla.
     * @param referencia texto en pantalla que sirve como referencia al siguiente
     *                   campo.
     * @return
     * @throws Exception
     */
    public static WebElement obtenerElementoReferenciaTabla(SearchContext driver, By tabla, String referencia)
            throws Exception {
        boolean encontrado = Boolean.FALSE;
        WebElement elementoEncontrado = null;
        List<WebElement> celdas = driver.findElements(tabla);
        for (WebElement webElement : celdas) {
            if (encontrado) {
                elementoEncontrado = webElement;
                break;
            }
            if (referencia.equals(webElement.getText())) {
                encontrado = Boolean.TRUE;
            }
        }
        if (elementoEncontrado == null) {
            throw new Exception("No se encontro elemento: " + referencia);
        }
        return elementoEncontrado;
    }

    /**
     * 
     * Método responsable de esperar que carguen los atributos asíncronos
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 23/01/2020
     * @version 1.0
     * @param driver la interfaz principal para usar en las pruebas, que representa
     *               un navegador web.
     */
    public static void cargandoAtributosAsincronos(WebDriver driver) {

        try {
            driver.manage().timeouts().implicitlyWait(DOS_MIL_MILISEGUNDOS, TimeUnit.MILLISECONDS);
            List<WebElement> cargando = driver.findElements(By.xpath("//div[@class='iconParam']"));
            driver.manage().timeouts().implicitlyWait(TIMEOUTS, TimeUnit.MILLISECONDS);
            WebDriverWait wait = new WebDriverWait(driver, ELEMENT_TO_BE_CLICKABLE);
            for (WebElement webElement : cargando) {
                wait.until(ExpectedConditions.invisibilityOf(webElement));
            }
        } catch (Exception e) {
            guardarExceptionControlada(e);
        }

    }

    /**
     * 
     * Método responsable de invocar para que se cambia a la última pestaña del
     * navegador
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 23/01/2020
     * @version 1.0
     * @param driver la interfaz principal para usar en las pruebas, que representa
     *               un navegador web.
     */
    public static void cambiarUltimaPestanya(WebDriver driver) {
        ultimaPrimeraPestanya(driver, false);
    }

    /**
     * 
     * Método responsable de invocar para cerrar la ultima pestaña y pasar a la
     * primera.
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 23/01/2020
     * @version 1.0
     * @param driver la interfaz principal para usar en las pruebas, que representa
     *               un navegador web.
     */
    public static void cerrarUltimaRegrearPrimeraPestanya(WebDriver driver) {
        ultimaPrimeraPestanya(driver, true);
    }

    /**
     * 
     * Método responsable cambiar a la última pestaña del navegador o de cerrar la
     * última pestaña y pasar a la primera.
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 23/01/2020
     * @version 1.0
     * @param driver la interfaz principal para usar en las pruebas, que representa
     *               un navegador web.
     * @param cerrar indica si hay que cerrar la pestaña actual y pasar a la
     *               primera.
     */
    public static void ultimaPrimeraPestanya(WebDriver driver, boolean cerrar) {
        String[] pestanyas = driver.getWindowHandles().toArray(new String[0]);
        if (pestanyas.length > 1) {
            int ultimo = pestanyas.length - 1;
            String ultima = pestanyas[ultimo];
            driver.switchTo().window(ultima);
            if (cerrar) {
                driver.close();
                String primera = pestanyas[0];
                driver.switchTo().window(primera);
            }
        }
    }

    /**
     * 
     * Método responsable de verificar obtener los mensajes de existo o error del
     * icono de bandeja de mensajes
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 27/01/2020
     * @version 1.0
     * @param driver la interfaz principal para usar en las pruebas, que representa
     *               un navegador web.
     */
    public static boolean obtenerMensajeErrorBandeja(WebDriver driver, Boolean esProcesoFinal) {
        driver.switchTo().defaultContent();
        Boolean existeError=Boolean.FALSE;
        By bandejaMensajeBy = By.xpath("//li[contains(@class,'notification-menu')]/div");
        WebElement bandejaMensajeElemento = obtenerIconoBandejaMensajes(driver, bandejaMensajeBy);
        String bandejaMensajeClase = null;
        if (bandejaMensajeElemento != null) {
            bandejaMensajeClase = bandejaMensajeElemento.getAttribute("class");
        }
        WebElement mensajeElemento = null;
        if (bandejaMensajeClase != null && !bandejaMensajeClase.contains("no-messages")) {
            mensajeElemento = obtenerMensajes(driver);
            
            if (mensajeElemento == null) {
                
                try {
                    clic(driver, bandejaMensajeBy);
                    mensajeElemento = obtenerMensajes(driver);
                }catch (Exception e) {
                    System.out.println("no se encontró bandeja de errores");
                }
                
            }
        }
        if (mensajeElemento != null) {
            List<WebElement> mensajes = mensajeElemento.findElements(By.xpath("./div"));
            for (WebElement webElement : mensajes) {
                if (webElement.getAttribute("class").contains("nc-user-notification-error")) {
                    guardarExceptionControlada(webElement.getText());
                    takeSnapShot(driver, "ErrorProceso.png");
                    String urlActual=null;
                    urlActual=driver.getCurrentUrl();
                    //actualizo url antes error
                    operacionesBD.updateTablasSalidaAntesError(urlActual, getIdEjecucion(),getEjecucion());
                    detallesProcesoFinal(esProcesoFinal,driver,urlActual);
                    existeError=Boolean.TRUE;
                    break;
                }
            }
        }
        return existeError;
    }

    private static void detallesProcesoFinal(Boolean esProcesoFinal, WebDriver driver, String urlActual) {
        if(esProcesoFinal){
            String urlVerDetalles="";
            WebElement detalles=null;
            Boolean hayClick=Boolean.FALSE;
            try{
            //buscamos VerDetalles
            detalles=driver.findElement(By.xpath("//a[contains(text(),'Ver detalles')]"));
            detalles.click();
            hayClick=Boolean.TRUE;
            }
            catch (Exception e) {
               //buscamos Detalles
                try{
                detalles=driver.findElement(By.xpath("//a[contains(text(),'Detalles')]"));
                detalles.click();
                hayClick=Boolean.TRUE;
                }
                catch (Exception e1) {
                    try{
                        detalles=driver.findElement(By.xpath("//a[contains(text(),'details')]"));
                        detalles.click();
                        hayClick=Boolean.TRUE;
                        }
                        catch (Exception e2) {
                            hayClick=Boolean.FALSE;
                        }
                }
            }
            if(hayClick){
            cargandoFrameInterno(driver);
            takeSnapShot(driver, "ErrorProceso.png");
            urlVerDetalles=driver.getCurrentUrl();
            operacionesBD.updateTablasSalida(urlActual,urlVerDetalles, getIdEjecucion(),getEjecucion());
            }
            }
        
    }

    /**
     * 
     * Método responsable de validar la existencia del icono bandejas de mensajes.
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 27/01/2020
     * @version 1.0
     * @param driver           la interfaz principal para usar en las pruebas, que
     *                         representa un navegador web.
     * @param bandejaMensajeBy xphat del icono de bandejas de mensajes.
     * @return
     */
    private static WebElement obtenerIconoBandejaMensajes(WebDriver driver, By bandejaMensajeBy) {
        WebElement mensajeElemento = null;
        try {
            driver.manage().timeouts().implicitlyWait(DIEZ_MILISEGUNDOS, TimeUnit.MILLISECONDS);
            mensajeElemento = driver.findElement(bandejaMensajeBy);
        } catch (NoSuchElementException e) {
            // TODO revisar Ecepciones
        } finally {
            driver.manage().timeouts().implicitlyWait(TIMEOUTS, TimeUnit.MILLISECONDS);
        }
        return mensajeElemento;
    }

    /**
     * 
     * Método responsable de obtener los mensajes de error.
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 27/01/2020
     * @version 1.0
     * @param driver la interfaz principal para usar en las pruebas, que representa
     *               un navegador web.
     * @return
     */
    private static WebElement obtenerMensajes(WebDriver driver) {
        By mensajesBy = By.xpath("//div[@id='nc-message-tray']/div[@class='nc-message-tray-notifications']");
        WebElement mensajeElemento = null;
        try {
            driver.manage().timeouts().implicitlyWait(DIEZ_MILISEGUNDOS, TimeUnit.MILLISECONDS);
            mensajeElemento = driver.findElement(mensajesBy);
        } catch (Exception e) {
            // TODO revisar Ecepciones
        } finally {
            driver.manage().timeouts().implicitlyWait(TIMEOUTS, TimeUnit.MILLISECONDS);
        }
        return mensajeElemento;
    }

    /**
     * 
     * Método responsable abrir la url en una pestaña nueva y pasarse a ella.
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 27/01/2020
     * @version 1.0
     * @param driver la interfaz principal para usar en las pruebas, que representa
     *               un navegador web.
     * @param url    dirección a abrir.
     */
    public static void abriNuevaPestanya(WebDriver driver, String url) {
        ((JavascriptExecutor) driver).executeScript("window.open('about:blank','_blank');");
        cambiarUltimaPestanya(driver);
        driver.get(url);
    }
    

    public static void ocultarMensajeNotificacionAutomatico(WebDriver driver) {
        driver.switchTo().defaultContent();
        WebElement mensajeElemento = obtenerMensajesAutomatico(driver);
        By iconoBandejaMensajeBy = By.xpath("//li[contains(@class,'notification-menu')]/div");
        if (mensajeElemento != null) {
            clic(driver, iconoBandejaMensajeBy);
        }
    }
    
    private static WebElement obtenerMensajesAutomatico(WebDriver driver) {
        By mensajesBy = By.xpath(
                "//div[contains(@class,'ui-dialog ui-widget ui-widget-content ui-corner-all ui-front ui-draggable nc-user-notification')]"
                        + "/div[@class='ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix ui-draggable-handle']");
        WebElement mensajeElemento = null;
        try {
            driver.manage().timeouts().implicitlyWait(DIEZ_MILISEGUNDOS, TimeUnit.MILLISECONDS);
            mensajeElemento = driver.findElement(mensajesBy);
        } catch (Exception e) {
            // TODO revisar Ecepciones
        } finally {
            driver.manage().timeouts().implicitlyWait(TIMEOUTS, TimeUnit.MILLISECONDS);
        }
        return mensajeElemento;
    }   

    /**
     * 
     * Método responsable de identificar y retornar la simcard.
     *
     * @uthor hataborda <br>
     *        Jhon Edisson Hurtado <br>
     *        jehocampo@indracompany.com
     *
     * @date 3/02/2020
     * @version 1.0
     * @param driver
     * @return String
     */
    public static String identificarSimCardInicial(WebDriver driver) {        
        By simCardInicial = By.xpath("//div[@class='gwt-HTML csrd-equipment-layout-value-sim-iccid']/div");
        esperarVisibleBy(driver, simCardInicial);

        return (driver.findElement(simCardInicial)).getText();
    }
    
    public static String identificarNombreDelEquipo(WebDriver driver) {    
        By nombreEquipo = By.xpath("//div[@class='gwt-HTML csrd-equipment-layout-value-name']/div");
        esperarVisibleBy(driver, nombreEquipo);
        
        return (driver.findElement(nombreEquipo)).getText();
    }
    
    /**
     * 
     * Método responsable de capturar y retornar el imedi del equipo
     *
     * @uthor hataborda <br>
     *        Jhon Edisson Hurtado <br>
     *        jehocampo@indracompany.com
     *
     * @date 3/02/2020
     * @version 1.0
     * @param driver
     * @return String
     */
    public static String identificarImeiDelEquipo(WebDriver driver) {
        By imeiEquipo = By.xpath("//div[@class='gwt-HTML csrd-equipment-layout-value-imei']/div/a");

        WebElement webImei = null;
        try {
             webImei = driver.findElement(imeiEquipo);
        } catch (Exception e) {
            // TODO revisar Excepciones
        } finally {
            driver.manage().timeouts().implicitlyWait(TIMEOUTS, TimeUnit.MILLISECONDS);
        }
         if(webImei == null) {
             imeiEquipo = By.xpath("//div[@class='gwt-HTML csrd-equipment-layout-value-imei']/div");
             webImei = driver.findElement(imeiEquipo);
         }

        return webImei.getText();
    }
    
    /**
     * 
     * Método responsable de capturar y retornar el nombre del plan asociado al número
     *
     * @uthor hataborda <br>
     *        Jhon Edisson Hurtado <br>
     *        jehocampo@indracompany.com
     *
     * @date 3/02/2020
     * @version 1.0
     * @param driver
     * @return String
     */
    public static String nombrePlanActual(WebDriver driver) {
        By planActual = By.xpath("(//*[@class='gwt-Anchor'])[1]");
        esperarVisibleBy(driver, planActual);

        return (driver.findElement(planActual)).getText();
    }

    public static void sleepMinutes(Integer tiemp) {
        try {
            Thread.sleep(tiemp*MILISEGUNDOS_MINUTO);
        } catch (Exception e) {
            LOGGER.error("Excepcion Sleep:", e);
        }
        
    }

    public static void asignarUsuarioBodega(WebDriver driver, String bodega,String usuario) {
        WebElement label = obtenerElementoSpanTexto(driver,"Gestión de Operadores");
        WebElement link=parent(label);
        String ruta=obteberLinkElemento(link);
        driver.get(ruta);
        esperarCargar(driver);
        //clic(driver,By.xpath("//li[@class='operations-menu-last-item ui-menu-item']/a/span"));
        //esperarCargar(driver);
        clicElementoATexto(driver,"Tiendas");
        setTextField(driver,By.xpath("//input[@class='gwt-TextBox nc-field-text-input']"),bodega);
        cilcElementoBottonTexto(driver,"Búsqueda");
        invisibleListaBy(driver,By.xpath("//div[@class='nc-search-results-wrapper']//tr[2]"));
        clicElementoATexto(driver,bodega);
        clicElementoATexto(driver,"Usuarios de tienda");
        esperarCargar(driver);
        cilcElementoBottonTexto(driver,"Asignar usuario");
        esperarCargar(driver);
        usuario = properties.getProperty(usuario);
        setTextField(driver,By.xpath("//div[@class='nc-composite-layout-wrapper']//textarea"),usuario);
        clic(driver, By
                .xpath("//div[@class='refsel_table_holder ps-container ps-theme-default'][not(contains(@style,'none'))]"
                        + "//div[@class='refsel_name'][contains(text(),'" + usuario + "')]"));
        cilcElementoBottonTexto(driver, "Guardar");
        clicElementoSpanTexto(driver,"Confirmar");
        esperarCargar(driver);
    }
    
    
    public static String obteberLinkBy(WebDriver driver,By elementoBy) {
        WebElement elemento = driver.findElement(elementoBy);
        return obteberLinkElemento(elemento);
        
    }
    public static String obteberLinkElemento(WebElement elementoBy) {
        return elementoBy.getAttribute("href");
    }
    
    public static WebElement obtenerElementoSpanTexto(WebDriver driver, String texto) {
        By boton = By.xpath("//span[contains(text(),'" + texto + "')]");
        return driver.findElement(boton);

    }

    public static WebElement obtenerElementoATexto(WebDriver driver, String texto) {
        By boton = By.xpath("//a[contains(text(),'" + texto + "')]");
        return driver.findElement(boton);
    }
    
    public static WebElement obtenerElementoBottonTexto(WebDriver driver, String texto) {
        By boton = By.xpath("//button[text()='" + texto + "']");
        ActionsUtil.esperarPoderClicBy(driver, boton);
        return driver.findElement(boton);
    }
    
    public static void cilcElementoBottonTexto(WebDriver driver, String texto) {
        obtenerElementoBottonTexto(driver,texto).click();
    }
    
    public static void clicElementoSpanTexto(WebDriver driver, String texto) {
        obtenerElementoSpanTexto(driver,texto).click();
    }
    
    public static void clicElementoATexto(WebDriver driver, String texto) {
        obtenerElementoATexto(driver,texto).click();
    }
    

    public static void esperarCargar(WebDriver driver) {
        invisibleListaBy(driver,By.xpath("//*[contains(@class,'nc-loading')]"));
    }

    public static void clicNavegarEscritorioUsuario(WebDriver driver,String usuario) {
        usuario = properties.getProperty(usuario);
        By usuarioBy = By.xpath("//li[@class='separate']/a[contains(text(),'" + usuario + "')]");
        ActionsUtil.clic(driver, usuarioBy);
        ActionsUtil.cambiarUltimaPestanya(driver);
    }
    
    public static boolean esNecesarioCambioBodegaProceso(WebDriver driver) {
        boolean mismaBodega=Boolean.FALSE;
        cargandoFrameInterno(driver);
        WebElement mensajeElemento = null;
        try {
            driver.manage().timeouts().implicitlyWait(DIEZ_MILISEGUNDOS, TimeUnit.MILLISECONDS);
            mensajeElemento = driver.findElement(By.xpath("//span[@class='tfn-warehouse-header']"));
            if(mensajeElemento.getText().contains("Bodega")) {
                mismaBodega=Boolean.TRUE;
                mensajeElemento = driver.findElement(By.xpath("//span[@class='tfn-warehouse-header']"));
                String bodegaDeseada=properties.getProperty("nombre.bodega.actual");
                if(bodegaDeseada.equals(mensajeElemento.getText())) {
                    mismaBodega=Boolean.FALSE;
                }
            }
           
        } catch (Exception e) {
            // TODO revisar Ecepciones
        } finally {
            driver.manage().timeouts().implicitlyWait(TIMEOUTS, TimeUnit.MILLISECONDS);
        }
        return mismaBodega;
    }

    public static void filtrarIrBodega(WebDriver driver,String nombreBodega) {
        clic(driver, By.xpath("//div[@class='tab-menu-holder'][1] | //div[@id='t9141747563313731920_0____$1']"));
        sleepSeconds(1);
        setTextField(driver,
                By.xpath("//div[@class='ui-tabs-panel ui-widget-content ui-corner-bottom']"
                        + "[@style='display: block;']//div[@id='typeinFilter']//input[@type='text']"),
                nombreBodega);
        sleepSeconds(1);
        WebElement elemento=driver.findElement(By.xpath("//a[contains(text(),'Aplicar')]"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", elemento);
        esperarCargar(driver);
        sleepSeconds(1);
        clicElementoSpanTexto(driver, nombreBodega);
        sleepSeconds(1);
        esperarCargar(driver);
        
    }

    public static String filtrarEquipoObtenerImeiInterno(WebDriver driver,By modalInputBy,String nombreEquipo) throws Exception {
        filtrarEquipo(driver,modalInputBy,nombreEquipo);
        return obtenerImeiInternoBodega(driver);
        
    }
    
    public static String filtarEquipoObtenerImeiMoverEquipo(WebDriver driver, By modalInputBy, String nombreEquipo,
            String nombreBodegaActual) throws Exception {
        filtrarEquipo(driver, modalInputBy, nombreEquipo);
        String imeiInterno;

        imeiInterno = obtenerImeiInternoBodega(driver);
        if (imeiInterno == null) {
            throw new Exception("No hay equipo en la bodega");
        }
        clic(driver, By.xpath("//tr[contains(@class,'GDEEHVHDAN')]//td[1]//input"));
        cilcElementoBottonTexto(driver, "Mover");
        WebElement element = driver.findElement(modalInputBy);
        element.clear();
        element.clear();
        sleepSeconds(3);
        element.sendKeys(nombreBodegaActual);
        sleepSeconds(3);
        ActionsUtil.buscarPrimerResultadoBodega(driver);
        clic(driver,
                By.xpath("//div[@class='ui-dialog-content ui-widget-content']//button[text()='Mover']"));
        esperarCargar(driver);
        boolean existeError = false;
        try {
            // si no se cerro el modal es porque hay un error
            clic(driver,
                    By.xpath("//div[@class='ui-dialog-content ui-widget-content']//button[text()='Cancelar']"));
            existeError =obtenerMensajeErrorBandeja(driver, Boolean.FALSE);
        }catch (Exception e) {
            // TODO: handle exception
        }
        if(existeError) {
            throw new Exception("No se es posible mover el equipo");
        }
        return imeiInterno;

    }
    
    private static String obtenerImeiInternoBodega(WebDriver driver) throws Exception {
        String imeiInterno=null;
        try {
            imeiInterno=driver.findElement(By.xpath("//tr[contains(@class,'GDEEHVHDAN')]//td[2]//a")).getText();
        }catch (Exception e) {
          imeiInterno=null;
        }
        if(imeiInterno==null && (properties.getProperty("ambiente_produccion")==null ||
                properties.getProperty("ambiente_produccion").equalsIgnoreCase("true"))){
            throw new Exception("No hay equipo en la bodega");
        }
        return imeiInterno;
    }
    
    public static void buscarPrimerResultadoBodega(WebDriver driver) {
        // TODO mover a la clase ObjetosPlmLogin.java
        ActionsUtil.sleepSeconds(3);
        By primerResultado = By.xpath("//*[@id='nc_refsel_list_row_0']/*[@class='refsel_name']");
        WebElement resultadoBusqueda = driver.findElement(primerResultado);
        resultadoBusqueda.click();
    }


    public static void filtrarEquipo(WebDriver driver,By modalInputBy,String nombreEquipo) {
       /** try{
        ActionsUtil.clic(driver, By.xpath("//span[@title='Ordenado y filtrado para columna IMEI']"));
        WebElement elementClear=driver.findElement(By.xpath("//button[@class='filter-dotted-link']"));
        elementClear.click();
        esperarCargar(driver);
        }
        catch (Exception e) {
            // TODO: handle exception
        }*/
        ActionsUtil.clic(driver, By.xpath("//span[@title='Ordenado y filtrado para columna Modelo']"));
        WebElement element=driver.findElement(modalInputBy);
        element.clear();
        element.sendKeys(nombreEquipo);
        //no es necesario dar clic, el busca al ingresar el texto
        try{
        WebElement elemento=driver.findElement(By.xpath("//button[contains(text(),'Aplicar')]"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", elemento);
        }catch (Exception e) {
            // TODO: handle exception
        }
        ActionsUtil.esperarCargar(driver);
    }

    public static void clicPanelIzquiedoExpandir(WebDriver driver) {
        ActionsUtil.sleepSeconds(1);
        By botonExpandir = By.xpath("//button[@class=\"gwt-Button left-panel-collapse-button collapsed\" "
                    + "or @class=\"left-panel-collapse-button collapsed gwt-Button\" "
                    + "or @class=\"collapsed left-panel-collapse-button gwt-Button\"]");
        ActionsUtil.esperarPoderClicBy(driver, botonExpandir);
        ActionsUtil.clic(driver, botonExpandir);
        ActionsUtil.sleepSeconds(1);
        
    }

    /**
     * Método que obtiene el valor de idEjecucion
     * @return the idEjecucion
     */
    public static String getIdEjecucion() {
        return idEjecucion;
    }

    /**
     * Método que establece el valor de idEjecucion
     * @param idEjecucion el idEjecucion a establecer
     */
    public static void setIdEjecucion(String idEjecucion) {
        ActionsUtil.idEjecucion = idEjecucion;
    }

    /**
     * Método que obtiene el valor de ejecucion
     * @return the ejecucion
     */
    public static String getEjecucion() {
        return ejecucion;
    }

    /**
     * Método que establece el valor de ejecucion
     * @param ejecucion el ejecucion a establecer
     */
    public static void setEjecucion(String ejecucion) {
        ActionsUtil.ejecucion = ejecucion;
    }

    public static void clickLinkTexto(WebDriver driver, String texto) {
        By link = By.xpath("//a[contains(text(), '" + texto + "')]");
        esperarPoderClicBy(driver, link);
        clic(driver, link);
    }

    public static void obtenerErrorSsp(WebDriver driver) {
      try{
          driver.findElement(By.xpath("//div[@class='nc-popup-overlay jsPopupOverlay jsInformationMessagePopup _top _no-background nc-popup-overlay_top nc-popup-overlay_no-background nc-popup-overlay_shadow_null']"));
          takeSnapShot(driver,"ErrorAlta.png");
          String urlActual=null;
          urlActual=driver.getCurrentUrl();
          //actualizo url antes error
          operacionesBD.updateTablasSalida(urlActual, getIdEjecucion(),getEjecucion());
      }
      catch (Exception e) {
       LOGGER.info("Error controlado");
    }
      try{
          driver.findElement(By.xpath("//section[@class='nc-popup__content jsPopupContent']"));
          takeSnapShot(driver,"ErrorAlta.png");
          String urlActual=null;
          urlActual=driver.getCurrentUrl();
          //actualizo url antes error
          operacionesBD.updateTablasSalida(urlActual, getIdEjecucion(),getEjecucion());
      }
      catch (Exception e) {
       LOGGER.info("Error controlado");
    }
    
        
    }

    public static List<Object> asignarParametrosSalida(AsignarParametrosSalidaInDTO inDTO) {
        List<Object> parametros= new ArrayList<>();
        parametros.add(inDTO.getUrl());
        parametros.add(inDTO.getEstadoOrden());
        parametros.add(inDTO.getCuentaFacturacion());
        parametros.add(inDTO.getBdNumeroTelefono());
        parametros.add(inDTO.getSimCardInicial());//sim
        parametros.add(inDTO.getProcesadoCuando());
        parametros.add(inDTO.getNombreCliente());
        parametros.add(inDTO.getNombreFlujo());
        parametros.add(inDTO.getParametrosJson());
        parametros.add(properties.getProperty("nc.user"));
        parametros.add(inDTO.getBdPlan());//plan actual
        parametros.add(inDTO.getBdTipoEquipo());//tipo equipop
        parametros.add(inDTO.getNombreDelEquipoInterno());
        parametros.add(inDTO.getNombreDelEquipoInterno());
        parametros.add(inDTO.getImeiDelEquipoIterno());
        parametros.add(inDTO.getSloActivados());
        parametros.add(inDTO.getSloDesactivados());
        parametros.add(inDTO.getPlanActual());
        parametros.add(inDTO.getPlanNuevo());
        parametros.add(inDTO.getSimcardVieja());
        parametros.add(inDTO.getSimcardNueva());
        parametros.add(inDTO.getNumeroAntiguo());
        parametros.add(inDTO.getNumeroNuevo());
        parametros.add(inDTO.getNombreDelEquipoRenovacion());
        parametros.add(inDTO.getNombreDelEquipoRenovacion());
        parametros.add(inDTO.getImeiDelEquipoRenovacion());
        parametros.add(inDTO.getNombreFlujo());//FLUJO
        parametros.add(inDTO.getIdFLujoTabla());//ID tabla ejecutada
        parametros.add(inDTO.getFechaSegundosMilisegundos());
        parametros.add(inDTO.getBdNumeroCuentaCliente());//"Número de cuenta del cliente"
        parametros.add(inDTO.getBdClientePospago());//"Número cuenta prepago"
        parametros.add(inDTO.getBdClientePospago());//"Número cuenta pospago"
        parametros.add(inDTO.getBdClientePospagoInAdvance());//"Número Cuenta pospago in advance"
        parametros.add(inDTO.getCategoria());
        parametros.add(inDTO.getBdNumeroDocumento());//"identificación"
        parametros.add(inDTO.getBdNombre());//"nombre completo"
        parametros.add(inDTO.getTipo_cuenta());//"tipo cuenta"
        parametros.add(inDTO.getCiclo());//"ciclo"
        parametros.add(inDTO.getFechaCreacion());
        parametros.add(Calendar.getInstance().getTime());
        parametros.add(inDTO.getDisputaGenerada());
        parametros.add(inDTO.getFacturaDisputa());
        parametros.add(inDTO.getFacturaNotaCredito());
        parametros.add(inDTO.getUrlDisputa());
        parametros.add(inDTO.getUrlNotaCredio());
        parametros.add(inDTO.getUrlDocumentoNotaCredito());
        return parametros;
    } 

    /**
     * Método responsable de trasforma una tabla por filas/columnas, en donde
     * una fila se divide en dos columnas, y una de ellas es el nombre en
     * pantalla la llave, y la otra columna es objeto del mapa.
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 14/02/2020
     * @version 1.0
     * @param driver         la interfaz principal para usar en las pruebas, que
     *                       representa un navegador web
     * @param objetoToAction xpath que representa cada fila/columna de la tabla
     * @return mapa con llave nombre columna, elemento columna
     */
    public static Map<String, WebElement> obtenerMapaTabla(
            final WebDriver driver, final By objetoToAction) {
        List<WebElement> filasColumnas = driver.findElements(objetoToAction);
        String nombreCampo = null;
        WebElement elemento = null;
        Map<String, WebElement> mpa = new HashMap<>();
        for (int i = 0; i < filasColumnas.size()-1; i++) {
            nombreCampo = filasColumnas.get(i).getText();
            i++;
            elemento = filasColumnas.get(i);
            mpa.put(nombreCampo, elemento);
        }
        return mpa;
    }
    

    public static void clickLinkTexto(WebDriver driver, String texto, boolean tablaParametros) {        
        
        if(tablaParametros) {
            texto = properties.getProperty(texto);
        }
            
        By link = By.xpath("//a[contains(text(), '" + texto + "')]");
        esperarPoderClicBy(driver, link);
        clic(driver, link);
    }

    public static Boolean validarPlanPrevioPago(String plan) {
        Boolean previopago = Boolean.FALSE;
        String[] planesPreviopago = properties.getProperty("texto.planes.previopago").split(",");
        for (String item_plan : planesPreviopago) {
            if (plan.startsWith(item_plan)) {
                previopago = Boolean.TRUE;
                break;
            }
        }
        return previopago;
    }

    public static void visibleElementoBottonTexto(WebDriver driver,
            String texto) {
        obtenerElementoBottonTexto(driver,texto);

    }
}
