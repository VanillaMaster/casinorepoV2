package com.company.classes.keyboards;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface keyboard {
    void setKeyboard(SendMessage sendMessage);
}
