package by.clevertec.repository.impl;

import by.clevertec.entity.Notebook;
import by.clevertec.repository.NotebookRepository;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.BsonValue;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static by.clevertec.util.Constants.COLLECTION_NAME;
import static by.clevertec.util.Constants.MONGO_DB_NAME;
import static by.clevertec.util.Constants.MONGO_DB_URL;
import static by.clevertec.util.Constants.NOTEBOOK_ID;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class NotebookRepositoryImpl implements NotebookRepository {

    private static final NotebookRepository INSTANCE = new NotebookRepositoryImpl();


    public static NotebookRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Notebook> findAll() {
        try (MongoClient mongoClient = MongoClients.create(MONGO_DB_URL)) {
            MongoCollection<Document> collection = getCollection(mongoClient);
            FindIterable<Document> cursor = collection.find();
            return collectResults(cursor);
        }
    }

    @Override
    public Notebook findById(String id) {
        try (MongoClient mongoClient = MongoClients.create(MONGO_DB_URL)) {
            MongoCollection<Document> collection = getCollection(mongoClient);
            Document document = buildDocumentById(id);
            FindIterable<Document> cursor = collection.find(document);
            try (MongoCursor<Document> cursorIterator = cursor.cursor()) {
                if (cursorIterator.hasNext()) {
                    return buildNotebook(cursorIterator.next());
                }
                return null;
            }
        }
    }

    @Override
    public List<Notebook> findByParams(String model, String description, String vendorName, String quantity, String price) {
        try (MongoClient mongoClient = MongoClients.create(MONGO_DB_URL)) {
            MongoCollection<Document> collection = getCollection(mongoClient);
            Document document = buildDocumentByParams(model, description, vendorName, quantity, price);
            FindIterable<Document> cursor = collection.find(document);
            return collectResults(cursor);
        }
    }

    @Override
    public Notebook add(Notebook notebook) {
        try (MongoClient mongoClient = MongoClients.create(MONGO_DB_URL)) {
            MongoCollection<Document> collection = getCollection(mongoClient);
            Document document = buildDocument(notebook);
            InsertOneResult result = collection.insertOne(document);
            BsonValue insertedId = result.getInsertedId();
            if (insertedId != null) {
                String id = insertedId.asObjectId().getValue().toString();
                notebook.setId(id);
            }
            return notebook;
        }
    }

    @Override
    public Notebook update(Notebook notebook) {
        try (MongoClient mongoClient = MongoClients.create(MONGO_DB_URL)) {
            MongoCollection<Document> collection = getCollection(mongoClient);
            Document document = buildDocument(notebook);
            document.append(NOTEBOOK_ID, new ObjectId(notebook.getId()));
            collection.replaceOne(Filters.eq(NOTEBOOK_ID, new ObjectId(notebook.getId())), document);
            return notebook;
        }
    }

    @Override
    public Notebook delete(String id) {
        try (MongoClient mongoClient = MongoClients.create(MONGO_DB_URL)) {
            MongoCollection<Document> collection = getCollection(mongoClient);
            Notebook deletedNotebook = findById(id);
            collection.deleteOne(Filters.eq(NOTEBOOK_ID, new ObjectId(id)));
            return deletedNotebook;
        }
    }

    private MongoCollection<Document> getCollection(MongoClient mongoClient) {
        MongoDatabase database = mongoClient.getDatabase(MONGO_DB_NAME);
        boolean collectionExists = database.listCollectionNames()
                .into(new ArrayList<>()).contains(COLLECTION_NAME);
        if (!collectionExists) {
            database.createCollection(COLLECTION_NAME);
        }
        return database.getCollection(COLLECTION_NAME);
    }

    private List<Notebook> collectResults(FindIterable<Document> cursor) {
        List<Notebook> notebooks = new ArrayList<>();
        try (MongoCursor<Document> cursorIterator = cursor.cursor()) {
            while (cursorIterator.hasNext()) {
                Notebook notebook = buildNotebook(cursorIterator.next());
                notebooks.add(notebook);
            }
        }
        return notebooks;
    }
}
