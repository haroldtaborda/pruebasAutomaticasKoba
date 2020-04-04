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
public class AutomatizacionesDTO implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Long idAutomatizacion;
    private String urlOrden;
    private String estadoOrden;
    private String cuentaFacturacion;
    private String numeroLinea;
    private String simCard;
    private String fechaOrden;
    private String cliente;
    private String nombreTransaccion;
    private String datosEntrada;    
    private String usuario;
    private String plan;
    private String tipoEquipo;
    private String nombreEquipoInterno;
    private String modeloEquipoInterno;
    private String imeiEquipoInterno;
    private String sloActivados;
    private String sloDesactivados;
    private String planAntiguo;
    private String planNuevo;
    private String simcardAntigua;
    private String simcardNueva;
    private String numeroAntiguo;
    private String numeroNuevo;
    private String nombreEquipo;
    private String modeloEquipo;
    private String imeiEquipo;
    private String flujo;
    private String idEjecucion;
    private String ejecucion;
    private String identificacion;
    private String numeroCuentaPrepago;
    private String numeroCuentaPostpago;
    private String numeroCentaPostAdvance;
    private String numeroCuentaCliente;
    private String nombreCompleto;
    private String tipoCuenta;
    private String ciclo;
    private String categoriaCliente;
    private Date fechaInicio;
    private Date fechaFin;
    private String confirmadoPor;
    private String fechaEnvio;
    private String canalDistribucion;
    private String bodegaDefecto;
    private String urlError;
    private String urlAntesError;
    private String plataforma;
    private String facturaDisputa;
    private String disputaGenerada;
    private String urlAjusteDisputa;
    private String urlDisputa;
    private String urlDocumentoNC;
    private String numeroNotaCredito;
    
    public AutomatizacionesDTO(){
        
    }
    

    /**
     * @param idAutomatizacion
     * @param urlOrden
     * @param estadoOrden
     * @param cuentaFacturacion
     * @param numeroLinea
     * @param simCard
     * @param fechaOrden
     * @param cliente
     * @param nombreTransaccion
     * @param datosEntrada
     * @param usuario
     * @param plan
     * @param tipoEquipo
     * @param nombreEquipoInterno
     * @param modeloEquipoInterno
     * @param imeiEquipoInterno
     * @param sloActivados
     * @param sloDesactivados
     * @param planAntiguo
     * @param planNuevo
     * @param simcardAntigua
     * @param simcardNueva
     * @param numeroAntiguo
     * @param numeroNuevo
     * @param nombreEquipo
     * @param modeloEquipo
     * @param imeiEquipo
     * @param flujo
     * @param string 
     */
    public AutomatizacionesDTO(Long idAutomatizacion, String urlOrden, String estadoOrden, String cuentaFacturacion,
            String numeroLinea, String simCard, String fechaOrden, String cliente, String nombreTransaccion,
            String datosEntrada, String usuario, String plan, String tipoEquipo, String nombreEquipoInterno,
            String modeloEquipoInterno, String imeiEquipoInterno, String sloActivados,String sloDesactivados, String planAntiguo,
            String planNuevo, String simcardAntigua, String simcardNueva, String numeroAntiguo, String numeroNuevo,
            String nombreEquipo, String modeloEquipo, String imeiEquipo, String flujo,String idEjecucion,
            String numeroCuentaCliente, String numeroCuentaPrepago, String numeroCuentaPostpago, String numeroCuentaPostAdv,String categoriaCliente,
            String identificacion, String nombreCompleto, String tipoCuenta, String ciclo, Date fechaInicio, Date fechaFin,
            String confirmadoPor, String fechaEnvio,String canalDistribucion,String bodegaDefecto,String urlError, String urlAntesError,String plataforma,
            String facturaDisputa, String numeroDisputa, String numeroNotaCredito, String urlAjusteDisputa, String urlDisputa, String urlDocumentoNotaCredito) {
        super();
        this.urlError=urlError;
        this.confirmadoPor=confirmadoPor;
        this.fechaEnvio=fechaEnvio;
        this.canalDistribucion=canalDistribucion;
        this.bodegaDefecto=bodegaDefecto;
        this.idAutomatizacion = idAutomatizacion;
        this.urlOrden = urlOrden;
        this.estadoOrden = estadoOrden;
        this.cuentaFacturacion = cuentaFacturacion;
        this.numeroLinea = numeroLinea;
        this.simCard = simCard;
        this.fechaOrden = fechaOrden;
        this.cliente = cliente;
        this.nombreTransaccion = nombreTransaccion;
        this.datosEntrada = datosEntrada;
        this.usuario = usuario;
        this.plan = plan;
        this.tipoEquipo = tipoEquipo;
        this.nombreEquipoInterno = nombreEquipoInterno;
        this.modeloEquipoInterno = modeloEquipoInterno;
        this.imeiEquipoInterno = imeiEquipoInterno;
        this.sloActivados = sloActivados;
        this.sloDesactivados = sloDesactivados;
        this.planAntiguo = planAntiguo;
        this.planNuevo = planNuevo;
        this.simcardAntigua = simcardAntigua;
        this.simcardNueva = simcardNueva;
        this.numeroAntiguo = numeroAntiguo;
        this.numeroNuevo = numeroNuevo;
        this.nombreEquipo = nombreEquipo;
        this.modeloEquipo = modeloEquipo;
        this.imeiEquipo = imeiEquipo;
        this.flujo = flujo;
        this.idEjecucion=idEjecucion;
        this.numeroCuentaCliente=numeroCuentaCliente;
        this.numeroCuentaPrepago=numeroCuentaPrepago;
        this.numeroCuentaPostpago=numeroCuentaPostpago;
        this.numeroCentaPostAdvance=numeroCuentaPostAdv;
        this.categoriaCliente=categoriaCliente;
        this.identificacion=identificacion;
        this.nombreCompleto=nombreCompleto;
        this.tipoCuenta=tipoCuenta;
        this.ciclo=ciclo;
        this.fechaInicio=fechaInicio;
        this.fechaFin=fechaFin;
        this.urlAntesError=urlAntesError;
        this.plataforma=plataforma;        
        this.facturaDisputa = facturaDisputa;
        this.disputaGenerada = numeroDisputa;
        this.numeroNotaCredito = numeroNotaCredito;
        this.urlDisputa = urlDisputa;
        this.urlAjusteDisputa = urlAjusteDisputa;
        this.urlDocumentoNC = urlDocumentoNotaCredito;

    }


    /**
     * Método que obtiene el valor de idAutomatizacion
     * @return the idAutomatizacion
     */
    public Long getIdAutomatizacion() {
        return idAutomatizacion;
    }
    /**
     * Método que establece el valor de idAutomatizacion
     * @param idAutomatizacion el idAutomatizacion a establecer
     */
    public void setIdAutomatizacion(Long idAutomatizacion) {
        this.idAutomatizacion = idAutomatizacion;
    }
    /**
     * Método que obtiene el valor de urlOrden
     * @return the urlOrden
     */
    public String getUrlOrden() {
        return urlOrden;
    }
    /**
     * Método que establece el valor de urlOrden
     * @param urlOrden el urlOrden a establecer
     */
    public void setUrlOrden(String urlOrden) {
        this.urlOrden = urlOrden;
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
     * Método que obtiene el valor de numeroLinea
     * @return the numeroLinea
     */
    public String getNumeroLinea() {
        return numeroLinea;
    }
    /**
     * Método que establece el valor de numeroLinea
     * @param numeroLinea el numeroLinea a establecer
     */
    public void setNumeroLinea(String numeroLinea) {
        this.numeroLinea = numeroLinea;
    }
    /**
     * Método que obtiene el valor de simCard
     * @return the simCard
     */
    public String getSimCard() {
        return simCard;
    }
    /**
     * Método que establece el valor de simCard
     * @param simCard el simCard a establecer
     */
    public void setSimCard(String simCard) {
        this.simCard = simCard;
    }
    /**
     * Método que obtiene el valor de fechaOrden
     * @return the fechaOrden
     */
    public String getFechaOrden() {
        return fechaOrden;
    }
    /**
     * Método que establece el valor de fechaOrden
     * @param fechaOrden el fechaOrden a establecer
     */
    public void setFechaOrden(String fechaOrden) {
        this.fechaOrden = fechaOrden;
    }
    /**
     * Método que obtiene el valor de cliente
     * @return the cliente
     */
    public String getCliente() {
        return cliente;
    }
    /**
     * Método que establece el valor de cliente
     * @param cliente el cliente a establecer
     */
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    /**
     * Método que obtiene el valor de nombreTransaccion
     * @return the nombreTransaccion
     */
    public String getNombreTransaccion() {
        return nombreTransaccion;
    }
    /**
     * Método que establece el valor de nombreTransaccion
     * @param nombreTransaccion el nombreTransaccion a establecer
     */
    public void setNombreTransaccion(String nombreTransaccion) {
        this.nombreTransaccion = nombreTransaccion;
    }
    /**
     * Método que obtiene el valor de datosEntrada
     * @return the datosEntrada
     */
    public String getDatosEntrada() {
        return datosEntrada;
    }
    /**
     * Método que establece el valor de datosEntrada
     * @param datosEntrada el datosEntrada a establecer
     */
    public void setDatosEntrada(String datosEntrada) {
        this.datosEntrada = datosEntrada;
    }
    /**
     * Método que obtiene el valor de usuario
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }
    /**
     * Método que establece el valor de usuario
     * @param usuario el usuario a establecer
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }


    /**
     * Método que obtiene el valor de plan
     * @return the plan
     */
    public String getPlan() {
        return plan;
    }


    /**
     * Método que establece el valor de plan
     * @param plan el plan a establecer
     */
    public void setPlan(String plan) {
        this.plan = plan;
    }


    /**
     * Método que obtiene el valor de tipoEquipo
     * @return the tipoEquipo
     */
    public String getTipoEquipo() {
        return tipoEquipo;
    }


    /**
     * Método que establece el valor de tipoEquipo
     * @param tipoEquipo el tipoEquipo a establecer
     */
    public void setTipoEquipo(String tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }


    /**
     * Método que obtiene el valor de nombreEquipoInterno
     * @return the nombreEquipoInterno
     */
    public String getNombreEquipoInterno() {
        return nombreEquipoInterno;
    }


    /**
     * Método que establece el valor de nombreEquipoInterno
     * @param nombreEquipoInterno el nombreEquipoInterno a establecer
     */
    public void setNombreEquipoInterno(String nombreEquipoInterno) {
        this.nombreEquipoInterno = nombreEquipoInterno;
    }


    /**
     * Método que obtiene el valor de modeloEquipoInterno
     * @return the modeloEquipoInterno
     */
    public String getModeloEquipoInterno() {
        return modeloEquipoInterno;
    }


    /**
     * Método que establece el valor de modeloEquipoInterno
     * @param modeloEquipoInterno el modeloEquipoInterno a establecer
     */
    public void setModeloEquipoInterno(String modeloEquipoInterno) {
        this.modeloEquipoInterno = modeloEquipoInterno;
    }


    /**
     * Método que obtiene el valor de imeiEquipoInterno
     * @return the imeiEquipoInterno
     */
    public String getImeiEquipoInterno() {
        return imeiEquipoInterno;
    }


    /**
     * Método que establece el valor de imeiEquipoInterno
     * @param imeiEquipoInterno el imeiEquipoInterno a establecer
     */
    public void setImeiEquipoInterno(String imeiEquipoInterno) {
        this.imeiEquipoInterno = imeiEquipoInterno;
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
     * Método que obtiene el valor de sloActivados
     * @return the sloActivados
     */
    public String getSloDesactivados() {
        return sloDesactivados;
    }


    /**
     * Método que establece el valor de sloActivados
     * @param sloActivados el sloActivados a establecer
     */
    public void setSloDesactivados(String sloActivados) {
        this.sloDesactivados = sloActivados;
    }



    /**
     * Método que obtiene el valor de planAntiguo
     * @return the planAntiguo
     */
    public String getPlanAntiguo() {
        return planAntiguo;
    }


    /**
     * Método que establece el valor de planAntiguo
     * @param planAntiguo el planAntiguo a establecer
     */
    public void setPlanAntiguo(String planAntiguo) {
        this.planAntiguo = planAntiguo;
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
     * Método que obtiene el valor de simcardAntigua
     * @return the simcardAntigua
     */
    public String getSimcardAntigua() {
        return simcardAntigua;
    }


    /**
     * Método que establece el valor de simcardAntigua
     * @param simcardAntigua el simcardAntigua a establecer
     */
    public void setSimcardAntigua(String simcardAntigua) {
        this.simcardAntigua = simcardAntigua;
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
     * Método que obtiene el valor de nombreEquipo
     * @return the nombreEquipo
     */
    public String getNombreEquipo() {
        return nombreEquipo;
    }


    /**
     * Método que establece el valor de nombreEquipo
     * @param nombreEquipo el nombreEquipo a establecer
     */
    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }


    /**
     * Método que obtiene el valor de modeloEquipo
     * @return the modeloEquipo
     */
    public String getModeloEquipo() {
        return modeloEquipo;
    }


    /**
     * Método que establece el valor de modeloEquipo
     * @param modeloEquipo el modeloEquipo a establecer
     */
    public void setModeloEquipo(String modeloEquipo) {
        this.modeloEquipo = modeloEquipo;
    }


    /**
     * Método que obtiene el valor de imeiEquipo
     * @return the imeiEquipo
     */
    public String getImeiEquipo() {
        return imeiEquipo;
    }


    /**
     * Método que establece el valor de imeiEquipo
     * @param imeiEquipo el imeiEquipo a establecer
     */
    public void setImeiEquipo(String imeiEquipo) {
        this.imeiEquipo = imeiEquipo;
    }


    /**
     * Método que obtiene el valor de flujo
     * @return the flujo
     */
    public String getFlujo() {
        return flujo;
    }


    /**
     * Método que establece el valor de flujo
     * @param flujo el flujo a establecer
     */
    public void setFlujo(String flujo) {
        this.flujo = flujo;
    }


    /**
     * Método que obtiene el valor de idEjecucion
     * @return the idEjecucion
     */
    public String getIdEjecucion() {
        return idEjecucion;
    }


    /**
     * Método que establece el valor de idEjecucion
     * @param idEjecucion el idEjecucion a establecer
     */
    public void setIdEjecucion(String idEjecucion) {
        this.idEjecucion = idEjecucion;
    }


    /**
     * Método que obtiene el valor de ejecucion
     * @return the ejecucion
     */
    public String getEjecucion() {
        return ejecucion;
    }


    /**
     * Método que establece el valor de ejecucion
     * @param ejecucion el ejecucion a establecer
     */
    public void setEjecucion(String ejecucion) {
        this.ejecucion = ejecucion;
    }


    /**
     * Método que obtiene el valor de identificacion
     * @return the identificacion
     */
    public String getIdentificacion() {
        return identificacion;
    }


    /**
     * Método que establece el valor de identificacion
     * @param identificacion el identificacion a establecer
     */
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    /**
     * Método que obtiene el valor de numeroCuentaPrepago
     * @return the numeroCuentaPrepago
     */
    public String getNumeroCuentaPrepago() {
        return numeroCuentaPrepago;
    }


    /**
     * Método que establece el valor de numeroCuentaPrepago
     * @param numeroCuentaPrepago el numeroCuentaPrepago a establecer
     */
    public void setNumeroCuentaPrepago(String numeroCuentaPrepago) {
        this.numeroCuentaPrepago = numeroCuentaPrepago;
    }


    /**
     * Método que obtiene el valor de numeroCuentaPostpago
     * @return the numeroCuentaPostpago
     */
    public String getNumeroCuentaPostpago() {
        return numeroCuentaPostpago;
    }


    /**
     * Método que establece el valor de numeroCuentaPostpago
     * @param numeroCuentaPostpago el numeroCuentaPostpago a establecer
     */
    public void setNumeroCuentaPostpago(String numeroCuentaPostpago) {
        this.numeroCuentaPostpago = numeroCuentaPostpago;
    }


    /**
     * Método que obtiene el valor de numeroCentaPostAdvance
     * @return the numeroCentaPostAdvance
     */
    public String getNumeroCentaPostAdvance() {
        return numeroCentaPostAdvance;
    }


    /**
     * Método que establece el valor de numeroCentaPostAdvance
     * @param numeroCentaPostAdvance el numeroCentaPostAdvance a establecer
     */
    public void setNumeroCentaPostAdvance(String numeroCentaPostAdvance) {
        this.numeroCentaPostAdvance = numeroCentaPostAdvance;
    }


    /**
     * Método que obtiene el valor de numeroCuentaCliente
     * @return the numeroCuentaCliente
     */
    public String getNumeroCuentaCliente() {
        return numeroCuentaCliente;
    }


    /**
     * Método que establece el valor de numeroCuentaCliente
     * @param numeroCuentaCliente el numeroCuentaCliente a establecer
     */
    public void setNumeroCuentaCliente(String numeroCuentaCliente) {
        this.numeroCuentaCliente = numeroCuentaCliente;
    }


    /**
     * Método que obtiene el valor de nombreCompleto
     * @return the nombreCompleto
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }


    /**
     * Método que establece el valor de nombreCompleto
     * @param nombreCompleto el nombreCompleto a establecer
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }


    /**
     * Método que obtiene el valor de tipoCuenta
     * @return the tipoCuenta
     */
    public String getTipoCuenta() {
        return tipoCuenta;
    }


    /**
     * Método que establece el valor de tipoCuenta
     * @param tipoCuenta el tipoCuenta a establecer
     */
    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
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
     * Método que obtiene el valor de categoriaCliente
     * @return the categoriaCliente
     */
    public String getCategoriaCliente() {
        return categoriaCliente;
    }


    /**
     * Método que establece el valor de categoriaCliente
     * @param categoriaCliente el categoriaCliente a establecer
     */
    public void setCategoriaCliente(String categoriaCliente) {
        this.categoriaCliente = categoriaCliente;
    }


    /**
     * Método que obtiene el valor de fechaInicio
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }


    /**
     * Método que establece el valor de fechaInicio
     * @param fechaInicio el fechaInicio a establecer
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }


    /**
     * Método que obtiene el valor de fechaFin
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }


    /**
     * Método que establece el valor de fechaFin
     * @param fechaFin el fechaFin a establecer
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }


    /**
     * Método que obtiene el valor de confirmadoPor
     * @return the confirmadoPor
     */
    public String getConfirmadoPor() {
        return confirmadoPor;
    }


    /**
     * Método que establece el valor de confirmadoPor
     * @param confirmadoPor el confirmadoPor a establecer
     */
    public void setConfirmadoPor(String confirmadoPor) {
        this.confirmadoPor = confirmadoPor;
    }


    /**
     * Método que obtiene el valor de fechaEnvio
     * @return the fechaEnvio
     */
    public String getFechaEnvio() {
        return fechaEnvio;
    }


    /**
     * Método que establece el valor de fechaEnvio
     * @param fechaEnvio el fechaEnvio a establecer
     */
    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }


    /**
     * Método que obtiene el valor de canalDistribucion
     * @return the canalDistribucion
     */
    public String getCanalDistribucion() {
        return canalDistribucion;
    }


    /**
     * Método que establece el valor de canalDistribucion
     * @param canalDistribucion el canalDistribucion a establecer
     */
    public void setCanalDistribucion(String canalDistribucion) {
        this.canalDistribucion = canalDistribucion;
    }


    /**
     * Método que obtiene el valor de bodegaDefecto
     * @return the bodegaDefecto
     */
    public String getBodegaDefecto() {
        return bodegaDefecto;
    }


    /**
     * Método que establece el valor de bodegaDefecto
     * @param bodegaDefecto el bodegaDefecto a establecer
     */
    public void setBodegaDefecto(String bodegaDefecto) {
        this.bodegaDefecto = bodegaDefecto;
    }


    /**
     * Método que obtiene el valor de urlError
     * @return the urlError
     */
    public String getUrlError() {
        return urlError;
    }


    /**
     * Método que establece el valor de urlError
     * @param urlError el urlError a establecer
     */
    public void setUrlError(String urlError) {
        this.urlError = urlError;
    }


    /**
     * Método que obtiene el valor de urlAntesError
     * @return the urlAntesError
     */
    public String getUrlAntesError() {
        return urlAntesError;
    }


    /**
     * Método que establece el valor de urlAntesError
     * @param urlAntesError el urlAntesError a establecer
     */
    public void setUrlAntesError(String urlAntesError) {
        this.urlAntesError = urlAntesError;
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


    public String getDisputaGenerada() {
        return disputaGenerada;
    }


    public void setDisputaGenerada(String disputaGenerada) {
        this.disputaGenerada = disputaGenerada;
    }


    public String getUrlAjusteDisputa() {
        return urlAjusteDisputa;
    }


    public void setUrlAjusteDisputa(String urlAjusteDisputa) {
        this.urlAjusteDisputa = urlAjusteDisputa;
    }


    public String getUrlDisputa() {
        return urlDisputa;
    }


    public void setUrlDisputa(String urlDisputa) {
        this.urlDisputa = urlDisputa;
    }


    public String getUrlDocumentoNC() {
        return urlDocumentoNC;
    }


    public void setUrlDocumentoNC(String urlDocumentoNC) {
        this.urlDocumentoNC = urlDocumentoNC;
    }


    public String getNumeroNotaCredito() {
        return numeroNotaCredito;
    }


    public void setNumeroNotaCredito(String numeroNotaCredito) {
        this.numeroNotaCredito = numeroNotaCredito;
    }
    

}
