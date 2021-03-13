package com.company.classes.playerDataConstruct;

import com.company.classes.TCI;
import com.company.classes.TCIcommands.*;
import com.google.gson.Gson;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.temporal.ChronoUnit.SECONDS;

public class playerDataShell {

    private final Gson gson = new Gson();

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private final db db = new db();

    private final TCI TCI; // интерфейс работы с чатом

    private boolean isNewUser = false;

    private ArrayDeque<String> commandTimeLine = new ArrayDeque<>();   //Очередь для перенаправления потока ввода

    public void addToQueue(String nextInputTarget){ commandTimeLine.addFirst(nextInputTarget); } //добавить элемент в очередь(станет первым)

    //============== data construct ================

    private playerData playerData; // хронит данные игрока, dataShell - является оболочкой для этих данных

    public playerData getPlayerData(){

        currentLifeSpan = lifeSpan;

        return playerData;
    }

    private String UserID;

    public String getUserID(){
        return UserID;
    }

    //==============================================

    private enum commnadList{
        none,
        help;

        public static commnadList get(String s)
        {
            for(commnadList choice:values())
                if (choice.name().equals(s))
                    return choice;
            return commnadList.none;
        }
    }

    private enum commands {
        help(new help()),
        info(new info()),
        slots(new slots()),
        kreps(new kreps()),
        lifespan(new lifespan()),
        DEFAULT(new help());


        public static TCICommands get(String s)
        {
            for(commands choice:values()) {
                if (("/"+choice.name()).equals(s))
                    return choice.command;
            }
            return commands.DEFAULT.command;
        }

        public static void init(TCI iTCI, playerDataShell iPlayerDataShell)
        {
            for(commands choice:values())
                choice.command.init(iTCI,iPlayerDataShell);
        }

        private final TCICommands command;

        commands(TCICommands command) {
            this.command = command;
        }

    }



    public playerDataShell(TCI iTCI,String telegramID){
        TCI = iTCI;
        UserID = telegramID;
        initPlayer();

        commands.init(iTCI,this);

    }



    //============= lifeSpan construct =============

    private final int lifeSpan = 5; //продолжительность жизни данных (в циклах таймера) (в процессе отладки)

    private int currentLifeSpan = lifeSpan; //текущая продолжительность жизни

    public int getCurrentLifeSpan(){
        return currentLifeSpan;
    }

    public void LifeSpanCycle(){
        currentLifeSpan--;
    }

    //==============================================



    //============== timer construct ===============

    private Timer timer = new Timer(true); //таймер

    //инициализация таймера
    private void initTimer(){

        TimerTask timerTask = new playerKillTimer(TCI,UserID,this, timer);
        timer.schedule(timerTask,0,10*1000);
    }

    //==============================================



    private void initPlayer(){

        try {

            isNewUser = !db.isExist(UserID);

            if (!isNewUser){

                playerData = db.readDb(UserID);

                System.out.println(SECONDS.between(LocalDateTime.parse(playerData.getDate(), formatter),java.time.LocalDateTime.now()));// < ==== последний вход

                playerData.setDate(formatter.format(java.time.LocalDateTime.now()));

            } else {
                playerData = new playerData(UserID);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        initTimer();

    }

    public void saveData(){

        db.saveData(isNewUser,playerData,UserID);

    }

    //обрабатывает текущие входные данные в соответствии с очередью( commandTimeLine )
    public void executeCommand(String iData){

        //=========== command pre-processing ===========

        String[] arguments = iData.split(" ");
        String command;

        if (commandTimeLine.isEmpty()){
            if (arguments.length > 1){
                command = arguments[0];
                String[] tmp = new String[arguments.length-1];

                System.arraycopy(arguments, 1, tmp, 0, tmp.length);
                arguments = tmp;
            } else {
                command = arguments[0];
                arguments = new String[0];
            }
        } else {
            String tmpCommand = commandTimeLine.pollFirst();

            if (tmpCommand.equals(arguments[0])){

                if (arguments.length > 1){
                    command = arguments[0];
                    String[] tmp = new String[arguments.length-1];

                    System.arraycopy(arguments, 1, tmp, 0, tmp.length);
                    arguments = tmp;
                } else {
                    command = arguments[0];
                    arguments = new String[0];
                }

            } else {
                command = tmpCommand;
            }
        }

        //==============================================

        //System.out.println(command);
        //System.out.println(arguments.length);

        //============ command executing ===============

        commands.get(command).execute(arguments);

        //======================================


    }

}
