import java.io.*;

class Jogador implements Serializable {
    private String nome;
    private int abates = 0;

    public Jogador(String nome) { this.nome = nome; }
    public void registrarAcerto() { this.abates++; }
    
    public String getNome() { return nome; }
    public int getAbates() { return abates; }
}