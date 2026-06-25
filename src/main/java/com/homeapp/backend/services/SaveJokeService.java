package com.homeapp.backend.services;

import com.homeapp.backend.models.DTOJoke;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;

@Service
public class SaveJokeService {
    private static final String JOKES_FILE = "src/main/resources/jokes.json";
    private static final String JOKES_FILE_BACKUP = "src/main/resources/jokes_backup.json";
    private final ObjectMapper om = new ObjectMapper();
    private final ArrayList<DTOJoke> jokes;
    private final Logger logger = Logger.getLogger(SaveJokeService.class.getName());


    public SaveJokeService() {
        this.jokes = readSavedJokesFile();
    }

    public ArrayList<DTOJoke> readSavedJokesFile() {
        try {
            File file = new File(JOKES_FILE);
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.length() == 0) {
                return new ArrayList<>();
            }
            return om.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            logger.log(SEVERE, "Error Reading joke file.\n" + e);
            throw new RuntimeException(e);
        }
    }

    public void save(DTOJoke joke) {
        logger.log(INFO, "Saving Joke: " + joke);
        jokes.add(joke);
        saveToFile(JOKES_FILE, jokes);
    }

    private void saveToFile(String fileName, ArrayList<DTOJoke> jokes) {
        try {
            om.writeValue(new File(fileName), jokes);
        } catch (Exception e) {
            logger.log(SEVERE, "Error Logging joke!\n" + e);
        }
    }

    public void deleteAllJokes() {
        backupJokes();
        try {
            om.writeValue(new File(JOKES_FILE), new ArrayList<>());
        } catch (Exception e) {
            logger.log(SEVERE, "Error Deleting Jokes.\n" + e);
        }
    }

    private void backupJokes() {
        ArrayList<DTOJoke> currentJokes = readSavedJokesFile();
        try {
            File file = new File(JOKES_FILE_BACKUP);
            if (!file.exists()) {
                file.createNewFile();
            }
            om.writeValue(file, currentJokes);
        } catch (Exception e) {
            logger.log(SEVERE, "Error Deleting Jokes.\n" + e);
        }
    }

    public void reloadJokesFromBackup() {
        ArrayList<DTOJoke> backupJokes = new ArrayList<>();
        try {
            File file = new File(JOKES_FILE_BACKUP);
            backupJokes = om.readValue(file, new TypeReference<>() {
            });
            saveToFile(JOKES_FILE, backupJokes);
        } catch (Exception e) {
            logger.log(SEVERE, "Error Backing up files.\n" + e);
        }
    }
}
