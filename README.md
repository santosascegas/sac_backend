# sac_backend

Para o desenvolvimento desta API foi usada as seguintes tecnologias:

* Amazon Corretto 17 OpenJDK
* Maven
* Spring Boot
* MySQL

Para rodar a API será necessário algumas configurações, sendo elas:

1. Será necessário a configuração de algum serviço de email. Para o desenvolvimento é recomendado o uso do MailDev.
    * Após a configuração do MailDev como descrito na pagina do GitHub deles, insira o usuario e a senha configurada no arquivo src/main/resources/application.yaml no trecho mail.
2. Configure também, o servidor SQL que nesse caso é o MySQL no arquivo src/main/resources/application.yaml no trecho spring -> datasource.
