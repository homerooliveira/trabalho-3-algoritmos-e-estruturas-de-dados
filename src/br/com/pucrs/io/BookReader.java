package br.com.pucrs.io;

import br.com.pucrs.collections.GeneralTree;
import br.com.pucrs.model.PageBook;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class BookReader {
    private PageBook capituloAtual;
    private PageBook secaoAtual;
    private PageBook subSecaoAtual;
    private int nroCapitulos;
    private int nroSecoes;
    private int nroSubSecoes;
    private int nroParagrafos;


    public BookReader() {
        capituloAtual = null;
        secaoAtual = null;
        subSecaoAtual = null;
        nroCapitulos = 0;
        nroSecoes = 0;
        nroSubSecoes = 0;
        nroParagrafos = 0;
    }

    public GeneralTree<PageBook> readFile(Path path) throws IOException {
        GeneralTree<PageBook> tree = new GeneralTree<>();

        try (Scanner scanner = new Scanner(Files.newBufferedReader(path))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String tipo = line.substring(0, 2).trim();
                PageBook page = new PageBook(line.substring(2), tipo);
                switch (tipo) {
                    case "L":
                        tree.add(page, null);
                        break;
                    case "C":
                        addChapter(tree, page);
                        break;
                    case "S":
                        addSection(tree, page);
                        break;
                    case "SS":
                        addSubSection(tree, page);
                        break;
                    case "P":
                        addParagraph(tree, page);
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
        capituloAtual = null;
        secaoAtual = null;
        subSecaoAtual = null;
        nroCapitulos = 0;
        nroSecoes = 0;
        nroSubSecoes = 0;
        nroParagrafos = 0;
    }

    private void addSubSection(GeneralTree<PageBook> tree, PageBook page) {
        subSecaoAtual = page;
        tree.add(subSecaoAtual, secaoAtual);
        nroSubSecoes++;
    }

    private void addParagraph(GeneralTree<PageBook> tree, PageBook page) {
        if (subSecaoAtual != null) {
            tree.add(page, subSecaoAtual);
        } else if (secaoAtual != null) {
            tree.add(page, secaoAtual);
        } else {
            tree.add(page, capituloAtual);
        }
        nroParagrafos++;
    }

    private void addSection(GeneralTree<PageBook> tree, PageBook page) {
        subSecaoAtual = null;
        secaoAtual = page;
        tree.add(secaoAtual, capituloAtual);
        nroSecoes++;
    }

    private void addChapter(GeneralTree<PageBook> tree, PageBook line) {
        if (capituloAtual != null) {
            secaoAtual = null;
            subSecaoAtual = null;
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
