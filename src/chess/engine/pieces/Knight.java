package chess.engine.pieces;

import chess.engine.Alliance;
import chess.engine.board.Board;
import chess.engine.board.Cell;
import chess.engine.board.Move;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by captain_nemo on 12/2/16.
 */
public class Knight extends Piece {

    public Knight(final int piecePositionX, final int piecePositionY,
                  final Alliance pieceAlliance) {

        super(PieceType.KNIGHT, piecePositionX, piecePositionY, pieceAlliance);
    }

    public boolean canMoveTo(int x, int y) {
        return Math.abs( (piecePositionX-x) * (piecePositionY-y) ) == 2;
    }

    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {

        final Set<Move> legalMoves = new LinkedHashSet<>();

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (canMoveTo(x, y)) {
                    final Cell candidateDestinationCell = board.getCell(x, y);
                    if (!candidateDestinationCell.isCellOccupied()) {
                        legalMoves.add(new Move.MajorMove(board, this, x, y));
                    } else {
                        final Piece pieceAtDestination = candidateDestinationCell.getPiece();
                        final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

                        if (this.pieceAlliance != pieceAlliance) {
                            legalMoves.add(new Move.AttackMove(board, this, x, y, pieceAtDestination));
                        }
                    }
                }
            }
        }

        return legalMoves;
    }
    @Override
    public Knight movedPiece(final Move move) {
        return new Knight(move.getDestCoordX(), move.getDestCoordY(), move.getMovedPiece().getPieceAlliance());
    }

    @Override
    public String toString() {
        return PieceType.KNIGHT.toString();
    }
}
