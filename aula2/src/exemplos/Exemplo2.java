package exemplos;

import java.util.Scanner;

public class Exemplo2 {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Informe seu primeiro nome: ");
        String firstName = reader.next();
        System.out.println("Informe seu ultimo nome: ");
        String lastName = reader.next();
        System.out.println("Bem-vindo " + firstName + " " + lastName);
    }
}
