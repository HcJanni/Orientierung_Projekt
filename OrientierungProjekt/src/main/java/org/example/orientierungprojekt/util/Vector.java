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

    public void add(Vector other) {
        this.x += other.x;
        this.y += other.y;
    }

    public void subtract(Vector other) {
        this.x -= other.x;
        this.y -= other.y;
    }

    public void multiply(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
    }

    public float getLength(){
        return (float)Math.sqrt(this.x * this.x + this.y * this.y);
    }
    
}
