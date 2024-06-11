Vamos construir um sistema cliente que representará o SINFIN e acessará os serviços do SISRH. O SISFIN será construído em JavaScript. Como nosso foco não está na programação de sistemas clientes, deixamos o código disponível e faremos a explicação na videoaula. Utilizaremos a framework Vue.js (https://vuejs.org/) para ajudar no desenvolvimento do cliente, principalmente no momento de fazer as chamadas para os serviços REST do SISRH.

Construa dois serviços para o recurso empregado:
1. Um serviço para listar apenas empregados ativos, ou seja, cuja data de desligamento seja nula.
2. Um serviço para listar apenas empregados inativos, ou seja, quando o empregado possui uma data
de desligamento.
Observação: se preciso, faça novas consultas na classe Banco.java.

Entrega:
Envie prints de tela de todos os testes feitos com o SoapUI para esses novos serviços.

Atividade 3: Serviços para manutenção de usuários
Até este momento, o SISRH não possui mecanismos para fazer gestão dos usuários. Por exemplo, se um
empregado for contratado, conseguiremos inclui-lo por meio dos serviços de empregado, mas não criar um
usuário para que ele acesse o SISRH.
Nosso objetivo é disponibilizar uma API completa para manipulação de usuário: criação, modificação de
senha e desativação.
Assim, construa uma API que permita a manutenção dos usuários por meio dos seguintes serviços REST:
1. Listar usuários.
2. Obter um usuário a partir de uma matrícula.
3. Incluir um usuário.
4. Alterar um usuário a partir da sua matrícula.
5. Excluir um usuário.
6. Criar um cenário de uso completo:
  a. Criar um empregado.
  b. Criar um usuário vinculado ao empregado recém-criado.
  c. Gerar um JWT para esse novo usuário.
  d. Com esse token, acessar um serviço a que um usuário comum tenha permissão.
  e. Tentar acessar um serviço a que um usuário comum não tenha permissão; o sistema deve apresentar uma mensagem de erro.
