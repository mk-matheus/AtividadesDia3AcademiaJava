package atividade10.model;

public enum Categoria {
    ELETRONICOS,
    VESTUARIO,
    ALIMENTOS,
    LIVROS,
    ESPORTES,
    OUTROS;

    // Retorna Categoria a partir de string, ignorando maiúsculas/minúsculas.
    public static Categoria fromString(String valor) {
        try {
            return Categoria.valueOf(valor.toUpperCase());
        } catch (IllegalArgumentException e) {
            return OUTROS;
        }
    }
}
