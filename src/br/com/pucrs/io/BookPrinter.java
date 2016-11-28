package br.com.pucrs.io;

import br.com.pucrs.collections.GeneralTree;
import br.com.pucrs.model.PageOfBook;

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
    private boolean fristChapter = true;
    private StringBuilder sumario = new StringBuilder("SUMÁRIO\n");

    public void print(GeneralTree<PageOfBook> tree, Path path) throws IOException {
        List<PageOfBook> positions = tree.positionsPre();
        if (!tree.isEmpty()) {
            try (PrintWriter printWriter = new PrintWriter(Files.newBufferedWriter(path))) {

                PageOfBook cover = positions.remove(0);
                printCover(cover, printWriter);

                for (PageOfBook position : positions) {
                    switch (position.getType()) {
                        case "C":
                            printChapter(position, printWriter);
                            break;
                        case "S":
                            printSection(position, printWriter);
                            break;
                        case "SS":
                            printSubSection(position, printWriter);
                            break;
                        case "P":
                            printParagraph(position, printWriter);
                            break;
                    }


                }

                printPage(printWriter);
                printWriter.print(sumario.toString());

            } catch (IOException e) {
                throw new IOException(e.getMessage(), e);
            }
        }

    }

    private void printParagraph(PageOfBook position, PrintWriter printWriter) {
        int paragrafos = Integer.parseInt(position.getContent().trim());
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

            if (isLastLine()) {
                printPage(printWriter);
            }
            paragrafos -= nroParagrafos;
        }
    }

    private void printSection(PageOfBook position, PrintWriter printWriter) {
        nroSecoes++;
        nroLinhas++;
        printWriter.println(String.format("%2d  %d.%d %s",
                nroLinhas, nroCapitulos, nroSecoes, position.getContent()));
        String format = String.format("  %d.%d %s",
                nroCapitulos, nroSecoes, position.getContent());

        sumario.append(fillDots(format));

        if (isLastLine()) {
            printPage(printWriter);
        }

    }

    private String fillDots(String format) {
        StringBuilder builder = new StringBuilder(format);
        if (builder.length() < 57) {
            for (int i = builder.length(); i <= 57; i++) {
                builder.append(".");
            }
        }
        builder.append(" ");
        builder.append(nroPaginas + 1);
        builder.append("\n");
        return builder.substring(0 );
    }

    private boolean isLastLine() {
        return nroLinhas == 15;
    }

    private void printSubSection(PageOfBook position, PrintWriter printWriter) {
        nroSubSecoes++;
        nroLinhas++;
        printWriter.println(String.format("%2d  %d.%d.%d %s",
                nroLinhas, nroCapitulos, nroSecoes, nroSubSecoes, position.getContent()));
        String format = String.format("   %d.%d.%d %s",
                nroCapitulos, nroSecoes, nroSubSecoes, position.getContent());
        sumario.append(fillDots(format));


        if (isLastLine()) {
            printPage(printWriter);
        }

    }

    private void printChapter(PageOfBook position, PrintWriter printWriter) {
        if (!fristChapter) {
            printPage(printWriter);
        }
        fristChapter = false;
        nroCapitulos++;
        nroLinhas++;
        nroSecoes = 0;
        nroSubSecoes = 0;
        printWriter.println(String.format("%2d  %d. %s",
                nroLinhas, nroCapitulos, position.getContent()));
        String format = String.format("%d. %s",
                nroCapitulos, position.getContent());
        sumario.append(fillDots(format));
    }

    private void printPage(PrintWriter printWriter) {
        nroPaginas++;
        nroLinhas = 0;
        printWriter.println(String.format("------------------------------ Pg. %d", nroPaginas));
    }

    private static void printCover(PageOfBook cover, PrintWriter printWriter) {
        printWriter.println("------------------------------------- ");
        for (int i = 1; i <= NRO_DE_LINHAS_POR_PAGINAS; i++) {
            if (i == 7) {
                printWriter.println(i + "            " + cover.getContent());
            } else {
                printWriter.println(i);
            }
        }
        printWriter.println("------------------------------ Capa");
    }


}
