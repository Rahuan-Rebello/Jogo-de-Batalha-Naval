import java.util.Scanner;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int opcao = -1;

        while(true) {
            try {
                System.out.println("1 - Iniciar Nova Guerra");
                System.out.println("2 - Carregar Jogo Salvo");
                System.out.print("Opção: ");
                opcao = sc.nextInt();
                sc.nextLine();
                if (opcao == 1 || opcao == 2) break;
                else {System.out.println("Digite uma opção válida!");}
            }
            catch (Exception e) {
                System.out.println("Digite apenas 1 ou 2 para selecionar uma opção!");
                sc.nextLine();
            }
        }

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