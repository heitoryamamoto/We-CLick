package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;

/**
 * Hello world!
 *
 */

public class App {
    private static final String URL = "jdbc:postgresql://localhost:5432/oficial";
    private static final String USER = "postgres";
    private static final String SENHA = "12345678";

    public static void main(String[] args) {
        int menu = 0;
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
            while (menu != 6) {
                menu = Integer.parseInt(JOptionPane.showInputDialog(Menu(cliente.getNome())));
                switch (menu) {
                    case 1:
                        List<Estoque> estoque = Loja.listarEstoque(conexao);
                        System.out.println(cliente.imprimir());
                        for (Estoque item : estoque) {
                            System.out.println(item);
                        }
                        escolha = Integer.parseInt(JOptionPane.showInputDialog("Escolha o item pelo id"));
                        Loja.adicionarCarrinho(escolha, conexao);
                        Limpar();
                        break;
                    case 2:
                        Limpar();
                        escolha = Integer.parseInt(JOptionPane.showInputDialog("Adicionar Saldo"));
                        cliente.adicionarSaldo(escolha);
                        JOptionPane.showMessageDialog(null, "Saldo adicionado");
                        System.out.println(cliente.imprimir());
                        break;
                    case 3:
                        Limpar();
                        System.out.println(cliente.imprimir() + "\n");
                        System.out.println("Total: " + Carrinho.getTotal());
                        for (Carrinho item : Loja.listaCarrinho) {
                            System.out.println(item);
                        }
                        break;
                    case 4:
                        if (Loja.listaCarrinho.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Você não possui nada no carrinho", "ERRO",
                                    JOptionPane.ERROR_MESSAGE);
                        } else {
                            escolha = JOptionPane.showConfirmDialog(null, "Deseja comprar?:", "Tela de pagamento",
                                    JOptionPane.YES_NO_OPTION);
                            if (escolha == JOptionPane.YES_OPTION && cliente.getSaldo() > Carrinho.getTotal()) {
                                JOptionPane.showMessageDialog(null, "Compra confirmada");
                                cliente.atualizarSaldo();
                                Carrinho.atualizarCarrinho();
                                System.out.println(cliente.imprimir());
                            } else {
                                JOptionPane.showMessageDialog(null, "Seu saldo é insuficiente", "ERRO",
                                        JOptionPane.ERROR_MESSAGE);

                            }
                        }
                        break;
                    case 5:
                        Limpar();
                        List<Empresa> listaEmpresa = Loja.listarInformacoes(conexao);
                        for (Empresa item : listaEmpresa) {
                            System.out.println(item);
                        }
                        break;
                    case 6:
                        Limpar();
                        System.out.println("Obrigado pela compra");
                    default:
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
                "5 - Informações Empresa \n6 - Sair da loja");
    }
}
