package com.company.classes.playerDataConstruct;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class db {

    private final httpRequest request = new httpRequest();

    private final Gson gson = new Gson();

    private static class data{
        String login;
        String password;
        String name;
        String data;
        public data clone(String name){
            data obj = new data();
            obj.login = this.login;
            obj.password = this.password;
            obj.name = name;
            return obj;
        }
        public data clone(String name,String data){
            data obj = new data();
            obj.login = this.login;
            obj.password = this.password;
            obj.name = name;
            obj.data = data;
            return obj;
        }
    }

    private data bodyPattern = new data();
    private String URL;

    public db(){
        try {
            FileInputStream fis = new FileInputStream("src/com/company/resources/conf.properties");
            Properties property = new Properties();
            property.load(fis);
            bodyPattern.password = property.getProperty("dbPassword");
            bodyPattern.login = property.getProperty("dbLogin");
            URL = property.getProperty("URL");
        } catch (IOException e) {
            System.err.println("Error: conf file doesn't exist");
        }
    }

    public void saveData(boolean isNewUser,playerData playerData ,String userID){

        String currentData = gson.toJson(playerData).replaceAll("\"","\\\\\"");
        System.out.println(currentData);

        String resp = "-1";

        String url = (URL + "api/v1/update");
        if (isNewUser){
            url = (URL + "api/v1/upload");
        }

        System.out.println("request sent...");

        data body = bodyPattern.clone(userID,currentData);

        try {
            resp = request.getDBIndex(url,(gson.toJson(body)).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("resp: ");
        System.out.println(resp);

    }

    public boolean isExist(String telegramID) throws IOException {
        data body = bodyPattern.clone(telegramID);
        return Integer.parseInt(request.getDBIndex((URL + "api/v1/isexisting"),(gson.toJson(body)).getBytes(StandardCharsets.UTF_8))) != -1;

    }

    public playerData readDb(String telegramID) throws IOException {
        data body = bodyPattern.clone(telegramID);
        String resp = request.getDBIndex((URL + "api/v1/getdbdata"),(gson.toJson(body)).getBytes(StandardCharsets.UTF_8));

        resp = resp.replaceAll("\\\\\"","\"");
        resp = resp.replaceAll("\"\\{","{");
        resp = resp.replaceAll("}\"","}");

        System.out.println(resp);

        System.out.println("converted");

        return gson.fromJson(resp,playerData.class);
    }

}
