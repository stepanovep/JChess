package chess.engine.pieces;

import chess.engine.Alliance;
import chess.engine.board.Board;
import chess.engine.board.Cell;
import chess.engine.board.Move;

import java.util.*;

/**
 * Created by stepanovep on 12/2/16.
 */
public class Rook extends Piece {

    public Rook(final int piecePositionX, final int piecePositionY,
                final Alliance pieceAlliance) {

        super(PieceType.ROOK, piecePositionX, piecePositionY, pieceAlliance, true);
    }

    public Rook(final int piecePostionX, final int piecePositionY,
                final Alliance pieceAllience,
                final boolean isFirstMove) {
        super(PieceType.ROOK, piecePostionX, piecePositionY, pieceAllience, isFirstMove);
    }

    private boolean canMoveTo (int toX, int toY) {
        return piecePositionX == toX || piecePositionY == toY;
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        // horizontal shifting
        int [] dx = {-1, 1};
        for (int i = 0; i < 2; i++) {
            for (int k = 1; k < 8; ++k) {
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
            for (int k = 1; k < 8; ++k) {
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
    public Rook movedPiece(final Move move) {
        return new Rook(move.getDestCoordX(), move.getDestCoordY(), move.getMovedPiece().getPieceAlliance());
    }

    @Override
    public String toString() {
        return PieceType.ROOK.toString();
    }
}
