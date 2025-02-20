package bancario.projeto.model;

import java.math.BigDecimal;
import java.util.List;

import exceptions.ContaInexistenteException;
import exceptions.SaldoInsuficienteException;
import exceptions.StatusException;
import exceptions.ValorInvalidoExeception;
import bancario.projeto.model.IConta;


public interface IConta {

	void depositar(BigDecimal quantia) throws ContaInexistenteException, ValorInvalidoExeception;
    
    void sacar(BigDecimal quantia) throws ContaInexistenteException, SaldoInsuficienteException, ValorInvalidoExeception;
    
    void transferir(IConta cdestino, BigDecimal quantia) throws ContaInexistenteException, SaldoInsuficienteException, ValorInvalidoExeception;
    
    BigDecimal getSaldo();
    
    Integer getNumeroConta();
    
    boolean isAtiva(); 
    
    void setSaldo(BigDecimal saldo); 
    
    void ativar();
    
    void desativar() throws StatusException;

    
    void extratoPorMesAno(int mes, int ano);

    List<Transacao> getHistorico();

}
