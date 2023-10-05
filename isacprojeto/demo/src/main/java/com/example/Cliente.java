package com.example;

public class Cliente {
    private double saldo;
    private String nome;

    public Cliente(double saldo, String nome){
        this.saldo = saldo;
        this.nome = nome;
    }

    public void adicionarSaldo(double valor){
        saldo = valor + saldo;
    }

    public void atualizarSaldo(){
        saldo = saldo - Carrinho.getTotalCarrinho();
    }

    public String imprimirSaldo(){
        return("Saldo atual: "+saldo+ " R$");
    }

    public double getSaldo(){
        return saldo;
    }

    public String getNome() {
        return nome;
    }
   
}
