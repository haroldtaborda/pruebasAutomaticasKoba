/**
 * 
 */
package co.com.hbt.koba.constans;

/**
 * @author Harold
 *
 */
public final class ConsultasDinamicasConstans {
    
    private ConsultasDinamicasConstans() {
      }
    
    public static final String SECUENCIA_PARAMETROS="OTC_S_PARAMETROS";
    public static final String SECUENCIA_AUTOMATIZACIONES="OTC_S_AUTOMATIZACIONES";
    public static final String NEXT_PARAMETROS =  SECUENCIA_PARAMETROS+".NEXTVAL";
    public static final String NEXT_AUTOMATIZACIONES =  SECUENCIA_AUTOMATIZACIONES+".NEXTVAL";
    public static final String ABRIR =  "(";
    public static final String CERRAR =  ")";
    public static final String SELECT_PROPIEDADES="SELECT * FROM OTC_T_PARAMETROS";
	public static final String CONSULTAR_TABLA_AUTOMATIZACIONES_ACTUAL = "SELECT ID_AUTOMATIZACION, URL_ORDEN, ESTADO_ORDEN, CUENTA_FACTURACION, NUMERO_LINEA, SIM_CARD, FECHA_ORDEN, CLIENTE, NOMBRE_TRANSACCION, DATOS_ENTRADA,  USUARIO, PLAN, TIPO_EQUIPO,  NOMBRE_EQUIPO_INTERNO, MODELO_EQUIPO_INTERNO, IMEI_EQUIPO_INTERNO, SLO_ACTIVADOS,SLO_DESACTIVADOS, PLAN_ANTIGUO, PLAN_NUEVO, SIMCARD_ANTIGUA, SIMCARD_NUEVA, NUMERO_ANTIGUO, NUMERO_NUEVO, NOMBRE_EQUIPO, MODELO_EQUIPO, IMEI_EQUIPO, FLUJO, ID_EJECUCION, EJECUCION, NUMERO_CUENTA_CLIENTE, NUMERO_CUENTA_PREPAGO, NUMERO_CUENTA_POSTPAGO, NUM_CUENTA_POST_ADVANCE, CATEGORIA_CLIENTE, IDENTIFICACION, NOMBRE_COMPLETO, TIPO_CUENTA, CICLO , FECHA_INICIO, FECHA_FIN, CONFIRMADO_POR, FECHA_ENVIO, CANAL_DISTRIBUCION, BODEGA_DEFECTO, URL_ERROR, URL_ANTES_ERROR, PLATAFORMA, FACTURA_DISPUTA, NUMERO_DISPUTA, NUMERO_NOTA_CREDITO, URL_DISPUTA, URL_AJUSTE_DISPUTA, URL_DOCUMENTO_NOTA_CREDITO FROM OTC_T_AUTOMATIZACIONES WHERE EJECUCION=?";
	public static final String CONSULTAR_TABLA_AUTOMATIZACIONES_CREAR_CLIENTE="SELECT ID_AUTOMATIZACION,ID_EJECUCION,NUMERO_CUENTA_CLIENTE, NUMERO_CUENTA_PREPAGO, NUMERO_CUENTA_POSTPAGO, NUM_CUENTA_POST_ADVANCE, "
	        + "CATEGORIA_CLIENTE, IDENTIFICACION, NOMBRE_COMPLETO, TIPO_CUENTA, CICLO  FROM PUBLIC.OTC_T_AUTOMATIZACIONES WHERE EJECUCION=?";
	
	public static final String INSER_FLUJOS_AUTOMATICOS = "INSERT INTO OTC_T_AUTOMATIZACIONES "
	        + "(URL_ORDEN, ESTADO_ORDEN, CUENTA_FACTURACION, NUMERO_LINEA, SIM_CARD, FECHA_ORDEN, CLIENTE,"
	        + "NOMBRE_TRANSACCION, DATOS_ENTRADA,USUARIO, PLAN, TIPO_EQUIPO, NOMBRE_EQUIPO_INTERNO, "
	        + "MODELO_EQUIPO_INTERNO, IMEI_EQUIPO_INTERNO, SLO_ACTIVADOS, SLO_DESACTIVADOS, PLAN_ANTIGUO, PLAN_NUEVO,"
	        + "SIMCARD_ANTIGUA, SIMCARD_NUEVA, NUMERO_ANTIGUO, NUMERO_NUEVO, NOMBRE_EQUIPO, MODELO_EQUIPO,"
	        + "IMEI_EQUIPO,FLUJO,ID_EJECUCION,EJECUCION,"
	        + "NUMERO_CUENTA_CLIENTE,NUMERO_CUENTA_PREPAGO,NUMERO_CUENTA_POSTPAGO,NUM_CUENTA_POST_ADVANCE,"
	        + "CATEGORIA_CLIENTE,IDENTIFICACION,NOMBRE_COMPLETO,TIPO_CUENTA,CICLO, FECHA_INICIO,FECHA_FIN, FACTURA_DISPUTA, NUMERO_DISPUTA, NUMERO_NOTA_CREDITO,"
	        + "URL_AJUSTE_DISPUTA, URL_DISPUTA, URL_DOCUMENTO_NOTA_CREDITO) VALUES ";
	
	   public static final String UPDATE_FLUJOS_AUTOMATICOS_ALL = "UPDATE  OTC_T_AUTOMATIZACIONES SET "
	            + "URL_ORDEN= ?, ESTADO_ORDEN= ?, CUENTA_FACTURACION= ?, NUMERO_LINEA= ?, SIM_CARD= ?, FECHA_ORDEN= ?, CLIENTE= ?,"
	            + "NOMBRE_TRANSACCION= ?, DATOS_ENTRADA= ?,USUARIO= ?, PLAN= ?, TIPO_EQUIPO= ?, NOMBRE_EQUIPO_INTERNO= ?, "
	            + "MODELO_EQUIPO_INTERNO= ?, IMEI_EQUIPO_INTERNO= ?, SLO_ACTIVADOS= ?, SLO_DESACTIVADOS= ?, PLAN_ANTIGUO= ?, PLAN_NUEVO= ?,"
	            + "SIMCARD_ANTIGUA= ?, SIMCARD_NUEVA= ?, NUMERO_ANTIGUO= ?, NUMERO_NUEVO= ?, NOMBRE_EQUIPO= ?, MODELO_EQUIPO= ?,"
	            + "IMEI_EQUIPO= ?,FLUJO= ?,ID_EJECUCION= ?,EJECUCION= ?,"
	            + "NUMERO_CUENTA_CLIENTE= ?,NUMERO_CUENTA_PREPAGO= ?,NUMERO_CUENTA_POSTPAGO= ?,NUM_CUENTA_POST_ADVANCE= ?,"
	            + "CATEGORIA_CLIENTE= ?,IDENTIFICACION= ?,NOMBRE_COMPLETO= ?,TIPO_CUENTA= ?,CICLO= ?, FECHA_INICIO= ?,FECHA_FIN= ?,"
	            + "NUMERO_DISPUTA=?, FACTURA_DISPUTA=?, NUMERO_NOTA_CREDITO=?, URL_AJUSTE_DISPUTA=?, URL_DISPUTA=?, URL_DOCUMENTO_NOTA_CREDITO=? WHERE ID_EJECUCION = ? AND EJECUCION=? ";
	
	   public static final String INSERT_PARAMETROS = "INSERT INTO OTC_T_PARAMETROS"
	            + " (NOMBRE,VALOR) VALUES ";
	   
	   public static final String UPDATE_FLUJOS_AUTOMATICOS ="UPDATE OTC_T_AUTOMATIZACIONES SET ESTADO_ORDEN= ?, FECHA_ORDEN = ?,"
	           + " bodega_defecto= ?, canal_distribucion= ?,fecha_envio= ?,confirmado_por= ? WHERE ID_EJECUCION = ? AND EJECUCION=?";
	   public static final String UPDATE_FLUJOS_AUTOMATICOS_CLIENTE ="UPDATE OTC_T_AUTOMATIZACIONES SET CATEGORIA_CLIENTE=?, NOMBRE_COMPLETO=?, TIPO_CUENTA=?, CICLO=? WHERE  ID_EJECUCION = ? AND EJECUCION=?";
       public static final String UPDATE_FLUJOS_AUTOMATICOS_DISPUTAS_Y_NOTA_CREDITO ="UPDATE OTC_T_AUTOMATIZACIONES SET FACTURA_DISPUTA=?, NUMERO_DISPUTA=?, NUMERO_NOTA_CREDITO=?, URL_DISPUTA=?, URL_AJUSTE_DISPUTA=?, URL_DOCUMENTO_NOTA_CREDITO=? WHERE  ID_EJECUCION = ? AND EJECUCION=?";
	   public static final String UPDATE_FLUJOS_AUTOMATICOS_CLIENTE_URL_ERROR ="UPDATE OTC_T_AUTOMATIZACIONES SET URL_ERROR=? WHERE  ID_EJECUCION = ? AND EJECUCION=?";
	   public static final String UPDATE_FLUJOS_AUTOMATICOS_CLIENTE_URL_ERROR_ANTES ="UPDATE OTC_T_AUTOMATIZACIONES SET URL_ANTES_ERROR=? WHERE  ID_EJECUCION = ? AND EJECUCION=?";
	   public static final String UPDATE_FLUJOS_AUTOMATICOS_CLIENTE_URL_ERROR_WARNING ="UPDATE OTC_T_AUTOMATIZACIONES SET URL_ANTES_ERROR= ?, URL_ERROR=? WHERE  ID_EJECUCION = ? AND EJECUCION=?";
	   
	   //SELECTS TABLAS FLUJOS
	   public static final String SELECT_TRASFERENCIA_BENEFICIARIO_ID="SELECT * FROM OTC_T_TRASFERENCIA_BENEFICIARIO WHERE ID = ?";
	   public static final String SELECT_OTC_T_ACTIVACION_SLO_ID="SELECT * FROM OTC_T_ACTIVACION_SLO WHERE ID = ?";
	   public static final String SELECT_OTC_T_ALTAS_LINEAS_ID="SELECT * FROM OTC_T_ALTAS_LINEAS WHERE ID = ?";
	   public static final String SELECT_OTC_T_BAJA_ID="SELECT * FROM OTC_T_BAJA WHERE ID = ?";
	   public static final String SELECT_OTC_T_CABMIO_SIM_ID="SELECT * FROM OTC_T_CAMBIO_SIM WHERE ID = ?";
	   public static final String SELECT_OTC_T_CAMBIO_NUMERO_ID="SELECT * FROM OTC_T_CAMBIO_NUMERO WHERE ID = ?";
	   public static final String SELECT_OTC_T_CAMBIO_PLAN_ID="SELECT * FROM OTC_T_CAMBIO_PLAN WHERE ID = ?";
	   public static final String SELECT_OTC_T_CREAR_CLIENTE_ID="SELECT * FROM OTC_T_CREAR_CLIENTE WHERE ID = ?";
	   public static final String SELECT_OTC_T_FAC_EQUIPO_SIN_LINEA_ID="SELECT * FROM OTC_T_FAC_EQUIPO_SIN_LINEA WHERE ID = ?";
	   public static final String SELECT_OTC_T_RENOVACION_EQUIPO_ID="SELECT * FROM OTC_T_RENOVACION_EQUIPO WHERE ID = ?";
	   public static final String SELECT_OTC_T_SUSPENDER_REANUDAR_ID="SELECT * FROM OTC_T_SUSPENDEER_REANUDAR WHERE ID = ?";
	   public static final String SELECT_OTC_T_FACTURAS_MISCELANEAS_ID="SELECT * FROM OTC_T_FACTURAS_MISCELANEAS WHERE ID = ?";
       public static final String SELECT_OTC_T_DISPUTAS_Y_NOTACREDITO_ID="SELECT * FROM OTC_T_DISPUTAS_Y_NOTACREDITO WHERE ID = ?";

       public static final String SELECT_OTC_T_OTC_T_ALTAS_SSP_ID="SELECT * FROM OTC_T_ALTAS_SSP WHERE ID = ?";
    /**
     * consulta todos los datos de la tabla cliente empresarial por el id.
     */
    public static final String SELECT_OTC_T_CLIENTE_EMPRESARIAL_ID = "SELECT * FROM OTC_T_CLIENTE_EMPRESARIAL WHERE ID = ?";

	   //updates
	   public static final String UPDATE_OTC_T_SUSPENDER_REANUDAR_ID="UPDATE OTC_T_SUSPENDEER_REANUDAR SET PROCESADO= ? WHERE ID = ?";
	   public static final String UPDATE_OTC_T_ACTIVACION_SLO_ID="UPDATE OTC_T_ACTIVACION_SLO SET PROCESADO= ? WHERE ID = ?";
	   public static final String UPDATE_OTC_T_TRASNFERENCIA_BENEFICIARIO_ID="UPDATE OTC_T_TRASFERENCIA_BENEFICIARIO SET PROCESADO= ? WHERE ID = ?";
	   public static final String UPDATE_OTC_T_ALTAS_LINEAS_ID="UPDATE OTC_T_ALTAS_LINEAS SET PROCESADO= ? WHERE ID = ?";
	   public static final String UPDATE_OTC_T_BAJA="UPDATE OTC_T_BAJA SET PROCESADO= ? WHERE ID = ?";
	   public static final String UPDATE_OTC_T_CABMIO_SIM="UPDATE OTC_T_CAMBIO_SIM SET PROCESADO= ? WHERE ID = ?";
	   public static final String UPDATE_OTC_T_CAMBIO_NUMERO_ID="UPDATE OTC_T_CAMBIO_NUMERO SET PROCESADO= ? WHERE ID = ?";
	   public static final String UPDATE_OTC_T_CAMBIO_PLAN_ID="UPDATE OTC_T_CAMBIO_PLAN SET PROCESADO= ? WHERE ID = ?";
	   public static final String UPDATE_OTC_T_CREAR_CLIENTE_ID="UPDATE OTC_T_CREAR_CLIENTE SET PROCESADO= ? WHERE ID = ?";
	   public static final String UPDATE_OTC_T_FAC_EQUIPO_SIN_LINEA_ID="UPDATE OTC_T_FAC_EQUIPO_SIN_LINEA SET PROCESADO= ? WHERE ID = ?";
	   public static final String UPDATE_OTC_T_RENOVACION_EQUIPO_ID="UPDATE OTC_T_RENOVACION_EQUIPO SET PROCESADO= ? WHERE ID = ?";
  	   public static final String UPDATE_OTC_T_FACTURAS_MISCELANEAS_ID="UPDATE OTC_T_FACTURAS_MISCELANEAS SET PROCESADO= ? WHERE ID = ?";
       public static final String UPDATE_OTC_T_DISPUTAS_Y_NOTACREDITO_ID="UPDATE OTC_T_DISPUTAS_Y_NOTACREDITO SET PROCESADO= ? WHERE ID = ?";

       /**
        * Actualizar el registro a procesado OTC_T_CLIENTE_EMPRESARIAL.
        */
       public static final String UPDATE_OTC_T_CLIENTE_EMPRESARIAL_ID="UPDATE OTC_T_CLIENTE_EMPRESARIAL SET PROCESADO= ? WHERE ID = ?";
       
       public static final String UPDATE_otc_t_altas_ssp="UPDATE OTC_T_ALTAS_SSP SET PROCESADO= ? WHERE ID = ?";

       public static final String SELECT_FINAL_URL_ID = "SELECT URL_ORDEN,ID_EJECUCION,EJECUCION,FLUJO,IDENTIFICACION,NUMERO_CUENTA_PREPAGO,NUMERO_CUENTA_POSTPAGO,NUM_CUENTA_POST_ADVANCE, NUMERO_LINEA FROM OTC_T_AUTOMATIZACIONES WHERE ID_EJECUCION IN (";
       public static final String SELECT_FINAL_URL_ID_FIN =" AND EJECUCION=?";
    /**
     * Consulta que actualiza los campos de salida del flujo cliente
     * empresarial.
     */
    public static final String UPDATE_FLUJOS_AUTOMATICOS_SALIDA_CLIENTE_EMPRESARIAL = "UPDATE PUBLIC.OTC_T_AUTOMATIZACIONES SET NUMERO_CUENTA_CLIENTE = ?, CATEGORIA_CLIENTE = ?, NOMBRE_COMPLETO=? WHERE ID_EJECUCION = ? AND EJECUCION=?";

	
}


