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

    private TCI TCI = this;

    /*
    private Map<String, TCICommands> commands = new HashMap<String, TCICommands>() {{
        put("/help", new help(TCI));
    }};
     */

    private Map<String, playerDataShell> players = new HashMap<String, playerDataShell>() {{
        //put("telegramID", new help(TCI));
    }};

    @Override
    public String getBotUsername() {
        return "MyAmazingBot(Дифффффичента?)";
    }

    @Override
    public String getBotToken() {
        return "1402260417:AAEWd3c_9fHZSjb5fM1ixvO-9BMfsqq-W5U";
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            String inputMsg = update.getMessage().getText();
            String chat_id = String.valueOf(update.getMessage().getChatId());

            if (!players.containsKey(chat_id)){
                playerDataShell tmpShell = new playerDataShell(this,chat_id);
                //tmpShell.initPlayer(chat_id);
                players.put(chat_id,tmpShell);
            }

            players.get(chat_id).executeCommand(inputMsg);

            /*
            if (commands.containsKey(inputMsg)) {
                //commands.get(inputMsg).execute(players.get(chat_id).getPlayerData());
                players.get(chat_id).executeCommand(inputMsg);
            }
            else {
                SendMessage message = new SendMessage(); // Create a message object object
                message.setChatId(chat_id);
                message.setText("unknown command, \"/help\" for command list");

                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }*/

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

    // trash ============================================

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

        keyboardFirstRow.add(new KeyboardButton("Привет"));


// Вторая строчка клавиатуры

        KeyboardRow keyboardSecondRow = new KeyboardRow();

// Добавляем кнопки во вторую строчку клавиатуры

        keyboardSecondRow.add(new KeyboardButton("Помощь?"));


// Добавляем все строчки клавиатуры в список

        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);

// и устанваливаем этот список нашей клавиатуре

        replyKeyboardMarkup.setKeyboard(keyboard);
    }

}
