package com.example;

public class Carrinho {

    private String nome;
    private double preco_unitario;
    private static double totalCarrinho = 0;

    public Carrinho(String nome, double preco_unitario) {
        this.nome = nome;
        this.preco_unitario = preco_unitario;
    }

    public static void CalcularCarrinho(double valor) {
        totalCarrinho = totalCarrinho + valor;
    }

    public static void atualizarCarrinho() {
        Loja.listaCarrinho.clear();
    }

    public static double getTotalCarrinho() {
        return totalCarrinho;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", Preço Unitário: " + preco_unitario;
    }

}
