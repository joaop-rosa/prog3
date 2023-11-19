import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Aluno: João Paulo da Rosa
public class Atendimento {
    private LocalDate data;
    private String descricao;

    public Atendimento(String descricao) {
        this.data = LocalDate.now();
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatoBr = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String data = formatoBr.format(this.data);
        String retorno = "Data: " + data;
        retorno += "\nInformações: " + descricao;
        return retorno;
    }
}
