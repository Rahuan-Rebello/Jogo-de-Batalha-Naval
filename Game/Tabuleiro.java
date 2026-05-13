import java.io.Serializable;
import java.util.Random;

public class Tabuleiro implements Serializable {
    private char[][] mapaInterno;
    private char[][] mapaVisivel;
    private final int TAMANHO = 20;

    public Tabuleiro() {
        mapaInterno = new char[TAMANHO][TAMANHO];
        mapaVisivel = new char[TAMANHO][TAMANHO];
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                mapaInterno[i][j] = 'V';
                mapaVisivel[i][j] = 'O';
            }
        }
    }

    public void configurarFrota() {
        posicionarVarios(new PortaAvioes(), 2);
        posicionarVarios(new Destroyer(), 3);
        posicionarVarios(new Submarino(), 4);
        posicionarVarios(new Fragata(), 5);
        posicionarVarios(new Bote(), 6);
    }

    private void posicionarVarios(Navio tipo, int qtd) {
        Random r = new Random();
        for (int i = 0; i < qtd; i++) {
            boolean posicionado = false;
            while (!posicionado) {
                int l = r.nextInt(TAMANHO), c = r.nextInt(TAMANHO), dir = r.nextInt(3);
                if (podePosicionar(l, c, tipo.getTamanho(), dir)) {
                    for (int j = 0; j < tipo.getTamanho(); j++) {
                        int nl = l, nc = c;
                        if (dir == 0) nc += j; else if (dir == 1) nl += j; else { nl += j; nc += j; }
                        mapaInterno[nl][nc] = tipo.getSimbolo();
                    }
                    posicionado = true;
                }
            }
        }
    }

    private boolean podePosicionar(int l, int c, int tam, int dir) {
        for (int i = 0; i < tam; i++) {
            int nl = l, nc = c;
            if (dir == 0) nc += i; else if (dir == 1) nl += i; else { nl += i; nc += i; }
              
            if (nl < 0 || nl >= TAMANHO || nc < 0 || nc >= TAMANHO || mapaInterno[nl][nc] != 'V') return false;
            
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    int al = nl + x, ac = nc + y;
                    if (al >= 0 && al < TAMANHO && ac >= 0 && ac < TAMANHO && mapaInterno[al][ac] != 'V') return false;
                }
            }
        }
        return true;
    }

    public boolean jaFoiAtacado(int l, int c) {
        return mapaVisivel[l][c] == '~' || mapaVisivel[l][c] == 'X' || mapaVisivel[l][c] == 'Y' || mapaVisivel[l][c] == '#';
    }

   public boolean receberTiro(int l, int c, boolean ataqueDoJogador) {
    if (mapaInterno[l][c] != 'V' && mapaInterno[l][c] != 'X') {
        char simboloOriginal = mapaInterno[l][c];

        afundarNavioEspecifico(l, c, simboloOriginal);

        mapaVisivel[l][c] = ataqueDoJogador ? 'X' : 'Y';
        
        return true;
    }
    mapaVisivel[l][c] = '~';
    return false;
}

    public void exibir(boolean esconderNavios) {
        System.out.println("   00 01 02 03 04 05 06 07 08 09 10 11 12 13 14 15 16 17 18 19");
        for (int i = 0; i < TAMANHO; i++) {
            System.out.printf("%02d ", i);
            for (int j = 0; j < TAMANHO; j++) {
                System.out.print(mapaVisivel[i][j] + "  ");
            }
            System.out.println();
        }
    }

    private void afundarNavioEspecifico(int l, int c, char simbolo) {
        if (l < 0 || l >= TAMANHO || c < 0 || c >= TAMANHO || mapaInterno[l][c] != simbolo) {
            return;
        }

        mapaInterno[l][c] = 'X';

        mapaVisivel[l][c] = '#';

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                if (x != 0 || y != 0) {
                    afundarNavioEspecifico(l + x, c + y, simbolo);
                }
            }
        }
}
}