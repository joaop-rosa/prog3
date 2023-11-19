import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    private static ArrayList<Paciente> pacientes = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

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

    static void aguardarTecla() {
        System.out.println("\nPressione qualquer tecla para continuar...");
        scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    static void incluirPaciente() {
        clearConsole();
        System.out.println("Incluir paciente");
        System.out.println("-----------------------------");
        Paciente paciente = new Paciente();
        System.out.println("Informe o nome do paciente");
        String nome = scanner.nextLine();
        scanner = new Scanner(System.in);
        System.out.println("Informe o sobrenome do paciente");
        String sobrenome = scanner.nextLine();
        scanner = new Scanner(System.in);
        paciente.setNome(nome + " " + sobrenome);

        boolean dataValida = false;
        do {
            try {
                System.out.println("Informe o ano de nascimento do paciente");
                int ano = scanner.nextInt();
                System.out.println("Informe o mes de nascimento do paciente");
                int mes = scanner.nextInt();
                System.out.println("Informe o dia de nascimento do paciente");
                int dia = scanner.nextInt();
                LocalDate dataNascimento = LocalDate.of(ano, mes, dia);
                if (Period.between(dataNascimento, LocalDate.now()).getDays() < 0) {
                    throw new Exception();
                }
                paciente.setDataNascimento(dataNascimento);
                dataValida = true;
            } catch (Exception e) {
                System.out.println("A data de nascimento informada eh invalida");
                dataValida = false;
            }
        } while (!dataValida);

        pacientes.add(paciente);
    }

    static void listarPacientes() {
        clearConsole();
        System.out.println("Lista de pacientes");
        System.out.println("-----------------------------");
        if (pacientes.size() == 0) {
            System.out.println("Não existe nenhum paciente cadastrado");
        } else {
            pacientes.forEach((paciente) -> System.out.println(paciente.toString()));
        }
        aguardarTecla();
    }

    static Paciente listarPacientesCodigo() {
        Paciente pacienteSelecionado = null;
        pacientes.forEach((paciente) -> {
            System.out.println("---------------------------------------");
            System.out.println("Codigo: " + pacientes.indexOf(paciente));
            System.out.println(paciente.toString());
            System.out.println("---------------------------------------");
        });
        boolean sucesso = false;
        do {
            System.out.println("Informe o codigo: ");
            int codigo = scanner.nextInt();
            try {
                pacienteSelecionado = pacientes.get(codigo);
                sucesso = true;
            } catch (Exception IndexOutOfBoundsException) {
                System.out.println("Codigo invalido");
                sucesso = false;
            }
        } while (!sucesso);
        return pacienteSelecionado;
    }

    static void listarPacienteDetalhado(Paciente paciente) {
        clearConsole();
        System.out.println("Busca detalhada de paciente");
        System.out.println("-----------------------------");
        System.out.println("Paciente: " + paciente.toString());
        System.out.println("-----------------------------");
        if (paciente.getAtendimentos().size() == 0) {
            System.out.println("O paciente não possui atendimentos");
        } else {
            for (int index = 0; index < paciente.getAtendimentos().size(); index++) {
                System.out.println("-------------------");
                System.out.println(paciente.getAtendimentos().get(index).toString());
                System.out.println("-------------------");
                if ((index + 1) % 5 == 0) {
                    aguardarTecla();
                }
            }

        }
        aguardarTecla();
    }

    static void editarPaciente(Paciente paciente) {
        int respostaNome = 99;
        int respostaData = 99;
        DateTimeFormatter formatoBr = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("-----------------------------------");
        System.out.println("Nome: " + paciente.getNome());
        System.out.println("Data de nascimento: " + formatoBr.format(paciente.getDataNascimento()));
        System.out.println("-----------------------------------");

        do {
            System.out.println("Deseja alterar o nome do paciente?");
            System.out.println("1 - Sim");
            System.out.println("2 - Não");
            respostaNome = scanner.nextInt();
            scanner = new Scanner(System.in);
        } while (respostaNome != 1 && respostaNome != 2);
        if (respostaNome == 1) {
            System.out.println("Informe o nome do paciente");
            String nome = scanner.nextLine();
            scanner = new Scanner(System.in);
            System.out.println("Informe o sobrenome do paciente");
            String sobrenome = scanner.nextLine();
            scanner = new Scanner(System.in);
            paciente.setNome(nome + " " + sobrenome);
        }

        scanner = new Scanner(System.in);

        do {
            System.out.println("Deseja a data de nascimento do paciente?");
            System.out.println("1 - Sim");
            System.out.println("2 - Não");
            respostaData = scanner.nextInt();
            scanner = new Scanner(System.in);
        } while (respostaData != 1 && respostaData != 2);
        if (respostaData == 1) {
            boolean dataValida = false;
            do {
                try {
                    System.out.println("Informe o ano de nascimento do paciente");
                    int ano = scanner.nextInt();
                    System.out.println("Informe o mes de nascimento do paciente");
                    int mes = scanner.nextInt();
                    System.out.println("Informe o dia de nascimento do paciente");
                    int dia = scanner.nextInt();
                    LocalDate dataNascimento = LocalDate.of(ano, mes, dia);
                    if (Period.between(dataNascimento, LocalDate.now()).getDays() < 0) {
                        throw new Exception();
                    }
                    paciente.setDataNascimento(dataNascimento);
                    dataValida = true;
                } catch (Exception e) {
                    System.out.println("A data de nascimento informada eh invalida");
                    dataValida = false;
                }
            } while (!dataValida);
        }
    }

    static void realizarConsulta(Paciente paciente) {
        clearConsole();
        System.out.println("Realizar consulta");
        System.out.println("Paciente: " + paciente.getNome());
        System.out.println("-----------------------------");
        System.out.println("Descreva o atendimento:");
        System.out.print("- ");
        scanner = new Scanner(System.in);
        String descricao = scanner.nextLine();
        Atendimento atendimento = new Atendimento(descricao);
        paciente.adicionarConsulta(atendimento);

        System.out.println("Consulta adiciona com sucesso");
        aguardarTecla();
    }

    static Paciente buscarPaciente() {
        System.out.println("Informe o nome do paciente");
        String nome = scanner.next();
        ArrayList<Paciente> pacientesFiltrados = new ArrayList<>();
        Paciente pacienteSelecionado = null;

        pacientes.forEach((paciente) -> {
            if (paciente.getNome().toLowerCase()
                    .contains(nome.toLowerCase())) {
                pacientesFiltrados.add(paciente);
            }
        });
        System.out.println("---------------------------------------\n");
        if (pacientesFiltrados.size() == 1) {
            pacienteSelecionado = pacientesFiltrados.get(0);
        } else if (pacientesFiltrados.size() == 0) {
            System.out.println("Nenhum paciente encontrado");
            aguardarTecla();
        } else {
            clearConsole();
            System.out.println("Foi encontrado mais de um paciente");
            System.out.println("Selecione o paciente que deseja pelo código");
            pacientesFiltrados.forEach((paciente) -> {
                System.out.println("---------------------------------------");
                System.out.println("Codigo: " + pacientesFiltrados.indexOf(paciente));
                System.out.println(paciente.toString());
                System.out.println("---------------------------------------");
            });
            boolean sucesso = false;
            do {
                System.out.print("Informe o codigo: ");
                int codigo = scanner.nextInt();
                try {
                    pacienteSelecionado = pacientesFiltrados.get(codigo);
                    sucesso = true;
                } catch (Exception IndexOutOfBoundsException) {
                    System.out.println("Codigo invalido");
                    sucesso = false;
                }
            } while (!sucesso);
        }

        return pacienteSelecionado;
    }

    public static void main(String[] args) throws Exception {
        int option = 99;

        do {
            clearConsole();
            System.out.println("Bem-vindo ao sistema de consulta médica");
            System.out.println("---------------------------------------");
            System.out.println("Opções:\n");
            System.out.println("1 - Incluir paciente");
            System.out.println("2 - Listar pacientes");
            System.out.println("3 - Realizar consulta");
            System.out.println("4 - Detalhar atendimentos de paciente");
            System.out.println("5 - Editar paciente");
            System.out.println("6 - Apagar paciente");
            System.out.println("0 - Sair");
            System.out.println("\nOpcoes de teste: ");
            System.out.println("7 - Carga inicial de pacientes e atendimentos");
            System.out.print("\nDigite a opcao: ");
            option = scanner.nextInt();
            scanner = new Scanner(System.in);

            switch (option) {
                case 1:
                    incluirPaciente();
                    System.out.println(pacientes.get(0).toString());
                    break;
                case 2:
                    listarPacientes();
                    break;
                case 3:
                    clearConsole();
                    System.out.println("Realizar consulta");
                    System.out.println("-----------------------------");
                    Paciente pacienteSelecionadoConsulta = buscarPaciente();
                    if (pacienteSelecionadoConsulta != null) {
                        realizarConsulta(pacienteSelecionadoConsulta);
                    }
                    break;
                case 4:
                    clearConsole();
                    System.out.println("Busca detalhada de paciente");
                    System.out.println("-----------------------------");
                    Paciente pacienteSelecionadoListar = buscarPaciente();
                    if (pacienteSelecionadoListar != null) {
                        listarPacienteDetalhado(pacienteSelecionadoListar);
                    }
                    break;
                case 5:
                    clearConsole();
                    System.out.println("Editar paciente");
                    System.out.println("-----------------------------");
                    System.out.println("Selecione o paciente que deseja editar");
                    Paciente pacienteEditar = buscarPaciente();
                    if (pacienteEditar != null) {
                        editarPaciente(pacienteEditar);
                        System.out.println("Paciente: " + pacienteEditar.getNome() + " editado com sucesso");
                        aguardarTecla();
                    }
                    break;
                case 6:
                    clearConsole();
                    System.out.println("Excluir paciente");
                    System.out.println("-----------------------------");
                    System.out.println("Selecione o paciente que deseja excluir");
                    Paciente pacienteExcluir = listarPacientesCodigo();
                    pacientes.remove(pacientes.indexOf(pacienteExcluir));
                    System.out.println("Paciente: " + pacienteExcluir.getNome() + " removido com sucesso");
                    aguardarTecla();
                    break;
                case 7:
                    Paciente paciente1 = new Paciente();
                    paciente1.setNome("Jose Antonio");
                    paciente1.setDataNascimento(LocalDate.of(1970, 8, 14));
                    pacientes.add(paciente1);
                    paciente1.adicionarConsulta(new Atendimento("Atendimento 1"));
                    paciente1.adicionarConsulta(new Atendimento("Atendimento 2"));
                    paciente1.adicionarConsulta(new Atendimento("Atendimento 3"));
                    paciente1.adicionarConsulta(new Atendimento("Atendimento 4"));
                    paciente1.adicionarConsulta(new Atendimento("Atendimento 5"));
                    paciente1.adicionarConsulta(new Atendimento("Atendimento 6"));
                    paciente1.adicionarConsulta(new Atendimento("Atendimento 7"));
                    paciente1.adicionarConsulta(new Atendimento("Atendimento 8"));
                    paciente1.adicionarConsulta(new Atendimento("Atendimento 9"));
                    paciente1.adicionarConsulta(new Atendimento("Atendimento 10"));
                    paciente1.adicionarConsulta(new Atendimento("Atendimento 11"));
                    Paciente paciente2 = new Paciente();
                    paciente2.setNome("Maria Silva");
                    paciente2.setDataNascimento(LocalDate.of(1950, 1, 30));
                    paciente2.adicionarConsulta(new Atendimento("Atendimento 1"));
                    paciente2.adicionarConsulta(new Atendimento("Atendimento 2"));
                    paciente2.adicionarConsulta(new Atendimento("Atendimento 3"));
                    pacientes.add(paciente2);
                    Paciente paciente3 = new Paciente();
                    paciente3.setNome("Carlos Silva");
                    paciente3.setDataNascimento(LocalDate.of(1978, 12, 1));
                    paciente3.adicionarConsulta(new Atendimento("Atendimento 1"));
                    paciente3.adicionarConsulta(new Atendimento("Atendimento 2"));
                    paciente3.adicionarConsulta(new Atendimento("Atendimento 3"));
                    paciente3.adicionarConsulta(new Atendimento("Atendimento 4"));
                    paciente3.adicionarConsulta(new Atendimento("Atendimento 5"));
                    paciente3.adicionarConsulta(new Atendimento("Atendimento 6"));
                    paciente3.adicionarConsulta(new Atendimento("Atendimento 7"));
                    pacientes.add(paciente3);
                    break;
                default:
                    break;
            }

        } while (option != 0);
    }
}
