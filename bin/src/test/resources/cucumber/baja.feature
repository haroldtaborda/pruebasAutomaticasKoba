Feature: realizar baja abonado
  COMO usuario
  QUIERO realizar la baja de un abonado 
  PARA obtener orden de venta, estado y url

  Background: 
    Given Login en "nc.url" "nc.port" "paht.cliente.residencial" con user "nc.user" en "Username" pass "nc.pass" en "Password" clic en "Iniciar Sesión"

  Scenario Outline: Baja Abonado
    When estoy en la url "nc.url" "nc.port" "paht.cliente.residencial"
    And ingreso "<numeroTelefono>" en el campo "campo.numero.telefono" para clientes residencial
    And Clic en boton por texto "boton.busqueda"
    And clic en numero telefono "<numeroTelefono>" estado "<estado.activo>"
    And esperar carga pantalla
    And Clic en boton por texto "boton.productos"
    And ingreso Canal de distribucion "<canalDistribucion>"
    And seleciono combo orden venta "combo.orden.venta.id" para "<numeroTelefono>"
    And Clic en boton por texto "boton.crear"
    And cambiar frame interno
    And espero habilitar servicios y caracteristicas
    #15
    And clic en boton desconectar
    And asignar razon desconexion "<razon>"
    And asignar descripcion de razon "<descripcion>"
    And clic en boton modal desconectar
    #3
    And validar Adendum
    And cambio a miga de pan validar
    And clic boton final generar factura
    And clic boton final enviar
    #6
    And extraer Numero orden de venta
    And Volver a la Orden
    And Entrada de Orden
    And Seleccionar la orden anterior
    Then Verificar Numero de Cuenta y Guardar URL

    Examples: 
              | numeroTelefono | canalDistribucion | razon              | descripcion |
   #prepago   |      998832848 | Interno           | Falta de cobertura | pruebas ti | 
   #pospago   |      983523893 | Interno           | Falta de cobertura | pruebas ti |   
         
