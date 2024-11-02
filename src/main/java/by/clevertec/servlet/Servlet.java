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
import java.util.ArrayList;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("NotebookDTO from doGet method {}", notebookDTO);
        String id = req.getParameter("id");
        String model = req.getParameter("model");
        String description = req.getParameter("description");
        String vendorName = req.getParameter("vendorName");
        String quantity = req.getParameter("quantity");
        String price = req.getParameter("price");
        List<NotebookDTO> notebooks = new ArrayList<>();
        if (id != null) {
            NotebookDTO notebook = notebookService.findById(id);
            notebooks.add(notebook);
        } else if (model != null || description != null || vendorName != null || quantity != null || price != null) {
            notebooks = notebookService.findByParams(model, description, vendorName, quantity, price);
        } else {
            notebooks = notebookService.findAll();
        }
        sendResult(notebooks, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        NotebookDTO notebookNewDTO = new Gson().fromJson(req.getReader(), NotebookDTO.class);
        NotebookDTO sevedNotebookDTO = notebookService.save(notebookNewDTO);
        sendResult(sevedNotebookDTO, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        NotebookDTO notebookNewDTO = new Gson().fromJson(req.getReader(), NotebookDTO.class);
        NotebookDTO updatedNotebookDTO = notebookService.update(notebookNewDTO);
        sendResult(updatedNotebookDTO, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //NotebookDTO notebookDeletedDTO = new Gson().fromJson(req.getReader(), NotebookDTO.class);
        String id = req.getParameter("id");
        NotebookDTO deletedNotebookDTO = notebookService.delete(id);
        sendResult(deletedNotebookDTO, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
        log.info("destroy method from NotebookServlet");
    }

    private <T> void sendResult(T result, HttpServletResponse response) throws IOException {
        String json = new Gson().toJson(result);
        try (PrintWriter out = response.getWriter()) {
            out.write(json);
            response.setStatus(SC_OK);
        }
    }
}
