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
          final Alliance pieceAlliance) {

        this.pieceType = pieceType;
        this.pieceAlliance = pieceAlliance;
        this.piecePositionX = piecePositionX;
        this.piecePositionY = piecePositionY;
        this.piecePosition = piecePositionX*8 + piecePositionY;
        this.isFirstMove = false;
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

    public abstract Piece movedPiece(Move move);

    public enum PieceType {

        PAWN("P"),
        KNIGHT("N"),
        BISHOP("B"),
        ROOK("R"),
        QUEEN("Q"),
        KING("K");

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

        PieceType(final String pieceName) {
            this.pieceName = pieceName;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }
    }
}
