@login
Feature: OrangeHRM login
  As a tester
  I want to do the login
  For complete de authentication process

  Scenario: Validar login
    Given AnalistaHR abre la pagina de orangeHRM
    When ingresa las credenciales de login
    Then debera ver el nombre al ingresar