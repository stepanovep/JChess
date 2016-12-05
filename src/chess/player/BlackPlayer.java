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

    @Override
    protected Collection<Move> calculateKingCastles(final Collection<Move> playerLegals,
                                                    final Collection<Move> opponentsLegals) {

        final List<Move> kingCastles = new ArrayList<>();

        if (this.playerKing.isFirstMove() && !this.isInCheck()) {
            if (!this.board.getCell(0, 5).isCellOccupied() && !this.board.getCell(0, 6).isCellOccupied()) {
                final Cell rookCell = this.board.getCell(0, 7);

                if (rookCell.isCellOccupied() && rookCell.getPiece().isFirstMove()) {
                    if (Player.calculateAttacksOnTile(0*8+5, opponentsLegals).isEmpty() &&
                        Player.calculateAttacksOnTile(0*8+6, opponentsLegals).isEmpty() &&
                        rookCell.getPiece().getPieceType().isRook()) {

                        kingCastles.add(new KingSideCastleMove(this.board,
                                                               this.getPlayerKing(),
                                                               0, 6,
                                                               (Rook)rookCell.getPiece(),
                                                               rookCell.getCellCoordinate(),
                                                               5));
                    }
                }
            }
            if (!this.board.getCell(0, 1).isCellOccupied() && !this.board.getCell(0, 2).isCellOccupied() &&
                    !this.board.getCell(0, 3).isCellOccupied()) {

                final Cell rookCell = this.board.getCell(0, 0);
                if (rookCell.isCellOccupied() && rookCell.getPiece().isFirstMove()) {
                    if (Player.calculateAttacksOnTile(0*8+1, opponentsLegals).isEmpty() &&
                        Player.calculateAttacksOnTile(0*8+2, opponentsLegals).isEmpty() &&
                        Player.calculateAttacksOnTile(0*8+3, opponentsLegals).isEmpty() &&
                        rookCell.getPiece().getPieceType().isRook()) {

                        kingCastles.add(new Move.QueenSideCastleMove(this.board,
                                                                     this.getPlayerKing(),
                                                                     0, 2,
                                                                     (Rook)rookCell.getPiece(),
                                                                     rookCell.getCellCoordinate(),
                                                                     3));
                    }
                }
            }
        }

        return kingCastles;
    }
}
