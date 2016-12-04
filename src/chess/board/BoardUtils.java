package chess.board;

/**
 * Created by stepanovep on 12/2/16.
 */

public class BoardUtils {

    public static final int NUM_CELLS = 64;
    public static final int NUM_CELLS_PER_ROW = 8;

    private BoardUtils() {
        throw new RuntimeException("Achtung!");
    }

    private static boolean[] intColumn (int columNumber) {
        final boolean[] column = new boolean[NUM_CELLS];
        do {
            column[columNumber] = true;
            columNumber += NUM_CELLS_PER_ROW;
        } while(columNumber < NUM_CELLS);
        return column;
    }

    public static boolean isValidCellCoordinate (int x, int y) {
        return x >= 0 && x < NUM_CELLS_PER_ROW && y >= 0 && y < NUM_CELLS_PER_ROW;
    }

}
