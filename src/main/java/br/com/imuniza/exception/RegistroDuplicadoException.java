package br.com.imuniza.exception;

public class RegistroDuplicadoException extends RuntimeException {

	private static final long serialVersionUID = 3770847251154799322L;

	public RegistroDuplicadoException(String mensagem) {
		super(mensagem);
	}

	public RegistroDuplicadoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}
}
