package com.prjt.explorateursautonomes.bouttons;

public class StartButtonController {
    public static StartButtonController ref;

    public StartButtonController(){
        init();
    }
    public void init(){
        if (ref == null){
            ref = this;
        }
    }
}
