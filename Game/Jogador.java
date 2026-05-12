import java.io.*;

class Jogador implements Serializable {
    private String nome;
    private int pontuacao = 0;
    private int abates = 0;

    public Jogador(String nome) { this.nome = nome; }
    public void registrarAcerto() { this.pontuacao += 10; this.abates++; }
    
    public String getNome() { return nome; }
    public int getPontuacao() { return pontuacao; }
    public int getAbates() { return abates; }
}