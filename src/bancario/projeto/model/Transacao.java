package bancario.projeto.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transacao implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String tipo;
    private BigDecimal valor;
    private int numeroOrigem;
    private int numeroDestino;
    private BigDecimal tarifa;
    private LocalDateTime dataHora;

    public Transacao(String tipo, BigDecimal valor, int numeroOrigem, int numeroDestino, BigDecimal tarifa) {
        this.tipo = tipo;
        this.valor = valor;
        this.numeroOrigem = numeroOrigem;
        this.numeroDestino = numeroDestino;
        this.tarifa = tarifa;
        this.dataHora = LocalDateTime.now();
    }

    public Transacao(String tipo, BigDecimal valor, int numeroOrigem, int numeroDestino) {
        this(tipo, valor, numeroOrigem, numeroDestino, BigDecimal.ZERO);
    }

    public Transacao(String tipo, BigDecimal valor) {
        this(tipo, valor, 0, 0, BigDecimal.ZERO);
    }

    // Getters e setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public int getNumeroOrigem() {
        return numeroOrigem;
    }

    public void setNumeroOrigem(int numeroOrigem) {
        this.numeroOrigem = numeroOrigem;
    }

    public int getNumeroDestino() {
        return numeroDestino;
    }

    public void setNumeroDestino(int numeroDestino) {
        this.numeroDestino = numeroDestino;
    }

    public BigDecimal getTarifa() {
        return tarifa;
    }

    public void setTarifa(BigDecimal tarifa) {
        this.tarifa = tarifa;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    @Override
    public String toString() {
        return "Transacao {\n" +
               "    Tipo: " + tipo + "\n" +
               "    Valor: R$ " + valor + "\n" +
               "    Tarifa: R$ " + tarifa + "\n" +
               "    Data e Hora: " + dataHora + "\n" +
               "    Conta Origem: " + numeroOrigem + "\n" +
               "    Conta Destino: " + numeroDestino + "\n" +
               "}";
    }
}
