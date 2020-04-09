package com.mozarellabytes.kroy.Save;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import com.mozarellabytes.kroy.Entities.AlienState;

import java.util.List;

public class SaveAlien {
    public float x;
    public float y;
    public float HP;
    public float speed;
    public List<Vector2> waypoints;
    public int waypointIndex;
    public Vector2 goal;
    public AlienState state;
}
