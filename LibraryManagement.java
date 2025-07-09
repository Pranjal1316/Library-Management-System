package com.training.dataFlair;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RepaintManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.MatteBorder;

public class LibraryManagement extends JFrame {
	 	private final String[] bookColumns = {"ID", "Title", "Author", "Genre", "Publication Date", "ISBN", "Available"};
	    private final String[] borrowerColumns = {"ID", "Name", "Email", "Phone", "Address"};
	    private final String[] checkoutColumns = {"ID", "Book ID", "Borrower ID", "Checkout Date", "Due Date", "Return Date"};

	    private JTabbedPane tabbedPane;
	    private JTable bookTable;
	    private JTable borrowerTable;
	    private JTable checkoutTable;

	    // Add UI components
	    private JTextField booktitleField;
	    private JTextField nameField;
	    private JTextField emailField;
	    private JTextField phoneField;
	    private JTextField addresssField;
	    private JTextField authorField;
	    private JTextField genreField;
	    private JTextField pubDateField;
	    private JTextField isbnField;
	    private JCheckBox availableField;
		private JTextField bookIDField;
		private JTextField borrowerIDField;
		private JTextField checkoutDateField;
		private JTextField dueDateField;
		private JTextField returnDateField;
		private JTextField deleteBookField;
		private JTextField deleteBorrowerField;
		private JTextField deletecheckoutField;
	    public LibraryManagement() {
	    	getContentPane().setBackground(new Color(221, 161, 94));

	        Database.createDatabase();

	        DefaultTableModel bookModel = new DefaultTableModel(bookColumns, 0);
	        bookTable = new JTable(bookModel);

	        DefaultTableModel borrowerModel = new DefaultTableModel(borrowerColumns, 0);
	        borrowerTable = new JTable(borrowerModel);

	        DefaultTableModel checkoutModel = new DefaultTableModel(checkoutColumns, 0);
	        checkoutTable = new JTable(checkoutModel);

	        Database.refreshTables(bookModel,borrowerModel,checkoutModel);

	        tabbedPane = new JTabbedPane();
	        tabbedPane.addTab("Books", new JScrollPane(bookTable));
	        tabbedPane.addTab("Borrowers", new JScrollPane(borrowerTable));
	        tabbedPane.addTab("Checkouts", new JScrollPane(checkoutTable));

	        // Book input Panel
	        booktitleField = new JTextField(20);
	        authorField = new JTextField(20);
	        genreField = new JTextField(20);
	        pubDateField = new JTextField(10);
	        isbnField = new JTextField(13);
	        availableField = new JCheckBox("Available");
	        deleteBookField = new JTextField();
	        JButton addBookButton = new JButton("Add Book");
	        addBookButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	               try {
	            	Database.addBook(booktitleField.getText(),
	                				authorField.getText(),
	                				genreField.getText(),
	                				pubDateField.getText(),
	                				isbnField.getText(),
	                				availableField.isSelected()
	                		);
	            	Database.refreshTables(bookModel, borrowerModel, checkoutModel);
	                JOptionPane.showMessageDialog(addBookButton, "Book added successfully.");
		        } catch (SQLException e1) {
		            e1.printStackTrace();
		            JOptionPane.showMessageDialog(addBookButton, "Error adding book. Please try again.");
		        }
	            }

				
	        });
	        
	        JButton remBookButton = new JButton("Remove Book");
	        remBookButton.addActionListener(new ActionListener() {
	        	public void actionPerformed(ActionEvent e) {
	        		
	        		try {
						Database.delete("books", deleteBookField.getText());
			            JOptionPane.showMessageDialog(remBookButton, "Book deleted successfully");
		            	Database.refreshTables(bookModel, borrowerModel, checkoutModel);

					} catch (SQLException e1) {
			            JOptionPane.showMessageDialog(remBookButton, "Error deleting book. Please try again.");

						e1.printStackTrace();
					}
	        	}
	        });

	        JPanel bookPanel = new JPanel(new GridLayout(0,2));
	        bookPanel.setBorder(new MatteBorder(5, 5, 5, 5, (Color) new Color(0, 0, 0)));
	        bookPanel.add(new JLabel("Title:"));
	        bookPanel.add(booktitleField);
	        bookPanel.add(new JLabel("Author:"));
	        bookPanel.add(authorField);
	        bookPanel.add(new JLabel("Genre:"));
	        bookPanel.add(genreField);
	        bookPanel.add(new JLabel("Publication Date (YYYY-MM-DD):"));
	        bookPanel.add(pubDateField);
	        bookPanel.add(new JLabel("ISBN:"));
	        bookPanel.add(isbnField);
	        bookPanel.add(new JLabel("Available:"));
	        bookPanel.add(availableField);
	        bookPanel.add(new JLabel(""));
	        bookPanel.add(addBookButton);
	        bookPanel.add(remBookButton);
	        bookPanel.add(deleteBookField);
	        
//	        Borrower input Panel
	        JButton btnAddBorrower = new JButton("Add");
	        JButton btnRemBorrower = new JButton("Remove");
	        nameField = new JTextField();
	        emailField = new JTextField();
	        phoneField = new JTextField();
	        addresssField = new JTextField();
	        deleteBorrowerField = new JTextField();
	        JPanel borrowerPanel= new JPanel(new GridLayout(0,2));
	        borrowerPanel.setBorder(new MatteBorder(5, 5, 5, 5, (Color) new Color(0, 0, 0)));
	        borrowerPanel.add(new JLabel("Name:"));
	        borrowerPanel.add(nameField);
	        borrowerPanel.add(new JLabel("Email:"));
	        borrowerPanel.add(emailField);
	        borrowerPanel.add(new JLabel("Phone:"));
	        borrowerPanel.add(phoneField);
	        borrowerPanel.add(new JLabel("Address:"));
	        borrowerPanel.add(addresssField);
	        borrowerPanel.add(new JLabel(""));
	        borrowerPanel.add(btnAddBorrower);
	        borrowerPanel.add(btnRemBorrower);
	        borrowerPanel.add(deleteBorrowerField);
	        btnAddBorrower.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						Database.addBorrower(nameField.getText(),
											emailField.getText(),
											phoneField.getText(),
											addresssField.getText());
		                JOptionPane.showMessageDialog(btnAddBorrower, "Borrower added successfully.");
		            	Database.refreshTables(bookModel, borrowerModel, checkoutModel);

					} catch (SQLException e1) {
			            JOptionPane.showMessageDialog(btnAddBorrower, "Error adding borrower. Please try again.");
						e1.printStackTrace();
						
					}
					
				}
			});
	        btnRemBorrower.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						Database.delete("borrowers", deleteBorrowerField.getText());
		                JOptionPane.showMessageDialog(btnRemBorrower, "Borrower deleted successfully.");
		            	Database.refreshTables(bookModel, borrowerModel, checkoutModel);

					} catch (SQLException e1) {
		                JOptionPane.showMessageDialog(btnRemBorrower, "Error deleting Borrower.Please try again");
						e1.printStackTrace();
					}
					
				}
			});
	        
//	        checkout input Panel
	        DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
	        JButton btnAddcheckout = new JButton("Add");
	        JButton btnRemcheckout = new JButton("Remove");
	        bookIDField = new JTextField();
	        borrowerIDField = new JTextField();
	        checkoutDateField =new JFormattedTextField(df);
	        dueDateField = new JFormattedTextField(df);
	        returnDateField = new JFormattedTextField(df);
	        deletecheckoutField = new JTextField();
	        JPanel checkoutPanel= new JPanel(new GridLayout(0,2));
	        checkoutPanel.setBorder(new MatteBorder(5, 5, 5, 5, (Color) new Color(0, 0, 0)));
	        checkoutPanel.add(new JLabel("Book ID:"));
	        checkoutPanel.add(bookIDField);
	        checkoutPanel.add(new JLabel("Borrower ID:"));
	        checkoutPanel.add(borrowerIDField);
	        checkoutPanel.add(new JLabel("Checkout Date:"));
	        checkoutPanel.add(checkoutDateField);
	        checkoutPanel.add(new JLabel("Due Date:"));
	        checkoutPanel.add(dueDateField);
	        checkoutPanel.add(new JLabel("Return Date:"));
	        checkoutPanel.add(returnDateField);
	        checkoutPanel.add(new JLabel(""));
	        checkoutPanel.add(btnAddcheckout);
	        checkoutPanel.add(btnRemcheckout);
	        checkoutPanel.add(deletecheckoutField);
	        btnAddcheckout.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						Database.addCheckout(bookIDField.getText(), 
											borrowerIDField.getText(),
											checkoutDateField.getText(),
											dueDateField.getText(),
											returnDateField.getText());
						Database.refreshTables(bookModel, borrowerModel, checkoutModel);
					} catch (SQLException e1) {
			            JOptionPane.showMessageDialog(addBookButton, "Error adding checkouts. Please try again.");
						e1.printStackTrace();
					}
					
				}
			});
	        btnRemcheckout.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						Database.delete("checkouts", deletecheckoutField.getText());
		                JOptionPane.showMessageDialog(btnRemcheckout, "Checkout deleted successfully.");
		            	Database.refreshTables(bookModel, borrowerModel, checkoutModel);

					} catch (SQLException e1) {
		                JOptionPane.showMessageDialog(btnRemcheckout, "Error deleting Checkout.Please try again");
						e1.printStackTrace();
					}
					
				}
			});
	        
	     // add a change listener to the tabbed pane to change the input panel accordingly
	        tabbedPane.addChangeListener(new ChangeListener() {
	            public void stateChanged(ChangeEvent e) {
	                // get the index of the selected tab
	                int selectedIndex = tabbedPane.getSelectedIndex();
	                if(tabbedPane.getTitleAt(selectedIndex) == "Books") {
	                	getContentPane().removeAll();
	                	getContentPane().add(bookPanel, BorderLayout.NORTH);
	         	        getContentPane().add(tabbedPane, BorderLayout.CENTER);
	         	        
	                }
	                if(tabbedPane.getTitleAt(selectedIndex) == "Borrowers") {
	                	getContentPane().removeAll();
	                	getContentPane().add(borrowerPanel, BorderLayout.NORTH);
	         	        getContentPane().add(tabbedPane, BorderLayout.CENTER);
	         	       
	                }
	                if(tabbedPane.getTitleAt(selectedIndex) == "Checkouts") {
	                	getContentPane().removeAll();
	                	getContentPane().add(checkoutPanel, BorderLayout.NORTH);
	         	        getContentPane().add(tabbedPane, BorderLayout.CENTER);
	                }
	                repaint();
	            }
	            
	        });
	        
	        // Add components to JFrame
	        getContentPane().add(bookPanel, BorderLayout.NORTH);
	        getContentPane().add(tabbedPane, BorderLayout.CENTER);

	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(800, 600);
	        setLocationRelativeTo(null);
	        setTitle("Library Management System by DataFlair");
	        setVisible(true);
	    }
public static void main(String[] args) {
	new LibraryManagement();
}

}
