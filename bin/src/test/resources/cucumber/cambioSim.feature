Feature: realizar cambio de sim 
  COMO usuario
  QUIERO realizar cambio de sim 
  PARA obtener orden de venta, estado y url 

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
    And seleciono combo orden venta "combo.orden.venta.completa" para "<numeroTelefono>"
    And Clic en boton por texto "boton.crear"
    And cambiar frame interno
    And espero habilitar servicios y caracteristicas
    
    And Validar Chip Usim "<tipoPago>"
    And Seleccionar ICCD "<iccid>"
    
    
    And cambio a miga de pan validar
    And clic boton final reservar activo
    And clic boton final generar factura
    And clic boton final enviar
    

    And extraer Numero orden de venta
    And Volver a la Orden
    And Entrada de Orden
    And Seleccionar la orden anterior
    Then Verificar Numero de Cuenta y Guardar URL
    
    Examples: 
      | numeroTelefono | canalDistribucion | tipoPago |iccid  |
      #Prepago
      |992973843      | Interno           | 0        | 8959  |
      #|987500047      | Interno           | 0        | 8959 |
      #|987100176      | Interno           | 0        | 8959 |
      #Pospago
      #|998542233     | Interno          | 1         | 8959 |
      
      
      