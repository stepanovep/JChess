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
    final int destinationCoordinate;

    public static final Move NULL_MOVE = new NullMove();

    private Move(final Board board,
         final Piece movedPiece,
         final int destCoordX, final int destCoordY) {

        this.board = board;
        this.movedPiece = movedPiece;
        this.destCoordX = destCoordX;
        this.destCoordY = destCoordY;
        this.destinationCoordinate = destCoordX*8 + destCoordY;
    }

    public int getCurrentCoordinate() {
        return this.getMovedPiece().getPiecePosition();
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

    public static class MajorMove extends Move {

        public MajorMove(final Board board,
                         final Piece movedPiece,
                         final int destCoordX,
                         final int destCoordY) {
            super(board, movedPiece, destCoordX, destCoordY);
        }
    }

    public static class AttackMove extends Move {

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

    public static final class PawnMove extends Move {

        public PawnMove(final Board board,
                        final Piece movedPiece,
                        final int destCoordX,
                        final int destCoordY) {
            super(board, movedPiece, destCoordX, destCoordY);
        }
    }

    public static class PawnAttackMove extends AttackMove{

        public PawnAttackMove(final Board board,
                              final Piece movedPiece,
                              final int destCoordX,
                              final int destCoordY,
                              final Piece atackedPiece) {
            super(board, movedPiece, destCoordX, destCoordY, atackedPiece);
        }
    }

    public static final class PawnEnPassantAttackMove extends chess.board.Move.PawnAttackMove {

        public PawnEnPassantAttackMove(final Board board,
                                       final Piece movedPiece,
                                       final int destCoordX,
                                       final int destCoordY,
                                       final Piece atackedPiece) {
            super(board, movedPiece, destCoordX, destCoordY, atackedPiece);
        }
    }

    public static final class PawnJump extends Move {

        public PawnJump(final Board board,
                        final Piece movedPiece,
                        final int destCoordX,
                        final int destCoordY) {
            super(board, movedPiece, destCoordX, destCoordY);
        }
    }

    static abstract class CastleMove extends Move {

        public CastleMove(final Board board,
                          final Piece movedPiece,
                          final int destCoordX,
                          final int destCoordY) {
            super(board, movedPiece, destCoordX, destCoordY);
        }
    }

    public static final class KingSideCastleMove extends CastleMove {

        public KingSideCastleMove(final Board board,
                                  final Piece movedPiece,
                                  final int destCoordX,
                                  final int destCoordY) {
            super(board, movedPiece, destCoordX, destCoordY);
        }
    }

    public static final class QueenSideCastleMove extends CastleMove {

        public QueenSideCastleMove(final Board board,
                                   final Piece movedPiece,
                                   final int destCoordX,
                                   final int destCoordY) {
            super(board, movedPiece, destCoordX, destCoordY);
        }
    }

    public static final class NullMove extends Move {

        public NullMove() {
            super(null, null, -1, -1);
        }

        @Override
        public Board execute() {
            throw new RuntimeException("cannot execute the null move!");
        }
    }

    public static class MoveFactory {

        private MoveFactory() {
            throw new RuntimeException("Not instantiable");
        }

        public static Move createMove(final Board board,
                                      final int currentCoordinate,
                                      final int destinationCoordinate) {

            for (final Move move: board.getAllLegalMoves()) {
                if (move.getCurrentCoordinate() == currentCoordinate &&
                    move.getDestinationCoordinate() == destinationCoordinate) {
                    return move;
                }
            }
            return NULL_MOVE;
        }
    }
}
