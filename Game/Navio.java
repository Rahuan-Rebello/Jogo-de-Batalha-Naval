public class Navio {
    private String nome;
    private int tamanho;
    private int saude;

    public Navio(String nome, int tamanho) {
        this.nome = nome;
        this.tamanho = tamanho;
        this.saude = tamanho;
    }

    public String getNome() { return nome; }
    public int getTamanho() { return tamanho; }
    
    public void registrarAcerto() {
        if (saude > 0) saude--;
    }

    public boolean estaAfundado() {
        return saude == 0;
    }
}