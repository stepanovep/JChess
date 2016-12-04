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
public class Rook extends Piece {

    public Rook(final int piecePositionX, final int piecePositionY,
                final Alliance pieceAlliance) {

        super(PieceType.ROOK, piecePositionX, piecePositionY, pieceAlliance);
    }

    private boolean canMoveTo (int toX, int toY) {
        return piecePositionX == toX || piecePositionY == toY;
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final Set<Move> legalMoves = new LinkedHashSet<>();


        // horizontal shifting
        int [] dx = {-1, 1};
        for (int i = 0; i < 2; i++) {
            for (int k = 0; k < 8; ++k) {
                final int toX = piecePositionX + k*dx[i];
                final int toY = piecePositionY;
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
                        break;
                    }
                } else {
                    break;
                }
            }
        }


        // vertical shifting
        int [] dy = {-1, 1};
        for (int i = 0; i < 2; i++) {
            for (int k = 0; k < 8; ++k) {
                final int toX = piecePositionX;
                final int toY = piecePositionY + k*dy[i];
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
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        return legalMoves;
    }

    @Override
    public String toString() {
        return PieceType.ROOK.toString();
    }
}
