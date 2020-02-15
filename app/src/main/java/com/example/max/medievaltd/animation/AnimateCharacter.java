package com.example.max.medievaltd.animation;

/**
 * Created by max on 28/08/2015.
 */
public class AnimateCharacter extends Animation{

    private int currentAction; //Down Left Up Right

    public void setFrames(int length) {

        super.setFrames(length);
        currentAction=0;
    }

    public void setCurrentAction(int a){
        currentAction=a;
    }

    public int getCurrentAction(){return currentAction;}

}


