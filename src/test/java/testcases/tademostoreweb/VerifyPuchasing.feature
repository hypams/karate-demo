Feature: Verify purchased by guest
  Background:
    * startConfig('chrome_local')
    * def appData = data('tademostoreweb', 'app')
    * def basePage = locator('tademostoreweb', 'basepage')
    * def cartPage = locator('tademostoreweb', 'cartpage')
#    * def shopPage = locator('tademostoreweb', 'shoppage')
#    * def computerAndOfficePage = locator('tademostoreweb', 'computerandofficepage')
#    * def aboutUsPage = locator('tademostoreweb', 'aboutuspage')

  Scenario: Verify purchase by guest
    * driver appData.url
    * mouse().move(basePage.cartButton).click()
    * waitFor(cartPage.emptyMessage)
    * match exists(cartPage.emptyMessage) == true
    * click(cartPage.returnButton)
    * mouse().move(basePage.closeNewsLetterButton).click()
    * call tademostoreweb.addProductToCart {name: 'AirPods'}
    * match text(basePage.cartNumberIcon) == '1'

