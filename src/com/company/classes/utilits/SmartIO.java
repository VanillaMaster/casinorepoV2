package com.company.classes.utilits;

import java.util.Scanner;

/**
 * содержит в себе паттерны для вывода данных в консоль/получения данных из консоли
 */
public class SmartIO {

    private Scanner scanner = new Scanner(System.in);

    /**
     * @return возвращает массив входных данных
     */
    public String[] input(){
        return scanner.nextLine().split(" ");
    }

    /**
     * @return возвращает строку входных данных
     */
    public String sInput(){
        return scanner.nextLine();
    }

    /**
     * @param str - втрока которая будет выведена в консоль
     */
    public void sOutput(String str){
        System.out.println(str);
    }

    /**
     * @param input - строки(визуально разделённые) которые будут выведены в консоль
     */
    public void hMultiOutput(String... input){
        for (String i:input){
            System.out.println(i);
            System.out.println("*****");
        }
    }

    /**
     * @param input - числа(визуально разделённые) которые будут выведены в консоль
     */
    public void hMultiOutput(Integer... input){
        for (Integer i:input){
            System.out.println(i);
            System.out.println("*****");
        }
    }

    /**
     * @param first - строка , скомбинированая с другой строкой и визуально отделены от других даннх, которые будут выведены в консоль
     * @param seccond - строка , скомбинированая с другой строкой и визуально отделены от других даннх, которые будут выведены в консоль
     */
    public void hDoubleOutput(String first,String seccond){
        System.out.println("\\/\\/\\/\\/\\/\\/");
        System.out.println(first);
        System.out.println(seccond);
        System.out.println("/\\/\\/\\/\\/\\/\\");
    }

    /**
     * @param first - число , скомбинированное с другим числом и визуально отделлено от других даннх, которые будут выведены в консоль
     * @param seccond - число , скомбинированное с другим числом и визуально отделлено от других даннх, которые будут выведены в консоль
     */
    public void hDoubleOutput(Integer first,Integer seccond){
        System.out.println("\\/\\/\\/\\/\\/\\/");
        System.out.println(first);
        System.out.println(seccond);
        System.out.println("/\\/\\/\\/\\/\\/\\");
    }
}
