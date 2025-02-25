package bancario.projeto.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import bancario.projeto.model.Cliente;

import bancario.projeto.model.ContaCorrente;
import bancario.projeto.model.ContaPoupanca;
import bancario.projeto.model.IConta;
import bancario.projeto.persistencia.PersistenciaCliente;
import exceptions.ContaInexistenteException;
import exceptions.SaldoInsuficienteException;
import exceptions.StatusException;
import exceptions.ValorInvalidoExeception;


public class Programa {
	
	private ArrayList<Cliente> clientes;

    public Programa() {
    	this.clientes = PersistenciaCliente.carregarClientes();
    }

    
    public void adicionarCliente(String nome, String cpf) {
        if (!clientes.contains(new Cliente(cpf))) {
            clientes.add(new Cliente(nome, cpf));
            PersistenciaCliente.salvarClientes(clientes);
            System.out.println("Cliente cadastrado com sucesso!");
        } else {
            System.out.println("Cliente já cadastrado.");
        }
    }
    
    public Cliente buscarCliente(String cpf) {
        for (int i = 0; i < clientes.size(); i++) {
            Cliente cliente = clientes.get(i);
            if (cliente.getCpf().equals(cpf)) {
                return cliente;
            }
        }
        return null;
    }
    
    public void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
        } else {
            for (int i = 0; i < clientes.size(); i++) {
                System.out.println(clientes.get(i));
            }
        }
    }
    
    public void removerCliente(String cpf) {
        Cliente cliente = buscarCliente(cpf);
        if (cliente != null) {
            clientes.remove(cliente);  
            PersistenciaCliente.salvarClientes(clientes);
            System.out.println("Cliente removido com sucesso!");
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }
    
    public void menu() {
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        do {
            System.out.println("\n--- Sistema Bancário ---");
            System.out.println("1. Cadastrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Adicionar conta");
            System.out.println("4. Listar contas de um cliente");
            System.out.println("5. Realizar depósito");
            System.out.println("6. Realizar saque");
            System.out.println("7. Transferir entre contas");
            System.out.println("8. Ver saldo da conta");
            System.out.println("9. Ver extrato da conta");
            System.out.println("10. Remover conta");
            System.out.println("11. Ativar conta");
            System.out.println("12. Desativar conta");
            System.out.println("13. Ver balanço do cliente");
            System.out.println("14. Remover cliente");
            System.out.println("15. Sair");
            System.out.print("Escolha uma opção: ");
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                    	System.out.print("Nome: ");
                        String nome = scanner.nextLine();
                        System.out.print("CPF: ");
                        String cpf = scanner.nextLine();
                        adicionarCliente(nome, cpf);
                        break;
                    case 2:
                        listarClientes();
                        break;
                    case 3:
                        System.out.print("CPF do cliente: ");
                        cpf = scanner.nextLine();
                        Cliente cliente = buscarCliente(cpf);
                        if (cliente != null) {
                            System.out.print("Número da conta: ");
                            int numeroConta = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("Escolha o tipo de conta:");
                            System.out.println("1. Conta Corrente");
                            System.out.println("2. Conta Poupança");
                            int tipoConta = scanner.nextInt();
                            scanner.nextLine();
                            if (tipoConta == 1) {
                                cliente.adicionarConta(new ContaCorrente(numeroConta), clientes);
                            } else if (tipoConta == 2) {
                                cliente.adicionarConta(new ContaPoupanca(numeroConta), clientes);
                            } else {
                                System.out.println("Tipo de conta inválido.");
                            }
                        } else {
                            System.out.println("Cliente não encontrado.");
                        }
                        break;
                    case 4:
                        System.out.print("CPF do cliente: ");
                        cpf = scanner.nextLine();
                        cliente = buscarCliente(cpf);
                        if (cliente != null) {
                            cliente.listarContas();
                        } else {
                            System.out.println("Cliente não encontrado.");
                        }
                        break;
                    case 5:
                        System.out.print("CPF do cliente: ");
                        cpf = scanner.nextLine();
                        cliente = buscarCliente(cpf);
                        if (cliente != null) {
                            System.out.print("Número da conta para depósito: ");
                            int numeroConta = scanner.nextInt();
                            scanner.nextLine();
                            IConta contaDeposito = cliente.buscarConta(numeroConta);
                            if (contaDeposito != null) {
                                System.out.print("Valor para depósito: ");
                                BigDecimal valorDeposito = scanner.nextBigDecimal();
                                scanner.nextLine();
                                contaDeposito.depositar(valorDeposito);
                            } else {
                                System.out.println("Conta não encontrada.");
                            }
                        } else {
                            System.out.println("Cliente não encontrado.");
                        }
                        break;
                    case 6:
                        System.out.print("CPF do cliente: ");
                        cpf = scanner.nextLine();
                        cliente = buscarCliente(cpf);
                        if (cliente != null) {
                            System.out.print("Número da conta para saque: ");
                            int numeroConta = scanner.nextInt();
                            scanner.nextLine();
                            IConta contaSaque = cliente.buscarConta(numeroConta);
                            if (contaSaque != null) {
                                System.out.print("Valor para saque: ");
                                BigDecimal valorSaque = scanner.nextBigDecimal();
                                scanner.nextLine();

                                if (valorSaque.compareTo(BigDecimal.ZERO) > 0) {
                                    try {
                                        contaSaque.sacar(valorSaque);
                                        System.out.println("Saque realizado com sucesso!");
                                    } catch (SaldoInsuficienteException e) {
                                        System.out.println("Saldo insuficiente para o saque.");
                                    } catch (ValorInvalidoExeception e) {
                                        System.out.println("Valor inválido para saque.");
                                    }
                                } else {
                                    System.out.println("O valor para saque deve ser maior que zero.");
                                }
                            } else {
                                System.out.println("Conta não encontrada.");
                            }
                        } else {
                            System.out.println("Cliente não encontrado.");
                        }
                        break;
                    case 7:
                        System.out.print("CPF do cliente de origem: ");
                        cpf = scanner.nextLine();
                        cliente = buscarCliente(cpf);
                        if (cliente != null) {
                            System.out.print("Número da conta de origem: ");
                            int numeroContaOrigem = scanner.nextInt();
                            scanner.nextLine();
                            IConta contaOrigem = cliente.buscarConta(numeroContaOrigem);
                            if (contaOrigem != null) {
                                System.out.print("Número da conta de destino: ");
                                int numeroContaDestino = scanner.nextInt();
                                scanner.nextLine();

                                IConta contaDestino = null;
                                Cliente clienteDestino = null;

                                for (int i = 0; i < clientes.size(); i++) { 
                                    Cliente c = clientes.get(i);
                                    contaDestino = c.buscarConta(numeroContaDestino);
                                    if (contaDestino != null) {
                                        clienteDestino = c;
                                        break;
                                    }
                                }

                                if (contaDestino != null) {
                                    System.out.println("Cliente de destino: " + clienteDestino.getNome());
                                    System.out.print("Valor para transferência: ");
                                    BigDecimal valorTransferencia = scanner.nextBigDecimal();
                                    scanner.nextLine();
                                    try {
                                        contaOrigem.transferir(contaDestino, valorTransferencia);
                                    } catch (SaldoInsuficienteException e) {
                                        System.out.println("Saldo insuficiente para a transferência.");
                                    }
                                } else {
                                    System.out.println("Conta de destino não encontrada.");
                                }
                            } else {
                                System.out.println("Conta de origem não encontrada.");
                            }
                        } else {
                            System.out.println("Cliente de origem não encontrado.");
                        }
                        break;
                    case 8:
                        System.out.print("CPF do cliente: ");
                        cpf = scanner.nextLine();
                        cliente = buscarCliente(cpf);
                        if (cliente != null) {
                            System.out.print("Número da conta para ver o saldo: ");
                            int numeroConta = scanner.nextInt();
                            scanner.nextLine();
                            IConta contaSaldo = cliente.buscarConta(numeroConta);
                            if (contaSaldo != null) {
                                System.out.println("Saldo da conta: R$" + contaSaldo.getSaldo());
                            } else {
                                System.out.println("Conta não encontrada.");
                            }
                        } else {
                            System.out.println("Cliente não encontrado.");
                        }
                        break;
                    case 9:
                        System.out.print("CPF do cliente: ");
                        cpf = scanner.nextLine();
                        cliente = buscarCliente(cpf);
                        if (cliente != null) {
                            System.out.print("Número da conta para ver o extrato: ");
                            int numeroConta = scanner.nextInt();
                            scanner.nextLine();

                            IConta contaExtrato = cliente.buscarConta(numeroConta);
                            if (contaExtrato != null) {
                                System.out.print("Informe o mês: ");
                                int mes = scanner.nextInt();
                                System.out.print("Informe o ano: ");
                                int ano = scanner.nextInt();
                                scanner.nextLine();

                                contaExtrato.extratoPorMesAno(mes, ano);
                            } else {
                                System.out.println("Conta não encontrada.");
                            }
                        } else {
                            System.out.println("Cliente não encontrado.");
                        }
                        break;
                    case 10:
                        System.out.print("CPF do cliente: ");
                        cpf = scanner.nextLine();
                        cliente = buscarCliente(cpf);
                        if (cliente != null) {
                            System.out.print("Número da conta para remover: ");
                            int numeroConta = scanner.nextInt();
                            scanner.nextLine();
                            IConta contaParaRemover = cliente.buscarConta(numeroConta);
                            if (contaParaRemover != null) {
                                if (!contaParaRemover.isAtiva() || contaParaRemover.getSaldo().compareTo(BigDecimal.ZERO) == 0) {
                                    cliente.removerConta(numeroConta);
                                    System.out.println("Conta removida com sucesso!");
                                } else {
                                    System.out.println("A conta está ativa e possui saldo, não pode ser removida.");
                                }
                            } else {
                                System.out.println("Conta não encontrada.");
                            }
                        } else {
                            System.out.println("Cliente não encontrado.");
                        }
                        break;
                    case 11:
                        System.out.print("CPF do cliente: ");
                        cpf = scanner.nextLine();
                        cliente = buscarCliente(cpf);
                        if (cliente != null) {
                            System.out.print("Número da conta para ativar: ");
                            int numeroConta = scanner.nextInt();
                            scanner.nextLine();
                            IConta contaAtivar = cliente.buscarConta(numeroConta);
                            if (contaAtivar != null) {
                                contaAtivar.ativar();
                            } else {
                                System.out.println("Conta não encontrada.");
                            }
                        } else {
                            System.out.println("Cliente não encontrado.");
                        }
                        break;
                    case 12:
                        System.out.print("CPF do cliente: ");
                        cpf = scanner.nextLine();
                        cliente = buscarCliente(cpf);
                        if (cliente != null) {
                            System.out.print("Número da conta para desativar: ");
                            int numeroConta = scanner.nextInt();
                            scanner.nextLine();
                            IConta contaDesativar = cliente.buscarConta(numeroConta);
                            if (contaDesativar != null) {
                                contaDesativar.desativar();
                            } else {
                                System.out.println("Conta não encontrada.");
                            }
                        } else {
                            System.out.println("Cliente não encontrado.");
                        }
                        break;
                    case 13:
                        System.out.print("CPF do cliente: ");
                        cpf = scanner.nextLine();
                        cliente = buscarCliente(cpf);
                        if (cliente != null) {
                            BigDecimal saldoCliente = BigDecimal.ZERO;
                            for (IConta conta : cliente.getContas()) {
                                if (conta.isAtiva()) {
                                    saldoCliente = saldoCliente.add(conta.getSaldo());
                                }
                            }
                            System.out.println("Balanço do cliente: R$" + saldoCliente);
                        } else {
                            System.out.println("Cliente não encontrado.");
                        }
                        break;
                    case 14:
                        System.out.print("CPF do cliente a ser removido: ");
                        cpf = scanner.nextLine();
                        Cliente clienteParaRemover = buscarCliente(cpf);
                        if (clienteParaRemover != null) {
                            boolean temContaAtiva = false;
                            for (IConta conta : clienteParaRemover.getContas()) {
                                if (conta.isAtiva()) {
                                    temContaAtiva = true;
                                    break;
                                }
                            }
                            if (!temContaAtiva) {
                                removerCliente(cpf);
                                System.out.println("Cliente removido com sucesso!");
                            } else {
                                System.out.println("O cliente possui contas ativas e não pode ser removido.");
                            }
                        } else {
                            System.out.println("Cliente não encontrado.");
                        }
                        break;
                    case 15:
                        PersistenciaCliente.salvarClientes(clientes);
                        System.out.println("Fim!");
                        break;
                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Por favor, insira um número.");
                scanner.nextLine();
            } catch (ContaInexistenteException e) {
                System.out.println("Conta inexistente.");
            } catch (ValorInvalidoExeception e) {
                System.out.println("Valor deve ser maior que 0.");
            } catch (StatusException e) {
                System.out.println("Conta ainda possui saldo!");
            }
        } while (opcao != 15);

        scanner.close();
    }
    
    public static void main(String[] args) {
        Programa Sistema = new Programa();
        Sistema.menu(); 
    }
}
