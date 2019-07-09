package com.example.max.medievaltd;

/**
 * Created by max on 1/12/2015.
 */
public class enemyWaves {
    long duration;
    long started;
    int presetCode;
    int numWave;
    int toDeploy;
    long paused;

            public enemyWaves(int wave,int preset) {
                numWave = wave;
                presetCode = preset;
                started = System.nanoTime();
               // if (numWave < 10) {
                    toDeploy = numWave * 3;
                    duration = 10;
                //}
               /* if (numWave >= 10 && numWave < 30) {
                    toDeploy = numWave * 4;
                    duration=5;
                }
                if (numWave >= 30) {
                    toDeploy = numWave * 5;
                    duration=2;
                }*/
            }
    public int getNumWave(){return numWave;}
    public boolean concluded(){return toDeploy<=0;}
    public void pause(){paused=System.nanoTime()-started;}
    public void unPause(){paused=0;}
            public int update()
            {
                if(paused==0) {
                    if ((System.nanoTime() - started) / 100000000 > duration && toDeploy > 0) {
                        if (presetCode == 0) {
                            toDeploy--;
                            started = System.nanoTime();
                            int max = 3;
                            if (numWave == 50) {
                                if (toDeploy == numWave * 3 - 1) {
                                    return 3;
                                }
                            }
                            if (numWave > 50) {
                                max = 4;
                            }

                            int code = (int) (Math.random() * max);
                            if (code < 1)
                                code = 0;
                            if (code > max - 1)
                                code = max - 1;
                            if (code == 3) {
                                code = (int) (Math.random() * max);
                                if (code < 1)
                                    code = 0;
                                if (code > max - 1)
                                    code = max - 1;
                            }
                            return code;
                        }
                        if (presetCode == 1 || presetCode == 2 || presetCode == 3) {
                            toDeploy--;
                            started = System.nanoTime();
                            return 0;
                        }
                        if (presetCode == 4) {
                            toDeploy--;
                            started = System.nanoTime();
                            return 2;
                        }
                        if (presetCode == 5) {
                            toDeploy--;
                            started = System.nanoTime();
                            if (toDeploy == 0)
                                return 2;
                            return 0;
                        }
                        if (presetCode == 6) {
                            toDeploy--;
                            started = System.nanoTime();
                            if (toDeploy % 2 == 0)
                                return 2;
                            return 0;
                        }
                        if (presetCode == 7) {
                            toDeploy--;
                            started = System.nanoTime();
                            if (toDeploy < 10)
                                return 2;
                            return 0;
                        }
                        if (presetCode == 8) {
                            toDeploy--;
                            started = System.nanoTime();
                            if (toDeploy > 20)
                                return 2;
                            if (toDeploy > 10)
                                return 0;
                            return 2;
                        }
                        if (presetCode == 9) {
                            toDeploy--;
                            started = System.nanoTime();
                            return 2;
                        }
                        if (presetCode == 10) {
                            toDeploy--;
                            started = System.nanoTime();
                            return 1;
                        }

                    }
                }
                else
                {
                    started=System.nanoTime()-paused;
                }

                return -1;
            }
}
