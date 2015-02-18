package de.dhbw.mbfl.jconnect4lib.board;

/**
 * Created by florian on 18.02.15.
 */
public class BoardUtils {

    public static void addStonesToBoard(Board board, String webUILogStr) {
        String[] lines = webUILogStr.split("\\$");

        for (String line : lines) {
            String[] positions = line.split(";");
            for (String positionStr : positions) {
                if (positionStr.isEmpty()) continue;

                board.addStone(new Position(positionStr));
            }
        }
    }

}
