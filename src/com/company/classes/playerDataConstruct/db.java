package com.company.classes.playerDataConstruct;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class db {

    private final httpRequest request = new httpRequest();

    private final Gson gson = new Gson();


    public void saveData(boolean isNewUser,playerData playerData ,String userID){

        String currentData = gson.toJson(playerData).replaceAll("\"","\\\\\"");
        System.out.println(currentData);

        String resp = "-1";

        String url = "https://vanilla-db.herokuapp.com/api/v1/update";
        if (isNewUser){
            url = "https://vanilla-db.herokuapp.com/api/v1/upload";
        }

        System.out.println("request sended...");

        try {
            resp = request.getDBIndex(url,
                    ("{\"login\":\"adminApp\",\"password\":\"000000\",\"name\":\""+ userID +"\",\"data\":\"" + currentData + "\"}").getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("resp: ");
        System.out.println(resp);

    }

    public boolean isExist(String telegramID) throws IOException {
        return Integer.parseInt(request.getDBIndex("https://vanilla-db.herokuapp.com/api/v1/isexisting",
                ("{\"login\":\"adminApp\",\"password\":\"000000\",\"name\":\"" + telegramID + "\"}").getBytes(StandardCharsets.UTF_8))) != -1;

    }

    public playerData readDb(String telegramID) throws IOException {
        String resp = request.getDBIndex("https://vanilla-db.herokuapp.com/api/v1/getdbdata",
                ("{\"login\":\"adminApp\",\"password\":\"000000\",\"name\":\""+ telegramID +"\"}") .getBytes(StandardCharsets.UTF_8) );

        resp = resp.replaceAll("\\\\\"","\"");

        resp = resp.replaceAll("\"\\{","{");

        resp = resp.replaceAll("}\"","}");

        System.out.println(resp);

        System.out.println("converted");

        return gson.fromJson(resp,playerData.class);
    }

}
