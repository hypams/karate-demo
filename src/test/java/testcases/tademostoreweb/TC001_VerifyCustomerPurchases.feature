Feature: Verify Customer Purchases
  Background:
    * def appData = data('tademostoreweb', 'app')
    * def basePage = locator('tademostoreweb', 'basepage')
    * def cartPage = locator('tademostoreweb', 'cartpage')
    * def checkoutPage = locator('tademostoreweb', 'checkoutpage')
    * def orderreceivedPage = locator('tademostoreweb', 'orderreceivedpage')

    * def firstName = getRandomFirstName()
    * def lastName = getRandomLastName()
    * def street = getRandomStreet()
    * def city = getRandomCity()
    * def phone = getRandomPhone()
    * def email = `${firstName}@mailinator.com`

    * def product = data('tademostoreweb', 'product')
    * def constant = data('tademostoreweb', 'constant')

    * startConfig('chrome_local')

  Scenario: Verify Customer Purchasest
    # Step 1: Go to homepage
    * driver appData.url

    # Step 2: Click on Cart icon in the top right of shopping page
    * mouse().move(basePage.cartButton).click()

    #VP: Verify massage is shown when cart is empty
    * waitFor(cartPage.emptyMessage)
    * match exists(cartPage.emptyMessage) == true

    # Step 3: Click on "Return to Shop" button
    * click(cartPage.returnButton)

    # Step 4: Click on "cart" icon to add a product to cart
    * mouse().move(basePage.closeNewsLetterButton).click()
    * call tademostoreweb.addProductToCart {name: 'AirPods'}
    * delay(2000)

    #VP: Cart icon number at the top right of shopping page is increased correctly
    * match parseInt(text(basePage.cartNumberIcon).trim()) == product.quantity

    # Step 5: Click on Cart icon in the top right of shopping page
    * mouse().move(basePage.cartButton).click()
    * waitForUrl('/cart/')


    # VP: Progress is shown correctly
    * match attribute(format(cartPage.processLabel, 'Shopping cart'), 'class') contains constant.COLOR_BLACK_CLASS
    * match attribute(format(cartPage.processLabel, ' Checkout'), 'class') !contains constant.COLOR_BLACK_CLASS
    * match attribute(format(cartPage.processLabel, ' Order'), 'class') !contains constant.COLOR_BLACK_CLASS

    * mouse().move(cartPage.acceptCookieButton).click()
    * delay(3000)

    # VP: SHOPPING CART page shows the corrected item
    * match parseInt(attribute(cartPage.quantityLabel, 'value')) == product.quantity
    * match text(cartPage.productName) == product.name
    * match text(cartPage.productPrice) contains (product.price.toString())

    # Step 6: Click on "Process to checkout" button
    * mouse().move(cartPage.checkoutButton).click()

    # VP: Click on "Process to checkout" button
    * match attribute(format(cartPage.processLabel, 'Shopping cart'), 'class') contains constant.COLOR_BLACK_CLASS
    * match attribute(format(cartPage.processLabel, ' Checkout'), 'class') contains constant.COLOR_BLACK_CLASS
    * match attribute(format(cartPage.processLabel, ' Order'), 'class') !contains constant.COLOR_BLACK_CLASS


    # Step 7: Enter required fields  then click on "Place Order" button
    * input(checkoutPage.firstNameField, firstName)
    * input(checkoutPage.lastNameField, lastName)
    * select(checkoutPage.countryCombobox, '{}Vietnam')
    * input(checkoutPage.streetTextbox,  street)
    * input(checkoutPage.cityTextbox, city)
    * input(checkoutPage.phoneTextbox, phone)
    * input(checkoutPage.emailTextbox, email)
    * scroll(checkoutPage.cashRadioButton)
    * click(checkoutPage.cashRadioButton)
    * click(checkoutPage.placeOrderButton)
    * delay(10000)

    #VP: Verify order is received
    * match exists(orderreceivedPage.orderSuccessLabel) == true
    * match isNumber(text(orderreceivedPage.orderNumberLabel)) == true
    * match text(orderreceivedPage.dateLabel) == getCurrentDateAndFormat()
    * def price = product.price * product.quantity
    * print text(orderreceivedPage.totalAmountLabel)
    * match (text(orderreceivedPage.totalAmountLabel)) contains price.toString()
    * match (text(orderreceivedPage.paymentMethodLabel)) == "Cash on delivery"
    * match (text(orderreceivedPage.productNameLabel)) == product.name
