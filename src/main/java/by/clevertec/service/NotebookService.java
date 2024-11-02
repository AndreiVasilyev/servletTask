package by.clevertec.service;

import by.clevertec.dto.NotebookDTO;

import java.util.List;

public interface NotebookService {

    List<NotebookDTO> findAll();

    NotebookDTO findById(String id);

    List<NotebookDTO> findByParams(String model, String description, String vendorName, String quantity, String price);

    NotebookDTO save(NotebookDTO notebookDTO);

    NotebookDTO update(NotebookDTO notebookDTO);

    NotebookDTO delete(String id);
}
