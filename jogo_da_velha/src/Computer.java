import java.util.ArrayList;

import enums.PlayerType;
import enums.Symbol;

public class Computer extends Player {

    Computer(Symbol symbol) {
        super(PlayerType.COM, symbol);
    }

    private static int minimax(Game game, Player player) {
        Game simulatedGame = new Game(game);

        if (simulatedGame.hasWinner() || simulatedGame.isDraw()) {
            return Game.getPlayScore(simulatedGame, player.getPlayerType());
        }
        final int[] betterValue = { -2 };

        Player simulatedPlayer = player.getPlayerType().equals(simulatedGame.getPlayer2().getPlayerType())
                ? simulatedGame.getPlayer1()
                : simulatedGame.getPlayer2();

        ArrayList<Integer> avaliablePositions = Game.getAvaliablePositions(simulatedGame);
        avaliablePositions.forEach((position) -> {
            int[] positionField = simulatedGame.getPositionFromValue(position);
            Field.checkField(simulatedPlayer, simulatedGame.getFields()[positionField[0]][positionField[1]]);
            int value = 0;
            value = minimax(simulatedGame, simulatedPlayer);
            Field.removeCheckField(simulatedGame.getFields()[positionField[0]][positionField[1]]);
            if (betterValue[0] == -2) {
                betterValue[0] = value;
            } else if (simulatedPlayer.getPlayerType().equals(PlayerType.COM)) {
                if (value > betterValue[0]) {
                    betterValue[0] = value;
                }
            } else if (simulatedPlayer.getPlayerType().equals(PlayerType.P1)) {
                if (value < betterValue[0]) {
                    betterValue[0] = value;
                }
            }

        });

        return betterValue[0];
    }

    public int play(Game game) {
        Game simulatedGame = new Game(game);
        final int[] betterMove = { -1 };
        final int[] betterValue = { -2 };

        ArrayList<Integer> avaliablePositions = Game.getAvaliablePositions(simulatedGame);

        avaliablePositions.forEach((position) -> {
            int[] positionField = simulatedGame.getPositionFromValue(position);
            Field.checkField(this, simulatedGame.getFields()[positionField[0]][positionField[1]]);
            int value = 0;
            value = minimax(simulatedGame, this);
            Field.removeCheckField(simulatedGame.getFields()[positionField[0]][positionField[1]]);
            if (betterValue[0] == -2) {
                betterValue[0] = value;
                betterMove[0] = position;

            }
            if (value > betterValue[0]) {
                betterValue[0] = value;
                betterMove[0] = position;
            }
        });

        return betterMove[0];
    }

}
