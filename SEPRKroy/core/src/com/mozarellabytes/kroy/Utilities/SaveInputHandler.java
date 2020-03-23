package com.mozarellabytes.kroy.Utilities;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.mozarellabytes.kroy.Screens.LoadScreen;
import com.mozarellabytes.kroy.Screens.SaveScreen;

public class SaveInputHandler implements InputProcessor {

    private SaveScreen saveScreen;
    /** Boolean to track mouse clicks in order to avoid carryover click from the previous screen */
    private boolean isMouseDown = false;

    public SaveInputHandler(SaveScreen saveScreen) {
        this.saveScreen = saveScreen;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            saveScreen.toGameScreen();
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
        isMouseDown = true;
        for (int i = 0; i < saveScreen.buttonRects.size(); i++) {
            Rectangle r = saveScreen.buttonRects.get(i);
            if (r.contains(screenX, screenY)) {
                saveScreen.buttonClickedDown(i);
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (isMouseDown) {

            for (int i = 0; i < saveScreen.buttonRects.size(); i++) {
                Rectangle r = saveScreen.buttonRects.get(i);
                if (r.contains(screenX, screenY)) {
                    saveScreen.buttonClickedUp(i);
                    saveScreen.toGameScreen();
                }
            }
            saveScreen.resetClick();
            if (!saveScreen.getMenuArea().contains(screenX, screenY)) {
                saveScreen.toGameScreen();
            }
            isMouseDown = false;
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
