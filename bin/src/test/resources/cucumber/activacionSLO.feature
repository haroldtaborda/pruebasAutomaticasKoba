Feature: Dar de alta SLO
  COMO Usuario
  QUIERO Activar o desactivar SLO 
  PARA Obtener orden de venta, estado y url

  Background: 
    Given Login en "nc.url" "nc.port" "paht.cliente.residencial" con user "nc.user" en "Username" pass "nc.pass" en "Password" clic en "Iniciar Sesión"

  Scenario Outline: Activacion de SLO
    When estoy en la url "nc.url" "nc.port" "paht.cliente.residencial"
    And ingreso "<numeroTelefono>" en el campo "campo.numero.telefono" para clientes residencial
    And Clic en boton por texto "boton.busqueda"
    And clic en numero telefono "<numeroTelefono>" estado "estado.activo"
    And esperar carga pantalla
    And Clic en boton por texto "boton.productos"
    And ingreso Canal de distribucion "<canalDistribucion>"
    And seleciono combo orden venta "combo.orden.venta.id" para "<numeroTelefono>"
    And Clic en boton por texto "boton.crear"
    And cambiar frame interno
    And espero habilitar servicios y caracteristicas
    And Seleccionar SLO "<tipo>" "<valor>"
    And extraer Numero orden de venta
    And Volver a la Orden
    And Entrada de Orden
    And Seleccionar la orden anterior
    Then Verificar Numero de Cuenta y Guardar URL
    
    Examples: 
      | numeroTelefono | canalDistribucion | tipo      | valor                                |
      | 987500047      | Interno           | SMS       | SMS 1500 Apoyo Discapacidad Auditiva |
      #| 992973843      | Interno           | SMS       | SMS 1500 Apoyo Discapacidad Auditiva |
      #| 992973843      | Interno           | Internet  | Chip ListoPack Combo $5              |