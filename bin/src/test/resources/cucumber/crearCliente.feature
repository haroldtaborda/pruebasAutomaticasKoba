Feature: crear cliente
  COMO usuario
  QUIERO crear un cliente
  PARA ser usado en los otros escenario

  Background: 
    Given Login en "nc.url" "nc.port" "paht.informacion.cliente" con user "nc.user" en "Username" pass "nc.pass" en "Password" clic en "Iniciar Sesión"

  Scenario Outline: crear cliente facturacion pre pos
    When estoy en la url "nc.url" "nc.port" "paht.informacion.cliente"
    And Clic en boton por texto "boton.nuevo.cliente.residencial"
    And ingreso informacion modal "<numeroDocumento>" "<nombre>" "<apellido>" "<correo>"
    # And obtener error bandeja
    And Clic en boton por texto "boton.crear.consultar.detalles"
    And Clic en link por texto "Cuentas de facturación"
    And Clic en boton nueva cuenta de facturacion por texto "boton.nueva.cuenta.facturacion.pospago"
    And asignar Subtipo Cuenta "<cuenta>"
    And Clic en boton por texto "boton.crear"
    And Clic en boton nueva cuenta de facturacion por texto "boton.nueva.cuenta.facturacion.prepago"
    And Clic en boton por texto "boton.crear"
    Then extraer Numero Cuenta

    Examples: 
      | numeroDocumento | nombre  | apellido | correo           | cuenta  |
      |      1094905030 | treinta | prueba   | nueve@correo.com | Hibrido |
