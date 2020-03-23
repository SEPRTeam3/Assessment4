package com.mozarellabytes.kroy.Utilities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.mozarellabytes.kroy.Screens.LoadScreen;

import java.awt.*;

public class LoadInputHandler implements InputProcessor {

    private LoadScreen loadScreen;

    public LoadInputHandler(LoadScreen loadScreen) {
        this.loadScreen = loadScreen;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            loadScreen.toMenuScreen();
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (int i = 0; i < loadScreen.buttonRects.size(); i++) {
            Rectangle r = loadScreen.buttonRects.get(i);
            if (r.contains(screenX, screenY)) {
                loadScreen.buttonClickedDown(i);
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        for (int i = 0; i < loadScreen.buttonRects.size(); i++) {
            Rectangle r = loadScreen.buttonRects.get(i);
            if (r.contains(screenX, screenY)) {
                loadScreen.buttonClickedUp(i);
            }
        }
        loadScreen.resetClick();
        if (!loadScreen.getMenuArea().contains(screenX, screenY)) {
            loadScreen.toMenuScreen();
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
