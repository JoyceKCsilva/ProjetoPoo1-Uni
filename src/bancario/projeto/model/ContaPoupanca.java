package bancario.projeto.model;

import java.math.BigDecimal;
import exceptions.ContaInexistenteException;
import exceptions.SaldoInsuficienteException;
import exceptions.ValorInvalidoExeception;

public class ContaPoupanca extends ContaBancaria {

    private static final long serialVersionUID = 1L;

    public ContaPoupanca(Integer numeroConta) {
        super(numeroConta);
    }

    @Override
    public void transferir(IConta cdestino, BigDecimal quantia) throws ContaInexistenteException, SaldoInsuficienteException, ValorInvalidoExeception {
        if (!this.isAtiva()) {
            throw new ContaInexistenteException("Não é possível transferir de uma conta inativa.");
        }
        if (!cdestino.isAtiva()) {
            throw new ContaInexistenteException("Não é possível transferir para uma conta inativa.");
        }
        if (quantia.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoExeception("O valor da transferência deve ser maior que zero.");
        }
        if (quantia.compareTo(this.getSaldo()) > 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente para transferência.");
        }

        this.setSaldo(this.getSaldo().subtract(quantia));
        cdestino.setSaldo(cdestino.getSaldo().add(quantia));

        getHistorico().add(new Transacao("Transferência enviada", quantia, this.getNumeroConta(), cdestino.getNumeroConta(), BigDecimal.ZERO));
        cdestino.getHistorico().add(new Transacao("Transferência recebida", quantia, this.getNumeroConta(), cdestino.getNumeroConta(), BigDecimal.ZERO));

        System.out.println("Transferência de R$ " + quantia + " realizada com sucesso!");
    }

    @Override
    public BigDecimal calcularTarifaSaque(BigDecimal valor) {
        return valor.multiply(new BigDecimal("0.02")); // 2% de tarifa
    }
}