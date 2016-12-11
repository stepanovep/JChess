package chess.engine.pieces;

import chess.engine.Alliance;
import chess.engine.board.Board;
import chess.engine.board.BoardUtils;
import chess.engine.board.Cell;
import chess.engine.board.Move;

import java.util.*;

import static chess.engine.board.Move.*;

/**
 * Created by stepanovep on 12/2/16.
 */

public class Pawn extends Piece {

    public Pawn(final int piecePositionX, final int piecePositionY,
                final Alliance pieceAlliance) {

        super(PieceType.PAWN, piecePositionX, piecePositionY, pieceAlliance, true);
    }

    public Pawn(final int piecePostionX, final int piecePositionY,
                final Alliance pieceAllience,
                final boolean isFirstMove) {
        super(PieceType.PAWN, piecePostionX, piecePositionY, pieceAllience, isFirstMove);
    }

    private boolean isPawnInStartPosition() {
        final int startPositionX = this.getPieceAlliance().isWhite() ? 6 : 1;
        return piecePositionX == startPositionX;
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        final int dx = this.getPieceAlliance().getDirection();
        final int [] dy = {-1, 1};

        // move forward 1 step
        int toX = piecePositionX + dx;
        int toY = piecePositionY;

        if (BoardUtils.isValidCellCoordinate(toX, toY)) {
            Cell moveToCell = board.getCell(toX, toY);
            if (!moveToCell.isCellOccupied()) {
                legalMoves.add(new PawnMove(board, this, toX, toY));

                // move forward 2 steps
                toX = piecePositionX + 2 * dx;
                if (BoardUtils.isValidCellCoordinate(toX, toY)) {
                    moveToCell = board.getCell(toX, toY);
                    //TODO ideally there is has to be used isFirstMove() method
                    if (!moveToCell.isCellOccupied() && isPawnInStartPosition()) {
                        legalMoves.add(new PawnJump(board, this, toX, toY));
                    }
                }
            }

            // check attack options
            for (int i = 0; i < 2; i++) {
                toX = piecePositionX + dx;
                toY = piecePositionY + dy[i];
                if (BoardUtils.isValidCellCoordinate(toX, toY)) {
                    moveToCell = board.getCell(toX, toY);
                    if (moveToCell.isCellOccupied()) {
                        final Piece pieceAtDestination = moveToCell.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.pieceAlliance;
                        if (this.getPieceAlliance() != pieceAlliance) {
                            legalMoves.add(new PawnAttackMove(board, this, toX, toY, pieceAtDestination));
                        }
                    }
                }
            }
        }

        //TODO enPassantMove attackMove!!

        return legalMoves;
    }

    @Override
    public Pawn movePiece(final Move move) {
        return new Pawn(move.getDestCoordX(), move.getDestCoordY(), move.getMovedPiece().getPieceAlliance());
    }

    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }
}
