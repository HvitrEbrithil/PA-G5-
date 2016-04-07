package no.pag6.models;

public class GameMap {
    GameLayer activeLayer;
    GameLayer inactiveLayer;

    public GameMap(GameLayer activeLayer, GameLayer inactiveLayer) {
        this.activeLayer = activeLayer;
        this.inactiveLayer = inactiveLayer;
    }

    // Set and get active layer

    public void setActiveLayer(GameLayer activeLayer){
        this.activeLayer = activeLayer;
    }

    public GameLayer getActiveLayer() {
        return activeLayer;
    }


    // Set and get inactive layer

    public void setInactiveLayer(GameLayer inactiveLayer) {
        this.inactiveLayer = inactiveLayer;
    }

    public GameLayer getInactiveLayer() {
        return inactiveLayer;
    }
}
