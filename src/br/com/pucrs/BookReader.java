package br.com.pucrs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class BookReader {
    private static final String EMPTY = "";
    private String capituloAtual;
    private String secaoAtual;
    private String subSecaoAtual;

    public BookReader() {
        capituloAtual = EMPTY;
        secaoAtual = EMPTY;
        subSecaoAtual = EMPTY;

    }

    public GeneralTree<String> readFile(Path path) throws IOException {
        GeneralTree<String> tree = new GeneralTree<>();

        try (Scanner scanner = new Scanner(Files.newBufferedReader(path))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String tipo = line.substring(0, 2).trim();
                switch (tipo) {
                    case "L":
                        tree.add(line, null);
                        break;
                    case "C":
                        readChapter(tree, line);
                        break;
                    case "S":
                        readSection(tree, line);
                        break;
                    case "SS":
                        readSubSection(tree, line);
                        break;
                    case "P":
                        readParagraph(tree, line);
                        break;
                    default:
                        //não faz nada
                        break;
                }

            }
        } catch (IOException e) {
            throw new IOException(e.getMessage(), e);

        }
        return tree;
    }

    private void readSubSection(GeneralTree<String> tree, String line) {
        subSecaoAtual = line;
        tree.add(line, secaoAtual);
    }

    private void readParagraph(GeneralTree<String> tree, String line) {
        if (!subSecaoAtual.isEmpty()) {
            tree.add(line, subSecaoAtual);
        } else if (!secaoAtual.isEmpty()) {
            tree.add(line, secaoAtual);
        } else {
            tree.add(line, capituloAtual);
        }
    }

    private void readSection(GeneralTree<String> tree, String line) {
        subSecaoAtual = EMPTY;
        secaoAtual = line;
        tree.add(line, capituloAtual);
    }

    private void readChapter(GeneralTree<String> tree, String line) {
        if (!(capituloAtual.isEmpty())) {
            secaoAtual = EMPTY;
            subSecaoAtual = EMPTY;
        }
        capituloAtual = line;
        tree.add(line, tree.getRoot());
    }
}
