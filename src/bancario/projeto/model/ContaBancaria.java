package bancario.projeto.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class ContaBancaria implements Serializable {
	 private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer numeroConta;
	private float saldo;
	private LocalDateTime dataAbertura;
	private boolean status;

	public ContaBancaria(Integer numero) {
		this.numeroConta = numero;
		this.saldo = 0f;
		this.dataAbertura = LocalDateTime.now();
		this.status = true;
	}
	
	//------------------------------------------------------------------

	public void depositar(float quantia) {
		if (!status) {
			System.out.println("Operação não permitida. Conta desativada.");
	        return;
	    }
	    if (quantia <= 0) {
	    	System.out.println("Valor inválido para depósito.");
	        return;
	    }
	    this.saldo += quantia;
	    System.out.println("Depósito realizado com sucesso.");
	}

	public void sacar(float quantia) {
		if (!status) {
			System.out.println("Operação não permitida. Conta desativada.");
	        return;
	    }

	    if (quantia <= 0) {
	    	System.out.println("Valor inválido para saque.");
	        return;
	    }

	    if (this.saldo < quantia) {
	    	System.out.println("Saldo insuficiente.");
	        return;
	    }

	    this.saldo -= quantia;
	    System.out.println("Saque realizado com sucesso!");

	}

	public void transferir(ContaBancaria c, float quantia) {
		if (!status || !c.isStatus()) {
			System.out.println("Operação não pode ser realizada entre contas desativadas.");
	        return;
	    }
	    if (quantia <= 0) {
	    	System.out.println("Valor inválido para transferência.");
	        return;
	    }
	    if (this.saldo < quantia) {
	    	System.out.println("Saldo insuficiente para realizar a transferência.");
	        return;
	    }

	    this.saldo -= quantia;
	    c.saldo += quantia;
	    System.out.println("Transferência realizada com sucesso!");
	    System.out.println("Saldo após a transferência:");
	    System.out.println("Conta origem: " + this.saldo);
	    System.out.println("Conta destino: " + c.getSaldo());
	}

	
	//------------------------------------------------------------------
	
	
	@Override
	public int hashCode() {
		return Objects.hash(numeroConta);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContaBancaria other = (ContaBancaria) obj;
		return Objects.equals(numeroConta, other.numeroConta);
	}
	
	
	@Override
	public String toString() {
		return "ContaBancaria [numeroConta=" + numeroConta + ", saldo=" + saldo + ", dataAbertura=" + dataAbertura
				+ ", status=" + status + "]";
	}

	public Integer getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(Integer numeroConta) {
		this.numeroConta = numeroConta;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}

	public LocalDateTime getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(LocalDateTime dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
