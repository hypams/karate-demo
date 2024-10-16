Feature: Verify purchased by guest
  Background:
    * def name = read('classpath:common/helper/javascript/random-helper.js')

    * startConfig('chrome_local')
    * def appData = data('tademostoreweb', 'app')
    * def basePage = locator('tademostoreweb', 'basepage')
    * def registerPage = locator('tademostoreweb', 'registerpage')
    * def accountPage = locator('tademostoreweb', 'accountpage')

    * def name = name().getRandomFirstName()
    * def email = `${name}@mailinator.com`

  Scenario: Verify creating and activating an account.
    # Step 1: Go to homepage
    * driver appData.url

    # Step 2: Click Log in / Sign up in the top page
    * click(basePage.loginAndSignUpLabel)
    * input(registerPage.registerEmailTextbox, email)
    * click(registerPage.acceptCookieButton)
    * scroll(registerPage.registerButton)
    * click(registerPage.registerButton)

    # VP
    * waitForUrl('/my-account/')
    * match text(accountPage.greetingLabel) contains name.toLowerCase()
