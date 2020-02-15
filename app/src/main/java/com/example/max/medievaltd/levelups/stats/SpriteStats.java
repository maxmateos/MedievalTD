package com.example.max.medievaltd.levelups.stats;

public class SpriteStats {

    private int width;
    private int height;
    private int frames;
    private int resourceId;

    public SpriteStats(int width, int height, int frames, int resourceId) {
        this.width = width;
        this.height = height;
        this.frames = frames;
        this.resourceId = resourceId;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getFrames() {
        return frames;
    }

    public void setFrames(int frames) {
        this.frames = frames;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}