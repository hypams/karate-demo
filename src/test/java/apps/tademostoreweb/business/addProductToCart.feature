@ignore
Feature: add product to cart
  Scenario:
    # In Shop page, click on "cart" icon to add a product to cart
    * def shopPageProductPrice = text(format(shopPage.productPriceShop, productName))
    * mouse().move(format(shopPage.productNameShop, productName)).go()
    * click(format(shopPage.addToCartIcon, productName))

    # VP: Cart icon number at the top right of shopping page is increased correctly
    * waitFor(shopPage.addedProductTooltip)