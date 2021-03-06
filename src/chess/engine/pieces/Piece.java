package chess.engine.pieces;

import chess.engine.Alliance;
import chess.engine.board.Board;
import chess.engine.board.Move;

import java.util.Collection;

/**
 * Created by stepanovep on 12/2/16.
 */

public abstract class Piece {

    protected final PieceType pieceType;
    protected final int piecePositionX;
    protected final int piecePositionY;
    protected final int piecePosition;
    protected final Alliance pieceAlliance;
    protected final boolean isFirstMove;
    private final int cachesHashCode;


    Piece(final PieceType pieceType,
          final int piecePositionX, final int piecePositionY,
          final Alliance pieceAlliance,
          final boolean isFirstMove) {

        this.pieceType = pieceType;
        this.pieceAlliance = pieceAlliance;
        this.piecePositionX = piecePositionX;
        this.piecePositionY = piecePositionY;
        this.piecePosition = piecePositionX*8 + piecePositionY;
        this.isFirstMove = isFirstMove;
        this.cachesHashCode = computeHashCode();
    }

    private int computeHashCode() {
        int result = pieceType.hashCode();
        result = 31 * result + pieceAlliance.hashCode();
        result = 31 * result + piecePosition;
        result = 31 * result + (isFirstMove ? 1 : 0);

        return result;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Piece)) {
            return false;
        }
        final Piece otherPiece = (Piece) other;
        return piecePosition == otherPiece.getPiecePosition() && pieceType == otherPiece.getPieceType()
                && pieceAlliance == otherPiece.getPieceAlliance() && isFirstMove == otherPiece.isFirstMove();
    }

    @Override
    public int hashCode() {
        return this.cachesHashCode;
    }

    @Override
    public String toString() {
        return this.pieceType.toString();
    }

    public String myToString() {
        return pieceType.toString() + ":" + (piecePositionX+1) + " " + (piecePositionY+1);
    }

    public int getPiecePositionX() {
        return piecePositionX;
    }

    public int getPiecePositionY() {
        return piecePositionY;
    }

    public int getPiecePosition() {
        return piecePositionX*8 + piecePositionY;
    }

    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    public boolean isFirstMove() {
        return this.isFirstMove;
    }

    public PieceType getPieceType() {
        return this.pieceType;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);

    public abstract Piece movePiece(Move move);

    public int getPieceValue() {
        return this.pieceType.getPieceValue();
    }

    public enum PieceType {

        PAWN("P", 100),
        KNIGHT("N", 300),
        BISHOP("B", 300),
        ROOK("R", 500),
        QUEEN("Q", 900),
        KING("K", 10000);

        public int getPieceValue() {
            return this.pieceValue;
        }

        // in the original code there is an abstract method isKing()
        // if it will FAIL use the original source !! Video# 17, 15:30
        public boolean isKing() {
            return pieceName.equals("K");
        }

        // in the original code there is an abstract method isKing()
        // if it will FAIL use the original source !! Video# 26, 15:45
        public boolean isRook() {
            return pieceName.equals("R");
        }

        private String pieceName;
        private int pieceValue;

        PieceType(final String pieceName, final int pieceValue) {
            this.pieceName = pieceName;
            this.pieceValue = pieceValue;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }
    }
}
