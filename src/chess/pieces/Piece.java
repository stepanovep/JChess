package chess.pieces;

import chess.Alliance;
import chess.board.Board;
import chess.board.Move;

import java.util.Collection;

/**
 * Created by stepanovep on 12/2/16.
 */

public abstract class Piece {

    protected final int piecePositionX;
    protected final int piecePositionY;
    protected final Alliance pieceAlliance;


    Piece(final int piecePositionX, final int piecePositionY, final Alliance pieceAlliance) {
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

    //public abstract boolean canMoveTo(int x, int y);

    public abstract Collection<Move> calculateLegalMoves(final Board board);
}
