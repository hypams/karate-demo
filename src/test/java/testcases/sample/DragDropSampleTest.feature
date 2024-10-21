Feature:

  Scenario:
    * startConfig('chromedriver_multi')
    * driver 'https://www.w3schools.com/html/html5_draganddrop.asp'
    * delay(5000)
    # document.querySelector('css selector')
    #* script("document.querySelector('#div2').appendChild(document.querySelector('#drag1'))")
    #* mouse('#drag1').down().move('#div2').up()
    * mouse("//a[@href='html5_draganddrop.asp']").down().move('#tnb-google-search-input').up()
    * delay(15000)