package org.zjw.blog.base.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TicketServlet extends HttpServlet {
    private static final long serialVersionUID = 5964206637772848290L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ticket = request.getParameter("ticket");
        String username = (String) JVMCache.map.get(ticket);
//        JVMCache.map.remove(ticket);
        PrintWriter writer = response.getWriter();
        writer.write(username);
    }

}