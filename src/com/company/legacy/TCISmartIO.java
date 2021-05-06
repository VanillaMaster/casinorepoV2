package com.company.legacy;

import com.company.classes.TCI;
import com.company.classes.playerDataConstruct.playerData;
import com.company.classes.utilits.KeyboardsList;

public class TCISmartIO {

    TCI TCI;

    public TCISmartIO(TCI iTCI){
        TCI = iTCI;
    }

    public void slotResultOutput(playerData playerData, String msg, Integer first, Integer seccond, Integer third){
        TCI.sendMsg("\\/\\/\\/\\/\\/ \n"+"| "+first+" | "+seccond+" | "+ third +" |" + "\n/\\/\\/\\/\\/\\"+"\n \n" +msg,playerData.telegramID, KeyboardsList.commands);
    }

    public void outPut(playerData playerData,String str,String keyboard){
        //TCI.sendMsg(str,playerData.telegramID,keyboard);
    }

    public void hMultiOutput(playerData playerData, String roll, String points, String keyboard){

        //TCI.sendMsg(roll+"\n\n"+points,playerData.telegramID,keyboard);

    }

    public void hDoubleOutput(playerData playerData, String firstRoll, String seccondRoll){

        TCI.sendMsg(firstRoll+"\n"+seccondRoll,playerData.telegramID,KeyboardsList.non);

    }

}
