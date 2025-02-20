package bancario.projeto.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import exceptions.ContaInexistenteException;
import exceptions.SaldoInsuficienteException;
import exceptions.StatusException;
import exceptions.ValorInvalidoExeception;

public abstract class ContaBancaria implements IConta, Serializable {
    private static final long serialVersionUID = 1L;

    private Integer numeroConta;
    private BigDecimal saldo;
    private LocalDateTime dataAbertura;
    private boolean status;
    private List<Transacao> historico;

    public ContaBancaria(Integer numeroConta) {
        this.numeroConta = numeroConta;
        this.saldo = BigDecimal.ZERO;
        this.dataAbertura = LocalDateTime.now();
        this.status = true;
        this.historico = new ArrayList<>();
    }

    
    public void depositar(BigDecimal quantia) throws ContaInexistenteException, ValorInvalidoExeception {
        if (!status) {
            throw new ContaInexistenteException("Operação não permitida. Conta desativada.");
        }
        if (quantia.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoExeception("Valor inválido para depósito.");
        }
        saldo = saldo.add(quantia);
        System.out.println("Depósito realizado com sucesso.");
        System.out.println("Seu saldo atual é de: " + saldo);
    }

    public void sacar(BigDecimal quantia) throws ContaInexistenteException, ValorInvalidoExeception, SaldoInsuficienteException {
        if (!status) {
            throw new ContaInexistenteException("Operação não permitida. Conta desativada.");
        }
        if (quantia.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoExeception("Valor inválido para saque.");
        }
        BigDecimal tarifa = calcularTarifaSaque(quantia);
        BigDecimal valorTotal = quantia.add(tarifa);

        if (saldo.compareTo(valorTotal) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para saque (incluindo tarifa de R$ " + tarifa + ").");
        }

        saldo = saldo.subtract(valorTotal);
        historico.add(new Transacao("Saque", quantia, this.numeroConta, 0, tarifa));
        System.out.println("Saque de R$ " + quantia + " realizado com sucesso! (Tarifa: R$ " + tarifa + ")");
    }

    public abstract void transferir(IConta cdestino, BigDecimal quantia) throws ContaInexistenteException, SaldoInsuficienteException, ValorInvalidoExeception;


    public abstract BigDecimal calcularTarifaSaque(BigDecimal quantia);

    public void extratoPorMesAno(int mes, int ano) {
        System.out.println("Seu extrato para o mês " + mes + "/" + ano + " é:");
        boolean encontrou = false;

        for (int i = 0; i < historico.size(); i++) {
            Transacao transacao = historico.get(i);
            if (transacao.getDataHora().getMonthValue() == mes && transacao.getDataHora().getYear() == ano) {
                System.out.println(transacao);
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println("Você não realizou nenhuma transação nesse período.");
        }
    }

    public void ativar() {
        this.status = true;
    }

    public void desativar() throws StatusException {
        if (this.saldo.compareTo(BigDecimal.ZERO) == 0) {
            this.status = false;
            System.out.println("Conta desativada com sucesso.");
        } else {
            throw new StatusException("Você não pode desativar sua conta se ainda tiver dinheiro nela! Transfira ou saque o valor restante!");
        }
    }

    // Getters e setters
    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public List<Transacao> getHistorico() {
        return historico;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public Integer getNumeroConta() {
        return numeroConta;
    }

    public boolean isAtiva() {
        return status;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        return "ContaBancaria [numeroConta=" + numeroConta + ", saldo=" + saldo + ", dataAbertura=" + dataAbertura
                + ", status=" + (status ? "Ativa" : "Inativa") + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroConta);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ContaBancaria other = (ContaBancaria) obj;
        return numeroConta.equals(other.numeroConta);
    }
}