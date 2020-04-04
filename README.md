# Pruebas Automaticas Koba

Repositorio para las pruebas automatizadas de KOBA

## Ejecutar

1. Realizar la compilación, verificar y ejecución de todos los flujos, la salida es el directorio `pruebasAutomaticasKoba\target`

   
   $ mvn clean install
   
2. Realizar la compilación, verificar y ejecución flujo especifico.
   
   
   $ mvn -Dcucumber.options="--tags '@nombreTag'" clean install

