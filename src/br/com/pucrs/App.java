package br.com.pucrs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class App {
    private static final String EMPTY = "";

    public static void main(String[] args) {
        GeneralTree<String> tree = new GeneralTree<>();
        String capituloAtual = EMPTY;
        String secaoAtual = EMPTY;
        String subSecaoAtual = EMPTY;

        try (Scanner scanner = new Scanner(Files.newBufferedReader(Paths.get("livro.txt")))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String tipo = line.substring(0, 2).trim();
                line = line.substring(2).trim();
                switch (tipo) {
                    case "L":
                        tree.add(line, null);
                        break;
                    case "C":
                        if (!(capituloAtual.isEmpty())) {
                            secaoAtual = EMPTY;
                            subSecaoAtual = EMPTY;
                        }
                        capituloAtual = line;
                        tree.add(line, tree.getRoot());
                        break;
                    case "S":
                        subSecaoAtual = EMPTY;
                        secaoAtual = line;
                        tree.add(line, capituloAtual);
                        break;
                    case "SS":
                        subSecaoAtual = line;
                        tree.add(line, secaoAtual);
                        break;
                    case "P":
                        if (!(capituloAtual.isEmpty())) {
                            if (!(secaoAtual.isEmpty())) {
                                if (!(subSecaoAtual.isEmpty())) {
                                    tree.add(line, subSecaoAtual);
                                } else {
                                    tree.add(line, secaoAtual);
                                }
                            } else {
                                tree.add(line, capituloAtual);
                            }
                        }
                        break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        tree.positionsPre().forEach(System.out::println);

    }

}
