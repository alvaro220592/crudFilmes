/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.infox.dal;

import java.sql.*;

/**
 *
 * @author usuario
 */
public class ModuloConexao {

    /*
        Instruções para acessar o banco de dados remotamente pelo programa:
        
        Para acesso remoto ao banco de dados é preciso que se crie no mysql
        um usuário cujo host seja '%.%.%.%'. Como o ip de internet geralmente
        é dividido em 4 partes, por exemplo "192.168.0.2", os símbolos de por-
        centagem representarão cada parte e poderão significar qualquer número
        de ip que acesse o banco de dados.
        Por exemplo: create user 'remoto'@'%.%.%.%' identified by 'senha'; 
        O usuário não poderá se conectar no próprio servidor que foi criado. Só
        à distância.
        Após sua criação, é necessário conceder-lhe os privilégios necessários
        de acesso e alterações no banco de dados. Não é recomendável que este
        usuário remoto tenha todos os privilégios.
        Após a concessão, é necessário atualizar a tabela de privilégios com o
        comando 'flush privileges;'
        No arquivo "mysqld.cnf", localizado em /etc/mysql/mysql.conf.d, vá até
        a linha "bind access = 127.0.0.1" e altere para "bind access = 0.0.0.0"
        A porta 3306 deve estar aberta para acesso remoto. Para verificar, use
        o comando "sudo nmap -sT- -O localhost" no terminal (linux) 
        No Linux, digite o comando "sudo ufw status verbose" para verificar se
        o firewall está ativo. Se estiver inativo, digite "sudo ufw enable".
        Verifique novamente o status e se estiver ativo, digite "sudo ufw allow 
        3306/tcp". 
        Vá ao site do roteador, entre em "Redirecionamento de Portas" e crie uma
        nova regra para esta conexão:
        No ip, coloque o ip de internet(externo e não o de rede;
        Na porta interna ponha "3306", que é a porta do mysql;
        Na porta externa ponha "8085"(As portas externas devem estar liberadas.
        Verifique com a empresa provedora da sua internet.
        FIM
     */

    //metodo responsavel por estabelecer a conexao com o banco de dados
    public static Connection conector() { //conector = metodo
        java.sql.Connection conexao = null;

        //chamando o driver importado pra bibliotecas
        String driver = "com.mysql.jdbc.Driver";

        //armazenando informações do banco:
        //complemento para a url no caso de ter que especificar o nao uso de ssh: ?useSSL=false";
        
        // Url de acesso local: 
        String url = "jdbc:mysql://localhost:3306/db_filmes_id";
        String user = "usertcc";
        String password = "TCC@2020";
        
        //Url de acesso remoto:
        /*
        String url = "jdbc:mysql://168.0.99.129:8085/db_filmes_id";
        String user = "remoto0"; //usuario 'remoto0'@'%.%.%.%' criado no mysql para acesso remoto
        String password = "1234";
        Obs: (usa-se 'jdbc:mysql://ipexterno:portaexterna/nomedobanco
        */
        

        //estabelecendo a conexao com o banco de dados    }
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;

        } catch (Exception e) {
            return null;
        }
    }
}
