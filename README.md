# empty-project
<!DOCTYPE html>
<html>
<body>

<h1>Simples portlet em Liferay<h1>

  <h3>Configuração</h3>
  

<ul>
  <h4><li>Passo 1</h4></li>
  <p>
      Primeiro é necessário ter o ambiente do Liferay configurado, caso ainda não tenha, 
    é necessário ler a documentação para facilitar na configuração do projeto. 
    <br> Segue link 
    <a href="https://dev.liferay.com/pt/discover/deployment?p_p_id=2_WAR_knowledgebaseportlet&p_p_lifecycle=0&_2_WAR_knowledgebaseportlet_kbFolderUrlTitle=6-2">Documentação do Liferay</a>
  </p>
  
  <h4><li>Passo 2</h4></li>
  <p>
     Desconsidere o passo 1 caso já tenha o ambiente Liferay configurado. É necessário fazer o checkout ou donwload do projeto e de           preferência inserir no plugin SDK no seu ambiente.
      Exemplo do path onde colocar o projeto ->   liferay-plugins-sdk-6.2\portlets
  </p>
    
  <h4><li>Passo 3</h4></li>
  
  <p>
     No passo 3 é necessário importar o projeto, caso esteja usando a IDE eclipse, é só clicar com o botão direito no painel do lado          esquerdo onde ficam os projetos e escolher a opção <strong>import</strong> na janela que abrir procurar por <strong>Liferay              Projects from Plugin SDK</strong> escolher essa opção e selecionar seu projeto na lista de projetos exibidos.
  </p>
  
   <h4><li>Passo 4</h4></li>
  
  <p>
     No passo 4 é necessário configurar o deploy do maven, é só modificar no pom.xml a  linha 140 <strong>e colocar o caminho referente      a pasta deploy do seu ambiente</strong>. 
  </p>
  
</ul> 

</body>
</html>
