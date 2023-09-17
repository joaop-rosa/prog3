import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import enums.PlayerType;

public class Game implements Cloneable {
    private Player player1;
    private Player player2;
    private Field[][] fields;
    private Player playerTurn;
    private Player playerWinner;
    static private int turn = 0;

    Game getClone() {
        try {
            return (Game) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(" Cloning not allowed. ");
            return this;
        }
    }

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
        position[0] = value / 3; // Linha
        position[1] = value % 3; // Coluna
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

    private void clearConsole() {
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

    private void print() {
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

    private boolean checkLine(int line, Player player) {
        return ((fields[line][0].isChecked() && fields[line][0].getCheckedPlayer().equals(player))
                && (fields[line][1].isChecked() && fields[line][1].getCheckedPlayer().equals(player))
                && (fields[line][2].isChecked() && fields[line][2].getCheckedPlayer().equals(player)));
    }

    private boolean checkColumn(int column, Player player) {
        return ((fields[0][column].isChecked() && fields[0][column].getCheckedPlayer().equals(player))
                && (fields[1][column].isChecked() && fields[1][column].getCheckedPlayer().equals(player))
                && (fields[2][column].isChecked() && fields[2][column].getCheckedPlayer().equals(player)));
    }

    private boolean checkDiagonal(Player player) {
        return ((fields[0][0].isChecked() && fields[0][0].getCheckedPlayer().equals(player))
                && (fields[1][1].isChecked() && fields[1][1].getCheckedPlayer().equals(player))
                && (fields[2][2].isChecked() && fields[2][2].getCheckedPlayer().equals(player))) ||
                ((fields[0][2].isChecked() && fields[0][2].getCheckedPlayer().equals(player))
                        && (fields[1][1].isChecked() && fields[1][1].getCheckedPlayer().equals(player))
                        && (fields[2][0].isChecked() && fields[2][0].getCheckedPlayer().equals(player)));
    }

    private boolean isLineDraw(int line) {
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
        return (qtdPlayer1Fields >= 1 && qtdPlayer2Fields >= 1);
    }

    private boolean isColumnDraw(int column) {
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
        return (qtdPlayer1Fields >= 1 && qtdPlayer2Fields >= 1);
    }

    private boolean isDiagonalDraw() {
        int diagonal1QtdPlayer1Fields = 0;
        int diagonal1QtdPlayer2Fields = 0;
        int diagonal2QtdPlayer1Fields = 0;
        int diagonal2QtdPlayer2Fields = 0;
        for (int x = 0; x < 3; x++) {
            if (fields[x][x].isChecked()) {
                if (!fields[x][x].getCheckedPlayer().equals(player1)) {
                    diagonal1QtdPlayer1Fields++;
                } else {
                    diagonal1QtdPlayer2Fields++;
                }
            }
        }

        for (int x = 0; x < 3; x++) {
            if (fields[x][3 - 1 - x].isChecked()) {
                if (!fields[x][3 - 1 - x].getCheckedPlayer().equals(player1)) {
                    diagonal2QtdPlayer1Fields++;
                } else {
                    diagonal2QtdPlayer2Fields++;
                }
            }
        }

        return (diagonal1QtdPlayer1Fields >= 1 && diagonal1QtdPlayer2Fields >= 1)
                && (diagonal2QtdPlayer1Fields >= 1 && diagonal2QtdPlayer2Fields >= 1);
    }

    public boolean isDraw() {
        boolean hasLinePlayAvaliable = false;
        boolean hasColumnPlayAvaliable = false;
        boolean hasDiagonalPlayAvaliable = false;
        for (int x = 0; x < 3; x++) {
            if (!isLineDraw(x)) {
                hasLinePlayAvaliable = true;
                break;
            }

            if (!isColumnDraw(x)) {
                hasColumnPlayAvaliable = true;
                break;
            }
        }
        hasDiagonalPlayAvaliable = !isDiagonalDraw();

        return (!hasLinePlayAvaliable && !hasColumnPlayAvaliable && !hasDiagonalPlayAvaliable);
    }

    public boolean hasWinner() {
        for (int line = 0; line < 3; line++) {
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
                    fields[line][column].checkField(player);
                }
            }
        }
    }

    public static int getPlayScore(Game game, PlayerType playerType) {
        if (game.isDraw()) {
            return 0;
        } else if (!game.hasWinner()) {
            return -2;
        } else if (game.getPlayerWinner().getPlayerType().equals(playerType)) {
            return 1;
        } else {
            return -1;
        }
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

    public void start() {
        int position;
        Scanner scanner = new Scanner(System.in);
        sortFirstPlayer();

        while (!hasWinner() && !isDraw()) {
            do {
                turn++;
                clearConsole();
                System.out.println(
                        "--- Vez de " + playerTurn.getPlayerType() + " (" + playerTurn.getSymbol() + ") --- \n");
                print();
                if (playerTurn.getPlayerType().equals(PlayerType.COM)) {
                    position = new Computer(player2.getSymbol()).play(this);
                } else {
                    System.out.printf("\nIndique a posição que deseja jogar: ");
                    position = scanner.nextInt();
                }
            } while (!isValidPlay(position));

            applyPlay(position, playerTurn);
            this.playerTurn = playerTurn.getPlayerType().equals(player1.getPlayerType()) ? player2 : player1;
        }

        clearConsole();

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
