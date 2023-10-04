package com.example;

public class Empresa {
    private String nome;
    private String endereco;
    private String donos;
    private String ramo;
    private String contato;

    public Empresa(String nome, String endereco, String donos, String ramo, String contato){
        this.nome = nome; 
        this.endereco = endereco;
        this.donos = donos;
        this.ramo = ramo;
        this.contato = contato;
    }

    @Override
    public String toString() {
        return ("Nome: " + nome + "\n" +
                "Endereço: " + endereco + "\n" +
                "Donos: " + donos + "\n" +
                "Ramo: " + ramo + "\n" +
                "Contato: " + contato);
    }
}
