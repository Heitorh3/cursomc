package br.com.heitor.cursomc.domain;

public enum TipoCliente {

	PESSOFISICA("Pessoa Física"), 
	PESSOAJURIDICA("Pessoa Jurídica");

	private String descricao;

	TipoCliente(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
