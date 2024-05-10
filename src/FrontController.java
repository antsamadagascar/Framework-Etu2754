package mg.itu.prom16;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class FrontController extends HttpServlet {
    public void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        try {
            out.println("Your're welcome. Your url:  "+request.getRequestURI());
        } catch (Exception e) {
            e.printStackTrace(out);
        }        
        out.close();
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        processRequest(request,response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        processRequest(request,response);
    }
}