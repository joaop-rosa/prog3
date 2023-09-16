import java.util.Scanner;

import enums.PlayerType;
import enums.Symbol;

/* 
    1 - Verificar caso empate
    2 - Simplificar código de validacão
    3 - Criar inteligência para COM
*/

public class App {

    static Symbol askSymbol() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Qual simbolo deseja usar? ");
        System.out.println("1 - X ");
        System.out.println("2 - O ");
        int symbol = scanner.nextInt();
        return symbol == 1 ? Symbol.X : Symbol.O;
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("--- Jogo da velha ---");
        System.out.println("Escolha o que deseja fazer: ");
        System.out.println("1 - Jogar contra o computador ");
        System.out.println("2 - Jogar contra outro jogador ");
        System.out.println("0 - Sair ");
        int option = scanner.nextInt();

        if (option == 1 || option == 2) {
            Game game;
            Player player1 = new Player(PlayerType.P1, askSymbol());
            if (option == 2) {
                Player player2 = new Player(PlayerType.P2, player1.getReverseSymbol());
                game = new Game(player1, player2);
            } else {
                game = new Game(player1);
            }
            game.start();
        }
    }
}
