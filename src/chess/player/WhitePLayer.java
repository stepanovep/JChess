package chess.player;

import chess.Alliance;
import chess.board.Board;
import chess.board.Cell;
import chess.board.Move;
import chess.board.Move.KingSideCastleMove;
import chess.pieces.Piece;
import chess.pieces.Rook;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals,
                                                    final Collection<Move> opponentsLegals) {

        final List<Move> kingCastles = new ArrayList<>();

        if (this.playerKing.isFirstMove() && !this.isInCheck()) {
            if (!this.board.getCell(7, 5).isCellOccupied() && !this.board.getCell(7, 6).isCellOccupied()) {
                final Cell rookCell = this.board.getCell(7, 7);

                if (rookCell.isCellOccupied() && rookCell.getPiece().isFirstMove()) {
                    if (Player.calculateAttacksOnTile(7*8+5, opponentsLegals).isEmpty() &&
                        Player.calculateAttacksOnTile(7*8+6, opponentsLegals).isEmpty() &&
                        rookCell.getPiece().getPieceType().isRook()) {

                        kingCastles.add(new KingSideCastleMove(this.board,
                                                               this.getPlayerKing(),
                                                               7, 6,
                                                               (Rook)rookCell.getPiece(),
                                                               rookCell.getCellCoordinate(),
                                                               61));
                    }
                }
            }
            if (!this.board.getCell(7, 1).isCellOccupied() && !this.board.getCell(7, 2).isCellOccupied() &&
                !this.board.getCell(7, 3).isCellOccupied()) {

                final Cell rookCell = this.board.getCell(7, 0);
                if (rookCell.isCellOccupied() && rookCell.getPiece().isFirstMove()) {
                    if (Player.calculateAttacksOnTile(7*8+1, opponentsLegals).isEmpty() &&
                        Player.calculateAttacksOnTile(7*8+2, opponentsLegals).isEmpty() &&
                        Player.calculateAttacksOnTile(7*8+3, opponentsLegals).isEmpty() &&
                        rookCell.getPiece().getPieceType().isRook()) {

                        kingCastles.add(new Move.QueenSideCastleMove(this.board,
                                                                     this.playerKing,
                                                                     7, 2,
                                                                     (Rook)rookCell.getPiece(),
                                                                     rookCell.getCellCoordinate(),
                                                                     59));
                    }
                }
            }
        }

        return kingCastles;
    }
}
