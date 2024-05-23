package com.quiz.quizapp.model;

public class UserData {
    private static String userName;

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String name) {
        userName = name;
    }
}
