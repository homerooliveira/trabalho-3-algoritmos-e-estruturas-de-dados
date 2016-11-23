package br.com.pucrs.io;

import br.com.pucrs.collections.GeneralTree;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class BookPrinter {

    private static final int NRO_DE_LINHAS_POR_PAGINAS = 15;
    private int nroPaginas = 0;
    private int nroCapitulos = 0;
    private int nroSecoes = 0;
    private int nroSubSecoes = 0;
    private int nroLinhas = 0;
    private boolean capitloImpresso = false;
    private StringBuilder sumario = new StringBuilder("SUMÁRIO\n");

    public void print(GeneralTree<String> tree, Path path) throws IOException {
        List<String> positions = tree.positionsPre();
        if (!tree.isEmpty()) {
            try (PrintWriter printWriter = new PrintWriter(Files.newBufferedWriter(path))) {

                String cover = positions.remove(0);
                printCover(cover, printWriter);

                for (String position : positions) {
                    if (position.startsWith("C")) {
                        printChapter(position, printWriter);
                    } else if (position.substring(0, 2).equals("SS")) {
                        printSubSection(position, printWriter);
                    } else if (position.startsWith("S")) {
                        printSection(position, printWriter);
                    } else if (position.startsWith("P")) {
                        printParagraph(position, printWriter);
                    }

                    if (nroLinhas == 15) {
                        printPage(printWriter);
                    }

                }

                printPage(printWriter);
                printWriter.println(sumario.toString());

            } catch (IOException e) {
                throw new IOException(e.getMessage(), e);
            }
        }

    }

    private void printParagraph(String position, PrintWriter printWriter) {
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
                printWriter.println(String.format("%2d  Lorem Ipsum %d", nroLinhas, paragrafoAtual));
            }

            if (nroLinhas == 15) {
                printPage(printWriter);
            }
            paragrafos -= nroParagrafos;
        }
    }

    private void printSection(String position, PrintWriter printWriter) {
        nroSecoes++;
        nroLinhas++;
        printWriter.println(String.format("%2d  %d.%d %s", nroLinhas, nroCapitulos, nroSecoes, position));
        sumario.append(String.format("  %d.%d %s %d\n",
                nroCapitulos, nroSecoes, position, nroPaginas + 1));
    }

    private void printSubSection(String position, PrintWriter printWriter) {
        nroSubSecoes++;
        nroLinhas++;
        printWriter.println(String.format("%2d  %d.%d.%d %s",
                nroLinhas, nroCapitulos, nroSecoes, nroSubSecoes, position));
        sumario.append(String.format("   %d.%d.%d %s %d\n",
                nroCapitulos, nroSecoes, nroSubSecoes, position, nroPaginas + 1));
    }

    private void printChapter(String position, PrintWriter printWriter) {
        if (capitloImpresso) {
            printPage(printWriter);
        }
        capitloImpresso = true;
        nroCapitulos++;
        nroLinhas++;
        printWriter.println(String.format("%2d  %d. %s", nroLinhas, nroCapitulos, position));
        sumario.append(String.format("%d. %s %d\n", nroCapitulos, position, nroPaginas + 1));
    }

    private void printPage(PrintWriter printWriter) {
        nroPaginas++;
        nroLinhas = 0;
        nroSecoes = 0;
        nroSubSecoes = 0;
        printWriter.println(String.format("------------------------------ Pg. %d", nroPaginas));
    }

    private static void printCover(String position, PrintWriter printWriter) {
        printWriter.println("------------------------------------- ");
        for (int i = 1; i <= NRO_DE_LINHAS_POR_PAGINAS; i++) {
            if (i == 7) {
                printWriter.println(i + "            " + position);
            } else {
                printWriter.println(i);
            }
        }
        printWriter.println("------------------------------ Capa");
    }


}
