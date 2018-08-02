package br.mp.mprj.util;


import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.text.Normalizer;

public class NormalizaStringWeb {
	
	private static Log _log = LogFactoryUtil.getLog(NormalizaStringWeb.class);
	
    /*
    * Retorna a string padronizada ao formato web (minÃºscula, nÃ£o terminada em ponto "." e sem caracteres especiais, espaÃ§os, etc).
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

    	  //String charsEspeciais = "..;,;/;';\\;\";!;@;#;$;%;&;*;(;);|;__;[;];{;};=;+;~;Â´;`;^";
//    	  String charsEspeciais = "\".'.!.@.#.$.%.Â¨.&.*.(.)._.+.Â¹.Â².Â³.Â£.Â¢.Â¬.=.Â§.Â´.`.[.].Âª.Âº.{.}.^.~.?.\\./.Â°.:.;.,.<.>.|.â”¤.â–“.â–’.â–‘.Â».Â«.Â¼.Â½.Â®.Â¿.Æ’.Ã˜.Ã¸.Ã†.Ã¦";
	    	String charsEspeciais = str;
	        charsEspeciais = charsEspeciais.replaceAll("[ÂÀÁÄÃ]", "A");
	        charsEspeciais = charsEspeciais.replaceAll("[âãàáä]", "a");
	        charsEspeciais = charsEspeciais.replaceAll("[ÊÈÉË]", "E");
	        charsEspeciais = charsEspeciais.replaceAll("[êèéë]", "e");
	        charsEspeciais = charsEspeciais.replaceAll("[ÎÍÌÏ]", "I");
	        charsEspeciais = charsEspeciais.replaceAll("[îíìï]", "i");
	        charsEspeciais = charsEspeciais.replaceAll("[ÔÕÒÓÖ]", "O");
	        charsEspeciais = charsEspeciais.replaceAll("[ôõòóö]", "o");
	        charsEspeciais = charsEspeciais.replaceAll("[ÛÙÚÜ]", "U");
	        charsEspeciais = charsEspeciais.replaceAll("[ûúùü]", "u");
	        charsEspeciais = charsEspeciais.replaceAll("[Ç]", "C");
	        charsEspeciais = charsEspeciais.replaceAll("[ç]", "c");
	        charsEspeciais = charsEspeciais.replaceAll("[ýÿ]", "y");
	        charsEspeciais = charsEspeciais.replaceAll("[Ý]", "Y");
	        charsEspeciais = charsEspeciais.replaceAll("[ñ]", "n");
	        charsEspeciais = charsEspeciais.replaceAll("[Ñ]", "N");
	        charsEspeciais = charsEspeciais.replaceAll("[-+=*&;%$#@!_]", "_");
	        charsEspeciais = charsEspeciais.replaceAll("['\"]", "_");
	        charsEspeciais = charsEspeciais.replaceAll("[<>()\\{\\}]", "_");
	        charsEspeciais = charsEspeciais.replaceAll("['\\\\,()|/]", "_");
	        charsEspeciais = charsEspeciais.replaceAll("[^!-ÿ]{1}[^ -ÿ]{0,}[^!-ÿ]{1}|[^!-ÿ]{1}", "");
	        
	       int lastIndex= charsEspeciais.lastIndexOf(".");
	       String extensao = charsEspeciais.substring(lastIndex);
	       String palavraTotal = charsEspeciais.substring(0, lastIndex);
	       palavraTotal = palavraTotal.replaceAll("[\\.]", "");
	       String arquivoNome = palavraTotal+extensao;
	       return arquivoNome;
    }
    
    /*
    * Remove espaÃ§o (" ") no inÃ­cio e final da string.
    * Remove tabulaÃ§Ãµes e espaÃ§os duplicados da string. 
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
    * Remove duplicidade dos parÃ¢metros "." e "_" em relaÃ§Ã£o ao prÃ³ximo elemento.
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
    * Remove a Ãºltima posiÃ§Ã£o da string, caso termine em ponto (".").
    */
    public String removePontoFinal(String texto){
          
          StringBuilder novaString = new StringBuilder(texto);
          
          if(novaString.substring(novaString.length()-1, novaString.length()).equals(".")){
          novaString.delete(novaString.length()-2, novaString.length()-1);
          } 
          return novaString.toString();

    }

}
