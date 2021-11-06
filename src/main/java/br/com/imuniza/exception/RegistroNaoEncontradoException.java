package br.com.imuniza.exception;

public class RegistroNaoEncontradoException extends RuntimeException {

	private static final long serialVersionUID = 3770847251154799322L;

	public RegistroNaoEncontradoException(String mensagem) {
		super(mensagem);
	}

	public RegistroNaoEncontradoException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
