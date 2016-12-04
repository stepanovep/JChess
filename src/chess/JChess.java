package chess;

import chess.board.Board;

/**
 * Created by stepanovep on 12/4/16.
 */
public class JChess {
    public static void main(String[] args) {

        Board board = Board.createStandartBoard();

        System.out.println(board);
    }

}
