Feature: realizar transferencia de beneficiario
  COMO usuario
  QUIERO realizar transferencia de beneficiario
  PARA obtener orden de venta, esado y url

  Background: 
    Given Login en "nc.url" "nc.port" "paht.cliente.residencial" con user "nc.user" en "Username" pass "nc.pass" en "Password" clic en "Iniciar Sesión"

  Scenario Outline: Transferencia beneficiario
    When estoy en la url "nc.url" "nc.port" "paht.cliente.residencial"
    And ingreso "<numeroTelefono>" en el campo "campo.numero.telefono" para clientes residencial
    And ingreso "<nombre>" en el campo "campo.nombre.cuenta" para clientes residencial
    And Clic en boton por texto "boton.busqueda"
    And clic en numero telefono "<numeroTelefono>" estado "<estado>"
    And esperar carga pantalla
    And Clic en boton por texto "boton.productos"
    And ingreso Canal de distribucion "<canalDistribucion>"
    And seleciono combo orden venta "combo.orden.venta.id" para "<numeroTelefono>"
    And Clic en boton por texto "boton.crear"
    And cambiar frame interno
    And espero habilitar servicios y caracteristicas
    #And Validar cargando
    And Clic en link por texto "Cambiar el propietario" espera "3"
    #Llenar campos propietario y boton
    And Ingresar informacion modal trasferencia beneficiario "<cuentaDesino>" "<ubicacion>" "<cuentaFacturacionDestino>" "<plan>" y clic en "boton.cambiobeneficiario"
    And validar Adendum
    And cambio a miga de pan validar
    And clic boton final configurar contrato na
    And clic boton final enviar
    And extraer Numero orden de venta
    And Volver a la Orden
    And Entrada de Orden
    And Seleccionar la orden anterior
    Then Verificar Numero de Cuenta y Guardar URL

    Examples: 
      | numeroTelefono | nombre  | canalDistribucion | cuentaDesino                      | ubicacion                         | cuentaFacturacionDestino | plan                            | estado        |
      |      998989772 | cuatro prueba | Interno           | GUILLERMO ARTURO CIFUENTES GARZON | Ubicacion del cliente GUILLERMO ARTURO CIFUENTES GARZON | 19172145 | FULL MEGAS EMPRESAS GOLD | estado.activo |
      