package chess.pieces;

import chess.Alliance;
import chess.board.Board;
import chess.board.Move;

import java.util.Collection;

/**
 * Created by stepanovep on 12/2/16.
 */

public abstract class Piece {

    protected final PieceType pieceType;
    protected final int piecePositionX;
    protected final int piecePositionY;
    protected final Alliance pieceAlliance;


    Piece(final PieceType pieceType,
          final int piecePositionX, final int piecePositionY,
          final Alliance pieceAlliance) {

        this.pieceType = pieceType;
        this.pieceAlliance = pieceAlliance;
        this.piecePositionX = piecePositionX;
        this.piecePositionY = piecePositionY;
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

    public PieceType getPieceType() {
        return this.pieceType;
    }

    public abstract Collection<Move> calculateLegalMoves(final Board board);

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
