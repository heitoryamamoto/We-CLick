package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Loja {
    public static List<Estoque> listarEstoque(Connection conexao) {
        List<Estoque> listaEstoque = new ArrayList<>();
        try {
            String sql = "SELECT * FROM estoque ORDER BY id_produto ASC ";
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id_produto = resultSet.getInt("id_produto");
                String nome = resultSet.getString("nome");
                String descricao = resultSet.getString("descricao");
                double preco_unitario = resultSet.getDouble("preco_unitario");
                int quantidade_estoque = resultSet.getInt("quantidade_estoque");
                double valor_estoque = resultSet.getDouble("valor_estoque");

                Estoque estoque = new Estoque(id_produto, nome, descricao, preco_unitario, quantidade_estoque,
                        valor_estoque);
                listaEstoque.add(estoque);
            }
        } catch (SQLException e) {

        }
        return listaEstoque;
    }

    static List<Carrinho> listaCarrinho = new ArrayList<>();

    public static void adicionarCarrinho(int escolha, Connection conexao) {
        try {
            String sql = "SELECT nome, preco_unitario, quantidade_estoque FROM estoque WHERE id_produto = " + escolha + "";
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String nome = resultSet.getString("nome");
                double preco_unitario = resultSet.getDouble("preco_unitario");
                int quantidade_estoque = resultSet.getInt("quantidade_estoque");

                Carrinho carrinho = new Carrinho(nome, preco_unitario);
                listaCarrinho.add(carrinho);
                Carrinho.CalcularCarrinho(preco_unitario);
                atualizarEstoque(escolha, quantidade_estoque, preco_unitario, conexao);

            }
        } catch (SQLException e) {

        }

    }

    public static void atualizarEstoque(int escolha, int quantidade_estoque, double preco_unitario,Connection conexao) {
        int qtdeEstoque = quantidade_estoque - 1;
        double valorEstoque = qtdeEstoque * preco_unitario;
        try {
            String sql = "UPDATE estoque SET quantidade_estoque = ?, valor_estoque = ? WHERE id_produto = ?";
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            
            preparedStatement.setInt(1, qtdeEstoque);
            preparedStatement.setDouble(2, valorEstoque);
            preparedStatement.setInt(3, escolha);

            int rowsUpdated = preparedStatement.executeUpdate();
        } catch (SQLException e) {

        }
    }

    public static List<Empresa> listarInformacoes(Connection conexao){
        List<Empresa> listaEmpresa = new ArrayList<>();

        try {
            String sql = "SELECT * FROM empresa";
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

             while (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String endereco = resultSet.getString("endereco");
                String donos = resultSet.getString("donos");
                String ramo = resultSet.getString("ramo");
                String contato = resultSet.getString("contato");

                Empresa empresa = new Empresa(nome, endereco, donos, ramo, contato);
                listaEmpresa.add(empresa);
            }
        } catch (SQLException e) {

        }
        return listaEmpresa;
    }
}
