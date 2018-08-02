package br.mp.mprj.util;

import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.util.ContentUtil;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.InternetAddress;
import javax.portlet.ActionRequest;

public class FormUtil {
	private static Log _log =  LogFactoryUtil.getLog(FormUtil.class);
	private static final String ASSUNTO =  "Assunto do e-mail";
	
	public static void enviaEmail(ActionRequest req, String[] nomesArq, List<File> arquivos, String emailGestor,
			 String emailUser, String nome) {
		try {
			
			if(validarEmail(emailGestor) && validarEmail(emailUser)){
			MailMessage mailMessage = new MailMessage();

			String body = ContentUtil.get("content/templates/modelo-email.html", true);

			body = StringUtil.replace(body, new String[] {"[$NOME$]",},new String[] { nome});

			mailMessage.setBody(body);
			mailMessage.setSubject(ASSUNTO);
			mailMessage.setHTMLFormat(true);

			mailMessage.setFrom(new InternetAddress(emailGestor));
			mailMessage.setTo(new InternetAddress(emailUser));

			// anexa os arquivos para area responsavel
			int i = 0;
			if (arquivos != null) {
				for (File file : arquivos) {
					if (file != null && file.exists() && file.length() > 1) {
						mailMessage.addFileAttachment(file, nomesArq[i]);
						i++;
					}
				}
			}

			
				MailServiceUtil.sendEmail(mailMessage);
			}

		} catch (Exception e) {
			_log.error("Erro ao enviar e-mail: ", e);
		}
	}
	
	
	
	/**
	 * Método para validar se o e-mail segue o padrão de e-mail
	 * @param email
	 * @return
	 */
	public static boolean validarEmail(String email)
    {
        boolean isEmailIdValid = false;
        if (email != null && email.length() > 0) {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                isEmailIdValid = true;
            }
        }
        return isEmailIdValid;
    }
}
