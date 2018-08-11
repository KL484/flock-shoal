package com.fondue.flockshoal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.LinkedList;
import java.util.List;

public class TestScreen implements Screen {

    private Main game;

    private OrthographicCamera camera;
    private Viewport viewport;
    private final int cameraWidth = 2000;
    private final int cameraHeight = 2000;

    private List<Fish> mFish = new LinkedList<Fish>();

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    public TestScreen(Main game) {
        this.game = game;

        camera = new OrthographicCamera(cameraWidth, cameraHeight);
        camera.setToOrtho(false, cameraWidth, cameraHeight);
        viewport = new FitViewport(cameraWidth, cameraHeight, camera);
        viewport.apply();

        for (int i = 0; i < 2000; i++) {
            mFish.add(new Fish(shapeRenderer, viewport));
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(0, 0.1f, 0.2f, 1);
        shapeRenderer.rect(0, 0, cameraWidth, cameraHeight);

        shapeRenderer.setColor(0, 0.7f, 0.7f, 1);

        for (Fish f : mFish) {
            f.update(delta, mFish);
            f.draw();
        }
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
