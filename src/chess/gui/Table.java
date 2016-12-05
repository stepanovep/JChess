package chess.gui;

import chess.engine.board.Board;
import chess.engine.board.BoardUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Table {

    private final JFrame gameFrame;
    private final BoardPanel boardPanel;
    private final Board chessBoard;

    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600, 600);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400, 350);
    private final static Dimension CELL_PANEL_DIMENSION = new Dimension(10, 10);

    private static String defaultPieceImagesPath = "art/fancy/";

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
    }

    private class CellPanel extends JPanel {

        private final int cellId;

        CellPanel(final BoardPanel boardPanel, final int cellId) {
            super(new GridBagLayout());
            this.cellId = cellId;
            setPreferredSize(CELL_PANEL_DIMENSION);
            assignCellColor();
            assignCellPieceIcon(chessBoard);
            validate();
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
