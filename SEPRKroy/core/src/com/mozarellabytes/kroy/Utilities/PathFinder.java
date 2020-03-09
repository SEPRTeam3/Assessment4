package com.mozarellabytes.kroy.Utilities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class PathFinder {
    LinkedList<Vector2> reconstructedPath;

    Vector2 currentPos;

    final int[] directionX = {-1, 1, 0, 0};
    final int[] directionY = {0, 0, 1, -1};

    boolean[][] vistited;

    public Queue<Vector2> allRoutes;

    private Vector2[] newPath;


    boolean reachedEnd = false;

    private Vector2 previousTile;

    private int reRouteCounter = 0;

    private Vector2 coordinate;
    private Queue<Vector2> currentPath;

    public PathFinder(Vector2 coordinate, Queue<Vector2> currentPath) {
        this.coordinate = coordinate;
        this.currentPath = currentPath;
    }

    public boolean isDragOffMap() {
        if (coordinate.y < 28) {
            if ((int) Math.abs(currentPath.last().x - coordinate.x) + (int) Math.abs(currentPath.last().y - coordinate.y) >= 2) {
                return true;
            } else {
                return false;
            }
        } else {
            return  false;
        }

    }
    private Vector2[] findPath(Vector2 goal, Vector2 start) {
        allRoutes= new Queue<>();


        vistited = new boolean[48][29];
        newPath = new Vector2[1392];

        for(int i=0; i<48; i++){
            for(int j=0; j<29; j++){
                vistited[i][j] = false;
            }
        }
        allRoutes.addLast(start);
        vistited[(int) start.x][(int) start.y] = true;

        while (!allRoutes.isEmpty()) {
            currentPos = allRoutes.removeLast();

            if(currentPos.x == goal.x && currentPos.y == goal.y) {
                reachedEnd = true;
                break;
            }
            exploreNeighbours(currentPos);
        }
        return shortestPath(goal, start);
    }

    private void exploreNeighbours(Vector2 currentPos) {

        for(int i = 0; i < 4; i++) {
            Vector2 newPos = new Vector2();
            newPos.x = currentPos.x + directionX[i];
            newPos.y = currentPos.y + directionY[i];

            if(newPos.x < 0 || newPos.y < 0) {
                continue;
            }
            if(newPos.x > 47 || newPos.y > 28) {
                continue;
            }

            //boolean isRoad = gameScreen.isRoad(Math.round(newPos.x), Math.round(newPos.y));
            //if(!isRoad) { continue; }

            if(vistited[(int)newPos.x][(int)newPos.y]) {
                continue;
            }
            allRoutes.addFirst(newPos);

            vistited[(int)newPos.x] [(int)newPos.y] = true;

            newPath[convertVector2ToIntPositionInMap(newPos)] = currentPos;
        }
    }

    private int convertVector2ToIntPositionInMap(Vector2 pos) {
        return ((int) (pos.x * 29 + pos.y));
    }

    private void reverse(Vector2[] a)
    {
        Collections.reverse(Arrays.asList(a));
    }

    private Vector2[] shortestPath(Vector2 endPos, Vector2 startPos) {
        reconstructedPath = new LinkedList<>();
        for(Vector2 at = endPos; at != null; at = newPath[convertVector2ToIntPositionInMap(at)]) {
            if(at == startPos) {
                //if(!this.trailPath.isEmpty())
                //continue;
            }
            reconstructedPath.add(at);
        }

        Object[] objectAarray = reconstructedPath.toArray();
        Vector2[] path = new Vector2[objectAarray.length];
        for(int i=0;i<objectAarray.length;i++) {
            path[i] = (Vector2) objectAarray[i];
        }
        reverse(path);
        return  path;
    }



}
