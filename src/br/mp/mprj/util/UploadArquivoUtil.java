package br.mp.mprj.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.portlet.ActionRequest;
import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.util.portlet.PortletProps;

/**
 * Classe responsável por criar folder, verificar folder e adicionar arquivos 
 * @author   edilson.carvalho
 * @version	 1.0
 * @since    09/11/2017
 * **/
public class UploadArquivoUtil {
	
	private static Log _log = LogFactoryUtil.getLog(UploadArquivoUtil.class);
	private static String ROOT_FOLDER_NAME = PortletProps.get("fileupload.folder.name");
	private static String ROOT_FOLDER_DESCRIPTION = PortletProps.get("fileupload.folder.description");
	private static long PARENT_FOLDER_ID = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
	public static final String VAZIO = "";
	private static final String DOCUMENTS = "documents/";
	private static final SimpleDateFormat SDF_APENAS_HORA = new SimpleDateFormat("hh:mm:ss");
	private static long folderID;
	private static String nome;
	private static double tamanho;
	
	/**
	 * Método responsável por criar um folder, caso não exista um.
	 * @author   edilson.carvalho
	 * @since    09/11/2017
	 * @param ActionRequest,ThemeDisplay
	 * @return Folder
	 * **/
	public Folder createFolder(ActionRequest actionRequest,ThemeDisplay themeDisplay)
	{
        boolean folderExist = isFolderExist(themeDisplay);
        Folder folder = null;
		if (!folderExist) {
			long repositoryId = themeDisplay.getScopeGroupId();		
			try {
				ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFolder.class.getName(), actionRequest);
				folder = DLAppLocalServiceUtil.addFolder(themeDisplay.getDefaultUserId(),repositoryId,PARENT_FOLDER_ID, ROOT_FOLDER_NAME,ROOT_FOLDER_DESCRIPTION, serviceContext);
			} catch (PortalException e1) {
				_log.error("Erro ao tentar criar folder",e1);
			} catch (SystemException e1) {
				_log.error("Erro genérico tentar criar folder",e1);
			}			
		}
		return folder;
	}

	
	/**
	 * Método responsável por verificar se já existe o folder
	 * caso não, cria um novo folder.
	 * @author   edilson.carvalho
	 * @since    09/11/2017
	 * @param ActionRequest,ThemeDisplay
	 * @return boolean
	 * **/
	public boolean isFolderExist(ThemeDisplay themeDisplay){
		boolean folderExist = false;
		try {
			DLAppLocalServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, ROOT_FOLDER_NAME);
			folderExist = true;
		} catch (Exception e) {	
			_log.error("Erro genérico tentar verificar a existência do folder",e);
		}
		return folderExist;
	}
	
	
	/**
	 * Método responsável por enviar o arquivo para o documents and media.
	 * @author   edilson.carvalho
	 * @since    09/11/2017
	 * @param ActionRequest,ThemeDisplay
	 * **/
	public void fileUpload(ThemeDisplay themeDisplay,ActionRequest actionRequest, UploadPortletRequest uploadReq)
	{
		String fileName = getNameArchive(uploadReq.getFileName("uploadedFile"));
		File file = uploadReq.getFile("uploadedFile");
		String mimeType = uploadReq.getContentType("uploadedFile");
        String title = fileName;
		String description = Constantes.DESCRICAO_ARQUIVO;
		long repositoryId = themeDisplay.getScopeGroupId();
	    try
	    {  
	    	Folder folder = getFolder(themeDisplay);
	    	ServiceContext serviceContext = ServiceContextFactory.getInstance(DLFileEntry.class.getName(), actionRequest);
	    	DLAppLocalServiceUtil.addFileEntry(themeDisplay.getDefaultUserId(), repositoryId, folder.getFolderId(), fileName, mimeType,
	    			title, description, "", file, serviceContext);
	    	setFolderID(folder.getFolderId());
	    	setNome(fileName);
	    	setTamanho(file.length()/1024);
	    	
	     } catch (Exception e) {
	    	 _log.error("Erro genérico tentar realizar o upload",e);
	    }

	}
	
	
	/**
	 * Método responsável por retornar 
	 * @author   edilson.carvalho
	 * @since    09/11/2017
	 * @param ActionRequest,ThemeDisplay
	 * @return Folder
	 * **/
	public Folder getFolder(ThemeDisplay themeDisplay){
		Folder folder = null;
		try {
			folder =DLAppLocalServiceUtil.getFolder(themeDisplay.getScopeGroupId(), PARENT_FOLDER_ID, ROOT_FOLDER_NAME);
		} catch (Exception e) {	
			_log.error("Erro genérico tentar retornar um folder",e);
		}
		return folder;
	}
	
	/**
	 * Método responsável por alterar o nome do arquivo
	 * para não ter conflito no momento de salvar o arquivo
	 * @author edilson.carvalho
	 * @param name
	 * @return name
	 */
	public String getNameArchive(String name){
		NormalizaStringWeb normaliza = new NormalizaStringWeb();
		name = normaliza.removerCaracteresEspeciais(name);
		int end = name.lastIndexOf('.');
		String nome = name.substring(0,end);
		String fim = name.substring(end);
		Date data = new Date();
		name = nome.concat(SDF_APENAS_HORA.format(data).replaceAll(":", "")).concat(fim);
		
		return name;
	}
	
	public static long getFolderID() {
		return folderID;
	}


	public static void setFolderID(long l) {
		UploadArquivoUtil.folderID = l;
	}


	public static String getNome() {
		return nome;
	}


	public static void setNome(String nome) {
		UploadArquivoUtil.nome = nome;
	}


	public static double getTamanho() {
		return tamanho;
	}


	public static void setTamanho(double tamanho) {
		UploadArquivoUtil.tamanho = tamanho;
	}

	
}

