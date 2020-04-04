@ingresar
Feature: Ingresar App
  COMO usuario
  QUIERO ingresar a KOBA
  PARA verificar el login

  Background: 
    Given Init
    And Login en "url.login" con user "url.usser" en "Username" pass "url.pass" en "Password" clic en "Ingresar"
  Scenario: 
    When Ingreso panel izquierdo "panelizquierdogestion"
    And Ingresar proceso de gestiones "gestionperfiles"
    And Seleciono el primer registro
    And Ingreso al menu registro "verdetalles"
    And Doy clic en boton volver
    #Then Cerrar Cesion

