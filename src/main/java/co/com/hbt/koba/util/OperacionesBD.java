/**
 * 
 */
package co.com.hbt.koba.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.hbt.koba.constans.ConsultasDinamicasConstans;

/**
 * @author Harold
 *
 */
public class OperacionesBD {

    private PreparedStatement statement;
    static Connection conexion = null;
    static Logger LOGGER = LoggerFactory.getLogger(OperacionesBD.class);

    /**
     * 
     * Método responsable
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 20/02/2020
     * @version 1.0
     */
    public void abrirConexionBD() {
        try {
            conexion = ConnectionUtils.getInstance().getConnectionBack();
        } catch (Exception e) {
            LOGGER.error("Se presento un error en abrir conexion BD", e);
        }
    }

    /***
     * 
     * Método responsable
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 20/02/2020
     * @version 1.0
     * @param consulta
     * @param parametros
     * @return
     */
    public ResultSet ejecutarConsulta(String consulta, List<Object> parametros) {

        if (conexion == null) {
            abrirConexionBD();
        }
        try {
            statement = conexion.prepareStatement(consulta);
            if (parametros != null) {
                int cont = 0;
                for (Object object : parametros) {
                    if (object instanceof Integer) {
                        statement.setInt(++cont, Integer.parseInt(object.toString()));
                    } else if (object instanceof String) {
                        statement.setString(++cont, object.toString());
                    } else if (object instanceof Double) {
                        statement.setDouble(++cont, Double.parseDouble(object.toString()));
                    } else if (object instanceof Boolean) {
                        statement.setBoolean(++cont, Boolean.getBoolean(object.toString()));
                    } else if (object instanceof Long) {
                        statement.setLong(++cont, Long.parseLong(object.toString()));
                    }
                }
            }
            return statement.executeQuery();
        } catch (SQLException e) {
            LOGGER.error("Se presento un error al ejecutar la consulta ", e);
        }

        return null;
    }

    /**
     * 
     * Método responsable
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 20/02/2020
     * @version 1.0
     */
    public void cerrarStatement() {
        try {
            if (statement != null && !statement.isClosed()) {
                statement.close();
            }
        } catch (SQLException e) {
            LOGGER.error("Error cerrando el statement ", e);
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
     * @date 20/02/2020
     * @version 1.0
     * @param parametros
     * @return
     */
    public int insertarParametros(List<Object> parametros) {
        if (conexion == null) {
            abrirConexionBD();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(ConsultasDinamicasConstans.INSERT_PARAMETROS);
        sb.append(ConsultasDinamicasConstans.ABRIR);
        for (Object object : parametros) {
            if (object instanceof String) {
                if (object.toString().contains("NEXTVAL")) {
                    sb.append(object + ",");
                } else {
                    sb.append("'" + object + "'" + ",");
                }
            } else if (object instanceof Date) {
                sb.append("'" + object + "'" + ",");
            } else {
                sb.append(object + ",");
            }
        }
        String insert = sb.toString().substring(0, sb.toString().length() - 1) + ConsultasDinamicasConstans.CERRAR;
        try {
            statement = conexion.prepareStatement(insert);
            return statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Error insertando en la tabla ", e);
            return 0;
        } finally {
            cerrarStatement();
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
     * @date 20/02/2020
     * @version 1.0
     * @param parametros
     * @return
     */
    public int insertarRegistroAutomatizacion(List<Object> parametros) {
        if (conexion == null) {
            abrirConexionBD();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(ConsultasDinamicasConstans.INSER_FLUJOS_AUTOMATICOS);
        sb.append(ConsultasDinamicasConstans.ABRIR);
        for (Object object : parametros) {
            if (object instanceof String) {
                if (object.toString().contains("NEXTVAL")) {
                    sb.append(object + ",");
                } else {
                    sb.append("'" + object + "'" + ",");
                }
            } else if (object instanceof Date) {
                sb.append("'" + object + "'" + ",");
            } else {
                sb.append(object + ",");
            }
        }
        String insert = sb.toString().substring(0, sb.toString().length() - 1) + ConsultasDinamicasConstans.CERRAR;
        try {
            statement = conexion.prepareStatement(insert);
            return statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Error insertando en la tabla ", e);
            return 0;
        } finally {
            cerrarStatement();
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
     * @date 20/02/2020
     * @version 1.0
     * @param parametros
     * @return
     */
    public int actualizarRegistroAutomatizacion(List<Object> parametros) {
        if (conexion == null) {
            abrirConexionBD();
        }
        try {
            statement = conexion.prepareStatement(ConsultasDinamicasConstans.UPDATE_FLUJOS_AUTOMATICOS_ALL);
            int cont = 1;
            for (int i = 0; i < parametros.size(); i++) {
                if (parametros.get(i) instanceof String) {
                    statement.setString(cont, parametros.get(i) != null && parametros.get(i).toString() != null && !parametros.get(i).toString().isEmpty() ?
                            parametros.get(i).toString() : null);
                } else if (parametros.get(i) instanceof Long) {
                    statement.setLong(
                            cont, parametros.get(i) != null && parametros.get(i).toString() != null && !parametros.get(i).toString().isEmpty() ? Long.parseLong(parametros.get(i).toString()) : null);
                } else if (parametros.get(i) instanceof Date) {
                    statement.setDate(
                            cont, parametros.get(i) != null && parametros.get(i).toString() != null && !parametros.get(i).toString().isEmpty()
                            ? new java.sql.Date(((Date) parametros.get(i)).getTime()) : null);
                } else {
                    statement.setString(cont, null);
                }
                cont++;
            }

            return statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Error insertando en la tabla ", e);
            return 0;
        } finally {
            cerrarStatement();
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
     * @date 20/02/2020
     * @version 1.0
     * @param consulta
     * @param name
     * @param parseLong
     * @return
     */
    public int updateTablasEntrada(String consulta, String name, Long parseLong) {
        if (conexion == null) {
            abrirConexionBD();
        }
        try {
            statement = conexion.prepareStatement(consulta);
            statement.setString(1, name);
            statement.setLong(2, parseLong);
            return statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Error insertando en la tabla ", e);
            return 0;
        } finally {
            cerrarStatement();
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
     * @date 20/02/2020
     * @version 1.0
     * @param procesadoCuando
     * @param estadoOrden
     * @param bodegaDefecto
     * @param canalDistribucion
     * @param fechaEnvio
     * @param confirmadoPor
     * @param idEjecucion
     * @param ejecucion
     * @return
     */
    public int updateTablasSalida(String procesadoCuando, String estadoOrden, String bodegaDefecto,
            String canalDistribucion, String fechaEnvio, String confirmadoPor, String idEjecucion, String ejecucion) {
        if (conexion == null) {
            abrirConexionBD();
        }
        try {
            statement = conexion.prepareStatement(ConsultasDinamicasConstans.UPDATE_FLUJOS_AUTOMATICOS);
            statement.setString(1, estadoOrden);
            statement.setString(2, procesadoCuando);
            statement.setString(3, bodegaDefecto);
            statement.setString(4, canalDistribucion);
            statement.setString(5, fechaEnvio);
            statement.setString(6, confirmadoPor);
            statement.setString(7, idEjecucion);
            statement.setString(8, ejecucion);

            return statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Error insertando en la tabla ", e);
            return 0;
        } finally {
            cerrarStatement();
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
     * @date 20/02/2020
     * @version 1.0
     */
    public void cerrarConexion() {
        try {
            if (conexion != null) {
                conexion.close();
            }
        } catch (SQLException e) {
            LOGGER.error("Error cerrando la conexion ", e);
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
     * @date 20/02/2020
     * @version 1.0
     * @param listaIdsProcesados
     * @param fecha
     * @return
     */
    public ResultSet ejecutarConsultaFlujoFinal(List<String> listaIdsProcesados, String fecha) {
        if (conexion == null) {
            abrirConexionBD();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(ConsultasDinamicasConstans.SELECT_FINAL_URL_ID);
        for (String object : listaIdsProcesados) {
            sb.append("'" + object + "'" + ",");
        }
        String insert = sb.toString().substring(0, sb.toString().length() - 1) + ConsultasDinamicasConstans.CERRAR;
        try {
            statement = conexion.prepareStatement(insert + ConsultasDinamicasConstans.SELECT_FINAL_URL_ID_FIN);
            statement.setString(1, fecha);
            return statement.executeQuery();
        } catch (SQLException e) {
            LOGGER.error("Se presento un error al realizar el insert ", e);
        }
        return null;
    }

    /**
     * 
     * Método responsable
     *
     * @uthor hataborda <br>
     *        Harold Taborda <br>
     *        hataborda@heinsohn.com.co
     *
     * @date 20/02/2020
     * @version 1.0
     * @param categoriaCliente
     * @param nombreCompleto
     * @param tipoCuenta
     * @param ciclo
     * @param idEjecucion
     * @param ejecucion
     * @return
     */
    public int updateTablasSalidaCliente(String categoriaCliente, String nombreCompleto, String tipoCuenta,
            String ciclo, String idEjecucion, String ejecucion) {
        if (conexion == null) {
            abrirConexionBD();
        }
        try {
            statement = conexion.prepareStatement(ConsultasDinamicasConstans.UPDATE_FLUJOS_AUTOMATICOS_CLIENTE);
            statement.setString(1, categoriaCliente);
            statement.setString(2, nombreCompleto);
            statement.setString(3, tipoCuenta);
            statement.setString(4, ciclo);
            statement.setString(5, idEjecucion);
            statement.setString(6, ejecucion);
            return statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Error insertando en la tabla ", e);
            return 0;
        } finally {
            cerrarStatement();
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
     * @date 20/02/2020
     * @version 1.0
     * @param urlError
     * @param idEjecucion
     * @param ejecucion
     * @return
     */
    public int updateTablasSalida(String urlError, String idEjecucion, String ejecucion) {

        if (conexion == null) {
            abrirConexionBD();
        }
        try {
            statement = conexion.prepareStatement(ConsultasDinamicasConstans.UPDATE_FLUJOS_AUTOMATICOS_CLIENTE_URL_ERROR);
            statement.setString(1, urlError);
            statement.setString(2, idEjecucion);
            statement.setString(3, ejecucion);
            return statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Error insertando en la tabla ", e);
            return 0;
        } finally {
            cerrarStatement();
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
     * @date 20/02/2020
     * @version 1.0
     * @param urlError
     * @param idEjecucion
     * @param ejecucion
     * @return
     */
    public int updateTablasSalidaAntesError(String urlError, String idEjecucion, String ejecucion) {

        if (conexion == null) {
            abrirConexionBD();
        }
        try {
            statement = conexion.prepareStatement(ConsultasDinamicasConstans.UPDATE_FLUJOS_AUTOMATICOS_CLIENTE_URL_ERROR_ANTES);
            statement.setString(1, urlError);
            statement.setString(2, idEjecucion);
            statement.setString(3, ejecucion);
            return statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Error insertando en la tabla ", e);
            return 0;
        } finally {
            cerrarStatement();
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
     * @date 20/02/2020
     * @version 1.0
     * @param urlAntes
     * @param urlError
     * @param idEjecucion
     * @param ejecucion
     * @return
     */
    public int updateTablasSalida(String urlAntes, String urlError,String idEjecucion, String ejecucion) {

        if (conexion == null) {
            abrirConexionBD();
        }
        try {
            statement = conexion.prepareStatement(ConsultasDinamicasConstans.UPDATE_FLUJOS_AUTOMATICOS_CLIENTE_URL_ERROR_WARNING);
            statement.setString(1, urlError);
            statement.setString(2, urlError);
            statement.setString(3, idEjecucion);
            statement.setString(4, ejecucion);
            return statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Error insertando en la tabla ", e);
            return 0;
        } finally {
            cerrarStatement();
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
     * @date 20/02/2020
     * @version 1.0
     * @param facturaDisputa
     * @param disputaGenerada
     * @param facturaNotaCredito
     * @param urlDisputa
     * @param urlNotaCredio
     * @param urlDocumento
     * @param idEjecucion
     * @param ejecucion
     * @return
     */
    public int updateTablasSalidaDisputasNotaCredito(String facturaDisputa, String disputaGenerada, String facturaNotaCredito,
            String urlDisputa, String urlNotaCredio, String urlDocumento, String idEjecucion, String ejecucion) {
        if (conexion == null) {
            abrirConexionBD();
        }
        try {
            statement = conexion.prepareStatement(ConsultasDinamicasConstans.UPDATE_FLUJOS_AUTOMATICOS_DISPUTAS_Y_NOTA_CREDITO);
            statement.setString(1, facturaDisputa);
            statement.setString(2, disputaGenerada);
            statement.setString(3, facturaNotaCredito);
            statement.setString(4, urlDisputa);
            statement.setString(5, urlNotaCredio);
            statement.setString(6, urlDocumento);
            statement.setString(7, idEjecucion);
            statement.setString(8, ejecucion);
            return statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Error insertando en la tabla ", e);
            return 0;
        } finally {
            cerrarStatement();
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
     * @date 20/02/2020
     * @version 1.0
     * @param bdNumeroCuentaCliente
     * @param bdCategoria
     * @param bdNombre
     * @param idEjecucion
     * @param ejecucion
     * @return
     */
    public int updateTablaSalidaClienteEmpresarial(
            String bdNumeroCuentaCliente, String bdCategoria, String bdNombre,
            String idEjecucion, String ejecucion) {
        if (conexion == null) {
            abrirConexionBD();
        }
        try {
            statement = conexion.prepareStatement(ConsultasDinamicasConstans.UPDATE_FLUJOS_AUTOMATICOS_SALIDA_CLIENTE_EMPRESARIAL);
            int j=1;
            statement.setString(j++, bdNumeroCuentaCliente);
            statement.setString(j++, bdCategoria);
            statement.setString(j++, bdNombre);
            statement.setString(j++, idEjecucion);
            statement.setString(j++, ejecucion);
            return statement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Error insertando en la tabla ", e);
            return 0;
        } finally {
            cerrarStatement();
        }
        
    }


}
