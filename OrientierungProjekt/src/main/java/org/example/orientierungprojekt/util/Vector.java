package org.example.orientierungprojekt.util;

import java.lang.Math;

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

    public void addAngleVelocity(float angle){
        float newX = (float) (this.x * Math.cos(angle) - this.y * Math.sin(angle));
        float newY = (float) (this.x * Math.sin(angle) + this.y * Math.cos(angle));
        this.x = newX;
        this.y = newY;
    }

    public Vector addVector(Vector other) {
        return new Vector(this.x + other.x, this.y + other.y);
    }

    public void subtract(Vector other) {
        this.x -= other.x;
        this.y -= other.y;
    }

    public Vector subtractVector(Vector other) {
        return new Vector(this.x - other.x, this.y - other.y);
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

    public float distanceTo(Vector other) {
        float dx = this.x - other.x;
        float dy = this.y - other.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public Vector getDistanceVector(Vector other) {
        return new Vector(this.x - other.x, this.y - other.y);
    }

    public float getDistanceBetween(Vector v1, Vector v2) {
        float dx = v2.x - v1.x;
        float dy = v2.y - v1.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    public Vector getDistanceBetweenVector(Vector v1, Vector v2){
        return new Vector(v2.x - v1.x, v2.y - v1.y);
    }

    public void normalize() {
        float mag = magnitude();
        if (mag != 0) {
            this.x /= mag;
            this.y /= mag;
        }
    }

    public Vector getNormalizedVector() {
        float mag = magnitude();
        if (mag != 0) {
            return new Vector(this.x / mag, this.y / mag);
        } else {
            return new Vector(0, 0); // Return zero vector if magnitude is zero
        }
    }

    public float dot(Vector other) {
        return this.x * other.x + this.y * other.y;
    }

    public float getAngle(Vector v1, Vector v2) {
        float dotProduct = v1.dot(v2);
        float magnitudeV1 = v1.magnitude();
        float magnitudeV2 = v2.magnitude();
        return (float) Math.acos(dotProduct / (magnitudeV1 * magnitudeV2));
    }
    
    public float getAngleDegrees(Vector v1, Vector v2) {
        return (float) Math.toDegrees(getAngle(v1, v2));
    }
    
}
