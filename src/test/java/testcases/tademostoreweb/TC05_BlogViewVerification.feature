Feature: Verify Blog View
  Background:
    * startConfig('chrome_local')
    * def appData = data('tademostoreweb', 'app')
    * def blogPage = locator('tademostoreweb', 'blogpage')

  Scenario: Verify viewing the previous blog
    # Step 1: Go to homepage
    * driver appData.url

    # Step 2: Click on the “Blog” menu item.
    * call tademostoreweb.selectTopMenu {menu: 'Blog'}

    # Step 3: Click on the “Continue Reading” button for the first blog post.
    * click(blogPage.continueReadingButton)
#    * waitForText('h1.title','My First Post')

    # Step 4: Hover over the arrow button to show the title of the previous blog post.
    * mouse().move(blogPage.leftArrow).go()

    # Step 5: Click on the title of the previous blog post.
    * click(blogPage.postDetailLeftArrow)

    # VP: The correct blog post detail page is displayed with the correct title.
    * match text(blogPage.title) contains 'Fandom’s League of Legends'
