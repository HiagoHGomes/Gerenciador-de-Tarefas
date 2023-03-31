/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Task;
import util.ConnectionFactory;

/**
 *
 * @author hiago
 */
public class TaskController {
    public void save(Task task) {
        String sql = "INSERT INTO taks (idProject, name, description, completed, notes, deadline, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new java.sql.Date(task.getCreatedAt().getTime()));
            statement.setDate(7, (java.sql.Date) new Date(task.getCreatedAt().getTime()));
            statement.setDate(7, (java.sql.Date) new Date(task.getUpdatedAt().getTime()));
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao salvar a tarefa " + ex.getMessage());
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public void update(Task task){
        String sql = "UPDATE tasks SET idProject = ?, name = ?, description = ?, notes = ?, deadline = ?, completed = ?, createdAt = ?, updatedAt = ? WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            //Cria uma conex�o com o banco
            connection = ConnectionFactory.getConnection();
            //Cria um PreparedStatment, classe usada para executar a query
            statement = connection.prepareStatement(sql);

            statement.setInt     (1, task.getIdProject());
            statement.setString  (2, task.getName());
            statement.setString  (3, task.getDescription());
            statement.setString  (4, task.getNotes());
            statement.setDate    (5, new java.sql.Date(task.getDeadline().getTime()));
            statement.setBoolean (6, task.isIsCompleted());
            statement.setDate    (7, new java.sql.Date(task.getCreatedAt().getTime()));
            statement.setDate    (8, new java.sql.Date(task.getUpdatedAt().getTime()));
            statement.setInt     (9, task.getId());

            //Executa a sql para inser��o dos dados
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro em atualizar a tarefa", ex);
        } 
    }
    
    public void removeById (int taskId) throws SQLException{
        String sql = "DELETE FROM tasks WHERE id = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, taskId);
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar a tarefa" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public List<Task> getAll(int IdProject){
        String sql = "SELECT * FROM tasks WHERE idProject = ?";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Task> tasks = new ArrayList<Task>();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, IdProject);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {

                Task task = new Task();

                task.setId(resultSet.getInt("id"));
                task.setIdProject(resultSet.getInt("idProject"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setNotes(resultSet.getString("notes"));
                task.setDeadline(resultSet.getDate("deadline"));
                task.setIsCompleted(resultSet.getBoolean("completed"));
                task.setCreatedAt(resultSet.getDate("createdAt"));
                task.setCreatedAt(resultSet.getDate("updatedAt"));

                //Adiciono o contato recuperado, a lista de contatos
                tasks.add(task);
            }
        } catch(Exception ex) {
            throw new RuntimeException("Erro ao deletar a tarefa" + ex.getMessage(), ex);
        } finally{
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        
        return tasks;
    }
}
