
package app;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

public class Connect {
    public Connection conn;
    public Statement stmt;
    public ResultSet rs;
    
    public Connect(){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:kursova.db");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
    }
    /**
     * Metod, koito stroi standartna SQL SELECT zaqvka
     * po dadeni masiv ot koloni za kolonite, za koito
     * iskame da vurne rezultat i ot koq tablica.
     * @param columnsArray kolonite, za koito iskame danni
     * @param table tablicata, ot koqto izvikvame danni
     * @return dinamichen masiv s dannite ot zaqvkata
     */
    public ArrayList<String> select(String[] columnsArray, String table){
        ArrayList data = new ArrayList<String>();
        
        //columnsArray -> [0] "FirstName", [1]"LastName"
        String columns = String.join(", ", columnsArray);
        //columns = FirstName, Lastname
        
        //SELECT FirstName, LastName FROM Employees
        String sql = "SELECT " + columns + " FROM " + table;
        //SELECT Firstname, lastname from Employees
        try{
            System.out.println(sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                String row = "";
                //columnsArray -> [0] "FirstName", [1]"LastName"
                for (int i = 0; i < columnsArray.length; i++) {
                    row = row + rs.getString(columnsArray[i])+"---";
                    //i=0 row=""+ Andrew---
                    //i=1 row= Andrew---Adams---
                }
                row=row.substring(0, row.length()-3);
                data.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    /**
     * Metod, koito stroi standartna SQL SELECT WHERE zaqvka
     * po dadeni masiv ot koloni za kolonite, za koito
     * iskame da vurne rezultat, ot koq tablica, po koq
     * kolona da tursi i kakva stoinost.
     * @param columnsArray kolonite, za koito iskame danni
     * @param table tablicata, ot koqto izvikvame danni
     * @param whereColumn v koq kolona da tursi suvpadenie
     * @param whereValue kakva stoinost da tursi v kolonata
     * @return dinamichen masiv s dannite ot zaqvkata
     */
    //select firstname, lastname from employees where city like '%a%'
    public ArrayList<String> selectWhere(String[] columnsArray, String table, String whereColumn, String whereValue){
        ArrayList data = new ArrayList<String>();
        
        //columnsArray -> [0] "FirstName", [1]"LastName"
        String columns = String.join(", ", columnsArray);
        //columns = FirstName, Lastname
        
        //SELECT FirstName, LastName FROM Employees where city like '%a%'
        String sql = "SELECT " + columns + " FROM " + table 
                + " WHERE " + whereColumn + " LIKE '%" + whereValue+"%'";
        //SELECT Firstname, lastname from Employees WHERE city LIKE '%a%'
        try{
            System.out.println(sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                String row = "";
                //columnsArray -> [0] "FirstName", [1]"LastName"
                for (int i = 0; i < columnsArray.length; i++) {
                    row = row + rs.getString(columnsArray[i])+"---";
                    //i=0 row=""+ Andrew---
                    //i=1 row= Andrew---Adams---
                }
                row=row.substring(0, row.length()-3);
                data.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    /**
     * Metod, koito stroi SQL SELECT WHERE zaqvka
     * po dadeni masiv ot koloni za kolonite, za koito
     * iskame da vurne rezultat, ot koq tablica i po koi
     * koloni da tursi suvpadenie, kato e neobhodimo samo
     * ednoto uslovie da e uspylneno.Dannite se podavat
     * v dva masiva, kato vseki element na ediniq suotvetstva
     * na elementiq sus sushtiq index ot drugiq.
     * @param columnsArray kolonite, za koito iskame danni
     * @param table tablicata, ot koqto izvikvame danni
     * @param whereColumns po koi koloni da tursi
     * @param whereValues kakvi stoinosti da tursi
     * @return dinamichen masiv s dannite ot zaqvkata
     */
    public ArrayList<String> selectWhereOr(String[] columnsArray, String table, String[] whereColumns, String[] whereValues){
        ArrayList data = new ArrayList<String>();
        
        //columnsArray -> [0] "FirstName", [1]"LastName"
        String columns = String.join(", ", columnsArray);
        //columns = FirstName, Lastname
        
        
        String sql = "SELECT " + columns + " FROM " + table + " WHERE ";
        
        for (int i = 0; i < whereColumns.length; i++) {
            sql=sql+whereColumns[i]+" LIKE '%" + whereValues[i]+"%' OR ";
        }
        sql=sql.substring(0,sql.length()-4);
        try{
            System.out.println(sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                String row = "";
                //columnsArray -> [0] "FirstName", [1]"LastName"
                for (int i = 0; i < columnsArray.length; i++) {
                    row = row + rs.getString(columnsArray[i])+"---";
                    //i=0 row=""+ Andrew---
                    //i=1 row= Andrew---Adams---
                }
                row=row.substring(0, row.length()-3);
                data.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    
    /**
     * Metod, koito stroi SQL SELECT WHERE zaqvka
     * po dadeni masiv ot koloni za kolonite, za koito
     * iskame da vurne rezultat, ot koq tablica i po koi
     * koloni da tursi suvpadenie, kato e neobhodimo VSICHKI
     * usloviq da sa uspylneni.Dannite se podavat
     * v dva masiva, kato vseki element na ediniq suotvetstva
     * na elementiq sus sushtiq index ot drugiq.
     * @param columnsArray kolonite, za koito iskame danni
     * @param table tablicata, ot koqto izvikvame danni
     * @param whereCols v koi koloni tursi stoinost
     * @param whereValues kakvi stoinosti da tursi
     * @return dinamichen masiv s dannite ot zaqvkata
     */
    public ArrayList<String> selectWhereAnd(String[] columnsArray, String table, String[] whereCols, String[] whereValues){
        ArrayList data = new ArrayList<String>();
        //columnsArray -> [0]"FirstName", [1]"LastName"
        String columns = String.join(", ",columnsArray);
        //columns -> "Firstname, Lastname"
        //table = Customers
        
        //select fn, ln from cstmrs where fn like 'a' or ln like 'b'
        String sql = "SELECT " + columns + " FROM " + table + " WHERE ";
        //SELECT Firstname, Lastname FROM Employees WHERE 
        //whereCols->[0]FirstName, [1]LastName
        //wehrevalues->[0]a, [1]b
        for(int i=0; i<whereCols.length; i++){
            sql = sql + whereCols[i] + " LIKE '" + whereValues[i] + "' AND ";
            //I==0 -> sql = ...where FirstName like 'a' AND 
            //i=1 -> sql=...where FirstName like 'a' OR Lastname like 'b' AND 
        }
        sql = sql.substring(0,sql.length()-5);
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            System.out.println(sql);
            while(rs.next()){
                String row="";
                for (int i = 0; i < columnsArray.length; i++) {
                    row+=rs.getString(columnsArray[i])+"---";
                }
                //andrew---adams---
                row=row.substring(0, row.length()-3);
               data.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return data;
    }
    
     /**
     * Metod, koito stroi INSERT zaqvka
     * @param table v koq tablica da dobavq
     * @param columnsArray v koi koloni da dobavq
     * @param valuesArray kakvi danni da dobavq
     */
    //insert into users (username, password) values ('ime', 'parola')
    public void insert(String table, String[] columnsArray, String[] valuesArray){
        //columnsarray-> "username", "password"
        String columns = String.join(", ", columnsArray);
        //columns-> username, password
        //valuesarray-> "ime", "parola"
        String values = String.join("', '", valuesArray);
        //values-> ime', 'parola
        String sql = "Insert into " + table + " ("+columns+") values ('"+values+"')";
        //insert into users (username, password) values ('ime', 'parola')

        System.out.println(sql);
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Metod, koito stroi UPDATE zaqvka
     * @param table v koq tablica shte promenq danni
     * @param columnsArray za koi koloni shte promenq danni
     * @param valuesArray kakvi stoinosti shte promenq
     * @param whereCol po kakva kolona shte tursi suvpadenie
     * @param whereVal kakvo syvpadenie shte tursi
     */
    //update employees set fn='ime', ln='familiq', title='titla', city='grad', country='durjava' where employeeid='1'
    public void update(String table, String[] columnsArray, String[] valuesArray, String whereCol, String whereVal){
        //columnsarray->fn, ln, title,city,country
        //valuesarray-> ime,familia,titlta,grad,durjava
        String sql = "update "+ table + " set ";
        for (int i = 0; i < columnsArray.length; i++) {
            sql=sql+columnsArray[i]+" = '"+valuesArray[i]+"', ";
        }
        sql=sql.substring(0, sql.length()-2);
        sql = sql + " WHERE "+ whereCol + " = '"+whereVal+"'";
        System.out.println(sql);
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Metod, koito stroi DELETE WHERE zaqvka
     * @param table ot koq tablica da trie
     * @param whereCol v koq kolona da tursi stoinost
     * @param whereValue kakva stoinost da tursi
     */
    //delete from employees where employeeid like '9'
    public void delete(String table, String whereCol, String whereValue){
        String sql = "DELETE FROM " + table + " WHERE "+
                whereCol + " LIKE '"+whereValue+"'";
        try{
            System.out.println(sql);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    /**
     * Zatvarq vruzkata s bazata.
     */
    public void close(){
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Throwable ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
