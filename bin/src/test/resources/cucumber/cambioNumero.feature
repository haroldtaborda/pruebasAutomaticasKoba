Feature: realizar el cambio de numero 
	COMO usuario
  QUIERO realizar el cambio de numero 
  PARA actualizar 

Background: 
	Given Login en "nc.url" "nc.port" "paht.cliente.residencial" con user "nc.user" en "Username" pass "nc.pass" en "Password" clic en "Iniciar Sesión" 
	
Scenario Outline: Cambio de número 
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
	# Cambio GC (cambio de numero)
	And activo oro "<cambioOro>" 
	And valido cuotas "<validoCuota>" 
	And asigno numero "<nuevoNumero>"
	# 
	And cambio a miga de pan validar
	And clic boton final generar factura
	And clic boton final enviar
	#
	And extraer Numero orden de venta
	And Volver a la Orden
	And Entrada de Orden
	And Seleccionar la orden anterior
	Then Verificar Numero de Cuenta y Guardar URL 
	
	
	Examples: 
		| numeroTelefono | canalDistribucion | tipo      | cambioOro                              | validoCuota|nuevoNumero|
		#| 987500047      | Interno           | SMS       | SMS 1500 Apoyo Discapacidad Auditiva 	| cambioOro |987 		|
		| 958691111      | Interno           | Internet  | No                						| No        |9980|
		# Prepago                                                                                               
		# | 958691111      | Interno           | Internet  | No 									| Si        |985       |
		# Pospago                                                                                               
		# | 998542233      | Interno           | Internet  | No 									| Si        |998000435|