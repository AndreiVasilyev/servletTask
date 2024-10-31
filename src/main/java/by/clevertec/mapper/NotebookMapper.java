package by.clevertec.mapper;

import by.clevertec.dto.NotebookDTO;
import by.clevertec.entity.Notebook;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NotebookMapper {
    NotebookMapper INSTANCE = Mappers.getMapper(NotebookMapper.class);

    NotebookDTO toNotebookDto(Notebook notebook);

    Notebook toNotebook(NotebookDTO notebookDTO);
}
