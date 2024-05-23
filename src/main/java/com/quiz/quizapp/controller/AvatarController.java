package com.quiz.quizapp.controller;

import com.quiz.quizapp.service.AvatarSelectionService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;

public class AvatarController {

    @FXML
    private ImageView avatar1;
    @FXML
    private ImageView avatar2;
    @FXML
    private ImageView avatar3;
    @FXML
    private ImageView avatar4;
    @FXML
    private ImageView avatar5;
    @FXML
    private ImageView avatar6;

    @FXML
    private void initialize() {
        avatar1.setOnMouseClicked(event -> handleAvatarSelection("/images/43.png"));
        avatar2.setOnMouseClicked(event -> handleAvatarSelection("/images/46.png"));
        avatar3.setOnMouseClicked(event -> handleAvatarSelection("/images/47.png"));
        avatar4.setOnMouseClicked(event -> handleAvatarSelection("/images/43.png"));
        avatar5.setOnMouseClicked(event -> handleAvatarSelection("/images/48.png"));
        avatar6.setOnMouseClicked(event -> handleAvatarSelection("/images/49.png"));
    }

    private void handleAvatarSelection(String avatarPath) {
        AvatarSelectionService.getInstance().setSelectedAvatar(avatarPath);
        redirectToCategories();
//        redirectToResults();
    }

    private void redirectToCategories() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/quiz/quizapp/categories.fxml"));
            Stage stage = (Stage) avatar1.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            CategoriesController categoriesController = loader.getController();
            categoriesController.setSelectedAvatar(AvatarSelectionService.getInstance().getSelectedAvatar());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void redirectToResults() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/quiz/quizapp/result.fxml"));
            Stage stage = (Stage) avatar1.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            ResultController resultController = loader.getController();
            resultController.setAvatarImage(AvatarSelectionService.getInstance().getSelectedAvatar());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
