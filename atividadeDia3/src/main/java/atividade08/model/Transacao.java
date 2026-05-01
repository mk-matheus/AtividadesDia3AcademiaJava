package atividade08.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transacao {

    public enum Tipo { DEPOSITO, SAQUE, TRANSFERENCIA_ENVIADA, TRANSFERENCIA_RECEBIDA }

    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final Tipo          tipo;
    private final double        valor;
    private final double        saldoApos;
    private final LocalDateTime dataHora;
    private final String        descricao;

    public Transacao(Tipo tipo, double valor, double saldoApos, String descricao) {
        this.tipo      = tipo;
        this.valor     = valor;
        this.saldoApos = saldoApos;
        this.dataHora  = LocalDateTime.now();
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return String.format("  [%s] %-30s | Valor: R$ %8.2f | Saldo após: R$ %10.2f",
            dataHora.format(FORMATTER), descricao, valor, saldoApos);
    }

    public Tipo          getTipo()      { return tipo; }
    public double        getValor()     { return valor; }
    public double        getSaldoApos() { return saldoApos; }
    public LocalDateTime getDataHora()  { return dataHora; }
    public String        getDescricao() { return descricao; }
}
