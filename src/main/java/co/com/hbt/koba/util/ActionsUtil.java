package co.com.hbt.koba.util;

import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.hamcrest.CoreMatchers;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
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
    private static final long DOS_MIL_MILISEGUNDOS = 2000L;
    private static final long CIEN_MILISEGUNDOS = 100L;
    private static String baseURL;
    static String fechaSegundosMiligesundos;
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
    
    public static void clickLinkTexto(WebDriver driver, String texto) {
        By link = By.xpath("//a[contains(text(), '" + texto + "')]");
        esperarPoderClicBy(driver, link);
        clic(driver, link);
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
            File destFile=validarExisteEvidencia(fileWithoutPath);
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


        public static WebElement parent(SearchContext checkCuotas) {
        return checkCuotas.findElement(By.xpath(".."));
    }

    public static void switchToDefaultContent(WebDriver driver) {
        driver.switchTo().defaultContent();
    }

   

   
}
