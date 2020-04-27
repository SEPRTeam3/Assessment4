package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.mozarellabytes.kroy.Save.Save;

import java.io.IOException;
import java.util.*;

public class PowerUps extends Sprite {

    /**
     * #Assessment4
     * Set of textures and animated textures
     */
    private TextureAtlas item_texture_atlas;
    private Animation<TextureRegion> item_animation;
    private float elapsedTime;

    private final Texture health_pack_texture;
    private final Texture refill_pack_texture;
    private final Texture sticky_road_texture;
    private final Texture resurrection_texture;
    private final Texture invisibility_texture;
    private final Texture empty_texture;
    private final Texture sticky_road_tile_texture;
    /**
     * #Assessment4
     * Hard coded positions of power ups
     */
    private final Set<Vector2> powerUpPositions = new HashSet<>(new ArrayList<>(
            Arrays.asList(
                    new Vector2(3,7),
                    new Vector2(2,7),
                    new Vector2(4,7),
                    new Vector2(32,7),
                    new Vector2(20,21),
                    new Vector2(29,15)
            )));

    /**
     * #Assessment4
     * Set of positions as strings and corresponding booleans to show if an item in a position exists
     */
    public HashMap<String, Boolean> powerUpPositionSpawn = new HashMap<>();
    public HashMap<String, Boolean> itemBoxSpawn = new HashMap<>();
    private ArrayList<Vector2> stickyRoadPositions = new ArrayList<>();
    public HashMap<String, Boolean> getPowerUpPositionSpawn() { return powerUpPositionSpawn; }
    public HashMap<String, Boolean> getItemBoxSpawn() { return itemBoxSpawn; }
    public ArrayList<Vector2> getStickyRoadPositions() {
        return stickyRoadPositions;
    }

    private String currentBoxType;
    public Vector2 leftItemPosition;
    public Vector2 rightItemPosition;
    /**
     * #Assessment4
     * Batch we draw with
     */
    private Batch mapBatch;
    /**
     * #Assessment4
     * Booleans so set spawning text and start spawning power ups
     */
    private boolean Spawning;
    private boolean SpawningText = false;
    public boolean isSpawningText() {
        return SpawningText;
    }
    public boolean isSpawning() {
        return Spawning;
    }

    /**
     * #Assessment4
     * Power up state based on type
     */
    private PowerUp state;
    private PowerUp leftstate;
    private PowerUp rightstate;
    public PowerUp getState() {
        return state;
    }
    public PowerUp getLeftstate() {
        return leftstate;
    }
    public PowerUp getRightstate() {
        return rightstate;
    }

    /**
     * #Assessment4
     * Power up types
     */
    public enum PowerUp {
        HEALTHPACK,
        REFILLPACK,
        STICKYROAD,
        RESURRECTION,
        INVISIBILITY,
        EMPTY
    }
    /**
     * #Assessment4
     * Timers for spawning and power ups
     */
    private float Timer = 0;
    private float invisibleTimer = 0;
    private boolean invisibleTimerBool;
    public boolean isInvisibleTimer() { return invisibleTimerBool; }
    public float getInvisibleTimer() { return invisibleTimer; }

    /**
     * #Assessment4
     * Constructor, setting power up positions, states and initialising textures
     * @param mapBatch setting the mapBatch to draw power up sprites with
     */
    public PowerUps(Batch mapBatch) {
        this.health_pack_texture = new Texture(Gdx.files.internal("HealthUp.png"));
        this.refill_pack_texture = new Texture(Gdx.files.internal("refill.png"));
        this.sticky_road_texture = new Texture(Gdx.files.internal("stickyRoad.png"));
        this.resurrection_texture = new Texture(Gdx.files.internal("resurrection.png"));
        this.invisibility_texture = new Texture(Gdx.files.internal("Invisible.png"));
        this.empty_texture = new Texture(Gdx.files.internal("container.png"));
        this.sticky_road_tile_texture = new Texture(Gdx.files.internal("stickyRoadTile.png"));

        powerUpPositionSpawn.put("(3.0,7.0)", false);
        powerUpPositionSpawn.put("(2.0,7.0)", false);
        powerUpPositionSpawn.put("(4.0,7.0)", false);
        powerUpPositionSpawn.put("(32.0,7.0)", false);
        powerUpPositionSpawn.put("(20.0,21.0)", false);
        powerUpPositionSpawn.put("(29.0,15.0)", false);

        itemBoxSpawn.put("Left", false);
        itemBoxSpawn.put("Right", false);

        state = PowerUp.EMPTY;
        leftstate = PowerUp.EMPTY;
        rightstate = PowerUp.EMPTY;

        leftItemPosition = new Vector2(0.3f,20);
        rightItemPosition = new Vector2(2.3f,20);

        item_texture_atlas = new TextureAtlas(Gdx.files.internal("Atlas'/ItemFrame.atlas"));
        item_animation = new Animation<>(.5f, item_texture_atlas.findRegions("ItemFrame"));

        Spawning = true;
        invisibleTimerBool = false;

        this.mapBatch = mapBatch;
    }
    /**
     * #Assessment4
     * Constructor from save, setting power up positions, states and initialising textures
     * @param save getting saved data
     * @param mapBatch setting the mapBatch to draw power up sprites with
     */
    public PowerUps(Save save, Batch mapBatch) {
        this.health_pack_texture = new Texture(Gdx.files.internal("HealthUp.png"));
        this.refill_pack_texture = new Texture(Gdx.files.internal("refill.png"));
        this.sticky_road_texture = new Texture(Gdx.files.internal("stickyRoad.png"));
        this.resurrection_texture = new Texture(Gdx.files.internal("resurrection.png"));
        this.invisibility_texture = new Texture(Gdx.files.internal("Invisible.png"));
        this.empty_texture = new Texture(Gdx.files.internal("container.png"));
        this.sticky_road_tile_texture = new Texture(Gdx.files.internal("stickyRoadTile.png"));

        item_texture_atlas = new TextureAtlas(Gdx.files.internal("Atlas'/ItemFrame.atlas"));
        item_animation = new Animation<>(.5f, item_texture_atlas.findRegions("ItemFrame"));

        leftItemPosition = new Vector2(0.3f,20);
        rightItemPosition = new Vector2(2.3f,20);

        powerUpPositionSpawn = save.powerUps.powerUpPositionSpawn;
        itemBoxSpawn = save.powerUps.itemBoxSpawn;
        stickyRoadPositions = save.powerUps.stickyRoadPositions;
        state = save.powerUps.state;
        leftstate = save.powerUps.leftstate;
        rightstate = save.powerUps.rightstate;

        invisibleTimerBool = save.powerUps.isInvisTimer;
        this.mapBatch = mapBatch;
    }

    /**
     * #Assessment4
     * Spawns power ups periodically in locations defined by a hash map
     * @param delta increments timer for spawning based on the render update
     */
    public void spawnPowerUps(float delta) {
        int spawnCount = 0;

        for(Map.Entry entry : powerUpPositionSpawn.entrySet()) {
            spawnCount++;
            boolean isSpawn = (boolean) entry.getValue();
            String key = (String) entry.getKey();

            if(!isSpawn) {
                if(Spawning) {
                    drawSprite(mapBatch, fromString(key), 1, 1);
                    powerUpPositionSpawn.replace(key, true);
                }
            } else {
                drawSprite(mapBatch, fromString(key), 1, 1);
            }

            if(spawnCount >=6) {
                Spawning = false;
            }
        }

        if(!Spawning) {
            Timer += delta;
            if(Timer >= 52) {
                SpawningText = true;
                if(Timer >= 55) {
                    SpawningText = false;
                    Timer = 0;
                    Spawning = true;
                }
            }

        }
    }

    /**
     * #Assessment4
     * Checks if a given fire truck is on a tile that contains a power up
     * Triggers an action depending on if the item boxes are full or not
     * @param truck the current truck we create a Vector2 position from
     */
    public void OnPowerUpTile(FireTruck truck) {
        Vector2 truckPos = new Vector2(Math.round(truck.getPosition().x), Math.round(truck.getPosition().y));
        if(powerUpPositions.contains(truckPos)) {
            if(powerUpPositionSpawn.get(truckPos.toString())) {
                if(itemBoxSpawn.get("Left") && itemBoxSpawn.get("Right")) {
                    //Both boxes are full
                } else {
                    PickUpPowerUp();
                    powerUpPositionSpawn.replace(truckPos.toString(), false);
                }
            }
        }
    }
    /**
     * #Assessment4
     * Determines which power up the player gets randomly
     * Picks a free box for the power up to be drawn into
     */
    public void PickUpPowerUp() {
        currentBoxType = "Left";
        if(itemBoxSpawn.get(currentBoxType)){
            currentBoxType = "Right";
        }
        itemBoxSpawn.replace(currentBoxType, true);

        Random random = new Random();
        int rand = random.nextInt(100) + 1;
        if(rand <= 30) {
            this.state = PowerUp.HEALTHPACK;
        } else if(rand <= 55){
            this.state = PowerUp.REFILLPACK;
        } else if (rand <= 75){
            this.state = PowerUp.STICKYROAD;
        } else if (rand <= 90){
            this.state = PowerUp.INVISIBILITY;
        } else {
            this.state = PowerUp.RESURRECTION;
        }
        if(currentBoxType == "Left") {
            leftstate = state;
            drawItemBox(mapBatch, leftItemPosition, 2,2);
        } else if (currentBoxType == "Right") {
            rightstate = state;
            drawItemBox(mapBatch, rightItemPosition, 2,2);
        }

    }

    /**
     * #Assessment4
     * Draws animated pick up box
     * @param mapBatch gets mapBatch the game screen uses for drawing
     * @param position position to be drawn
     * @param width width of drawn object
     * @param height height of drawn object
     */
    public void drawSprite(Batch mapBatch, Vector2 position, int width, int height) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = item_animation.getKeyFrame(elapsedTime, true);
        mapBatch.draw(currentFrame, position.x, position.y, width, height);
    }
    /**
     * #Assessment4
     * Draws a sticky road sprite from a list of sticky road positions
     */
    public void drawStickyRoad() {
        for(Vector2 pos: stickyRoadPositions) {
            mapBatch.draw(sticky_road_tile_texture, pos.x, pos.y, 1, 1);
        }
    }
    /**
     * #Assessment4
     * Removes a sticky road from the list of sticky road positions
     */
    public void removeStickyRoad(Vector2 position) {
        stickyRoadPositions.remove(position);
    }
    /**
     * #Assessment4
     *Updates the item boxes and draws the new items
     */
    public void ItemBoxUpdate() {
        for (Map.Entry entry : itemBoxSpawn.entrySet()) {
            boolean isSpawn = (boolean) entry.getValue();
            String key = (String) entry.getKey();
            if (isSpawn) {
                if (key.contentEquals("Left")) {
                    state = leftstate;
                    drawItemBox(mapBatch, leftItemPosition, 2, 2);
                } else {
                    //right
                    state = rightstate;
                    drawItemBox(mapBatch, rightItemPosition, 2, 2);
                }
            } else {
                state = PowerUp.EMPTY;
                if (key.contentEquals("Left")) {
                    drawItemBox(mapBatch, leftItemPosition, 2, 2);
                } else {
                    drawItemBox(mapBatch, rightItemPosition, 2, 2);
                }
            }
        }
    }
    /**
     * #Assessment4
     * Draws the item depending on the state
     * @param mapBatch gets mapBatch the game screen uses for drawing
     * @param position position to be drawn
     * @param width width of drawn object
     * @param height height of drawn object
     */
    public void drawItemBox(Batch mapBatch, Vector2 position, int width, int height) {
        switch (state) {
            case HEALTHPACK:
                mapBatch.draw(health_pack_texture, position.x, position.y, width, height);
                break;
            case REFILLPACK:
                mapBatch.draw(refill_pack_texture, position.x, position.y, width, height);
                break;
            case STICKYROAD:
                mapBatch.draw(sticky_road_texture, position.x, position.y, width, height);
                break;
            case RESURRECTION:
                mapBatch.draw(resurrection_texture, position.x, position.y, width, height);
                break;
            case INVISIBILITY:
                mapBatch.draw(invisibility_texture, position.x, position.y, width, height);
                break;
            default:
                mapBatch.draw(empty_texture, position.x, position.y, width, height);
                break;
        }
    }
    /**
     * #Assessment4
     * Activates the item in slot 1 or slot 2
     * @param key the key pressed to activate the item
     * @param truck selected truck the item will have an effect on
     */
    public void usePowerUp(char key, FireTruck truck) {
        if(key == '1') {
            PowerUpAction(leftstate, truck);
            String box = "Left";
            itemBoxSpawn.replace(box, false);
            state = PowerUp.EMPTY;
            leftstate = state;

        } else if(key == '2') {
            PowerUpAction(rightstate, truck);
            String box = "Right";
            itemBoxSpawn.replace(box, false);
            state = PowerUp.EMPTY;
            rightstate = state;

        }
    }
    /**
     * #Assessment4
     * Activates resurrection which restores a truck's health when it dies
     * @param truck gets max health of the truck type
     */
    public void Resurrection(FireTruck truck) {
        truck.setResurrection(false);
        truck.setHP(truck.getType().getMaxHP()/1.5f);
    }
    /**
     * #Assessment4
     * Activates power ups
     * @param state The state representing the power ups
     * @param truck Current truck that we take stats from
     */
    public void PowerUpAction(PowerUp state, FireTruck truck) {
        switch (state) {
            case HEALTHPACK:
                    truck.setHP(truck.getType().getMaxHP());
                break;
            case REFILLPACK:
                    truck.setReserve(truck.getType().getMaxReserve());
                break;
            case STICKYROAD:
                stickyRoadPositions.add(new Vector2(truck.getPosition().x, truck.getPosition().y));
                break;
            case RESURRECTION:
                truck.setResurrection(true);
                break;
            case INVISIBILITY:
              truck.setInvisible(true);
              invisibleTimerBool = true;
                break;
            default:
                break;
        }
    }
    /**
     * #Assessment4
     * Timer for invisibility power up
     * @param delta increments timer for invisibility based on the render update
     */
    public void setInvisibleTimer(float delta) {
        if(invisibleTimerBool) {
            invisibleTimer += delta;
            if (invisibleTimer > 10) {
                invisibleTimer = 0;
                invisibleTimerBool = false;
            }
        }
    }
    /**
     * #Assessment4
     * Converts a string into a Vector2
     * Used for the hashmap keys as JSON saves keys as strings
     * @param v The string Vector2
     */
    public Vector2 fromString (String v) {
        int s = v.indexOf(',', 1);
        if (s != -1 && v.charAt(0) == '(' && v.charAt(v.length() - 1) == ')') {
            try {
                float x = Float.parseFloat(v.substring(1, s));
                float y = Float.parseFloat(v.substring(s + 1, v.length() - 1));
                return new Vector2(x,y);
            } catch (NumberFormatException ex) {

            }
        }
        throw new GdxRuntimeException("Malformed Vector2: " + v);
    }

    public void dispose(){
        item_texture_atlas.dispose();
    }
}
