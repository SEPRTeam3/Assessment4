package com.mozarellabytes.kroy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mozarellabytes.kroy.Screens.*;

/**
 * Our main class where everything starts from. It contains
 * a number of heavy objects which are shared and accessed by
 * different screens so that each screen doesn't need to
 * create their own heavy objects.
 */
public class Kroy extends Game {

	public SpriteBatch batch;
	public ShapeRenderer shapeRenderer;

	public BitmapFont font26;
	public BitmapFont font19;
	public BitmapFont font60;
	public BitmapFont font50;
	public BitmapFont font25;
	public BitmapFont font33;
	public BitmapFont font33Custom;
	public BitmapFont font33Red;
	public BitmapFont font60Purple;
	public BitmapFont font60Black;
	public BitmapFont font60Custom;
	public BitmapFont font19Gold;
	@Override
	public void create () {
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Magero.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

		parameter.size = 60;
		font60 = generator.generateFont(parameter);
		parameter.size = 26;
		font26 = generator.generateFont(parameter);
		parameter.size = 19;
		font19 = generator.generateFont(parameter);
		parameter.color = Color.GOLDENROD;
		font19Gold = generator.generateFont(parameter);
		parameter.color = Color.WHITE;
		parameter.size = 50;
		font50 = generator.generateFont(parameter);
		parameter.size = 25;
		font25 = generator.generateFont(parameter);
		parameter.size = 33;
		font33 = generator.generateFont(parameter);
		parameter.color = Color.FIREBRICK;
		font33Red = generator.generateFont(parameter);
		parameter.color = Color.MAROON;
		font33Custom = generator.generateFont(parameter);
		parameter.color = Color.BLACK;
		parameter.size = 60;
		font60Black = generator.generateFont(parameter);
		parameter.color = Color.MAROON;
		font60Purple = generator.generateFont(parameter);
		parameter.color = Color.SALMON;
		font60Custom = generator.generateFont(parameter);

		this.setScreen(new SplashScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
		font19.dispose();
		font25.dispose();
		font26.dispose();
		font33.dispose();
		font33Red.dispose();
		font33Custom.dispose();
		font50.dispose();
		font60.dispose();
		font60Black.dispose();
		font60Purple.dispose();
		font60Custom.dispose();
		font19Gold.dispose();
	}
}
