package atividade08;

import atividade08.exceptions.ContaInativaException;
import atividade08.exceptions.DadosInvalidosException;
import atividade08.exceptions.SaldoInsuficienteException;
import atividade08.exceptions.ValorInvalidoException;
import atividade08.model.ContaCorrente;

public class Main {

    public static void main(String[] args) {

        System.out.println("       ATIVIDADE 08 — SISTEMA DE CONTA CORRENTE            ");

        ContaCorrente contaA = null;
        ContaCorrente contaB = null;

        try {
            contaA = new ContaCorrente(1001, "Ana Souza", 500.00);
            contaB = new ContaCorrente(1002, "Bruno Lima", 200.00);
            System.out.println("  Contas criadas com sucesso.\n");
        } catch (DadosInvalidosException e) {
            System.out.println("  Erro ao criar conta: " + e.getMessage());
            return;
        }

        try { contaA.depositar(300.00); }
        catch (ValorInvalidoException | ContaInativaException e) {
            System.out.println("✘  " + e.getMessage());
        }

        try { contaA.sacar(100.00); }
        catch (ValorInvalidoException | ContaInativaException e) {
            System.out.println("  " + e.getMessage());
        } catch (SaldoInsuficienteException e) {
            System.out.printf("  Saldo insuficiente! Saldo: R$ %.2f%n", e.getSaldoAtual());
        }

        try { contaA.transferir(contaB, 250.00); }
        catch (ValorInvalidoException | ContaInativaException e) {
            System.out.println("  " + e.getMessage());
        } catch (SaldoInsuficienteException e) {
            System.out.printf("  Saldo insuficiente para transferência! Saldo: R$ %.2f%n", e.getSaldoAtual());
        }

        try { contaA.exibirExtrato(); contaB.exibirExtrato(); }
        catch (ContaInativaException e) { System.out.println("  " + e.getMessage()); }

        System.out.println("── CENÁRIOS DE ERRO ──────────────────────────────────────\n");

        System.out.print("Tentando sacar R$ 9999.00 → ");
        try { contaA.sacar(9999.00); }
        catch (SaldoInsuficienteException e) { System.out.println("  " + e.getMessage()); }
        catch (ValorInvalidoException | ContaInativaException e) { System.out.println("  " + e.getMessage()); }

        System.out.print("Tentando depositar R$ -50.00 → ");
        try { contaA.depositar(-50.00); }
        catch (ValorInvalidoException | ContaInativaException e) { System.out.println("✘  " + e.getMessage()); }

        System.out.print("Tentando operar em conta encerrada → ");
        contaA.encerrar();
        try { contaA.depositar(100.00); }
        catch (ContaInativaException | ValorInvalidoException e) { System.out.println("✘  " + e.getMessage()); }

        System.out.print("Criando conta com nome vazio → ");
        try { new ContaCorrente(1003, "  ", 0); }
        catch (DadosInvalidosException e) { System.out.println("✘  " + e.getMessage()); }

        System.out.print("Criando conta com numero -1 → ");
        try { new ContaCorrente(-1, "Carlos", 100); }
        catch (DadosInvalidosException e) { System.out.println("✘  " + e.getMessage()); }

        System.out.print("Criando conta com saldo inicial -200 → ");
        try { new ContaCorrente(1004, "Diana", -200); }
        catch (DadosInvalidosException e) { System.out.println("✘  " + e.getMessage()); }

    }
}
