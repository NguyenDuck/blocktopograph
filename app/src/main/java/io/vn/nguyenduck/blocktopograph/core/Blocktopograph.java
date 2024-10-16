package io.vn.nguyenduck.blocktopograph.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Blocktopograph implements ApplicationListener {
    public PerspectiveCamera camera;
    public SpriteBatch sprite;
    public Texture texture;

    @Override
    public void create() {
        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.update();
        sprite = new SpriteBatch();
        texture = new Texture("img.png");
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        sprite.begin();
        sprite.draw(
                texture,
                (Gdx.graphics.getWidth() - texture.getWidth()) / 2f,
                (Gdx.graphics.getHeight() - texture.getHeight()) / 2f);
        sprite.end();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void dispose() {
    }
}