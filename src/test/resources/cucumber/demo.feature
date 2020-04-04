@ingresar
Feature: Ingresar App
  COMO usuario
  QUIERO ingresar a KOBA
  PARA verificar el login

  Background: 
    Given Init

  Scenario Outline: 
     When Login en "url.login" con user "url.usser" en "Username" pass "url.pass" en "Password" clic en "Ingresar"

    Examples: 
      | ID |
      |  1 |
      |  2 |
