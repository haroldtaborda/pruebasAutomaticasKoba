Feature: realizar renovacion de equipo
  COMO usuario
  QUIERO realizar la renovación de un equipo
  PARA obtener orden de venta, estado y url

  Background: 
    Given Login en "nc.url" "nc.port" "paht.cliente.residencial" con user "nc.user" en "Username" pass "nc.pass" en "Password" clic en "Iniciar Sesión"

  Scenario Outline: Renovación de equipo
    When estoy en la url "nc.url" "nc.port" "paht.cliente.residencial"
    And ingreso "<numeroTelefono>" en el campo "campo.numero.telefono" para clientes residencial
    And Clic en boton por texto "boton.busqueda"
    And clic en numero telefono "<numeroTelefono>" estado "estado.activo"
    And esperar carga pantalla
    And identificar plan actual
    And identificar procedencia terminal actual
    And Clic en boton por texto "boton.productos"
    And ingreso Canal de distribucion "<canalDistribucion>"
    And seleciono combo orden venta "combo.orden.venta.id" para "<numeroTelefono>"
    And Clic en boton por texto "boton.crear"
    And cambiar frame interno
    And espero habilitar servicios y caracteristicas
    And clic en label desconectar
    And asignar cambio de equipo como razon de desconexion
    And asignar descripcion de razon "<descripcion>"    
    And clic en boton modal desconectar
    And validar equipo "<equipo>" ingresar imei "<imei>" numero cuotas "<numeroCuota>"
    And cambio a miga de pan validar
    #And validar despliegue bandeja notificaciones
    #And cambiar a frame principal
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
                             | numeroTelefono | canalDistribucion | equipo            | imei                | numeroCuota | descripcion             |
             #ok88int-int pre| 984580718      | Interno           | GSM HUAWEI G7     | 139999362251081     | 0           | Prueba cambio de equipo |          
             #ok88int-int pos| 987208545      | Interno           | LTE NOKIA 1 PLUS  | 352893100517178     | 0           | Prueba cambio de equipo |
   #ok88int-int pos con cuota| 995599370      | Interno           | LTE NOKIA 1 PLUS  | 139999362251719     | 3           | Prueba cambio de equipo |  
             #ok88ext-ext pre| 995172408			| Interno           | Equipo Externo    | 528319142706198     | 0           | Prueba cambio de equipo |   
             #ok88ext-ext pos| 987879620			| Interno           | Equipo Externo    | 515016587795331     | 0           | Prueba cambio de equipo |
             #ok88ext-int pre| 979221017			| Interno           | LTE NOKIA 1 PLUS  | 352893100515685     | 0           | Prueba cambio de equipo |
             #ok88ext-int pos| 987708614		  | Interno           | LTE NOKIA 1 PLUS  | 352893100516899     | 0           | Prueba cambio de equipo |
             #ok88int-ext pos| 999002323		  | Interno           | Equipo Externo    | 986263541240299     | 0           | Prueba cambio de equipo |
             #ok88int-ext pre| 984580718 		  | Interno           | Equipo Externo    | 868169742640695     | 0           | Prueba cambio de equipo |