package Dashboard;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Java.CustomerManager;

public class deposit {

	public static void main(String userName, double amt, String pass, HttpServletRequest request, HttpServletResponse response) {
		
		PreparedStatement st=null;
		Connection conn=null;
		
		try 
		{
			conn=CustomerManager.getConnection();
			st = conn.prepareStatement("select pass,balance from Customer where accountNo=?");
			st.setString(1,userName);
            ResultSet rs = st.executeQuery();
            rs.next();
            PrintWriter out = response.getWriter();
            if(rs.getString(1).equals(pass) || rs.getString(1) == pass)
            {
            	double bal = rs.getDouble(2);
            	bal = bal + amt;
            	PreparedStatement st1 = conn.prepareStatement("update Customer set balance=? where accountNo=?");
            	st1.setDouble(1,bal);
            	st1.setString(2,userName);
            	st1.executeUpdate();
//            	out.println("<font color=red>Transaction Successful.</font>");
            	out.print("<script type=\"text/javascript\">alert(\"The Transaction is succesful! :)\")</script>");
            	RequestDispatcher rd = request.getRequestDispatcher("Deposit.jsp");
        		rd.include(request, response);
            }
            else
            {
//        		out.println("<font color=red>Password is wrong.</font>");
            	out.print("<script type=\"text/javascript\">alert(\"The Password was wrong! :(\")</script>");
        		RequestDispatcher rd = request.getRequestDispatcher("Dashboard.jsp");
        		rd.include(request, response);
            }
            
            
            
            st.close();
            conn.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
}
