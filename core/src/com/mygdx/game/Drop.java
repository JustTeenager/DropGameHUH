package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Drop extends Game {//главный и самый крутой класс
    SpriteBatch batch;
    BitmapFont font;
    @Override
    public void create() {
        batch=new SpriteBatch();
        font =new BitmapFont();
        this.setScreen(new MainMenuScreen(this));// ставит экран главного меню
    }
    public void render(){
        super.render();
    }
    public void dispose(){
        super.dispose();
    }
}
