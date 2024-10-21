Feature: Verify Logo Slide
  Background:
    * startConfig('chromedriver_multi')
    * def appData = data('tademostoreweb', 'app')
    * def homePage = locator('tademostoreweb', 'basepage')

  Scenario: Verify Logo Slider functionality on Home page
    # Step 1: Go to home page
    * driver appData.url

    # Step 2: Slide the logo panel to the next logo
    * scroll(homePage.appleLogo)
    * delay(2000)
    * mouse(homePage.appleLogo).down().move(homePage.canonLogo).up()
    * delay(2000)

    # VP: The next logo (Canon) is displayed in the visible area.
    * match exists(homePage.canonLogo) == true
