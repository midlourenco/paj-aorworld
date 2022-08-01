# Projeto 5 - PAJ 2021/22

## Autor:
* Mariana Lourenço

## Objectivo:
Este projeto visa a implementação em ReactJS de uma plataforma de gestão de projetos e notícias da organização AoR World com um servidor REST e com persistência de dados guardados em base de dados MySQL, usando Java Peristence API (JPA) com hibernate. Introduzindo também componentes que utilizam Websocket, testes unitários e internacionalização.
<br />
A aplicação web deve permitir a utilizadores criar projectos e notícias que possam estar associados entre si, com utilizadores e ou palavras-chaves. Deve existir 3 níveis de privilégios de utilizadores (utilizador sem sessão iniciada, utilizador com privilégios de admin de sistema e utilizador com privilégios de membro). Consoante os privilégios do utilizador atual a informação exibida pode variar.


## Estrutura da aplicação:
- Página principal
- Login
- Reset de palavra-passe
- Registo de novo utilizador
- Fórum de notícias e de projeto
- Criação de notícias e de projeto
- Pesquisa de notícuas e projetos por palavra-chave
- Ver detalhe de notícia e de projeto
- Editar detalhes de notícias e de projetos
- Permitir associações de notícias e projetos entre si, bem como a palavras-chave e a utilizadores
- Lista de utilizadores registados
- Detalhes do perfil do utilizador, projeto e notícias aos quais está associado
- Editar detalhes do perfil do utilizador
- Gestão de privilégios de administradores
- Dashboard para administradores
- Internacionalização: idiomas suportados PT-PT e EN
- EXTRA: notificações de associações com possibilidade de serem marcadas como lidas


## Frameworks utilizadas:
### Backend:
- Java EE
- REST
- JPA
- CDI
- Websocket
- JUnit e Mockito
- MySQL database
- API criteria e JPQL queries

### Frontend:
- ReactJS
- Redux
- Chakra-UI
- React-router
- use-http
- intl (i18n)

## Detalhes principais:

<img src="https://user-images.githubusercontent.com/84135100/182206967-7884601f-987a-444e-b6e4-1d251a2a0c5a.png" alt="home" width="300"/>


### Login:
Do lado do servidor usou-se encriptação da password e token UUID associado a cada sessão.
<p float="left">
  <img src="https://user-images.githubusercontent.com/84135100/182206998-120c1a25-7c6a-4245-b24b-2abd7b2f9f73.png" alt="login-pt" width="300"/>

  <img src="https://user-images.githubusercontent.com/84135100/182207009-4304e664-39c4-4ba4-b834-249225b7618f.png" alt="login-en" width="300"/>
 </p>
<img src="https://user-images.githubusercontent.com/84135100/182207122-4d4765e4-9d2a-40e9-b4d8-d8b676c5c987.png" alt="reset-password" width="300"/>

### Registo de novo utilizador
<img src="https://user-images.githubusercontent.com/84135100/182207054-5f71cad0-2f35-4b87-a827-3964d700c4d7.png" alt="user-register" width="300"/>

### Forum de Notícias:
Para cada notícia podemos ver os projetos, as pessoas e as palavras-chaves associadas.
É possível pesquisar e filtrar notícias por palavra-chave associada.
É possível visualizar notícias atuais e notícias arquivadas.
Para as imagens existe uma fallback imagem caso o link que se coloque não seja de uma imagem.
A criação de uma notícia é feita com um stepper em 3 passos (detalhes da notícia, associar utilizadores, associar projetos).
<img src="https://user-images.githubusercontent.com/84135100/182203152-51d69d3b-90a3-4974-ab99-e6f0bbd1045a.png" alt="news-forum" width="500"/>
<p float="left">
  <img src="https://user-images.githubusercontent.com/84135100/182204315-52e3f440-0d6f-4faf-9074-92a11d984ba9.png" alt="news-details" width="300"/>
  <img src="https://user-images.githubusercontent.com/84135100/182204755-a8ad6e43-57d2-4d73-88fa-888d40d02993.png" alt="news-creation" width="300"/>
</p>

### Forum de Projetos:
Semelhante aos detalhes de implemtação das notícias.
Excepto no que toca à associação com utilizadores, onde é possível acrescentar o cargo desse utilizador no projeto a que está a ser associado.
A asssociação de utilizadores gera o envio de notificações.
O convite de associação é automaticamente aceite (embora a API permita a implementação de um sistema de associação completa apenas após aceitação de convite).
<br />
<img src="https://user-images.githubusercontent.com/84135100/182203404-9960df56-abfb-4563-a897-0b995c284e29.png" alt="project-forum" width="500"/>

### Sobre a Equipa
No perfil de cada utilizador para além da sua informação pessoal, conseguimos ver os projetos aos quais está associado. Caso se trate do perfil do utilizador atual ou utilizador com privilégios de admin é possível editar todos os campos. Um utilizador com privilégios de admin pode ainda alterar os privilégios do utilizador que está a visualizar.

 <img src="https://user-images.githubusercontent.com/84135100/182203886-9fdcd601-5223-4d3e-828c-2c25a6a0ebb9.png" alt="users-list" width="500"/>
 <p float="left">
  <img src="https://user-images.githubusercontent.com/84135100/182204025-e01af48e-96ba-4b91-bae4-78da6c5ea6ff.png" alt="user-details" width="300"/>
   <img src="https://user-images.githubusercontent.com/84135100/182204263-262301aa-433d-4d17-8e14-5a70c1450940.png" alt="user-edit" width="170"/>
  </p>

### Dashboard
Área acessível apenas a utilizadores com perfil de admin de sistema.
Através de utilização de websockets é possível observar a evolução de algumas estatísticas em tempo real.
<br />
<img src="https://user-images.githubusercontent.com/84135100/182203442-9d2b882f-ce9b-447e-aeac-1a47c7942e13.png" alt="dashbord" width="300"/>
