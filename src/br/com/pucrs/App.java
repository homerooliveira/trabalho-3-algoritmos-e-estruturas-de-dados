package br.com.pucrs;

import java.io.IOException;
import java.nio.file.Paths;

public class App {
    private static final int NRO_DE_LINHAS_POR_PAGINAS = 15;

    public static void main(String[] args) {
        BookReader reader = new BookReader();
        BookPrinter printer = new BookPrinter();
        GeneralTree<String> tree = new GeneralTree<>();
        try {
            tree = reader.readFile(Paths.get("livro.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        tree.positionsPre().forEach(System.out::println);
        printer.print(tree);

    }

}
