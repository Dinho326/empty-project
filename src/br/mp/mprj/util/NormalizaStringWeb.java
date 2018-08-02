package br.mp.mprj.util;


import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.text.Normalizer;

public class NormalizaStringWeb {
	
	private static Log _log = LogFactoryUtil.getLog(NormalizaStringWeb.class);
	
    /*
    * Retorna a string padronizada ao formato web (minúscula, não terminada em ponto "." e sem caracteres especiais, espaços, etc).
    */
    public String normalizaStringWeb(String string){
    	  String teste = string;
    	  teste = string.toLowerCase();
    	  teste = removeEspacos(teste);     
          teste = normalizaTexto(teste);
          teste = removeDuplicidade(teste);
          teste = removerCaracteresEspeciais(teste);
          teste = removePontoFinal(teste);
          
          return teste;
    }
    
   public String removerTeste(String str){
	   return  Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    	
    }
    /*
    * Remove caracteres especiais latinos.
    */
    public String removerCaracteresEspeciais(String str) {

    	  //String charsEspeciais = "..;,;/;';\\;\";!;@;#;$;%;&;*;(;);|;__;[;];{;};=;+;~;´;`;^";
//    	  String charsEspeciais = "\".'.!.@.#.$.%.¨.&.*.(.)._.+.¹.².³.£.¢.¬.=.§.´.`.[.].ª.º.{.}.^.~.?.\\./.°.:.;.,.<.>.|.┤.▓.▒.░.».«.¼.½.®.¿.ƒ.Ø.ø.Æ.æ";
	    	String charsEspeciais = str;
	        charsEspeciais = charsEspeciais.replaceAll("[�����]", "A");
	        charsEspeciais = charsEspeciais.replaceAll("[�����]", "a");
	        charsEspeciais = charsEspeciais.replaceAll("[����]", "E");
	        charsEspeciais = charsEspeciais.replaceAll("[����]", "e");
	        charsEspeciais = charsEspeciais.replaceAll("[����]", "I");
	        charsEspeciais = charsEspeciais.replaceAll("[����]", "i");
	        charsEspeciais = charsEspeciais.replaceAll("[�����]", "O");
	        charsEspeciais = charsEspeciais.replaceAll("[�����]", "o");
	        charsEspeciais = charsEspeciais.replaceAll("[����]", "U");
	        charsEspeciais = charsEspeciais.replaceAll("[����]", "u");
	        charsEspeciais = charsEspeciais.replaceAll("[�]", "C");
	        charsEspeciais = charsEspeciais.replaceAll("[�]", "c");
	        charsEspeciais = charsEspeciais.replaceAll("[��]", "y");
	        charsEspeciais = charsEspeciais.replaceAll("[�]", "Y");
	        charsEspeciais = charsEspeciais.replaceAll("[�]", "n");
	        charsEspeciais = charsEspeciais.replaceAll("[�]", "N");
	        charsEspeciais = charsEspeciais.replaceAll("[-+=*&;%$#@!_]", "_");
	        charsEspeciais = charsEspeciais.replaceAll("['\"]", "_");
	        charsEspeciais = charsEspeciais.replaceAll("[<>()\\{\\}]", "_");
	        charsEspeciais = charsEspeciais.replaceAll("['\\\\,()|/]", "_");
	        charsEspeciais = charsEspeciais.replaceAll("[^!-�]{1}[^ -�]{0,}[^!-�]{1}|[^!-�]{1}", "");
	        
	       int lastIndex= charsEspeciais.lastIndexOf(".");
	       String extensao = charsEspeciais.substring(lastIndex);
	       String palavraTotal = charsEspeciais.substring(0, lastIndex);
	       palavraTotal = palavraTotal.replaceAll("[\\.]", "");
	       String arquivoNome = palavraTotal+extensao;
	       return arquivoNome;
    }
    
    /*
    * Remove espaço (" ") no início e final da string.
    * Remove tabulações e espaços duplicados da string. 
     */
    public String removeEspacos(String texto) {
          texto = texto.replaceAll("^\\s+", "");
          texto = texto.replaceAll("\\s+$", "");
          texto = texto.replaceAll("\\s+", "");
          return texto;
    }
    
    /*
    * Normaliza o texto para formato ASCII (latino).
    * Substitui " " por "_".
    * Substitui "-" por "_".
    */
    public String normalizaTexto(String texto) {
          texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
          texto = texto.replaceAll("[^\\p{ASCII}]", "");
          texto = texto.replaceAll(" ", "_");
          texto = texto.replaceAll("-", "_");
          return texto;
    }
    
    /*
    * Remove duplicidade dos parâmetros "." e "_" em relação ao próximo elemento.
    */
    public String removeDuplicidade(String texto){
          
          StringBuilder novaString = new StringBuilder(texto);
          
          for(int i = 0 ; i < novaString.length() ; i++ ){    
                 if (novaString.substring(i,i+1).equals(".") && novaString.substring(i,i+1).contains(novaString.substring(i+1,i+2))){
                       novaString.delete(i, i+1);
                       i = i-1;
                 }
          }
          for(int i = 0 ; i < novaString.length() ; i++ ){
                 if (novaString.substring(i,i+1).equals("_") && novaString.substring(i,i+1).contains(novaString.substring(i+1,i+2))){
                        novaString.delete(i, i+1);
                        i = i-1;
                 }
          }
          return novaString.toString();
    }
    
    /*
    * Remove a última posição da string, caso termine em ponto (".").
    */
    public String removePontoFinal(String texto){
          
          StringBuilder novaString = new StringBuilder(texto);
          
          if(novaString.substring(novaString.length()-1, novaString.length()).equals(".")){
          novaString.delete(novaString.length()-2, novaString.length()-1);
          } 
          return novaString.toString();

    }

}
