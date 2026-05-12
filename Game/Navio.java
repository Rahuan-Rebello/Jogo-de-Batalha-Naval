import java.io.Serializable;

// Arquivo: Navio.java
public abstract class Navio implements Serializable {
    protected String nome;
    protected char simbolo;
    protected int tamanho;

    public Navio(String nome, char simbolo, int tamanho) {
        this.nome = nome;
        this.simbolo = simbolo;
        this.tamanho = tamanho;
    }

    public String getNome() { return nome; }
    public char getSimbolo() { return simbolo; }
    public int getTamanho() { return tamanho; }
}

class PortaAvioes extends Navio { 
    public PortaAvioes() { 
        super("Porta-Aviões", 'a', 8); 
    } 
}

class Destroyer extends Navio { 
    public Destroyer() {
        super("Destroyer", 'd', 5); 
    } 
}

class Submarino extends Navio { 
    public Submarino() { 
        super("Submarino", 's', 4); 
    } 
}

class Fragata extends Navio { 
    public Fragata() { 
        super("Fragata", 'f', 3); 
    } 
}

class Bote extends Navio { 
    public Bote() { 
        super("Bote", 'b', 2);
    } 
}
