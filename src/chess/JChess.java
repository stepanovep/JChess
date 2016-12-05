package chess;

import chess.engine.board.Board;
import chess.gui.Table;

/**
 * Created by stepanovep on 12/4/16.
 */

public class JChess {
    public static void main(String[] args) {

        Board board = Board.createStandardBoard();

        System.out.println(board);

        Table table = new Table();

    }

}
