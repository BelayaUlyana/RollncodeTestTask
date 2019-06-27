package com.rollncode;

class SharedPreferences {

    private String timeOfTheLastLaunchService;
    private int counter;

    String getTimeOfTheLastLaunchService() {
        return timeOfTheLastLaunchService;
    }

    void setTimeOfTheLastLaunchService(String timeOfTheLastLaunchService) {
        this.timeOfTheLastLaunchService = timeOfTheLastLaunchService;
    }

    int getCounter() {
        return counter;
    }

    void setCounter(int counter) {
        this.counter = counter;
    }
}
