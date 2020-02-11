package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MainMenuScreen implements Screen {
    final Drop game;
    OrthographicCamera camera;

    public MainMenuScreen(Drop game) {
        this.game = game;
        camera=new OrthographicCamera();
        camera.setToOrtho(false,800,480);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1); // очистка экрана от логотипа бэд логик геймс
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update(); // обновление камеры,а-ля отрисовка


        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin(); // запрос на рисование батча внутри бегина и енда
        game.font.draw(game.batch,"WHAT A FUCK IS THIS GAME ",200,150);//рисуем текстик
        game.font.draw(game.batch,"Tap to understand",200,100);
        game.batch.end();

        if (Gdx.input.isTouched()){//если нажали - выводим игру и выпиливаемся
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {

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
