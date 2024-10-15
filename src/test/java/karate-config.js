function fn() {
    karate.configure('afterScenario', function(){ quitAll() })
    //karate.configure('retry', { count: 10, interval: 3000 })
    init()
}