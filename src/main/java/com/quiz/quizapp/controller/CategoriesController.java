package com.quiz.quizapp.controller;

import com.quiz.quizapp.model.UserData;
import com.quiz.quizapp.service.AvatarSelectionService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.InputStream;
import java.net.URL;

public class CategoriesController {

    @FXML
    private Pane mathPane;

    @FXML
    private Pane sciPane;

    @FXML
    private Pane geoPane;

    private String selectedAvatar;

    @FXML
    private Label userNameLabel;

    public void setSelectedAvatar(String selectedAvatar) {
        this.selectedAvatar = selectedAvatar;
    }

    @FXML
    private void initialize() {
        mathPane.setOnMouseClicked(event -> loadQuiz(event, "math"));
        sciPane.setOnMouseClicked(event -> loadQuiz(event, "science"));
        geoPane.setOnMouseClicked(event -> loadQuiz(event, "geography"));

        userNameLabel.setText("Let's Play, " + UserData.getUserName() + "!");
    }

    private void loadQuiz(MouseEvent event, String category) {
        MusicController.playClickSound(); // Play click sound
        try {
            Stage thisStage = (Stage) ((Pane) event.getSource()).getScene().getWindow();
            thisStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/quiz/quizapp/quiz.fxml"));
            Scene scene = new Scene(loader.load());

            QuizController controller = loader.getController();
            controller.setCategory(category);
            controller.setAvatarImage(AvatarSelectionService.getInstance().getSelectedAvatar());  // CRS: 05/21/2024 | Pass the selected avatar to the QuizController

            // Play the corresponding music
            playMusicForCategory(category);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playMusicForCategory(String category) {
        String musicPath = null;

        switch (category) {
            case "math":
                musicPath = "src/main/resources/music/math_music.wav";
                break;
            case "science":
                musicPath = "src/main/resources/music/science_music.wav";
                break;
            case "geography":
                musicPath = "src/main/resources/music/geography_music.wav";
                break;
            default:
                System.out.println("No music for this category");
                return;
        }

        MusicController.playMusic(musicPath, -15.0f, true); // Adjust volume as needed
    }


}
