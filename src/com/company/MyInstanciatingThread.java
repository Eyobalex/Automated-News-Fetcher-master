package com.company;

import java.io.IOException;

public class MyInstanciatingThread extends Thread {
    @Override
    public void run() {
        try
        {
            // We are running "dir" and "ping" command on cmd
            Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"cd src && node hosting.js\"");
        }
        catch (Exception e)
        {
            System.out.println("HEY Buddy ! U r Doing Something Wrong ");
            e.printStackTrace();
        }
    }

}
