package com.fondue.flockshoal;

public class Vector {
    private float x;
    private float y;

    public static Vector scale(Vector v1, float factor) {
        return new Vector(v1.getX() * factor, v1.getY() * factor);
    }

    public static float getSquaredDistance(Vector v1, Vector v2) {
        float dX = v1.getX() - v2.getX();
        float dY = v1.getY() - v2.getY();
        return dX * dX + dY * dY;
    }

    public static float getDistance(Vector v1, Vector v2) {
        return (float)Math.sqrt(getSquaredDistance(v1, v2));
    }

    public static float dotProduct(Vector v1, Vector v2) {
        return (v1.x * v2.x + v1.y * v2.y);
    }

    public Vector(Vector v) {
        this.x = v.x;
        this.y = v.y;
    }

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

    public Vector add(Vector v) {
        x += v.x;
        y += v.y;
        return this;
    }

    public Vector subtract(Vector v) {
        x -= v.x;
        y -= v.y;
        return this;
    }

    public Vector add(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector normalise() {
        double magnitude = magnitude();
        if (magnitude > 0) {
            x /= magnitude;
            y /= magnitude;
        }
        return this;
    }

    public Vector scale(float factor) {
        x *= factor;
        y *= factor;
        return this;
    }

    public float magnitude() {
        return (float)Math.sqrt(x * x + y * y);
    }

    public Vector bound(float min, float max) {
        if (x < min)
            x = max;
        else if (x > max)
            x = min;

        if (y < min)
            y = max;
        else if (y > max)
            y = min;
        return this;
    }

    public Vector clamp(float magnitude) {
        double currentMagnitude = Math.sqrt(x * x + y * y);
        if (currentMagnitude > magnitude) {
            this.x *= magnitude/currentMagnitude;
            this.y *= magnitude/currentMagnitude;
        }
        return this;
    }
}
