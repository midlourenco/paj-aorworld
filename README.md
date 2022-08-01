# Projeto 5 - PAJ 2021/22

## Autor:
* Mariana Lourenço

## Objectivo:
Este projeto visa a implementação em ReactJS de uma plataforma de gestao de prohjectos e noticias da organizaçao AoR World com um servidor REST e com persistência de dados guardados em base de dados MySQL, usando Java Peristence API (JPA) com hibernate. Introduzindo também componentes que utilizam Websocket, testes unitários e internacionalização.




## Detalhes:
Internacionalização: idiomas suportados PT-PT e EN

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
Para as imagens existe uma fallback imagem caso o link que se coloque não seja de uma imagem.
A criação de uma notícia é feita com um stepper em 3 passos (detalhes da notícia, associar utilizadores, associar projetos).
<img src="https://user-images.githubusercontent.com/84135100/182203152-51d69d3b-90a3-4974-ab99-e6f0bbd1045a.png" alt="news-forum" width="500"/>
<p float="left">
  <img src="https://user-images.githubusercontent.com/84135100/182204315-52e3f440-0d6f-4faf-9074-92a11d984ba9.png" alt="news-forum" width="300"/>
  <img src="https://user-images.githubusercontent.com/84135100/182204755-a8ad6e43-57d2-4d73-88fa-888d40d02993.png" alt="news-forum" width="300"/>
</p>

### Forum de Projetos:
Semelhante aos detalhes de implemtação das notícias
 <img src="https://user-images.githubusercontent.com/84135100/182203404-9960df56-abfb-4563-a897-0b995c284e29.png" alt="news-forum" width="300"/>

### Sobre a Equipa
No perfil de cada utilizador para além da sua informação pessoal, conseguimos ver os projetos aos quais está associado. Caso se trate do perfil do utilizador atual ou utilizador com privilégios de admin é possível editar todos os campos. Um utilizador com privilégios de admin pode ainda alterar os privilégios do utilizador que está a visualizar.

 <img src="https://user-images.githubusercontent.com/84135100/182203886-9fdcd601-5223-4d3e-828c-2c25a6a0ebb9.png" alt="news-forum" width="300"/>
  <img src="https://user-images.githubusercontent.com/84135100/182204025-e01af48e-96ba-4b91-bae4-78da6c5ea6ff.png" alt="news-forum" width="300"/>
   <img src="https://user-images.githubusercontent.com/84135100/182204263-262301aa-433d-4d17-8e14-5a70c1450940.png" alt="news-forum" width="300"/>

### Dashboard
Área acessível apenas a utilizadores com perfil de admin de sistema.
Através de utilização de websockets é possível observar a evolução de algumas estatísticas em tempo real.
   <img src="https://user-images.githubusercontent.com/84135100/182203442-9d2b882f-ce9b-447e-aeac-1a47c7942e13.png" alt="news-forum" width="300"/>
