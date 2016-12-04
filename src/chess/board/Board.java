package chess.board;

import chess.Alliance;
import chess.pieces.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by stepanovep on 12/2/16.
 */

public class Board {

    private final Cell[][] gameBoard;
    private final Collection<Piece> whitePieces;
    private final Collection<Piece> blackPieces;

    public Board(Builder builder) {
        this.gameBoard = createGameBoard(builder);
        this.whitePieces = calculateActivePieces(this.gameBoard, Alliance.WHITE);
        this.blackPieces = calculateActivePieces(this.gameBoard, Alliance.BLACK);

        final Collection<Move> whiteStandartLegalMoves = calculateLegamMoves(this.whitePieces);
        final Collection<Move> blackStandartLegalMoves = calculateLegamMoves(this.blackPieces);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < BoardUtils.NUM_CELLS_PER_ROW; i++) {
            for (int j = 0; j < BoardUtils.NUM_CELLS_PER_ROW; j++) {
                final String cellText = gameBoard[i][j].toString();
                builder.append(String.format("%3s", cellText));
            }
            builder.append("\n");
        }

        return builder.toString();
    }

    private static String prettyPrint(Cell cell) {
        return cell.toString();
    }

    private Collection<Move> calculateLegamMoves(final Collection<Piece> pieces) {

        final List<Move> legalMoves = new ArrayList<>();
        for (final Piece piece: pieces) {
            legalMoves.addAll(piece.calculateLegalMoves(this));
        }

        return legalMoves;
    }

    private static Collection<Piece> calculateActivePieces(final Cell[][] gameBoard,
                                                           final Alliance alliance) {

        final List<Piece> activePieces = new ArrayList<>();
        final int N = BoardUtils.NUM_CELLS_PER_ROW;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                final Cell cell = gameBoard[i][j];
                if (cell.isCellOccupied()) {
                    final Piece piece = cell.getPiece();
                    if(piece.getPieceAlliance() == alliance) {
                        activePieces.add(piece);
                    }
                }
            }
        }

        return activePieces;
    }

    public Cell getCell(final int x, final int y) {
        return gameBoard[x][y];
    }

    public static Cell[][] createGameBoard(final Builder builder) {
        final int N = BoardUtils.NUM_CELLS_PER_ROW;
        final Cell[][] cells = new Cell[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                cells[i][j] = Cell.createCell(i, j, builder.boardConfig.get(i*8+j));
            }
        }
        return cells;
    }

    public static Board createStandartBoard() {
        final Builder builder = new Builder();

        //Black Layout
        builder.setPiece(new Rook   (7, 0, Alliance.BLACK));
        builder.setPiece(new Knight (7, 1, Alliance.BLACK));
        builder.setPiece(new Bishop (7, 2, Alliance.BLACK));
        builder.setPiece(new Queen  (7, 3, Alliance.BLACK));
        builder.setPiece(new King   (7, 4, Alliance.BLACK));
        builder.setPiece(new Bishop (7, 5, Alliance.BLACK));
        builder.setPiece(new King   (7, 6, Alliance.BLACK));
        builder.setPiece(new Rook   (7, 7, Alliance.BLACK));

        for (int i = 0; i < BoardUtils.NUM_CELLS_PER_ROW; i++) {
            builder.setPiece(new Pawn(6, i, Alliance.BLACK));
        }

        //White Layout
        builder.setPiece(new Rook   (0, 0, Alliance.WHITE));
        builder.setPiece(new Knight (0, 1, Alliance.WHITE));
        builder.setPiece(new Bishop (0, 2, Alliance.WHITE));
        builder.setPiece(new Queen  (0, 3, Alliance.WHITE));
        builder.setPiece(new King   (0, 4, Alliance.WHITE));
        builder.setPiece(new Bishop (0, 5, Alliance.WHITE));
        builder.setPiece(new Knight (0, 6, Alliance.WHITE));
        builder.setPiece(new Rook   (0, 7, Alliance.WHITE));

        for (int i = 0; i < BoardUtils.NUM_CELLS_PER_ROW; i++) {
            builder.setPiece(new Pawn(1, i, Alliance.WHITE));
        }

        return builder.build();
    }


    public static class Builder {

        Map<Integer, Piece> boardConfig;
        Alliance nextMoveMaker;

        public Builder() {

        }

        public Builder setPiece(final Piece piece) {
            this.boardConfig.put(piece.getPiecePosition(), piece);
            return this;
        }

        public Builder setMoveMaker(final Alliance alliance) {
            this.nextMoveMaker = nextMoveMaker;
            return this;
        }

        public Board build() {
            return new Board(this);
        }
    }
}
