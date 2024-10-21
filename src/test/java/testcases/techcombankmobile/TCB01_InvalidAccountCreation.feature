Feature: Verify Account Creation with Invalid Mobile Number

  Background: Set up page objects
    * startConfig(karate.env)
    * def testData = data('techcombankmobile', 'TCB001')
    * def welcomePage = locator('techcombankmobile', 'basepage')
    * def accountOptionPage = locator('techcombankmobile', 'basepage')
    * def termsConfirmationPage = locator('techcombankmobile', 'termsconfirmationpage')
    * def verifyMobilePhonePage = locator('techcombankmobile', 'verifymobilephonepage')

  Scenario: Account Creation with Invalid Mobile Number
    #1. Launch the Techcombank mobile app.

    #2. Tap 'New to Techcombank'.
    * click(format(welcomePage.dynamicButton, 'New to Techcombank?'))

    #VP: Welcome screen displays: "Welcome! Which option are you interested in?"
    * waitFor(format(accountOptionPage.dynamicText, testData.welcomeMessage))

    #3. Select 'Basic Current Account'.
    * call techcombankmobile.scrollDown
    * click(format(accountOptionPage.dynamicText, 'Basic Current Account'))

    #4. Uncheck the Terms and Conditions checkbox.
    * waitFor(termsConfirmationPage.termUnCheckbox)
    * click(termsConfirmationPage.termUnCheckbox)

    #VP: 'Register now' button is disabled.
    * match attribute(format(termsConfirmationPage.dynamicButton, 'Register now'), 'enabled') == 'false'

    #5. Check the Terms and Conditions checkbox
    * click(termsConfirmationPage.termCheckbox)

    #6. Tap 'Register now'.
    * click(format(termsConfirmationPage.dynamicButton, 'Register now'))

    #VP: Screen displays: "Let's verify your mobile number." The 'Continue' button is disabled, and the line under the mobile number field is blue.
    * waitFor(format(verifyMobilePhonePage.dynamicText, testData.verifyMobileNumberText))
    * match attribute(format(verifyMobilePhonePage.dynamicButton, 'Continue'), 'enabled') == 'false'

    #7. Input invalid mobile number '9999999999'.
    * clear(verifyMobilePhonePage.phoneTextbox)
    * input(verifyMobilePhonePage.phoneTextbox, '9999999999')

    #VP:
    #iOS: 'Continue' button remains disabled, no error message displayed, and the line under the mobile number field turns red.
    #Android: 'Continue' button remains disabled, error message "Invalid phone number. Please input again" displayed in red, and the line under the mobile number field stays blue.
    * match attribute(format(verifyMobilePhonePage.dynamicButton, 'Continue'), 'enabled') == 'false'
    * def doesTextExist = exists(format(verifyMobilePhonePage.dynamicText, testData.invalidPhoneError))
    * def expectedErrorExist = platform() == 'ios' ? false : true
    * match doesTextExist == expectedErrorExist

    #8. Input invalid format '0000000000'.
    * clear(verifyMobilePhonePage.phoneTextbox)
    * input(verifyMobilePhonePage.phoneTextbox, '0000000000')

    #VP: 'Continue' button is enabled, and the line remains blue.
    * match attribute(format(verifyMobilePhonePage.dynamicButton, 'Continue'), 'enabled') == 'true'

    #9. Tap 'Continue'.
    * click(format(verifyMobilePhonePage.dynamicButton, 'Continue'))

    #VP: Error message: "Invalid mobile number format. Please enter numbers only. (OBS-OTP-005)"
    * match exists(format(verifyMobilePhonePage.dynamicText, testData.obsotp005Error)) == true

    #10. Tap 'Close' to dismiss the error.
    * click(format(verifyMobilePhonePage.dynamicButton, 'Close'))

    #VP: Verify the invalid mobile number is removed and the 'Continue' button is disabled.
    * match exists(format(verifyMobilePhonePage.dynamicText, testData.obsotp005Error)) == false
    * match text(verifyMobilePhonePage.phoneTextbox) == ''
    * match attribute(format(verifyMobilePhonePage.dynamicButton, 'Continue'), 'enabled') == 'false'
