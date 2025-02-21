package org.edd.view.choices;

public class ComplexQueryChoice {
    public static void main(String[] args) {
        System.out.println("\nEscolha o tipo de busca:");
        System.out.println("1. Busca com operador (AND/OR)");
        System.out.println("2. Busca por valor exato ou intervalo");
        System.out.println("3. Sair");
    }

    public static void operatorMenu() {
        System.out.println("\nEscolha o operador:");
        System.out.println("1. AND (E)");
        System.out.println("2. OR (OU)");
    }

    public static void criteriaMenu() {
        System.out.println("\nEscolha o tipo de busca:");
        System.out.println("1. Busca por valor exato");
        System.out.println("2. Busca por intervalo de data de nascimento");
        System.out.println("3. Finalizar busca");
    }

    public static void fieldMenu() {
        System.out.println("\nEscolha o campo para busca:");
        System.out.println("1. Nome");
        System.out.println("2. CPF");
        System.out.println("3. Email");
    }
} 