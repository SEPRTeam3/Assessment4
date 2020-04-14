package com.mozarellabytes.kroy.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;

import java.util.*;

public class PowerUps extends Sprite {

/*
Positions for new map
(35,20)
(11,29)
(11,12)
(11,2)
(47,5)
(32,8)
Spawn over time
Random locations
Mario Kart music
Weight randomness of item box
Common Healing/Refill commmon
epic Sticky and
legendary Rez
rarity symbol

Key to use them

two boxes right?
 */

//initialise first spawn;//Random random = new Random();
//int oneTwoThree = random.nextInt(3) + 1;

    /**
     * Set of item box textures, packed for animation
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

    private final Set<Vector2> powerUpPositions = new HashSet<>(new ArrayList<>(
            Arrays.asList(
                    new Vector2(3,7),
                    new Vector2(2,7),
                    new Vector2(4,7),
                    new Vector2(11,2),
                    new Vector2(3,5),
                    new Vector2(20,8)
            )));

    public HashMap<Vector2, Boolean> powerUpPositionSpawn = new HashMap<>();
    public HashMap<String, Boolean> itemBoxSpawn = new HashMap<>();

    public ArrayList<Vector2> getStickyRoadPositions() {
        return stickyRoadPositions;
    }

    private ArrayList<Vector2> stickyRoadPositions = new ArrayList<>();

    private String box;
    private Batch mapBatch;

    public boolean isSpawning() {
        return SpawningText;
    }

    private boolean Spawning = true;
    private boolean SpawningText = false;

    private PowerUp state;

    public PowerUp getLeftstate() {
        return leftstate;
    }

    public PowerUp getRightstate() {
        return rightstate;
    }

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

    public PowerUps(Batch mapBatch) {
        //super(new Texture(Gdx.files.internal("container.png")));

        this.health_pack_texture = new Texture(Gdx.files.internal("HealthUp.png"));
        this.refill_pack_texture = new Texture(Gdx.files.internal("refill.png"));
        this.sticky_road_texture = new Texture(Gdx.files.internal("stickyRoad.png"));
        this.resurrection_texture = new Texture(Gdx.files.internal("resurrection.png"));
        this.invisibility_texture = new Texture(Gdx.files.internal("Invisible.png"));
        this.empty_texture = new Texture(Gdx.files.internal("container.png"));
        this.sticky_road_tile_texture = new Texture(Gdx.files.internal("stickyRoadTile.png"));

        powerUpPositionSpawn.put(new Vector2(3,7), false);
        powerUpPositionSpawn.put(new Vector2(2,7), false);
        powerUpPositionSpawn.put(new Vector2(4,7), false);
        powerUpPositionSpawn.put(new Vector2(11,2), false);
        powerUpPositionSpawn.put(new Vector2(3,5), false);
        powerUpPositionSpawn.put(new Vector2(20,8), false);

        itemBoxSpawn.put("Left", false);
        itemBoxSpawn.put("Right", false);

        state = PowerUp.EMPTY;
        leftstate = PowerUp.EMPTY;
        rightstate = PowerUp.EMPTY;

        leftItemPosition = new Vector2(0.3f,20);
        rightItemPosition = new Vector2(2.3f,20);

        item_texture_atlas = new TextureAtlas(Gdx.files.internal("Atlas'/ItemFrame.atlas"));
        item_animation = new Animation<>(.5f, item_texture_atlas.findRegions("ItemFrame"));

        this.mapBatch = mapBatch;
    }

    public void spawnPowerUps(float delta) {
        //On clock tick or whatever set Spawing true
        int spawnCount = 0;

        for(Map.Entry entry : powerUpPositionSpawn.entrySet()) {
            spawnCount++;
            boolean isSpawn = (boolean) entry.getValue();
            Vector2 key = (Vector2) entry.getKey();

            if(!isSpawn) {
                if(Spawning) {
                    drawSprite(mapBatch, key, 1, 1);
                    powerUpPositionSpawn.replace(key, true);
                }
            } else {
                drawSprite(mapBatch, key, 1, 1);
            }

            if(spawnCount >=6) {
                Spawning = false;
            }
        }

        if(!Spawning) {
            Timer += delta;
            if(Timer >= 42) {
                SpawningText = true;
                if(Timer >= 45) {
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
            if(powerUpPositionSpawn.get(truckPos)) {
                if(itemBoxSpawn.get("Left") && itemBoxSpawn.get("Right")) {
                   //Both boxes are full
                } else {
                    PickUpPowerUp(truck);
                    powerUpPositionSpawn.replace(truckPos, false);
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
                if (key == "Left") {
                    state = leftstate;
                    drawItemBox(mapBatch, leftItemPosition, 2, 2);
                } else {
                    //right
                    state = rightstate;
                    drawItemBox(mapBatch, rightItemPosition, 2, 2);

                }
            } else {
                state = PowerUp.EMPTY;
                if (key == "Left") {
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
                //also do some cool effect like change the sprite
              truck.setInvisible(true);
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
}
