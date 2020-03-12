package com.mozarellabytes.kroy.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;
import com.mozarellabytes.kroy.Kroy;
import com.mozarellabytes.kroy.Save.Save;
import com.mozarellabytes.kroy.Save.SaveManager;
import com.mozarellabytes.kroy.Utilities.LoadInputHandler;
import com.mozarellabytes.kroy.Utilities.MenuInputHandler;

import java.util.ArrayList;
import java.util.List;

public class LoadScreen implements Screen {

    private Kroy game;

    private OrthographicCamera camera;

    private List<Save> saveList;

    public List<Rectangle> buttonRects;

    private int pressed = -1;

    int offset = 0;

    private final float POPUP_WIDTH = .4f;
    private final float POPUP_HEIGHT = .8f;
    private Rectangle popupButton;
    private final float TEXT_START = .25f;
    private final float TEXT_GAP = .1f;
    private final float BUTTON_START = .29f;
    private final float BUTTON_WIDTH = POPUP_WIDTH - .05f;
    private final float BUTTON_HEIGHT = TEXT_GAP - .05f;

    public LoadScreen(Kroy game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getDisplayMode().width, Gdx.graphics.getDisplayMode().height);
        saveList = SaveManager.getSaves();
        buttonRects = new ArrayList();
        for (int i = 0; i < saveList.size(); i++) {
            buttonRects.add(new Rectangle(camera.viewportWidth * (.5f - BUTTON_WIDTH/2), camera.viewportHeight * (BUTTON_START + ((i-.5f) * TEXT_GAP)), camera.viewportWidth * BUTTON_WIDTH, camera.viewportHeight * BUTTON_HEIGHT));
        }
        Gdx.input.setInputProcessor(new LoadInputHandler(this));
        popupButton = new Rectangle(camera.viewportWidth * (.5f-POPUP_WIDTH/2), camera.viewportHeight * (.5f - POPUP_HEIGHT/2 +.05f), camera.viewportWidth*POPUP_WIDTH, camera.viewportHeight*POPUP_HEIGHT);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        game.shapeRenderer.setColor(143f/255f, 53f/255f, 103f/255f, 1f);
        game.shapeRenderer.rect(camera.viewportWidth * (.5f-POPUP_WIDTH/2), camera.viewportHeight * (.5f - POPUP_HEIGHT/2 +.05f), camera.viewportWidth*POPUP_WIDTH, camera.viewportHeight*POPUP_HEIGHT);
        game.shapeRenderer.end();
        game.batch.begin();
        game.font50.draw(game.batch, "Load Save", camera.viewportWidth * (.5f - POPUP_WIDTH/2), camera.viewportHeight * (.5f + POPUP_HEIGHT/2), camera.viewportWidth * POPUP_WIDTH, Align.center, false);
        game.batch.end();
        //        game.batch.setColor(0f, 0f, 0f, 1f);
        for (int i = offset, j = 0; j < 6; i++, j++) {
            if (i < saveList.size() && i >= 0) {
                Save s = saveList.get(i);
    //            System.out.println(s.saveName);
                game.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                if (pressed == i) {
                    game.shapeRenderer.setColor(195/255f, 55/255f, 197/255f, 1f);
                } else {
                    game.shapeRenderer.setColor(255f/255f, 60f/255f, 122f/255f, 1f);
                }
    //            game.shapeRenderer.rect(100, 100 + 10 * i, BUTTON_WIDTH, BUTTON_HEIGHT);
                game.shapeRenderer.rect(camera.viewportWidth * (.5f - BUTTON_WIDTH/2), camera.viewportHeight * (1f - BUTTON_START - (j * TEXT_GAP)), camera.viewportWidth * BUTTON_WIDTH, camera.viewportHeight * BUTTON_HEIGHT);
                game.shapeRenderer.end();
                game.batch.begin();
                game.font33.draw(game.batch, s.saveName, camera.viewportWidth * (.5f - POPUP_WIDTH/2), camera.viewportHeight * (1f - TEXT_START - (j * TEXT_GAP)), camera.viewportWidth * POPUP_WIDTH, Align.center, false);
                game.batch.end();
            }
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

    public int getNumberOfSaves() {
        return this.saveList.size();
    }

    public void buttonClickedUp(int i) {
        System.out.println(saveList.get(i+offset).saveName + " pressed");
        pressed = -1;
        game.setScreen(new GameScreen(game, saveList.get(i+offset)));
    }

    public void buttonClickedDown(int i) {
        pressed = i+offset;
    }

    public Rectangle getMenuArea() { return popupButton; }

    public void resetClick() { pressed = -1; }

    public void scrollDown() { if (offset < saveList.size()-1) {offset++;} }

    public void scrollUp() { if (offset >= 1 ) {offset--;} }

    public void toMenuScreen() { game.setScreen(new MenuScreen(game)); }
}
