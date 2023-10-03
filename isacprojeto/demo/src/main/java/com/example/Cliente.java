package com.example;

public class Cliente {
    private double saldo;
    private String nome;

    public String getNome() {
        return nome;
    }

    public Cliente(double saldo, String nome){
        this.saldo = saldo;
        this.nome = nome;
    }

    public void adicionarSaldo(int valor){
        saldo = valor + saldo;
    }

    public void atualizarSaldo(){
        saldo = saldo - Carrinho.getTotal();
    }

    public String imprimir(){
        return("Saldo atual "+saldo+ " R$");
    }

    public double getSaldo(){
        return saldo;
    }
   
}
