package com.fondue.flockshoal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.List;

public class Fish {
    private final int WIDTH = 10;
    private final int CANVAS =2000;
    private ShapeRenderer shapeRenderer;
    private Viewport viewport;

    private Vector position;
    private Vector velocity;

    private final int VISIBLE_DISTANCE = 100;
    private final int MAX_SPEED = 200;
    private final float DRAG_COEFFICIENT = 1;
    private final int SWIM_FORCE = 400;

    private final float REPEL_DISTANCE = 100;

    private final float REPEL_SCALE = 1f;
    private final float COHEDE_SCALE = 1.2f;
    private final float ALIGN_SCALE = 1f;

    private Vector3 mouseInWorld = new Vector3();

    public Fish(ShapeRenderer shapeRenderer, Viewport viewport) {
        this.shapeRenderer = shapeRenderer;
        this.viewport = viewport;
        this.position = new Vector((float)(Math.random() * CANVAS), (float)(Math.random() * CANVAS));

        //Start with random velocity
        this.velocity = new Vector((float)((Math.random() - 0.5f) * MAX_SPEED), (float)((Math.random() - 0.5f) * MAX_SPEED));
    }

    public void update(float delta, List<Fish> allFish) {
        Vector force = new Vector(0,0);
        Vector desiredDirection = new Vector(0,0);
        float desire = 1;

        mouseInWorld.x = Gdx.input.getX();
        mouseInWorld.y = Gdx.input.getY();
        mouseInWorld.z = 0;
        viewport.unproject(mouseInWorld);

        Vector predator = new Vector(mouseInWorld.x, mouseInWorld.y);

        Vector repelDir = new Vector(0,0);
        Vector cohedeDir = new Vector(0,0);
        Vector alignDir = new Vector(0,0);

        for (Fish f : allFish) {
            if (isFishVisible(f)) {
                //Repel
                float distance = Vector.getDistance(position, f.getPosition());
                if (distance < REPEL_DISTANCE) {
                    float repelScale = (REPEL_DISTANCE - distance) / REPEL_DISTANCE;
                    repelDir.subtract(f.getPosition().subtract(position).normalise().scale(repelScale));
                }

                //Cohede
                cohedeDir.add(f.getPosition().subtract(position));

                //Align
                alignDir.add(f.getVelocity().normalise());
            }
        }

        // Predator
        if (getPosition().subtract(predator).magnitude() < 130) {
            desire = 5;
            desiredDirection.add(getPosition().subtract(predator).normalise().scale(5));
        }

        desiredDirection.add(repelDir.normalise().scale(REPEL_SCALE));
        desiredDirection.add(cohedeDir.normalise().scale(COHEDE_SCALE));
        desiredDirection.add(alignDir.normalise().scale(ALIGN_SCALE));

        //Jitter
        desiredDirection.add(new Vector((float)Math.random() - 0.5f, (float)Math.random() - 0.5f).normalise().scale(2));

        // Add swim
        force.add(desiredDirection.normalise().scale(SWIM_FORCE).scale(desire));
        // Add drag
        force.subtract((new Vector(velocity)).scale(DRAG_COEFFICIENT));
        // Update velocity
        velocity.add(Vector.scale(force, delta));
        // Update position
        position.add(Vector.scale(velocity.clamp(MAX_SPEED), delta)).bound(0, CANVAS);
    }

    private boolean isFishVisible(Fish f) {
        Vector fishPos = f.getPosition();
        if (Vector.getDistance(position, fishPos) > VISIBLE_DISTANCE)
            return false;
        if (velocity.magnitude() == 0)
            return true;

        Vector relativePos = fishPos.subtract(position);
        return Vector.dotProduct(velocity, relativePos) > 0;
    }

    public void draw() {
        shapeRenderer.rect(position.getX(), position.getY(), WIDTH, WIDTH);
    }

    public Vector getPosition() {
        return new Vector(position);
    }

    public Vector getVelocity() {
        return new Vector(velocity);
    }

    public float getX() {
        return position.getX();
    }

    public float getY() {
        return position.getY();
    }
}
