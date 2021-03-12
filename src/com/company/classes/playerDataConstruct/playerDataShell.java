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

    private final httpRequest request = new httpRequest();

    private final Gson gson = new Gson();

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private boolean isNewUser = false;

    private final db db = new db();

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


    private ArrayDeque<String> commandTimeLine = new ArrayDeque<>();   //Очередь для перенаправления потока ввода

    public void addToQueue(String nextInputTarget){ commandTimeLine.addFirst(nextInputTarget); } //добавить элемент в очередь(станет первым)

    private final TCI TCI; // интерфейс работы с чатом

    private Map<String, TCICommands> commands = new HashMap<>();


    public playerDataShell(TCI iTCI,String telegramID){
        TCI = iTCI;
        UserID = telegramID;
        initPlayer();

        //команды доступные игроку
        commands.put("/help", new help(iTCI,this));
        commands.put("/info",new info(iTCI,this));
        commands.put("/slots",new slots(iTCI,this));
        commands.put("/kreps",new kreps(iTCI,this));
        commands.put("/lifespan",new lifespan(iTCI,this));

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

        //новая система обработки комманд (с поддержкой аргументов - аргументы и так существовали но работали не явно и небыло возможности их использовать вне кейса игр)


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

                System.out.println("0");

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
                System.out.println("1");
                command = tmpCommand;
            }
        }

        //==============================================

        System.out.println(command);
        System.out.println(arguments.length);


        //============ command executing ===============

        if (commands.containsKey(command)) {
            commands.get(command).execute(arguments);
        }
        else {
            TCI.sendMsg("unknown command, \"/help\" for command list",getPlayerData().telegramID,"non");
        }

        //======================================


    }

}
