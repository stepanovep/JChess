package chess.engine.pieces;

import chess.engine.Alliance;
import chess.engine.board.Board;
import chess.engine.board.Cell;
import chess.engine.board.Move;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by stepanovep on 12/2/16.
 */

public class King extends Piece {

    public King(final int piecePositionX, final int piecePositionY,
                final Alliance pieceAlliance) {

        super(PieceType.KING, piecePositionX, piecePositionY, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final Set<Move> legalMoves = new LinkedHashSet<>();

        int [] dx = {-1, 0, 1};
        int [] dy = {-1, 0, 1};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int toX = piecePositionX + dx[i];
                final int toY = piecePositionY + dy[j];
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
                    }
                }
            }
        }

        return legalMoves;
    }
    @Override
    public King movedPiece(final Move move) {
        return new King(move.getDestCoordX(), move.getDestCoordY(), move.getMovedPiece().getPieceAlliance());
    }

    @Override
    public String toString() {
        return PieceType.KING.toString();
    }
}