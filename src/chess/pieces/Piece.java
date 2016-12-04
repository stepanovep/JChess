package chess.pieces;

import chess.Alliance;
import chess.board.Board;
import chess.board.Cell;
import chess.board.Move;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by captain_nemo on 12/2/16.
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

    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    //public abstract boolean canMoveTo(int x, int y);

    public abstract Set<Move> calculateLegalMoves(final Board board);
}
