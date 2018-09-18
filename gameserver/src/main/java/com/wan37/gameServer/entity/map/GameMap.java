package com.wan37.gameServer.entity.map;


import com.wan37.gameServer.common.MapMarker;

/**
 * @author gonefuture  gonefuture@qq.com
 * time 2018/9/10 15:49
 * @version 1.00
 * Description: JavaLearn
 */
public  class GameMap implements IGameMap<MapMarker> {

    // 地图名字
    private String gameMapName;

    // 行与列
    private int row;
    private int column;

    private MapMarker[][] map = new MapMarker[row][column];

    public GameMap() {

    }

    public GameMap(String name, int row, int clumn) {
        this.gameMapName = name;
        this.row = row;
        this.column = clumn;
    }

    @Override
    public MapMarker[][] init() {
        return null;
    }

    @Override
    public MapMarker[][] getMap() {
        return map;
    }

    @Override
    public void setMap(MapMarker[][] map) {
        this.map = map;
    }

    @Override
    public String StringMap() {
        StringBuilder save = new StringBuilder();
        for (int i=0; i<row; i++) {
            for (int j = 0; j< column; j++) {
                save.append(map[i][j].toString()+ " ");
            }
            save.append("\n");
        }
        return save.toString();
    }


}
