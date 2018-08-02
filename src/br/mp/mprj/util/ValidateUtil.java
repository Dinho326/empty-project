package br.mp.mprj.util;



import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.apache.commons.lang3.StringUtils;

/**
 * Classe responsável por validar formulário
 * 
 * @author edilson.carvalho
 * @since 01/08/2018
 * @version 1.0
 */
public class ValidateUtil {
	
	private static Log _log =  LogFactoryUtil.getLog(ValidateUtil.class);
	/**
	 * Método para validar se o e-mail segue o padrão de e-mail
	 * @param email
	 * @return boolean
	 */
	public static boolean isEmail(String email) {
		boolean isEmailIdValid = false;
		if (email != null && email.length() > 0) {
			String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
			Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(email);
			if (matcher.matches()) {
				isEmailIdValid = true;
			}
		}
		return isEmailIdValid;
	}

	/**
	 * Método responsável por validar o telefone fixo ou celular, com ou sem o
	 * 9, máscara = (99) 9999?-9999
	 * @param numeroTelefone
	 * @return bollean
	 */
	public static boolean isTelefone(String numeroTelefone) {
		return numeroTelefone
				.matches(".((10)|([1-9][1-9]).)\\s9?[6-9][0-9]{3}-[0-9]{4}")
				|| numeroTelefone.matches(".((10)|([1-9][1-9]).)\\s[2-5][0-9]{3}-[0-9]{4}");
	}

	/**
	 * Método responsável por verificar se a string é composta apenas de números
	 * @param str
	 * @return boolean
	 */
	public static boolean isNumeric(String str) {
		return StringUtils.isNumeric(str);
	}

	/**
	 * Método responsável por validar CPF
	 * @param cpf
	 * @return boolean
	 */
	public static boolean validaCpf(String cpf){
		// considera-se erro CPF's formados por uma sequencia de numeros iguais
		String cpfNovo = cpf.replaceAll("[.-]", "");
		if (cpfNovo.equals("00000000000") || cpfNovo.equals("11111111111")
				|| cpfNovo.equals("22222222222")
				|| cpfNovo.equals("33333333333")
				|| cpfNovo.equals("44444444444")
				|| cpfNovo.equals("55555555555")
				|| cpfNovo.equals("66666666666")
				|| cpfNovo.equals("77777777777")
				|| cpfNovo.equals("88888888888")
				|| cpfNovo.equals("99999999999") || (cpfNovo.length() != 11)) {
			return false;
		}
		char dig10, dig11;
		int sm, i, r, num, peso;
		// "try" - protege o codigo para eventuais erros de conversao de tipo
		// (int)
		try {
			// Calculo do 1o. Digito Verificador
			sm = 0;
			peso = 10;
			for (i = 0; i < 9; i++) {
				// converte o i-esimo caractere do CPF em um numero:
				// por exemplo, transforma o caractere '0' no inteiro 0
				// (48 eh a posicao de '0' na tabela ASCII)
				num = (int) (cpfNovo.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}
			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig10 = '0';
			else
				dig10 = (char) (r + 48); // converte no respectivo caractere
			// Calculo do 2o. Digito Verificador
			sm = 0;
			peso = 11;
			for (i = 0; i < 10; i++) {
				num = (int) (cpfNovo.charAt(i) - 48);
				sm = sm + (num * peso);
				peso = peso - 1;
			}

			r = 11 - (sm % 11);
			if ((r == 10) || (r == 11))
				dig11 = '0';
			else
				dig11 = (char) (r + 48);
			// Verifica se os digitos calculados conferem com os digitos
			// informados.
			if ((dig10 == cpfNovo.charAt(9)) && (dig11 == cpfNovo.charAt(10))) {
				return true;
			} else
				return false;
		} catch (InputMismatchException erro) {
			return false;
		}

	}
	
	/**
	 * Método responsável por validar o tamanho do arquivo
	 * @param actionRequest
	 * @param actionResponse
	 * @param lista
	 * @param limite
	 * @throws IOException
	 */
	public static void validaTamanhoArquivo(ActionRequest actionRequest,
			ActionResponse actionResponse, List<File> lista, double limite) throws IOException {
		double total = 0.0;
		try {
			for(int i=0; i<lista.size(); i++){
				total += lista.get(i).length()/1024.0;
			}
			calculaTamanhoArquivo(total, limite);
		} catch (Exception e) {
			_log.error("Erro excedeu o tamanho do campo Arquivo, Método calculaTamanhoArquivo TOTAL =>" +
					" "+ total +" \n" +limite+" => " + limite , e);
			addError(actionRequest, "anexoTotal");
			ThemeDisplay td = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
			redirect(actionRequest, actionResponse, td, "path_da_jsp");
		}
	}
	
	/**
	 * Método responsável por lançar uma excessão caso o arquivo seja maior que o definido
	 * @param total
	 * @param limite
	 * @throws Exception
	 */
	private static void calculaTamanhoArquivo(double total, double limite)throws Exception{
		double result = total /1024.0;
		if(result>limite){
			throw new Exception();
		}
	}
	
	/**
	 * Método responsável por adicionar um erro na sessão
	 * @param actionRequest
	 * @param key
	 */
	public static void addError(ActionRequest actionRequest, String key) {
		SessionErrors.add(actionRequest.getPortletSession(), key);
		SessionMessages.add(actionRequest, PortalUtil.getPortletId(actionRequest) + SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
	
	}
	
	/**
	 * Método responsável por realizar o redirect para uma determinada page
	 * @param actionRequest
	 * @param actionResponse
	 * @param td
	 * @param path
	 * @throws IOException
	 */
	public static void redirect(ActionRequest actionRequest,ActionResponse actionResponse, ThemeDisplay td, String path)
			throws IOException{

		String portletName = (String) actionRequest.getAttribute(WebKeys.PORTLET_ID);
		PortletURL redirectURL = PortletURLFactoryUtil.create(PortalUtil.getHttpServletRequest(actionRequest), portletName,
				td.getLayout().getPlid(), PortletRequest.RENDER_PHASE);
		redirectURL.setParameter("jspPage", path);
		actionResponse.sendRedirect(redirectURL.toString());
	}

}
