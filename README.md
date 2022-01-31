# Jakarta EE Bank Transaction Manager


Este é um projeto com fins de prática deliberada que contempla conceitos e especificações importantes da plataforma Jakarta EE.

## Resumo

Bank Transaction Manager é uma aplicação REST que permite criar usuários, gerenciar contas bancárias, efetuar transferências Pix e fornecer extrato bancário para as contas.

Bank Analytics é um módulo que recebe, via Message-Driven Bean, transações do tipo Pix e salva em uma tabela de acordo com o contexto da aplicação.

## Tecnologias, especificações e uso

- Uso da especificação JAX-RS para construção do WebService REST.
- CDI para injeção de dependências.
- EJB Stateless Session Beans para criação dos beans de camada negocial.
- EJB Singleton Session Beans com `@Startup` para inserção de elementos no banco ao iniciar a aplicação.
- EJB Message-Driven Bean para enviar as transferências Pix para outra aplicação aproveitando recursos da tecnologia JMS (Java Message Service).
- JPA (Java Persistence API) com Hibernate como persistence provider. Conceito de herança aplicado com estratégia `InheritanceType.JOINED` para separação de tabelas de clientes Pessoa Física e Jurídica.
- JPQL e Criteria API usado em instruções mais detalhadas com queries mais performáticas.
- Controle transacional gerido pelo Container-Managed Transactions para assegurar transações de maior risco como transferência e depósito aplicando conceito de atomicidade.
- WebSocket usado em um endpoint que lista em tempo real todas as transferências do tipo Pix que acontecem na aplicação. Aplicado também um Decoder para as mensagens devolvidas para os clientes (sessions) conectadas.
- Implementação de Provider para fazer o tratamento das exceções da aplicação para retornar em formato Json de forma amigável.
- Annotations customizadas para validação de campos.
- Estrutura de logging alterado para Log4j2 - versão 2.17.0 :)
- Documentação da aplicação com Swagger.

## Instruções importantes para executar

1. Aplicação testada no servidor de aplicação Wildfly 19.0
2. Para executar a instância do banco de dados com docker-compose.
    - Entre no diretório `database` e execute `docker-compose up -d`
    - Criará dois bancos. Um para cada aplicação. 
    - Necessário criar os JTA datasources `jdbc/MySqlDSTransactionManager` e `jdbc/MySqlDSAnalytics` no servidor de aplicação relativo para cada banco.
3. Rodar o servidor no modo `standalone-full`. Caso necessário alterar o script de execução para `{diretorio_wildfly}/bin/standalone.sh  -c standalone-full.xml -Dresteasy.preferJacksonOverJsonB=false`.
4. Adicionar fila JMS `java:/jms/queue/FinancialMovementQueue` no servidor de aplicação.
