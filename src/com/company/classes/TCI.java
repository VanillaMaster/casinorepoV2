package com.company.classes;


import com.company.classes.playerDataConstruct.playerDataShell;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TCI extends TelegramLongPollingBot {

    private String token;

    private Map<String, playerDataShell> players = new HashMap<>();

    public TCI(String token){
        this.token = token;
    }

    @Override
    public String getBotUsername() {
        return "MyAmazingBot(Дифффффичента?)";
    }

    @Override
    public String getBotToken() { return token; }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            String inputMsg = update.getMessage().getText();
            String chat_id = String.valueOf(update.getMessage().getChatId());

            if (!players.containsKey(chat_id)){
                playerDataShell tmpShell = new playerDataShell(this,chat_id);
                players.put(chat_id,tmpShell);
            }

            players.get(chat_id).executeCommand(inputMsg);
        }

    }

    public void sendMsg(String msg, String chat_id){

        SendMessage message = new SendMessage(); // Create a message object object
        message.setChatId(chat_id);
        message.setText(msg);

        setButtons(message);

        try {
            execute(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public synchronized void setButtons(SendMessage sendMessage) {

        // Создаем клавиуатуру
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();

        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add(new KeyboardButton("/help"));
        keyboardFirstRow.add(new KeyboardButton("/info"));

        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();

        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add(new KeyboardButton("/slots"));
        keyboardSecondRow.add(new KeyboardButton("/kreps"));

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);

        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

}
