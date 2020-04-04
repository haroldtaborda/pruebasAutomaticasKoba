@ingresar
Feature: dar de alta
  COMO usuario
  QUIERO dar de alta un pre o pos
  PARA obtener orden de venta, esado y url

  Background: 
    Given Init
    And Login en "url.login" con user "url.usser" en "Username" pass "url.pass" en "Password" clic en "Ingresar"

  Scenario Outline: Alta lineas pospago/prepago

    Examples: 
      | ID |
      |  1 |
      |  2 |
