package edu.ifsp.seunutri.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.util.Base64;

import org.springframework.stereotype.Service;

@Service
public class SenhaService {

	private static final int ITERACOES = 65536;
	private static final int TAMANHO_CHAVE = 256;
	private static final int TAMANHO_SALT = 16;

	public String hash(String senha) {
		try {
			byte[] salt = new byte[TAMANHO_SALT];
			new SecureRandom().nextBytes(salt);
			PBEKeySpec spec = new PBEKeySpec(senha.toCharArray(), salt, ITERACOES, TAMANHO_CHAVE);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			byte[] hash = skf.generateSecret(spec).getEncoded();
			String saltB64 = Base64.getEncoder().encodeToString(salt);
			String hashB64 = Base64.getEncoder().encodeToString(hash);
			return saltB64 + ":" + hashB64;
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new RuntimeException("Erro ao gerar hash de senha", e);
		}
	}

	public boolean verificar(String senha, String armazenado) {
		try {
			String[] partes = armazenado.split(":");
			if (partes.length != 2) {
				return false;
			}
			byte[] salt = Base64.getDecoder().decode(partes[0]);
			PBEKeySpec spec = new PBEKeySpec(senha.toCharArray(), salt, ITERACOES, TAMANHO_CHAVE);
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			byte[] hash = skf.generateSecret(spec).getEncoded();
			String hashB64 = Base64.getEncoder().encodeToString(hash);
			return hashB64.equals(partes[1]);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			return false;
		}
	}
}
