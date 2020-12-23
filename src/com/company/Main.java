package com.company;

import com.company.classes.TCI;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) throws TelegramApiException {

        FileInputStream fis;
        Properties property = new Properties();
        String token ="-1";

        try {
            fis = new FileInputStream("src/com/company/resources/conf.properties");
            property.load(fis);

            token = property.getProperty("token");

            System.out.println("token: " + token);

        } catch (IOException e) {
            System.err.println("Error: conf file doesn't exist");
        }

        if (token!="-1") {
            try {
                TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
                botsApi.registerBot(new TCI(token));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
}
