package org.edd.view.choices;

public class ComplexQueryChoice {
    public static void main(String[] args) {
        System.out.println("\nBusca Complexa - Você pode buscar por múltiplos critérios");
        System.out.println("Escolha o operador lógico:");
        System.out.println("1. AND (Todas as condições devem ser verdadeiras)");
        System.out.println("2. OR (Pelo menos uma condição deve ser verdadeira)");
        System.out.println();
    }

    public static void criteriaMenu() {
        System.out.println("\nEscolha o critério de busca:");
        System.out.println("1. Nome");
        System.out.println("2. CPF");
        System.out.println("3. Email");
        System.out.println("4. Finalizar e buscar");
        System.out.println();
    }
} 