package chess.gui;

import chess.engine.board.Move;
import chess.engine.pieces.Piece;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static chess.gui.Table.*;

/**
 * Created by stepanovep on 12/10/16.
 */

public class TakenPiecesPanel extends JPanel {

    private final JPanel northPanel;
    private final JPanel southPanel;

    private final static Color PANEL_COLOR = Color.decode("0xFDFE6");
    private static final Dimension TAKEN_PIECES_DIMENSION = new Dimension(40, 80);

    private static final EtchedBorder PANEL_BORDER = new EtchedBorder(EtchedBorder.RAISED);

    public TakenPiecesPanel() {
        super(new BorderLayout());
        setBackground(PANEL_COLOR);
        setBorder(PANEL_BORDER);
        this.northPanel = new JPanel(new GridLayout(8, 2));
        this.southPanel = new JPanel(new GridLayout(8, 2));

        this.northPanel.setBackground(PANEL_COLOR);
        this.southPanel.setBackground(PANEL_COLOR);

        add(this.northPanel, BorderLayout.NORTH);
        add(this.southPanel, BorderLayout.SOUTH);

        setPreferredSize(TAKEN_PIECES_DIMENSION);
    }

    public void redo(final MoveLog moveLog) {
        southPanel.removeAll();
        northPanel.removeAll();

        final List<Piece> whiteTakenPieces = new ArrayList<>();
        final List<Piece> blackTakenPieces = new ArrayList<>();

        for (final Move move: moveLog.getMoves()) {
            if (move.isAttack()) {
                final Piece takenPiece = move.getAttackedPiece();
                if (takenPiece.getPieceAlliance().isWhite()) {
                    whiteTakenPieces.add(takenPiece);
                } else {
                    blackTakenPieces.add(takenPiece);
                }
            }
        }

        Collections.sort(whiteTakenPieces, new Comparator<Piece>() {
            @Override
            public int compare(Piece o1, Piece o2) {
                return Integer.compare(o1.getPieceValue(), o2.getPieceValue());
            }
        });

        Collections.sort(blackTakenPieces, new Comparator<Piece>() {
            @Override
            public int compare(Piece o1, Piece o2) {
                return Integer.compare(o1.getPieceValue(), o2.getPieceValue());
            }
        });

        for (final Piece takenPiece: whiteTakenPieces) {
            try {
                final String iconPath = "art/fancy_whited/" + takenPiece.getPieceAlliance().toString().substring(0,1) + takenPiece.toString() + ".gif";
                final BufferedImage image = ImageIO.read(new File(iconPath));
                final ImageIcon icon = new ImageIcon(image);
                final JLabel imageLabel = new JLabel();
                this.southPanel.add(imageLabel);
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }

        for (final Piece takenPiece: blackTakenPieces) {
            try {
                final String iconPath = "art/fancy_whited/" + takenPiece.getPieceAlliance().toString().substring(0,1) + takenPiece.toString() + ".gif";
                final BufferedImage image = ImageIO.read(new File(iconPath));
                final ImageIcon icon = new ImageIcon(image);
                final JLabel imageLabel = new JLabel();
                this.northPanel.add(imageLabel);
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }

        validate();
    }
}
