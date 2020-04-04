package co.com.hbt.koba.util;

import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Clase responsable de manejar el driver y su logica
 *
 * @uthor hataborda <br>
 *        Harold Taborda <br>
 *        hataborda@heinsohn.com.co
 *
 * @date 17/01/2020
 * @version 1.0
 */
public class DriverManagerFactory {
    //atributos requeridos
	static WebDriver driver;
	static PropertiesLoader properties = PropertiesLoader.getInstance();
	private static final Logger LOGGER = LoggerFactory.getLogger(DriverManagerFactory.class);

	/**
     * 
     * Método responsable de inicializar el driver y sus parametros
     *
     * @uthor hataborda <br>
     *        Harold Taborda<br>
     *        hataborda@heinsohn.com.co
     *
     * @date 17/01/2020
     * @version 1.0
     */
	public DriverManagerFactory() {
		String driverType = properties.getProperty("webdriver.driver");
		if (ActionsUtil.textoMinusculasSinEspacios(driverType).equalsIgnoreCase("firefox")) {
			String firefoxDriverPath = properties.getProperty("webdriver.firefox.driver");
			System.setProperty("webdriver.chfirefoxrome.driver", firefoxDriverPath);
			driver = new FirefoxDriver();
		} else {
			String chromeDriverPath = properties.getProperty("webdriver.chrome.driver");
			String chromeSwitches = properties.getProperty("chrome.switches");
			String verbose = properties.getProperty("webdriver.chrome.verboseLogging");
			String logPath = System.getProperty("user.dir") + "\\target\\chromedriver.log";
			long implic= (properties.getProperty("webdriver.timeouts.implicitlywait")) != null
					? Long.parseLong(properties.getProperty("webdriver.timeouts.implicitlywait")) : 0L;
			long pageLo= (properties.getProperty("webdriver.timeouts.pageLoadTimeout")) != null
					? Long.parseLong(properties.getProperty("webdriver.timeouts.pageLoadTimeout")): 300000L;
			long script = (properties.getProperty("webdriver.timeouts.setScriptTimeout")) != null
					? Long.parseLong(properties.getProperty("webdriver.timeouts.setScriptTimeout")): 30000L;
			LOGGER.info(logPath);
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			System.setProperty("webdriver.chrome.logfile", logPath);
			System.setProperty("webdriver.chrome.verboseLogging", verbose);
			ChromeOptions options = new ChromeOptions();
			String []  chromeArguments=chromeSwitches.split(",");
			for(String chromeSwitchesx:chromeArguments) {
				options.addArguments(chromeSwitchesx.trim());
			}
			
			//{implicit=0, pageLoad=300000, script=30000}
			HashMap<String, Long> timeouts = new HashMap<String, Long>();
			timeouts.put("implicit", implic);
			timeouts.put("pageLoad", pageLo);
			timeouts.put("script", script);
			options.setCapability("timeouts", timeouts);
			driver = new ChromeDriver(options);

		}
	}

	 /**
     * 
     * Método responsable de retornar el driver
     *
     * @uthor hataborda <br>
     *        Harold Taborda<br>
     *        hataborda@heinsohn.com.co
     *
     * @date 17/01/2020
     * @version 1.0
     */
	public static WebDriver getDriver() {
		return driver;
	}

	 /**
     * 
     * Método responsable de cerrar el navegador
     *
     * @uthor hataborda <br>
     *        Harold Taborda<br>
     *        hataborda@heinsohn.com.co
     *
     * @date 17/01/2020
     * @version 1.0
     */
	public void cerrarNavegador() {
		if(driver != null) {
	    driver.quit(); 
		}
    }

}
