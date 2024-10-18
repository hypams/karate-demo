Feature: Verify logo slider functionality
  Background:
    * def basePage = locator('tademostoreweb', 'basepage')

    * startConfig('chrome_multi')

  Scenario: Verify logo slider functionality
    * scroll(basePage.logoSlide)
    *