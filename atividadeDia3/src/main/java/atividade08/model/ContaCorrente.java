package atividade08.model;

import atividade08.exceptions.ContaInativaException;
import atividade08.exceptions.DadosInvalidosException;
import atividade08.exceptions.SaldoInsuficienteException;
import atividade08.exceptions.ValorInvalidoException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContaCorrente {

    private final int             numero;
    private final String          nome;
    private double                saldo;
    private final LocalDate       data;
    private boolean               ativa;
    private final List<Transacao> historico;

    private static final DateTimeFormatter FMT_DATA =
        DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public ContaCorrente(int numero, String nome, double saldo)
            throws DadosInvalidosException {

        validarNumero(numero);
        validarNome(nome);
        validarSaldoInicial(saldo);

        this.numero    = numero;
        this.nome      = nome.trim();
        this.saldo     = saldo;
        this.data      = LocalDate.now();
        this.ativa     = true;
        this.historico = new ArrayList<>();
    }

    // ── Operações financeiras

    public void depositar(double valor)
            throws ValorInvalidoException, ContaInativaException {
        verificarContaAtiva();
        validarValorPositivo(valor);
        saldo += valor;
        registrarTransacao(Transacao.Tipo.DEPOSITO, valor, "Depósito em conta");
        System.out.printf("✔  Depósito de R$ %.2f realizado. Novo saldo: R$ %.2f%n", valor, saldo);
    }

    public void sacar(double valor)
            throws ValorInvalidoException, SaldoInsuficienteException, ContaInativaException {
        verificarContaAtiva();
        validarValorPositivo(valor);
        verificarSaldo(valor);
        saldo -= valor;
        registrarTransacao(Transacao.Tipo.SAQUE, valor, "Saque em conta");
        System.out.printf("✔  Saque de R$ %.2f realizado. Novo saldo: R$ %.2f%n", valor, saldo);
    }

    public void transferir(ContaCorrente destino, double valor)
            throws ValorInvalidoException, SaldoInsuficienteException, ContaInativaException {
        if (destino == null) throw new IllegalArgumentException("Conta destino não pode ser nula.");
        verificarContaAtiva();
        destino.verificarContaAtiva();
        validarValorPositivo(valor);
        verificarSaldo(valor);

        saldo         -= valor;
        destino.saldo += valor;

        registrarTransacao(Transacao.Tipo.TRANSFERENCIA_ENVIADA, valor,
            String.format("Transferência enviada → Conta %d (%s)", destino.numero, destino.nome));
        destino.registrarTransacao(Transacao.Tipo.TRANSFERENCIA_RECEBIDA, valor,
            String.format("Transferência recebida ← Conta %d (%s)", this.numero, this.nome));

        System.out.printf("✔  Transferência de R$ %.2f para a conta %d (%s) realizada.%n",
            valor, destino.numero, destino.nome);
    }

    public void exibirExtrato() throws ContaInativaException {
        verificarContaAtiva();
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.printf ("║  EXTRATO DA CONTA CORRENTE %-33s║%n", "");
        System.out.printf ("║  Titular : %-51s║%n", nome);
        System.out.printf ("║  Conta   : %-51d║%n", numero);
        System.out.printf ("║  Abertura: %-51s║%n", data.format(FMT_DATA));
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        if (historico.isEmpty()) {
            System.out.println("║  Nenhuma movimentação registrada.                            ║");
        } else {
            historico.forEach(t -> System.out.println(t));
        }
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.printf ("║  SALDO ATUAL: R$ %-44.2f║%n", saldo);
        System.out.printf ("║  Status: %-53s║%n", ativa ? "ATIVA" : "INATIVA");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();
    }

    public void encerrar() {
        this.ativa = false;
        System.out.printf("⚠  Conta %d encerrada.%n", numero);
    }

    // ── Validações privadas

    private void verificarContaAtiva() throws ContaInativaException {
        if (!ativa) throw new ContaInativaException(numero);
    }

    private void validarValorPositivo(double valor) throws ValorInvalidoException {
        if (valor <= 0) throw new ValorInvalidoException(valor);
    }

    private void verificarSaldo(double valor) throws SaldoInsuficienteException {
        if (valor > saldo) throw new SaldoInsuficienteException(saldo, valor);
    }

    private static void validarNumero(int numero) throws DadosInvalidosException {
        if (numero <= 0)
            throw new DadosInvalidosException("numero", "deve ser um inteiro positivo.");
    }

    private static void validarNome(String nome) throws DadosInvalidosException {
        if (nome == null || nome.trim().isEmpty())
            throw new DadosInvalidosException("nome", "não pode ser nulo ou vazio.");
        if (nome.trim().length() < 3)
            throw new DadosInvalidosException("nome", "deve ter pelo menos 3 caracteres.");
    }

    private static void validarSaldoInicial(double saldo) throws DadosInvalidosException {
        if (saldo < 0)
            throw new DadosInvalidosException("saldo", "o saldo inicial não pode ser negativo.");
    }

    private void registrarTransacao(Transacao.Tipo tipo, double valor, String descricao) {
        historico.add(new Transacao(tipo, valor, saldo, descricao));
    }

    // ── Getters

    public int             getNumero()    { return numero; }
    public String          getNome()      { return nome; }
    public double          getSaldo()     { return saldo; }
    public LocalDate       getData()      { return data; }
    public boolean         isAtiva()      { return ativa; }
    public List<Transacao> getHistorico() { return Collections.unmodifiableList(historico); }
}
