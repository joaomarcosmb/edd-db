package org.edd.view;

import org.edd.controller.SystemController;
import org.edd.model.entities.Customer;
import org.edd.model.structures.NodeList;

import org.edd.view.color.ColorANSI;
import org.edd.view.choices.EditChoice;
import org.edd.view.choices.HomeChoice;

import java.util.List;
import java.util.Scanner;

public class BankCLI extends ColorANSI {
    private final SystemController controller;
    private final Scanner scanner;

    public BankCLI() {
        controller = new SystemController();
        scanner = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;
        this.TESTE(); /* TODO: remove after testing*/

        while (running) {
            HomeChoice.main(null);

            try{
                int choice = scanner.nextInt();
                scanner.nextLine();
    
                switch (choice) {
                    case 1:
                        addCustomer();
                        break;
                    case 2:
                        /*TODO: fixbug remove sem id existente*/
                        removeCustomer();
                        break;
                    case 3:
                        editCustomer();
                        break;
                    case 4:
                        rollback();
                        break;
                    case 5:
                        listAllCustomers();
                        break;
                    case 6:
                        System.out.println("Sistema desligando...");
                        running = false;
                        break;
                    default:
                        System.out.println(formatError("Opção inválida. Tente novamente.\n"));
                }
            } catch (Exception e) {
                System.out.println(formatError("Opção inválida. Tente novamente.\n"));
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private void addCustomer() {
        int idCustomer = this.controller.findSize() + 1;

        System.out.println("\nDigite o nome do cliente:");
        String nameCustomer = scanner.nextLine();

        System.out.println("\nDigite o CPF do cliente:");
        String cpfCustomer = scanner.nextLine();

        System.out.println("\nDigite a data de nascimento (DD/MM/AAAA) do cliente:");
        String birthDateCustomer  = scanner.nextLine();

        System.out.println("\nDigite o email do cliente:");
        String emailCustomer = scanner.nextLine();

        controller.addCustomer(idCustomer, nameCustomer, cpfCustomer, birthDateCustomer, emailCustomer);

        System.out.println(formatSuccess("\nCliente adicionado com sucesso.\n"));
    }

    private void removeCustomer() {
        boolean isStop = false;
        while (!isStop) {
            System.out.println("\nDigite o ID do cliente que deseja remover:");
            try {
                int id = scanner.nextInt();
                scanner.nextLine();

                boolean isRemoved = controller.removeCustomer(id);
    
                if (isRemoved) {
                    System.out.println(formatSuccess("\nCliente com id:" + id + " removido com sucesso.\n"));
                } else {
                    System.out.println(formatNotice("\nCliente não encontrado.\n"));
                }
                isStop = true;
            } catch (java.util.InputMismatchException e) {
                System.err.println(formatError("ID inválido. Tente novamente.\n"));
                scanner.nextLine();
            }
        }
    }

    private void editCustomer(){
        boolean running = true;
        while (running) {
            System.out.println("\nDigite o ID do cliente que deseja editar:");
            try {
                int id = scanner.nextInt();
                scanner.nextLine();

                NodeList customer = controller.findNodeCustomer(id);

                if (customer == null) {
                    System.out.println(formatNotice("\nCliente não encontrado.\n"));
                    return;
                }

                System.out.println("\nCliente encontrado:");
                System.out.println(customer.customer.getAllAttributes());

                while (running) {
                   EditChoice.main(null);
                   try{
                        int choice = scanner.nextInt();
                        scanner.nextLine();

                        switch (choice) {
                            case 1:
                                System.out.println("\nDigite o novo nome do cliente:");
                                customer.customer.setName(scanner.nextLine());
                                System.out.println(formatSuccess("Nome alterado com sucesso.\n"));
                                running = false;
                                break;
                            case 2:
                                System.out.println("\nDigite o novo CPF do cliente:");
                                customer.customer.setCpf(scanner.nextLine());
                                System.out.println(formatSuccess("CPF alterado com sucesso.\n"));
                                running = false;
                                break;
                            case 3:
                                System.out.println("\nDigite a nova data de nascimento (DD/MM/AAAA) do cliente:");
                                customer.customer.setBirthDate(scanner.nextLine());
                                System.out.println(formatSuccess("Data de nascimento alterado com sucesso.\n"));
                                running = false;
                                break;
                            case 4:
                                System.out.println("\nDigite o novo email do cliente:");
                                System.out.println(formatSuccess("Email alterado com sucesso.\n"));
                                customer.customer.setEmail(scanner.nextLine());
                                running = false;
                                break;
                            case 5:
                                System.out.println("\nDigite o novo nome do cliente:");
                                customer.customer.setName(scanner.nextLine());
                                System.out.println("\nDigite o novo CPF do cliente:");
                                customer.customer.setCpf(scanner.nextLine());
                                System.out.println("\nDigite a nova data de nascimento (DD/MM/AAAA) do cliente:");
                                customer.customer.setBirthDate(scanner.nextLine());
                                System.out.println("\nDigite o novo email do cliente:");
                                customer.customer.setEmail(scanner.nextLine());
                                System.out.println(formatSuccess("Todas as informações alteradas com sucesso.\n"));
                                running = false;
                                break;
                            case 6:
                                System.out.println(formatNotice("\nEdição cancelada.\n"));
                                running = false;
                                break;
                            default:
                                System.out.println(formatError("Opção inválida. Tente novamente.\n"));
                        }
                   } catch (java.util.InputMismatchException e) {
                        System.err.println(formatError("Opção inválido. Tente novamente.\n"));
                        scanner.nextLine();
                    }
                }
            } catch (java.util.InputMismatchException e) {
                System.err.println(formatError("ID inválido. Tente novamente."));
                scanner.nextLine();
            }
        }
    }

    private void rollback(){
        System.out.println("asd");
        controller.rollbackStack();
        System.out.println(formatSuccess("Operação desfeita com sucesso\n"));
    }

    private void listAllCustomers() {
        List<Customer> allConsumers = controller.getAllCustomer();

        if (allConsumers.isEmpty()) {
            System.out.println(formatNotice("Nenhum cliente cadastrado.\n"));
            return;
        }

        for(Customer consumer: allConsumers) {
            System.out.println(consumer.getAllAttributes());
        }

        System.out.println();
    }

    private void TESTE(){
        String[] namesCustomer = {"Carlos Souza", "Mariana Silva", "João Pereira", "Ana Santos"};
        String[] cpfsCustomer = {"123.456.789-01", "987.654.321-02", "456.789.123-03", "321.654.987-04"};
        String[] birthDatesCustomer = {"1985-03-10", "1990-06-24", "1978-11-15", "2002-01-30"};
        String[] emailsCustomer = {"carlos.souza@example.com", "mariana.silva@example.com", "joao.pereira@example.com", "ana.santos@example.com"};
        for(int i = 0; i < namesCustomer.length; i++){
            String nameCustomer = namesCustomer[i];
            String cpfCustomer = cpfsCustomer[i];
            String birthDateCustomer = birthDatesCustomer[i];
            String emailCustomer = emailsCustomer[i];

            controller.addCustomer(i + 1, nameCustomer, cpfCustomer, birthDateCustomer, emailCustomer);
        }
    }
}