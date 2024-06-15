package back;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import cores.Cor;
import cores.StringColorida;
import static back.tabuleiro.*;

//ChatGPT foi usado aqui
public class save{

    private static String FILE_NAME = "save.txt";

    static carta aux ;

    public  void saveContent(tabuleiro partida) {

        // Obter o caminho da área de trabalho
        String userHome = System.getProperty("user.home");
        String desktopPath = userHome + File.separator + "Desktop";

        //adiciona os principais do jogo
        List<String> content0 = new ArrayList<>();
        content0.add(partida.vez);
        content0.add(String.valueOf(partida.primeiroLance));
        content0.add(String.valueOf(partida.pecaDeJogoDir));
        content0.add(String.valueOf(partida.pecaDeJogoEsq));

        //adiciona o baralho do jogo
        List<String> contentbaralho = new ArrayList<>();
        assert aux!= null;

        for(int i = 1;i < partida.baralho.length; i++) {
            aux = partida.baralho[i];
            if (aux != null) {
                contentbaralho.add(String.valueOf((aux.valorD())));
                contentbaralho.add(String.valueOf((aux.valorE())));
            }
        }
        //adiciona a mao do jogador do jogo
        List<String> contentmao = new ArrayList<>();
        assert false;
        for(int i = 1;i < partida.maoJogador.length; i++) {
            aux =partida.maoJogador[i];
            contentmao.add(String.valueOf(aux.numDireita));
            contentmao.add(String.valueOf(aux.numEsquerda));
        }
        //adiciona o baralho do jogo
        List<String> contentMaoBot = new ArrayList<>();
        assert false;
        for(int i = 1;i < partida.maoBot.length; i++) {
            aux = partida.maoBot[i];
            contentMaoBot.add(String.valueOf(aux.numDireita));
            contentMaoBot.add(String.valueOf(aux.numEsquerda));
        }

        List<String> contents = new ArrayList<>(content0);
        contents.addAll(contentbaralho);
        contents.addAll(contentmao);
        contents.addAll(contentMaoBot);

        // Criar o arquivo
        File file = new File(desktopPath, FILE_NAME);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            for (String line : contents) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ocorreu um erro ao criar o arquivo: " + e.getMessage());
        }
    }

    // Função para ler o conteúdo do arquivo
    public List<List<Object>> readContent(tabuleiro partida) {
        List<List<Object>> segmentedContent = new ArrayList<>();

        // Obter o caminho da área de trabalho
        String userHome = System.getProperty("user.home");
        String desktopPath = userHome + File.separator + "Desktop";

        // Verificar se há arquivos alternativos e permitir ao usuário escolher qual ler
        List<String> fileNames = new ArrayList<>();
        int fileCount = 0;
        File desktop = new File(desktopPath);
        for (File file : Objects.requireNonNull(desktop.listFiles())) {
            if (file.getName().startsWith(FILE_NAME.replace(".txt", ""))) {
                fileNames.add(file.getName());
                fileCount++;
            }
        }

        if (fileCount > 1) {
            System.out.println("Foram encontrados arquivos alternativos:");
            for (int i = 0; i < fileCount; i++) {
                System.out.println((i + 1) + ". " + fileNames.get(i));
            }
            Scanner scanner = new Scanner(System.in);
            System.out.print("Escolha o número do arquivo que deseja ler: ");
            int choice = scanner.nextInt();
            FILE_NAME = fileNames.get(choice - 1);
            scanner.close();
        }

        // Ler o arquivo
        File file = new File(desktopPath, FILE_NAME);

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                List<Object> currentContent = new ArrayList<>();

                while ((line = reader.readLine()) != null) {
                    // Adiciona a linha ao conteúdo atual
                    if (line.trim().isEmpty()) {
                        currentContent.add("");
                    } else {
                        // Verifica se a linha contém apenas números e converte para inteiro se necessário
                        try {
                            int number = Integer.parseInt(line);
                            currentContent.add(number);
                        } catch (NumberFormatException e) {
                            // Se não for um número, adiciona como string
                            currentContent.add(line);
                        }
                    }

                    // Cada linha é um conteúdo separado
                    segmentedContent.add(new ArrayList<>(currentContent));
                    currentContent.clear();
                }

                // Adiciona o último conteúdo se houver
                if (!currentContent.isEmpty()) {
                    segmentedContent.add(currentContent);
                }

            } catch (IOException e) {
                System.err.println("Ocorreu um erro ao ler o arquivo: " + e.getMessage());
            }
        } else {
            System.out.println("Arquivo '" + FILE_NAME + "' não encontrado.");
        }

        partida.vez = segmentedContent.get(0).toString();
        partida.primeiroLance = Integer.parseInt(segmentedContent.get(1).toString());
        partida.pecaDeJogoDir = Integer.parseInt(segmentedContent.get(2).toString());
        partida.pecaDeJogoEsq = Integer.parseInt(segmentedContent.get(3).toString());
        int aux = 0;
        for(int i = 4; i <= 32;i = i +2) {
            int x = Integer.parseInt(segmentedContent.get(i).toString());
            int y = Integer.parseInt(segmentedContent.get(i +1).toString());
            StringColorida frente = new StringColorida(x + "" + y, Cor.VERDE, Cor.FUNDO_BRANCO);
            partida.baralho[aux] = new carta(frente, partida.verso, false);
        }
        for(int i = 33; i <= 47;i = i +2) {
            int x = Integer.parseInt(segmentedContent.get(i).toString());
            int y = Integer.parseInt(segmentedContent.get(i +1).toString());
            StringColorida frente = new StringColorida(x + "" + y, Cor.VERDE, Cor.FUNDO_BRANCO);
            partida.maoJogador[aux] = new carta(frente, partida.verso, true);
        }
        for(int i = 48; i <= 62;i = i +2) {
            int x = Integer.parseInt(segmentedContent.get(i).toString());
            int y = Integer.parseInt(segmentedContent.get(i +1).toString());
            StringColorida frente = new StringColorida(x + "" + y, Cor.VERDE, Cor.FUNDO_BRANCO);
            partida.maoBot[aux] = new carta(frente, partida.verso, false);
        }


        return segmentedContent;
    }
}
