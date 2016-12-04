package chess.board;

import chess.board.Board.Builder;
import chess.pieces.Piece;

/**
 * Created by stepanovep on 12/2/16.
 */

public abstract class Move {

    final Board board;
    final Piece movedPiece;
    final int destCoordX, destCoordY;

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

    public int getDestCoordX() {
        return destCoordX;
    }

    public int getDestCoordY() {
        return destCoordY;
    }

    public Piece getMovedPiece() {
        return this.movedPiece;
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
            final Builder builder = new Builder();
            for (final Piece piece: this.board.currentPlayer().getActivePieces()) {
                // TODO hashCode and equals for Pieces
                if (!this.movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for (final Piece piece: this.board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }
            // move the moved piece!
            builder.setPiece(this.movedPiece.movedPiece(this));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
            return builder.build();
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
