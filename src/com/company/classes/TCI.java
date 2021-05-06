package com.company.classes;


import com.company.classes.keyboards.keyboard;
import com.company.classes.keyboards.krepsKeyboard;
import com.company.classes.keyboards.mainKeyboard;
import com.company.classes.keyboards.slotMachineKeyboard;
import com.company.classes.playerDataConstruct.playerDataShell;
import com.company.classes.utilits.KeyboardsList;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

public class TCI extends TelegramLongPollingBot {
    
    private String token;

    private Map<Long, playerDataShell> players = new HashMap<>();

    private Map<KeyboardsList, keyboard> keyboards = new HashMap<>(){
        {
            put(KeyboardsList.commands,new mainKeyboard());
            put(KeyboardsList.slots,new slotMachineKeyboard());
            put(KeyboardsList.kreps,new krepsKeyboard());
        }
    };

    public TCI(String token) { this.token = token; }

    @Override
    public String getBotUsername() {
        return "MyAmazingBot(Дифффффичента?)";
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {

            String inputMsg = update.getMessage().getText();
            Long chat_id = update.getMessage().getChatId();

            if (!players.containsKey(chat_id)) {
                playerDataShell tmpShell = new playerDataShell(this, String.valueOf(chat_id));
                players.put(chat_id, tmpShell);
            }

            players.get(chat_id).executeCommand(inputMsg);
        }

    }
    //отправляет сообщение пользователю
    public void sendMsg(String msg, String chat_id,KeyboardsList keyboardType) {

        SendMessage message = new SendMessage(); // Create a message object object
        message.setChatId(chat_id);
        message.setText(msg);

        if (keyboards.containsKey(keyboardType)) {
            keyboards.get(keyboardType).setKeyboard(message);
        }

        try {
            execute(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public void removePlayer(String ID){
        players.get(ID).saveData();
        players.remove(ID);
        System.gc();
    }

}
