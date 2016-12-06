package chess.engine.pieces;

import chess.engine.Alliance;
import chess.engine.board.Board;
import chess.engine.board.Cell;
import chess.engine.board.Move;

import java.util.*;

/**
 * Created by stepanovep on 12/2/16.
 */

public class Bishop extends Piece {

    public Bishop(final int piecePositionX, final int piecePositionY,
                  final Alliance pieceAlliance) {

        super(PieceType.BISHOP, piecePositionX, piecePositionY, pieceAlliance);
    }

    private boolean canMoveTo(int toX, int toY) {
        return (piecePositionX - piecePositionY == toX - toY
                || piecePositionX + piecePositionX == toX + toY);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        int [] dx = {-1, 1};
        int [] dy = {-1, 1};
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                iteration:
                for (int k = 1; k < 8; k++) {
                    final int toX = piecePositionX + k*dx[i];
                    final int toY = piecePositionY + k*dy[j];
                    if (Cell.legalPosition(toX, toY)) {
                        final Cell candidateDestinationCell = board.getCell(toX, toY);
                        if (!candidateDestinationCell.isCellOccupied()) {
                            legalMoves.add(new Move.MajorMove(board, this, toX, toY));
                        } else {
                            final Piece pieceAtDestination = candidateDestinationCell.getPiece();
                            final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                            if (this.pieceAlliance != pieceAlliance) {
                                legalMoves.add(new Move.AttackMove(board, this, toX, toY, pieceAtDestination));
                            }
                            break iteration;
                        }
                    } else {
                        break;
                    }
                }
            }
        }

        return legalMoves;
    }

    @Override
    public Bishop movedPiece(final Move move) {
        return new Bishop(move.getDestCoordX(), move.getDestCoordY(), move.getMovedPiece().getPieceAlliance());
    }

    @Override
    public String toString() {
        return PieceType.BISHOP.toString();
    }
}
