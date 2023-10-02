////////////////////////////////////
//// Autor: João Paulo da Rosa ////
///////////////////////////////////

package jogoVelha;

import java.util.Scanner;

import jogoVelha.enums.PlayerType;
import jogoVelha.enums.Symbol;

public class App {

    public static void clearConsole() {
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

    static Symbol askSymbol() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1 - X ");
        System.out.println("2 - O ");
        System.out.printf("\n" + PlayerType.P1 + " qual simbolo deseja usar? ");
        int symbol = scanner.nextInt();
        return symbol == 1 ? Symbol.X : Symbol.O;
    }

    public static void main(String[] args) throws Exception {
        int option;
        int playAgain;

        do {
            clearConsole();
            Scanner scanner = new Scanner(System.in);
            System.out.println("--- Jogo da velha ---\n");
            System.out.println("1 - Jogar contra o computador ");
            System.out.println("2 - Jogar contra outro jogador ");
            System.out.println("0 - Sair ");
            System.out.printf("\nEscolha o que deseja fazer: ");
            option = scanner.nextInt();

            clearConsole();

            switch (option) {
                case 1:
                    Game gameXCom;
                    Player player = new Player(PlayerType.P1, askSymbol());
                    gameXCom = new Game(player);
                    gameXCom.start();
                    break;
                case 2:
                    Game gamePvp;
                    Player player1 = new Player(PlayerType.P1, askSymbol());
                    Player player2 = new Player(PlayerType.P2, player1.getReverseSymbol());
                    gamePvp = new Game(player1, player2);
                    gamePvp.start();
                    break;
                default:
                    break;
            }

            if (option == 1 || option == 2) {
                do {
                    System.out.println("\n--------------\n");
                    System.out.println("1 - Sim ");
                    System.out.println("2 - Não ");
                    System.out.printf("\nDeseja jogar novamente: ");
                    playAgain = scanner.nextInt();
                } while (playAgain != 1 && playAgain != 2);
            } else {
                playAgain = 2;
            }

        } while (option != 0 && playAgain == 1);

    }
}
