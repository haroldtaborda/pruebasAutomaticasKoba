/**
 * ConnectionUtils.java
 */
package co.com.hbt.koba.util;

import java.sql.Connection;

public class ConnectionUtils {

	static PropertiesLoader properties = PropertiesLoader.getInstance();
	/**
	 * Instancia de la clase
	 */
	private static ConnectionUtils instance = null;
	private IConnection delegado;

	/**
	 * Constructor de la clase
	 */
	private ConnectionUtils() {
	    delegado = new ConnectionPostgresql();
	}

	/**
	 * <b>Descripcion: </b>Devuelve la instancia de la clase
	 * @return ConnectionUtils
	 */
	public static ConnectionUtils getInstance() {
		if (instance == null) {
			instance = new ConnectionUtils();
		}
		return instance;
	}
	/**
	 * <b>Descripcion: </b>Obtiene la conexion a la base de datos del Back
	 * @return Connection
	 * @throws Exception
	 */
	public Connection getConnectionBack() throws Exception {

		return delegado.getConnectionBack();
	}
}
