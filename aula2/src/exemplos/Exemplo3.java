package exemplos;

import java.util.Scanner;

public class Exemplo3 {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        float[] notas = new float[4];
        float somaNotas = 0;
        for (int i = 0; i < notas.length; i++) {
            System.out.println("Informe a nota " + (i + 1) + " : ");
            notas[i] = reader.nextFloat();
            somaNotas += notas[i];
        }
        float media = somaNotas / notas.length;
        System.out.println("Sua média é: " + media);
    }
}
