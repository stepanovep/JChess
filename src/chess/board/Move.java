package chess.board;

import chess.pieces.Piece;

/**
 * Created by captain_nemo on 12/2/16.
 */

public class Move {

    final Board board;
    final Piece movedPiece;
    final int destCoordX, destCoordY;

    public Move(final Board board,
         final Piece movedPiece,
         final int destCoordX, final int destCoordY) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destCoordX = destCoordX;
        this.destCoordY = destCoordY;
    }

    public static final class MajorMove extends Move {

        public MajorMove(final Board board,
                         final Piece movedPiece,
                         final int destCoordX,
                         final int destCoordY) {
            super(board, movedPiece, destCoordX, destCoordY);
        }
    }

    public static final class AttackMove extends Move {

        final Piece attackedPiece;

        public AttackMove(final Board board,
                   final Piece movedPiece,
                   final int destCoordX, final int destCoordY,
                   final Piece attackedPiece) {
            super(board, movedPiece, destCoordX, destCoordY);
            this.attackedPiece = attackedPiece;
        }
    }
}
