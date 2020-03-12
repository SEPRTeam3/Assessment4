//package com.mozarellabytes.kroy.Screens;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.mozarellabytes.kroy.Kroy;
//import com.mozarellabytes.kroy.Utilities.LevelScreenInputHandler;
//import java.awt.*;
//
//public class LevelScreen implements Screen {
//    private final Kroy game;
//
//    private Texture backgroundImage;
//
//    private final Rectangle easyButton;
//
//    private final Rectangle mediumButton;
//
//    private final Rectangle hardButton;
//
//    private final Texture easyButtonTexture;
//
//    private final Texture mediumButtonTexture;
//
//    private final Texture hardButtonTexture;
//
//    public final OrthographicCamera camera;
//
//    public LevelScreen(final Kroy game) {
//        camera = new OrthographicCamera();
//        camera.setToOrtho(false, Gdx.graphics.getDisplayMode().width, Gdx.graphics.getDisplayMode().height);
//
//        this.game = game;
//        backgroundImage = new Texture(Gdx.files.internal("menuscreen_blank_2.png"), true);
//        backgroundImage.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);
//        easyButtonTexture = new Texture(Gdx.files.internal("easyButton.png"),true);
//        easyButtonTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);
//        mediumButtonTexture = new Texture(Gdx.files.internal("mediumButton.png"), true);
//        mediumButtonTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);
//        hardButtonTexture = new Texture(Gdx.files.internal("hardButton.png"), true);
//        hardButtonTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.MipMapLinearNearest);
//
//        easyButton = new Rectangle();
//        easyButton.width = (int) (easyButtonTexture.getWidth()*0.75);
//        easyButton.height = (int) (easyButtonTexture.getHeight()*0.75);
//        easyButton.x = (int) (camera.viewportWidth/2 - easyButton.width/2);
//        easyButton.y = (int) (camera.viewportHeight/2 - easyButton.height/2);
//
//        mediumButton = new Rectangle();
//        mediumButton.width = (int) (mediumButtonTexture.getWidth()*0.75);
//        mediumButton.height = (int) (mediumButtonTexture.getHeight()*0.75);
//        mediumButton.x = (int) (camera.viewportWidth /2 -mediumButton.width/2);
//        mediumButton.y = (int) (camera.viewportHeight/2 - mediumButton.height/2);
//
//        hardButton = new Rectangle();
//        hardButton.width = (int) (hardButtonTexture.getWidth()*0.75);
//        hardButton.height = (int) (hardButtonTexture.getHeight()*0.75);
//        hardButton.x = (int) (camera.viewportWidth /2 - hardButton.width/2);
//        hardButton.y = (int) (camera.viewportHeight/2 - hardButton.height);
//
//        Gdx.input.setInputProcessor(new LevelScreenInputHandler(this));
//
//    }
//
//    @Override
//    public void show() {
//
//    }
//
//    @Override
//    public void render(float delta) {
//
//    }
//
//    @Override
//    public void resize(int width, int height) {
//
//    }
//
//    @Override
//    public void pause() {
//
//    }
//
//    @Override
//    public void resume() {
//
//    }
//
//    @Override
//    public void hide() {
//
//    }
//
//    @Override
//    public void dispose() {
//
//    }
//
//
//
//}
