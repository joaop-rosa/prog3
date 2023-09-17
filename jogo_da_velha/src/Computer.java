import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import enums.PlayerType;
import enums.Symbol;

public class Computer extends Player {

    Computer(Symbol symbol) {
        super(PlayerType.COM, symbol);
    }

    private static int minimax(Game game, Player player) {
        Game simulatedGame = game.getClone();

        if (simulatedGame.hasWinner()) {
            return Game.getPlayScore(simulatedGame, player.getPlayerType());
        }
        final int[] betterValue = { -1 };

        Player simulatedPlayer = player.getPlayerType().equals(simulatedGame.getPlayer2().getPlayerType())
                ? simulatedGame.getPlayer1()
                : simulatedGame.getPlayer2();

        ArrayList<Integer> avaliablePositions = Game.getAvaliablePositions(simulatedGame);
        avaliablePositions.forEach((position) -> {
            int[] positionField = simulatedGame.getPositionFromValue(position);
            simulatedGame.getFields()[positionField[0]][positionField[1]].checkField(simulatedPlayer);
            int value = minimax(simulatedGame, simulatedPlayer);
            if (betterValue[0] == -1) {
                betterValue[0] = value;
            } else if (value > betterValue[0]) {
                betterValue[0] = value;
            }
        });

        return betterValue[0];
    }

    public int play(Game game) {
        Game simulatedGame = game.getClone();
        final int[] betterMove = { -1 };
        final int[] betterValue = { -1 };

        ArrayList<Integer> avaliablePositions = Game.getAvaliablePositions(simulatedGame);

        avaliablePositions.forEach((position) -> {
            int[] positionField = simulatedGame.getPositionFromValue(position);
            simulatedGame.getFields()[positionField[0]][positionField[1]].checkField(this);
            int value = minimax(simulatedGame, this);
            if (betterValue[0] == -1) {
                betterValue[0] = value;
                betterMove[0] = position;

            } else if (value > betterValue[0]) {
                betterValue[0] = value;
                betterMove[0] = position;
            }
        });

        return betterMove[0];
    }

}
