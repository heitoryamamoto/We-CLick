package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Loja {

    //Listar estoque
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

                Estoque estoque = new Estoque(id_produto, nome, descricao, preco_unitario, quantidade_estoque, valor_estoque);
                listaEstoque.add(estoque);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaEstoque;
    }

    static List<Carrinho> listaCarrinho = new ArrayList<>();

    public static void adicionarCarrinho(int id, Connection conexao) {
        try {
            String sql = "SELECT * FROM estoque WHERE id_produto = " + id + "";
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id_produto = resultSet.getInt("id_produto");
                String nome = resultSet.getString("nome");
                String descricao = resultSet.getString("descricao");
                double preco_unitario = resultSet.getDouble("preco_unitario");
                int quantidade_estoque = resultSet.getInt("quantidade_estoque");
                double valor_estoque = resultSet.getDouble("valor_estoque");

                Carrinho carrinho = new Carrinho(nome, preco_unitario);

                listaCarrinho.add(carrinho);

                Carrinho.CalcularCarrinho(preco_unitario);
                atualizarEstoque(id, quantidade_estoque, preco_unitario, conexao);
                armazenarExportacao(id_produto, nome, descricao, preco_unitario, quantidade_estoque, valor_estoque);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void atualizarEstoque(int id, int quantidade_estoque, double preco_unitario, Connection conexao) {
        int qtdeEstoque = quantidade_estoque - 1;
        double valorEstoque = qtdeEstoque * preco_unitario;
        try {
            String sql = "UPDATE estoque SET quantidade_estoque = ?, valor_estoque = ? WHERE id_produto = ?";
            PreparedStatement preparedStatement = conexao.prepareStatement(sql);

            preparedStatement.setInt(1, qtdeEstoque);
            preparedStatement.setDouble(2, valorEstoque);
            preparedStatement.setInt(3, id);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 1) {
                System.out.println("Linhas atualizadas com sucesso");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Empresa> listarInformacoes(Connection conexao) {
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
            e.printStackTrace();
        }
        return listaEmpresa;
    }

    static List<String[]> listaExportacao = new ArrayList<>();
    
    public static void armazenarExportacao(int id_produto,String nome,String descricao,double preco_unitario,int quantidade_estoque,double valor_estoque){
        String id = Integer.toString(id_produto);
        String preco_u = Double.toString(preco_unitario);
        String qtde_estoque = Integer.toString(quantidade_estoque);
        String valor_est = Double.toString(valor_estoque);

        //Adiciona produto no array
        String[] export = {id, nome, descricao, preco_u, qtde_estoque, valor_est};

        //Adiciona no List
        listaExportacao.add(export);
    }

}
