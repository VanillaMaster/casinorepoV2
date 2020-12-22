package com.company.classes.playerDataConstruct;

import com.company.classes.TCI;
import com.company.classes.TCIcommands.TCICommands;
import com.company.classes.TCIcommands.*;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class playerDataShell {

    private playerDataShell iThis = this;

    private final Gson gson = new Gson();

    //написать жабадок
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

            url = new URL("https://vanilla-db.herokuapp.com/api/v1/isexisting");
            URLConnection con = url.openConnection();
            HttpURLConnection http = (HttpURLConnection)con;
            http.setRequestMethod("POST"); // PUT is another valid option
            http.setDoOutput(true);

            byte[] out = ("{\"login\":\"adminApp\",\"password\":\"000000\",\"name\":\""+ telegramID +".json\"}") .getBytes(StandardCharsets.UTF_8);
            int length = out.length;

            http.setFixedLengthStreamingMode(length);
            http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            http.connect();
            try(OutputStream os = http.getOutputStream()) {
                os.write(out);
            }

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                alreadyExsist = Boolean.parseBoolean(response.toString());
            }

            if (alreadyExsist){

                url = new URL("https://vanilla-db.herokuapp.com/api/v1/getdbdata");
                con = url.openConnection();
                http = (HttpURLConnection)con;
                http.setRequestMethod("POST"); // PUT is another valid option
                http.setDoOutput(true);

                out = ("{\"login\":\"adminApp\",\"password\":\"000000\",\"name\":\""+ telegramID +".json\"}") .getBytes(StandardCharsets.UTF_8);
                length = out.length;

                http.setFixedLengthStreamingMode(length);
                http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                http.connect();
                try(OutputStream os = http.getOutputStream()) {
                    os.write(out);
                }

                try(BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), "utf-8"))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }

                    playerData = gson.fromJson(response.toString(), playerData.class);

                }

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
                commands.get(command).execute(iThis,"");
            }
            else {
                TCI.sendMsg("unknown command, \"/help\" for command list",getPlayerData().telegramID);
            }

        } else {


            String tmp = commandTimeLine.pollFirst();

            switch (tmp){

                case "/slots":
                    commands.get("/slots").execute(iThis,command);
                    break;

                case "/kreps":
                    commands.get("/kreps").execute(iThis,command);
                    break;

                default:
                    TCI.sendMsg("error on pds switch: "+tmp,getPlayerData().telegramID);
                    break;

            }

        }

    }

    //==============================================

    private playerData playerData = new playerData();

}
