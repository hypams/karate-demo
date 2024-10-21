Feature: Verify shopping cart
  Background:
    * startConfig('chrome_local')
    * def appData = data('tademostoreweb', 'app')
    * def homePage = locator('tademostoreweb', 'basepage')
    * def cartPage = locator('tademostoreweb', 'cartpage')
    * def shopPage = locator('tademostoreweb', 'shoppage')

  Scenario: Verify shopping cart is clear
    # Step 1: Go to homepage
    * driver appData.url

    # Steo 2: Click on Shop link
    * call tademostoreweb.selectTopMenu {menu: 'Shop'}

    # Step 3: Click on "cart" icon to add a product to cart
    * waitFor(shopPage.closeAdPopupButton)
    * click(shopPage.closeAdPopupButton)

    * def productName = 'AirPods'
    * def shopPageProductPrice = text(format(shopPage.productPriceShop, productName))
    * mouse().move(format(shopPage.productNameShop, productName)).go()
    * click(format(shopPage.addToCartIcon, productName))
    * def cartQuantity = 1

    #VP: Cart icon number at the top right of shopping page is increased correctly
    * waitFor(shopPage.addedProductTooltip)
    * waitForResultCount(shopPage.addedProductTooltip, 0)
    * match Number(text(homePage.cartQuantityCount).trim()) == cartQuantity

    # Step 4: Click on Cart icon in the top right of shopping page
    * click(homePage.cartButton)

    # Step 5: Click on "Clear Shopping Cart"
    * scroll(cartPage.clearCartButton)
    * click(cartPage.clearCartButton)

    # VP: Pop-up "Are you sure" is shown
    * match driver.dialogText == 'Are you sure?'

    # Step 6: Accept alert
    * dialog(true)

    # VP: Pop-up "Are you sure" is shown
    * match exists(cartPage.emptyCartMessage) == true
