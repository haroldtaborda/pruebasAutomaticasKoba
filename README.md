# janusssp-regre-pru-auto

Repositorio para las pruebas automatizadas regresivas en janus y ssp por ahora

## Ejecutar

1. Realizar la compilación, verificar y ejecución de todos los flujos, la salida es el directorio `janusssp-regre-pruauto\target`

   
   $ mvn clean install
   
2. Realizar la compilación, verificar y ejecución flujo especifico.
   
   
   $ mvn -Dcucumber.options="--tags '@slo'" clean install
   
   $ mvn -Dcucumber.options="--tags '@altas'" clean install
   
   $ mvn -Dcucumber.options="--tags '@bajas'" clean install
   
   $ mvn -Dcucumber.options="--tags '@cambioNumero'" clean install
   
   $ mvn -Dcucumber.options="--tags '@cambioPlan'" clean install
   
   $ mvn -Dcucumber.options="--tags '@cambioSim'" clean install
   
   $ mvn -Dcucumber.options="--tags '@clienteResidencial'" clean install
   
   $ mvn -Dcucumber.options="--tags '@clienteEmpresarial'" clean install
   
   $ mvn -Dcucumber.options="--tags '@equipoSinLinea'" clean install
   
   $ mvn -Dcucumber.options="--tags '@facturasMiselaneas'" clean install
   
   $ mvn -Dcucumber.options="--tags '@generacionDisputas'" clean install
   
   $ mvn -Dcucumber.options="--tags '@renovacionEquipo'" clean install
   
   $ mvn -Dcucumber.options="--tags '@suspenderReaunudar'" clean install
   
   $ mvn -Dcucumber.options="--tags '@transferenciaBeneficiario'" clean install

