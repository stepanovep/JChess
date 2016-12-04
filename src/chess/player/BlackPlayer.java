package chess.player;

import chess.Alliance;
import chess.board.Board;
import chess.board.Move;
import chess.pieces.Piece;

import java.util.Collection;

/**
 * Created by stepanovep on 12/4/16.
 */
public class BlackPlayer extends Player {
    public BlackPlayer(final Board board,
                       final Collection<Move> whiteStandartLegalMoves,
                       final Collection<Move> blackStandartLegalMoves) {

        super(board, blackStandartLegalMoves, whiteStandartLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getBlackPieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.BLACK;
    }

    @Override
    public Player getOpponent() {
        return this.board.whitePlayer();
    }
}
