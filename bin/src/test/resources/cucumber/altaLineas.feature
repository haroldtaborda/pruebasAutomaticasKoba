Feature: dar de alta
  COMO usuario
  QUIERO dar de alta un pre o pos
  PARA obtener orden de venta, esado y url

  Background: 
    Given Login en "nc.url" "nc.port" "paht.cliente.residencial" con user "nc.user" en "Username" pass "nc.pass" en "Password" clic en "Iniciar Sesión"

  Scenario Outline: Alta lineas pospago/prepago
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
    
    And ingreso plan "<plan>" en servicio de busqueda
    And seleciono plan recien anadido
    And validar plan "<plan>" y selecionar chip prepago "<chipPrepago>"
    And selecionar contrato "<contrato>" ICCD "<sim>" numero telefono "<numeroTelefono>"
    And validar equipo "<equipo>" ingresar imei "<imei>" numero cuotas "<numeroCuota>"
    And validar crear cliente "<cliente>"
    
    And cambio a miga de pan validar
    And clic boton final reservar activo
    And clic boton final configurar contrato
    And clic boton final generar factura
    And clic boton final enviar
    
    And extraer Numero orden de venta
    And Volver a la Orden
    And Entrada de Orden
    And Seleccionar la orden anterior
    Then Verificar Numero de Cuenta y Guardar URL

    Examples: 
      | numeroCuentaFacturacion | canalDistribucion | plan                     | chipPrepago               | contrato | sim     | numeroTelefono | equipo         | imei            | numeroCuota | cliente |
      #|                41132016 | Interno           | P0067 - PLAN MOVISTAR $24.99 | CHIP PREPAGO PORTABILIDAD |       24 |    8959 |           9589 | LTE SAMSUNG NOTE 10 NEGRO |            1399 |           1 |       1 |
      #2|                41132016 | Interno           | 7E - PREPAGO TODO EN UNO | CHIP PREPAGO PORTABILIDAD |       24 | 8959300 |           9952 | Equipo Externo | 012950165523558 |           1 |       1 |
      #1|                40330064 | Interno           | 7E - PREPAGO TODO EN UNO | CHIP PREPAGO PORTABILIDAD |       24 | 8959 |           9952 | Equipo Externo | 012950165523558 |           1 |       1 |
      |                40687384 | Interno           | 7E - PREPAGO TODO EN UNO | CHIP PREPAGO PORTABILIDAD |       24 | 8959 |           9985 | Equipo Externo | 012950165523558 |           1 |       1 |
