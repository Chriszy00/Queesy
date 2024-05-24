package com.quiz.quizapp;

import com.quiz.quizapp.controller.MusicController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class QuizMainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(QuizMainApplication.class.getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        stage.show();

        String cloudinaryMusicUrl = "https://res.cloudinary.com/drhiswbtx/video/upload/v1716456882/Music/default_music_agtqua.wav";
        MusicController.playMusic(cloudinaryMusicUrl, -10.0f, true); // Adjust volume and loop as needed
    }

    public static void main(String[] args) {
        launch();
    }
}
