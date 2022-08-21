
# estoque-ag
API REST desenvolvida com Spring Boot para controle de produtos de estoque de uma loja. 

## Dificuldades encontradas durante o desenvolvimento

 - Desenvolver método de busca para produtos que aceite múltiplos parâmetros.
 - Desenvolver os Testes para a busca de produtos, uma vez que a busca retorna um Page dos objetos.
 - Desenvolver os Testes com segurança. 

Foi o primeiro contato que tive com TDD na prática, apesar de já ter estudado a teoria anteriormente. Creio que com mais estudos práticos, me adapte a desenvolver testes com facilidade

## Testes desenvolvidos
Validador de produtos
 1. Deve disparar uma exception caso código seja nulo na inclusão
 2. Deve disparar uma exception caso descrição seja nula
 3. Deve disparar uma exception caso a data de validade seja menor que a de fabricação

Serviços de Produto

 1. Deve incluir um Produto sem erros
 2. Deve alterar um Produto sem erros
 3. Deve disparar uma exception ao tentar alterar um produto que não existe
 4. Deve desativar um produto corretamente
 5. Deve disparar um erro ao tentar desativar um produto inexistente
 6. Deve recuperar produto pelo código
 7. Deve disparar exception ao não encontrar produto com id recebido como parâmetro

## Rotas da API
apesar de saber da existência de ferramentas para documentação de APIS, optei por criar esta simples documentação.

**Recuperar todos os produtos**
GET - http://localhost:8080/produto

**Recuperar produto por código**
GET - http://localhost:8080/produto/{codigo}

**Criar produto**
POST - http://localhost:8080/produto
JSON BODY:

{
			"descricao":"Farol",
			"situacao":"ATIVO",
			"dataFabricacao":"2022-08-30",
			"dataValidade":"2022-08-31",
			"codigoFornecedor":"0001",
			"descricaoFornecedor":"VIDROS LTDA",
			"cnpjFornecedor":"11.111.111/0001-00"
}

**Alterar Produto**
PUT - http://localhost:8080/produto
JSON BODY:

{
			"descricao":"Farol",
			"situacao":"ATIVO",
			"dataFabricacao":"2022-08-30",
			"dataValidade":"2022-08-31",
			"codigoFornecedor":"0001",
			"descricaoFornecedor":"VIDROS LTDA",
			"cnpjFornecedor":"11.111.111/0001-00"
}

**Desativar produto**
*A deleção do produto é lógica, apenas ocorrendo a desativação do produto*
DELETE -  http://localhost:8080/produto/{codigo}

