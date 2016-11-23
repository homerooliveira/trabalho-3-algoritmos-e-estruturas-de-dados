package br.com.pucrs;

import br.com.pucrs.collections.GeneralTree;
import br.com.pucrs.io.BookPrinter;
import br.com.pucrs.io.BookReader;

import java.io.IOException;
import java.nio.file.Paths;

public class App {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.printf("Tem mais de dois argumentos!!");
            System.out.println("Sintaxe: trabalho-3-algoritmos-e-estruturas-de-dados.jar [arquivo_entrada] [arquivo_saida]");
        }

        BookReader reader = new BookReader();
        BookPrinter printer = new BookPrinter();
        GeneralTree<String> tree = new GeneralTree<>();

        try {
            tree = reader.readFile(Paths.get(args[0]));
            System.out.printf("Carregando arquivo %s ... ok\n", args[0]);
            System.out.println("Gerando a árvore... ok");
            System.out.println(" Capitulos...: " + reader.getNroCapitulos());
            System.out.println(" Seções......: " + reader.getNroSecoes());
            System.out.println(" Subseções...: " + reader.getNroSubSecoes());
            System.out.println(" Parágrafos..: " + reader.getNroParagrafos());
            reader.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            printer.print(tree, Paths.get(args[1]));
            System.out.println("Gerando o sumário... ok");
            System.out.printf("Imprimindo o livro para o arquivo %s... ok.\n", args[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
