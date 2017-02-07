package br.com.pucrs;

import br.com.pucrs.collections.GeneralTree;
import br.com.pucrs.io.BookPrinter;
import br.com.pucrs.io.BookReader;
import br.com.pucrs.model.ContentBook;

import java.io.IOException;
import java.nio.file.Paths;

public class App {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Número de argumentos inválido.");
            System.out.println("Sintaxe: trabalho-3-algoritmos-e-estruturas-de-dados.jar [arquivo_entrada] [arquivo_saida]");
            return;
        }

        BookReader reader = new BookReader();
        BookPrinter printer = new BookPrinter();
        GeneralTree<ContentBook> tree = new GeneralTree<>();

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
            System.out.println("Erro na leitura do arquivo " + args[0]);
        }

        try {
            printer.print(tree, Paths.get(args[1]));
            System.out.println("Gerando o sumário... ok");
            System.out.printf("Imprimindo o livro para o arquivo %s... ok.\n", args[1]);
        } catch (IOException e) {
            System.out.println("Erro na escrita do arquivo " + args[1]);
        }

    }

}
