package com.quiz.quizapp.controller;

import com.quiz.quizapp.model.Question;
import com.quiz.quizapp.service.AvatarSelectionService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.List;

public class ResultController {
    @FXML
    public Label remark, marks, markstext, correcttext, wrongtext, timeTaken, correctProgress, wrongProgress;

    @FXML
    private Label categoryLabel;

    @FXML
    private ImageView avatarImageViewResults;

    private List<Question> questions; // Add this line to declare the questions list

    int correct;
    int wrong;

    @FXML
    private void initialize() {
        correct = QuizController.correct;
        wrong = QuizController.wrong;
        questions = QuizController.questions; // Add this line to get the questions from QuizController

        correcttext.setText("No. of Correct \nAnswers: " + correct);
        wrongtext.setText("No. of Incorrect \nAnswers: " + String.valueOf(QuizController.wrong));

        marks.setText(correct + "/10");
        float correctf = (float) correct / 10;
        float wrongf = (float) wrong / 10;

        correctProgress.setText(String.format("%.0f%%", correctf * 100));
        wrongProgress.setText(String.format("%.0f%%", wrongf * 100));

        markstext.setText(correct + " Marks Scored");

        if (correct < 2) {
            remark.setText("Oh no..! You have failed the quiz.");
        } else if (correct >= 2 && correct < 5) {
            remark.setText("Oops..! You have scored less marks.");
        } else if (correct >= 5 && correct <= 7) {
            remark.setText("Need more improvement!");
        } else if (correct == 8 || correct == 9) {
            remark.setText("Congratulations!");
        } else if (correct == 10) {
            remark.setText("Congratulations!");
        }

        // Set the avatar image from the shared state
        setAvatarImage(AvatarSelectionService.getInstance().getSelectedAvatar());
    }

    public void setTimeTaken(String time) {
        timeTaken.setText(time);
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions; // Add this method to set the questions
        System.out.println("Questions set in ResultController: " + questions.size());
    }

//    @FXML
//    private void handleTakeAnotherQuiz(ActionEvent event) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/quiz/quizapp/categories.fxml"));
//            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
//            Scene scene = new Scene(loader.load());
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void setCategory(String category) {

        // Update the category label
        categoryLabel.setText(category.toUpperCase());
    }

    public void setAvatarImage(String imageUrl) {
        Image image = new Image(getClass().getResourceAsStream(imageUrl));
        avatarImageViewResults.setImage(image);
    }

    @FXML
    private void handleCloseGame(ActionEvent event) {
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleReviewQuiz(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/quiz/quizapp/review.fxml"));
            Scene scene = new Scene(loader.load());

            // Get the ReviewController instance from the FXMLLoader
            ReviewController reviewController = loader.getController();

            // Pass the questions to the ReviewController
            if (questions != null) {
                System.out.println("Passing questions to ReviewController: " + questions.size());
            } else {
                System.out.println("Questions list is null");
            }
            reviewController.setQuestions(questions);

            // Set the new scene
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
