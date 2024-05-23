package com.quiz.quizapp.service;

public class AvatarSelectionService {
    private static AvatarSelectionService instance;
    private String selectedAvatar;

    private AvatarSelectionService() {}

    public static AvatarSelectionService getInstance() {
        if (instance == null) {
            instance = new AvatarSelectionService();
        }
        return instance;
    }

    public String getSelectedAvatar() {
        return selectedAvatar;
    }

    public void setSelectedAvatar(String selectedAvatar) {
        this.selectedAvatar = selectedAvatar;
    }
}
