package chess.engine.board;

import java.util.HashMap;
import java.util.Map;

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

    public static final String[][] ALGEBRAIC_NOTATION = initializeAlgebraicNotation();
    public static final Map<String, Integer> POSITION_TO_COORDINATE = initializePositionToCoordinateMap();

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

    private static Map<String, Integer> initializePositionToCoordinateMap() {
        final Map<String, Integer> positionToCoordinate = new HashMap<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                positionToCoordinate.put(ALGEBRAIC_NOTATION[i][j], i*8+j);
            }
        }
        return positionToCoordinate;
    }

    private static String[][] initializeAlgebraicNotation() {
        return new String[][] {
                {"a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8"},
                {"a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7"},
                {"a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6"},
                {"a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5"},
                {"a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4"},
                {"a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3"},
                {"a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2"},
                {"a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"}
        };
    }

    public static boolean isValidCellCoordinate (int x, int y) {
        return x >= 0 && x < NUM_CELLS_PER_ROW && y >= 0 && y < NUM_CELLS_PER_ROW;
    }

    public static int getCoordinateAtPosition(final String position) {
        return POSITION_TO_COORDINATE.get(position);
    }

    public static String getPositionAtCoordinate(final int coordinate) {
        final int x = coordinate / 8;
        final int y = coordinate % 8;
        return ALGEBRAIC_NOTATION[x][y];
    }
}
