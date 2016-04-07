package no.pag6.models;

public class Player {
    private int score = 0;
    private PlayerCharacter gameCharacter;

    public Player(PlayerCharacter character){
        gameCharacter = character;
    }

    public void setScore(int points){
        score += points;
    }

    public int getScore() {
        return score;
    }
}
