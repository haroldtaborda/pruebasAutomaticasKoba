package co.com.hbt.koba.util;

import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Clase responsable cargar los properties para su lectura
 *
 * @uthor hataborda <br>
 *        Harold Taborda <br>
 *        hataborda@heinsohn.com.co
 *
 * @date 17/01/2020
 * @version 1.0
 */
public class PropertiesLoader {

    /**
     * 
     * Método responsable de inicializar el loader
     *
     * @uthor hataborda <br>
     *        Harold Taborda<br>
     *        hataborda@heinsohn.com.co
     *
     * @date 17/01/2020
     * @version 1.0
     */
    private PropertiesLoader() {
        load();
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesLoader.class);

    /**
     * 
     * Método responsable de retornar la instancia del loader
     *
     * @uthor hataborda <br>
     *        Harold Taborda<br>
     *        hataborda@heinsohn.com.co
     *
     * @date 17/01/2020
     * @version 1.0
     */
    public static synchronized PropertiesLoader getInstance() {
        if (instance == null)
            instance = new PropertiesLoader();
        return instance;
    }

    /**
     * 
     * Método responsable de cargar los properties
     *
     * @uthor hataborda <br>
     *        Harold Taborda<br>
     *        hataborda@heinsohn.com.co
     *
     * @date 17/01/2020
     * @version 1.0
     */
    private Properties load() {
        try {
            properties.load(new FileInputStream("selenium.properties"));
            properties.load(new FileInputStream("application.properties"));
        } catch (Exception e) {
            LOGGER.error("Excepcion load propiedades:", e);
        }
        return properties;
    }
    
    /**
     * 
     * Método responsable de retornar el valor del propertie por key
     *
     * @uthor hataborda <br>
     *        Harold Taborda<br>
     *        hataborda@heinsohn.com.co
     *
     * @date 17/01/2020
     * @version 1.0
     */
    public String getProperty(String prop) {
        return properties.getProperty(prop);
    }

    /**
     * 
     * Método responsable de asignar un propertie por llave y valor
     *
     * @uthor hataborda <br>
     *        Harold Taborda<br>
     *        hataborda@heinsohn.com.co
     *
     * @date 17/01/2020
     * @version 1.0
     */
    public void setProperty(String property, String value) {
        properties.setProperty(property, value);
    }
    
    public Enumeration<Object> getAll(){
       return properties.keys();
    }

    //isntancias
    private static PropertiesLoader instance;
    private static Properties properties = new Properties();
}
