package chess.player;

import chess.Alliance;
import chess.board.Board;
import chess.board.Move;
import chess.pieces.Piece;

import java.util.Collection;

/**
 * Created by stepanovep on 12/4/16.
 */
public class WhitePLayer extends Player {
    public WhitePLayer(final Board board,
                       final Collection<Move> whiteStandartLegalMoves,
                       final Collection<Move> blackStandartLegalMoves) {

        super(board, whiteStandartLegalMoves, blackStandartLegalMoves);
    }

    @Override
    public Collection<Piece> getActivePieces() {
        return this.board.getWhitePieces();
    }

    @Override
    public Alliance getAlliance() {
        return Alliance.WHITE;
    }

    @Override
    public Player getOpponent() {
        return this.board.blackPlayer();
    }
}
