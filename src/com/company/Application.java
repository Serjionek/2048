package com.company;

public class Application {
    public void run() {
        View view = new View();
        Controller controller = new Controller();
        view.setController(controller);
        controller.setView(view);
        view.create();
        controller.startGame();
    }
}