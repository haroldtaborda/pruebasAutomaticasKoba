package co.com.hbt.koba.step;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.hbt.koba.util.EmailUtil;
import co.com.hbt.koba.util.PropertiesLoader;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/cucumber/demo.feature", glue = {
        "co.com.hbt.koba" }, plugin = { "pretty", "html:target/cucumber", "json:target/cucumber.json",
                "junit:target/cucumber.xml" })
public class RunCucumberTest {

    static Logger LOGGER = LoggerFactory.getLogger(RunCucumberTest.class);
    static PropertiesLoader properties = PropertiesLoader.getInstance();

    @BeforeClass
    public static void before() {

    }
    
    @AfterClass
    public static void after() {
      //  eventosFinalesReportes();
    }

    private static void eventosFinalesReportes() {
        // se genera el reporte total por todos los features cucumber reporting
        EmailUtil.generarRepote();
        // se envia correo
        EmailUtil.sendArchivo(System.getProperty("user.dir") + "\\target\\ReporteAutomatizacion.zip",
                System.getProperty("user.dir") + "\\target\\cucumber-html-reports", null);
        
    }

}
