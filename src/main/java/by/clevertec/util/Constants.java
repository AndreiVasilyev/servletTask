package by.clevertec.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {
    public static final String MONGO_DB_URL = "mongodb://localhost:27017";
    public static final String MONGO_DB_NAME = "notebook-db";
    public static final String COLLECTION_NAME = "notebooks";
    public static final String NOTEBOOK_ID = "_id";
    public static final String NOTEBOOK_MODEL = "notebook_model";
    public static final String NOTEBOOK_VENDOR_NAME = "notebook_vendor_name";
    public static final String NOTEBOOK_DESCRIPTION = "notebook_description";
    public static final String NOTEBOOK_QUANTITY = "notebook_quantity";
    public static final String NOTEBOOK_PRICE = "notebook_price";
}
