package chess.engine.board;

import chess.engine.pieces.Rook;
import chess.engine.pieces.Pawn;
import chess.engine.pieces.Piece;

import static chess.engine.board.Board.*;

/**
 * Created by stepanovep on 12/2/16.
 */

public abstract class Move {

    protected final Board board;
    protected final Piece movedPiece;
    protected final int destCoordX, destCoordY;
    protected final int destinationCoordinate;
    protected final boolean isFirstMove;

    public static final Move NULL_MOVE = new NullMove();

    private Move(final Board board,
                 final Piece movedPiece,
                 final int destCoordX, final int destCoordY) {

        this.board = board;
        this.movedPiece = movedPiece;
        this.destCoordX = destCoordX;
        this.destCoordY = destCoordY;
        this.destinationCoordinate = destCoordX*8 + destCoordY;
        this.isFirstMove = movedPiece.isFirstMove();
    }

    private Move(final Board board,
                 final int destCoordX, final int destCoordY) {

        this.board = board;
        this.destCoordX = destCoordX;
        this.destCoordY = destCoordY;
        this.destinationCoordinate = destCoordX*8 + destCoordY;
        this.movedPiece = null;
        this.isFirstMove = false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + this.destinationCoordinate;
        result = prime * result + this.movedPiece.hashCode();
        result = prime * result + this.movedPiece.getPiecePosition();
        result = result + (isFirstMove ? 1: 0);
        return result;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Move)) {
            return false;
        }
        final Move otherMove = (Move) other;
        return  getCurrentCoordinate() == otherMove.getCurrentCoordinate() &&
                getDestinationCoordinate() == otherMove.getDestinationCoordinate() &&
                getMovedPiece().equals(otherMove.getMovedPiece());
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

    public boolean isAttack() {
        return false;
    }

    public boolean isCastlingMove() {
        return false;
    }

    public Piece getAttackedPiece() {
        return null;
    }

    public Board execute() {
        final Builder builder = new Builder();
        for (final Piece piece: this.board.currentPlayer().getActivePieces()) {
            if (!this.movedPiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        for (final Piece piece: this.board.currentPlayer().getOpponent().getActivePieces()) {
            builder.setPiece(piece);
        }
        // move the moved piece!
        builder.setPiece(this.movedPiece.movePiece(this));
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

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof MajorMove && super.equals(other);
        }

        @Override
        public String toString() {
            return movedPiece.getPieceType().toString() + BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
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
        public int hashCode() {
            return this.attackedPiece.hashCode() + super.hashCode();
        }

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof AttackMove)) {
                return false;
            }
            final AttackMove otherAttackMove = (AttackMove) other;
            return super.equals(otherAttackMove) && getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
        }

        @Override
        public boolean isAttack() {
            return true;
        }

        @Override
        public Piece getAttackedPiece() {
            return this.attackedPiece;
        }

    }

    public static class MajorAttackMove extends AttackMove {
        final int destinationCoordinate;
        public MajorAttackMove(final Board board,
                               final Piece pieceMoved,
                               final int destCoordX, final int destCoordY,
                               final Piece pieceAttacked) {
            super(board, pieceMoved, destCoordX, destCoordY, pieceAttacked);
            destinationCoordinate = destCoordX*8 + destCoordY;
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof MajorAttackMove && super.equals(other);
        }

        @Override
        public String toString() {
            return movedPiece.getPieceType() + BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }
    }


    public static final class PawnMove extends Move {

        public PawnMove(final Board board,
                        final Piece movedPiece,
                        final int destCoordX,
                        final int destCoordY) {
            super(board, movedPiece, destCoordX, destCoordY);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof PawnMove && super.equals(other);
        }

        @Override
        public String toString() {
            return BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }
    }

    public static class PawnAttackMove extends AttackMove {

        public PawnAttackMove(final Board board,
                              final Piece movedPiece,
                              final int destCoordX,
                              final int destCoordY,
                              final Piece atackedPiece) {
            super(board, movedPiece, destCoordX, destCoordY, atackedPiece);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof PawnAttackMove && super.equals(other);
        }

        @Override
        public String toString() {
            return BoardUtils.getPositionAtCoordinate(this.movedPiece.getPiecePosition()).substring(0, 1) +
                    "x" + BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }
    }

    public static final class PawnEnPassantAttackMove extends Move.PawnAttackMove {

        public PawnEnPassantAttackMove(final Board board,
                                       final Piece movedPiece,
                                       final int destCoordX,
                                       final int destCoordY,
                                       final Piece atackedPiece) {
            super(board, movedPiece, destCoordX, destCoordY, atackedPiece);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof PawnEnPassantAttackMove && super.equals(other);
        }

        @Override
        public Board execute() {
            final Builder builder = new Builder();
            for (final Piece piece: this.board.currentPlayer().getActivePieces()) {
                if (!this.movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for (final Piece piece: this.board.currentPlayer().getOpponent().getActivePieces()) {
                if (!piece.equals(this.getAttackedPiece())) {
                    builder.setPiece(piece);
                }
            }
            builder.setPiece(this.movedPiece.movePiece(this));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());

            return builder.build();
        }
    }

    public static final class PawnJump extends Move {


        public PawnJump(final Board board,
                        final Piece movedPiece,
                        final int destCoordX,
                        final int destCoordY) {
            super(board, movedPiece, destCoordX, destCoordY);
        }

        @Override
        public Board execute() {
            final Builder builder = new Builder();
            for (final Piece piece: this.board.currentPlayer().getActivePieces()) {
                if (!this.movedPiece.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for (final Piece piece: this.board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }

            final Pawn movedPawn = (Pawn) this.movedPiece.movePiece(this);
            builder.setPiece(movedPawn);
            builder.setEnPassantPawn(movedPawn);
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());

            return builder.build();
        }

        @Override
        public String toString() {
            return BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
        }
    }

    static abstract class CastleMove extends Move {

        protected final Rook castleRook;
        protected final int castleRookStart;
        protected final int castleRookDestination;

        public CastleMove(final Board board,
                          final Piece movedPiece,
                          final int destCoordX,
                          final int destCoordY,
                          final Rook castleRook,
                          final int castleRookStart,
                          final int castleRookDestination) {

            super(board, movedPiece, destCoordX, destCoordY);
            this.castleRook = castleRook;
            this.castleRookStart = castleRookStart;
            this.castleRookDestination = castleRookDestination;
        }

        public Rook getCastleRook() {
            return this.castleRook;
        }

        @Override
        public boolean isCastlingMove() {
            return true;
        }

        @Override
        public Board execute() {

            final Builder builder = new Builder();
            for (final Piece piece: this.board.currentPlayer().getActivePieces()) {
                if (!this.movedPiece.equals(piece) && !this.castleRook.equals(piece)) {
                    builder.setPiece(piece);
                }
            }
            for (final Piece piece: this.board.currentPlayer().getOpponent().getActivePieces()) {
                builder.setPiece(piece);
            }
            builder.setPiece(this.movedPiece.movePiece(this));
            final int pieceDestinationX = this.castleRookDestination / 8;
            final int pieceDestinationY = this.castleRookDestination % 8;
            //TODO look into the first move on normal pieces
            builder.setPiece(new Rook(pieceDestinationX, pieceDestinationY, this.castleRook.getPieceAlliance()));
            builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());

            return builder.build();
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = super.hashCode();
            result = prime * result + this.castleRook.hashCode();
            result = prime * result + this.castleRookDestination;

            return result;
        }

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof CastleMove)) {
                return false;
            }
            final CastleMove otherCastleMove = (CastleMove) other;
            return super.equals(otherCastleMove) && this.castleRook.equals(otherCastleMove.getCastleRook());
        }

    }

    public static final class KingSideCastleMove extends CastleMove {

        public KingSideCastleMove(final Board board,
                                  final Piece movedPiece,
                                  final int destCoordX,
                                  final int destCoordY,
                                  final Rook castleRook,
                                  final int castleRookStart,
                                  final int castleRookDestination) {
            super(board, movedPiece, destCoordX, destCoordY, castleRook, castleRookStart, castleRookDestination);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof KingSideCastleMove && super.equals(other);
        }

        @Override
        public String toString() {
            return "0-0";
        }
    }

    public static final class QueenSideCastleMove extends CastleMove {

        public QueenSideCastleMove(final Board board,
                                   final Piece movedPiece,
                                   final int destCoordX,
                                   final int destCoordY,
                                   final Rook castleRook,
                                   final int castleRookStart,
                                   final int castleRookDestination) {
            super(board, movedPiece, destCoordX, destCoordY, castleRook, castleRookStart, castleRookDestination);
        }

        @Override
        public boolean equals(final Object other) {
            return this == other || other instanceof QueenSideCastleMove && super.equals(other);
        }

        @Override
        public String toString() {
            return "0-0-0";
        }
    }

    public static final class NullMove extends Move {

        public NullMove() {
            super(null, -1, -1);
            //super(null, 65); ??
        }

        @Override
        public Board execute() {
            throw new RuntimeException("cannot execute the null move!");
        }

        @Override
        public int getCurrentCoordinate() {
            return -1;
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
