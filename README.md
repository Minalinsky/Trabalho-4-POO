# Trab4POO
Implementação do Trabalho 4 da disciplina de Programação Orientada a Objetos - Implementação de um Mercado Online

####Programado em
```
Sistema Operacional: Windows 8
IDE: Eclipse Luna (versão: Luna Service Release 2 (4.4.2))
Java 8 (java version "1.8.0_45")
```

####Instrucoes de Execucao
1. Executar a classe MainServer para inicializar o servidor
2. Executar a classe MainClient para inicializar o cliente
3. Na janela aberta pelo MainClient, digitar o IP do servidor
4. Na nova janela do cliente, realizar Login ou se Registrar 
5. Realizar operacoes desejadas (tanto no servidor quanto no cliente)

**OBS.:** .jar não incluso.

####Design Patterns usados:
Na classe MarketServer: Singleton

Na classe MarketClient: Singleton

####Descricao de algumas das funcionalidades
#####Cliente:
  Após o usuario estar logado:
  
  -Ao clicar no botão "Buy", o arquivo Products.csv do cliente é automaticamente atualizado e os novos valores são mostrados na tela.
 
  -Não é possível realizar uma compra se a quantidade desejada for maior que a do estoque do servidor
  
  -O botão "Refresh" tanto atualiza o arquivo de produtos do cliente quanto mostra na tela os produtos atualizados
  
#####Servidor:
  -A notificação por email é enviada a todos os costumers/usuarios registrados no servidor. **OBS:** Ao reestocar os produtos, os emails são automaticamente enviados. **ATENCAO! Há um certo delay para a ação ser completada! O programa não está travado! Ao término do envio dos emails, a tela de reabastecimento do estoque é automaticamente fechada**
  
  -Quando um produto é zerado do estoque, abre-se uma nova janela listando todos os produtos fora de estoque e requisitando o reabastecimento dos produtos listados. **Todos os produtos serão reabastecidos com a quantidade passada**

  -Assim como no cliente, o botão "Refresh" tanto atualiza o arquivo de produtos do cliente quanto mostra na tela os produtos atualizados

  -Nao é possível cadastrar novos produtos ao passar campos como Price e Quantity inválidos (devem ser respectivamente float e int)
##Obervações Gerais
1 - **PACOTES**

1.1 - No pacote "client" encontam-se os arquivos relacionados ao cliente

1.2 - No pacote "server" encontam-se os arquivos relacionados ao servidor

1.3 - No pacote "marketlib" encontam-se os arquivos relacionados a ambos (Costumer.java, Products.java e DEFINE.java)

2 - **ARQUIVOS**

Todos os arquivos de registros encontram-se no formato .csv.

Possuímos junto ao servidor, o arquivo Products.csv e o Costumers.csv. No primeiro temos todos os dados dos produtos registrados, enquanto que no segundo, dos usuarios registrados.

Junto ao cliente, temos somente o Products.csv com todos os dados recebidos do servidor dos produtos. Esse é o arquivo alterado a cada atualização da lista de produtos realizada pelo cliente.


##Implementações Extras
1 - Interface Gráfica em JavaFX (1.5): **Implementado**

2 - Teste em JUnit (1.5): **Nao Implementado**

3 - Geração de relatórios PDF (1.5): **Nao Implementado**

4 - Uso de pelo menos 1 padrão de projeto e descrever no README. (1.5): **Implementado e Descrito**

5 - Notificação de Produtos Indisponíveis / JavaMail API (1.5): **Implementado**