package com.company.classes.playerDataConstruct;

import com.company.classes.TCI;
import com.company.classes.TCIcommands.*;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class playerDataShell {

    private httpRequest requset = new httpRequest();

    private final Gson gson = new Gson();

    private boolean isNewUser = false;



    //============== data construct ================

    private playerData playerData = new playerData(); // хронит данные игрока, dataShell - является оболочкой для этих данных

    public playerData getPlayerData(){

        currentLifeSpan = lifeSpan;

        return playerData;
    }

    private String UserID;

    public String getUserID(){
        return UserID;
    }

    //==============================================


    private ArrayDeque<String> commandTimeLine = new ArrayDeque<String>();   //Очередь для перенаправления потока ввода

    public void addToQueue(String nextInputTarget){ commandTimeLine.addFirst(nextInputTarget); } //добавить элемент в очередь(станет первым)

    private TCI TCI; // интерфейс работы с чатом

    private Map<String, TCICommands> commands = new HashMap<String, TCICommands>();


    public playerDataShell(TCI iTCI,String telegramID){
        TCI = iTCI;
        UserID = telegramID;
        initPlayer(telegramID);

        //команды доступные игроку
        commands.put("/help", new help(iTCI));
        commands.put("/info",new info(iTCI));
        commands.put("/slots",new slots(iTCI));
        commands.put("/kreps",new kreps(iTCI));
        commands.put("/lifespan",new lifespan(iTCI));

    }



    //============= lifeSpan construct =============

    private static final int lifeSpan = 5; //продолжительность жизни данных (в циклах таймера) (в процессе отладки)

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



    private void initPlayer(String telegramID){

        boolean alreadyExist = false;

        //URL url = null;

        try {
            alreadyExist = Boolean.parseBoolean(requset.getDBIndex("https://vanilla-db.herokuapp.com/api/v1/isexisting",
                    ("{\"login\":\"adminApp\",\"password\":\"000000\",\"name\":\""+ telegramID +".json\"}").getBytes(StandardCharsets.UTF_8)));
            System.out.println(alreadyExist);

            if (alreadyExist){
                playerData = gson.fromJson(requset.getDBIndex("https://vanilla-db.herokuapp.com/api/v1/getdbdata",
                        ("{\"login\":\"adminApp\",\"password\":\"000000\",\"name\":\""+ telegramID +".json\"}") .getBytes(StandardCharsets.UTF_8) ),playerData.class);


            } else {

                playerData = new playerData(telegramID);
                isNewUser = true;

            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        initTimer();

    }

    public void saveData(){

        String currentData = gson.toJson(playerData).replaceAll("\"","\\\\\"");
        System.out.println(currentData);

        String resp = "-1";

        String url = "https://vanilla-db.herokuapp.com/api/v1/update";
        if (isNewUser){
            url = "https://vanilla-db.herokuapp.com/api/v1/upload";
        }

        try {
            resp = requset.getDBIndex(url,
                    ("{\"login\":\"adminApp\",\"password\":\"000000\",\"name\":\""+ UserID +".json\",\"data\":\"" + currentData + "\"}").getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(resp);

    }

    //обрабатывает текущие входные данные в соответствии с очередью( commandTimeLine )
    public void executeCommand(String command){

        if (commandTimeLine.peekFirst()== null){

            if (commands.containsKey(command)) {
                commands.get(command).execute(this,"");
            }
            else {
                TCI.sendMsg("unknown command, \"/help\" for command list",getPlayerData().telegramID,"non");
            }

        } else {


            String tmp = commandTimeLine.pollFirst();

            if (commands.containsKey(tmp)) {
                commands.get(tmp).execute(this,command);
            }
            else {
                TCI.sendMsg("error on pds switch: "+tmp,getPlayerData().telegramID ,"non");
            }


        }

    }

}
