package dataBaseReference.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dataBaseReference.DTO.Orders;


public class Order_DB_DAO extends AbstractOrderDAO
   {
   private Connection connection;

   public Order_DB_DAO(Connection connection)
      {
      super();
      this.connection = connection;
      }

   @Override
	public List<Orders> getAllOrdersOrderedByNumber() throws SQLException {
		List<Orders> orders = new ArrayList<>();
		String query = "SELECT * FROM Orders ORDER BY number";
		try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
			ResultSet resultSet = preparedStatement.executeQuery();

	         while (resultSet.next()) {
	            Orders order = new Orders();
	            order.setNumber(resultSet.getInt("number"));
	            order.setCustomerId(resultSet.getInt("customerId"));
	            order.setDescription(resultSet.getString("description"));
	            order.setPrice(resultSet.getFloat("price"));
	            orders.add(order);
	            
	         }
		}
		return orders;
	}
   
   @Override
   public List<Orders> getAllOrdersByCustomerIdOrderedByNumber(int customerId) throws SQLException {
	   List<Orders> orders = new ArrayList<>();
	   String query = "SELECT * FROM Orders WHERE customerId = ? ORDER BY number";
	   try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
		   preparedStatement.setInt(1, customerId);
		   ResultSet resultSet = preparedStatement.executeQuery();
		   
		   while(resultSet.next()) {
			   	Orders order = new Orders();
	            order.setNumber(resultSet.getInt("number"));
	            order.setCustomerId(resultSet.getInt("customerId"));
	            order.setDescription(resultSet.getString("description"));
	            order.setPrice(resultSet.getFloat("price"));
	            orders.add(order);
		   }
	   }
	   return orders;
   }
   
   @Override
   public List<Orders> getAllOrdersByCustomerId(int customerId) throws SQLException
      {
      List<Orders> orders = new ArrayList<>();
      String query = "SELECT * FROM Orders WHERE customerId = ?";

      try (PreparedStatement preparedStatement = connection.prepareStatement(query))
         {
         preparedStatement.setInt(1, customerId);
         ResultSet resultSet = preparedStatement.executeQuery();

         while (resultSet.next())
            {
            Orders order = new Orders();
            order.setNumber(resultSet.getInt("number"));
            order.setCustomerId(resultSet.getInt("customerId"));
            order.setDescription(resultSet.getString("description"));
            order.setPrice(resultSet.getFloat("price"));
            orders.add(order);
            }
         }

      return orders;
      }

   @Override
   public Orders getOrderByNumber(int orderNumber) throws SQLException
      {
      String query = "SELECT * FROM Orders WHERE number = ?";
      Orders order = null;

      try (PreparedStatement preparedStatement = connection.prepareStatement(query))
         {
         preparedStatement.setInt(1, orderNumber);
         ResultSet resultSet = preparedStatement.executeQuery();

         if (resultSet.next())
            {
            order = new Orders();
            order.setNumber(resultSet.getInt("number"));
            order.setCustomerId(resultSet.getInt("customerId"));
            order.setDescription(resultSet.getString("description"));
            order.setPrice(resultSet.getFloat("price"));
            }
         }

      return order;
      }

   @Override
   public void addOrder(Orders order) throws SQLException
      {
      String query = "INSERT INTO Orders (number, customerId, description, price) VALUES (?, ?, ?, ?)";

      try (PreparedStatement preparedStatement = connection.prepareStatement(query))
         {
         preparedStatement.setInt(1, order.getNumber());
         preparedStatement.setInt(2, order.getCustomerId());
         preparedStatement.setString(3, order.getDescription());
         preparedStatement.setFloat(4, order.getPrice());

         preparedStatement.executeUpdate();
         }
      }

   @Override
   public void deleteOrder(int orderNumber) throws SQLException
      {
      String query = "DELETE FROM Orders WHERE number = ?";

      try (PreparedStatement preparedStatement = connection.prepareStatement(query))
         {
         preparedStatement.setInt(1, orderNumber);
         preparedStatement.executeUpdate();
         }
      }

   @Override
   public void deleteAllOrders() throws SQLException
      {
      String query = "DELETE FROM Orders";

      try (PreparedStatement preparedStatement = connection.prepareStatement(query))
         {
         preparedStatement.executeUpdate();
         }
      }

   @Override
   public void deleteAllOrdersFromCustomer(int customerId) throws SQLException{
	   String query = "DELETE FROM Orders WHERE customerId = ?";
	   
	   try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
		   preparedStatement.setInt(1, customerId);
		   preparedStatement.executeUpdate();
	   }
   }
}
