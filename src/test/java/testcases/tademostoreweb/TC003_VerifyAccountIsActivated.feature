Feature: Verify purchased by guest
  Background:
    * def basePage = locator('tademostoreweb', 'basepage')
    * def registerPage = locator('tademostoreweb', 'registerpage')
    * def accountPage = locator('tademostoreweb', 'accountpage')
    * def baseMailPage = locator('tademostoreweb', 'basemailpage')
    * def publicMailPage = locator('tademostoreweb', 'publicmailpage')

    * def name = getRandomFirstName()
    * def mail = `${name}${getTodayWithFormat('yyyymmdd')}@mailinator.com`

    * def appData = data('tademostoreweb', 'app')
    * def constant = data('tademostoreweb', 'constant')

    * startConfig('chrome_local')

  Scenario: Verify creating and activating an account
    # Step 1: Go to homepage
    * driver appData.url

    # Step 2: Click Log in / Sign up in the top page
    * click(basePage.loginAndSignUpLabel)

    # Step 3: Enter email in Register email textbox

    * input(registerPage.registerEmailTextbox, mail)
    * click(registerPage.acceptCookieButton)
    * scroll(registerPage.registerButton)
    * click(registerPage.registerButton)

    # VP: My Account page displays with the correct email info
    * waitForUrl('/my-account/')
    * match text(accountPage.greetingLabel) contains name.toLowerCase()

    # Step 4: Close Shopping page window
    * driver.close()

    # Step 5: Go to mail server page
    * startConfig('chrome_local')
    * driver appData.MAIL_SERVER

    # Step 6: Click on "Public Inbox" on the top right of mail server page
    * click(baseMailPage.publicInboxIcon)

    # Step 7: Enter email at step 3 to textbox at the top right of the "Public Messages" page
    * input(publicMailPage.findingTextbox, mail)
    * print mail
    * click(publicMailPage.findButton)

    # Step 8: Click on email with subject contained "Sample Website account has been created!"
    * def emailGreeting = format(publicMailPage.emailSubject, constant.TA_MAIL_SUBJECT)
    * waitForExists(emailGreeting)
    * click(emailGreeting)

    # Step 9: Find and click on hyperlink "Click here to set your new password." in the email content
    * switchFrame(publicMailPage.contentIframeSection)
    * waitForExists(publicMailPage.resetPasswordLink)
    * scroll(publicMailPage.resetPasswordLink).click()
    * switchFrame(null)

    # Step 10: Enter new password and confirm password
    * switchPage('/my-account/lost-password/?show-reset-form=true&action=newaccount')
    * input(accountPage.newPasswordTextbox, constant.DEFAULT_PASSWORD)
    * input(accountPage.confirmPasswordTextbox, constant.DEFAULT_PASSWORD)

    # Step 11: Click Save button
    * click(accountPage.saveButton)

    # VP: Message appear and background color is green
    * waitForExists(accountPage.title)
    * match exists(accountPage.successMessageLabel) == true
    * match attribute(accountPage.successMessageLabel, 'class') == constant.COLOR_GREEN_CLASS
