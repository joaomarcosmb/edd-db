package org.edd.view;

import org.edd.controller.SystemController;
import org.edd.model.entities.Customer;
import org.edd.model.entities.Record;

import org.edd.view.color.ColorANSI;
import org.edd.view.choices.HomeChoice;
import org.edd.view.choices.SearchChoice;
import org.edd.view.choices.ComplexQueryChoice;

import java.util.List;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class BankCLI extends ColorANSI {
    private final SystemController controller;
    private final Scanner scanner;

    public BankCLI() {
        controller = new SystemController();
        scanner = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;
        this.TESTE(); /* TODO: Remove after testing */

        while (running) {
            HomeChoice.main(null);

            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        this.addCustomer();
                        break;
                    case 2:
                        this.removeCustomer();
                        break;
                    case 3:
                        this.editCustomer();
                        break;
                    case 4:
                        this.rollback();
                        break;
                    case 5:
                        this.listAllCustomers();
                        break;
                    case 6:
                        this.searchCustomers();
                        break;
                    case 7:
                        this.addComplexQuery();
                        break;
                    case 8:
                        System.out.println("Sistema desligando...");
                        running = false;
                        break;
                    default:
                        System.out.println(formatError("Opção inválida. Tente novamente.\n"));
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println(formatError("Opção inválida. Por favor, digite um número entre 1 e 8.\n"));
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println(formatError("Erro: " + e.getMessage() + "\n"));
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private void addCustomer() {
        System.out.println("\nDigite o nome do cliente:");
        String nameCustomer = scanner.nextLine();

        System.out.println("\nDigite o CPF do cliente:");
        String cpfCustomer = scanner.nextLine();

        System.out.println("\nDigite a data de nascimento (DD/MM/AAAA) do cliente:");
        String birthDateCustomer = scanner.nextLine();

        System.out.println("\nDigite o email do cliente:");
        String emailCustomer = scanner.nextLine();

        controller.addCustomer(nameCustomer, cpfCustomer, birthDateCustomer, emailCustomer);

        System.out.println(this.formatSuccess("\nCliente adicionado com sucesso.\n"));
    }

    private void removeCustomer() {
        boolean isStop = false;
        while (!isStop) {
            System.out.println("\nDigite o ID do cliente que deseja remover:");
            try {
                int id = this.scanner.nextInt();
                this.scanner.nextLine();

                boolean isRemoved = controller.removeCustomer(id);

                if (isRemoved) {
                    System.out.println(formatSuccess("\nCliente com id:" + id + " removido com sucesso.\n"));
                } else {
                    System.out.println(formatNotice("\nCliente não encontrado.\n"));
                }
                isStop = true;
            } catch (java.util.InputMismatchException e) {
                System.err.println(formatError("ID inválido. Tente novamente.\n"));
                this.scanner.nextLine();
            }
        }
    }

    private void editCustomer() {
        boolean isStop = false;

        while (!isStop) {
            System.out.println("\nDigite o ID do cliente que deseja editar:");
            try {
                int id = this.scanner.nextInt();
                this.scanner.nextLine();

                Customer customer = this.controller.getCustomerById(id);

                if (customer != null) {
                    System.out.println("\nCliente encontrado:");
                    System.out.println(customer.getAllAttributes());
                } else {
                    System.out.println(formatNotice("\nCliente não encontrado.\n"));
                    isStop = true;
                }

                System.out.println("\nDigite o novo nome do cliente:");
                String newName = this.scanner.nextLine();

                System.out.println("\nDigite o novo CPF do cliente:");
                String newCPF = this.scanner.nextLine();

                System.out.println("\nDigite a nova data de nascimento (DD/MM/AAAA) do cliente:");
                String newBirthDate = this.scanner.nextLine();

                System.out.println("\nDigite o novo email do cliente:");
                String newEmail = this.scanner.nextLine();

                boolean isEdited = this.controller.editCustomer(id, newName, newCPF, newBirthDate, newEmail, true);

                if (isEdited) {
                    System.out.println(formatSuccess("Todas as informações alteradas com sucesso.\n"));
                } else {
                    System.out.println(formatSuccess("Ocorreu algum erro. Tente novamente.\n"));
                }

                isStop = true;
            } catch (java.util.InputMismatchException e) {
                System.err.println(formatError("ID inválido. Tente novamente.\n"));
                this.scanner.nextLine();
            }
        }
    }

    private void rollback() {
        boolean isSuccess = this.controller.rollbackStack();

        if (isSuccess) {
            System.out.println(this.formatSuccess("Operação desfeita com sucesso\n"));
        } else {
            System.out.println(this.formatError("Nenhuma ação para desfazer\n"));
        }
    }

    private void listAllCustomers() {
        List<Customer> allCustomers = this.controller.getAllCustomer();

        if (allCustomers.isEmpty()) {
            System.out.println(formatNotice("Nenhum cliente cadastrado.\n"));
            return;
        }

        printCustomerTree(allCustomers, 0, "Root: ");
        System.out.println();
    }

    private void printCustomerTree(List<Customer> customers, int level, String prefix) {
        if (customers.isEmpty()) {
            return;
        }

        Customer root = customers.getFirst();
        System.out.println("  ".repeat(level) + prefix + "[Conta: " + root.getId() + ", Nome: " + root.getName() + "]");

        List<Customer> leftSubtree = new ArrayList<>();
        List<Customer> rightSubtree = new ArrayList<>();
        
        for (int i = 1; i < customers.size(); i++) {
            if (customers.get(i).getName().compareTo(root.getName()) < 0) {
                leftSubtree.add(customers.get(i));
            } else {
                rightSubtree.add(customers.get(i));
            }
        }

        if (!leftSubtree.isEmpty()) {
            printCustomerTree(leftSubtree, level + 1, "L--- ");
        }
        if (!rightSubtree.isEmpty()) {
            printCustomerTree(rightSubtree, level + 1, "R--- ");
        }
    }

    private void searchCustomers() {
        SearchChoice.main(null);

        try {
            int choice = scanner.nextInt();
            scanner.nextLine();

            System.out.println("\nDigite o valor para busca:");
            String searchValue = scanner.nextLine().trim().toLowerCase();

            String field;
            switch (choice) {
                case 1:
                    field = "name";
                    break;
                case 2:
                    field = "cpf";
                    break;
                case 3:
                    field = "email";
                    break;
                default:
                    System.out.println(formatError("Opção inválida"));
                    return;
            }

            Map<String, Object> criteria = new HashMap<>();
            criteria.put(field, searchValue);
            List<Record> results = controller.searchComplex(criteria, "AND");

            if (results.isEmpty()) {
                System.out.println(formatNotice("\nNenhum cliente encontrado com os critérios especificados.\n"));
            } else {
                System.out.println("\nClientes encontrados:");
                for (Record record : results) {
                    System.out.println("ID: " + record.getValue("id") +
                            ", Nome: " + record.getValue("name") +
                            ", CPF: " + record.getValue("cpf") +
                            ", Data de Nascimento: " + record.getValue("birthDate") +
                            ", Email: " + record.getValue("email"));
                }
                System.out.println();
            }

        } catch (Exception e) {
            System.out.println(formatError("Erro ao realizar a busca: " + e.getMessage() + "\n"));
            scanner.nextLine();
        }
    }

    private void addComplexQuery() {
        ComplexQueryChoice.main(null);

        try {
            int operatorChoice = scanner.nextInt();
            scanner.nextLine();

            String operator = operatorChoice == 1 ? "AND" : "OR";
            Map<String, Object> criteria = new HashMap<>();

            boolean addingCriteria = true;
            while (addingCriteria) {
                ComplexQueryChoice.criteriaMenu();

                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 4) {
                    addingCriteria = false;
                } else if (choice >= 1 && choice <= 3) {
                    System.out.println("\nDigite o valor para busca:");
                    String searchValue = scanner.nextLine().trim();

                    switch (choice) {
                        case 1:
                            criteria.put("name", searchValue.toLowerCase());
                            break;
                        case 2:
                            criteria.put("cpf", searchValue);
                            break;
                        case 3:
                            criteria.put("email", searchValue.toLowerCase());
                            break;
                    }
                } else {
                    System.out.println(formatError("Opção inválida"));
                }
            }

            if (criteria.isEmpty()) {
                System.out.println(formatNotice("\nNenhum critério de busca foi especificado.\n"));
                return;
            }

            List<Record> results = controller.searchComplex(criteria, operator);

            if (results.isEmpty()) {
                System.out.println(formatNotice("\nNenhum cliente encontrado com os critérios especificados.\n"));
            } else {
                System.out.println("\nClientes encontrados:");
                for (Record record : results) {
                    System.out.println("ID: " + record.getValue("id") +
                            ", Nome: " + record.getValue("name") +
                            ", CPF: " + record.getValue("cpf") +
                            ", Data de Nascimento: " + record.getValue("birthDate") +
                            ", Email: " + record.getValue("email"));
                }
                System.out.println();
            }

        } catch (Exception e) {
            System.out.println(formatError("Erro ao realizar a busca complexa: " + e.getMessage() + "\n"));
            scanner.nextLine();
        }
    }

    private void TESTE() {
        String[] namesCustomer = { "Carlos Souza", "Mariana Silva", "João Pereira", "Ana Santos" };
        String[] cpfsCustomer = { "123.456.789-01", "987.654.321-02", "456.789.123-03", "321.654.987-04" };
        String[] birthDatesCustomer = { "10/03/1985", "24/06/1990", "15/11/1978", "30/01/2002" };
        String[] emailsCustomer = { "carlos.souza@example.com", "mariana.silva@example.com", "joao.pereira@example.com",
                "ana.santos@example.com" };

        for (int i = 0; i < namesCustomer.length; i++) {
            String nameCustomer = namesCustomer[i];
            String cpfCustomer = cpfsCustomer[i];
            String birthDateCustomer = birthDatesCustomer[i];
            String emailCustomer = emailsCustomer[i];

            this.controller.addCustomer(nameCustomer, cpfCustomer, birthDateCustomer, emailCustomer);
        }
    }
}