package fr.fms.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import fr.fms.entities.User;

public class UserDao implements Dao<User> {

	private ArrayList<User> users;
	
	public UserDao() {
		users=new ArrayList<User>();
	}
	
	@Override
	public void create(User obj) {
		String strSql="INSERT INTO T_Users(Login, Password) VALUES(?, ?);";	// une fois connecté, réalisation d'un requête
		try(PreparedStatement ps =connection.prepareStatement(strSql)){ // de java.sql
			ps.setString(1, obj.getLogin());
			ps.setString(2, obj.getPassword());
			
			if (ps.executeUpdate()==1) {
				System.out.println("insertion ok");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public User read(int id) {
		User user = null;
		String strSql="SELECT * FROM t_users WHERE IdUser = ?;";		// une fois connecté, réalisation d'un requête
		try(PreparedStatement ps =connection.prepareStatement(strSql)){ // de java.sql
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();  // ResultSet de java.sql
			while (rs.next()) {
				int rsidUser=rs.getInt(1);  // soit index(de 1 à n) de la colonne, soit le nom de la colonne
				String rslogin =rs.getString(2);
				String rspassword=rs.getString(3);

				user=new User(rsidUser,rslogin,rspassword);

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	@Override
	public boolean update(User obj) {
		String strSql="UPDATE t_users SET login = ?, password = ? WHERE IdUser = ? ;";						// une fois connecté, réalisation d'un requête
		try(PreparedStatement ps =connection.prepareStatement(strSql)){ // de java.sql
			ps.setString(1, obj.getLogin());
			ps.setString(2, obj.getPassword());
			ps.setDouble(3, obj.getIdUser());
		

			if (ps.executeUpdate()==1) {
				System.out.println("mise à jour ok");
				return true;
			}else {
				System.out.println("mise à jour erreur");
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(int id) {
		String strSql="DELETE FROM t_users WHERE IdUser = ?;";	// une fois connecté, réalisation d'un requête
		try(PreparedStatement ps =connection.prepareStatement(strSql)){ // de java.sql
			ps.setInt(1, id);

			if (ps.executeUpdate()==1) {
				System.out.println("suppression ok");
				return true;
			}else {
				System.out.println("echec de suppression");
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}


		return false;
	}

	@Override
	public ArrayList<User> readAll() {
		String strSql="SELECT * FROM T_users";						// une fois connecté, réalisation d'un requête
		try(Statement statement =connection.createStatement()){
			try(ResultSet resultSet=statement.executeQuery(strSql)){   // ResultSet de java.sql
				while (resultSet.next()) {
					int rsidUser=resultSet.getInt(1);  // soit index(de 1 à n) de la colonne, soit le nom de la colonne
					String rslogin =resultSet.getString(2);
					String rspassword=resultSet.getString(3);
					
					users.add((new User(rsidUser,rslogin,rspassword)));
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}

}
