package chess.board;

import chess.pieces.Piece;

/**
 * Created by stepanovep on 12/2/16.
 */

public abstract class Move {

    private final Board board;
    private final Piece movedPiece;
    private final int destCoordX, destCoordY;

    private Move(final Board board,
         final Piece movedPiece,
         final int destCoordX, final int destCoordY) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destCoordX = destCoordX;
        this.destCoordY = destCoordY;
    }

    public int getDestinationCoordinate() {
        return destCoordX*8 + destCoordY;
    }

    public abstract Board execute();

    public static final class MajorMove extends Move {

        public MajorMove(final Board board,
                         final Piece movedPiece,
                         final int destCoordX,
                         final int destCoordY) {
            super(board, movedPiece, destCoordX, destCoordY);
        }

        @Override
        public Board execute() {
            return null;
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

        @Override
        public Board execute() {
            return null;
        }
    }
}
