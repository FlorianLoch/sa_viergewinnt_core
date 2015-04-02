package de.dhbw.mbfl.jconnect4lib.board;

/**
 * Created by florian on 02.04.15.
 */
public enum GameState {
    WIN(2), REMIS(1), NOT_YET_OVER(0);

    private int intRepresentation;

    GameState(int intRepresentation) {
        this.intRepresentation = intRepresentation;
    }

    public int getIntRepresentation() {
        return intRepresentation;
    }
}
