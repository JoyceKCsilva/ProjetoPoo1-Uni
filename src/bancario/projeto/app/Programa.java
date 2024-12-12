package bancario.projeto.app;

import java.util.ArrayList;
import java.util.Scanner;

import bancario.projeto.model.Cliente;
import bancario.projeto.model.ContaBancaria;
import bancario.projeto.persistencia.PersistenciaCliente;

public class Programa {

	public static void main(String[] args) {
		
		PersistenciaCliente p = new PersistenciaCliente();
		
		Scanner sc = new Scanner(System.in);
		boolean sair = true;
		int o = 0;
		
		System.out.println("Sistema Bancário");
		while(sair) {
			System.out.println("\nEscolha uma opção:\n"
				    + "\n1 - Cadastrar cliente;\n"
				    + "2 - Listar os clientes cadastrados;\n"
				    + "3 - Consultar cliente por CPF;\n"
				    + "4 - Remover cliente;\n"
				    + "5 - Criar conta e associar ao cliente;\n"
				    + "6 - Listar as contas cadastradas do cliente;\n"
				    + "7 - Remover conta de um dado cliente;\n"
				    + "8 - Realizar depósito de uma dada quantia;\n"
				    + "9 - Realizar saque de uma dada quantia;\n"
				    + "10 - Efetuar transferência de quantia entre contas;\n"
				    + "11 - Consultar saldo;\n"
				    + "12 - Consultar balanco das contas;\n"
				    + "13 - Sair do sistema.\n");
			o = sc.nextInt();
			switch (o) {
			
			
			case 1: {
			    System.out.println("Digite o CPF do cliente:");
			    String cpf = sc.next();
			    System.out.println("Digite o nome do cliente:");
			    String nome = sc.next();
			    Cliente cliente = new Cliente(cpf, nome);
			    p.adicionarCliente(cliente);
			    break;
			}

			case 2: {
			    System.out.println("Clientes cadastrados:");
			    ArrayList<Cliente> listaClientes = p.listarClientes();
			    if (listaClientes.isEmpty()) {
			        System.out.println("Nenhum cliente cadastrado.");
			    } else {
			        for (Cliente cliente : listaClientes) {
			            System.out.println("CPF: " + cliente.getCpf() + ", Nome: " + cliente.getNome());
			        }
			    }
			    break;
			}

			case 3: {
			    System.out.println("Digite o CPF do cliente a ser consultado:");
			    String cpf = sc.next();
			    Cliente cliente = p.localizarClientePorCpf(cpf);
			    if (cliente == null) {
			        System.out.println("Cliente não encontrado.");
			    } else {
			        System.out.println("CPF: " + cliente.getCpf() + ", Nome: " + cliente.getNome());
			    }
			    break;
			}

			case 4: {
			    System.out.println("Digite o CPF do cliente a ser removido:");
			    String cpf = sc.next();
			    Cliente cliente = p.localizarClientePorCpf(cpf);
			    if (cliente == null) {
			        System.out.println("Cliente não encontrado.");
			    } else {
			        p.removerCliente(cliente);
			    }
			    break;
			}

			case 5: {
			    System.out.println("Digite o CPF do cliente:");
			    String cpf = sc.next();
			    Cliente cliente = p.localizarClientePorCpf(cpf);
			    if (cliente == null) {
			        System.out.println("Cliente não encontrado.");
			    } else {
			        System.out.println("Digite o número da nova conta:");
			        int numeroConta = sc.nextInt();
			        ContaBancaria conta = new ContaBancaria(numeroConta);
			        cliente.adicionarConta(conta);
			    }
			    break;
			}

			case 6: {
			    System.out.println("Digite o CPF do cliente:");
			    String cpf = sc.next();
			    Cliente cliente = p.localizarClientePorCpf(cpf);
			    if (cliente == null) {
			        System.out.println("Cliente não encontrado.");
			    } else {
			        System.out.println("Contas do cliente:");
			        for (ContaBancaria conta : cliente.getContas()) {
			            System.out.println(conta);
			        }
			    }
			    break;
			}

			case 7: {
			    System.out.println("Digite o CPF do cliente:");
			    String cpf = sc.next();
			    Cliente cliente = p.localizarClientePorCpf(cpf);
			    if (cliente == null) {
			        System.out.println("Cliente não encontrado.");
			    } else {
			        System.out.println("Digite o número da conta para remover:");
			        int numeroConta = sc.nextInt();
			        ContaBancaria conta = cliente.localizarContaPorNumero(numeroConta);
			        if (conta == null) {
			            System.out.println("Conta não encontrada.");
			        } else {
			            cliente.removerConta(conta);
			        }
			    }
			    break;
			}

			case 8: {
			    System.out.println("Digite o CPF do cliente:");
			    String cpf = sc.next();
			    Cliente cliente = p.localizarClientePorCpf(cpf);
			    if (cliente == null) {
			        System.out.println("Cliente não encontrado.");
			    } else {
			        System.out.println("Digite o número da conta para depósito:");
			        int numeroConta = sc.nextInt();
			        ContaBancaria conta = cliente.localizarContaPorNumero(numeroConta);
			        if (conta == null) {
			            System.out.println("Conta não encontrada.");
			        } else {
			            System.out.println("Digite o valor a ser depositado:");
			            float quantia = sc.nextFloat();
			            conta.depositar(quantia);
			        }
			    }
			    break;
			}

			case 9: {
			    System.out.println("Digite o CPF do cliente:");
			    String cpf = sc.next();
			    Cliente cliente = p.localizarClientePorCpf(cpf);
			    if (cliente == null) {
			        System.out.println("Cliente não encontrado.");
			    } else {
			        System.out.println("Digite o número da conta para saque:");
			        int numeroConta = sc.nextInt();
			        ContaBancaria conta = cliente.localizarContaPorNumero(numeroConta);
			        if (conta == null) {
			            System.out.println("Conta não encontrada.");
			        } else {
			            System.out.println("Digite o valor a ser sacado:");
			            float quantia = sc.nextFloat();
			            conta.sacar(quantia);
			        }
			    }
			    break;
			}
			
			case 10: { 
				System.out.println("Digite o CPF do cliente de origem:");
				String cpfOrigem = sc.next();
				Cliente clienteOrigem = p.localizarClientePorCpf(cpfOrigem);

				if (clienteOrigem == null) {
				    System.out.println("Cliente de origem não encontrado.");
				    break;
				}

				System.out.println("Digite o número da conta de origem:");
				int numeroContaOrigem = sc.nextInt();
				ContaBancaria contaOrigem = clienteOrigem.localizarContaPorNumero(numeroContaOrigem);

				if (contaOrigem == null) {
				    System.out.println("Conta de origem não encontrada.");
				    break;
				}

				System.out.println("Digite o CPF do cliente de destino:");
				String cpfDestino = sc.next();
				Cliente clienteDestino = p.localizarClientePorCpf(cpfDestino);

				if (clienteDestino == null) {
				    System.out.println("Cliente de destino não encontrado.");
				    break;
				}

				System.out.println("Digite o número da conta de destino:");
				int numeroContaDestino = sc.nextInt();
				ContaBancaria contaDestino = clienteDestino.localizarContaPorNumero(numeroContaDestino);

				if (contaDestino == null) {
				    System.out.println("Conta de destino não encontrada.");
				    break;
				}

				System.out.println("Digite o valor a ser transferido:");
				float quantia = sc.nextFloat();
				contaOrigem.transferir(contaDestino, quantia);
				break;
			}
			
			
			case 11: {
			    System.out.println("Digite o CPF do cliente:");
			    String cpf = sc.next();
			    Cliente cliente = p.localizarClientePorCpf(cpf);
			    if (cliente == null) {
			        System.out.println("Cliente não encontrado.");
			    } else {
			        System.out.println("Digite o número da conta para consultar o saldo:");
			        int numeroConta = sc.nextInt();
			        ContaBancaria conta = cliente.localizarContaPorNumero(numeroConta);
			        if (conta == null) {
			            System.out.println("Conta não encontrada.");
			        } else {
			            System.out.println("Saldo da conta: " + conta.getSaldo());
			        }
			    }
			    break;
			}

			case 12: {
			    System.out.println("Digite o CPF do cliente:");
			    String cpf = sc.next();
			    Cliente cliente = p.localizarClientePorCpf(cpf);
			    if (cliente == null) {
			        System.out.println("Cliente não encontrado.");
			    } else {
			        float balancoTotal = 0;
			        for (ContaBancaria conta : cliente.getContas()) {
			            balancoTotal += conta.getSaldo();
			        }
			        System.out.println("Balanço total das contas do cliente: " + balancoTotal);
			    }
			    break;
			}

			case 13: {
			    System.out.println("Você saiu do sistema.");
			    p.salvarClientes();
			    sair = false;
			    break;
			}
            default:
                throw new IllegalArgumentException("Unexpected value: " + o);
				}
			}
			sc.close();
	}
}
