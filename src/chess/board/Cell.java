package chess.board;

import chess.pieces.Piece;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by stepanovep on 12/2/16.
 */

public abstract class Cell {

    protected final int x;
    protected final int y;

    private static final Map<Integer, EmptyCell> EMPTY_CELLS_CACHE = createALLPossibleEmptyCells();

    private static Map<Integer,EmptyCell> createALLPossibleEmptyCells() {

        final Map<Integer, EmptyCell> emptyCellMap = new HashMap<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                emptyCellMap.put(i*8 + j, new EmptyCell(i, j));
            }
        }

        return Collections.unmodifiableMap(emptyCellMap);
    }

    public static Cell createCell(final int x, final int y, final Piece piece) {
        return piece != null ? new OccupiedCell(x, y, piece) : EMPTY_CELLS_CACHE.get(x*8+y);
    }

    private Cell (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public abstract boolean isCellOccupied();
    public abstract Piece getPiece();

    public static final class EmptyCell extends Cell {

        private EmptyCell(final int x, final int y) {
            super(x, y);
        }

        @Override
        public boolean isCellOccupied() {
            return false;
        }

        @Override
        public Piece getPiece () {
            return null;
        }

        @Override
        public String toString() {
            return "-";
        }

    }

    public static final class OccupiedCell extends Cell {

        private Piece pieceOnCell;

        private OccupiedCell(int x, int y, final Piece pieceOnCell) {
            super(x, y);
            this.pieceOnCell = pieceOnCell;
        }

        @Override
        public boolean isCellOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return pieceOnCell;
        }

        @Override
        public String toString() {
            return this.pieceOnCell.toString();
        }
    }

    public static boolean legalPosition(final int x, final int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }
}
