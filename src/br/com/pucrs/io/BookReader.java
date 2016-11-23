package br.com.pucrs.io;

import br.com.pucrs.collections.GeneralTree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class BookReader {
    private static final String EMPTY = "";
    private String capituloAtual;
    private String secaoAtual;
    private String subSecaoAtual;
    private int nroCapitulos = 0;
    private int nroSecoes = 0;
    private int nroSubSecoes = 0;
    private int nroParagrafos = 0;


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
                        addChapter(tree, line);
                        break;
                    case "S":
                        addSection(tree, line);
                        break;
                    case "SS":
                        addSubSection(tree, line);
                        break;
                    case "P":
                        addParagraph(tree, line);
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

    public void clear() {
        capituloAtual = EMPTY;
        secaoAtual = EMPTY;
        subSecaoAtual = EMPTY;
        nroCapitulos = 0;
        nroSecoes = 0;
        nroSubSecoes = 0;
    }

    private void addSubSection(GeneralTree<String> tree, String line) {
        subSecaoAtual = line;
        tree.add(line, secaoAtual);
        nroSubSecoes++;
    }

    private void addParagraph(GeneralTree<String> tree, String line) {
        if (!subSecaoAtual.isEmpty()) {
            tree.add(line, subSecaoAtual);
        } else if (!secaoAtual.isEmpty()) {
            tree.add(line, secaoAtual);
        } else {
            tree.add(line, capituloAtual);
        }
        nroParagrafos++;
    }

    private void addSection(GeneralTree<String> tree, String line) {
        subSecaoAtual = EMPTY;
        secaoAtual = line;
        tree.add(line, capituloAtual);
        nroSecoes++;
    }

    private void addChapter(GeneralTree<String> tree, String line) {
        if (!(capituloAtual.isEmpty())) {
            secaoAtual = EMPTY;
            subSecaoAtual = EMPTY;
        }
        capituloAtual = line;
        tree.add(line, tree.getRoot());
        nroCapitulos++;
    }

    public int getNroCapitulos() {
        return nroCapitulos;
    }

    public int getNroSecoes() {
        return nroSecoes;
    }

    public int getNroSubSecoes() {
        return nroSubSecoes;
    }

    public int getNroParagrafos() {
        return nroParagrafos;
    }
}
