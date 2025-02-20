package bancario.projeto.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;


public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nome;
	private String cpf;
	private ArrayList<IConta> contas;
	
	
	
	public Cliente(String nome, String cpf) {
		this.nome = nome;
	    this.cpf = cpf;
		this.contas = new ArrayList<>();
	}
	
	public Cliente(String cpf) {
        this.cpf = cpf;
        this.contas = new ArrayList<>();
    }
	
	
	//-----------------------------------------------------------------------
	
	
	public void adicionarConta(IConta ContaBancaria, ArrayList<Cliente> c) {
		if (numeroContaExiste(c, ContaBancaria.getNumeroConta())) {
			System.out.println("Conta já cadastrada");
		} else {
			contas.add(ContaBancaria);
			System.out.println("Conta cadastrada com sucesso");
		}
	}
	
	public void removerConta(int numero) {
        IConta ContaBancaria = buscarConta(numero);
        if (ContaBancaria != null) {
            if (ContaBancaria.getSaldo().compareTo(BigDecimal.ZERO) == 0) {
                contas.remove(ContaBancaria);
                System.out.println("Conta removida com sucesso!");
            } else {
                System.out.println("Conta com saldo, remova o saldo para finalizar a conta!");
            }
        } else {
            System.out.println("Conta não encontrada.");
        }
    }
	
	public void listarContas() {
        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada para este cliente.");
        } else {
            contas.forEach(System.out::println);
        }
    }

	public IConta buscarConta(int numero) {
        for (IConta conta : contas) {
            if (conta.getNumeroConta() == numero) {
                return conta;
            }
        }
        return null;
    }
	
	public void atualizarConta(ContaBancaria c) {
		if(contas.contains(c)) {
			int index = contas.indexOf(c);
			contas.set(index, c);
			System.out.println("Sua conta foi atualizada");
		} else {
			System.out.println("Sua conta não foi encontrada");
		}
	}

	
	//------------------------------------------------------------------------

	
	@Override
	public String toString() {
		return "Cliente [cpf=" + cpf + ", nome=" + nome + ", contas=" + contas + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(cpf, other.cpf);
	}
	
	 public static boolean numeroContaExiste(ArrayList<Cliente> c, int numero) {
	        for (Cliente cliente : c) {
	            for (IConta conta : cliente.getContas()) {
	                if (conta.getNumeroConta() == numero) {
	                    return true;
	                }
	            }
	        }
	        return false;
	    }
	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ArrayList<IConta> getContas() {
		return contas;
	}

	public void setContas(ArrayList<IConta> contas) {
		this.contas = contas;
	}
	
	
}
