package ru.kosarev.pasteboxreduce.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import ru.kosarev.pasteboxreduce.api.request.PasteboxRequest;
import ru.kosarev.pasteboxreduce.api.request.PublicStatus;
import ru.kosarev.pasteboxreduce.api.response.PasteboxResponse;
import ru.kosarev.pasteboxreduce.api.response.PasteboxUrlResponse;
import ru.kosarev.pasteboxreduce.repository.PasteboxEntity;
import ru.kosarev.pasteboxreduce.repository.PasteboxRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Setter
@ConfigurationProperties(prefix = "app")
public class PasteboxServiceImpl implements PasteboxService {

    private String host;
    private int publicListSize;

    private final PasteboxRepository repository;
    private AtomicInteger idGenerator = new AtomicInteger(0);

    @Override
    public PasteboxResponse getByHash(String hash) {
        PasteboxEntity pasteBoxEntity = repository.getByHash(hash);
        return new PasteboxResponse(pasteBoxEntity.getData(), pasteBoxEntity.isPublic());
    }

    @Override
    public List<PasteboxResponse> getFirstPublicPasteboxes() {

        List<PasteboxEntity> list = repository.getListOfPublicAndAlive(publicListSize);

        return list.stream().map(pasteboxEntity ->
                        new PasteboxResponse(pasteboxEntity.getData(), pasteboxEntity.isPublic()))
                .collect(Collectors.toList());
    }

    @Override
    public PasteboxUrlResponse create(PasteboxRequest request) {

        int hash = generateId();
        PasteboxEntity pasteBoxEntity = new PasteboxEntity();
        pasteBoxEntity.setData(request.getData());
        pasteBoxEntity.setId(hash);
        pasteBoxEntity.setHash(Integer.toHexString(hash));
        pasteBoxEntity.setPublic(request.getPublicStatus() == PublicStatus.PUBLIC);
        pasteBoxEntity.setLifetime(LocalDateTime.now().plusSeconds(request.getExpirationTimeSeconds()));
        repository.add(pasteBoxEntity);

        return new PasteboxUrlResponse(host + "/" + pasteBoxEntity.getHash());
    }

    private int generateId() {
        return idGenerator.getAndIncrement();
    }
}
