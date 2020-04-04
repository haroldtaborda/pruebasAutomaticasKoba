Feature: Facturacion de equipo sin linea
  COMO usuario
  QUIERO generar una factura sin linea
  PARA obtener orden de venta, esado y url


  Background: 
    Given Login en "nc.url" "nc.port" "paht.cliente.residencial" con user "nc.user" en "Username" pass "nc.pass" en "Password" clic en "Iniciar Sesión"

  Scenario Outline: Facturacion equipo sin linea
    When estoy en la url "nc.url" "nc.port" "paht.cliente.residencial"
    And ingreso "<numeroCuentaFacturacion>" en el campo "campo.numero.cuenta.facturacion" para clientes residencial
    And Clic en boton por texto "boton.busqueda"
    And clic en nombre cuenta cliente
    And Clic en boton por texto "boton.productos"
    And ingreso Canal de distribucion "<canalDistribucion>"
    And seleciono combo orden venta "combo.orden.venta.vacia" para "n.a"
    And Clic en boton por texto "boton.crear"
    And cambiar frame interno
    And espero habilitar servicios y caracteristicas
    #13
    #And ingreso plan "<plan>" en servicio de busqueda
    And ingreso plan equipo "<plan>"
    And seleciono plan recien anadido
    And validar equipo interno "<equipo>" ingresar imei "<imei>" numero cuotas "0"
    And cambio a miga de pan validar
    And clic boton final reservar activo
    And clic boton final generar factura
    And clic boton final enviar
    #6
    And extraer Numero orden de venta
    And Volver a la Orden
    And Entrada de Orden
    And Seleccionar la orden anterior
    Then Verificar Numero de Cuenta y Guardar URL

    Examples: 
      | numeroCuentaFacturacion | canalDistribucion | plan   | equipo         | imei            | numeroCuota | cliente |
      |                40330064 | Interno           | Equipo |		GSM LTE SAMSUNG GALAXY J1 ACE BLANCO | 139999362247402 |           1 |       1 | 
      #1|                40330064 | Interno           | Equipo | CHIP PREPAGO PORTABILIDAD |       24 | 8959 |           9985 |	LTE HUAWEI P SMART 2019 NEGRO | 333982926278349 |           1 |       1 | 
      #1|                40330064 | Interno           | Equipo | CHIP PREPAGO PORTABILIDAD |       24 | 8959 |           9985 |GSM LTE SAMSUNG S9 GRIS | 352818092587897 |           1 |       1 | 
      #1|                40330064 | Interno           | Equipo | CHIP PREPAGO PORTABILIDAD |       24 | 8959 |           9985 |LTE HUAWEI P SMART 2019 NEGRO | 333982926278349 |           1 |       1 | 
      #2|                41132016 | Interno           | P0067 - PLAN MOVISTAR $24.99 | CHIP PREPAGO PORTABILIDAD |       24 |    8959 |           9589 | LTE SAMSUNG NOTE 10 NEGRO |            1399 |           1 |       1 |
      #2|                41132016 | Interno           | 7E - PREPAGO TODO EN UNO | CHIP PREPAGO PORTABILIDAD |       24 | 8959300 |           9952 | Equipo Externo | 012950165523558 |           1 |       1 |
      #1|                40330064 | Interno           | 7E - PREPAGO TODO EN UNO | CHIP PREPAGO PORTABILIDAD |       24 | 8959 |           9952 | Equipo Externo | 012950165523558 |           1 |       1 |
      #3|                40687384 | Interno           | 7E - PREPAGO TODO EN UNO | CHIP PREPAGO PORTABILIDAD |       24 | 8959 |           9985 | Equipo Externo | 012950165523558 |           1 |       1 |
