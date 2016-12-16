package chess.engine;

import chess.engine.board.BoardUtils;
import chess.engine.player.BlackPlayer;
import chess.engine.player.Player;
import chess.engine.player.WhitePLayer;

public enum Alliance {
    WHITE {
        @Override
        public int getDirection() {
            return -1;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public boolean isPawnPromotionSquare(int positionX, int positionY) {
            return positionX == 0;
        }

        @Override
        public Player choosePlayer(final WhitePLayer whitePlayer,
                                   final BlackPlayer blackPlayer) {
            return whitePlayer;
        }
    },
    BLACK {
        @Override
        public int getDirection() {
            return 1;
        }

        @Override
        public boolean isBlack() {
            return true;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public boolean isPawnPromotionSquare(int positionX, int positionY) {
            return positionX == 7;
        }

        @Override
        public Player choosePlayer(final WhitePLayer whitePlayer,
                                   final BlackPlayer blackPlayer) {
            return blackPlayer;
        }
    };

    public abstract int getDirection();
    public int getOppositeDirection() {
        return -1 * getDirection();
    }
    public abstract boolean isBlack();
    public abstract boolean isWhite();

    public abstract boolean isPawnPromotionSquare(int positionX, int positionY);

    public abstract Player choosePlayer(WhitePLayer whitePlayer, BlackPlayer blackPlayer);
}
