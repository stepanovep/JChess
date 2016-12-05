package chess.engine.board;

/**
 * Created by stepanovep on 12/2/16.
 */

public class BoardUtils {

    public static final int NUM_CELLS = 64;
    public static final int NUM_CELLS_PER_ROW = 8;

    public static final boolean[] FIRST_COLUMN = initColumn(0);
    public static final boolean[] SECOND_COLUMN = initColumn(1);
    public static final boolean[] THIRD_COLUMN = initColumn(2);
    public static final boolean[] FOURTH_COLUMN = initColumn(3);
    public static final boolean[] FIFTH_COLUMN = initColumn(4);
    public static final boolean[] SIXTH_COLUMN = initColumn(5);
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);

    public static final boolean[] EIGHT_ROW = initRow(0);
    public static final boolean[] SEVENTH_ROW = initRow(8);
    public static final boolean[] SIXTH_ROW = initRow(16);
    public static final boolean[] FIFTH_ROW = initRow(24);
    public static final boolean[] FOURTH_RANK = initRow(32);
    public static final boolean[] THRID_ROW = initRow(40);
    public static final boolean[] SECOND_ROW = initRow(48);
    public static final boolean[] FIRST_ROW = initRow(56);

    private BoardUtils() {
        throw new RuntimeException("Achtung!");
    }

    private static boolean[] initColumn(int columnNumber) {
        final boolean[] column = new boolean[NUM_CELLS];
        for(int i = 0; i < column.length; i++) {
            column[i] = false;
        }
        do {
            column[columnNumber] = true;
            columnNumber += NUM_CELLS_PER_ROW;
        } while(columnNumber < NUM_CELLS);
        return column;
    }

    private static boolean[] initRow(int rowNumber) {
        final boolean[] row = new boolean[NUM_CELLS];
        for(int i = 0; i < row.length; i++) {
            row[i] = false;
        }
        do {
            row[rowNumber] = true;
            rowNumber++;
        } while(rowNumber % NUM_CELLS_PER_ROW != 0);
        return row;
    }

    public static boolean isValidCellCoordinate (int x, int y) {
        return x >= 0 && x < NUM_CELLS_PER_ROW && y >= 0 && y < NUM_CELLS_PER_ROW;
    }

}
