 package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

import static com.badlogic.gdx.math.MathUtils.random;

 public class GameScreen implements Screen {
 	final Drop game;
	OrthographicCamera camera; //  обьявляем камеру
	SpriteBatch batch; // рисовашка
	Texture img; // текстуры:
	Texture DropImage; // капли
	Texture BucketImage;// ведра
	Sound DropSound; // муызка падения капли (короткая)
	Music RainMusic; // музыка дождя (больше 3х мин)
	Rectangle bucket; // прямоугольник,обозначающий ведро
	Vector3 vect;
	Array<Rectangle> raindrops;
	long LastDropTime;
	int dropsEarned;

	public GameScreen (final Drop game) {
		this.game=game;
		camera= new OrthographicCamera(); // установка камеры
		camera.setToOrtho(false,800,480); // установка "размеров поля"

		vect=new Vector3(); // вектор тридэшный на самом деле
		batch = new SpriteBatch();

		DropImage = new Texture("droplet.png");
		BucketImage = new Texture("bucket.png");

		DropSound=Gdx.audio.newSound(Gdx.files.internal("waterdrop.wav"));
		RainMusic=Gdx.audio.newMusic(Gdx.files.internal("undertreeinrain.mp3"));

		RainMusic.setLooping(true); // запуск "бесконечной" музыки
		RainMusic.play();

		bucket=new Rectangle();//создали прямоугольник ведра
		bucket.x=800/2-64/2;
		bucket.y=20;

		bucket.height=64;//задали его размер
		bucket.width=64;

		raindrops=new Array<Rectangle>();//крутой массив для капель
		SpawnRaindrops();
	}

	 private void SpawnRaindrops(){
		Rectangle drop =new Rectangle();
		drop.width=64;
		drop.height=64;
		drop.y=480;
		drop.x=random(0,800-drop.width);
		raindrops.add(drop);
		LastDropTime= TimeUtils.nanoTime();//время в наносекнудах
	}

	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1); // очистка экрана от логотипа бэд логик геймс
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update(); // обновление камеры,а-ля отрисовка


		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin(); // запрос на рисование батча внутри бегина и енда
		game.font.draw(game.batch,"Gathered: "+ dropsEarned,0,480);//фонт рисует слова,здесь счетчик
		for (Rectangle raindrop:raindrops){
			game.batch.draw(DropImage,raindrop.x,raindrop.y);
		}
		game.batch.draw(BucketImage, bucket.x, bucket.y);
		game.batch.end();

		if (Gdx.input.isTouched()){ // логика касания по экрану (ведро бежит к тачу)

			vect.set(Gdx.input.getX(),Gdx.input.getY(),0); // задали вектор
			camera.unproject(vect); // сверили координаты html,десктопа и андроида,т.к могут быть разные
			bucket.x=vect.x - 32;
			if (bucket.x<0) bucket.x=0;
			if (bucket.x>800-64) bucket.x=800-64;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= 200*Gdx.graphics.getDeltaTime();//на нажатие по калвишам двигается ведро для КОМПА
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += 200*Gdx.graphics.getDeltaTime();//ДельтаТайм - изменение во времени в сравнении с пред нажатием

		if (TimeUtils.nanoTime() - LastDropTime >1000000000) SpawnRaindrops(); // создаем капли с какой-то периодчиностью
		Iterator<Rectangle> iter=raindrops.iterator(); // итератор для отслеживания состояния...капель?
		while (iter.hasNext()){
			Rectangle raindrop = iter.next();
			raindrop.y-=200*Gdx.graphics.getDeltaTime();
			if (raindrop.y<64) iter.remove();
			if (raindrop.overlaps(bucket)) {//посмотреть,столкнулся ли прямоугольник капли с прямоугольником ведра
				DropSound.play();
				dropsEarned++;
				iter.remove();
			}
		}
	}

	 @Override
	 public void show() {//это все методы интерфейса screen

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
	public void dispose () {// чистка,сюда пишем все
		batch.dispose();
		img.dispose();
	}
}
