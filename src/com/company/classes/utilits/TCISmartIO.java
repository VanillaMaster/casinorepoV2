package com.company.classes.utilits;

import com.company.classes.TCI;
import com.company.classes.playerDataConstruct.playerData;

public class TCISmartIO {

    TCI TCI;

    public TCISmartIO(TCI iTCI){
        TCI = iTCI;
    }

    public void slotResultOutput(playerData playerData, String msg, Integer first, Integer seccond, Integer third){
        TCI.sendMsg("\\/\\/\\/\\/\\/ \n"+"| "+first+" | "+seccond+" | "+ third +" |" + "\n/\\/\\/\\/\\/\\"+"\n \n" +msg,playerData.telegramID);
    }
    public void outPut(playerData playerData,String str){
        TCI.sendMsg(str,playerData.telegramID);
    }
}
