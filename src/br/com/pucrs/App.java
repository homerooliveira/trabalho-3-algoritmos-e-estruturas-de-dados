package br.com.pucrs;

import javafx.print.Printer;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class App {
    private static final String EMPTY = "";
    private static final int NRO_DE_LINHAS_POR_PAGINAS = 15;

    public static void main(String[] args) {
        GeneralTree<String> tree = readFileAndCreateTree();
        tree.positionsPre().forEach(System.out::println);
        printBook(tree);

    }

    private static void printBook(GeneralTree<String> tree) {
        List<String> positions = tree.positionsPre();
        try (PrintWriter printWriter = new PrintWriter(Files.newBufferedWriter(Paths.get("livro_prod.txt")))) {


            if (!positions.isEmpty()) {
                String cover = positions.remove(0);
                printCover(cover);
            }

            int nroPaginas = 0;
            int nroCapitulos = 0;
            int nroSecoes = 0;
            int nroSubSecoes = 0;
            int nroLinhas = 0;
            boolean capitloImpresso = false;
            StringBuilder sumario = new StringBuilder("SUMÁRIO\n");

            for (int i = 0; i < positions.size(); i++) {
                String position = positions.get(i);

                if (position.startsWith("C")) {
                    if (capitloImpresso) {
                        nroPaginas++;
                        System.out.println(String.format("------------------------------ Pg. %d", nroPaginas));
                        nroLinhas = 0;
                        nroSecoes = 0;
                        nroSubSecoes = 0;
                    }
                    capitloImpresso = true;
                    nroCapitulos++;
                    nroLinhas++;
                    System.out.println(String.format("%2d  %d. %s", nroLinhas, nroCapitulos, position));
                    sumario.append(String.format("%d. %s %d\n", nroCapitulos, position, nroPaginas + 1));
                } else if (position.substring(0, 2).equals("SS")) {
                    nroSubSecoes++;
                    nroLinhas++;
                    System.out.println(String.format("%2d  %d.%d.%d %s",
                            nroLinhas, nroCapitulos, nroSecoes, nroSubSecoes, position));
                    sumario.append(String.format("   %d.%d.%d %s %d\n",
                            nroCapitulos, nroSecoes, nroSubSecoes, position, nroPaginas + 1));
                } else if (position.startsWith("S")) {
                    nroSecoes++;
                    nroLinhas++;
                    System.out.println(String.format("%2d  %d.%d %s", nroLinhas, nroCapitulos, nroSecoes, position));
                    sumario.append(String.format("  %d.%d %s %d\n",
                            nroCapitulos, nroSecoes, position, nroPaginas + 1));
                } else if (position.startsWith("P")) {
                    int paragrafos = Integer.parseInt(position.substring(2));
                    int paragrafoAtual = 0;

                    while (paragrafos > 0) {
                        int nroParagrafos = NRO_DE_LINHAS_POR_PAGINAS - nroLinhas;
                        if (paragrafos < nroParagrafos) {
                            nroParagrafos = paragrafos;
                        }

                        for (int j = 0; j < nroParagrafos; j++) {
                            nroLinhas++;
                            paragrafoAtual++;
                            System.out.println(String.format("%2d  Lorem Ipsum %d", nroLinhas, paragrafoAtual));
                        }

                        if (nroLinhas == 15) {
                            nroPaginas++;
                            System.out.println(String.format("------------------------------ Pg. %d", nroPaginas));
                            nroLinhas = 0;
                        }
                        paragrafos -= nroParagrafos;
                    }

                }

                if (nroLinhas == 15) {
                    nroPaginas++;
                    System.out.println(String.format("------------------------------ Pg. %d", nroPaginas));
                    nroLinhas = 0;
                }

            }
            nroPaginas++;
            System.out.println(String.format("------------------------------ Pg. %d", nroPaginas));
            System.out.println(sumario.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void printCover(String position) {
        System.out.println("------------------------------------- ");
        for (int i = 1; i <= NRO_DE_LINHAS_POR_PAGINAS; i++) {
            if (i == 7) {
                System.out.println(i + "            " + position);
            } else {
                System.out.println(i);
            }
        }
        System.out.println("------------------------------ Capa");
    }

    private static GeneralTree<String> readFileAndCreateTree() {
        GeneralTree<String> tree = new GeneralTree<>();
        String capituloAtual = EMPTY;
        String secaoAtual = EMPTY;
        String subSecaoAtual = EMPTY;

        try (Scanner scanner = new Scanner(Files.newBufferedReader(Paths.get("livro.txt")))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String tipo = line.substring(0, 2).trim();
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
                        if (!subSecaoAtual.isEmpty()) {
                            tree.add(line, subSecaoAtual);
                        } else if (!secaoAtual.isEmpty()) {
                            tree.add(line, secaoAtual);
                        } else {
                            tree.add(line, capituloAtual);
                        }
                        break;
                    default:
                        //não faz nada
                        break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        return tree;
    }

}
