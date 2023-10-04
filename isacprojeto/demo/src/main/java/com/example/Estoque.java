package com.example;

public class Estoque {
    private int id_produto;
    private String nome;
    private String descricao;
    private double preco_unitario;
    private int quantidade_estoque;
    private double valor_estoque;

    public int getQuantidade_estoque() {
        return quantidade_estoque;
    }

    public double getValor_estoque() {
        return valor_estoque;
    }
    
    public Estoque(){

    }
    public Estoque(int id_produto, String nome, String descricao, double preco_unitario, int quantidade_estoque, double valor_estoque){
        this.id_produto = id_produto;
        this.nome = nome;
        this.descricao = descricao;
        this.preco_unitario = preco_unitario;
        this.quantidade_estoque = quantidade_estoque;
        this.valor_estoque = valor_estoque;
    }

    @Override
    public String toString() {
        return "ID: " + id_produto + ", Nome: " + nome + ", Descrição: " + descricao +
               ", Preço Unitário: " + preco_unitario + ", Quantidade em Estoque: " + quantidade_estoque +
               ", Valor em Estoque: " + valor_estoque;
    }
}
