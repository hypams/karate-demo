@ignore
Feature: Select top menu
  Background:
    * def controls = locator('tademostoreweb', 'basepage')

  Scenario: Select top menu
    * print 'DEFAULT: select top menu ' + menu
    * click(format(controls.topMenu, menu))