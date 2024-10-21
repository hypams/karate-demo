Feature: Verify menu navigation
  Background:
    * startConfig('chrome_local')
    * def appData = data('tademostoreweb', 'app')
    * def guestData = data('tademostoreweb', 'guestData')
    * def homePage = locator('tademostoreweb', 'basepage')
    * def cartPage = locator('tademostoreweb', 'cartpage')
    * def shopPage = locator('tademostoreweb', 'shoppage')
    * def checkoutPage = locator('tademostoreweb', 'checkoutpage')
    * def orderReceivedPage = locator('tademostoreweb', 'orderreceivedpage')
    * def paymentMethod = 'Cash on delivery'
    * def blackColor = 'rgb(34, 34, 34)'
    * def greyColor = 'rgb(154, 154, 154)'
    * def boldWeight = 400

  Scenario: Verify puchasing by a Guest
    # Step 1: Go to homepage
    * driver appData.url

    # Step 2: Click on Cart icon in the top right of shopping page
    * click(homePage.cartButton)

    # VP: Message is shown: YOUR SHOPPING CART IS EMPTY
    * waitFor(cartPage.emptyCartMessage)
    * match text(cartPage.emptyCartMessage) == 'YOUR SHOPPING CART IS EMPTY'
    * def cartQuantity = 0

    # Step 3: Click on "Return to Shop" button
    * click(cartPage.returnToShopButton)



    # SHOP page is shown
    * waitFor(shopPage.closeAdPopupButton)
    * click(shopPage.closeAdPopupButton)
    * match getURL() == appData.shopURL
    * match exists(shopPage.shopLabel) == true

    # Step 4: In Shop page, click on "cart" icon to add a product to cart
    * match Number(text(shopPage.cartQuantityCount).trim()) == cartQuantity

    * def productName = 'AirPods'
    * def shopPageProductPrice = text(format(shopPage.productPriceShop, productName))
    * mouse().move(format(shopPage.productNameShop, productName)).go()
    * click(format(shopPage.addToCartIcon, productName))
    * def cartQuantity = 1

    # VP: Cart icon number at the top right of shopping page is increased correctly
    * waitFor(shopPage.addedProductTooltip)
    * waitForResultCount(shopPage.addedProductTooltip, 0)
    * match Number(text(shopPage.cartQuantityCount).trim()) == cartQuantity

    #Step 5: Click on Cart icon in the top right of shopping page
    * click(shopPage.cartButton)

    # VP: Progress is shown correctly: SHOPPING Cart is black bold while CHECKOUT and ORDER STATUS is grey
    * waitFor(cartPage.shoppingCartLink)
    * def shoppingCartBold = script(cartPage.shoppingCartLink, "function(e){ return getComputedStyle(e).fontWeight}")
    * def shoppingCartColor = script(cartPage.shoppingCartLink, "function(e){ return getComputedStyle(e).color}")
    * def checkOutColor = script(cartPage.checkOutLink, "function(e){ return getComputedStyle(e).color}")
    * def orderStatusColor = script(cartPage.orderStatusLink, "function(e){ return getComputedStyle(e).color}")
    * match Number(shoppingCartBold) == boldWeight
    * match shoppingCartColor == blackColor
    * match checkOutColor == greyColor
    * match orderStatusColor == greyColor

    # VP: SHOPPING CART page shows the corrected item (name, price, quantity, total)
    * match text(cartPage.productTitle) == productName
    * match text(cartPage.productPrice) == shopPageProductPrice
    * match Number(value(cartPage.productQuantity)) == cartQuantity
    * match text(cartPage.productSubTotal) == shopPageProductPrice

    # Step 6: Click on "Process to checkout" button
    * click(cartPage.proceedCheckOutButton)

    # VP: Progress is shown correctly: SHOPPING Cart and CHECKOUT are black bold while  ORDER STATUS is grey
    * waitFor(checkoutPage.shoppingCartLink)
    * def shoppingCartBold = script(checkoutPage.shoppingCartLink, "function(e){ return getComputedStyle(e).fontWeight}")
    * def checkOutBold = script(checkoutPage.checkOutLink, "function(e){ return getComputedStyle(e).fontWeight}")
    * def shoppingCartColor = script(checkoutPage.shoppingCartLink, "function(e){ return getComputedStyle(e).color}")
    * def checkOutColor = script(checkoutPage.checkOutLink, "function(e){ return getComputedStyle(e).color}")
    * def orderStatusColor = script(checkoutPage.orderStatusLink, "function(e){ return getComputedStyle(e).color}")
    * match Number(shoppingCartBold) == boldWeight
    * match Number(checkOutBold) == boldWeight
    * match shoppingCartColor == blackColor
    * match checkOutColor == blackColor
    * match orderStatusColor == greyColor

    # Step 7: Enter required fields  then click on "Place Order" button
    * input(checkoutPage.firstNameTextbox, guestData.firstname)
    * input(checkoutPage.lastNameTextbox, guestData.lastname)
    * input(checkoutPage.addressTextbox, guestData.address)
    * select(checkoutPage.countryDropdown, guestData.country)
    * input(checkoutPage.townCityTextbox, getRandomFromArray(guestData.cityList))
    * input(checkoutPage.phoneTextbox, guestData.phone)
    * input(checkoutPage.emailTextbox, format(guestData.email, getTodayWithFormat("yyyyMMdd")))
    * input(checkoutPage.zipCodeTextbox, guestData.postcode)
    * click(checkoutPage.paymentMethodCOD)
    * waitForEnabled(checkoutPage.placeOrderButton)
    * click(checkoutPage.placeOrderButton)

    # VP : Message is shown: "THANK YOU. YOUR ORDER HAS BEEN RECEIVED."
    * waitFor(orderReceivedPage.thankYouMessage)
    * match text(orderReceivedPage.thankYouMessage).toUpperCase() == "THANK YOU. YOUR ORDER HAS BEEN RECEIVED."

    # Order info is shown correctly:
    # - Order number: a number
    # - Date: today
    # - Total: corrected price
    # - Payment method: Cash on Delivery
    # - Product: correct name as at step 4
    # - Email: corrected email address
    * match isNumber(text(orderReceivedPage.orderNumber)) == true
    * match text(orderReceivedPage.orderDate) == getTodayWithFormat("MMMM dd, yyyy")
    * match text(orderReceivedPage.total) == shopPageProductPrice
    * match text(orderReceivedPage.paymentMethod) == paymentMethod
    * match text(orderReceivedPage.orderItem) == productName
