package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

/**
 * FortressType is an enum defining the fortresses that can be present in the game.
 * Each fortress type has a unique name, delay between firing bombs, attack range,
 * maximum health points, attack points, width, height and sprite.
 * This allows there to be numerous different types of fortresses without having
 * to randomly generate values which may make the game unplayable in some instances
 * and too easy in other instances.
 */

public enum FortressType {

    /** The preset values for the different fortress types includes the type's:
     * name, delay between firing bombs, attack range, maximum health points,
     * attack points, width, height and sprite.
     */
    //#Asessment3 Changed attack power values for assessment 3
    Revs ("Revolution", 2500, 7, 90, 4, 5, 3, new Texture(Gdx.files.internal("sprites/fortress/fortress_revs.png"))),
    Walmgate ("Walmgate Bar", 1500, 8, 90, 6, 5, 5, new Texture(Gdx.files.internal("sprites/fortress/fortress_walmgate.png"))),
    Clifford ("Clifford's Tower", 500, 4, 90, 7, 4, 3, new Texture(Gdx.files.internal("sprites/fortress/fortress_clifford.png"))),

    //#Assessment3 Added 3 new fortresses
    TrainStation ("Train Station", 3000, 5, 90, 5, 7, 4, new Texture(Gdx.files.internal("sprites/fortress/train.png"))),
    Minster ("Minster", 2000, 6, 100, 7, 5, 6, new Texture(Gdx.files.internal("sprites/fortress/minster.png"))),
    Shambles ("Shambles", 1000, 9, 80, 6, 6, 4, new Texture(Gdx.files.internal("sprites/fortress/shambles.png")));

    /** The name for the fortress, visible once the fortress has been clicked on */
    private final String name;

    /** The time between firing bombs */
    private final int delay;

    /** The range that the fortress can see and attack firetrucks */
    private final float range;

    /** The maximum health points for the fortress - always 100 */
    private final float maxHP;

    /** Attack points - how much damage the fortress can inflict */
    private final float AP;

    /** The width of the sprite measured in tiles */
    private final int w;

    /** The height of the sprite measured in tiles */
    private final int h;

    /** The sprite for the fortress */
    private final Texture texture;

    /**
     * Constructs the FortressType
     *
     * @param name The name for this type of fortress
     * @param delay The delay between firing bombs in millimillisecond
     * @param range The attack range for this type of fortress in tiles
     * @param maxHP The maximum health points for this type of fortress
     * @param AP The attack points for this type of fortress
     * @param w The width of the sprite for this type of fortress in tiles
     * @param h The height of the sprite for this type of fortress in tiles
     * @param texture The sprite for this type of fortress
     *
     */
    FortressType(String name, int delay, float range, float maxHP, float AP, int w, int h, Texture texture) {
        this.name = name;
        this.delay = delay;
        this.range = range;
        this.maxHP = maxHP;
        this.AP = AP;
        this.w = w;
        this.h = h;
        this.texture = texture;
    }

    public String getName() { return name; }

    public int getDelay() { return delay; }

    public float getRange() { return range; }

    public float getMaxHP() { return maxHP; }

    public float getAP() { return AP; }

    public int getW() { return w; }

    public int getH() { return h; }

    public Texture getTexture() { return texture; }

}
