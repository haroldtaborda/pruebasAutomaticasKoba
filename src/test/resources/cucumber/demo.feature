@altas
Feature: dar de alta
  COMO usuario
  QUIERO dar de alta un pre o pos
  PARA obtener orden de venta, esado y url

  Background: 
    Given Init
    And Login en "nc.url" "nc.port" "paht.cliente.residencial" con user "nc.user" en "Username" pass "nc.pass" en "Password" clic en "Iniciar Sesión"

  Scenario Outline: Alta lineas pospago/prepago
    When Consulto la tabla "OTC_T_ALTAS_LINEAS" con el id "<ID>" para el flujo "ALTAS_LINEAS"
    And validar operador "nc.user"
    And validar bodega "nombre.bodega.actual"
    And estoy en la url "nc.url" "nc.port" "paht.cliente.residencial"
    And ingreso "numero_cuenta_distribucion" en el campo "campo.numero.cuenta.facturacion" para clientes residencial
    And ingreso "id_cuenta_cliente" en el campo "campo.id.cuenta.cliente" para clientes residencial
    And Clic en boton por texto "boton.busqueda"
    And clic en nombre cuenta cliente
    And obtener datos nombre cliente cuenta facturacion telefono
    And Clic en boton por texto "boton.productos"
    And ingreso Canal de distribucion "canal_distribucion"
    And seleciono combo orden venta "combo.orden.venta.vacia" para "n.a"
    And Clic en boton por texto "boton.crear"
    And cambiar frame interno
    And espero habilitar servicios y caracteristicas
    And ingreso plan "plan" en servicio de busqueda
    And seleciono plan recien anadido
    And validar plan "plan" y selecionar chip prepago "chip_prepago"
    And selecionar contrato "contrato" ICCD "sim" numero telefono "numero_telefono"
    And validar equipo "equipo" ingresar imei "imei" numero cuotas "numero_cuota" renovacion "renovacion" inicial "cuota_inicial"
    And validar crear cliente "cliente"
    And cambio a miga de pan validar
    And cambiar limite credito "20000"
    And clic boton final reservar activo
    And clic boton final configurar contrato
    And generar documento
    And clic boton final generar factura
    And clic boton final enviar
    And extraer Numero orden de venta
    And Volver a la Orden
    And Entrada de Orden
    And Seleccionar la orden anterior
    And Verificar Numero de Cuenta y Guardar URL
    Then Guardar resultado en BD flujo "ALTAS_LINEAS"

    Examples: 
      | ID |
      |  1 |
      |  2 |
