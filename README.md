# Trab4POO
Implementa��o do Trabalho 4 da disciplina de Programa��o Orientada a Objetos - Implementa��o de um Mercado Online

####Programado em
```
Sistema Operacional: Windows 8
IDE: Eclipse Luna (vers�o: Luna Service Release 2 (4.4.2))
Java 8 (java version "1.8.0_45")
```

####Instrucoes de Execucao
1. Executar a classe MainServer para inicializar o servidor
2. Executar a classe MainClient para inicializar o cliente
3. Na janela aberta pelo MainClient, digitar o IP do servidor
4. Na nova janela do cliente, realizar Login ou se Registrar 
5. Realizar operacoes desejadas (tanto no servidor quanto no cliente)

**OBS.:** .jar n�o incluso.

####Design Patterns usados:
Na classe MarketServer: Singleton

Na classe MarketClient: Singleton

####Descricao de algumas das funcionalidades
#####Cliente:
  Ap�s o usuario estar logado:
  
  -Ao clicar no bot�o "Buy", o arquivo Products.csv do cliente � automaticamente atualizado e os novos valores s�o mostrados na tela.
 
  -N�o � poss�vel realizar uma compra se a quantidade desejada for maior que a do estoque do servidor
  
  -O bot�o "Refresh" tanto atualiza o arquivo de produtos do cliente quanto mostra na tela os produtos atualizados
  
#####Servidor:
  -A notifica��o por email � enviada a todos os costumers/usuarios registrados no servidor. **OBS:** Ao reestocar os produtos, os emails s�o automaticamente enviados. **ATENCAO! H� um certo delay para a a��o ser completada! O programa n�o est� travado! Ao t�rmino do envio dos emails, a tela de reabastecimento do estoque � automaticamente fechada**
  
  -Quando um produto � zerado do estoque, abre-se uma nova janela listando todos os produtos fora de estoque e requisitando o reabastecimento dos produtos listados. **Todos os produtos ser�o reabastecidos com a quantidade passada**

  -Assim como no cliente, o bot�o "Refresh" tanto atualiza o arquivo de produtos do cliente quanto mostra na tela os produtos atualizados

  -Nao � poss�vel cadastrar novos produtos ao passar campos como Price e Quantity inv�lidos (devem ser respectivamente float e int)
##Oberva��es Gerais
1 - **PACOTES**

1.1 - No pacote "client" encontam-se os arquivos relacionados ao cliente

1.2 - No pacote "server" encontam-se os arquivos relacionados ao servidor

1.3 - No pacote "marketlib" encontam-se os arquivos relacionados a ambos (Costumer.java, Products.java e DEFINE.java)

2 - **ARQUIVOS**

Todos os arquivos de registros encontram-se no formato .csv.

Possu�mos junto ao servidor, o arquivo Products.csv e o Costumers.csv. No primeiro temos todos os dados dos produtos registrados, enquanto que no segundo, dos usuarios registrados.

Junto ao cliente, temos somente o Products.csv com todos os dados recebidos do servidor dos produtos. Esse � o arquivo alterado a cada atualiza��o da lista de produtos realizada pelo cliente.


##Implementa��es Extras
1 - Interface Gr�fica em JavaFX (1.5): **Implementado**

2 - Teste em JUnit (1.5): **Nao Implementado**

3 - Gera��o de relat�rios PDF (1.5): **Nao Implementado**

4 - Uso de pelo menos 1 padr�o de projeto e descrever no README. (1.5): **Implementado e Descrito**

5 - Notifica��o de Produtos Indispon�veis / JavaMail API (1.5): **Implementado**