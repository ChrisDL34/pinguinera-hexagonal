package com.penguin.model.bookStoreQuotes.factory;

import com.penguin.model.bookStoreQuotes.entity.Copy;
import com.penguin.model.bookStoreQuotes.values.copy.*;

public interface CopyFactory {
    Copy createCopy(
            String type,
            Title title,
            Author author,
            Stock stock,
            PublicationYear publicationYear,
            Price price
    );
}
