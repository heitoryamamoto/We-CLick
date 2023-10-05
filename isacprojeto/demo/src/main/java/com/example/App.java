package com.example;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;
import com.opencsv.CSVWriter;

public class App {
    private static final String URL = "jdbc:postgresql://localhost:5432/oficial";
    private static final String USER = "postgres";
    private static final String SENHA = "12345678";

    public static void main(String[] args) {
        int menu = 0;
        int id_produto = 0;
        double saldo = 0;
        int escolha = 0;
        Connection conexao = null;

        try {
            conexao = DriverManager.getConnection(URL, USER, SENHA);
            if (conexao != null) {
                System.out.println("Conexão com banco dados bem sucedida");
            }

            String nome = JOptionPane.showInputDialog("##### Cadastro ##### \n Nome:");
            if (nome.isBlank()) {
                return;
            }

            Cliente cliente = new Cliente(0, nome);
            while (menu != 7) {
                menu = Integer.parseInt(JOptionPane.showInputDialog(Menu(cliente.getNome())));
                switch (menu) {
                    // Adicionar no carrinho
                    case 1:
                        Limpar();
                        List<Estoque> listaEstoque = Loja.listarEstoque(conexao);
                        System.out.println(cliente.imprimirSaldo());
                        for (Estoque item : listaEstoque) {
                            System.out.println(item);
                        }

                        id_produto = Integer.parseInt(JOptionPane.showInputDialog("Escolha o item pelo id"));
                        if (id_produto <= 5 && id_produto > 0) {
                            Loja.adicionarCarrinho(id_produto, conexao);
                        } else {
                            JOptionPane.showMessageDialog(null, "Insira um ID válido", "ERRO",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        Limpar();
                        break;

                    // Adicionar saldo
                    case 2:
                        Limpar();
                        saldo = Double.parseDouble(JOptionPane.showInputDialog("Adicionar Saldo"));
                        cliente.adicionarSaldo(saldo);
                        JOptionPane.showMessageDialog(null, "Saldo adicionado\n\n" + cliente.imprimirSaldo() + "");
                        System.out.println(cliente.imprimirSaldo());
                        break;

                    // Verificar carrinho
                    case 3:
                        String itemCarrinho = "";
                        Limpar();
                        System.out.println("Valor carrinho: " + Carrinho.getTotalCarrinho());
                        for (Carrinho item : Loja.listaCarrinho) {
                            itemCarrinho += item + "\n";
                        }
                        JOptionPane.showMessageDialog(null,
                                "" + cliente.imprimirSaldo() + "\n" +"Valor carrinho: "+ Carrinho.getTotalCarrinho()+ " R$\n\n" + "" + itemCarrinho + "");
                        break;

                    // Confimar compra
                    case 4:
                        if (Loja.listaCarrinho.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Você não possui nada no carrinho", "ERRO",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            escolha = JOptionPane.showConfirmDialog(null, "Deseja comprar?:", "Confirmação compra",
                                    JOptionPane.YES_NO_OPTION);
                            if (escolha == JOptionPane.YES_OPTION && cliente.getSaldo() > Carrinho.getTotalCarrinho()) {
                                JOptionPane.showMessageDialog(null, "Compra confirmada");
                                cliente.atualizarSaldo();
                                Carrinho.atualizarCarrinho();
                                System.out.println(cliente.imprimirSaldo());
                            } else {
                                JOptionPane.showMessageDialog(null, "Seu saldo é insuficiente", "ERRO",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        break;
                    // informações empresa
                    case 5:
                        String empresa = "";
                        Limpar();
                        List<Empresa> listaEmpresa = Loja.listarInformacoes(conexao);
                        for (Empresa item : listaEmpresa) {
                            empresa += item + "\n";
                        }
                        JOptionPane.showMessageDialog(null,
                                "" + empresa + "");
                        break;
                    //Exportar venda
                    case 6:
                        Limpar();
                        int resposta = JOptionPane.showConfirmDialog(null, "Deseja exportar as vendas?",
                                "Exportação CSV", JOptionPane.YES_NO_OPTION);
                        if (resposta == JOptionPane.YES_OPTION) {
                            try (CSVWriter writer = new CSVWriter(new FileWriter("Arquivo_expo.csv"))) {
                                writer.writeAll(Loja.listaExportacao);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Menu(cliente.getNome());
                        }

                        break;
                    //Final
                    case 7:
                        Limpar();
                        JOptionPane.showMessageDialog(null, "##### We Click #####\n\nObrigado pela visita, "+cliente.getNome()+". Volte sempre");
                    default:
                        break;
                }
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        } 
        finally {
            if (conexao != null) {
                try {
                    conexao.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    static void Limpar() {
        for (int i = 0; i < 50; i++) {
            System.out.println("");
        }
    }

    static String Menu(String user) {
        return ("##### We Click #####\nUsuário: " + user + " \nEscolha uma das opções:\n" +
                "1 - Iniciar compra \n2 - Adicionar saldo\n3 - Ver carrinho\n4 - Comprar\n" +
                "5 - Informações Empresa\n6 - Exportas Vendas \n7 - Sair da loja");
    }
}
