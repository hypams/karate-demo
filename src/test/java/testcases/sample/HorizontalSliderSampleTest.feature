Feature: HorizontalSliderSampleTest

  Scenario:
    * startConfig('chromedriver_multi')
    * driver 'https://demo.testarchitect.com/'
    * delay(5000)
    * scroll("//img[@src='https://demo.testarchitect.com/wp-content/uploads/2016/05/logo-1-1-1.png']")
    * delay(5000)
    * mouse("//img[@src='https://demo.testarchitect.com/wp-content/uploads/2016/05/logo-1-1-1.png']").down().move("//input[@value='Sign up']").up()
    * delay(5000)