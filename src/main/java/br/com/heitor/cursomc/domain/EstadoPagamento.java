package br.com.heitor.cursomc.domain;

public enum EstadoPagamento {

	PENDENTE("Pendente"),
	CANCELADO("Cancelado"),
	QUITADO("Quitado");

	private String descricao;

	EstadoPagamento(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
