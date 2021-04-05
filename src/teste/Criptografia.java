package teste;

import javax.crypto.*;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Criptografia {

    public void hash(){
        String senhaAdmin = "admin";
        MessageDigest algorithm;
        try {
            algorithm = MessageDigest.getInstance("SHA-256");

        byte messageDigestSenhaAdmin[] = algorithm.digest(senhaAdmin.getBytes("UTF-8"));

        StringBuilder hexStringSenhaAdmin = new StringBuilder();
        for (byte b : messageDigestSenhaAdmin) {
            hexStringSenhaAdmin.append(String.format("%02X", 0xFF & b));
        }
        String senhahexAdmin = hexStringSenhaAdmin.toString();

        System.out.println(senhahexAdmin);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public void cripto(){
        try{
            KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
            SecretKey chaveDES = keygenerator.generateKey();

            Cipher cifraDES = Cipher.getInstance("DES/ECB/PKCS5Padding");

            // Inicializa a cifra para o processo de encriptação
            cifraDES.init(Cipher.ENCRYPT_MODE, chaveDES);

            // Texto puro
            byte[] textoPuro = "Exemplo de texto puro".getBytes();

            System.out.println("Texto [Formato de Byte] : " + textoPuro);
            System.out.println("Texto Puro : " + new String(textoPuro));

            // Texto encriptado
            byte[] textoEncriptado = cifraDES.doFinal(textoPuro);

            System.out.println("Texto Encriptado : " + textoEncriptado);

            // Inicializa a cifra também para o processo de decriptação
            cifraDES.init(Cipher.DECRYPT_MODE, chaveDES);

            // Decriptografa o texto
            byte[] textoDecriptografado = cifraDES.doFinal(textoEncriptado);

            System.out.println("Texto Decriptografado : " + new String(textoDecriptografado));

        }catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e){
            e.printStackTrace();
        }
    }

    public void criptobase64(){
        String textoOriginal = "esta é uma string de teste para serialização/deserialização em Base64";

        System.out.println("Texto original: " + textoOriginal);

        String textoSerializado = Base64.getEncoder().encodeToString(textoOriginal.getBytes());

        System.out.println("Texto em Base64: " + textoSerializado);

        String textoDeserializado = new String(Base64.getDecoder().decode(textoSerializado));

        System.out.println("Texto deserializado: "+ textoDeserializado);

    }

}
