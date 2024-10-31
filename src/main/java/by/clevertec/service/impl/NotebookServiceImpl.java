package by.clevertec.service.impl;

import by.clevertec.dto.NotebookDTO;
import by.clevertec.entity.Notebook;
import by.clevertec.mapper.NotebookMapper;
import by.clevertec.repository.NotebookRepository;
import by.clevertec.repository.impl.NotebookRepositoryImpl;
import by.clevertec.service.NotebookService;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NotebookServiceImpl implements NotebookService {

    private static final NotebookService INSTANCE = new NotebookServiceImpl();
    private final NotebookRepository notebookRepository = NotebookRepositoryImpl.getInstance();

    public static NotebookService getInstance() {
        return INSTANCE;
    }


    @Override
    public List<NotebookDTO> findByParams(String model, String description, String vendorName, String quantity, String price) {
                return notebookRepository.findByParams(model, description, vendorName, quantity, price).stream()
                .map(NotebookMapper.INSTANCE::toNotebookDto)
                .toList();
    }

    @Override
    public NotebookDTO save(NotebookDTO notebookDTO) {
        Notebook savedNotebook = notebookRepository.add(NotebookMapper.INSTANCE.toNotebook(notebookDTO));
        return NotebookMapper.INSTANCE.toNotebookDto(savedNotebook);
    }
}
