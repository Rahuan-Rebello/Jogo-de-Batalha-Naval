import java.util.Random;

public class Tabuleiro {
    private char[][] grade;
    private final int TAMANHO = 10;
    private final char AGUA = '~';
    private final char NAVIO = 'N';
    private final char ERRO = 'O';
    private final char ACERTO = 'X';

    public Tabuleiro() {
        grade = new char[TAMANHO][TAMANHO];
        inicializarTabuleiro();
    }

    private void inicializarTabuleiro() {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                grade[i][j] = AGUA;
            }
        }
    }

    public void exibir(boolean esconderNavios) {
        System.out.println("\n  0 1 2 3 4 5 6 7 8 9");
        for (int i = 0; i < TAMANHO; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < TAMANHO; j++) {
                char charAtual = grade[i][j];
                if (esconderNavios && charAtual == NAVIO) {
                    System.out.print(AGUA + " ");
                } else {
                    System.out.print(charAtual + " ");
                }
            }
            System.out.println();
        }
    }

    public void posicionarNavioAleatorio(Navio navio) {
        Random random = new Random();
        boolean posicionado = false;

        while (!posicionado) {
            int linha = random.nextInt(TAMANHO);
            int coluna = random.nextInt(TAMANHO);
            boolean horizontal = random.nextBoolean();

            if (podePosicionar(linha, coluna, navio.getTamanho(), horizontal)) {
                for (int i = 0; i < navio.getTamanho(); i++) {
                    if (horizontal) grade[linha][coluna + i] = NAVIO;
                    else grade[linha + i][coluna] = NAVIO;
                }
                posicionado = true;
            }
        }
    }

    private boolean podePosicionar(int linha, int coluna, int tamanho, boolean horizontal) {
        if (horizontal) {
            if (coluna + tamanho > TAMANHO) return false;
            for (int i = 0; i < tamanho; i++) if (grade[linha][coluna + i] != AGUA) return false;
        } else {
            if (linha + tamanho > TAMANHO) return false;
            for (int i = 0; i < tamanho; i++) if (grade[linha + i][coluna] != AGUA) return false;
        }
        return true;
    }

    public boolean receberTiro(int linha, int coluna) {
        if (grade[linha][coluna] == NAVIO) {
            grade[linha][coluna] = ACERTO;
            return true;
        } else if (grade[linha][coluna] == AGUA) {
            grade[linha][coluna] = ERRO;
        }
        return false;
    }

    public boolean temNaviosVivos() {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                if (grade[i][j] == NAVIO) return true;
            }
        }
        return false;
    }
}