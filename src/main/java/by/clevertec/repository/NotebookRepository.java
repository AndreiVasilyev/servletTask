package by.clevertec.repository;

import by.clevertec.entity.Notebook;
import by.clevertec.util.Constants;
import org.bson.Document;

import java.util.List;

public interface NotebookRepository extends Repository<Notebook> {

    List<Notebook> findByParams(String model, String description, String vendorName, String quantity, String price);


    default Document buildDocument(Notebook notebook) {
        return new Document()
                .append(Constants.NOTEBOOK_ID, notebook.getId())
                .append(Constants.NOTEBOOK_MODEL, notebook.getModel())
                .append(Constants.NOTEBOOK_DESCRIPTION, notebook.getDescription())
                .append(Constants.NOTEBOOK_DESCRIPTION, notebook.getVendorName())
                .append(Constants.NOTEBOOK_QUANTITY, notebook.getQuantity())
                .append(Constants.NOTEBOOK_PRICE, notebook.getPrice());
    }

    default Document buildDocumentById(String id) {
        Document document = new Document();
        if (id != null) {
            document.put(Constants.NOTEBOOK_ID, id);
        }
        return document;
    }

    default Document buildDocumentByParams(String model, String description, String vendorName, String quantity, String price) {
        Document document = new Document();
        putDocument(document, Constants.NOTEBOOK_MODEL, model);
        putDocument(document, Constants.NOTEBOOK_DESCRIPTION, description);
        putDocument(document, Constants.NOTEBOOK_VENDOR_NAME, vendorName);
        putDocument(document, Constants.NOTEBOOK_QUANTITY, quantity);
        putDocument(document, Constants.NOTEBOOK_PRICE, price);
        return document;
    }

    default void putDocument(Document document, String name, String value) {
        if (value != null) {
            document.put(name, value);
        }
    }

    default Notebook buildNotebook(Document document) {
        return Notebook.builder()
                .id(document.getString(Constants.NOTEBOOK_ID))
                .model(document.getString(Constants.NOTEBOOK_MODEL))
                .description(document.getString(Constants.NOTEBOOK_DESCRIPTION))
                .vendorName(document.getString(Constants.NOTEBOOK_VENDOR_NAME))
                .quantity(document.getInteger(Constants.NOTEBOOK_QUANTITY))
                .price(document.getDouble(Constants.NOTEBOOK_PRICE))
                .build();
    }

}
