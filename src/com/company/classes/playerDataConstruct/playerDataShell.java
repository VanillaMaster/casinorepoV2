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
        help,
        info,
        slots,
        kreps,
        lifespan;

        public static commnadList get(String s)
        {
            for(commnadList choice:values())
                if ((choice.name()).equals(s))
                    return choice;
            return commnadList.none;
        }
    }

    private Map<commnadList, TCICommands> commands = new HashMap<>(){
        {
            put(commnadList.help, new help());
            put(commnadList.info,new info());
            put(commnadList.slots,new slots());
            put(commnadList.kreps,new kreps());
            put(commnadList.lifespan,new lifespan());
        }
    };


    public playerDataShell(TCI iTCI,String telegramID){
        TCI = iTCI;
        UserID = telegramID;
        initPlayer();

        commands.forEach((key, value) -> {
            value.init(TCI,this);
        });

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
        ArrayList<String> arguments = new ArrayList<>(Arrays.asList(iData.split(" ")));
        commnadList command;

        if (commandTimeLine.isEmpty()){
            command = commnadList.get(arguments.get(0).substring(1));
            arguments.remove(0);
        } else {
            String tmpCommand = commandTimeLine.pollFirst();

            if (tmpCommand.equals(arguments.get(0))){
                command = commnadList.get(arguments.get(0).substring(1));
                arguments.remove(0);
            } else {
                command = commnadList.get(tmpCommand.substring(1));
            }
        }
        //============ command executing ===============
        commands.get(command).execute(arguments.toArray(new String[0]));
        //==============================================


    }

}
