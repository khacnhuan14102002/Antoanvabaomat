package vn.edu.hcmuaf.fit.controller;

import vn.edu.hcmuaf.fit.bean.Invoice;
import vn.edu.hcmuaf.fit.bean.Key;
import vn.edu.hcmuaf.fit.bean.RSAKeyGenerator;
import vn.edu.hcmuaf.fit.bean.User;
import vn.edu.hcmuaf.fit.service.InvoiceService;
import vn.edu.hcmuaf.fit.service.KeyService;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Time;
import java.sql.Timestamp;

@WebServlet(name = "vetify", value = "/vetify")
public class VetifyControll extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        String idinvoice = request.getParameter("Idinvoice");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int invoiceid =0;
        try{
            invoiceid = Integer.parseInt(idinvoice);
        }catch(NumberFormatException e){
            e.printStackTrace();

        }


        RSAKeyGenerator rsa = new RSAKeyGenerator();
        InvoiceService invoicese = new InvoiceService();
        KeyService key = new KeyService();
        Key k = new Key();
        Timestamp timeinvoice = invoicese.getTime(invoiceid);
        Timestamp timere = key.getTime(user.getIdUser());
        String pub = key.getPublic(user.getIdUser());
        String cipher=invoicese.getCipher(invoiceid);
        boolean hl;
        String mess = " ";
        if (timere==null) {
            hl = rsa.areCypherText(cipher, pub);
            System.out.println("Kết quả là :" + hl);
            if (hl) {
                mess = "Confirmed";
                invoicese.updateStat(invoiceid, mess);
            } else{
                mess = "Failed";
            }

        } else if(timeinvoice.after(timere)){
           mess="Khóa không còn được sử dụng";
        }
        request.setAttribute("mess", mess);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/historyinvoice");
        dispatcher.forward(request, resp);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
