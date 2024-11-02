package by.clevertec.repository;

import by.clevertec.entity.Notebook;
import by.clevertec.util.Constants;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;

import static by.clevertec.util.Constants.NOTEBOOK_DESCRIPTION;
import static by.clevertec.util.Constants.NOTEBOOK_ID;
import static by.clevertec.util.Constants.NOTEBOOK_MODEL;
import static by.clevertec.util.Constants.NOTEBOOK_PRICE;
import static by.clevertec.util.Constants.NOTEBOOK_QUANTITY;
import static by.clevertec.util.Constants.NOTEBOOK_VENDOR_NAME;

public interface NotebookRepository extends Repository<Notebook> {

    List<Notebook> findByParams(String model, String description, String vendorName, String quantity, String price);


    default Document buildDocument(Notebook notebook) {
        return new Document()
                .append(NOTEBOOK_MODEL, notebook.getModel())
                .append(NOTEBOOK_DESCRIPTION, notebook.getDescription())
                .append(NOTEBOOK_VENDOR_NAME, notebook.getVendorName())
                .append(NOTEBOOK_QUANTITY, notebook.getQuantity())
                .append(NOTEBOOK_PRICE, notebook.getPrice());
    }

    default Document buildDocumentById(String id) {
        Document document = new Document();
        document.put(NOTEBOOK_ID, new ObjectId(id));
        return document;
    }

    default Document buildDocumentByParams(String model, String description, String vendorName, String quantity, String price) {
        Document document = new Document();
        putDocument(document, NOTEBOOK_MODEL, model);
        putDocument(document, NOTEBOOK_DESCRIPTION, description);
        putDocument(document, NOTEBOOK_VENDOR_NAME, vendorName);
        putDocument(document, NOTEBOOK_QUANTITY, quantity);
        putDocument(document, NOTEBOOK_PRICE, price);
        return document;
    }

    default void putDocument(Document document, String name, String value) {
        if (value != null) {
            document.put(name, value);
        }
    }

    default Notebook buildNotebook(Document document) {
        ObjectId id = document.getObjectId(NOTEBOOK_ID);
        return Notebook.builder()
                .id(id.toString())
                .model(document.getString(NOTEBOOK_MODEL))
                .description(document.getString(NOTEBOOK_DESCRIPTION))
                .vendorName(document.getString(Constants.NOTEBOOK_VENDOR_NAME))
                .quantity(document.getInteger(NOTEBOOK_QUANTITY))
                .price(document.getDouble(NOTEBOOK_PRICE))
                .build();
    }

}
