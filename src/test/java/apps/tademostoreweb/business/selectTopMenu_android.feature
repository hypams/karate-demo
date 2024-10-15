@ignore
Feature: Select top menu
  Background:
    * def controls = locator('tademostoreweb', 'basepage', 'android')

  Scenario: Select top menu
    * print 'ANDROID: select top menu ' + menu
    * click(format(controls.topMenu, menu))