Feature: realizar cambio de plan
  COMO usuario
  QUIERO realizar cambio de plan 
  PARA obtener orden de venta, esado y url

  Background: 
    Given Login en "nc.url" "nc.port" "paht.cliente.residencial" con user "nc.user" en "Username" pass "nc.pass" en "Password" clic en "Iniciar Sesión"

  Scenario Outline: cambio de plan
    When estoy en la url "nc.url" "nc.port" "paht.cliente.residencial"
    And ingreso "<numeroTelefono>" en el campo "campo.numero.telefono" para clientes residencial
    And Clic en boton por texto "boton.busqueda"
    And clic en numero telefono "<numeroTelefono>" estado "estado.activo"
    #
    And esperar carga pantalla
    And validar mismo plan "<plan>"
    And Clic en boton por texto "boton.productos"
    And ingreso Canal de distribucion "<canalDistribucion>"
    And seleciono combo orden venta "combo.orden.venta.id" para "<numeroTelefono>"
    And Clic en boton por texto "boton.crear"
    #
    And cambiar frame interno
    And espero habilitar servicios y caracteristicas
    #
    And abrir menu Horizontal
    And clic en icono lupa
    And ingresar "<plan>" en input lupa
    And selecionar plan cambio "<plan>"
    And validar tipo de cambio plan "<plan>"
    #
    And validar Adendum
    And selecionar nuevo plan "<numeroTelefono>"
    And validar crear cliente cambio plan
    #
    And cambio a miga de pan validar
    And clic boton final configurar contrato
    And clic boton final enviar
    #
    And extraer Numero orden de venta
    And Volver a la Orden
    And Entrada de Orden
    And Seleccionar la orden anterior
    Then Verificar Numero de Cuenta y Guardar URL

    Examples: 
      | numeroTelefono | canalDistribucion | plan                            |
      |      992650076 | Interno           | 9G - PLAN FUSIÓN ILIMITADO GOLD |
