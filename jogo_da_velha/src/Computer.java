import java.util.ArrayList;

import enums.PlayerType;
import enums.Symbol;

public class Computer extends Player {

    Computer(Symbol symbol) {
        super(PlayerType.COM, symbol);
    }

    public static ArrayList<Integer> getAvaliablePositions(Game game) {
        ArrayList<Integer> positions = new ArrayList<>();
        for (int line = 0; line < 3; line++) {
            for (int column = 0; column < 3; column++) {
                if (!game.getFields()[line][column].isChecked()) {
                    positions.add(game.getFields()[line][column].getPosition());
                }
            }
        }

        return positions;
    }

    private static int minimax(Game game, Player player) {
        if (game.hasWinner() || game.isDraw()) {
            if (game.isDraw()) {
                return 0;
            } else if (game.getPlayerWinner().getPlayerType().equals(PlayerType.COM)) {
                return 1;
            } else {
                return -1;
            }
        }

        Player simulatedPlayer = player.equals(game.getPlayer2())
                ? game.getPlayer1()
                : game.getPlayer2();

        int[] betterValue = { player.getPlayerType().equals(PlayerType.COM) ? Integer.MIN_VALUE : Integer.MAX_VALUE };

        ArrayList<Integer> avaliablePositions = getAvaliablePositions(game);
        avaliablePositions.forEach((position) -> {
            int[] positionField = game.getPositionFromValue(position);
            Field.checkField(simulatedPlayer, game.getFields()[positionField[0]][positionField[1]]);
            int value = minimax(game, simulatedPlayer);
            Field.removeCheckField(game.getFields()[positionField[0]][positionField[1]]);

            if (betterValue[0] == Integer.MIN_VALUE || betterValue[0] == Integer.MAX_VALUE) {
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

    public static int play(Game game) {
        int[] betterMove = { Integer.MIN_VALUE };
        int[] betterValue = { Integer.MIN_VALUE };

        ArrayList<Integer> avaliablePositions = getAvaliablePositions(game);

        avaliablePositions.forEach((position) -> {
            int[] positionField = game.getPositionFromValue(position);
            Field.checkField(game.getPlayer2(), game.getFields()[positionField[0]][positionField[1]]);
            int value = minimax(game, game.getPlayer2());
            Field.removeCheckField(game.getFields()[positionField[0]][positionField[1]]);
            if (betterValue[0] == Integer.MIN_VALUE) {
                betterValue[0] = value;
                betterMove[0] = position;
            } else if (value > betterValue[0]) {
                betterValue[0] = value;
                betterMove[0] = position;
            }
            // System.out.println("position:" + position);
            // System.out.println("value: " + value);
        });

        return betterMove[0];
    }

}
