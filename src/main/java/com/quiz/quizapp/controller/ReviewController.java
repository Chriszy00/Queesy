package com.quiz.quizapp.controller;

import com.quiz.quizapp.model.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

public class ReviewController {

    @FXML
    private ListView<Question> reviewListView;

    private List<Question> questions;

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
        loadReviewData();
    }

    private void loadReviewData() {
        reviewListView.getItems().addAll(questions);
        reviewListView.setCellFactory(new Callback<ListView<Question>, ListCell<Question>>() {
            @Override
            public ListCell<Question> call(ListView<Question> listView) {
                return new ListCell<Question>() {
                    @Override
                    protected void updateItem(Question question, boolean empty) {
                        super.updateItem(question, empty);
                        if (question != null) {
                            int index = getIndex() + 1;
                            String reviewText = index + ". " + question.getQuestion() + "\n" +
                                    "Your Answer: " + question.getUserAnswer() + "\n" +
                                    "Correct Answer: " + question.getAnswer();
                            setText(reviewText);
                            setStyle("-fx-padding: 10px; -fx-background-color: #f0f0f0; -fx-border-color: #dcdcdc; -fx-border-width: 1px;");
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
    }

    @FXML
    private void handleBackToResults(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/quiz/quizapp/result.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
