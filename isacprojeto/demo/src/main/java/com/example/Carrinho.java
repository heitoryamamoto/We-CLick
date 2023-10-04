package com.example;

public class Carrinho {

    private String nome;
    private double preco_unitario;
    private static double total = 0;

    public Carrinho() {

    }

    public Carrinho(String nome, double preco_unitario) {
        this.nome = nome;
        this.preco_unitario = preco_unitario;
    }

    public static void CalcularCarrinho(double valor) {
        total = total + valor;
    }

    public static double getTotal() {
        return total;
    }

    public static void setTotal(double total) {
        Carrinho.total = total;
    }

    public static void atualizarCarrinho(){
        Loja.listaCarrinho.clear();
    }

    @Override
    public String toString() {

        return "Nome: " + nome + ", Preço Unitário: " + preco_unitario;
    }

}
