package br.com.pucrs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class App {
    private static final int NRO_DE_LINHAS_POR_PAGINAS = 15;

    public static void main(String[] args) {
        GeneralTree<String> tree = new GeneralTree<>();
        BookReader reader = new BookReader();
        try {
            tree = reader.readFile(Paths.get("livro.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                        nroLinhas = 0;
                        nroSecoes = 0;
                        nroSubSecoes = 0;
                        System.out.println(String.format("------------------------------ Pg. %d", nroPaginas));
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
                            nroLinhas = 0;
                            nroSecoes = 0;
                            nroSubSecoes = 0;
                            System.out.println(String.format("------------------------------ Pg. %d", nroPaginas));
                        }
                        paragrafos -= nroParagrafos;
                    }

                }

                if (nroLinhas == 15) {
                    nroPaginas++;
                    nroLinhas = 0;
                    nroSecoes = 0;
                    nroSubSecoes = 0;
                    System.out.println(String.format("------------------------------ Pg. %d", nroPaginas));
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

}
