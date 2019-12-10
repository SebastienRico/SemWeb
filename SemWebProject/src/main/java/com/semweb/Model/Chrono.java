package com.semweb.Model;

import com.semweb.Controller.JSONController;
import com.semweb.utilities.Util;

public class Chrono extends Thread{

    private long startTime = 0;
    private long now = 0;
    private long maxTime = 0;
    
    public Chrono(){
        this.maxTime = Long.parseLong(Util.getProperty("refresh_data_timer"));
        this.now = System.currentTimeMillis() / 1000;
        this.startTime = System.currentTimeMillis() / 1000;
    }

    @Override
    public void run() {
        now = System.currentTimeMillis() / 1000;
        while(true){
            now = System.currentTimeMillis() / 1000;
            if(now - startTime < maxTime){
                now = System.currentTimeMillis() / 1000;
            } else {
                JSONController.parseJSONDatas();
                this.startTime = System.currentTimeMillis() / 1000;
            }
        }
    }
}
