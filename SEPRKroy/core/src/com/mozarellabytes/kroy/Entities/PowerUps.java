package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.mozarellabytes.kroy.Save.Save;

import java.util.*;

public class PowerUps extends Sprite {

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

    private final Set<Vector2> powerUpPositions = new HashSet<>(new ArrayList<>(
            Arrays.asList(
                    new Vector2(3,7),
                    new Vector2(2,7),
                    new Vector2(4,7),
                    new Vector2(32,7),
                    new Vector2(20,21),
                    new Vector2(29,15)
            )));

    public HashMap<String, Boolean> powerUpPositionSpawn = new HashMap<>();
    public HashMap<String, Boolean> itemBoxSpawn = new HashMap<>();

    public ArrayList<Vector2> getStickyRoadPositions() {
        return stickyRoadPositions;
    }

    private ArrayList<Vector2> stickyRoadPositions = new ArrayList<>();

    private String box;
    private Batch mapBatch;

    public boolean isSpawningText() {
        return SpawningText;
    }
    public boolean isSpawning() {
        return Spawning;
    }

    private boolean Spawning;
    private boolean SpawningText = false;



    public PowerUp getLeftstate() {
        return leftstate;
    }

    public PowerUp getRightstate() {
        return rightstate;
    }

    private PowerUp state;
    private PowerUp leftstate;
    private PowerUp rightstate;

    public enum PowerUp {
        HEALTHPACK,
        REFILLPACK,
        STICKYROAD,
        RESURRECTION,
        INVISIBILITY,
        EMPTY
    }

    public Vector2 leftItemPosition;
    public Vector2 rightItemPosition;

    private float Timer = 0;
    private float invisibleTimer = 0;

    private boolean invisibleTimerBool;

    public HashMap<String, Boolean> getPowerUpPositionSpawn() {
        return powerUpPositionSpawn;
    }

    public HashMap<String, Boolean> getItemBoxSpawn() {
        return itemBoxSpawn;
    }

    public PowerUp getState() {
        return state;
    }

    private boolean loadFromSave = false;
    public PowerUps(Batch mapBatch) {
        //super(new Texture(Gdx.files.internal("container.png")));

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
        loadFromSave = false;
        invisibleTimerBool = false;

        this.mapBatch = mapBatch;
    }

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

        loadFromSave = true;
        invisibleTimerBool = save.powerUps.isInvisTimer;
        this.mapBatch = mapBatch;
    }
    public Vector2 fromString (String v) {
        int s = v.indexOf(',', 1);
        if (s != -1 && v.charAt(0) == '(' && v.charAt(v.length() - 1) == ')') {
            try {
                float x = Float.parseFloat(v.substring(1, s));
                float y = Float.parseFloat(v.substring(s + 1, v.length() - 1));
                return new Vector2(x,y);
            } catch (NumberFormatException ex) {
                // Throw a GdxRuntimeException
            }
        }
        throw new GdxRuntimeException("Malformed Vector2: " + v);
    }

    public void spawnPowerUps(float delta) {
        //On clock tick or whatever set Spawing true
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


    public void OnPowerUpTile(FireTruck truck) {

        Vector2 truckPos = new Vector2(Math.round(truck.getPosition().x), Math.round(truck.getPosition().y));
        if(powerUpPositions.contains(truckPos)) {
            //if it is a valid point
            System.out.print(truckPos.toString());
            if(powerUpPositionSpawn.get(truckPos.toString())) {
                if(itemBoxSpawn.get("Left") && itemBoxSpawn.get("Right")) {
                   //Both boxes are full
                } else {
                    PickUpPowerUp(truck);
                    powerUpPositionSpawn.replace(truckPos.toString(), false);
                }
            } else {
                //no power up
            }
        }

    }
    public void PickUpPowerUp(FireTruck rez) {
        box = "Left";
        if(itemBoxSpawn.get(box)){
            box = "Right";
        }
        itemBoxSpawn.replace(box, true);

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
            rez.setResurrection(true);
        }
        if(box == "Left") {
            leftstate = state;
            drawItemBox(mapBatch, leftItemPosition, 2,2);
        } else if (box == "Right") {
            rightstate = state;
            drawItemBox(mapBatch, rightItemPosition, 2,2);
        }

    }

    public void drawSprite(Batch mapBatch, Vector2 position, int width, int height) {
        elapsedTime += Gdx.graphics.getDeltaTime();

        TextureRegion currentFrame = item_animation.getKeyFrame(elapsedTime, true);
        mapBatch.draw(currentFrame, position.x, position.y, width, height);
    }

    public void drawStickyRoad() {
        for(Vector2 pos: stickyRoadPositions) {
            mapBatch.draw(sticky_road_tile_texture, pos.x, pos.y, 1, 1);
        }
    }
    public void removeStickyRoad(Vector2 position) {
        stickyRoadPositions.remove(position);
    }

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
    public void Resurrection(FireTruck truck) {
            truck.setResurrection(false);
            truck.setHP(truck.getType().getMaxHP()/2);
            if(leftstate == PowerUp.RESURRECTION) {
                itemBoxSpawn.replace("Left", false);
                leftstate = PowerUp.EMPTY;
            } else if (rightstate == PowerUp.RESURRECTION) {
                itemBoxSpawn.replace("Right", false);
                rightstate = PowerUp.EMPTY;
            } else {
            }

    }

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
            //maybe just get rid of it for an empty space
                break;
            case INVISIBILITY:
              truck.setInvisible(true);
              invisibleTimerBool = true;
                break;
            default:

                break;
        }
    }

    /*
    public boolean drawExplosion(Batch mapBatch){
        // Accumulate amount of time that has passed
        elapsedTime += Gdx.graphics.getDeltaTime();

        // Get current frame of animation for the current stateTime
        TextureRegion currentFrame = animation.getKeyFrame(elapsedTime, false);
        mapBatch.draw(currentFrame, x, y, width, height);
        if(elapsedTime > animation.getAnimationDuration()){
            return true;
        }
        return false
        }
     */

    public void dispose(){
        item_texture_atlas.dispose();
    }
    public boolean isInvisibleTimer() {
        return invisibleTimerBool;
    }

    public void setInvisibleTimer(float delta) {
        if(invisibleTimerBool) {
            invisibleTimer += delta;
            if (invisibleTimer > 10) {
                invisibleTimer = 0;
                invisibleTimerBool = false;
            }
        }
    }
}
