import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import enums.PlayerType;

public class Game {
    private Player player1;
    private Player player2;
    private Player playerStart;
    private Field[][] fields;
    private Player playerTurn;
    private Player playerWinner;

    int calcPosition(int line, int column) {
        return (line * 3) + column + 1;
    }

    void fillFields() {
        for (int line = 0; line < 3; line++) {
            for (int column = 0; column < 3; column++) {
                this.fields[line][column] = new Field(calcPosition(line, column));
            }
        }
    }

    Game(Player player1) {
        this.player1 = player1;
        this.player2 = new Player(PlayerType.COM, player1.getReverseSymbol());
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
        this.playerStart = (result == 0) ? player1 : player2;
        this.playerTurn = playerStart;
    }

    public void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void print() {
        for (int line = 0; line < 3; line++) {
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

    boolean checkLine(int line, Player player) {
        return ((fields[line][0].isChecked() && fields[line][0].getCheckedPlayer().equals(player))
                && (fields[line][1].isChecked() && fields[line][1].getCheckedPlayer().equals(player))
                && (fields[line][2].isChecked() && fields[line][2].getCheckedPlayer().equals(player)));
    }

    boolean checkColumn(int column, Player player) {
        return ((fields[0][column].isChecked() && fields[0][column].getCheckedPlayer().equals(player))
                && (fields[1][column].isChecked() && fields[1][column].getCheckedPlayer().equals(player))
                && (fields[2][column].isChecked() && fields[2][column].getCheckedPlayer().equals(player)));
    }

    boolean checkDiagonal(Player player) {
        return ((fields[0][0].isChecked() && fields[0][0].getCheckedPlayer().equals(player))
                && (fields[1][1].isChecked() && fields[1][1].getCheckedPlayer().equals(player))
                && (fields[2][2].isChecked() && fields[2][2].getCheckedPlayer().equals(player))) ||
                ((fields[0][2].isChecked() && fields[0][2].getCheckedPlayer().equals(player))
                        && (fields[1][1].isChecked() && fields[1][1].getCheckedPlayer().equals(player))
                        && (fields[2][0].isChecked() && fields[2][0].getCheckedPlayer().equals(player)));
    }

    boolean isLineDraw(int line) {
        int qtdPlayer1Fields = 0;
        int qtdPlayer2Fields = 0;
        for (int column = 0; column < 3; column++) {
            if (fields[line][column].isChecked()) {
                if (!fields[line][column].getCheckedPlayer().equals(player1)) {
                    qtdPlayer1Fields++;
                } else {
                    qtdPlayer2Fields++;
                }
            }
        }
        return (qtdPlayer1Fields == 1 && qtdPlayer2Fields == 1);
    }

    boolean isColumnDraw(int column) {
        int qtdPlayer1Fields = 0;
        int qtdPlayer2Fields = 0;
        for (int line = 0; line < 3; line++) {
            if (fields[line][column].isChecked()) {
                if (!fields[line][column].getCheckedPlayer().equals(player1)) {
                    qtdPlayer1Fields++;
                } else {
                    qtdPlayer2Fields++;
                }
            }
        }
        return (qtdPlayer1Fields == 1 && qtdPlayer2Fields == 1);
    }

    boolean isDiagonalDraw() {
        int qtdPlayer1Fields = 0;
        int qtdPlayer2Fields = 0;
        for (int line = 0; line < 3; line++) {
            for (int column = 0; line < 3; line++) {
                if (line == column) {
                    if (fields[line][column].isChecked()) {
                        if (!fields[line][column].getCheckedPlayer().equals(player1)) {
                            qtdPlayer1Fields++;
                        } else {
                            qtdPlayer2Fields++;
                        }
                    }
                }
            }

        }
        return (qtdPlayer1Fields == 1 && qtdPlayer2Fields == 1);
    }

    boolean isDraw() {
        boolean hasLinePlayAvaliable = false;
        boolean hasColumnPlayAvaliable = false;
        boolean hasDiagonalPlayAvaliable = false;
        for (int x = 0; x < 3; x++) {
            hasLinePlayAvaliable = isLineDraw(x);
            hasColumnPlayAvaliable = isColumnDraw(x);
        }
        hasDiagonalPlayAvaliable = isDiagonalDraw();

        return (hasLinePlayAvaliable || hasColumnPlayAvaliable || hasDiagonalPlayAvaliable);
    }

    public boolean hasWinner() {
        // boolean hasWinner = false;
        for (int line = 0; line < 3; line++) {
            // hasWinner = checkLine(line, player1);
            // hasWinner = checkLine(line, player1);
            if (checkLine(line, player1)) {
                playerWinner = player1;
                return true;
            }
            if (checkLine(line, player2)) {
                playerWinner = player2;
                return true;
            }
        }
        for (int column = 0; column < 3; column++) {
            if (checkColumn(column, player1)) {
                playerWinner = player1;
                return true;
            }
            if (checkColumn(column, player2)) {
                playerWinner = player2;
                return true;
            }
        }

        if (checkDiagonal(player1)) {
            playerWinner = player1;
            return true;
        }
        if (checkDiagonal(player2)) {
            playerWinner = player2;
            return true;
        }
        return false;
    }

    public boolean isValidPlay(int positionPlayed) {
        for (int line = 0; line < 3; line++) {
            for (int column = 0; column < 3; column++) {
                if (fields[line][column].getPosition() == positionPlayed && !fields[line][column].isChecked()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void applyPlay(int positionPlayed, Player player) {
        for (int line = 0; line < 3; line++) {
            for (int column = 0; column < 3; column++) {
                if (fields[line][column].getPosition() == positionPlayed) {
                    fields[line][column].checkField(player);
                }
            }
        }
    }

    public void start() {
        int position;
        Scanner scanner = new Scanner(System.in);
        sortFirstPlayer();

        while (!hasWinner()) {
            do {
                clearConsole();
                System.out.println(
                        "--- Vez de " + playerTurn.getPlayerType() + " (" + playerTurn.getSymbol() + ") --- \n");
                print();
                System.out.printf("\nIndique a posição que deseja jogar: ");
                position = scanner.nextInt();
            } while (!isValidPlay(position));

            applyPlay(position, playerTurn);
            this.playerTurn = playerTurn.getPlayerType().equals(player1.getPlayerType()) ? player2 : player1;
        }

        System.out.println(
                "--- " + playerWinner.getPlayerType() + " (" + playerWinner.getSymbol() + ") Ganhou --- \n");
        print();
    }
}
