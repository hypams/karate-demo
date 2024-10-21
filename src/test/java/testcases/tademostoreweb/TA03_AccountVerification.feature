Feature: Verify account
  Background:
    * startConfig('chrome_local')
    * def appData = data('tademostoreweb', 'app')
    * def guestData = data('tademostoreweb', 'guestData')
    * def homePage = locator('tademostoreweb', 'basepage')
    * def registerPage = locator('tademostoreweb', 'registerpage')
    * def accountPage = locator('tademostoreweb', 'accountpage')
    * def mailPage = locator('tademostoreweb', 'mailpage')
    * def loginPage = locator('tademostoreweb', 'loginpage')
    * def greenColor = 'rgb(46, 125, 50)'

  Scenario: Verify creating and activating account
    # Step 1: Go to homepage
    * driver appData.url

    # Step 2: Click Log in / Sign up in the top page
    * mouse(format(homePage.genericLabel, 'Log in / Sign up')).click()

    # Step 3: Enter email in Register email textbox
    * def email = format(guestData.email, getTodayWithFormat('hhmmss'), getTodayWithFormat("yyyyMMdd"))
    * input(registerPage.emailTextbox, email)
    * click(registerPage.acceptCookieButton)
    * scroll(registerPage.registerButton)
    * click(registerPage.registerButton)

    # VP: My Account page displays with the correct email info
    * waitFor(accountPage.title)
    * match text(accountPage.greetingLabel) contains email.split('@')[0]

    # Step 4: Close Shopping page window
    * driver.close()

    # Step 5: Go to mail server page
    * startConfig('chrome_local')
    * driver appData.mailServerURL

    # Step 6: Click on "Public Inbox" on the top right of mail server page
    * click(format(mailPage.genericLink, 'Public Inbox'))

    # Step 7: Enter email at step 3 to textbox at the top right of the "Public Messages" page
    * input(mailPage.findTextBox, email)
    * click(mailPage.goButton)

    # Step 8: Click on email with subject contained "Sample Website account has been created!"
    * waitForResultCount(mailPage.emailGreeting, 1)
    * click(mailPage.emailGreeting)

    # Step 9: Find and click on hyperlink "Click here to set your new password." in the email content
    * switchFrame(mailPage.contentIframeSection)
    * scroll(format(mailPage.genericLink, 'Click here to set your new password.')).click()
    * switchFrame(null)

    # Step 10: Enter new password and confirm password
    * switchPage('/my-account/lost-password/?show-reset-form=true&action=newaccount')
    * input(accountPage.newPasswordTextbox, guestData.password)
    * input(accountPage.confirmPasswordTextbox, guestData.password)

    # Step 11: Click Save button
    * click(accountPage.saveButton)

    # VP: Message appear and background color is green
    * def statusColor = script(accountPage.successMessage, "function(e){ return getComputedStyle(e).getPropertyValue('background-color')}")
    * match exists(accountPage.successMessage) == true
    * match text(accountPage.successMessage).trim() == 'Your password has been reset successfully.'
    * match statusColor == greenColor

    # step 12: Enter email and password to login to shopping page
    * input(loginPage.usernameTextbox, email)
    * input(loginPage.passwordTextbox, guestData.password)
    * click(loginPage.rememberCheckbox)
    * click(loginPage.loginButton)

    # VP: MY ACCOUNT page is displayed with corrected user name and email
    * match text(accountPage.greetingLabel) contains email.split('@')[0]
