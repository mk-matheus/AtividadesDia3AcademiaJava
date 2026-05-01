package atividade09.model;

public enum Situacao {

    APROVADO    ("APROVADO",    "✔"),
    RECUPERACAO ("RECUPERAÇÃO", "⚠"),
    REPROVADO   ("REPROVADO",   "✘");

    private final String label;
    private final String icone;

    Situacao(String label, String icone) {
        this.label = label;
        this.icone = icone;
    }

    public static Situacao calcular(double media) {
        return switch ((int) media / 10) {
            case 7, 8, 9, 10 -> APROVADO;
            case 5, 6        -> RECUPERACAO;
            default          -> REPROVADO;
        };
    }

    public String getLabel() { return label; }
    public String getIcone() { return icone; }

    @Override
    public String toString() { return label; }
}
