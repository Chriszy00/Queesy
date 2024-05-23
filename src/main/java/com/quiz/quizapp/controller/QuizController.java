package com.quiz.quizapp.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quiz.quizapp.model.Question;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class QuizController {

    @FXML
    public Label question, timerLabel;

    @FXML
    public Button opt1, opt2, opt3, opt4;

    @FXML
    public ProgressIndicator progressIndicator;

    @FXML
    private ImageView avatarImageView;

    @FXML
    private Label categoryLabel;

    static int counter = 0;
    static int correct = 0;
    static int wrong = 0;
    static List<Question> questions; // Change this to static to access it in ResultController
    private long startTime;
    private Timeline timeline;

    private String category;

    public void setCategory(String category) {
        this.category = category;
        categoryLabel.setText(category.toUpperCase());
        loadQuestionsFromFile();
        loadQuestions();
    }

    private void startTimer() {
        startTime = System.currentTimeMillis();
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTimer()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateTimer() {
        long elapsedMillis = System.currentTimeMillis() - startTime;
        long elapsedSeconds = elapsedMillis / 1000;
        long hours = elapsedSeconds / 3600;
        long minutes = (elapsedSeconds % 3600) / 60;
        long seconds = elapsedSeconds % 60;
        timerLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }

    private void stopTimer() {
        timeline.stop();
    }

    public void setAvatarImage(String imageUrl) {
        Image image = new Image(getClass().getResourceAsStream(imageUrl));
        avatarImageView.setImage(image);
    }

    private void loadQuestionsFromFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String fileName = "src/main/resources/json/" + category + ".json";
            questions = objectMapper.readValue(new File(fileName), new TypeReference<List<Question>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadQuestions() {
        if (counter < questions.size()) {
            if (counter == 0) {
                startTimer(); // Start the timer when the first question is loaded
            }
            Question currentQuestion = questions.get(counter);
            question.setText((counter + 1) + ". " + currentQuestion.getQuestion());
            opt1.setText(currentQuestion.getOptions().get(0));
            opt2.setText(currentQuestion.getOptions().get(1));
            opt3.setText(currentQuestion.getOptions().get(2));
            opt4.setText(currentQuestion.getOptions().get(3));
            updateProgressBar();  // Update progress bar
        }
    }

    private void updateProgressBar() {
        double progress = (double) (counter + 1) / questions.size();
        progressIndicator.setProgress(progress);
    }

    private boolean checkAnswer(String answer) {
        return questions.get(counter).getAnswer().equals(answer);
    }

    private void handleOptionClick(ActionEvent event, Button button) {
        // Save the user's answer to the question
        questions.get(counter).setUserAnswer(button.getText());
        MusicController.playClickSound();

        if (checkAnswer(button.getText())) {
            correct++;
        } else {
            wrong++;
        }
        if (counter == questions.size() - 1) {
            showResult(event);
        } else {
            counter++;
            loadQuestions();
        }
    }

    private void showResult(ActionEvent event) {
        try {
            stopTimer(); // Stop the timer when the quiz ends
            long elapsedMillis = System.currentTimeMillis() - startTime;
            long elapsedSeconds = elapsedMillis / 1000;
            long hours = elapsedSeconds / 3600;
            long minutes = (elapsedSeconds % 3600) / 60;
            long seconds = elapsedSeconds % 60;
            String elapsedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds);

            // Stop the current music
            MusicController.stopMusic();

            // Determine the result music based on the score
            String resultMusicPath;
            if (correct < 7) {
                resultMusicPath = "src/main/resources/music/result_fail.wav"; // Path to fail music
            } else {
                resultMusicPath = "src/main/resources/music/result_success.wav"; // Path to success music
            }

            // Play the selected result music
            MusicController.playMusic(resultMusicPath, -10.0f, false); // Adjust volume as needed

            Stage thisStage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            thisStage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/quiz/quizapp/result.fxml"));
            Scene scene = new Scene(loader.load());

            ResultController controller = loader.getController();
            controller.setCategory(category);

            scene.setFill(Color.TRANSPARENT);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);

            // Pass the elapsed time to the ResultController
            ResultController resultController = loader.getController();
            resultController.setTimeTaken(elapsedTime);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void opt1clicked(ActionEvent event) {
        handleOptionClick(event, opt1);
    }

    @FXML
    public void opt2clicked(ActionEvent event) {
        handleOptionClick(event, opt2);
    }

    @FXML
    public void opt3clicked(ActionEvent event) {
        handleOptionClick(event, opt3);
    }

    @FXML
    public void opt4clicked(ActionEvent event) {
        handleOptionClick(event, opt4);
    }

    @FXML
    public void skipQuestion(ActionEvent event) {
        // Mark the question as skipped by setting an empty user answer
        questions.get(counter).setUserAnswer("");
        wrong++; // Increment the wrong answer count

        if (counter == questions.size() - 1) {
            showResult(event);
        } else {
            counter++;
            loadQuestions();
        }
    }
}
