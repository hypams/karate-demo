Feature:

  Scenario:

    * driver 'https://material.angular.io/components/tooltip/overview'
    * mouse().move('tooltip-overview-example .mat-mdc-button-touch-target').go()
    * def tip = attribute('tooltip-overview-example button', 'mattooltip')
    * match tip == 'Info about the action'