package exemplos;

import java.util.Scanner;

public class Exemplo1 {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Digite um número: ");
        int numero = reader.nextInt();
        System.out.println("O número informado foi " + numero);
    }
}
