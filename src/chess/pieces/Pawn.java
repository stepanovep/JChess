package chess.pieces;

import chess.Alliance;
import chess.board.Board;
import chess.board.Cell;
import chess.board.Move;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by stepanovep on 12/2/16.
 */

public class Pawn extends Piece {

    public Pawn(int piecePositionX, int piecePositionY, Alliance pieceAlliance) {
        super(piecePositionX, piecePositionY, pieceAlliance);
    }

    private boolean isPawnInStartPosition() {
        final int startPositionX = this.getPieceAlliance() == Alliance.WHITE ? 1 : 6;
        return piecePositionX == startPositionX;
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final Set<Move> legalMoves = new LinkedHashSet<>();

        final int dy = this.getPieceAlliance() == Alliance.WHITE ? +1 : -1;
        final int [] dx = {-1, 1};

        // move forward 1 step
        int toX = piecePositionX;
        int toY = piecePositionX + dy;

        Cell moveToCell = board.getCell(toX, toY);
        if (!moveToCell.isCellOccupied()) {
            legalMoves.add(new Move.MajorMove(board, this, toX, toY));

            // move forward 2 step
            toY = piecePositionY + 2*dy;
            moveToCell = board.getCell(toX, toY);
            if (!moveToCell.isCellOccupied() && isPawnInStartPosition()) {
                legalMoves.add(new Move.MajorMove(board, this, toX, toY));
            }
        }

        // check attack options
        for (int i = 0; i < 2; i++) {
            toX = piecePositionX + dx[i];
            toY = piecePositionY + dy;

            moveToCell = board.getCell(toX, toY);
            final Piece pieceAtDestination = moveToCell.getPiece();
            final Alliance pieceAlliance = pieceAtDestination.pieceAlliance;
            if (this.getPieceAlliance() != pieceAlliance) {
                legalMoves.add(new Move.AttackMove(board, this, toX, toY, pieceAtDestination));
            }
        }

        return legalMoves;
    }
    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }
}
