@ignore
Feature: Add product to cart
  Background:
    * def controls = locator('tademostoreweb', 'shoppage')

  Scenario: Select left menu
    * print 'DEFAULT: Add ' + name + ' To Cart'
    * mouse().move(format(controls.productName, name))
    * waitFor(format(controls.product, name)).click()
