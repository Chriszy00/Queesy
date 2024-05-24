package com.quiz.quizapp.controller;

import javax.sound.sampled.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class MusicController {

    private static Clip currentClip;

    public static void playMusic(String cloudinaryUrl, float volume, boolean loop) {
        try {
            // Stop any currently playing clip
            if (currentClip != null && currentClip.isRunning()) {
                currentClip.stop();
                currentClip.close();
            }

            // Fetch music file from Cloudinary
            URL url = new URL(cloudinaryUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();

            // Create a temporary file to store the music
            File tempFile = File.createTempFile("bg-music", ".wav");
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // Close streams
            inputStream.close();
            outputStream.close();

            // Create audio input stream from the temporary file
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(tempFile);
            currentClip = AudioSystem.getClip();
            currentClip.open(audioInput);

            // Get the volume control and set the volume
            FloatControl volumeControl = (FloatControl) currentClip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(volume);

            if (loop) {
                currentClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music continuously
            }
            currentClip.start();

            // Delete temporary file when the application exits
            tempFile.deleteOnExit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopMusic() {
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
            currentClip.close();
        }
    }

    public static void playClickSound() {
        String cloudinaryClickSoundUrl = "https://res.cloudinary.com/drhiswbtx/video/upload/v1716456866/Music/click_sound_yjjxqe.wav";
        try {
            // Fetch click sound file from Cloudinary
            URL url = new URL(cloudinaryClickSoundUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();

            // Create a temporary file to store the click sound
            File tempFile = File.createTempFile("click-sound", ".wav");
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            // Close streams
            inputStream.close();
            outputStream.close();

            // Create audio input stream from the temporary file
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(tempFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);

            // Get the volume control and set the volume (optional)
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            volumeControl.setValue(-10.0f); // Adjust volume as needed

            clip.start();

            // Delete temporary file when the application exits
            tempFile.deleteOnExit();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

