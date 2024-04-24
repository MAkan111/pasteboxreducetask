package ru.kosarev.pasteboxreduce.service;

import ru.kosarev.pasteboxreduce.api.request.PasteboxRequest;
import ru.kosarev.pasteboxreduce.api.response.PasteboxResponse;
import ru.kosarev.pasteboxreduce.api.response.PasteboxUrlResponse;

import java.util.List;

public interface PasteboxService {
    PasteboxResponse getByHash(String hash);
    List<PasteboxResponse> getFirstPublicPasteboxes();
    PasteboxUrlResponse create(PasteboxRequest request);
}
