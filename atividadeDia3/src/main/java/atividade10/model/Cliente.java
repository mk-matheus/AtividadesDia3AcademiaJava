package atividade10.model;

public class Cliente {

    private final String id;
    private final String nome;
    private final String email;
    private int          totalPedidos;

    public Cliente(String nome, String email) {
        validar(nome, email);
        this.id           = gerarId(nome);
        this.nome         = nome.trim();
        this.email        = email.trim().toLowerCase();
        this.totalPedidos = 0;
    }

    private static void validar(String nome, String email) {
        if (nome == null || nome.isBlank())   throw new IllegalArgumentException("Nome do cliente não pode ser vazio.");
        if (email == null || !email.contains("@"))
            throw new IllegalArgumentException("E-mail inválido: '" + email + "'.");
    }

    private static int contador = 1;

    private static String gerarId(String nome) {
        String prefix = nome.trim().replaceAll("\\s+", "").toUpperCase();
        prefix = prefix.length() >= 3 ? prefix.substring(0, 3) : prefix;
        return String.format("%s%03d", prefix, contador++);
    }

    public void incrementarPedidos() { totalPedidos++; }

    public String getId()           { return id; }
    public String getNome()         { return nome; }
    public String getEmail()        { return email; }
    public int    getTotalPedidos() { return totalPedidos; }

    @Override
    public String toString() {
        return String.format("[%-10s] %-20s | %s | Pedidos: %d",
            id, nome, email, totalPedidos);
    }
}
