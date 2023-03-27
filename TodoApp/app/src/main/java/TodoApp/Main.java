/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TodoApp;

import java.sql.Connection;
import util.ConnectionFactory;

/**
 *
 * @author hiago
 */
public class Main {
    public static void main (String [] args){
        System.out.println("Hello World!");
        Connection c = ConnectionFactory.getConnection();
        ConnectionFactory.closeConnection(c);
    }
}
