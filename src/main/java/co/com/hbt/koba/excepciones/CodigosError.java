package co.com.hbt.koba.excepciones;

/**
 * 
 * Clase responsable de almacenar los enums para identificar errores
 *
 * @uthor hataborda <br>
 *        Harold Taborda <br>
 *        hataborda@heinsohn.com.co
 *
 * @date 16/01/2020
 * @version 1.0
 */
public enum CodigosError {

    MAILENVIOADJUNTO("0022", "Ha ocurrido un error en el envio del correo electronico con archivo adjunto."),
    MAILENVIOHTML("0021", "Ha ocurrido un error en el envio del correo electronico en formato HTML."),
    DBCONEXION("0003", "No es posible establecer la conexion");

    private String codigo;
    private String mensaje;

    /**
     * Constructor por defecto
     * 
     * @param codigo
     * @param mensaje
     */
    CodigosError(String codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    /**
     * Método que obtiene el valor de codigo
     * 
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Método que obtiene el valor de mensaje
     * 
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }
}
