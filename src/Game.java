import java.util.Random;
import java.util.Scanner;

import enums.PlayerType;

public class Game {
    private Player player1;
    private Player player2;
    private Field[][] fields;
    private Player playerTurn;
    private Player playerWinner;
    static private int turn = 0;

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Field[][] getFields() {
        return fields;
    }

    public Player getPlayerTurn() {
        return playerTurn;
    }

    public Player getPlayerWinner() {
        return playerWinner;
    }

    public int getTurn() {
        return turn;
    }

    private int calcPosition(int line, int column) {
        return (line * 3) + column + 1;
    }

    private void fillFields() {
        for (int line = 0; line < 3; line++) {
            for (int column = 0; column < 3; column++) {
                this.fields[line][column] = new Field(calcPosition(line, column));
            }
        }
    }

    public int[] getPositionFromValue(int value) {
        int[] position = new int[2];
        position[0] = (value - 1) / 3;
        position[1] = (value - 1) % 3;
        return position;
    }

    Game(Player player1) {
        this.player1 = player1;
        this.player2 = new Computer(player1.getReverseSymbol());
        this.fields = new Field[3][3];
        fillFields();
    }

    Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.fields = new Field[3][3];
        fillFields();
    }

    public void sortFirstPlayer() {
        Random random = new Random();
        int result = random.nextInt(2);
        this.playerTurn = (result == 0) ? player1 : player2;
    }

    private void print() {
        for (int line = 0; line < 3; line++) {
            System.out.print("  ");
            for (int column = 0; column < 3; column++) {
                if (fields[line][column].isChecked()) {
                    System.out.print(fields[line][column].getCheckedPlayer().getSymbol() + "  ");
                } else {
                    System.out.print(fields[line][column].getPosition() + "  ");
                }
            }
            System.out.println();
        }
    }

    private boolean hasLineWinner() {
        int player1Fields = 0;
        int player2Fields = 0;
        for (int line = 0; line < fields.length; line++) {
            for (int column = 0; column < 3; column++) {
                if (fields[line][column].isChecked()) {
                    if (fields[line][column].getCheckedPlayer().equals(this.player1)) {
                        player1Fields++;
                    } else if (fields[line][column].getCheckedPlayer().equals(this.player2)) {
                        player2Fields++;
                    }
                }
            }
            if (player1Fields == 3) {
                playerWinner = this.player1;
                return true;
            } else if (player2Fields == 3) {
                playerWinner = this.player2;
                return true;
            }
            player1Fields = 0;
            player2Fields = 0;
        }

        return false;
    }

    private boolean hasColumnWinner() {
        int player1Fields = 0;
        int player2Fields = 0;
        for (int column = 0; column < fields.length; column++) {
            for (int line = 0; line < fields.length; line++) {
                if (fields[line][column].isChecked()) {
                    if (fields[line][column].getCheckedPlayer().equals(this.player1)) {
                        player1Fields++;
                    } else if (fields[line][column].getCheckedPlayer().equals(this.player2)) {
                        player2Fields++;
                    }
                }
            }
            if (player1Fields == 3) {
                playerWinner = this.player1;
                return true;
            } else if (player2Fields == 3) {
                playerWinner = this.player2;
                return true;
            }
            player1Fields = 0;
            player2Fields = 0;
        }

        return false;
    }

    private boolean hasDiagonal1Winner() {
        int player1Fields = 0;
        int player2Fields = 0;
        for (int index = 0; index < 3; index++) {
            if (fields[index][index].isChecked()) {
                if (fields[index][index].getCheckedPlayer().equals(player1)) {
                    player1Fields++;
                } else if (fields[index][index].getCheckedPlayer().equals(this.player2)) {
                    player2Fields++;
                }
            }
        }

        if (player1Fields == 3) {
            playerWinner = this.player1;
            return true;
        } else if (player2Fields == 3) {
            playerWinner = this.player2;
            return true;
        }
        return false;
    }

    private boolean hasDiagonal2Winner() {
        int player1Fields = 0;
        int player2Fields = 0;

        for (int index = 0; index < 3; index++) {
            int diagonal2Calc = 3 - 1 - index;
            if (fields[index][diagonal2Calc].isChecked()) {
                if (fields[index][diagonal2Calc].getCheckedPlayer().equals(player1)) {
                    player1Fields++;
                } else if (fields[index][diagonal2Calc].getCheckedPlayer().equals(player2)) {
                    player2Fields++;
                }
            }
        }

        if (player1Fields == 3) {
            playerWinner = this.player1;
            return true;
        } else if (player2Fields == 3) {
            playerWinner = this.player2;
            return true;
        }

        return false;
    }

    private boolean isLinesDraw() {
        int linesDraw = 0;
        int player1Fields = 0;
        int player2Fields = 0;
        for (int line = 0; line < fields.length; line++) {
            for (int column = 0; column < 3; column++) {
                if (fields[line][column].isChecked()) {
                    if (!fields[line][column].getCheckedPlayer().equals(player1)) {
                        player1Fields++;
                    } else {
                        player2Fields++;
                    }
                }
            }
            if (player1Fields >= 1 && player2Fields >= 1) {
                linesDraw++;
            }
            player1Fields = 0;
            player2Fields = 0;
        }

        return (linesDraw == 3);
    }

    private boolean isColumnsDraw() {
        int columnDraw = 0;
        var player1Fields = 0;
        int player2Fields = 0;
        for (int column = 0; column < fields.length; column++) {
            for (int line = 0; line < fields.length; line++) {
                if (fields[line][column].isChecked()) {
                    if (!fields[line][column].getCheckedPlayer().equals(player1)) {
                        player1Fields++;
                    } else {
                        player2Fields++;
                    }
                }
            }
            if (player1Fields >= 1 && player2Fields >= 1) {
                columnDraw++;
            }
            player1Fields = 0;
            player2Fields = 0;
        }

        return (columnDraw == 3);
    }

    private boolean isDiagonal1Draw() {
        int player1Fields = 0;
        int player2Fields = 0;
        for (int index = 0; index < 3; index++) {
            if (fields[index][index].isChecked()) {
                if (fields[index][index].getCheckedPlayer().equals(player1)) {
                    player1Fields++;
                } else if (fields[index][index].getCheckedPlayer().equals(this.player2)) {
                    player2Fields++;
                }
            }
        }

        return (player1Fields >= 1 && player2Fields >= 1);
    }

    private boolean isDiagonal2Draw() {
        int player1Fields = 0;
        int player2Fields = 0;

        for (int index = 0; index < 3; index++) {
            int diagonal2Calc = 3 - 1 - index;
            if (fields[index][diagonal2Calc].isChecked()) {
                if (fields[index][diagonal2Calc].getCheckedPlayer().equals(player1)) {
                    player1Fields++;
                } else if (fields[index][diagonal2Calc].getCheckedPlayer().equals(player2)) {
                    player2Fields++;
                }
            }
        }

        return (player1Fields >= 1 && player2Fields >= 1);
    }

    public boolean isDraw() {
        return (isLinesDraw() && isColumnsDraw() && isDiagonal1Draw() && isDiagonal2Draw());
    }

    public boolean hasWinner() {
        return (hasLineWinner() || hasColumnWinner() || hasDiagonal1Winner() || hasDiagonal2Winner());
    }

    private boolean isValidPlay(int positionPlayed) {
        for (int line = 0; line < 3; line++) {
            for (int column = 0; column < 3; column++) {
                if (fields[line][column].getPosition() == positionPlayed && !fields[line][column].isChecked()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void applyPlay(int positionPlayed, Player player) {
        for (int line = 0; line < 3; line++) {
            for (int column = 0; column < 3; column++) {
                if (fields[line][column].getPosition() == positionPlayed) {
                    Field.checkField(player, fields[line][column]);
                }
            }
        }
    }

    public void start() {
        int position;
        Scanner scanner = new Scanner(System.in);
        sortFirstPlayer();

        while (!hasWinner() && !isDraw()) {
            do {
                turn++;
                App.clearConsole();
                System.out.println(
                        "--- Vez de " + playerTurn.getPlayerType() + " (" + playerTurn.getSymbol() + ") --- \n");
                print();
                if (playerTurn.getPlayerType().equals(PlayerType.COM)) {
                    position = Computer.play(this);
                } else {
                    System.out.printf("\nIndique a posição que deseja jogar: ");
                    position = scanner.nextInt();
                }
            } while (!isValidPlay(position));

            applyPlay(position, playerTurn);
            this.playerTurn = playerTurn.getPlayerType().equals(player1.getPlayerType()) ? player2 : player1;
        }

        App.clearConsole();

        if (hasWinner()) {
            System.out.println(
                    "--- " + playerWinner.getPlayerType() + " (" + playerWinner.getSymbol() + ") Ganhou --- \n");
        }

        if (isDraw()) {
            System.out.println("--- Empate --- \n");
        }

        print();
    }

}
