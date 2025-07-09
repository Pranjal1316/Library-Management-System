package com.training.dataFlair;

import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class Database  {
//		declaring the database path 
	  private final static String DB_URL = "jdbc:sqlite:library.db";

//	  Method to add the book into the database
	    static void addBook(String title, String author, String genre, String publicationDate, String isbn, boolean available) throws SQLException {
	        
	       

	        String insertBookQuery = "INSERT INTO books (title, author, genre, publication_date, isbn, available) VALUES (?, ?, ?, ?, ?, ?)";

	        Connection conn = DriverManager.getConnection(DB_URL);
	             PreparedStatement insertBookStmt = conn.prepareStatement(insertBookQuery);

	            insertBookStmt.setString(1, title);
	            insertBookStmt.setString(2, author);
	            insertBookStmt.setString(3, genre);
	            insertBookStmt.setString(4, publicationDate);
	            insertBookStmt.setString(5, isbn);
	            insertBookStmt.setBoolean(6, available);
	            insertBookStmt.executeUpdate();
	            
	            insertBookStmt.close();
	            conn.close();
	            
	    }
//	    Method to add the borrower into the database 
	    static void addBorrower(String name, String email, String phone, String address)throws SQLException {
	        
		       

	        String query = "INSERT INTO borrowers (name,email,phone,address) VALUES (?, ?, ?, ?)";

	        Connection conn = DriverManager.getConnection(DB_URL);
	             PreparedStatement insertBorrower = conn.prepareStatement(query);

	             insertBorrower.setString(1, name);
	             insertBorrower.setString(2, email);
	             insertBorrower.setString(3, phone);
	             insertBorrower.setString(4, address);
	            insertBorrower.executeUpdate();
	            
	            insertBorrower.close();
	            conn.close();
	            
	    }
	    
//	    Method to add checkout into the database
	    static void addCheckout(String bookID,String borrowerID,String coutdate,String duedate ,String returnDate)throws SQLException {
	        
		       

	        String query = "INSERT INTO checkouts(book_id,borrower_id,checkout_date,due_date,return_date) VALUES (?, ?, ?, ?, ?)";
	        
	        Connection conn = DriverManager.getConnection(DB_URL);
	             PreparedStatement insetBorrower = conn.prepareStatement(query);

	             insetBorrower.setInt(1, Integer.valueOf(bookID));
	             insetBorrower.setInt(2, Integer.valueOf(borrowerID));
	             insetBorrower.setString(3,coutdate);
	             insetBorrower.setString(4, duedate);
	             insetBorrower.setString(5, returnDate);
	             insetBorrower.executeUpdate();
	            
	            insetBorrower.close();
	            conn.close();
	            
	    }
	    
//	    Method to create database with the three columns books,borrowers and checkouts
    static void createDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            String createBooksTable = "CREATE TABLE IF NOT EXISTS books (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "title TEXT NOT NULL, " +
                    "author TEXT NOT NULL, " +
                    "genre TEXT, " +
                    "publication_date TEXT, " +
                    "isbn TEXT, " +
                    "available INTEGER DEFAULT 1" +
                    ")";
            stmt.executeUpdate(createBooksTable);

            String createBorrowersTable = "CREATE TABLE IF NOT EXISTS borrowers (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "name TEXT NOT NULL, " +
                    "email TEXT UNIQUE NOT NULL, " +
                    "phone TEXT, " +
                    "address TEXT" +
                    ")";
            stmt.executeUpdate(createBorrowersTable);

            String createCheckoutsTable = "CREATE TABLE IF NOT EXISTS checkouts (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "book_id INTEGER NOT NULL, " +
                    "borrower_id INTEGER NOT NULL, " +
                    "checkout_date TEXT NOT NULL, " +
                    "due_date TEXT NOT NULL, " +
                    "return_date TEXT, " +
                    "FOREIGN KEY (book_id) REFERENCES books(id), " +
                    "FOREIGN KEY (borrower_id) REFERENCES borrowers(id)" +
                    ")";
            stmt.executeUpdate(createCheckoutsTable);
            stmt.close();
            conn.close();
            

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    Method to delete the entry from the database given the id and the table name
    static void delete(String tableName,String id) throws SQLException {
    	String query = "DELETE FROM "+tableName+" WHERE id = "+id+";";
        
        Connection conn = DriverManager.getConnection(DB_URL);
             Statement delete = conn.createStatement();
             delete.execute(query);
             
            
            delete.close();
            conn.close();
            
	}
    
//    Method to refresh the tables with the updated data from the database 
    static void refreshTables(DefaultTableModel bookModel, DefaultTableModel borrowerModel, DefaultTableModel checkoutModel) {
        bookModel.setRowCount(0);

        borrowerModel.setRowCount(0);

        checkoutModel.setRowCount(0);

        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String selectBooksQuery = "SELECT id, title, author, genre, publication_date, isbn, available FROM books";
            PreparedStatement selectBooksStmt = conn.prepareStatement(selectBooksQuery);
            ResultSet bookResults = selectBooksStmt.executeQuery();
            while (bookResults.next()) {
                Object[] bookData = {
                        bookResults.getInt("id"),
                        bookResults.getString("title"),
                        bookResults.getString("author"),
                        bookResults.getString("genre"),
                        bookResults.getString("publication_date"),
                        bookResults.getString("isbn"),
                        bookResults.getBoolean("available")
                };
                bookModel.addRow(bookData);
            }

            String selectBorrowersQuery = "SELECT id, name, email, phone, address FROM borrowers";
            PreparedStatement selectBorrowersStmt = conn.prepareStatement(selectBorrowersQuery);
            ResultSet borrowerResults = selectBorrowersStmt.executeQuery();
            while (borrowerResults.next()) {
                Object[] borrowerData = {
                        borrowerResults.getInt("id"),
                        borrowerResults.getString("name"),
                        borrowerResults.getString("email"),
                        borrowerResults.getString("phone"),
                        borrowerResults.getString("address")
                };
                borrowerModel.addRow(borrowerData);
            }

            String selectCheckoutsQuery = "SELECT id, book_id, borrower_id, checkout_date, due_date, return_date FROM checkouts";
            PreparedStatement selectCheckoutsStmt = conn.prepareStatement(selectCheckoutsQuery);
            ResultSet checkoutResults = selectCheckoutsStmt.executeQuery();
            while (checkoutResults.next()) {
                Object[] checkoutData = {
                        checkoutResults.getInt("id"),
                        checkoutResults.getInt("book_id"),
                        checkoutResults.getInt("borrower_id"),
                        checkoutResults.getString("checkout_date"),
                        checkoutResults.getString("due_date"),
                        checkoutResults.getString("return_date")
                };
                checkoutModel.addRow(checkoutData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
