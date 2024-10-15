Feature: Verify menu navigation
  Background:
    * startConfig('chrome_local')
    * def appData = data('tademostoreweb', 'app')
    * def computerAndOfficePage = locator('tademostoreweb', 'computerandofficepage')
    * def aboutUsPage = locator('tademostoreweb', 'aboutuspage')

  Scenario: Verify menu navigation
    # Step 1: Navigate to app
    * driver appData.url

    # Step 2: Navigate to About Us page via top menu
    * call tademostoreweb.selectTopMenu {menu: 'About Us'}

    # VP: Verify 'About Us' page title and 'Satisfaction' label appear
    * waitForUrl('/about-us/')
    * match text(aboutUsPage.title) == 'About us'
    * match exists(aboutUsPage.satisfactionLabel) == true

    # Step 3: Navigate to Computer & Office page via left menu
    * call tademostoreweb.selectLeftMenu {menu: 'Computer & Office'}

    # VP: Verify 'Computer & Office' page title appears
    * waitForUrl('/computer-office/')
    * match text(computerAndOfficePage.title) == 'Computer & Office'










