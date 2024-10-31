package by.clevertec.servlet;

import by.clevertec.dto.NotebookDTO;
import by.clevertec.service.NotebookService;
import by.clevertec.service.impl.NotebookServiceImpl;
import com.google.gson.Gson;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static jakarta.servlet.http.HttpServletResponse.SC_CREATED;
import static jakarta.servlet.http.HttpServletResponse.SC_OK;


@WebServlet(name = "notebook-servlet", value = "/notebook")
@Slf4j
public class Servlet extends HttpServlet {

    private final NotebookService notebookService = NotebookServiceImpl.getInstance();
    private NotebookDTO notebookDTO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init();
        notebookDTO = NotebookDTO.builder().build();
        log.info("init method from NotebookServlet");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("NotebookDTO from doGet method {}", notebookDTO);
        String model = req.getParameter("model");
        String description = req.getParameter("description");
        String vendorName = req.getParameter("vendorName");
        String quantity = req.getParameter("quantity");
        String price = req.getParameter("price");
        List<NotebookDTO> notebooks = notebookService.findByParams(model, description, vendorName, quantity, price);
        String json = new Gson().toJson(notebooks);
        try (PrintWriter out = resp.getWriter()) {
            out.write(json);
            resp.setStatus(SC_OK);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        NotebookDTO notebookNewDTO = new Gson().fromJson(req.getReader(), NotebookDTO.class);
        String json = new Gson().toJson(notebookService.save(notebookNewDTO));
        try (PrintWriter out = resp.getWriter()) {
            out.write(json);
            resp.setStatus(SC_CREATED);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        log.info("destroy method from NotebookServlet");
    }
}
