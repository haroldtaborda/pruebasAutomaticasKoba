Feature: realizar Suspensión/Reanudación de Línea
  COMO usuario
  QUIERO realizar suspender o reanudar la línea
  PARA obtener orden de venta, estado y url

  Background: 
    Given Login en "nc.url" "nc.port" "paht.cliente.residencial" con user "nc.user" en "Username" pass "nc.pass" en "Password" clic en "Iniciar Sesión"

  Scenario Outline: Suspensión de Línea
    When estoy en la url "nc.url" "nc.port" "paht.cliente.residencial"
    And ingreso "<numeroTelefono>" en el campo "campo.numero.telefono" para clientes residencial
    And Clic en boton por texto "boton.busqueda"
    And clic en numero telefono "<numeroTelefono>" estado "<estado>"
    And esperar carga pantalla
    And Clic en boton por texto "boton.productos"
    And ingreso Canal de distribucion "<canalDistribucion>"
    And seleciono combo orden venta "combo.orden.venta.completa" para "<numeroTelefono>"
    And Clic en boton por texto "boton.crear"
    And cambiar frame interno
    And espero habilitar servicios y caracteristicas
    #15
    
    And Clic en boton por texto en servicios y caracteristicas "<boton>"
    And Asignar razon del proceso texto "<razon>" con "<boton>" y "<equipo>" 
    And Validar razon del proceso texto "<razon>" y "<motivo>" 

    And cambio a miga de pan validar  
    And clic boton final enviar
    #6
    And extraer Numero orden de venta
    And Volver a la Orden
    And Entrada de Orden
    And Seleccionar la orden anterior
    Then Verificar Numero de Cuenta y Guardar URL

    Examples: 
      | numeroTelefono | canalDistribucion | estado            | boton           | razon        | desactivar | equipo | motivo           |
      #Pospago
      #| 998542233      | Interno           | estado.activo     | boton.suspender | Robo         | si         | 1      | Motivo de prueba |
      #| 998542233      | Interno           | estado.suspendido | boton.reanudar  | Robo         | si         | 0      | Motivo de prueba |
      #| 998725634      | Interno           | estado.activo     | boton.suspender | Robo         | si         | 1      | Motivo de prueba |      
      | 998397336      | Interno           | estado.suspendido | boton.reanudar  | Robo         | si         | 0      | Motivo de prueba |  
      #| 998397336      | Interno           | estado.activo     | boton.suspender | Robo         | si         | 1      | Motivo de prueba |      
       #Prepago
      #| 998657592      | Interno           | estado.activo     | boton.suspender | Robo         | si         | 1      | Motivo de prueba |
      #| 998657592      | Interno           | estado.suspendido | boton.reanudar  | Robo         | si         | 0      | Motivo de prueba |
