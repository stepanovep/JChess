package chess.gui;

import chess.engine.board.Board;
import chess.engine.board.BoardUtils;
import chess.engine.board.Cell;
import chess.engine.board.Move;
import chess.engine.pieces.Piece;
import chess.engine.player.MoveTransition;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table {

    private final JFrame gameFrame;
    private final GameHistoryPanel gameHistoryPanel;
    private final TakenPiecesPanel takenPiecesPanel;

    private final MoveLog moveLog;
    private final BoardPanel boardPanel;
    private Board chessBoard;

    private Cell sourceCell;
    private Cell destinationCell;
    private Piece humanMovedPiece;
    private BoardDirection boardDirection;

    private boolean highlighLegalMoves;


    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private final static Dimension CELL_PANEL_DIMENSION = new Dimension(10, 10);

    private static String defaultPieceImagesPath = "art/fancy_whited/";

    private final Color lightCellColor = Color.decode("#ffce9e");
    private final Color darkCellColor = Color.decode("#d18b47");

    public Table() {
        this.gameFrame = new JFrame("JChess");
        this.gameFrame.setLayout(new BorderLayout());

        final JMenuBar tableMenuBar = createTableMenuBar();
        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
        this.chessBoard = Board.createStandardBoard();

        this.gameHistoryPanel = new GameHistoryPanel();
        this.takenPiecesPanel = new TakenPiecesPanel();
        this.moveLog = new MoveLog();

        this.boardPanel = new BoardPanel();
        this.boardDirection = BoardDirection.NORMAL;

        this.highlighLegalMoves = false;

        this.gameFrame.add(this.takenPiecesPanel, BorderLayout.WEST);
        this.gameFrame.add(this.gameHistoryPanel, BorderLayout.EAST);
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
        this.gameFrame.setVisible(true);

    }

    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        tableMenuBar.add(createPreferencesMenu());

        return tableMenuBar;
    }

    private JMenu createFileMenu() {
        final JMenu fileMenu = new JMenu("File");
        final JMenuItem openPGN = new JMenuItem("Load PGN File");
        openPGN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("open up that pgn file!");
            }
        });
        fileMenu.add(openPGN);

        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        fileMenu.add(exitMenuItem);

        return fileMenu;
    }

    private JMenu createPreferencesMenu() {

        final JMenu preferencesMenu = new JMenu("Preferences");
        final JMenuItem flipBoardMenuItem = new JMenuItem("Flip Board");
        flipBoardMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                boardDirection = boardDirection.opposite();
                boardPanel.drawBoard(chessBoard);
            }
        });
        preferencesMenu.add(flipBoardMenuItem);

        preferencesMenu.addSeparator();

        final JCheckBoxMenuItem legalMoveHighlighterCheckBox = new JCheckBoxMenuItem("Highlight Legal Moves", false);

        legalMoveHighlighterCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                highlighLegalMoves = legalMoveHighlighterCheckBox.isSelected();
            }
        });

        preferencesMenu.add(legalMoveHighlighterCheckBox);

        return preferencesMenu;
    }

    public enum BoardDirection {
        NORMAL {
            @Override
            BoardDirection opposite() {
                return FLIPPED;
            }

            @Override
            List<CellPanel> traverse(List<CellPanel> boardCells) {
                return boardCells;
            }
        },
        FLIPPED {
            @Override
            BoardDirection opposite() {
                return NORMAL;
            }

            @Override
            List<CellPanel> traverse(List<CellPanel> boardCells) {
                List<CellPanel> copiedBoardCells = new ArrayList<>(boardCells);
                Collections.reverse(copiedBoardCells);

                return copiedBoardCells;
            }
        };
        abstract BoardDirection opposite();
        abstract List<CellPanel> traverse(final List<CellPanel> boardCells);
    }

    private class BoardPanel extends JPanel {
        final List<CellPanel> boardCells;

        BoardPanel() {
            super(new GridLayout(8, 8));
            this.boardCells = new ArrayList<>();
            for (int i = 0;i < BoardUtils.NUM_CELLS; i++) {
                final CellPanel cellPanel = new CellPanel(this, i);
                this.boardCells.add(cellPanel);
                add(cellPanel);
            }
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }

        public void drawBoard(final Board board) {
            removeAll();
            for (final CellPanel cellPanel: boardDirection.traverse(boardCells)) {
                cellPanel.drawCell(board);
                add(cellPanel);
            }
            validate();
            repaint();
        }
    }

    public static class MoveLog {

        private final List<Move> moves;

        MoveLog() {
            this.moves = new ArrayList<>();
        }

        public List<Move> getMoves() {
            return this.moves;
        }

        public void addMove(final Move move) {
             this.moves.add(move);
        }

        public int size() {
            return this.moves.size();
        }

        public void clear() {
            this.moves.clear();
        }

        public Move removeMove(int idx) {
            return this.moves.remove(idx);
        }

        public boolean removeMove(final Move move) {
            return this.moves.remove(move);
        }
    }

    private class CellPanel extends JPanel {

        private final int cellId;

        CellPanel(final BoardPanel boardPanel, final int cellId) {
            super(new GridBagLayout());
            this.cellId = cellId;
            setPreferredSize(CELL_PANEL_DIMENSION);
            assignCellColor();
            assignCellPieceIcon(chessBoard);
            //TODO: can't move pieces, except the white Knight
            //TODO: probably wrong legalMoves() methods !!!
            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(final MouseEvent e) {

                    if (isRightMouseButton(e)) {
                        sourceCell = null;
                        destinationCell = null;
                        humanMovedPiece = null;
                    }
                    else if (isLeftMouseButton(e)) {
                        if(sourceCell == null) {
                            sourceCell = chessBoard.getCell(cellId);
                            humanMovedPiece = sourceCell.getPiece();
                            if (humanMovedPiece == null) {
                                sourceCell = null;
                            }
                        } else {
                            destinationCell = chessBoard.getCell(cellId);
                            final Move move = Move.MoveFactory.createMove(chessBoard, sourceCell.getCellCoordinate(), destinationCell.getCellCoordinate());
                            final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
                            if (transition.getMoveStatus().isDone()) {
                                chessBoard = transition.getTransitionBoard();
                                moveLog.addMove(move);
                                //TODO add the move that was made to the move log
                            }
                            sourceCell = null;
                            destinationCell = null;
                            humanMovedPiece = null;
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                gameHistoryPanel.redo(chessBoard, moveLog);
                                takenPiecesPanel.redo(moveLog);
                                boardPanel.drawBoard(chessBoard);
                            }
                        });
                    }
                }

                @Override
                public void mousePressed(final MouseEvent e) {

                }

                @Override
                public void mouseReleased(final MouseEvent e) {

                }

                @Override
                public void mouseEntered(final MouseEvent e) {

                }

                @Override
                public void mouseExited(final MouseEvent e) {

                }
            });

            validate();
        }

        public void drawCell (final Board board) {
            assignCellColor();
            assignCellPieceIcon(board);
            highligthLegals(board);
            validate();
            repaint();
        }

        private void assignCellPieceIcon (final Board board) {
            this.removeAll();
            if (board.getCell(this.cellId).isCellOccupied()) {
                try {
                    final BufferedImage image =
                            ImageIO.read(new File(defaultPieceImagesPath + board.getCell(this.cellId).getPiece().getPieceAlliance().toString().substring(0, 1) +
                                    board.getCell(this.cellId).getPiece().toString() + ".gif"));
                    add(new JLabel(new ImageIcon(image)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void highligthLegals(final Board board) {
            if (highlighLegalMoves) {
                for (final Move move: pieceLegalMoves(board)) {
                    if (move.getDestinationCoordinate() == this.cellId) {
                        try {
                            add(new JLabel(new ImageIcon(ImageIO.read(new File("art/misc/green_dot.png")))));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        private Collection<Move> pieceLegalMoves(final Board board) {
            if (humanMovedPiece != null && humanMovedPiece.getPieceAlliance() == board.currentPlayer().getAlliance()) {
                return humanMovedPiece.calculateLegalMoves(board);
            }
            return Collections.emptyList();
        }

        private void assignCellColor() {
            // the author's solution here is really crap,
            // I am gonna implement this on my own way
            final int cellPositionX = this.cellId / 8;
            final int cellPositionY = this.cellId % 8;
            setBackground((cellPositionX + cellPositionY) % 2 == 0 ? lightCellColor : darkCellColor);
        }
    }
}
