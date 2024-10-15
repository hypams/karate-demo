@ignore
Feature: Select left menu
  Background:
    * def controls = locator('tademostoreweb', 'basepage')

  Scenario: Select left menu
    * print 'DEFAULT: select left menu ' + menu
    * mouse(format(controls.genericLabel, 'All departments')).go()
    * click(format(controls.genericLink, menu))