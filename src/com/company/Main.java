package com.company;

import com.company.classes.TCI;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {

    private enum test {
        DEFAULT{
            public void run(){}
        },

        TEST{
            public void run() {
                System.out.println("asd");
            }
        };

        public static test get(String s)
        {
            for(test choice:values())
                if (choice.name().equals(s))
                    return test.valueOf(choice.name());
            return test.DEFAULT;
        }


        public abstract void run();

    }

    public static void main(String[] args) throws TelegramApiException {


        String token = getToken();

        if (token != "") {
            try {
                TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
                botsApi.registerBot(new TCI(token));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }


    }

    private static String getToken(){

        try {
            FileInputStream fis = new FileInputStream("src/com/company/resources/conf.properties");

            Properties property = new Properties();

            property.load(fis);

            String token = property.getProperty("token");

            System.out.println("token: " + token);

            return token;

        } catch (IOException e) {
            System.err.println("Error: conf file doesn't exist");
            return "-1";
        }

    }
}

