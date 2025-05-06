package org.example.orientierungprojekt.util;

import java.lang.Math;

public class Vector {

    /*
     * Implementation mit Hilfe von: thenatureofcode.com und Mathebuch, irgendwas mit Vektoren, es gibt ja so viele, muss ich mal raussuchen
     */
    
    private float x;
    private float y;

    public Vector(){
        new Vector(0,0);
    }

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector(Vector other){
        this.x = other.getX();
        this.y = other.getY();
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
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

    public void addToY(float dy) {
        this.y += dy;
    }

    public void addToX(float dx) {
        this.x += dx;
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

    public void subtract(float x, float y){
        this.x -= x;
        this.y -= y;
    }

    public Vector subtractVector(Vector other) {
        return new Vector(this.x - other.x, this.y - other.y);
    }

    public Vector subtractVector(float x, float y){
        return new Vector(this.x - x, this.y - y);
    }

    public void scale(float scalar) {
        this.x *= scalar;
        this.y *= scalar;
    }

    public Vector scaleVector(float scalar) {
        return new Vector(this.x * scalar, this.y * scalar);
    }

    public float getLength(){
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public float getLength(Vector vector){
        return (float) Math.sqrt(vector.getX() * vector.getX() + vector.getY() * vector.getY());
    }

    public float distanceTo(Vector other) {
        float dx = (this.x - other.x);
        float dy = (this.y - other.y);
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
        float mag = getLength();
        if (mag != 0) {
            this.x /= mag;
            this.y /= mag;
        }
    }

    public Vector getNormalizedVector() {
        float mag = getLength();
        if (mag != 0) {
            return new Vector(this.x / mag, this.y / mag);
        } else {
            return new Vector(0, 0); // Return zero vector if magnitude is zero
        }
    }

    public float dot(Vector other) {
        return this.x * other.x + this.y * other.y;
    }

    public void rotateByRadians(float angleInRadians){
        float newX = this.x * (float) Math.cos(angleInRadians) - this.y * (float) Math.sin(angleInRadians);
        float newY = this.x * (float) Math.sin(angleInRadians) + this.y * (float) Math.cos(angleInRadians);
        
        this.x = newX; this.y = newY;
    }

    public Vector rotateVector(float angleInRadians){
        float newX = this.x * (float) Math.cos(angleInRadians) - this.getY() * (float) Math.sin(angleInRadians);
        float newY = this.x * (float) Math.sin(angleInRadians) + this.getY() * (float) Math.cos(angleInRadians);

        return new Vector(newX, newY);
    }

    public Vector rotateVector(Vector other, float angleInRadians) {
        float newX = other.getX() * (float) Math.cos(angleInRadians) - other.getY() * (float) Math.sin(angleInRadians);
        float newY = other.getX() * (float) Math.sin(angleInRadians) + other.getY() * (float) Math.cos(angleInRadians);

        return new Vector(newX, newY);
    }

    public float getAngle(Vector v1, Vector v2) {
        float dotProduct = v1.dot(v2);
        float magnitudeV1 = v1.getLength();
        float magnitudeV2 = v2.getLength();
        return (float) Math.acos(dotProduct / (magnitudeV1 * magnitudeV2));
    }
    
}
