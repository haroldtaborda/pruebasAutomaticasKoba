/**
 *
 * files
 */
package co.com.hbt.koba.util;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Clase responsable
 *
 * @uthor hataborda <br>
 * Harold Taborda <br>
 * hataborda@heinsohn.com.co
 *
 * @date 21/01/2020
 * @version 1.0
 */
public class Main {

    static OperacionesBD operacionesBD = new OperacionesBD();
    static PropertiesLoader properties = PropertiesLoader.getInstance();

    /**
     * Método responsable
     *
     * @uthor hataborda <br>
     * Harold Taborda <br>
     * hataborda@heinsohn.com.co
     *
     * @date 21/01/2020
     * @version 1.0
     * @param args
     */
    public static void main(String[] args) {
        // se guarda por BD
        operacionesBD.abrirConexionBD();
        List<Object> parametros = null;
        Enumeration<Object> keys = properties.getAll();
        Object key = null;
        while (keys.hasMoreElements()) {
            key = keys.nextElement();
            parametros = new ArrayList<>();
            parametros.add(key.toString());
            parametros.add(properties.getProperty(key.toString()));
            operacionesBD.insertarParametros(parametros);
        }

    }

}
