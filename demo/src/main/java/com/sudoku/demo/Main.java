package com.sudoku.demo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

@SpringBootApplication
public class Main extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() throws Exception {
        ApplicationContextInitializer<GenericApplicationContext> initializer =
        new ApplicationContextInitializer<GenericApplicationContext>() {

            @Override
            public void initialize(GenericApplicationContext ac) {
                // TODO Auto-generated method stub
                ac.registerBean(Application.class, ()->JavafxApplication.this);
                ac.registerBean(Parameters.class, this::getParameters);
                ac.registerBean(HostServices.class, this::getHostServices);
            }
            
        }

        this.context = new SpringApplicationBuilder(DemoApplication.class)
                        .initializers().run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MainUI.fxml"));
            Scene scene = new Scene(root);

            stage.setTitle("Sudoku");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void stop() throws Exception {
        this.context.close();
        Platform.exit();
    }

    @GetMapping(value = "/")
    public static void main(String[] args) {
        Application.launch( Main.class, args);
    }
}