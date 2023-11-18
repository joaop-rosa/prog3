import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    private static ArrayList<Paciente> pacientes = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    static void incluirPaciente() {
        Paciente paciente = new Paciente();
        System.out.println("Informe o nome do paciente");
        String nome = scanner.nextLine();
        System.out.println("Informe o sobrenome do paciente");
        String sobrenome = scanner.nextLine();
        paciente.setNome(nome + " " + sobrenome);

        System.out.println("Informe o ano de nascimento do paciente");
        int ano = scanner.nextInt();
        System.out.println("Informe o mes de nascimento do paciente");
        int mes = scanner.nextInt();
        System.out.println("Informe o dia de nascimento do paciente");
        int dia = scanner.nextInt();
        paciente.setDataNascimento(LocalDate.of(ano, mes, dia));

        pacientes.add(paciente);
    }

    public static void main(String[] args) throws Exception {
        int option = 99;

        do {
            System.out.println("Bem-vindo ao sistema de consulta médica");
            System.out.println("---------------------------------------");
            System.out.println("Selecione a opção");
            System.out.println("1 - Incluir paciente");
            System.out.println("2 - Alterar paciente");
            System.out.println("3 - Realizar consulta");
            System.out.println("4 - Listar pacientes");
            System.out.println("5 - Mostrar paciente");
            System.out.println("6 - Apagar paciente");
            System.out.print("Digite a opcao: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    incluirPaciente();
                    break;
                case 2:

                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 6:

                    break;
                default:
                    break;
            }

            pacientes.toString();
        } while (option != 0);
    }
}
