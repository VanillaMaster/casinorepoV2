package com.company.classes.playerDataConstruct;

import com.company.classes.TCI;
import com.company.classes.TCIcommands.*;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class playerDataShell {

    private httpRequest requset = new httpRequest();

    private final Gson gson = new Gson();

    private static final int lifeSpan = 5; //Сктолько сколько существует информация о сущности(не работает)

    private HashMap parrentMap;

    private ArrayDeque<String> commandTimeLine = new ArrayDeque<String>();   //Очередь для перенаправления потока ввода

    //добавить элемент в очередь(станет первым)
    public void addToQueue(String nextInputTarget){
        commandTimeLine.addFirst(nextInputTarget);
    }

    private TCI TCI;

    private Map<String, TCICommands> commands = new HashMap<String, TCICommands>() {{
    }};

    //команды доступные игроку
    public playerDataShell(TCI iTCI,String telegramID){
        TCI = iTCI;
        initPlayer(telegramID);

        commands.put("/help", new help(iTCI));
        commands.put("/info",new info(iTCI));
        commands.put("/slots",new slots(iTCI));
        commands.put("/kreps",new kreps(iTCI));
    }

    public int currentLifeSpan = lifeSpan;

    private Timer timer = new Timer(true); //(не работает)

    private void initTimer(){
        TimerTask timerTask = new playerKillTimer(parrentMap,this);
        timer.schedule(timerTask,0,60*1000);
    }

    private void initPlayer(String telegramID){

        boolean alreadyExsist = false;

        URL url = null;
        try {
            alreadyExsist = Boolean.parseBoolean(requset.getDBIndex("https://vanilla-db.herokuapp.com/api/v1/isexisting",
                    ("{\"login\":\"adminApp\",\"password\":\"000000\",\"name\":\""+ telegramID +".json\"}").getBytes(StandardCharsets.UTF_8)));
            System.out.println(alreadyExsist);

            if (alreadyExsist){
                playerData = gson.fromJson(requset.getDBIndex("https://vanilla-db.herokuapp.com/api/v1/getdbdata",
                        ("{\"login\":\"adminApp\",\"password\":\"000000\",\"name\":\""+ telegramID +".json\"}") .getBytes(StandardCharsets.UTF_8) ),playerData.class);


            } else {

                playerData = new playerData(telegramID);

            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //initTimer();

    }

    //инициализирует данные игрока
    public playerData getPlayerData(){

        currentLifeSpan = lifeSpan;

        return playerData;
    }

    //исполняет команды доступные игроку + для определния потока ввода для команд требущих ввод после себя
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

    //==============================================

    private playerData playerData = new playerData();

}
