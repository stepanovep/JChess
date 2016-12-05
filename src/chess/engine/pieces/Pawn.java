package chess.engine.pieces;

import chess.engine.Alliance;
import chess.engine.board.Board;
import chess.engine.board.BoardUtils;
import chess.engine.board.Cell;
import chess.engine.board.Move;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by stepanovep on 12/2/16.
 */

public class Pawn extends Piece {

    public Pawn(final int piecePositionX, final int piecePositionY,
                final Alliance pieceAlliance) {

        super(PieceType.PAWN, piecePositionX, piecePositionY, pieceAlliance);
    }

    private boolean isPawnInStartPosition() {
        final int startPositionX = this.getPieceAlliance().isWhite() ? 1 : 6;
        return piecePositionX == startPositionX;
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final Set<Move> legalMoves = new LinkedHashSet<>();

        final int dy = this.getPieceAlliance().getDirection();
        final int [] dx = {-1, 1};

        // move forward 1 step
        int toX = piecePositionX;
        int toY = piecePositionX + dy;

        if (BoardUtils.isValidCellCoordinate(toX, toY)) {
            Cell moveToCell = board.getCell(toX, toY);
            if (!moveToCell.isCellOccupied()) {
                legalMoves.add(new Move.MajorMove(board, this, toX, toY));

                // move forward 2 step
                toY = piecePositionY + 2 * dy;
                if (BoardUtils.isValidCellCoordinate(toX, toY)) {
                    moveToCell = board.getCell(toX, toY);
                    if (!moveToCell.isCellOccupied() && isPawnInStartPosition()) {
                        legalMoves.add(new Move.MajorMove(board, this, toX, toY));
                    }
                }
            }

            // check attack options
            for (int i = 0; i < 2; i++) {
                toX = piecePositionX + dx[i];
                toY = piecePositionY + dy;
                if (BoardUtils.isValidCellCoordinate(toX, toY)) {
                    moveToCell = board.getCell(toX, toY);
                    if (moveToCell.isCellOccupied()) {
                        final Piece pieceAtDestination = moveToCell.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.pieceAlliance;
                        if (this.getPieceAlliance() != pieceAlliance) {
                            legalMoves.add(new Move.AttackMove(board, this, toX, toY, pieceAtDestination));
                        }
                    }
                }
            }
        }

        return legalMoves;
    }

    @Override
    public Pawn movedPiece(final Move move) {
        return new Pawn(move.getDestCoordX(), move.getDestCoordY(), move.getMovedPiece().getPieceAlliance());
    }

    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }
}
