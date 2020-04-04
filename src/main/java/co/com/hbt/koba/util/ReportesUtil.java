/**
 *
 * files
 */
package co.com.hbt.koba.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.hbt.koba.constans.FormatConstans;
import co.com.hbt.koba.dto.AutomatizacionesDTO;
import co.com.hbt.koba.enums.FlujoEnum;

/**
 * Clase responsable
 *
 * @uthor hataborda <br>
 * HaroldTaborda <br>
 * hstaborda@heinsohn.com.co
 *
 * @date 22/01/2020
 * @version 1.0
 */
public class ReportesUtil {

    static PropertiesLoader properties = PropertiesLoader.getInstance();
    static Logger LOGGER = LoggerFactory.getLogger(ReportesUtil.class);
    static String fechaSegundosMiligesundos;
    static List<String> listaIdsProcesados;

    /**
     * 
     */
    public ReportesUtil() {
    }

    public static File generarReporteAutomatizacionesBD() {
        return rerpoteGeneral();
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
     * @return
     */
    private static File rerpoteGeneral() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(0, "Reporte General");
        String[] headers = new String[] { "ID_AUTOMATIZACION", "URL_ORDEN", "ESTADO_ORDEN", "CUENTA_FACTURACION",
                "NUMERO_LINEA", "SIM_CARD", "" + "FECHA_ORDEN", "NOMBRE_TRANSACCION", "DATOS_ENTRADA",
                "USUARIO", "PLAN", "TIPO_EQUIPO", "NOMBRE_EQUIPO_INTERNO", "" + "MODELO_EQUIPO_INTERNO",
                "IMEI_EQUIPO_INTERNO", "SLO_ACTIVADOS", "PLAN_ANTIGUO", "PLAN_NUEVO", "SIMCARD_ANTIGUA",
                "" + "SIMCARD_NUEVA", "NUMERO_ANTIGUO", "NUMERO_NUEVO", "NOMBRE_EQUIPO", "MODELO_EQUIPO", "IMEI_EQUIPO",
                "EJECUCION", "ID_EJECUCION", "" + "NUMERO_CUENTA_CLIENTE", "NUMERO_CUENTA_PREPAGO",
                "NUMERO_CUENTA_POSTPAGO", "NUMERO_CUENTA_POST_ADVANCE", "CATEGORIA_CLIENTE", "IDENTIFICACION",
                "NOMBRE_COMPLETO", "TIPO_CUENTA", "CICLO", "FECHA_INICIO", "FECHA_FIN","CONFIRMADO_POR","CANAL_DISTRIBUCION","FECHA_ENVIO","BODEGA_DEFECTO","PLATAFORMA", "SLO_DESACTIVADOS",
                "FACTURA_DISPUTA", "NUMERO_DISPUTA", "NUMERO_NOTA_CREDITO", "URL_AJUSTE_DISPUTA", "URL_DISPUTA", "URL_DOCUMENTO_NOTA_CREDITO"};

        // consulto la BD
        List<AutomatizacionesDTO> automatizaciones = new ArrayList<AutomatizacionesDTO>();

        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFRow headerRow = sheet.createRow(0);
        for (int j = 0; j < headers.length; ++j) {
            String header = headers[j];
            XSSFCell cell = headerRow.createCell(j);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(header);
        }

        XSSFRow dataRow = null;
        SimpleDateFormat fecha = new SimpleDateFormat(FormatConstans.FORMATO_FECHA_HORA);
        for (int i = 0; i < automatizaciones.size(); i++) {
            dataRow = sheet.createRow(i + 1);
            dataRow.createCell(NumeroConstans.CERO).setCellValue(automatizaciones.get(i).getIdAutomatizacion());
            dataRow.createCell(NumeroConstans.UNO).setCellValue(automatizaciones.get(i).getUrlOrden());
            dataRow.createCell(NumeroConstans.DOS).setCellValue(automatizaciones.get(i).getEstadoOrden());
            dataRow.createCell(NumeroConstans.TRES).setCellValue(automatizaciones.get(i).getCuentaFacturacion());
            dataRow.createCell(NumeroConstans.CUATRO).setCellValue(automatizaciones.get(i).getNumeroLinea());
            dataRow.createCell(NumeroConstans.CINCO).setCellValue(automatizaciones.get(i).getSimCard());
            dataRow.createCell(NumeroConstans.SEIS).setCellValue(automatizaciones.get(i).getFechaOrden());
            //dataRow.createCell(NumeroConstans.SIETE).setCellValue(automatizaciones.get(i).getCliente());
            dataRow.createCell(NumeroConstans.SIETE).setCellValue(automatizaciones.get(i).getNombreTransaccion());
            dataRow.createCell(NumeroConstans.OCHO).setCellValue(automatizaciones.get(i).getDatosEntrada());
            dataRow.createCell(NumeroConstans.NUEVE).setCellValue(automatizaciones.get(i).getUsuario());
            dataRow.createCell(NumeroConstans.DIEZ).setCellValue(automatizaciones.get(i).getPlan());
            dataRow.createCell(NumeroConstans.ONCE).setCellValue(automatizaciones.get(i).getTipoEquipo());
            dataRow.createCell(NumeroConstans.DOCE).setCellValue(automatizaciones.get(i).getNombreEquipoInterno());
            dataRow.createCell(NumeroConstans.TRECE)
                    .setCellValue(automatizaciones.get(i).getModeloEquipoInterno());
            dataRow.createCell(NumeroConstans.CATORCE).setCellValue(automatizaciones.get(i).getImeiEquipoInterno());
            dataRow.createCell(NumeroConstans.QUINCE).setCellValue(automatizaciones.get(i).getSloActivados());
            dataRow.createCell(NumeroConstans.DIECISEIS).setCellValue(automatizaciones.get(i).getPlanAntiguo());
            dataRow.createCell(NumeroConstans.DIECISIETE).setCellValue(automatizaciones.get(i).getPlanNuevo());
            dataRow.createCell(NumeroConstans.DIECIOCHO).setCellValue(automatizaciones.get(i).getSimcardAntigua());
            dataRow.createCell(NumeroConstans.DIECINUEVE).setCellValue(automatizaciones.get(i).getSimcardNueva());
            dataRow.createCell(NumeroConstans.VEINTE).setCellValue(automatizaciones.get(i).getNumeroAntiguo());
            dataRow.createCell(NumeroConstans.VEINTIUNO).setCellValue(automatizaciones.get(i).getNumeroNuevo());
            dataRow.createCell(NumeroConstans.VEINTIDOS).setCellValue(automatizaciones.get(i).getNombreEquipo());
            dataRow.createCell(NumeroConstans.VEINTITRES).setCellValue(automatizaciones.get(i).getModeloEquipo());
            dataRow.createCell(NumeroConstans.VEINTICUATRO).setCellValue(automatizaciones.get(i).getImeiEquipo());
            dataRow.createCell(NumeroConstans.VEINTICINCO).setCellValue(automatizaciones.get(i).getFlujo());
            dataRow.createCell(NumeroConstans.VEINTISEIS).setCellValue(automatizaciones.get(i).getIdEjecucion());
            dataRow.createCell(NumeroConstans.VEINTISIETE)
                    .setCellValue(automatizaciones.get(i).getNumeroCuentaCliente());
            dataRow.createCell(NumeroConstans.VEINTIOCHO)
                    .setCellValue(automatizaciones.get(i).getNumeroCuentaPrepago());
            dataRow.createCell(NumeroConstans.VEINTINUEVE)
                    .setCellValue(automatizaciones.get(i).getNumeroCuentaPostpago());
            dataRow.createCell(NumeroConstans.TREINTA)
                    .setCellValue(automatizaciones.get(i).getNumeroCentaPostAdvance());
            dataRow.createCell(NumeroConstans.TREINTAYUNO)
                    .setCellValue(automatizaciones.get(i).getCategoriaCliente());
            dataRow.createCell(NumeroConstans.TREINTAYDOS)
                    .setCellValue(automatizaciones.get(i).getIdentificacion());
            if(automatizaciones.get(i).getCliente() != null && !automatizaciones.get(i).getCliente().isEmpty()){
                dataRow.createCell(NumeroConstans.TREINTAYTRES)
                .setCellValue(automatizaciones.get(i).getCliente());   
            }
            else{
            dataRow.createCell(NumeroConstans.TREINTAYTRES)
                    .setCellValue(automatizaciones.get(i).getNombreCompleto());
            }
            dataRow.createCell(NumeroConstans.TREINTAYCUATRO).setCellValue(automatizaciones.get(i).getTipoCuenta());
            dataRow.createCell(NumeroConstans.TREINTAYCINCO).setCellValue(automatizaciones.get(i).getCiclo());
            dataRow.createCell(NumeroConstans.TREINTAYCEIS)
                    .setCellValue(automatizaciones.get(i).getFechaInicio() != null
                            ? fecha.format(automatizaciones.get(i).getFechaInicio()) : null);
            dataRow.createCell(NumeroConstans.TREINTAYSIETE)
                    .setCellValue(automatizaciones.get(i).getFechaInicio() != null
                            ? fecha.format(automatizaciones.get(i).getFechaFin()) : null);
            dataRow.createCell(NumeroConstans.TREINTAYOCHO).setCellValue(automatizaciones.get(i).getConfirmadoPor());
            dataRow.createCell(NumeroConstans.TREINTAYNUEVE).setCellValue(automatizaciones.get(i).getCanalDistribucion());
            dataRow.createCell(NumeroConstans.CUARENTA).setCellValue(automatizaciones.get(i).getFechaEnvio());
            dataRow.createCell(NumeroConstans.CUARENTAYUNO).setCellValue(automatizaciones.get(i).getBodegaDefecto());
            dataRow.createCell(NumeroConstans.CUARENTAYDOS).setCellValue(automatizaciones.get(i).getPlataforma());
            dataRow.createCell(NumeroConstans.CUARENTAYTRES).setCellValue(automatizaciones.get(i).getSloDesactivados());
            dataRow.createCell(NumeroConstans.CUARENTAYCUATRO).setCellValue(automatizaciones.get(i).getFacturaDisputa());
            dataRow.createCell(NumeroConstans.CUARENTAYCINCO).setCellValue(automatizaciones.get(i).getDisputaGenerada());
            dataRow.createCell(NumeroConstans.CUARENTAYSEIS).setCellValue(automatizaciones.get(i).getNumeroNotaCredito());
            dataRow.createCell(NumeroConstans.CUARENTAYSIETE).setCellValue(automatizaciones.get(i).getUrlAjusteDisputa());
            dataRow.createCell(NumeroConstans.CUARENTAYOCHO).setCellValue(automatizaciones.get(i).getUrlDisputa());
            dataRow.createCell(NumeroConstans.CUARENTAYNUEVE).setCellValue(automatizaciones.get(i).getUrlDocumentoNC());
        }

        // se crea una hoja por cada flujo
        List<AutomatizacionesDTO> automatizacionesHija = null;
        XSSFSheet sheetHija = null;
        
        // Trasnferencia beneficiario
        automatizacionesHija = filtrarAutomatizaciones(automatizaciones, FlujoEnum.TRANSFERENCIA_BENEFICIARIO);
        sheetHija = workbook.createSheet();
        workbook.setSheetName(NumeroConstans.UNO, "Trasnferencia Beneficiario");
        sheetHija = procesarHojaHija(sheetHija, workbook, automatizacionesHija);
        // Cambio sim
        automatizacionesHija = filtrarAutomatizaciones(automatizaciones, FlujoEnum.CAMBIO_SIM);
        sheetHija = workbook.createSheet();
        workbook.setSheetName(NumeroConstans.DOS, "Cambio Sim");
        sheetHija = procesarHojaHijaCambioSim(sheetHija, workbook, automatizacionesHija);
        // Cambio plan
        automatizacionesHija = filtrarAutomatizaciones(automatizaciones, FlujoEnum.CAMBIO_PLAN);
        sheetHija = workbook.createSheet();
        workbook.setSheetName(NumeroConstans.TRES, "Cambio Plan");
        sheetHija = procesarHojaHijaCambioPlan(sheetHija, workbook, automatizacionesHija);
        // Baja abonados
        automatizacionesHija = filtrarAutomatizaciones(automatizaciones, FlujoEnum.BAJA_ABONADO);
        sheetHija = workbook.createSheet();
        workbook.setSheetName(NumeroConstans.CUATRO, "Baja Abonados");
        sheetHija = procesarHojaHija(sheetHija, workbook, automatizacionesHija);
        // Facturas miselane
        automatizacionesHija = filtrarAutomatizaciones(automatizaciones, FlujoEnum.FACTURAS_MISCELANEAS);
        sheetHija = workbook.createSheet();
        workbook.setSheetName(NumeroConstans.CINCO, "Facturas Micelaneas");
        sheetHija = procesarHojaHija(sheetHija, workbook, automatizacionesHija);
        // Renovación equipo
        automatizacionesHija = filtrarAutomatizaciones(automatizaciones, FlujoEnum.RENOVACION_EQUIPO);
        sheetHija = workbook.createSheet();
        workbook.setSheetName(NumeroConstans.SEIS, "Renovación Equipo");
        sheetHija = procesarHojaHijaRenovacion(sheetHija, workbook, automatizacionesHija);
        // Suspensioón linea
        automatizacionesHija = filtrarAutomatizaciones(automatizaciones, FlujoEnum.SUSPENCION_LINEA);
        sheetHija = workbook.createSheet();
        workbook.setSheetName(NumeroConstans.SIETE, "Suspensión Linea");
        sheetHija = procesarHojaHijaConSim(sheetHija, workbook, automatizacionesHija);
        // Activación slo
        automatizacionesHija = filtrarAutomatizaciones(automatizaciones, FlujoEnum.ACTIVACION_SLO);
        sheetHija = workbook.createSheet();
        workbook.setSheetName(NumeroConstans.OCHO, "Activación SLO");
        sheetHija = procesarHojaHijaSlo(sheetHija, workbook, automatizacionesHija);
        // Altas
        automatizacionesHija = filtrarAutomatizaciones(automatizaciones, FlujoEnum.ALTAS_LINEAS);
        sheetHija = workbook.createSheet();
        workbook.setSheetName(NumeroConstans.NUEVE, "Altas Lineas");
        sheetHija = procesarHojaHijaAltas(sheetHija, workbook, automatizacionesHija);
        // crear cliente
        automatizacionesHija = filtrarAutomatizaciones(automatizaciones, FlujoEnum.CREAR_CLIENTE);
        sheetHija = workbook.createSheet();
        workbook.setSheetName(NumeroConstans.DIEZ, "Creación Cliente");
        sheetHija = procesarHojaHijaCliente(sheetHija, workbook, automatizacionesHija);
        // Cambio numero
        automatizacionesHija = filtrarAutomatizaciones(automatizaciones, FlujoEnum.CAMBIO_NUMERO);
        sheetHija = workbook.createSheet();
        workbook.setSheetName(NumeroConstans.ONCE, "Cambio Número");
        sheetHija = procesarHojaHijaNumero(sheetHija, workbook, automatizacionesHija);
        // Altas ssp
        automatizacionesHija = filtrarAutomatizaciones(automatizaciones, FlujoEnum.ALTAS_LINEAS_SSP);
        sheetHija = workbook.createSheet();
        workbook.setSheetName(NumeroConstans.DOCE, "Altas SSP");
        sheetHija = procesarHojaHijaAltaSsp(sheetHija, workbook, automatizacionesHija);
        // 
        automatizacionesHija = filtrarAutomatizaciones(automatizaciones, FlujoEnum.CREAR_CLIENTE_EMPRESARIAL);
        sheetHija = workbook.createSheet();
        workbook.setSheetName(NumeroConstans.TRECE, "Cliente Empresarial");
        sheetHija = procesarHojaHijaClienteEmpresarial(sheetHija, workbook, automatizacionesHija);

        // generación disputas y nota crédito
        automatizacionesHija = filtrarAutomatizaciones(automatizaciones, FlujoEnum.GENERACION_DISPUTAS_Y_NOTACREDITO);
        sheetHija = workbook.createSheet();
        workbook.setSheetName(NumeroConstans.CATORCE, "Disputas y Nota Crédito");
        sheetHija = procesarHojaHijaDisputasNotaCredito(sheetHija, workbook, automatizacionesHija);
                
        
        
        // Resize all columns to fit the content size
        for (int i = 0; i < 60; i++) {
            sheet.autoSizeColumn(i);
        }
        String ruta = properties.getProperty("rutaArchivos") + getFechaSegundosMiligesundos() + "\\"
                + properties.getProperty("nombreXls") + ".xlsx";
        FileOutputStream file;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            file = new FileOutputStream(ruta);
            workbook.write(file);
            file.close();
        } catch (FileNotFoundException e) {
            LOGGER.error("No se encontro el archivo ", e);
        } catch (IOException e) {
            LOGGER.error("IOException ", e);
        }
        return new File(ruta);

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
     * @param sheetHija
     * @param workbook
     * @param automatizaciones
     * @return
     */
    private static XSSFSheet procesarHojaHijaAltaSsp(XSSFSheet sheetHija, XSSFWorkbook workbook,
            List<AutomatizacionesDTO> automatizaciones) {

        String[] headersHija = new String[] { "ID_AUTOMATIZACION", "URL_ORDEN", "NOMBRE_TRANSACCION", "DATOS_ENTRADA", "USUARIO", "ID_EJECUCION", "FECHA_INICIO", "FECHA_FIN","URL_ERROR" };
        
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFRow headerRow = sheetHija.createRow(0);
        for (int j = 0; j < headersHija.length; ++j) {
            String header = headersHija[j];
            XSSFCell cell = headerRow.createCell(j);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(header);
        }
        XSSFRow dataRow = null;
        SimpleDateFormat fecha = new SimpleDateFormat(FormatConstans.FORMATO_FECHA_HORA);
        for (int i = 0; i < automatizaciones.size(); i++) {
            dataRow = sheetHija.createRow(i + 1);
            dataRow.createCell(NumeroConstans.CERO).setCellValue(automatizaciones.get(i).getIdAutomatizacion());
            dataRow.createCell(NumeroConstans.UNO).setCellValue(automatizaciones.get(i).getUrlOrden());
            dataRow.createCell(NumeroConstans.DOS).setCellValue(automatizaciones.get(i).getNombreTransaccion());
            dataRow.createCell(NumeroConstans.TRES).setCellValue(automatizaciones.get(i).getDatosEntrada());
            dataRow.createCell(NumeroConstans.CUATRO).setCellValue(automatizaciones.get(i).getUsuario());
            dataRow.createCell(NumeroConstans.CINCO).setCellValue(automatizaciones.get(i).getIdEjecucion());
            dataRow.createCell(NumeroConstans.SEIS).setCellValue(automatizaciones.get(i).getFechaInicio() != null ? fecha.format(automatizaciones.get(i).getFechaInicio()) : null);
            dataRow.createCell(NumeroConstans.SIETE).setCellValue(automatizaciones.get(i).getFechaFin() != null ? fecha.format(automatizaciones.get(i).getFechaFin()) : null);
            dataRow.createCell(NumeroConstans.OCHO).setCellValue(automatizaciones.get(i).getUrlError());
        }

        // Resize all columns to fit the content size
        for (int i = 0; i < 40; i++) {
            sheetHija.autoSizeColumn(i);
        }
        return sheetHija;

    
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
     * @param sheetHija
     * @param workbook
     * @param automatizaciones
     * @return
     */
    private static XSSFSheet procesarHojaHijaCliente(XSSFSheet sheetHija, XSSFWorkbook workbook,
            List<AutomatizacionesDTO> automatizaciones) {

        String[] headersHija = new String[] { "ID_AUTOMATIZACION", "NOMBRE_TRANSACCION", "DATOS_ENTRADA", "USUARIO",
                "ID_EJECUCION","NUMERO_CUENTA_CLIENTE","NUMERO_CUENTA_PREPAGO", "NUMERO_CUENTA_POST_PAGO","NUMERO_CUENTA_POST_ADVANCED",
                "CATEGORIA_CLIENTE","IDENTIFICACION","NOMBRE_COMPLETO","TIPO_CUENTA","CICLO"
                ,"FECHA_INICIO", "FECHA_FIN", "URL_ANTES_ERROR" , "URL_ERROR" };
        
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFRow headerRow = sheetHija.createRow(0);
        for (int j = 0; j < headersHija.length; ++j) {
            String header = headersHija[j];
            XSSFCell cell = headerRow.createCell(j);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(header);
        }
        XSSFRow dataRow = null;
        SimpleDateFormat fecha = new SimpleDateFormat(FormatConstans.FORMATO_FECHA_HORA);
        for (int i = 0; i < automatizaciones.size(); i++) {
            dataRow = sheetHija.createRow(i + 1);
            dataRow.createCell(NumeroConstans.CERO).setCellValue(automatizaciones.get(i).getIdAutomatizacion());
            dataRow.createCell(NumeroConstans.UNO).setCellValue(automatizaciones.get(i).getNombreTransaccion());
            dataRow.createCell(NumeroConstans.DOS).setCellValue(automatizaciones.get(i).getDatosEntrada());
            dataRow.createCell(NumeroConstans.TRES).setCellValue(automatizaciones.get(i).getUsuario());
            dataRow.createCell(NumeroConstans.CUATRO).setCellValue(automatizaciones.get(i).getIdEjecucion());
            dataRow.createCell(NumeroConstans.CINCO).setCellValue(automatizaciones.get(i).getNumeroCuentaCliente());
            dataRow.createCell(NumeroConstans.SEIS).setCellValue(automatizaciones.get(i).getNumeroCuentaPrepago());
            dataRow.createCell(NumeroConstans.SIETE).setCellValue(automatizaciones.get(i).getNumeroCuentaPostpago());
            dataRow.createCell(NumeroConstans.OCHO).setCellValue(automatizaciones.get(i).getNumeroCentaPostAdvance());
            dataRow.createCell(NumeroConstans.NUEVE).setCellValue(automatizaciones.get(i).getCategoriaCliente());
            dataRow.createCell(NumeroConstans.DIEZ).setCellValue(automatizaciones.get(i).getIdentificacion());
            dataRow.createCell(NumeroConstans.ONCE).setCellValue(automatizaciones.get(i).getNombreCompleto());
            dataRow.createCell(NumeroConstans.DOCE).setCellValue(automatizaciones.get(i).getTipoCuenta());
            dataRow.createCell(NumeroConstans.TRECE).setCellValue(automatizaciones.get(i).getCiclo());
            dataRow.createCell(NumeroConstans.CATORCE).setCellValue(automatizaciones.get(i).getFechaInicio() != null ? fecha.format(automatizaciones.get(i).getFechaInicio()) : null);
            dataRow.createCell(NumeroConstans.QUINCE).setCellValue(automatizaciones.get(i).getFechaFin() != null ? fecha.format(automatizaciones.get(i).getFechaFin()) : null);
            dataRow.createCell(NumeroConstans.DIECISEIS).setCellValue(automatizaciones.get(i).getUrlAntesError());
            dataRow.createCell(NumeroConstans.DIECISIETE).setCellValue(automatizaciones.get(i).getUrlError());
        }

        // Resize all columns to fit the content size
        for (int i = 0; i < 40; i++) {
            sheetHija.autoSizeColumn(i);
        }
        return sheetHija;

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
     * @param sheetHija
     * @param workbook
     * @param automatizaciones
     * @return
     */
    private static XSSFSheet procesarHojaHijaAltas(XSSFSheet sheetHija, XSSFWorkbook workbook,
            List<AutomatizacionesDTO> automatizaciones) {

        String[] headersHija = new String[] { "ID_AUTOMATIZACION", "URL_ORDEN", "ESTADO_ORDEN", "CUENTA_FACTURACION",
                "NUMERO_LINEA","SIM_CARD", "FECHA_ORDEN", "CLIENTE", "NOMBRE_TRANSACCION", "DATOS_ENTRADA", "USUARIO",
                "PLAN","TIPO_EQUIPO","NOMBRE_EQUIPO_INTERNO",
                "MODELO_EQUIPO_INTERNO","IMEI_EQUIPO_INTERNO","NOMBRE_EQUIPO","MODELO_EQUIPO","IMEI_EQUIPO",
                "ID_EJECUCION", "FECHA_INICIO", "FECHA_FIN","CONFIRMADO_POR","CANAL_DISTRIBUCION","FECHA_ENVIO","BODEGA_DEFECTO", "URL_ANTES_ERROR" , "URL_ERROR" };
        
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFRow headerRow = sheetHija.createRow(0);
        for (int j = 0; j < headersHija.length; ++j) {
            String header = headersHija[j];
            XSSFCell cell = headerRow.createCell(j);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(header);
        }
        XSSFRow dataRow = null;
        SimpleDateFormat fecha = new SimpleDateFormat(FormatConstans.FORMATO_FECHA_HORA);
        int j=0;
        for (int i = 0; i < automatizaciones.size(); i++) {
            dataRow = sheetHija.createRow(i + 1);
            j=0;
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getIdAutomatizacion());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getUrlOrden());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getEstadoOrden());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getCuentaFacturacion());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getNumeroLinea());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getSimCard());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getFechaOrden());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getCliente());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getNombreTransaccion());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getDatosEntrada());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getUsuario());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getPlan());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getTipoEquipo());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getNombreEquipoInterno());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getModeloEquipoInterno());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getImeiEquipoInterno());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getNombreEquipo());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getModeloEquipo());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getImeiEquipo());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getIdEjecucion());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getFechaInicio() != null ? fecha.format(automatizaciones.get(i).getFechaInicio()) : null);
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getFechaFin() != null ? fecha.format(automatizaciones.get(i).getFechaFin()) : null);
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getConfirmadoPor());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getCanalDistribucion());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getFechaEnvio());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getBodegaDefecto());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getUrlAntesError());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getUrlError());
        }

        // Resize all columns to fit the content size
        for (int i = 0; i < 40; i++) {
            sheetHija.autoSizeColumn(i);
        }
        return sheetHija;

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
     * @param sheetHija
     * @param workbook
     * @param automatizaciones
     * @return
     */
    private static XSSFSheet procesarHojaHijaRenovacion(XSSFSheet sheetHija, XSSFWorkbook workbook,
            List<AutomatizacionesDTO> automatizaciones) {

        String[] headersHija = new String[] { "ID_AUTOMATIZACION", "URL_ORDEN", "ESTADO_ORDEN", "CUENTA_FACTURACION",
                "NUMERO_LINEA", "FECHA_ORDEN", "CLIENTE", "NOMBRE_TRANSACCION", "DATOS_ENTRADA", "USUARIO","NOMBRE_EQUIPO_INTERNO","MODELO_EQUIPO_INTERNO",
                "IMEI_EQUIPO_INTERNO","NOMBRE_EQUIPO",   "MODELO_EQUIPO",   "IMEI_EQUIPO EJECUCION",
                "ID_EJECUCION", "FECHA_INICIO", "FECHA_FIN","CONFIRMADO_POR","CANAL_DISTRIBUCION","FECHA_ENVIO","BODEGA_DEFECTO", "URL_ANTES_ERROR" , "URL_ERROR" };
        
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFRow headerRow = sheetHija.createRow(0);
        for (int j = 0; j < headersHija.length; ++j) {
            String header = headersHija[j];
            XSSFCell cell = headerRow.createCell(j);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(header);
        }
        XSSFRow dataRow = null;
        SimpleDateFormat fecha = new SimpleDateFormat(FormatConstans.FORMATO_FECHA_HORA);
        for (int i = 0; i < automatizaciones.size(); i++) {
            dataRow = sheetHija.createRow(i + 1);
            dataRow.createCell(NumeroConstans.CERO).setCellValue(automatizaciones.get(i).getIdAutomatizacion());
            dataRow.createCell(NumeroConstans.UNO).setCellValue(automatizaciones.get(i).getUrlOrden());
            dataRow.createCell(NumeroConstans.DOS).setCellValue(automatizaciones.get(i).getEstadoOrden());
            dataRow.createCell(NumeroConstans.TRES).setCellValue(automatizaciones.get(i).getCuentaFacturacion());
            dataRow.createCell(NumeroConstans.CUATRO).setCellValue(automatizaciones.get(i).getNumeroLinea());
            dataRow.createCell(NumeroConstans.CINCO).setCellValue(automatizaciones.get(i).getFechaOrden());
            dataRow.createCell(NumeroConstans.SEIS).setCellValue(automatizaciones.get(i).getCliente());
            dataRow.createCell(NumeroConstans.SIETE).setCellValue(automatizaciones.get(i).getNombreTransaccion());
            dataRow.createCell(NumeroConstans.OCHO).setCellValue(automatizaciones.get(i).getDatosEntrada());
            dataRow.createCell(NumeroConstans.NUEVE).setCellValue(automatizaciones.get(i).getUsuario());
            dataRow.createCell(NumeroConstans.DIEZ).setCellValue(automatizaciones.get(i).getUsuario());
            dataRow.createCell(NumeroConstans.ONCE).setCellValue(automatizaciones.get(i).getNombreEquipoInterno());
            dataRow.createCell(NumeroConstans.DOCE).setCellValue(automatizaciones.get(i).getModeloEquipoInterno());
            dataRow.createCell(NumeroConstans.TRECE).setCellValue(automatizaciones.get(i).getImeiEquipoInterno());
            dataRow.createCell(NumeroConstans.CATORCE).setCellValue(automatizaciones.get(i).getNombreEquipo());
            dataRow.createCell(NumeroConstans.QUINCE).setCellValue(automatizaciones.get(i).getModeloEquipo());
            dataRow.createCell(NumeroConstans.DIECISEIS).setCellValue(automatizaciones.get(i).getImeiEquipo());
            dataRow.createCell(NumeroConstans.DIECISIETE).setCellValue(automatizaciones.get(i).getIdEjecucion());
            dataRow.createCell(NumeroConstans.DIECIOCHO).setCellValue(automatizaciones.get(i).getFechaInicio() != null ? fecha.format(automatizaciones.get(i).getFechaInicio()) : null);
            dataRow.createCell(NumeroConstans.DIECINUEVE).setCellValue(automatizaciones.get(i).getFechaFin() != null ? fecha.format(automatizaciones.get(i).getFechaFin()) : null);
            dataRow.createCell(NumeroConstans.VEINTE).setCellValue(automatizaciones.get(i).getConfirmadoPor());
            dataRow.createCell(NumeroConstans.VEINTIUNO).setCellValue(automatizaciones.get(i).getCanalDistribucion());
            dataRow.createCell(NumeroConstans.VEINTIDOS).setCellValue(automatizaciones.get(i).getFechaEnvio());
            dataRow.createCell(NumeroConstans.VEINTITRES).setCellValue(automatizaciones.get(i).getBodegaDefecto());
            dataRow.createCell(NumeroConstans.VEINTICUATRO).setCellValue(automatizaciones.get(i).getUrlAntesError());
            dataRow.createCell(NumeroConstans.VEINTICINCO).setCellValue(automatizaciones.get(i).getUrlError());
        }

        // Resize all columns to fit the content size
        for (int i = 0; i < 40; i++) {
            sheetHija.autoSizeColumn(i);
        }
        return sheetHija;

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
     * @param sheetHija
     * @param workbook
     * @param automatizaciones
     * @return
     */
    private static XSSFSheet procesarHojaHijaNumero(XSSFSheet sheetHija, XSSFWorkbook workbook,
            List<AutomatizacionesDTO> automatizaciones) {

        String[] headersHija = new String[] { "ID_AUTOMATIZACION", "URL_ORDEN", "ESTADO_ORDEN", "CUENTA_FACTURACION",
                "NUMERO_LINEA", "FECHA_ORDEN", "CLIENTE", "NOMBRE_TRANSACCION", "DATOS_ENTRADA", "USUARIO","NUMERO_ANTIGUO","NUMERO_NUEVO",
                "ID_EJECUCION", "FECHA_INICIO", "FECHA_FIN","CONFIRMADO_POR","CANAL_DISTRIBUCION","FECHA_ENVIO","BODEGA_DEFECTO", "URL_ANTES_ERROR" , "URL_ERROR" };
        
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFRow headerRow = sheetHija.createRow(0);
        for (int j = 0; j < headersHija.length; ++j) {
            String header = headersHija[j];
            XSSFCell cell = headerRow.createCell(j);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(header);
        }
        XSSFRow dataRow = null;
        SimpleDateFormat fecha = new SimpleDateFormat(FormatConstans.FORMATO_FECHA_HORA);
        for (int i = 0; i < automatizaciones.size(); i++) {
            dataRow = sheetHija.createRow(i + 1);
            dataRow.createCell(NumeroConstans.CERO).setCellValue(automatizaciones.get(i).getIdAutomatizacion());
            dataRow.createCell(NumeroConstans.UNO).setCellValue(automatizaciones.get(i).getUrlOrden());
            dataRow.createCell(NumeroConstans.DOS).setCellValue(automatizaciones.get(i).getEstadoOrden());
            dataRow.createCell(NumeroConstans.TRES).setCellValue(automatizaciones.get(i).getCuentaFacturacion());
            dataRow.createCell(NumeroConstans.CUATRO).setCellValue(automatizaciones.get(i).getNumeroLinea());
            dataRow.createCell(NumeroConstans.CINCO).setCellValue(automatizaciones.get(i).getFechaOrden());
            dataRow.createCell(NumeroConstans.SEIS).setCellValue(automatizaciones.get(i).getCliente());
            dataRow.createCell(NumeroConstans.SIETE).setCellValue(automatizaciones.get(i).getNombreTransaccion());
            dataRow.createCell(NumeroConstans.OCHO).setCellValue(automatizaciones.get(i).getDatosEntrada());
            dataRow.createCell(NumeroConstans.NUEVE).setCellValue(automatizaciones.get(i).getUsuario());
            dataRow.createCell(NumeroConstans.DIEZ).setCellValue(automatizaciones.get(i).getNumeroAntiguo());
            dataRow.createCell(NumeroConstans.ONCE).setCellValue(automatizaciones.get(i).getNumeroNuevo());
            dataRow.createCell(NumeroConstans.DOCE).setCellValue(automatizaciones.get(i).getIdEjecucion());
            dataRow.createCell(NumeroConstans.TRECE).setCellValue(automatizaciones.get(i).getFechaInicio() != null ? fecha.format(automatizaciones.get(i).getFechaInicio()) : null);
            dataRow.createCell(NumeroConstans.CATORCE).setCellValue(automatizaciones.get(i).getFechaFin() != null ? fecha.format(automatizaciones.get(i).getFechaFin()) : null);
            dataRow.createCell(NumeroConstans.QUINCE).setCellValue(automatizaciones.get(i).getConfirmadoPor());
            dataRow.createCell(NumeroConstans.DIECISEIS).setCellValue(automatizaciones.get(i).getCanalDistribucion());
            dataRow.createCell(NumeroConstans.DIECISIETE).setCellValue(automatizaciones.get(i).getFechaEnvio());
            dataRow.createCell(NumeroConstans.DIECIOCHO).setCellValue(automatizaciones.get(i).getBodegaDefecto());
            dataRow.createCell(NumeroConstans.DIECINUEVE).setCellValue(automatizaciones.get(i).getUrlAntesError());
            dataRow.createCell(NumeroConstans.VEINTE).setCellValue(automatizaciones.get(i).getUrlError());
        }

        // Resize all columns to fit the content size
        for (int i = 0; i < 40; i++) {
            sheetHija.autoSizeColumn(i);
        }
        return sheetHija;

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
     * @param sheetHija
     * @param workbook
     * @param automatizaciones
     * @return
     */
    private static XSSFSheet procesarHojaHijaCambioPlan(XSSFSheet sheetHija, XSSFWorkbook workbook,
            List<AutomatizacionesDTO> automatizaciones) {

        String[] headersHija = new String[] { "ID_AUTOMATIZACION", "URL_ORDEN", "ESTADO_ORDEN", "CUENTA_FACTURACION",
                "NUMERO_LINEA", "FECHA_ORDEN", "CLIENTE", "NOMBRE_TRANSACCION", "DATOS_ENTRADA", "USUARIO","PLAN_ANTIGUO","PLAN_NUEVO",
                "ID_EJECUCION", "FECHA_INICIO", "FECHA_FIN","CONFIRMADO_POR","CANAL_DISTRIBUCION","FECHA_ENVIO","BODEGA_DEFECTO", "URL_ANTES_ERROR" ,"URL_ERROR" };
        
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFRow headerRow = sheetHija.createRow(0);
        for (int j = 0; j < headersHija.length; ++j) {
            String header = headersHija[j];
            XSSFCell cell = headerRow.createCell(j);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(header);
        }
        XSSFRow dataRow = null;
        SimpleDateFormat fecha = new SimpleDateFormat(FormatConstans.FORMATO_FECHA_HORA);
        for (int i = 0; i < automatizaciones.size(); i++) {
            dataRow = sheetHija.createRow(i + 1);
            dataRow.createCell(NumeroConstans.CERO).setCellValue(automatizaciones.get(i).getIdAutomatizacion());
            dataRow.createCell(NumeroConstans.UNO).setCellValue(automatizaciones.get(i).getUrlOrden());
            dataRow.createCell(NumeroConstans.DOS).setCellValue(automatizaciones.get(i).getEstadoOrden());
            dataRow.createCell(NumeroConstans.TRES).setCellValue(automatizaciones.get(i).getCuentaFacturacion());
            dataRow.createCell(NumeroConstans.CUATRO).setCellValue(automatizaciones.get(i).getNumeroLinea());
            dataRow.createCell(NumeroConstans.CINCO).setCellValue(automatizaciones.get(i).getFechaOrden());
            dataRow.createCell(NumeroConstans.SEIS).setCellValue(automatizaciones.get(i).getCliente());
            dataRow.createCell(NumeroConstans.SIETE).setCellValue(automatizaciones.get(i).getNombreTransaccion());
            dataRow.createCell(NumeroConstans.OCHO).setCellValue(automatizaciones.get(i).getDatosEntrada());
            dataRow.createCell(NumeroConstans.NUEVE).setCellValue(automatizaciones.get(i).getUsuario());
            dataRow.createCell(NumeroConstans.DIEZ).setCellValue(automatizaciones.get(i).getPlanAntiguo());
            dataRow.createCell(NumeroConstans.ONCE).setCellValue(automatizaciones.get(i).getPlanNuevo());
            dataRow.createCell(NumeroConstans.DOCE).setCellValue(automatizaciones.get(i).getIdEjecucion());
            dataRow.createCell(NumeroConstans.TRECE).setCellValue(automatizaciones.get(i).getFechaInicio() != null ? fecha.format(automatizaciones.get(i).getFechaInicio()) : null);
            dataRow.createCell(NumeroConstans.CATORCE).setCellValue(automatizaciones.get(i).getFechaFin() != null ? fecha.format(automatizaciones.get(i).getFechaFin()) : null);
            dataRow.createCell(NumeroConstans.QUINCE).setCellValue(automatizaciones.get(i).getConfirmadoPor());
            dataRow.createCell(NumeroConstans.DIECISEIS).setCellValue(automatizaciones.get(i).getCanalDistribucion());
            dataRow.createCell(NumeroConstans.DIECISIETE).setCellValue(automatizaciones.get(i).getFechaEnvio());
            dataRow.createCell(NumeroConstans.DIECIOCHO).setCellValue(automatizaciones.get(i).getBodegaDefecto());
            dataRow.createCell(NumeroConstans.DIECINUEVE).setCellValue(automatizaciones.get(i).getUrlAntesError());
            dataRow.createCell(NumeroConstans.VEINTE).setCellValue(automatizaciones.get(i).getUrlError());
            
        }

        // Resize all columns to fit the content size
        for (int i = 0; i < 40; i++) {
            sheetHija.autoSizeColumn(i);
        }
        return sheetHija;

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
     * @param sheetHija
     * @param workbook
     * @param automatizaciones
     * @return
     */
    private static XSSFSheet procesarHojaHijaCambioSim(XSSFSheet sheetHija, XSSFWorkbook workbook,
            List<AutomatizacionesDTO> automatizaciones) {

        String[] headersHija = new String[] { "ID_AUTOMATIZACION", "URL_ORDEN", "ESTADO_ORDEN", "CUENTA_FACTURACION",
                "NUMERO_LINEA", "FECHA_ORDEN", "CLIENTE", "NOMBRE_TRANSACCION", "DATOS_ENTRADA", "USUARIO","SIMCARD_ANTIGUA","SIMCARD_NUEVA",
                "ID_EJECUCION", "FECHA_INICIO", "FECHA_FIN","CONFIRMADO_POR","CANAL_DISTRIBUCION","FECHA_ENVIO","BODEGA_DEFECTO", "URL_ANTES_ERROR" , "URL_ERROR" };
        
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFRow headerRow = sheetHija.createRow(0);
        for (int j = 0; j < headersHija.length; ++j) {
            String header = headersHija[j];
            XSSFCell cell = headerRow.createCell(j);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(header);
        }
        XSSFRow dataRow = null;
        SimpleDateFormat fecha = new SimpleDateFormat(FormatConstans.FORMATO_FECHA_HORA);
        for (int i = 0; i < automatizaciones.size(); i++) {
            dataRow = sheetHija.createRow(i + 1);
            dataRow.createCell(NumeroConstans.CERO).setCellValue(automatizaciones.get(i).getIdAutomatizacion());
            dataRow.createCell(NumeroConstans.UNO).setCellValue(automatizaciones.get(i).getUrlOrden());
            dataRow.createCell(NumeroConstans.DOS).setCellValue(automatizaciones.get(i).getEstadoOrden());
            dataRow.createCell(NumeroConstans.TRES).setCellValue(automatizaciones.get(i).getCuentaFacturacion());
            dataRow.createCell(NumeroConstans.CUATRO).setCellValue(automatizaciones.get(i).getNumeroLinea());
            dataRow.createCell(NumeroConstans.CINCO).setCellValue(automatizaciones.get(i).getFechaOrden());
            dataRow.createCell(NumeroConstans.SEIS).setCellValue(automatizaciones.get(i).getCliente());
            dataRow.createCell(NumeroConstans.SIETE).setCellValue(automatizaciones.get(i).getNombreTransaccion());
            dataRow.createCell(NumeroConstans.OCHO).setCellValue(automatizaciones.get(i).getDatosEntrada());
            dataRow.createCell(NumeroConstans.NUEVE).setCellValue(automatizaciones.get(i).getUsuario());
            dataRow.createCell(NumeroConstans.DIEZ).setCellValue(automatizaciones.get(i).getSimcardAntigua());
            dataRow.createCell(NumeroConstans.ONCE).setCellValue(automatizaciones.get(i).getSimcardNueva());
            dataRow.createCell(NumeroConstans.DOCE).setCellValue(automatizaciones.get(i).getIdEjecucion());
            dataRow.createCell(NumeroConstans.TRECE).setCellValue(automatizaciones.get(i).getFechaInicio() != null ? fecha.format(automatizaciones.get(i).getFechaInicio()) : null);
            dataRow.createCell(NumeroConstans.CATORCE).setCellValue(automatizaciones.get(i).getFechaFin() != null ? fecha.format(automatizaciones.get(i).getFechaFin()) : null);
            dataRow.createCell(NumeroConstans.QUINCE).setCellValue(automatizaciones.get(i).getConfirmadoPor());
            dataRow.createCell(NumeroConstans.DIECISEIS).setCellValue(automatizaciones.get(i).getCanalDistribucion());
            dataRow.createCell(NumeroConstans.DIECISIETE).setCellValue(automatizaciones.get(i).getFechaEnvio());
            dataRow.createCell(NumeroConstans.DIECIOCHO).setCellValue(automatizaciones.get(i).getBodegaDefecto());
            dataRow.createCell(NumeroConstans.DIECINUEVE).setCellValue(automatizaciones.get(i).getUrlAntesError());
            dataRow.createCell(NumeroConstans.VEINTE).setCellValue(automatizaciones.get(i).getUrlError());
        }

        // Resize all columns to fit the content size
        for (int i = 0; i < 40; i++) {
            sheetHija.autoSizeColumn(i);
        }
        return sheetHija;

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
     * @param sheetHija
     * @param workbook
     * @param automatizaciones
     * @return
     */
    private static XSSFSheet procesarHojaHijaSlo(XSSFSheet sheetHija, XSSFWorkbook workbook,
            List<AutomatizacionesDTO> automatizaciones) {

        String[] headersHija = new String[] { "ID_AUTOMATIZACION", "URL_ORDEN", "ESTADO_ORDEN", "CUENTA_FACTURACION",
                "NUMERO_LINEA","SIM_CARD", "FECHA_ORDEN", "CLIENTE", "NOMBRE_TRANSACCION", "DATOS_ENTRADA", "USUARIO","PLAN","SLO_ACTIVADOS",
                "ID_EJECUCION", "FECHA_INICIO", "FECHA_FIN","CONFIRMADO_POR","CANAL_DISTRIBUCION","FECHA_ENVIO","BODEGA_DEFECTO", "URL_ANTES_ERROR" , "URL_ERROR", "SLO_DESACTIVADOS" };
        
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFRow headerRow = sheetHija.createRow(0);
        for (int j = 0; j < headersHija.length; ++j) {
            String header = headersHija[j];
            XSSFCell cell = headerRow.createCell(j);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(header);
        }
        XSSFRow dataRow = null;
        SimpleDateFormat fecha = new SimpleDateFormat(FormatConstans.FORMATO_FECHA_HORA);
        for (int i = 0; i < automatizaciones.size(); i++) {
            dataRow = sheetHija.createRow(i + 1);
            dataRow.createCell(NumeroConstans.CERO).setCellValue(automatizaciones.get(i).getIdAutomatizacion());
            dataRow.createCell(NumeroConstans.UNO).setCellValue(automatizaciones.get(i).getUrlOrden());
            dataRow.createCell(NumeroConstans.DOS).setCellValue(automatizaciones.get(i).getEstadoOrden());
            dataRow.createCell(NumeroConstans.TRES).setCellValue(automatizaciones.get(i).getCuentaFacturacion());
            dataRow.createCell(NumeroConstans.CUATRO).setCellValue(automatizaciones.get(i).getNumeroLinea());
            dataRow.createCell(NumeroConstans.CINCO).setCellValue(automatizaciones.get(i).getSimCard());
            dataRow.createCell(NumeroConstans.SEIS).setCellValue(automatizaciones.get(i).getFechaOrden());
            dataRow.createCell(NumeroConstans.SIETE).setCellValue(automatizaciones.get(i).getCliente());
            dataRow.createCell(NumeroConstans.OCHO).setCellValue(automatizaciones.get(i).getNombreTransaccion());
            dataRow.createCell(NumeroConstans.NUEVE).setCellValue(automatizaciones.get(i).getDatosEntrada());
            dataRow.createCell(NumeroConstans.DIEZ).setCellValue(automatizaciones.get(i).getUsuario());
            dataRow.createCell(NumeroConstans.ONCE).setCellValue(automatizaciones.get(i).getPlan());
            dataRow.createCell(NumeroConstans.DOCE).setCellValue(automatizaciones.get(i).getSloActivados());
            dataRow.createCell(NumeroConstans.TRECE).setCellValue(automatizaciones.get(i).getIdEjecucion());
            dataRow.createCell(NumeroConstans.CATORCE).setCellValue(automatizaciones.get(i).getFechaInicio() != null ? fecha.format(automatizaciones.get(i).getFechaInicio()) : null);
            dataRow.createCell(NumeroConstans.QUINCE).setCellValue(automatizaciones.get(i).getFechaFin() != null ? fecha.format(automatizaciones.get(i).getFechaFin()) : null);
            dataRow.createCell(NumeroConstans.DIECISEIS).setCellValue(automatizaciones.get(i).getConfirmadoPor());
            dataRow.createCell(NumeroConstans.DIECISIETE).setCellValue(automatizaciones.get(i).getCanalDistribucion());
            dataRow.createCell(NumeroConstans.DIECIOCHO).setCellValue(automatizaciones.get(i).getFechaEnvio());
            dataRow.createCell(NumeroConstans.DIECINUEVE).setCellValue(automatizaciones.get(i).getBodegaDefecto());
            dataRow.createCell(NumeroConstans.VEINTE).setCellValue(automatizaciones.get(i).getUrlAntesError());
            dataRow.createCell(NumeroConstans.VEINTIUNO).setCellValue(automatizaciones.get(i).getUrlError());
            dataRow.createCell(NumeroConstans.VEINTIDOS).setCellValue(automatizaciones.get(i).getSloDesactivados());
        }

        // Resize all columns to fit the content size
        for (int i = 0; i < 40; i++) {
            sheetHija.autoSizeColumn(i);
        }
        return sheetHija;

    }

    private static XSSFSheet procesarHojaHijaConSim(XSSFSheet sheetHija, XSSFWorkbook workbook,
            List<AutomatizacionesDTO> automatizaciones) {

        String[] headersHija = new String[] { "ID_AUTOMATIZACION", "URL_ORDEN", "ESTADO_ORDEN", "CUENTA_FACTURACION",
                "NUMERO_LINEA","SIM_CARD", "FECHA_ORDEN", "CLIENTE", "NOMBRE_TRANSACCION", "DATOS_ENTRADA", "USUARIO",
                "ID_EJECUCION", "FECHA_INICIO", "FECHA_FIN","CONFIRMADO_POR","CANAL_DISTRIBUCION","FECHA_ENVIO","BODEGA_DEFECTO", "URL_ANTES_ERROR" , "URL_ERROR" };
        
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFRow headerRow = sheetHija.createRow(0);
        for (int j = 0; j < headersHija.length; ++j) {
            String header = headersHija[j];
            XSSFCell cell = headerRow.createCell(j);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(header);
        }
        XSSFRow dataRow = null;
        SimpleDateFormat fecha = new SimpleDateFormat(FormatConstans.FORMATO_FECHA_HORA);
        for (int i = 0; i < automatizaciones.size(); i++) {
            dataRow = sheetHija.createRow(i + 1);
            dataRow.createCell(NumeroConstans.CERO).setCellValue(automatizaciones.get(i).getIdAutomatizacion());
            dataRow.createCell(NumeroConstans.UNO).setCellValue(automatizaciones.get(i).getUrlOrden());
            dataRow.createCell(NumeroConstans.DOS).setCellValue(automatizaciones.get(i).getEstadoOrden());
            dataRow.createCell(NumeroConstans.TRES).setCellValue(automatizaciones.get(i).getCuentaFacturacion());
            dataRow.createCell(NumeroConstans.CUATRO).setCellValue(automatizaciones.get(i).getNumeroLinea());
            dataRow.createCell(NumeroConstans.CINCO).setCellValue(automatizaciones.get(i).getSimCard());
            dataRow.createCell(NumeroConstans.SEIS).setCellValue(automatizaciones.get(i).getFechaOrden());
            dataRow.createCell(NumeroConstans.SIETE).setCellValue(automatizaciones.get(i).getCliente());
            dataRow.createCell(NumeroConstans.OCHO).setCellValue(automatizaciones.get(i).getNombreTransaccion());
            dataRow.createCell(NumeroConstans.NUEVE).setCellValue(automatizaciones.get(i).getDatosEntrada());
            dataRow.createCell(NumeroConstans.DIEZ).setCellValue(automatizaciones.get(i).getUsuario());
            dataRow.createCell(NumeroConstans.ONCE).setCellValue(automatizaciones.get(i).getIdEjecucion());
            dataRow.createCell(NumeroConstans.DOCE).setCellValue(automatizaciones.get(i).getFechaInicio() != null ? fecha.format(automatizaciones.get(i).getFechaInicio()) : null);
            dataRow.createCell(NumeroConstans.TRECE).setCellValue(automatizaciones.get(i).getFechaFin() != null ? fecha.format(automatizaciones.get(i).getFechaFin()) : null);
            dataRow.createCell(NumeroConstans.CATORCE).setCellValue(automatizaciones.get(i).getConfirmadoPor());
            dataRow.createCell(NumeroConstans.QUINCE).setCellValue(automatizaciones.get(i).getCanalDistribucion());
            dataRow.createCell(NumeroConstans.DIECISEIS).setCellValue(automatizaciones.get(i).getFechaEnvio());
            dataRow.createCell(NumeroConstans.DIECISIETE).setCellValue(automatizaciones.get(i).getBodegaDefecto());
            dataRow.createCell(NumeroConstans.DIECIOCHO).setCellValue(automatizaciones.get(i).getUrlAntesError());
            dataRow.createCell(NumeroConstans.DIECINUEVE).setCellValue(automatizaciones.get(i).getUrlError());
        }

        // Resize all columns to fit the content size
        for (int i = 0; i < 40; i++) {
            sheetHija.autoSizeColumn(i);
        }
        return sheetHija;

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
     * @param sheetHija
     * @param workbook
     * @param automatizaciones
     * @return
     */
    private static XSSFSheet procesarHojaHijaClienteEmpresarial(XSSFSheet sheetHija, XSSFWorkbook workbook,
            List<AutomatizacionesDTO> automatizaciones) {

        String[] headersHija = new String[] { "ID_AUTOMATIZACION",
                "NOMBRE_TRANSACCION", "DATOS_ENTRADA", "USUARIO",
                "ID_EJECUCION", "NUMERO_CUENTA_CLIENTE", "CATEGORIA_CLIENTE",
                "IDENTIFICACION", "NOMBRE_COMPLETO", "FECHA_INICIO",
                "FECHA_FIN", "URL_ANTES_ERROR", "URL_ERROR" };
        
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFRow headerRow = sheetHija.createRow(0);
        for (int j = 0; j < headersHija.length; ++j) {
            String header = headersHija[j];
            XSSFCell cell = headerRow.createCell(j);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(header);
        }
        XSSFRow dataRow = null;
        SimpleDateFormat fecha = new SimpleDateFormat(FormatConstans.FORMATO_FECHA_HORA);
        int j=0;
        for (int i = 0; i < automatizaciones.size(); i++) {
            j=0;
            dataRow = sheetHija.createRow(i + 1);
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getIdAutomatizacion());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getNombreTransaccion());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getDatosEntrada());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getUsuario());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getIdEjecucion());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getNumeroCuentaCliente());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getCategoriaCliente());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getIdentificacion());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getNombreCompleto());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getFechaInicio() != null ? fecha.format(automatizaciones.get(i).getFechaInicio()) : null);
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getFechaFin() != null ? fecha.format(automatizaciones.get(i).getFechaFin()) : null);
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getUrlAntesError());
            dataRow.createCell(j++).setCellValue(automatizaciones.get(i).getUrlError());
        }

        // Resize all columns to fit the content size
        for (int i = 0; i < 40; i++) {
            sheetHija.autoSizeColumn(i);
        }
        return sheetHija;

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
     * @param automatizaciones
     * @param flujo
     * @return
     */
    private static List<AutomatizacionesDTO> filtrarAutomatizaciones(List<AutomatizacionesDTO> automatizaciones,
            FlujoEnum flujo) {
        List<AutomatizacionesDTO> retorno = new ArrayList<>();
        for (AutomatizacionesDTO automatizacionesDTO : automatizaciones) {
            if (automatizacionesDTO.getFlujo().equals(flujo.name())) {
                retorno.add(automatizacionesDTO);
            }
        }
        return retorno;
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
     * @param sheetHija
     * @param workbook
     * @param automatizaciones
     * @return
     */
    private static XSSFSheet procesarHojaHija(XSSFSheet sheetHija, XSSFWorkbook workbook, 
            List<AutomatizacionesDTO> automatizaciones) {

        String[] headersHija = new String[] { "ID_AUTOMATIZACION", "URL_ORDEN", "ESTADO_ORDEN", "CUENTA_FACTURACION",
                "NUMERO_LINEA", "FECHA_ORDEN", "CLIENTE", "NOMBRE_TRANSACCION", "DATOS_ENTRADA", "USUARIO",
                "ID_EJECUCION", "FECHA_INICIO", "FECHA_FIN","CONFIRMADO_POR","CANAL_DISTRIBUCION","FECHA_ENVIO","BODEGA_DEFECTO", "URL_ANTES_ERROR" , "URL_ERROR" };
        
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFRow headerRow = sheetHija.createRow(0);
        for (int j = 0; j < headersHija.length; ++j) {
            String header = headersHija[j];
            XSSFCell cell = headerRow.createCell(j);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(header);
        }
        XSSFRow dataRow = null;
        SimpleDateFormat fecha = new SimpleDateFormat(FormatConstans.FORMATO_FECHA_HORA);
        for (int i = 0; i < automatizaciones.size(); i++) {
            dataRow = sheetHija.createRow(i + 1);
            dataRow.createCell(NumeroConstans.CERO).setCellValue(automatizaciones.get(i).getIdAutomatizacion());
            dataRow.createCell(NumeroConstans.UNO).setCellValue(automatizaciones.get(i).getUrlOrden());
            dataRow.createCell(NumeroConstans.DOS).setCellValue(automatizaciones.get(i).getEstadoOrden());
            dataRow.createCell(NumeroConstans.TRES).setCellValue(automatizaciones.get(i).getCuentaFacturacion());
            dataRow.createCell(NumeroConstans.CUATRO).setCellValue(automatizaciones.get(i).getNumeroLinea());
            dataRow.createCell(NumeroConstans.CINCO).setCellValue(automatizaciones.get(i).getFechaOrden());
            dataRow.createCell(NumeroConstans.SEIS).setCellValue(automatizaciones.get(i).getCliente());
            dataRow.createCell(NumeroConstans.SIETE).setCellValue(automatizaciones.get(i).getNombreTransaccion());
            dataRow.createCell(NumeroConstans.OCHO).setCellValue(automatizaciones.get(i).getDatosEntrada());
            dataRow.createCell(NumeroConstans.NUEVE).setCellValue(automatizaciones.get(i).getUsuario());
            dataRow.createCell(NumeroConstans.DIEZ).setCellValue(automatizaciones.get(i).getIdEjecucion());
            dataRow.createCell(NumeroConstans.ONCE).setCellValue(automatizaciones.get(i).getFechaInicio() != null ? fecha.format(automatizaciones.get(i).getFechaInicio()) : null);
            dataRow.createCell(NumeroConstans.DOCE).setCellValue(automatizaciones.get(i).getFechaFin() != null ? fecha.format(automatizaciones.get(i).getFechaFin()) : null);
            dataRow.createCell(NumeroConstans.TRECE).setCellValue(automatizaciones.get(i).getConfirmadoPor());
            dataRow.createCell(NumeroConstans.CATORCE).setCellValue(automatizaciones.get(i).getCanalDistribucion());
            dataRow.createCell(NumeroConstans.QUINCE).setCellValue(automatizaciones.get(i).getFechaEnvio());
            dataRow.createCell(NumeroConstans.DIECISEIS).setCellValue(automatizaciones.get(i).getBodegaDefecto());
            dataRow.createCell(NumeroConstans.DIECISIETE).setCellValue(automatizaciones.get(i).getUrlAntesError());
            dataRow.createCell(NumeroConstans.DIECIOCHO).setCellValue(automatizaciones.get(i).getUrlError());
        }

        // Resize all columns to fit the content size
        for (int i = 0; i < 40; i++) {
            sheetHija.autoSizeColumn(i);
        }
        return sheetHija;

    }

    /**
     * 
     * Método responsable consultar las evidencias generadas en el proceso
     *
     * @uthor hataborda <br>
     * htaborda <br>
     * htaborda@indracompany.com
     *
     * @date 22/01/2020
     * @version 1.0
     * @param fechaSegundosMilisegundos
     * @return
     */
    public static List<File> consultarImagenesProceso() {
        // se consulta la carpeta evidencias
        File evidencia = new File(properties.getProperty("rutaArchivos") + getFechaSegundosMiligesundos());
        if (evidencia.exists()) {
            return new ArrayList<File>(Arrays.asList(evidencia.listFiles()));
        }
        return null;

    }

    /**
     * Método que obtiene el valor de fechaSegundosMiligesundos
     * 
     * @return the fechaSegundosMiligesundos
     */
    public static String getFechaSegundosMiligesundos() {
        return fechaSegundosMiligesundos;
    }

    /**
     * Método que establece el valor de fechaSegundosMiligesundos
     * 
     * @param fechaSegundosMiligesundos el fechaSegundosMiligesundos a
     * establecer
     */
    public static void setFechaSegundosMiligesundos(String fechaSegundosMiligesundos) {
        ReportesUtil.fechaSegundosMiligesundos = fechaSegundosMiligesundos;
    }

    public static List<String> getListaIdsProcesados() {
        return listaIdsProcesados;
    }

    /**
     * Método que establece el valor de fechaSegundosMiligesundos
     * 
     * @param fechaSegundosMiligesundos el fechaSegundosMiligesundos a
     * establecer
     */
    public static void setListaIdsProcesados(List<String> listaIdsProcesados) {
        ReportesUtil.listaIdsProcesados = listaIdsProcesados;
    }

    /**
     * 
     * Método responsable de verificar si exsite la carpeta de evidencias
     *
     * @uthor hataborda <br>
     * Harold Taborda <br>
     * hataborda@heinsohn.com.co
     *
     * @date 27/01/2020
     * @version 1.0
     */
    public static void verificarExisteRutaEvidencia() {
        File evidencia = new File(properties.getProperty("rutaArchivos") + getFechaSegundosMiligesundos());
        if (!evidencia.exists()) {
            evidencia.mkdir();
        }

    }
    
    private static XSSFSheet procesarHojaHijaDisputasNotaCredito(XSSFSheet sheetHija, XSSFWorkbook workbook,
            List<AutomatizacionesDTO> automatizaciones) {

        String[] headersHija = new String[] { "ID_AUTOMATIZACION", "URL_ORDEN", "ESTADO_ORDEN", "CUENTA_FACTURACION",
                "NUMERO_LINEA", "FECHA_ORDEN", "CLIENTE", "NOMBRE_TRANSACCION", "DATOS_ENTRADA", "USUARIO","NUMERO_ANTIGUO","NUMERO_NUEVO",
                "ID_EJECUCION", "FECHA_INICIO", "FECHA_FIN","CONFIRMADO_POR","CANAL_DISTRIBUCION","FECHA_ENVIO","BODEGA_DEFECTO", "URL_ANTES_ERROR" , "URL_ERROR",
                "FACTURA_DISPUTA", "NUMERO_DISPUTA", "NUMERO_NOTA_CREDITO", "URL_AJUSTE_DISPUTA", "URL_DISPUTA", "URL_DOCUMENTO_NOTA_CREDITO"};
        
        CellStyle headerStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        CellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFRow headerRow = sheetHija.createRow(0);
        for (int j = 0; j < headersHija.length; ++j) {
            String header = headersHija[j];
            XSSFCell cell = headerRow.createCell(j);
            cell.setCellStyle(headerStyle);
            cell.setCellValue(header);
        }
        XSSFRow dataRow = null;
        SimpleDateFormat fecha = new SimpleDateFormat(FormatConstans.FORMATO_FECHA_HORA);
        for (int i = 0; i < automatizaciones.size(); i++) {
            dataRow = sheetHija.createRow(i + 1);
            dataRow.createCell(NumeroConstans.CERO).setCellValue(automatizaciones.get(i).getIdAutomatizacion());
            dataRow.createCell(NumeroConstans.UNO).setCellValue(automatizaciones.get(i).getUrlOrden());
            dataRow.createCell(NumeroConstans.DOS).setCellValue(automatizaciones.get(i).getEstadoOrden());
            dataRow.createCell(NumeroConstans.TRES).setCellValue(automatizaciones.get(i).getCuentaFacturacion());
            dataRow.createCell(NumeroConstans.CUATRO).setCellValue(automatizaciones.get(i).getNumeroLinea());
            dataRow.createCell(NumeroConstans.CINCO).setCellValue(automatizaciones.get(i).getFechaOrden());
            dataRow.createCell(NumeroConstans.SEIS).setCellValue(automatizaciones.get(i).getCliente());
            dataRow.createCell(NumeroConstans.SIETE).setCellValue(automatizaciones.get(i).getNombreTransaccion());
            dataRow.createCell(NumeroConstans.OCHO).setCellValue(automatizaciones.get(i).getDatosEntrada());
            dataRow.createCell(NumeroConstans.NUEVE).setCellValue(automatizaciones.get(i).getUsuario());
            dataRow.createCell(NumeroConstans.DIEZ).setCellValue(automatizaciones.get(i).getNumeroAntiguo());
            dataRow.createCell(NumeroConstans.ONCE).setCellValue(automatizaciones.get(i).getNumeroNuevo());
            dataRow.createCell(NumeroConstans.DOCE).setCellValue(automatizaciones.get(i).getIdEjecucion());
            dataRow.createCell(NumeroConstans.TRECE).setCellValue(automatizaciones.get(i).getFechaInicio() != null ? fecha.format(automatizaciones.get(i).getFechaInicio()) : null);
            dataRow.createCell(NumeroConstans.CATORCE).setCellValue(automatizaciones.get(i).getFechaFin() != null ? fecha.format(automatizaciones.get(i).getFechaFin()) : null);
            dataRow.createCell(NumeroConstans.QUINCE).setCellValue(automatizaciones.get(i).getConfirmadoPor());
            dataRow.createCell(NumeroConstans.DIECISEIS).setCellValue(automatizaciones.get(i).getCanalDistribucion());
            dataRow.createCell(NumeroConstans.DIECISIETE).setCellValue(automatizaciones.get(i).getFechaEnvio());
            dataRow.createCell(NumeroConstans.DIECIOCHO).setCellValue(automatizaciones.get(i).getBodegaDefecto());
            dataRow.createCell(NumeroConstans.DIECINUEVE).setCellValue(automatizaciones.get(i).getUrlAntesError());
            dataRow.createCell(NumeroConstans.VEINTE).setCellValue(automatizaciones.get(i).getUrlError());            
            dataRow.createCell(NumeroConstans.VEINTIUNO).setCellValue(automatizaciones.get(i).getFacturaDisputa());
            dataRow.createCell(NumeroConstans.VEINTIDOS).setCellValue(automatizaciones.get(i).getDisputaGenerada());
            dataRow.createCell(NumeroConstans.VEINTITRES).setCellValue(automatizaciones.get(i).getNumeroNotaCredito());
            dataRow.createCell(NumeroConstans.VEINTICUATRO).setCellValue(automatizaciones.get(i).getUrlAjusteDisputa());
            dataRow.createCell(NumeroConstans.VEINTICINCO).setCellValue(automatizaciones.get(i).getUrlDisputa());
            dataRow.createCell(NumeroConstans.VEINTISEIS).setCellValue(automatizaciones.get(i).getUrlDocumentoNC());
        }

        // Resize all columns to fit the content size
        for (int i = 0; i < 40; i++) {
            sheetHija.autoSizeColumn(i);
        }
        return sheetHija;

    }

}
