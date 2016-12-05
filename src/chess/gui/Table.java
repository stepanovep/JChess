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
    private final BoardPanel boardPanel;
    private Board chessBoard;

    private Cell sourceCell;
    private Cell destinationCell;
    private Piece humanMovedPiece;


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
        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);

        this.gameFrame.setVisible(true);

    }

    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());

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
            for (final CellPanel cellPanel: boardCells) {
                cellPanel.drawCell(board);
                add(cellPanel);
            }
            validate();
            repaint();
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
                                //TODO add the move that was made to the move log
                            }
                            sourceCell = null;
                            destinationCell = null;
                            humanMovedPiece = null;
                        }
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
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

        private void assignCellColor() {
            // the author's solution here is really crap,
            // I am gonna implement this on my own way
            final int cellPositionX = this.cellId / 8;
            final int cellPositionY = this.cellId % 8;
            setBackground((cellPositionX + cellPositionY) % 2 == 0 ? lightCellColor : darkCellColor);
        }

    }

}
