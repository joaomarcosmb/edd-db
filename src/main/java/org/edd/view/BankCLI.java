package org.edd.view;

import org.edd.controller.SystemController;
import org.edd.model.entities.Customer;
import org.edd.model.structures.NodeList;
import org.edd.view.Color.ColorANSI;
import org.edd.view.choices.EditChoice;
import org.edd.view.choices.HomeChoice;

import java.util.List;
import java.util.Scanner;

public class BankCLI {
    private final SystemController controller;
    private final Scanner scanner;

    public BankCLI() {
        controller = new SystemController();
        scanner = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;
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
                        removeCustomer();
                        break;
                    case 3:
                        editCustomer();
                        break;
                    case 5:
                        listAllCustomers();
                        break;
                    case 6:
                        System.out.println("Sistema desligando...");
                        running = false;
                        break;
                    default:
                        System.out.println(ColorANSI.ANSI_RED +"Opção inválida. Tente novamente.\n" + ColorANSI.ANSI_RESET);
                }
            } catch (Exception e) {
                System.out.println(ColorANSI.ANSI_RED +"Opção inválida. Tente novamente.\n" + ColorANSI.ANSI_RESET);
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

        System.out.println(ColorANSI.ANSI_GREEN + "\nCliente adicionado com sucesso.\n" + ColorANSI.ANSI_RESET);
    }

    private void removeCustomer() {
        boolean stop = false;
        while (!stop) {
            System.out.println("\nDigite o ID do cliente que deseja remover:");
            try {
                int id = scanner.nextInt();
                scanner.nextLine();
    
                if (controller.removeCustomer(id)) {
                    System.out.println(ColorANSI.ANSI_GREEN +"\nCliente com id:" + id + " removido com sucesso.\n" + ColorANSI.ANSI_RESET);
                } else {
                    System.out.println(ColorANSI.ANSI_YELLOW + "\nCliente não encontrado." + ColorANSI.ANSI_RESET);
                }

                stop = true;
            } catch (java.util.InputMismatchException e) {
                System.err.println(ColorANSI.ANSI_RED + "ID inválido. Tente novamente.\n" + ColorANSI.ANSI_RESET);
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

                NodeList customer = controller.findCustomer(id);

                if (customer == null) {
                    System.out.println(ColorANSI.ANSI_YELLOW +"\nCliente não encontrado.\n" + ColorANSI.ANSI_RESET);
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
                                System.out.println(ColorANSI.ANSI_GREEN + "Nome alterado com sucesso.\n" + ColorANSI.ANSI_RESET);
                                running = false;
                                break;
                            case 2:
                                System.out.println("\nDigite o novo CPF do cliente:");
                                customer.customer.setCpf(scanner.nextLine());
                                System.out.println(ColorANSI.ANSI_GREEN + "CPF alterado com sucesso.\n" + ColorANSI.ANSI_RESET);
                                running = false;
                                break;
                            case 3:
                                System.out.println("\nDigite a nova data de nascimento (DD/MM/AAAA) do cliente:");
                                customer.customer.setBirthDate(scanner.nextLine());
                                System.out.println(ColorANSI.ANSI_GREEN + "Data de nascimento alterado com sucesso.\n" + ColorANSI.ANSI_RESET);
                                running = false;
                                break;
                            case 4:
                                System.out.println("\nDigite o novo email do cliente:");
                                System.out.println(ColorANSI.ANSI_GREEN + "Email alterado com sucesso.\n" + ColorANSI.ANSI_RESET);
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
                                System.out.println(ColorANSI.ANSI_GREEN + "Todas as informações alteradas com sucesso.\n" + ColorANSI.ANSI_RESET);
                                running = false;
                                break;
                            case 6:
                                System.out.println(ColorANSI.ANSI_YELLOW +"\nEdição cancelada.\n" + ColorANSI.ANSI_RESET);
                                running = false;
                                break;
                            default:
                                System.out.println(ColorANSI.ANSI_RED + "Opção inválida. Tente novamente.\n" + ColorANSI.ANSI_RESET);
                        }
                   } catch (java.util.InputMismatchException e) {
                        System.err.println(ColorANSI.ANSI_RED + "Opção inválido. Tente novamente.\n" + ColorANSI.ANSI_RESET);
                        scanner.nextLine();
                    }
                }
            } catch (java.util.InputMismatchException e) {
                System.err.println(ColorANSI.ANSI_RED + "ID inválido. Tente novamente." + ColorANSI.ANSI_RESET);
                scanner.nextLine();
            }
        }
    }

    private void listAllCustomers() {
        List<Customer> allConsumers = controller.getAllCustomer();

        if (allConsumers.size() == 0) {
            System.out.println(ColorANSI.ANSI_YELLOW + "Nenhum cliente cadastrado.\n" + ColorANSI.ANSI_RESET);
            return;
        }

        for (int i = 0; i < allConsumers.size(); i++) {
            System.out.println(allConsumers.get(i).getAllAttributes());
        }

        System.out.println("\n");
    }
}
