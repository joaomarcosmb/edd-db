package org.edd.view;

import org.edd.controller.SystemController;
import org.edd.model.entities.Customer;

import org.edd.view.color.ColorANSI;
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
                        System.out.println("Sistema desligando...");
                        running = false;
                        break;
                    default:
                        System.out.println(formatError("Opção inválida. Tente novamente.\n"));
                }
            } catch (Exception e) {
                System.out.println(formatError("Opção inválida. Tente novamente.\n"));
                System.err.println(formatError(e.getMessage()));
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
        String birthDateCustomer  = scanner.nextLine();

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

    private void editCustomer(){
        boolean isStop = false;

        while (!isStop) {
            System.out.println("\nDigite o ID do cliente que deseja editar:");
            try {
                int id = this.scanner.nextInt();
                this.scanner.nextLine();

                Customer customer = this.controller.getCostumeById(id);

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

                if(isEdited){
                    System.out.println(formatSuccess("Todas as informações alteradas com sucesso.\n"));
                } else{
                    System.out.println(formatSuccess("Ocorreu algum erro. Tente novamente.\n"));
                }

                isStop = true;
            } catch (java.util.InputMismatchException e) {
                System.err.println(formatError("ID inválido. Tente novamente.\n"));
                this.scanner.nextLine();
            }
        }
    }

    private void rollback(){
        boolean isSuccess = this.controller.rollbackStack();

        if (isSuccess) {
            System.out.println(this.formatSuccess("Operação desfeita com sucesso\n"));
        } else {
            System.out.println(this.formatError("Nenhuma ação para desfazer\n"));
        }
    }

    private void listAllCustomers() {
        List<Customer> allConsumers = this.controller.getAllCustomer();

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

            this.controller.addCustomer(nameCustomer, cpfCustomer, birthDateCustomer, emailCustomer);
        }
    }
}