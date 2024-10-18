Feature: Verify Shopping cart is cleared
  Background:
    * def appData = data('tademostoreweb', 'app')
    * def basePage = locator('tademostoreweb', 'basepage')
    * def cartPage = locator('tademostoreweb', 'cartpage')

    * def product = data('tademostoreweb', 'product')

    * startConfig('chrome_local')

  Scenario: Verify Shopping cart is cleared
    # Step 1: Go to homepage
    * driver appData.url

    # Steo 2: Click on Shop link
    * call tademostoreweb.selectTopMenu {menu: 'Shop'}

    * waitForUrl('/shop/')

    # Step 3: Click on "cart" icon to add a product to cart
    * mouse().move(basePage.closeNewsLetterButton).click()
    * call tademostoreweb.addProductToCart {name: 'AirPods'}
    * delay(2000)

    #VP: Cart icon number at the top right of shopping page is increased correctly
    * match parseInt(text(basePage.cartNumberIcon).trim()) == product.quantity

    # Step 4: Click on Cart icon in the top right of shopping page
    * mouse().move(basePage.cartButton).click()
    * waitForUrl('/cart/')

    # Step 5: Click on "Clear Shopping Cart"
    * scroll(cartPage.clearCartButton)
    * click(cartPage.clearCartButton)

    # VP: Pop-up "Are you sure" is shown
    * match driver.dialogText == 'Are you sure?'

    # Step 6: Accept alert
    * dialog(true)

    # VP: Pop-up "Are you sure" is shown
    * match exists(cartPage.emptyMessage) == true