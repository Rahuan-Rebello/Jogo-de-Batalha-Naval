import java.util.Scanner;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("1 - Iniciar Nova Guerra");
        System.out.println("2 - Carregar Jogo Salvo");
        System.out.print("Opção: ");
        int opcao = sc.nextInt();
        sc.nextLine();

        if (opcao == 2) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save_vs_bot.dat"))) {
                Jogo jogoSalvo = (Jogo) ois.readObject();
                System.out.println("Jogo carregado com sucesso!");
                jogoSalvo.iniciarPartida();
            } catch (Exception e) {
                System.out.println("Nenhum save encontrado. Comece um novo jogo.");
            }
        } else {
            System.out.print("Digite seu nome, Comandante: ");
            String nome = sc.nextLine();
            Jogo novoJogo = new Jogo(nome);
            novoJogo.iniciarPartida();
        }
    }
}