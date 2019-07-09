
    package com.example.max.medievaltd;

    import android.graphics.Bitmap;



    import android.graphics.Bitmap;

    /**
     * Created by max on 28/08/2015.
     */
    public class AnimateCharacter {
        private int currentFrame;
        private long startTime;
        private long delay;
        private boolean playedOnce;
        private int currentAction; //D L U R
        private int length;
        public void setFrames(int l) {
            length=l;
            currentFrame = 0;
            currentAction=0;

            startTime = System.nanoTime();

        }

        public void setCurrentAction(int a){currentAction=a;}
        public void setDelay(long d) {
            delay = d;
        }

        public void setFrame(int i) {
            currentFrame = i;
        }

        public void update() {
            long elapsed = (System.nanoTime() - startTime) / 1000000;
            if (elapsed > delay) {
                currentFrame++;
                startTime = System.nanoTime();
            }
            if (currentFrame == length)
            {
                currentFrame=0;
                playedOnce=true;
            }

        }

        public int getFrame()
        {
            return currentFrame;
        }
        public int getCurrentAction(){return currentAction;}
        public boolean isPlayedOnce()
        {
            return playedOnce;
        }
        public void setPlayedOnce(boolean b)
        {
            playedOnce=b;
        }

    }


