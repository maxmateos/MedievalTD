package com.example.max.medievaltd.gameobjects;

import android.graphics.Rect;

import com.example.max.medievaltd.animation.Animation;

/**
 * Created by max on 10/09/2015.
 */
public class GameObject {

    protected int x;
    protected int y;
    protected int dx;
    protected int dy;
    protected int width;
    protected int height;
    protected int matrixScale;
    protected Animation animation;

    public GameObject() {

    }

    public GameObject(GameObject gameObject) {

        x = gameObject.x;
        y = gameObject.y;
        dx = gameObject.dx;
        dy = gameObject.dy;
        width = gameObject.width;
        height = gameObject.height;
        matrixScale = gameObject.matrixScale;
        animation = gameObject.animation;
    }

    public GameObject(int x, int y, int width, int height, int matrixScale) {

        this.x = x;
        this.y = y;
        this.dx = 0;
        this.dy = 0;
        this.width = width;
        this.height = height;
        this.matrixScale = matrixScale;
    }

    public GameObject(int x, int y, int dx, int dy, int width, int height, int matrixScale) {

        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.width = width;
        this.height = height;
        this.matrixScale = matrixScale;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setMatrixScale(int matrixScale) {
        this.matrixScale = matrixScale;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getMatrixScale() {
        return matrixScale;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }

    public Rect getRectangle() {
        return new Rect(x, y, x + width, y + height);
    }

    public int transposeX(int xi, int matrixScale) {
        return xi * matrixScale + (int)(matrixScale * 2.3f);
    }

    public int transposeY(int yi,int matrixScale) {
        return yi * matrixScale + matrixScale;
    }

    public GameObject getGameObject() {
        return this;
    }

    public static class Builder {

        private int x;
        private int y;
        private int dx;
        private int dy;
        private int width;
        private int height;
        private int matrixScale;
        private Animation animation;

        public GameObject buildGameObject() {

            GameObject newGameObject = new GameObject();
            newGameObject.setX(x);
            newGameObject.setY(y);
            newGameObject.setDx(dx);
            newGameObject.setDy(dy);
            newGameObject.setWidth(width);
            newGameObject.setHeight(height);
            newGameObject.setMatrixScale(matrixScale);
            newGameObject.setAnimation(animation);

            return newGameObject;
        }

        public Builder setX(int x) {
            this.x = x;
            return this;
        }

        public Builder setY(int y) {
            this.y = y;
            return this;
        }

        public Builder setDx(int dx) {
            this.dx = dx;
            return this;
        }

        public Builder setDy(int dy) {
            this.dy = dy;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setMatrixScale(int matrixScale) {
            this.matrixScale = matrixScale;
            return this;
        }

        public Builder setAnimation(Animation animation) {
            this.animation = animation;
            return this;
        }
    }
}
