package org.example.orientierungprojekt.util;

import java.lang.Math.*;

public class Vector {
    private float x;
    private float y;

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setVector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void add(Vector other) {
        this.x += other.x;
        this.y += other.y;
    }

    public void add(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public void subtract(Vector other) {
        this.x -= other.x;
        this.y -= other.y;
    }

    public void scale(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
    }

    public Vector scaleVector(float scalar) {
        return new Vector(this.x * scalar, this.y * scalar);
    }

    public float magnitude(){
        return (float)Math.sqrt(this.x * this.x + this.y * this.y);
    }
    
}
