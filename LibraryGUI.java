import javax.swing.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.awt.event.ActionListener;

public class LibraryGUI {
    private static Connection connection;
    private static JFrame frame;
    private static JTextField searchField;
    private static JTable booksTable;
    private static JPanel loginPanel;
    private static JPanel mainPanel;
    

    public static void runGUI(String[] args) {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/lms", "root", "");

            SwingUtilities.invokeLater(() -> {
                frame = new JFrame("Library Management System");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1400, 650);
                frame.setLayout(new BorderLayout());

                addTopBar();
                addLoginPanel();

                frame.setVisible(true);
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addTopBar() {
        JPanel topBarPanel = new JPanel(new BorderLayout());
        topBarPanel.setBackground(Color.BLACK);
        topBarPanel.setPreferredSize(new Dimension(frame.getWidth(), 130));

        JLabel title = new JLabel("Library Management System", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        topBarPanel.add(title, BorderLayout.CENTER);

        frame.add(topBarPanel, BorderLayout.NORTH);
    }
    private static JButton createStyledLoginButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(0, 0,0));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(actionListener);
        return button;
    }

    private static void addLoginPanel() {
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(Color.WHITE); // Set background color to white
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS)); // Set layout to vertical
        addTopBar();

        // Add the image
        String imagePath = System.getProperty("user.home") + "/Downloads/logo1.jpg";
        ImageIcon imageIcon = new ImageIcon(imagePath);
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the image
        loginPanel.add(imageLabel);

        // Add some spacing between image and buttons
        loginPanel.add(Box.createVerticalStrut(20)); // Add vertical spacing
        JButton adminButton = new JButton("Admin Login");
        adminButton.setForeground(Color.WHITE);
        adminButton.setBackground(new Color(30, 144, 255));
        adminButton.setFocusPainted(false); // Remove button border
        adminButton.setBorderPainted(false); // Remove button border
        adminButton.setContentAreaFilled(true); // Set content area filled
        adminButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the button
        adminButton.addActionListener(e -> handleAdminLogin());
        loginPanel.add(adminButton);

        // Increase padding between buttons
        loginPanel.add(Box.createVerticalStrut(10)); // Add vertical spacing

        JButton studentTeacherButton = new JButton("Customer Login");
        studentTeacherButton.setForeground(Color.WHITE);
        studentTeacherButton.setBackground(new Color(30, 144, 255));
        studentTeacherButton.setFocusPainted(false); // Remove button border
        studentTeacherButton.setBorderPainted(false); // Remove button border
        studentTeacherButton.setContentAreaFilled(true); // Set content area filled
        studentTeacherButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the button
        studentTeacherButton.addActionListener(e -> handleStudentTeacherLogin());
        loginPanel.add(studentTeacherButton);
        frame.add(loginPanel, BorderLayout.CENTER);
    }


 


 // Custom header renderer class
    static class HeaderRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            component.setBackground(Color.BLUE); // Set header background color
            component.setForeground(Color.WHITE); // Set header text color
            return component;
        }
    }
    static class CustomCellRenderer extends DefaultTableCellRenderer {
    	private static final int CELL_PADDING = 50;
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Set content font to bold
            component.setFont(component.getFont().deriveFont(Font.BOLD));

            // Increase cell width
            table.getColumnModel().getColumn(column).setPreferredWidth(300); // Adjust the width as needed
            setBorder(BorderFactory.createEmptyBorder(CELL_PADDING, CELL_PADDING, CELL_PADDING, CELL_PADDING));
            return component;
        }
    }


    private static void displayBooks(boolean showBackButton){
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    frame.getContentPane().removeAll();
                    frame.repaint();

                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM library");
                    JPanel topBarPanel = new JPanel(new BorderLayout());
                    topBarPanel.setBackground(Color.BLACK);
                    topBarPanel.setPreferredSize(new Dimension(frame.getWidth(), 100));

                    JLabel title = new JLabel("View Books", SwingConstants.CENTER);
                    title.setForeground(Color.WHITE);      
                    topBarPanel.add(title, BorderLayout.CENTER);
                    if (showBackButton) {
                        JButton backButton = createStyledLoginButton("<--", e -> addAdminOptionsPanel());
                        topBarPanel.add(backButton, BorderLayout.WEST); // Add the back button
                    } 

                    frame.add(topBarPanel, BorderLayout.NORTH);

                    DefaultTableModel tableModel = new DefaultTableModel() {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false; // Make cells non-editable
                        }
                    };
                    tableModel.addColumn("Sl_No");
                    tableModel.addColumn("Book ID");
                    tableModel.addColumn("Book Title");
                    tableModel.addColumn("Book Author");
                    tableModel.addColumn("Book Cost");
                    tableModel.addColumn("Borrowed");
                    tableModel.addColumn("Section");
                    tableModel.addColumn("Row_Number");

                    while (resultSet.next()) {
                    	String slno = resultSet.getString("Sl_No");
                        String bookId = resultSet.getString("Book_Id");
                        String title1 = resultSet.getString("Book_Name");
                        String author = resultSet.getString("Book_Author");
                        float cost = resultSet.getFloat("Cost");
                        int isBorrowed = resultSet.getInt("Status");
                        String sec = resultSet.getString("Section");
                        String rno = resultSet.getString("Row_Number");

                        Object[] row = {slno,bookId, title1, author, cost, (isBorrowed == 1 ? "Yes" : "No"),sec,rno};
                        tableModel.addRow(row);
                    }

                    booksTable = new JTable(tableModel);
                    booksTable.getTableHeader().setDefaultRenderer(new HeaderRenderer()); // Set custom header renderer
          

                    
                    for (int i = 0; i < booksTable.getColumnModel().getColumnCount(); i++) {
                        booksTable.getColumnModel().getColumn(i).setCellRenderer(new CustomCellRenderer());
                        

                    }
                    booksTable.setRowHeight(30); // Set the desired row height
                    booksTable.setIntercellSpacing(new Dimension(10, 10)); // Set the spacing between cells
                    booksTable.getTableHeader().setPreferredSize(new Dimension(booksTable.getTableHeader().getWidth(), 30));
                    JScrollPane scrollPane = new JScrollPane(booksTable);

                    frame.add(scrollPane, BorderLayout.CENTER);

                    frame.revalidate();
                    frame.repaint();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        worker.execute();
    }
    private static void displayBooks_ST(boolean showBackButton){
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    frame.getContentPane().removeAll();
                    frame.repaint();

                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM library");
                    JPanel topBarPanel = new JPanel(new BorderLayout());
                    topBarPanel.setBackground(Color.BLACK);
                    topBarPanel.setPreferredSize(new Dimension(frame.getWidth(), 100));

                    JLabel title = new JLabel("View Books", SwingConstants.CENTER);
                    title.setForeground(Color.WHITE);
                    topBarPanel.add(title, BorderLayout.CENTER);
                    if (showBackButton) {
                        JButton backButton = createStyledLoginButton("<--", e -> handleStudentTeacherLogin());
                        topBarPanel.add(backButton, BorderLayout.WEST); // Add the back button
                    } 

                    frame.add(topBarPanel, BorderLayout.NORTH);

                    DefaultTableModel tableModel = new DefaultTableModel() {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false; // Make cells non-editable
                        }
                    };
                    tableModel.addColumn("Sl_No");
                    tableModel.addColumn("Book ID");
                    tableModel.addColumn("Book Title");
                    tableModel.addColumn("Book Author");
                    tableModel.addColumn("Book Cost");
                    tableModel.addColumn("Borrowed");
                    tableModel.addColumn("Section");
                    tableModel.addColumn("Row_Number");
                   

                    while (resultSet.next()) {
                    	String slno = resultSet.getString("Sl_No");
                        String bookId = resultSet.getString("Book_Id");
                        String title1 = resultSet.getString("Book_Name");
                        String author = resultSet.getString("Book_Author");
                        float cost = resultSet.getFloat("Cost");
                        int isBorrowed = resultSet.getInt("Status");
                        String sec = resultSet.getString("Section");
                        String rno = resultSet.getString("Row_Number");
                       

                        Object[] row = {slno,bookId, title1, author, cost, (isBorrowed == 1 ? "Yes" : "No"),sec,rno};
                        tableModel.addRow(row);
                    }

                    booksTable = new JTable(tableModel);
                    booksTable.getTableHeader().setDefaultRenderer(new HeaderRenderer()); // Set custom header renderer
          

                    
                    for (int i = 0; i < booksTable.getColumnModel().getColumnCount(); i++) {
                        booksTable.getColumnModel().getColumn(i).setCellRenderer(new CustomCellRenderer());
                        

                    }
                    booksTable.setRowHeight(30); // Set the desired row height
                    booksTable.setIntercellSpacing(new Dimension(10, 10)); // Set the spacing between cells
                    booksTable.getTableHeader().setPreferredSize(new Dimension(booksTable.getTableHeader().getWidth(), 30));
                    JScrollPane scrollPane = new JScrollPane(booksTable);

                    frame.add(scrollPane, BorderLayout.CENTER);

                    frame.revalidate();
                    frame.repaint();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        worker.execute();
    }
    private static void searchBooks() {
        frame.getContentPane().removeAll();
        frame.repaint();
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        frame.add(mainPanel);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        frame.add(mainPanel);

        JPanel topBarPanel = new JPanel(new BorderLayout());
        topBarPanel.setBackground(Color.BLACK);
        topBarPanel.setPreferredSize(new Dimension(frame.getWidth(), 100));

        JLabel title = new JLabel("Search Books", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        topBarPanel.add(title, BorderLayout.CENTER);

        JButton backButton = createStyledLoginButton("<--", e -> addAdminOptionsPanel());
        topBarPanel.add(backButton, BorderLayout.WEST);

        mainPanel.add(topBarPanel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new FlowLayout());

        JLabel searchLabel = new JLabel("Enter Book Title:");
        searchPanel.add(searchLabel);

        searchField = new JTextField(20);
        searchPanel.add(searchField);

        JButton searchButton = new JButton("Search");
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(new Color(30, 144, 255));
        searchButton.addActionListener(e -> performSearch());
        searchPanel.add(searchButton);

        mainPanel.add(searchPanel, BorderLayout.CENTER);

        frame.revalidate();
        frame.repaint();
    }

    private static void performSearch() {
        try {
            String searchTerm = searchField.getText();
            String searchBooksSQL = "SELECT * FROM library WHERE Book_Name = ?";
            PreparedStatement searchBooksStatement = connection.prepareStatement(searchBooksSQL);

            searchBooksStatement.setString(1, searchTerm);
            ResultSet resultSet = searchBooksStatement.executeQuery();

            if (resultSet.next()) {
                displayBookDetails(resultSet);
            } else {
                displaySearchResult("Book not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            displaySearchResult("Error during search.");
        }
    }

    private static void displayBookDetails(ResultSet resultSet) throws SQLException {
        try {
            StringBuilder details = new StringBuilder();
            details.append("Sl No: ").append(resultSet.getInt("Sl_No")).append("\n");
            details.append("Book ID: ").append(resultSet.getString("Book_Id")).append("\n");
            details.append("Book Title: ").append(resultSet.getString("Book_Name")).append("\n");
            details.append("Book Author: ").append(resultSet.getString("Book_Author")).append("\n");
            details.append("Book Cost: ").append(resultSet.getFloat("Cost")).append("\n");
            details.append("Borrowed: ").append(resultSet.getInt("Status") == 1 ? "Yes" : "No").append("\n");
            details.append("Section: ").append(resultSet.getString("Section")).append("\n");
            details.append("Row Number: ").append(resultSet.getString("Row_Number")).append("\n");
       

            JTextArea resultArea = new JTextArea(details.toString());
            resultArea.setEditable(false);

            JOptionPane.showMessageDialog(frame, new JScrollPane(resultArea), "Book Details", JOptionPane.PLAIN_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            displaySearchResult("Error displaying book details.");
        }
    }


    private static void displaySearchResult(String result) {
        JOptionPane.showMessageDialog(frame, result, "Search Result", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void searchBooks_ST() {
        frame.getContentPane().removeAll();
        frame.repaint();
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        frame.add(mainPanel);

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        frame.add(mainPanel);

        JPanel topBarPanel = new JPanel(new BorderLayout());
        topBarPanel.setBackground(Color.BLACK);
        topBarPanel.setPreferredSize(new Dimension(frame.getWidth(), 100));

        JLabel title = new JLabel("Search Books", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        topBarPanel.add(title, BorderLayout.CENTER);

        JButton backButton = createStyledLoginButton("<--", e -> handleStudentTeacherLogin());
        topBarPanel.add(backButton, BorderLayout.WEST);

        mainPanel.add(topBarPanel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel(new FlowLayout());

        JLabel searchLabel = new JLabel("Enter Book Title:");
        searchPanel.add(searchLabel);

        searchField = new JTextField(20);
        searchPanel.add(searchField);

        JButton searchButton = new JButton("Search");
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(new Color(30, 144, 255));
        searchButton.addActionListener(e -> performSearch_ST());
        searchPanel.add(searchButton);

        mainPanel.add(searchPanel, BorderLayout.CENTER);

        frame.revalidate();
        frame.repaint();
    }

    private static void performSearch_ST() {
        try {
            String searchTerm = searchField.getText();
            String searchBooksSQL = "SELECT * FROM library WHERE Book_Name = ?";
            PreparedStatement searchBooksStatement = connection.prepareStatement(searchBooksSQL);

            searchBooksStatement.setString(1, searchTerm);
            ResultSet resultSet = searchBooksStatement.executeQuery();

            if (resultSet.next()) {
                displayBookDetails_ST(resultSet);
            } else {
                displaySearchResult("Book not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            displaySearchResult("Error during search.");
        }
    }

    private static void displayBookDetails_ST(ResultSet resultSet) throws SQLException {
        try {
            StringBuilder details = new StringBuilder();
            details.append("Sl No: ").append(resultSet.getInt("Sl_No")).append("\n");
            details.append("Book ID: ").append(resultSet.getString("Book_Id")).append("\n");
            details.append("Book Title: ").append(resultSet.getString("Book_Name")).append("\n");
            details.append("Book Author: ").append(resultSet.getString("Book_Author")).append("\n");
            details.append("Book Cost: ").append(resultSet.getFloat("Cost")).append("\n");
            details.append("Borrowed: ").append(resultSet.getInt("Status") == 1 ? "Yes" : "No").append("\n");
            details.append("Section: ").append(resultSet.getString("Section")).append("\n");
            details.append("Row Number: ").append(resultSet.getString("Row_Number")).append("\n");

            JTextArea resultArea = new JTextArea(details.toString());
            resultArea.setEditable(false);

            JOptionPane.showMessageDialog(frame, new JScrollPane(resultArea), "Book Details", JOptionPane.PLAIN_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            displaySearchResult_ST("Error displaying book details.");
        }
    }


    private static void displaySearchResult_ST(String result) {
        JTextArea resultArea = new JTextArea(result);
        resultArea.setEditable(false);
        mainPanel.add(new JScrollPane(resultArea), BorderLayout.SOUTH);

        frame.revalidate();
        frame.repaint();
    }



    private static void borrowBook() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    String bookId = JOptionPane.showInputDialog(frame, "Enter the Book ID to borrow:");
                    if (bookId != null) {
                        String checkBookAvailabilitySQL = "SELECT * FROM library WHERE Book_Id = ? AND Status = 0";
                        PreparedStatement checkBookAvailabilityStatement = connection.prepareStatement(checkBookAvailabilitySQL);
                        checkBookAvailabilityStatement.setString(1, bookId);
                        ResultSet resultSet = checkBookAvailabilityStatement.executeQuery();

                        if (resultSet.next()) {
                            String customerName = JOptionPane.showInputDialog(frame, "Enter Customer Name:");
                            String customerNo = JOptionPane.showInputDialog(frame, "Enter Customer Mobile No:");
                            String customerId = JOptionPane.showInputDialog(frame, "Enter Customer Id allotted:");
                            LocalDate currentDate = LocalDate.now();
                            // Calculate the return date (15 days from the current date)
                            LocalDate returnDate = currentDate.plusDays(15);

                            // Update the book's status to borrowed in the library database
                            String updateBookStatusSQL = "UPDATE library SET Status = 1 WHERE Book_Id = ?";
                            PreparedStatement updateBookStatusStatement = connection.prepareStatement(updateBookStatusSQL);
                            updateBookStatusStatement.setString(1, bookId);
                            updateBookStatusStatement.executeUpdate();

                            // Insert the customer's record into the customers table in another database
                            String insertCustomerSQL = "INSERT INTO customer_details(Customer_Name, Mobile_No, Customer_Id, Book_Id,Borrowed_Date,Return_Date) VALUES (?, ?, ?, ?, ?, ?)";
                            PreparedStatement insertCustomerStatement = connection.prepareStatement(insertCustomerSQL);
                            insertCustomerStatement.setString(1, customerName);
                            insertCustomerStatement.setString(2, customerNo);
                            insertCustomerStatement.setString(3, customerId);
                            insertCustomerStatement.setString(4, bookId);
                            insertCustomerStatement.setDate(5, java.sql.Date.valueOf(currentDate));
                            insertCustomerStatement.setDate(6, java.sql.Date.valueOf(returnDate));
                            insertCustomerStatement.executeUpdate();

                            // Display a formatted success message
                            String successMessage = String.format(
                                    "<html><body style='width: 250px; text-align: center;'><b>Book borrowed successfully!</b><br>" +
                                    "Book ID: %s<br>Customer Name: %s<br>Mobile No: %s<br>Customer ID: %s<br> Return Date: %s</body></html>",
                                    bookId, customerName, customerNo, customerId,returnDate);
                            
                            JOptionPane.showMessageDialog(frame, successMessage, "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(frame, "The Book with the given ID is not available or does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void done() {
                frame.revalidate();
                frame.repaint();
            }
        };
        worker.execute();
    }
  
    private static void viewCustomerDetails() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    frame.getContentPane().removeAll();
                    frame.repaint();

                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT cd.Customer_Name, cd.Mobile_No, cd.Customer_Id, cd.Book_Id, b.Book_Name, cd.Borrowed_Date,cd.Return_Date FROM customer_details cd INNER JOIN library b ON cd.Book_Id = b.Book_Id");



                    JPanel topBarPanel = new JPanel(new BorderLayout());
                    topBarPanel.setBackground(Color.BLACK);
                    topBarPanel.setPreferredSize(new Dimension(frame.getWidth(), 100));

                    JLabel title = new JLabel("Customer Details", SwingConstants.CENTER);
                    title.setForeground(Color.WHITE);
                    topBarPanel.add(title, BorderLayout.CENTER);

                    JButton backButton = createStyledLoginButton("<--", e -> addAdminOptionsPanel());
                    topBarPanel.add(backButton, BorderLayout.WEST);

                    frame.add(topBarPanel, BorderLayout.NORTH);

                    DefaultTableModel tableModel = new DefaultTableModel() {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    tableModel.addColumn("Customer Name");
                    tableModel.addColumn("Customer Mobile No.");
                    tableModel.addColumn("Customer ID");
                    tableModel.addColumn("Book ID");
                    tableModel.addColumn("Book Title");
                    tableModel.addColumn("Borrowed Date");
                    tableModel.addColumn("Return Date");
                    while (resultSet.next()) {
                        String customerName = resultSet.getString("Customer_Name");
                        String customermno = resultSet.getString("Mobile_No");
                        String customerId = resultSet.getString("Customer_Id");
                        String bookId = resultSet.getString("Book_Id");
                        String bookTitle = resultSet.getString("Book_Name");
                        String borrowDate = resultSet.getString("Borrowed_Date");
                        String returnDate = resultSet.getString("Return_Date");

                        Object[] row = {customerName,customermno, customerId, bookId, bookTitle, borrowDate,returnDate};
                        tableModel.addRow(row);
                    }

                    JTable customerDetailsTable = new JTable(tableModel);
                    customerDetailsTable.getTableHeader().setDefaultRenderer(new HeaderRenderer());
                    customerDetailsTable.setRowHeight(30);
                    customerDetailsTable.setIntercellSpacing(new Dimension(10, 10));
                    customerDetailsTable.getTableHeader().setPreferredSize(new Dimension(customerDetailsTable.getTableHeader().getWidth(), 30));
                    JScrollPane scrollPane = new JScrollPane(customerDetailsTable);

                    frame.add(scrollPane, BorderLayout.CENTER);

                    frame.revalidate();
                    frame.repaint();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        worker.execute();
    }



    private static void returnBook() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    String customerId = JOptionPane.showInputDialog(frame, "Enter the Customer ID:");
                    String bookId = JOptionPane.showInputDialog(frame, "Enter the Book ID to return:");

                    // Get the current date
                    LocalDate currentDate = LocalDate.now();

                    // Retrieve the return date issued from the database
                    String returnDateSQL = "SELECT Return_Date FROM customer_details WHERE Customer_Id = ? AND Book_Id = ?";
                    PreparedStatement returnDateStatement = connection.prepareStatement(returnDateSQL);
                    returnDateStatement.setString(1, customerId);
                    returnDateStatement.setString(2, bookId);
                    ResultSet returnDateResultSet = returnDateStatement.executeQuery();

                    if (returnDateResultSet.next()) {
                        LocalDate returnDate = returnDateResultSet.getDate("Return_Date").toLocalDate();
                        long daysOverdue = ChronoUnit.DAYS.between(returnDate, currentDate);
                        int fine = 0;
                        if (daysOverdue > 0) {
                            fine = (int) daysOverdue; // Fine of 1₹ per day overdue
                        }

                        // Update the status of the book in "Lib2" database to 0
                        String updateBookStatusSQL = "UPDATE library SET Status = 0 WHERE Book_Id = ?";
                        PreparedStatement updateBookStatusStatement = connection.prepareStatement(updateBookStatusSQL);
                        updateBookStatusStatement.setString(1, bookId);
                        updateBookStatusStatement.executeUpdate();

                        // Delete all information of the specified customer
                        String deleteCustomerSQL = "DELETE FROM customer_details WHERE Customer_Id = ?";
                        PreparedStatement deleteCustomerStatement = connection.prepareStatement(deleteCustomerSQL);
                        deleteCustomerStatement.setString(1, customerId);
                        int rowsAffected = deleteCustomerStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(frame, "Book returned successfully for Customer ID: " + customerId + ".\n Fine amount: ₹" + fine);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Failed to delete information for Customer ID: " + customerId);
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "The Book with the given ID is not borrowed by Customer ID: " + customerId);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        worker.execute();
    }


    private static void collectCustomerFeedback() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    String customerName = JOptionPane.showInputDialog(frame, "Enter Customer Name:");
                    String customerId = JOptionPane.showInputDialog(frame, "Enter Customer ID:");
                    // ... rest of the code
                    Object[] options = {"Excellent", "Very Good", "Good", "Average", "Satisfactory"};
                    int feedbackOption = JOptionPane.showOptionDialog(frame,
                            "Select feedback option:", "Customer Feedback",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                            null, options, options[0]);

                    if (feedbackOption != JOptionPane.CLOSED_OPTION) {
                        String feedback = options[feedbackOption].toString();

                        // Insert customer feedback into the 'Customer_Feedback' table
                        String insertFeedbackSQL = "INSERT INTO customer_feedback (Customer_Name, Customer_Id, Feedback,Date) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
                        PreparedStatement insertFeedbackStatement = connection.prepareStatement(insertFeedbackSQL);
                        insertFeedbackStatement.setString(1, customerName);
                        insertFeedbackStatement.setString(2, customerId);
                        insertFeedbackStatement.setString(3, feedback);

                        int rowsAffected = insertFeedbackStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(frame, "Feedback has been successfully recorded.");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Failed to record the feedback.");
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        worker.execute();
    }
    private static void addBookDialog() {
        JTextField bookslno = new JTextField(20);
        JTextField bookIdField = new JTextField(20);
        JTextField bookTitleField = new JTextField(20);
        JTextField bookAuthorField = new JTextField(20);
        JTextField bookCostField = new JTextField(20);
        JTextField booksec = new JTextField(20);
        JTextField bookrno = new JTextField(20);

        JPanel panel = new JPanel(new GridLayout(0, 2));
        
        bookslno.setPreferredSize(new Dimension(30, 30));
        bookIdField.setPreferredSize(new Dimension(30, 30));
        bookTitleField.setPreferredSize(new Dimension(30, 30));
        bookAuthorField.setPreferredSize(new Dimension(30, 30));
        bookCostField.setPreferredSize(new Dimension(30,30));
        booksec.setPreferredSize(new Dimension(30, 30));
        bookrno.setPreferredSize(new Dimension(30, 30));
        
        
        panel.add(new JLabel("Sl No:"));
        panel.add(bookslno);
        panel.add(new JLabel("Book ID:"));
        panel.add(bookIdField);
        panel.add(new JLabel("Book Title:"));
        panel.add(bookTitleField);
        panel.add(new JLabel("Book Author:"));
        panel.add(bookAuthorField);
        panel.add(new JLabel("Book Cost:"));
        panel.add(bookCostField);
        panel.add(new JLabel("Section"));
        panel.add(booksec);
        panel.add(new JLabel("Row_Number:"));
        panel.add(bookrno);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String slno = bookslno.getText();
                String bookId = bookIdField.getText();
                String bookTitle = bookTitleField.getText();
                String bookAuthor = bookAuthorField.getText();
                float bookCost = Float.parseFloat(bookCostField.getText());
                String booksecn = booksec.getText();
                String bookrn = bookrno.getText();
                insertBook(slno, bookId, bookTitle, bookAuthor, bookCost, booksecn, bookrn,e);
            }
        });

        int result = JOptionPane.showOptionDialog(null, panel, "Add Book", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[]{addButton}, addButton);
    }

    private static void insertBook(String slno, String bookId, String bookTitle, String bookAuthor, float bookCost, String booksecn, String bookrn,ActionEvent e) {
        try {
            String insertSQL = "INSERT INTO library (Sl_No, Book_Id, Book_Name, Book_Author, Cost, Status, Section, Row_Number) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insertSQL);
            statement.setString(1, slno);
            statement.setString(2, bookId);
            statement.setString(3, bookTitle);
            statement.setString(4, bookAuthor);
            statement.setFloat(5, bookCost);
            statement.setString(6, "0");
            statement.setString(7, booksecn);
            statement.setString(8, bookrn);
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Book added successfully.");
                // Close the dialog
                Window parentWindow = SwingUtilities.windowForComponent((Component) e.getSource());
                parentWindow.dispose();
            }

            else {
                JOptionPane.showMessageDialog(null, "Failed to add book.");
            }
        } catch (SQLException E) {
            E.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to add book.");
        }
    }



    private static void displayFeedback(boolean showBackButton) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    frame.getContentPane().removeAll();
                    frame.repaint();

                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * FROM customer_feedback");
                    JPanel topBarPanel = new JPanel(new BorderLayout());
                    topBarPanel.setBackground(Color.BLACK);
                    topBarPanel.setPreferredSize(new Dimension(frame.getWidth(), 100));

                    JLabel title = new JLabel("View Feedback", SwingConstants.CENTER);
                    title.setForeground(Color.WHITE);
                    topBarPanel.add(title, BorderLayout.CENTER);
                    if (showBackButton) {
                        JButton backButton = createStyledLoginButton("<--", e -> addAdminOptionsPanel());
                        topBarPanel.add(backButton, BorderLayout.WEST); // Add the back button
                    }

                    frame.add(topBarPanel, BorderLayout.NORTH);

                    DefaultTableModel tableModel = new DefaultTableModel() {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false; // Make cells non-editable
                        }
                    };
                    tableModel.addColumn("Customer Name");
                    tableModel.addColumn("Customer ID");
                    tableModel.addColumn("Feedback");
                    tableModel.addColumn("Feedback Date");

                    while (resultSet.next()) {
                        String customerName = resultSet.getString("Customer_Name");
                        String customerId = resultSet.getString("Customer_Id");
                        String feedback = resultSet.getString("Feedback");
                        String feedbackDate = resultSet.getString("Date");

                        Object[] row = {customerName, customerId, feedback, feedbackDate};
                        tableModel.addRow(row);
                    }

                    JTable feedbackTable = new JTable(tableModel);
                    feedbackTable.getTableHeader().setDefaultRenderer(new HeaderRenderer()); // Set custom header renderer
                    feedbackTable.setRowHeight(30); // Set the desired row height
                    feedbackTable.setIntercellSpacing(new Dimension(10, 10)); // Set the spacing between cells
                    feedbackTable.getTableHeader().setPreferredSize(new Dimension(feedbackTable.getTableHeader().getWidth(), 30));
                    JScrollPane scrollPane = new JScrollPane(feedbackTable);

                    frame.add(scrollPane, BorderLayout.CENTER);

                    frame.revalidate();
                    frame.repaint();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        worker.execute();
    }


    private static void handleAdminLogin() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] message = {
            "Enter admin username:", usernameField,
            "Enter admin password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Admin Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if ("admin123".equals(username) && "654321".equals(password)) {
                frame.getContentPane().removeAll();
                frame.repaint();
                addAdminOptionsPanel();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid admin credentials. Please try again.");
            }
        }
    }

    private static void handleStudentTeacherLogin() {
        frame.getContentPane().removeAll();
        frame.repaint();

        // Top blue bar
        JPanel topBarPanel = new JPanel(new BorderLayout());
        topBarPanel.setBackground(new Color(30, 144, 255));
        topBarPanel.setPreferredSize(new Dimension(frame.getWidth(), 80));

        JLabel welcomeLabel = new JLabel("Welcome Customer", SwingConstants.CENTER);
        welcomeLabel.setForeground(Color.WHITE);
        topBarPanel.add(welcomeLabel, BorderLayout.CENTER);

        frame.add(topBarPanel, BorderLayout.NORTH);

        // Image panel
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));

        // Add some spacing between the top bar and image
        imagePanel.add(Box.createVerticalStrut(10));

     // Add the image from the "Downloads" directory
     String imagePath = System.getProperty("user.home") + "/Downloads/logo2.jpg";
     ImageIcon imageIcon = new ImageIcon(imagePath);

     JLabel imageLabel = new JLabel(imageIcon);
     imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
     imagePanel.add(imageLabel);


        frame.add(imagePanel, BorderLayout.CENTER);

        // Buttons panel
        JPanel studentTeacherPanel = new JPanel();
        studentTeacherPanel.setLayout(new BoxLayout(studentTeacherPanel, BoxLayout.Y_AXIS));
        // Add some spacing between the image and buttons
        studentTeacherPanel.add(Box.createVerticalStrut(10));

        JButton displayBooksButton = createStyledLoginButton("Display Books", e -> displayBooks_ST(true));
        studentTeacherPanel.add(displayBooksButton);
        studentTeacherPanel.add(Box.createVerticalStrut(10));

        JButton searchBooksButton = createStyledLoginButton("Search Books", e -> searchBooks_ST());
        studentTeacherPanel.add(searchBooksButton);
        studentTeacherPanel.add(Box.createVerticalStrut(10));

        JButton borrowBookButton = createStyledLoginButton("Borrow Book", e -> borrowBook());
        studentTeacherPanel.add(borrowBookButton);
        studentTeacherPanel.add(Box.createVerticalStrut(10));

        JButton returnBookButton = createStyledLoginButton("Return Book", e -> returnBook());
        studentTeacherPanel.add(returnBookButton);
        studentTeacherPanel.add(Box.createVerticalStrut(10));
        
        JButton feedbackButton = createStyledLoginButton("Customer Feedback", e -> collectCustomerFeedback());
        studentTeacherPanel.add(feedbackButton);
        studentTeacherPanel.add(Box.createVerticalStrut(10));

        JButton exitButton = createStyledLoginButton("Logout", e -> System.exit(0));
        studentTeacherPanel.add(exitButton);
        studentTeacherPanel.add(Box.createVerticalStrut(10));

        // Back button
        JButton backButton = createStyledLoginButton("Back", e -> {
            frame.getContentPane().removeAll();
            frame.repaint();
            addLoginPanel();
            frame.revalidate();
            frame.repaint();
        });
        studentTeacherPanel.add(backButton);
        studentTeacherPanel.add(Box.createVerticalStrut(20));

        frame.add(studentTeacherPanel, BorderLayout.SOUTH); // Adjusted to add buttons at the bottom
        frame.revalidate();
        frame.repaint();
    }



    private static void addAdminOptionsPanel() {
        frame.getContentPane().removeAll();
        frame.repaint();

        // Top blue bar
        JPanel topBarPanel = new JPanel(new BorderLayout());
        topBarPanel.setBackground(new Color(30, 144, 255));
        topBarPanel.setPreferredSize(new Dimension(frame.getWidth(), 100));

        JLabel welcomeLabel = new JLabel("Welcome Admin", SwingConstants.CENTER);
        welcomeLabel.setForeground(Color.WHITE);
        topBarPanel.add(welcomeLabel, BorderLayout.CENTER);

        frame.add(topBarPanel, BorderLayout.NORTH);

        // Image panel
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));

        // Add some spacing between the top bar and image
        imagePanel.add(Box.createVerticalStrut(10));

        // Add the image from the "Downloads" directory
        String imagePath = System.getProperty("user.home") + "/Downloads/logo3.jpg";
        ImageIcon imageIcon = new ImageIcon(imagePath);

        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imagePanel.add(imageLabel);

        frame.add(imagePanel, BorderLayout.CENTER);

        // Main content panel with white background
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setBackground(Color.WHITE);
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));

        // Buttons panel
        JPanel adminOptionsPanel = new JPanel();
        adminOptionsPanel.setLayout(new BoxLayout(adminOptionsPanel, BoxLayout.Y_AXIS));
        // Add some spacing between the image and buttons
        adminOptionsPanel.add(Box.createVerticalStrut(10));

        JButton displayBooksButton = createStyledLoginButton("Display Books", e -> displayBooks(true));
        adminOptionsPanel.add(displayBooksButton);
        adminOptionsPanel.add(Box.createVerticalStrut(10));
        
        JButton addBookButton = createStyledLoginButton("Add Book", e -> addBookDialog());
        adminOptionsPanel.add(addBookButton);
        adminOptionsPanel.add(Box.createVerticalStrut(10));

        JButton searchBooksButton = createStyledLoginButton("Search Books", e -> searchBooks());
        adminOptionsPanel.add(searchBooksButton);
        adminOptionsPanel.add(Box.createVerticalStrut(10));
        JButton viewCustomerDetailsButton = createStyledLoginButton("View Customer Details", e -> viewCustomerDetails());
        adminOptionsPanel.add(viewCustomerDetailsButton);
        adminOptionsPanel.add(Box.createVerticalStrut(10));
        
        JButton displayFeedbackButton = createStyledLoginButton("View Feedback", e -> displayFeedback(true));
        adminOptionsPanel.add(displayFeedbackButton);
        adminOptionsPanel.add(Box.createVerticalStrut(10));

        JButton exitButton = createStyledLoginButton("Logout", e -> System.exit(0));
        adminOptionsPanel.add(exitButton);
        adminOptionsPanel.add(Box.createVerticalStrut(10));
        JButton backButton = createStyledLoginButton("Back", e -> {
            frame.getContentPane().removeAll();
            frame.repaint();
            addLoginPanel();
            frame.revalidate();
            frame.repaint();
        });
        adminOptionsPanel.add(backButton);
        adminOptionsPanel.add(Box.createVerticalStrut(20));

        mainContentPanel.add(adminOptionsPanel); // Add buttons panel to main content panel
        frame.add(mainContentPanel, BorderLayout.SOUTH); // Adjusted to add buttons at the bottom
        frame.revalidate();
        frame.repaint();
    }

    // Remaining methods remain unchanged

    public static void main(String[] args) {
        runGUI(args);
    }
}