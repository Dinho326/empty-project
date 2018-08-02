package br.mp.mprj.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Nome : DataUtil
 * 
 * @author : Edilson Carvalho
 * @version : version 1.0
 * @since : Data de Criação 23/06/2017
 * 
 * **/
public final class DataUtil {

	public static final String FORMATO_DDMMAAAA_COM_BARRA = "dd/MM/yyyy";
	public static final String FORMATO_DATA_BANCO_DE_DADOS = "yyyy-MM-dd";
	public static final String FORMATO_MMAAAA_COM_BARRA = "MM/yyyy";
	private static Log _log =  LogFactoryUtil.getLog(DataUtil.class);
	
	private static final SimpleDateFormat SDF_DATA_PUBLICACAO = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm");
	
	private static final SimpleDateFormat SDF_APENAS_DATA = new SimpleDateFormat(
			"dd/MM/yyyy");
	
	private static final SimpleDateFormat SDF_ANO_MES_DIA = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static SimpleDateFormat getDateFormat(){
		return SDF_DATA_PUBLICACAO;
	}
	public static String formatarData(Date date){
		return SDF_ANO_MES_DIA.format(date);
	}
	
	public static String formatar(Date date) {
		return SDF_DATA_PUBLICACAO.format(date);
	}

	public static String formatarApenasData(Date date) {
		if (date == null) {
			return "";
		}
		return SDF_APENAS_DATA.format(date);
	}
	
	/**
	 * 
	 * @param data
	 * @return
	 */
	public static Date getData(Date data){
		String dataString = formatar(data);
		return parseDataFormatoBancoDados(dataString);
		
	}

	/**
	 * . Descricao: Metodo converte data de acordo com formato informado
	 * @param Date, String
	 * @param pattern
	 * @return Date
	 */
	public static Date parseData(Object data, String pattern) throws Exception {
		if (data != null) {
			DateFormat df = new SimpleDateFormat(pattern);
			return df.parse(data.toString());
		} else {
			return null;
		}
	}

	/**
	 * . Descricao: Metodo converte data informada para o formato ddmmaaaa com
	 * barra
	 * @param String
	 * @throws Exception
	 * @return Date
	 */
	public static Date parseDataFormatoBancoDados(String dataTexto) {

		SimpleDateFormat format = new SimpleDateFormat(FORMATO_DDMMAAAA_COM_BARRA);
		Date data = null;
		try {
			data = format.parse(dataTexto);
		} catch (ParseException e) {
			_log.error("Erro ao tentar formatar a data ",e);
		}
		return data;
	}

	/**
	 * . Descricao: Metodo converte data da Base de dados para uma String no
	 * formato aceito padrão de tela
	 * @param String
	 * @throws Exception
	 * @return Date
	 */
	public static String formataDataArquivoDataTela(String data)
			throws Exception {
		try {
			SimpleDateFormat df = new SimpleDateFormat(
					FORMATO_DDMMAAAA_COM_BARRA);
			Calendar calendar = Calendar.getInstance();
			int dia = Integer.valueOf(data.substring(0, 2));
			int mes = Integer.valueOf(data.substring(2, 4)) - 1;
			int ano = Integer.valueOf("20" + data.substring(4));
			calendar.set(ano, mes, dia);
			return df.format(calendar.getTime());
		} catch (Exception e) {
			return "";
		}
	}
	
}
