package raspi.controller;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("raspi");
        Controller controller = context.getBean(Controller.class);
        controller.run();
    }
}