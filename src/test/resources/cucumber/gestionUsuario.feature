@ingresar
Feature: Ingresar App
  COMO usuario
  QUIERO ingresar a KOBA
  PARA verificar el login

  Background: 
    Given Init
    And Login en "url.login" con user "url.usser" en "Username" pass "url.pass" en "Password" clic en "Ingresar"
  Scenario Outline: 
    When Ingreso panel izquierdo "panelizquierdogestion"
    And Ingresar proceso de gestiones "gestionUsuario"

    Examples: 
      | ID |
      |  1 |
