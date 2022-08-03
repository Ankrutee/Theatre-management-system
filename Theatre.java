import java.sql.*;
import java.util.Scanner;

public class Theatre {

   static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
   static final String DB_URL = "jdbc:mysql://localhost/Theatre?useSSL=false";
   static final String USER = "ankrutee";
   static final String PASS = "9246809610";

   public static void main(String[] args) {

      Connection conn = null;
      Statement stmt = null;
      try {
         Class.forName(JDBC_DRIVER);
         conn = DriverManager.getConnection(DB_URL, USER, PASS);
         stmt = conn.createStatement();
         Scanner scan = new Scanner(System.in); // Create a Scanner object

         clearScreen();
         System.out.println("\nWELCOME TO UR THEATRE!\n");
         main_menu(stmt, scan);

         scan.close();
         stmt.close();
         conn.close();
      } catch (SQLException se) { 
          se.printStackTrace();
      } catch (Exception e) { 
          e.printStackTrace();
      } finally {
         try {
            if (stmt != null)
               stmt.close();
         } catch (SQLException se2) {
         }
         try {
            if (conn != null)
               conn.close();
         } catch (SQLException se) {
             se.printStackTrace();
         }
      }
   }

   static void main_menu(Statement stmt, Scanner scan) {
      System.out.println("Login as a- ");
      System.out.println("1. Customer");
      System.out.println("2. Staff");
      System.out.println("3. Super Admin");
      System.out.println("0. Exit");

      System.out.print("\n\nENTER YOUR CHOICE : ");
      int choice = Integer.parseInt(scan.nextLine());
      clearScreen();

      switch (choice) {
         case 0:
            System.out.println("\nThank You for coming, Visit Us Again!!\n\n");
            System.exit(0);
         case 1:
            customer_menu(stmt, scan);
            break;
         case 2:
            check_TheatreStaff(stmt, scan);
            break;
         case 3:
            check_super_admin(stmt, scan);
            break;
         default:
            clearScreen();
            System.out.println("Your entered choice is invalid. Please enter a valid choice.\n");
            break;
      }
      main_menu(stmt, scan);
   }

   static void customer_menu(Statement stmt, Scanner scan) {
      System.out.println("Please select appropriate option-");
      System.out.println("1. List of available movies");
      System.out.println("0. Exit");

      System.out.print("\n\nENTER YOUR CHOICE : ");
      int choice = Integer.parseInt(scan.nextLine());
      clearScreen();

      switch (choice) {
         case 0:
            return;
         case 1:
            list_of_movies(stmt, scan, true);
            break;
         default:
            clearScreen();
            System.out.println("Please Enter a Valid Choice!!\n");
            break;
      }
      customer_menu(stmt, scan);
   }

   static boolean authentication(Statement stmt, Scanner scan, boolean isSuperAdmin) {
      System.out.print("Enter your ID: ");
      String id = scan.nextLine();
      System.out.print("Enter your password: ");
      String password = scan.nextLine();

      clearScreen();
      boolean authenticated = false;

      if (isSuperAdmin) {
         String sql = "SELECT * from super_admin";
         ResultSet rs = executeSqlStmt(stmt, sql);

         try {
            while (rs.next()) {
               String possible_id = rs.getString("super_admin_id");
               String possible_password = rs.getString("super_admin_password");

               if (possible_id.equals(id) && password.equals(possible_password)) {
                  authenticated = true;
                  break;
               }
            }
         } catch (SQLException se) {
         }
      } else {
         String sql = "SELECT * from TheatreStaff";
         ResultSet rs = executeSqlStmt(stmt, sql);

         try {
            while (rs.next()) {
               String possible_id = rs.getString("staff_id");
               String possible_password = rs.getString("staff_password");

               if (possible_id.equals(id) && password.equals(possible_password)) {
                  authenticated = true;
                  break;
               }
            }
         } catch (SQLException se) {
         }
      }
      return authenticated;
   }

   static void check_TheatreStaff(Statement stmt, Scanner scan) {
      if (authentication(stmt, scan, false)) {
         TheatreStaff_menu(stmt, scan);
      } else {
         System.out.print("Entered details were incorrect. Do you want to retry (Y/N)? ");
         String input = scan.nextLine();
         if (input.equals("Y"))
            check_TheatreStaff(stmt, scan);
         else
            return;
      }
   }

   static void check_super_admin(Statement stmt, Scanner scan) {
      if (authentication(stmt, scan, true)) {
         super_admin_menu(stmt, scan);
      } else {
         System.out.print("Entered details were incorrect. Do you want to retry (Y/N)? ");
         String input = scan.nextLine();
         if (input.equals("Y"))
            check_super_admin(stmt, scan);
         else
            return;
      }
   }

   static void TheatreStaff_menu(Statement stmt, Scanner scan) {
      System.out.println("Please select appropriate option-");
      System.out.println("1. List of all Movies");
      System.out.println("2. Get a Ticket");
      System.out.println("3. Cancel A Ticket");
      System.out.println("4. Add a Movie");
      System.out.println("5. Delete a Movie");
      System.out.println("0. Exit");
      System.out.print("\n\nENTER YOUR CHOICE : ");
      int choice = Integer.parseInt(scan.nextLine());
      clearScreen();

      switch (choice) {
         case 0:
            return;
         case 1:
            list_of_movies(stmt, scan, false);
            break;
         case 2:
            sell_movie(stmt, scan);
            break;
         case 3:
            return_movie(stmt, scan);
            break;
         case 4:
            add_movie(stmt, scan);
            break;
         case 5:
            delete_movie(stmt, scan);
            break;
         default:
            clearScreen();
            System.out.println("Please Enter a Valid Choice!!\n");
            break;
      }
      TheatreStaff_menu(stmt, scan);
   }

   static void super_admin_menu(Statement stmt, Scanner scan) {
      System.out.println("Please select appropriate option-");
      System.out.println("1. List of Customers");
      System.out.println("2. List of Theatre staff members");
      System.out.println("3. Add a customer");
      System.out.println("4. Remove a customer");
      System.out.println("5. Add a theatre staff");
      System.out.println("6. Delete a theatre staff");
      System.out.println("0. Exit");

      System.out.print("\n\nENTER YOUR CHOICE : ");
      int choice = Integer.parseInt(scan.nextLine());
      clearScreen();

      switch (choice) {
         case 0:
            return;
         case 1:
            list_of_customers(stmt, scan);
            break;
         case 2:
            list_of_TheatreStaffs(stmt, scan);
            break;
         case 3:
            add_customer(stmt, scan);
            break;
         case 4:
            delete_customer(stmt, scan);
            break;
         case 5:
            add_TheatreStaff(stmt, scan);
            break;
         case 6:
            delete_TheatreStaff(stmt, scan);
            break;
         default:
            clearScreen();
            System.out.println("Please Enter a Valid Choice!!\n");
            break;
      }
      super_admin_menu(stmt, scan);
   }

   static boolean list_of_movies(Statement stmt, Scanner scan, boolean checkAvailable) {
      String sql = "select * from movie";
      ResultSet rs = executeSqlStmt(stmt, sql);
      boolean noMovie= true;

      try {
        System.out.println("List of Movies:\n");
         while (rs.next()) {
            String id = rs.getString("movie_id");
            String name = rs.getString("movie_name");
            String customer = rs.getString("customer");

            if (checkAvailable) {
               if (customer == null) {
                  System.out.println("Movie ID : " + id);
                  System.out.println("Movie Name: " + name);
                  System.out.println("");
                  noMovie = false;
               }
            } else {
                  System.out.println("Movie ID : " + id);
                  System.out.println("Movie Name: " + name);
                  
                  System.out.println("");
                  noMovie = false;
            }
         }

            if (noMovie)
            System.out.println("Sorry, no movie available!\n");

         rs.close();
      } catch (SQLException e) {
          //e.printStackTrace();
      }
      return noMovie;
   }

   static void sell_movie(Statement stmt, Scanner scan) {
      try {
         boolean noMovie = list_of_movies(stmt, scan, true);
         if (!noMovie) {
            System.out.print("\nEnter Movie id : ");
            String movie_id = scan.nextLine();

            System.out.print("Enter customer id : ");
            String customer_id = scan.nextLine();

            clearScreen();

            String sql = String.format("UPDATE movie SET customer = '%s' WHERE movie_id = '%s'", customer_id, movie_id);
            int result = updateSqlStmt(stmt, sql);

            if (result != 0)
               System.out.println("CUSTOMER HAS BEEN UPDATED SUCCESFULLY!!\n");
            else
               System.out.println("Something went wrong!\n");
         }
      } catch (Exception e) {
          e.printStackTrace();
      }
   }

   static void return_movie(Statement stmt, Scanner scan) {
      try {
         System.out.print("\nEnter Movie id : ");
         String movie_id = scan.nextLine();

         clearScreen();

         String sql = String.format("UPDATE movie SET customer = NULL WHERE movie_id = '%s'", movie_id);
         int result = updateSqlStmt(stmt, sql);

         if (result != 0)
            System.out.println("Movie Ticket has been cancelled!!\n");
         else
            System.out.println("Something went wrong!\n");
      } catch (Exception e) {
          e.printStackTrace();
      }
   }

   static void add_movie(Statement stmt, Scanner scan) {
      try {
         System.out.print("\nEnter Movie id : ");
         String movie_id = scan.nextLine();
         System.out.print("\nEnter Movie name : ");
         String movie_name = scan.nextLine();
         
         clearScreen();

         String sql = String.format("INSERT INTO movie VALUES('%s', '%s', NULL);", movie_id, movie_name);
         int result = updateSqlStmt(stmt, sql);

         if (result != 0)
            System.out.println("Movie has been added successfully!!\n");
         else
            System.out.println("Something went wrong!\n");
      } catch (Exception e) {
          e.printStackTrace();
      }
   }

   static void delete_movie(Statement stmt, Scanner scan) {
      try {
         System.out.print("\nEnter movie ID : ");
         String movie_id = scan.nextLine();

         clearScreen();

         String sql = String.format("DELETE FROM movie where movie_id = '%s'", movie_id);
         int result = updateSqlStmt(stmt, sql);

         if (result != 0)
            System.out.println("Movie has been deleted successfully!!\n");
         else
            System.out.println("Something went wrong!\n");
      } catch (Exception e) {
          e.printStackTrace();
      }
   }

   static void list_of_customers(Statement stmt, Scanner scan) {
      String sql = "select * from customer";
      ResultSet rs = executeSqlStmt(stmt, sql);

      try {
         System.out.println("List of customers:\n");
         while (rs.next()) {
            String customer_id = rs.getString("customer_id");
            String customer_name = rs.getString("customer_name");

            System.out.println("Customer Id : " + customer_id);
            System.out.println("Customer Name : " + customer_name);
            System.out.println("\n");
         }

         rs.close();
      } catch (SQLException e) {
          e.printStackTrace();
      }
   }

   static void list_of_TheatreStaffs(Statement stmt, Scanner scan) {
      String sql = "select * from TheatreStaff";
      ResultSet rs = executeSqlStmt(stmt, sql);

      try {
         System.out.println("List of Staff members:\n");
         while (rs.next()) {
            String staff_id = rs.getString("staff_id");
            String staff_name = rs.getString("staff_name");

            System.out.println("Staff Id : " + staff_id);
            System.out.println("Staff Name: " + staff_name);
            System.out.println("\n");
         }

         rs.close();
      } catch (SQLException e) {
          e.printStackTrace();
      }
   }

   static void add_customer(Statement stmt, Scanner scan) {
      try {
         System.out.print("Enter customer id : ");
         String customer_id = scan.nextLine();
         System.out.print("Enter customer name : ");
         String customer_name = scan.nextLine();

         clearScreen();

         String sql = String.format("INSERT INTO customer VALUES('%s', '%s', NULL)", customer_id, customer_name);
         int result = updateSqlStmt(stmt, sql);

         if (result != 0)
            System.out.println("Customer has been added successfully!!\n");
         else
            System.out.println("Something went wrong!\n");
      } catch (Exception e) {
          e.printStackTrace();
      }
   }

   static void add_TheatreStaff(Statement stmt, Scanner scan) {
      try {
         System.out.print("Enter Staff id : ");
         String staff_id = scan.nextLine();
         System.out.print("Enter Staff name : ");
         String staff_name = scan.nextLine();
         System.out.print("Enter Staff password : ");
         String staff_password = scan.nextLine();

         clearScreen();

         String sql = String.format("INSERT INTO TheatreStaff VALUES('%s', '%s', '%s')", staff_id, staff_name, staff_password);
         int result = updateSqlStmt(stmt, sql);

         if (result != 0)
            System.out.println("Staff has been added successfully!!\n");
         else
            System.out.println("Something went wrong!\n");
      } catch (Exception e) {
          e.printStackTrace();
      }
   }

   static void delete_customer(Statement stmt, Scanner scan) {
      try {
         System.out.print("Enter customer id : ");
         String customer_id = scan.nextLine();

         clearScreen();

         String sql = String.format("DELETE FROM customer where customer_id = '%s'", customer_id);
         int result = updateSqlStmt(stmt, sql);

         if (result != 0)
            System.out.println("Customer has been deleted successfully!!\n");
         else
            System.out.println("Something went wrong!\n");
      } catch (Exception e) {
          e.printStackTrace();
      }
   }

   static void delete_TheatreStaff(Statement stmt, Scanner scan) {
      try {
         System.out.print("Enter Staff id : ");
         String id = scan.nextLine();

         clearScreen();

         String sql = String.format("DELETE FROM TheatreStaff where staff_id = '%s'", id);
         int result = updateSqlStmt(stmt, sql);

         if (result != 0)
            System.out.println("Staff has been deleted successfully!!\n");
         else
            System.out.println("Something went wrong!\n");
      } catch (Exception e) {
          e.printStackTrace();
      }
   }

   static ResultSet executeSqlStmt(Statement stmt, String sql) {
      try {
         ResultSet rs = stmt.executeQuery(sql);
         return rs;
      } catch (SQLException se) {
          se.printStackTrace();
      } catch (Exception e) {
          e.printStackTrace();
      }
      return null;
   }

   static int updateSqlStmt(Statement stmt, String sql) {
      try {
         int rs = stmt.executeUpdate(sql);
         return rs;
      } catch (SQLException se) {
          //se.printStackTrace();
      } catch (Exception e) {
          //e.printStackTrace();
      }
      return 0;
   }

   static void clearScreen() {
      System.out.println("\033[H\033[J");
      System.out.flush();
   }
}
