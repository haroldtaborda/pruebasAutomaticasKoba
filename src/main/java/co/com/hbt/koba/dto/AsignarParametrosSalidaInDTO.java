/**
 *
 * files
 */
package co.com.hbt.koba.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Clase responsable
 *
 * @uthor hataborda <br>
 *        Harold Taborda <br>
 *        hataborda@heinsohn.com.co
 *
 * @String 22/01/2020
 * @version 1.0
 */
public class AsignarParametrosSalidaInDTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String url;
    private String estadoOrden;
    private String cuentaFacturacion;
    private String bdNumeroTelefono;
    private String simCardInicial;//sim
    private String procesadoCuando;
    private String nombreCliente;
    private String nombreFlujo;
    private String parametrosJson;
    private String bdPlan;//plan actual
    private String bdTipoEquipo;//tipo equipop
    private String nombreDelEquipoInterno;
    private String modeloDelEquipoInterno;
    private String imeiDelEquipoIterno;
    private String sloActivados;
    private String sloDesactivados;
    private String planActual;
    private String planNuevo;
    private String simcardVieja;
    private String simcardNueva;
    private String numeroAntiguo;
    private String numeroNuevo;
    private String nombreDelEquipoRenovacion;
    private String modeloDelEquipoRenovacion;
    private String imeiDelEquipoRenovacion;
    private String idFLujoTabla;//ID tabla ejecutada
    private String fechaSegundosMilisegundos;
    private String bdNumeroCuentaCliente;//"Número de cuenta del cliente"
    private String bdClientePospago;//"Número cuenta prepago"
    private String bdClientePospagoInAdvance;//"Número Cuenta pospago in advance"
    private String categoria;
    private String bdNumeroDocumento;//"identificación"
    private String bdNombre;//"nombre completo"
    private String tipo_cuenta;//"tipo cuenta"
    private String ciclo;//"ciclo"
    private Date fechaCreacion;
    private String plataforma;
    private String facturaDisputa;
    private String urlNotaCredio;
    private String urlDisputa;
    private String urlDocumentoNotaCredito;
    private String facturaNotaCredito;
    private String disputaGenerada;

    /**
     * Método que obtiene el valor de url
     * @return the url
     */
    public String getUrl() {
        return url;
    }
    /**
     * Método que establece el valor de url
     * @param url el url a establecer
     */
    public void setUrl(String url) {
        this.url = url;
    }
    /**
     * Método que obtiene el valor de estadoOrden
     * @return the estadoOrden
     */
    public String getEstadoOrden() {
        return estadoOrden;
    }
    /**
     * Método que establece el valor de estadoOrden
     * @param estadoOrden el estadoOrden a establecer
     */
    public void setEstadoOrden(String estadoOrden) {
        this.estadoOrden = estadoOrden;
    }
    /**
     * Método que obtiene el valor de cuentaFacturacion
     * @return the cuentaFacturacion
     */
    public String getCuentaFacturacion() {
        return cuentaFacturacion;
    }
    /**
     * Método que establece el valor de cuentaFacturacion
     * @param cuentaFacturacion el cuentaFacturacion a establecer
     */
    public void setCuentaFacturacion(String cuentaFacturacion) {
        this.cuentaFacturacion = cuentaFacturacion;
    }
    /**
     * Método que obtiene el valor de bdNumeroTelefono
     * @return the bdNumeroTelefono
     */
    public String getBdNumeroTelefono() {
        return bdNumeroTelefono;
    }
    /**
     * Método que establece el valor de bdNumeroTelefono
     * @param bdNumeroTelefono el bdNumeroTelefono a establecer
     */
    public void setBdNumeroTelefono(String bdNumeroTelefono) {
        this.bdNumeroTelefono = bdNumeroTelefono;
    }
    /**
     * Método que obtiene el valor de simCardInicial
     * @return the simCardInicial
     */
    public String getSimCardInicial() {
        return simCardInicial;
    }
    /**
     * Método que establece el valor de simCardInicial
     * @param simCardInicial el simCardInicial a establecer
     */
    public void setSimCardInicial(String simCardInicial) {
        this.simCardInicial = simCardInicial;
    }
    /**
     * Método que obtiene el valor de procesadoCuando
     * @return the procesadoCuando
     */
    public String getProcesadoCuando() {
        return procesadoCuando;
    }
    /**
     * Método que establece el valor de procesadoCuando
     * @param procesadoCuando el procesadoCuando a establecer
     */
    public void setProcesadoCuando(String procesadoCuando) {
        this.procesadoCuando = procesadoCuando;
    }
    /**
     * Método que obtiene el valor de nombreCliente
     * @return the nombreCliente
     */
    public String getNombreCliente() {
        return nombreCliente;
    }
    /**
     * Método que establece el valor de nombreCliente
     * @param nombreCliente el nombreCliente a establecer
     */
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    /**
     * Método que obtiene el valor de nombreFlujo
     * @return the nombreFlujo
     */
    public String getNombreFlujo() {
        return nombreFlujo;
    }
    /**
     * Método que establece el valor de nombreFlujo
     * @param nombreFlujo el nombreFlujo a establecer
     */
    public void setNombreFlujo(String nombreFlujo) {
        this.nombreFlujo = nombreFlujo;
    }
    /**
     * Método que obtiene el valor de parametrosJson
     * @return the parametrosJson
     */
    public String getParametrosJson() {
        return parametrosJson;
    }
    /**
     * Método que establece el valor de parametrosJson
     * @param parametrosJson el parametrosJson a establecer
     */
    public void setParametrosJson(String parametrosJson) {
        this.parametrosJson = parametrosJson;
    }
    /**
     * Método que obtiene el valor de bdPlan
     * @return the bdPlan
     */
    public String getBdPlan() {
        return bdPlan;
    }
    /**
     * Método que establece el valor de bdPlan
     * @param bdPlan el bdPlan a establecer
     */
    public void setBdPlan(String bdPlan) {
        this.bdPlan = bdPlan;
    }
    /**
     * Método que obtiene el valor de bdTipoEquipo
     * @return the bdTipoEquipo
     */
    public String getBdTipoEquipo() {
        return bdTipoEquipo;
    }
    /**
     * Método que establece el valor de bdTipoEquipo
     * @param bdTipoEquipo el bdTipoEquipo a establecer
     */
    public void setBdTipoEquipo(String bdTipoEquipo) {
        this.bdTipoEquipo = bdTipoEquipo;
    }
    /**
     * Método que obtiene el valor de nombreDelEquipoInterno
     * @return the nombreDelEquipoInterno
     */
    public String getNombreDelEquipoInterno() {
        return nombreDelEquipoInterno;
    }
    /**
     * Método que establece el valor de nombreDelEquipoInterno
     * @param nombreDelEquipoInterno el nombreDelEquipoInterno a establecer
     */
    public void setNombreDelEquipoInterno(String nombreDelEquipoInterno) {
        this.nombreDelEquipoInterno = nombreDelEquipoInterno;
    }
    /**
     * Método que obtiene el valor de modeloDelEquipoInterno
     * @return the modeloDelEquipoInterno
     */
    public String getModeloDelEquipoInterno() {
        return modeloDelEquipoInterno;
    }
    /**
     * Método que establece el valor de modeloDelEquipoInterno
     * @param modeloDelEquipoInterno el modeloDelEquipoInterno a establecer
     */
    public void setModeloDelEquipoInterno(String modeloDelEquipoInterno) {
        this.modeloDelEquipoInterno = modeloDelEquipoInterno;
    }
    /**
     * Método que obtiene el valor de imeiDelEquipoIterno
     * @return the imeiDelEquipoIterno
     */
    public String getImeiDelEquipoIterno() {
        return imeiDelEquipoIterno;
    }
    /**
     * Método que establece el valor de imeiDelEquipoIterno
     * @param imeiDelEquipoIterno el imeiDelEquipoIterno a establecer
     */
    public void setImeiDelEquipoIterno(String imeiDelEquipoIterno) {
        this.imeiDelEquipoIterno = imeiDelEquipoIterno;
    }
    /**
     * Método que obtiene el valor de sloActivados
     * @return the sloActivados
     */
    public String getSloActivados() {
        return sloActivados;
    }
    /**
     * Método que establece el valor de sloActivados
     * @param sloActivados el sloActivados a establecer
     */
    public void setSloActivados(String sloActivados) {
        this.sloActivados = sloActivados;
    }
    /**
     * Método que obtiene el valor de sloDesactivados
     * @return the sloDesactivados
     */
    public String getSloDesactivados() {
        return sloDesactivados;
    }
    /**
     * Método que establece el valor de sloDesactivados
     * @param sloDesactivados el sloDesactivado a establecer
     */
    public void setSloDesactivados(String sloDesactivados) {
        this.sloDesactivados = sloDesactivados;
    }
    /**
     * Método que obtiene el valor de planActual
     * @return the planActual
     */
    public String getPlanActual() {
        return planActual;
    }
    /**
     * Método que establece el valor de planActual
     * @param planActual el planActual a establecer
     */
    public void setPlanActual(String planActual) {
        this.planActual = planActual;
    }
    /**
     * Método que obtiene el valor de planNuevo
     * @return the planNuevo
     */
    public String getPlanNuevo() {
        return planNuevo;
    }
    /**
     * Método que establece el valor de planNuevo
     * @param planNuevo el planNuevo a establecer
     */
    public void setPlanNuevo(String planNuevo) {
        this.planNuevo = planNuevo;
    }
    /**
     * Método que obtiene el valor de simcardVieja
     * @return the simcardVieja
     */
    public String getSimcardVieja() {
        return simcardVieja;
    }
    /**
     * Método que establece el valor de simcardVieja
     * @param simcardVieja el simcardVieja a establecer
     */
    public void setSimcardVieja(String simcardVieja) {
        this.simcardVieja = simcardVieja;
    }
    /**
     * Método que obtiene el valor de simcardNueva
     * @return the simcardNueva
     */
    public String getSimcardNueva() {
        return simcardNueva;
    }
    /**
     * Método que establece el valor de simcardNueva
     * @param simcardNueva el simcardNueva a establecer
     */
    public void setSimcardNueva(String simcardNueva) {
        this.simcardNueva = simcardNueva;
    }
    /**
     * Método que obtiene el valor de numeroAntiguo
     * @return the numeroAntiguo
     */
    public String getNumeroAntiguo() {
        return numeroAntiguo;
    }
    /**
     * Método que establece el valor de numeroAntiguo
     * @param numeroAntiguo el numeroAntiguo a establecer
     */
    public void setNumeroAntiguo(String numeroAntiguo) {
        this.numeroAntiguo = numeroAntiguo;
    }
    /**
     * Método que obtiene el valor de numeroNuevo
     * @return the numeroNuevo
     */
    public String getNumeroNuevo() {
        return numeroNuevo;
    }
    /**
     * Método que establece el valor de numeroNuevo
     * @param numeroNuevo el numeroNuevo a establecer
     */
    public void setNumeroNuevo(String numeroNuevo) {
        this.numeroNuevo = numeroNuevo;
    }
    /**
     * Método que obtiene el valor de nombreDelEquipoRenovacion
     * @return the nombreDelEquipoRenovacion
     */
    public String getNombreDelEquipoRenovacion() {
        return nombreDelEquipoRenovacion;
    }
    /**
     * Método que establece el valor de nombreDelEquipoRenovacion
     * @param nombreDelEquipoRenovacion el nombreDelEquipoRenovacion a establecer
     */
    public void setNombreDelEquipoRenovacion(String nombreDelEquipoRenovacion) {
        this.nombreDelEquipoRenovacion = nombreDelEquipoRenovacion;
    }
    /**
     * Método que obtiene el valor de modeloDelEquipoRenovacion
     * @return the modeloDelEquipoRenovacion
     */
    public String getModeloDelEquipoRenovacion() {
        return modeloDelEquipoRenovacion;
    }
    /**
     * Método que establece el valor de modeloDelEquipoRenovacion
     * @param modeloDelEquipoRenovacion el modeloDelEquipoRenovacion a establecer
     */
    public void setModeloDelEquipoRenovacion(String modeloDelEquipoRenovacion) {
        this.modeloDelEquipoRenovacion = modeloDelEquipoRenovacion;
    }
    /**
     * Método que obtiene el valor de imeiDelEquipoRenovacion
     * @return the imeiDelEquipoRenovacion
     */
    public String getImeiDelEquipoRenovacion() {
        return imeiDelEquipoRenovacion;
    }
    /**
     * Método que establece el valor de imeiDelEquipoRenovacion
     * @param imeiDelEquipoRenovacion el imeiDelEquipoRenovacion a establecer
     */
    public void setImeiDelEquipoRenovacion(String imeiDelEquipoRenovacion) {
        this.imeiDelEquipoRenovacion = imeiDelEquipoRenovacion;
    }
    /**
     * Método que obtiene el valor de idFLujoTabla
     * @return the idFLujoTabla
     */
    public String getIdFLujoTabla() {
        return idFLujoTabla;
    }
    /**
     * Método que establece el valor de idFLujoTabla
     * @param idFLujoTabla el idFLujoTabla a establecer
     */
    public void setIdFLujoTabla(String idFLujoTabla) {
        this.idFLujoTabla = idFLujoTabla;
    }
    /**
     * Método que obtiene el valor de fechaSegundosMilisegundos
     * @return the fechaSegundosMilisegundos
     */
    public String getFechaSegundosMilisegundos() {
        return fechaSegundosMilisegundos;
    }
    /**
     * Método que establece el valor de fechaSegundosMilisegundos
     * @param fechaSegundosMilisegundos el fechaSegundosMilisegundos a establecer
     */
    public void setFechaSegundosMilisegundos(String fechaSegundosMilisegundos) {
        this.fechaSegundosMilisegundos = fechaSegundosMilisegundos;
    }
    /**
     * Método que obtiene el valor de bdNumeroCuentaCliente
     * @return the bdNumeroCuentaCliente
     */
    public String getBdNumeroCuentaCliente() {
        return bdNumeroCuentaCliente;
    }
    /**
     * Método que establece el valor de bdNumeroCuentaCliente
     * @param bdNumeroCuentaCliente el bdNumeroCuentaCliente a establecer
     */
    public void setBdNumeroCuentaCliente(String bdNumeroCuentaCliente) {
        this.bdNumeroCuentaCliente = bdNumeroCuentaCliente;
    }
    /**
     * Método que obtiene el valor de bdClientePospago
     * @return the bdClientePospago
     */
    public String getBdClientePospago() {
        return bdClientePospago;
    }
    /**
     * Método que establece el valor de bdClientePospago
     * @param bdClientePospago el bdClientePospago a establecer
     */
    public void setBdClientePospago(String bdClientePospago) {
        this.bdClientePospago = bdClientePospago;
    }
    /**
     * Método que obtiene el valor de bdClientePospagoInAdvance
     * @return the bdClientePospagoInAdvance
     */
    public String getBdClientePospagoInAdvance() {
        return bdClientePospagoInAdvance;
    }
    /**
     * Método que establece el valor de bdClientePospagoInAdvance
     * @param bdClientePospagoInAdvance el bdClientePospagoInAdvance a establecer
     */
    public void setBdClientePospagoInAdvance(String bdClientePospagoInAdvance) {
        this.bdClientePospagoInAdvance = bdClientePospagoInAdvance;
    }
    /**
     * Método que obtiene el valor de categoria
     * @return the categoria
     */
    public String getCategoria() {
        return categoria;
    }
    /**
     * Método que establece el valor de categoria
     * @param categoria el categoria a establecer
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    /**
     * Método que obtiene el valor de bdNumeroDocumento
     * @return the bdNumeroDocumento
     */
    public String getBdNumeroDocumento() {
        return bdNumeroDocumento;
    }
    /**
     * Método que establece el valor de bdNumeroDocumento
     * @param bdNumeroDocumento el bdNumeroDocumento a establecer
     */
    public void setBdNumeroDocumento(String bdNumeroDocumento) {
        this.bdNumeroDocumento = bdNumeroDocumento;
    }
    /**
     * Método que obtiene el valor de bdNombre
     * @return the bdNombre
     */
    public String getBdNombre() {
        return bdNombre;
    }
    /**
     * Método que establece el valor de bdNombre
     * @param bdNombre el bdNombre a establecer
     */
    public void setBdNombre(String bdNombre) {
        this.bdNombre = bdNombre;
    }
    /**
     * Método que obtiene el valor de tipo_cuenta
     * @return the tipo_cuenta
     */
    public String getTipo_cuenta() {
        return tipo_cuenta;
    }
    /**
     * Método que establece el valor de tipo_cuenta
     * @param tipo_cuenta el tipo_cuenta a establecer
     */
    public void setTipo_cuenta(String tipo_cuenta) {
        this.tipo_cuenta = tipo_cuenta;
    }
    /**
     * Método que obtiene el valor de ciclo
     * @return the ciclo
     */
    public String getCiclo() {
        return ciclo;
    }
    /**
     * Método que establece el valor de ciclo
     * @param ciclo el ciclo a establecer
     */
    public void setCiclo(String ciclo) {
        this.ciclo = ciclo;
    }
    /**
     * Método que obtiene el valor de fechaCreacion
     * @return the fechaCreacion
     */
    public Date getFechaCreacion() {
        return fechaCreacion;
    }
    /**
     * Método que establece el valor de fechaCreacion
     * @param fechaCreacion el fechaCreacion a establecer
     */
    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    /**
     * Método que obtiene el valor de plataforma
     * @return the plataforma
     */
    public String getPlataforma() {
        return plataforma;
    }
    /**
     * Método que establece el valor de plataforma
     * @param plataforma el plataforma a establecer
     */
    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }
    public String getFacturaDisputa() {
        return facturaDisputa;
    }
    public void setFacturaDisputa(String facturaDisputa) {
        this.facturaDisputa = facturaDisputa;
    }
    public String getUrlNotaCredio() {
        return urlNotaCredio;
    }
    public void setUrlNotaCredio(String urlNotaCredio) {
        this.urlNotaCredio = urlNotaCredio;
    }
    public String getUrlDisputa() {
        return urlDisputa;
    }
    public void setUrlDisputa(String urlDisputa) {
        this.urlDisputa = urlDisputa;
    }
    public String getUrlDocumentoNotaCredito() {
        return urlDocumentoNotaCredito;
    }
    public void setUrlDocumentoNotaCredito(String urlDocumentoNotaCredito) {
        this.urlDocumentoNotaCredito = urlDocumentoNotaCredito;
    }
    public String getFacturaNotaCredito() {
        return facturaNotaCredito;
    }
    public void setFacturaNotaCredito(String facturaNotaCredito) {
        this.facturaNotaCredito = facturaNotaCredito;
    }
    public String getDisputaGenerada() {
        return disputaGenerada;
    }
    public void setDisputaGenerada(String disputaGenerada) {
        this.disputaGenerada = disputaGenerada;
    }
    
    
}
