
package co.com.hbt.koba.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionPostgresql implements IConnection {

	static PropertiesLoader properties = PropertiesLoader.getInstance();
	private static Connection cnx = null;

	/**
	 * Constructor de la clase
	 */
	public ConnectionPostgresql() {
	}

	/**
	 * <b>Descripcion: </b>Obtiene la conexion a la base de datos del Back
	 * 
	 * @return Connection
	 * @throws Exception
	 */
	public Connection getConnectionBack() throws Exception {

		if (cnx == null) {
			try {
			   // Class.forName("org.postgresql.Driver");
				/**String hostname = properties.getProperty("hostnameBD");
				String port = properties.getProperty("portBD");
				String url = "jdbc:postgresql:" + hostname + ":" + port +"/"+properties.getProperty("nombreBD");
				cnx = DriverManager.getConnection(url, properties.getProperty("usernameBD"),
						properties.getProperty("passwordBD"));*/
			    
			    String url = "jdbc:postgresql://"+properties.getProperty("hostnameBD")+":" +properties.getProperty("portBD")+"/"+properties.getProperty("nombreBD");
			    Properties props = new Properties();
			    props.setProperty("user",properties.getProperty("usernameBD"));
			    props.setProperty("password",properties.getProperty("passwordBD"));
			    cnx = DriverManager.getConnection(url, props);
			} catch (SQLException ex) {
				throw new SQLException(ex);
			}
		}
		return cnx;

	}

}
