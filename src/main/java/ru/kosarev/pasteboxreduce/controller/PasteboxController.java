package ru.kosarev.pasteboxreduce.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kosarev.pasteboxreduce.api.request.PasteboxRequest;
import ru.kosarev.pasteboxreduce.api.response.PasteboxResponse;
import ru.kosarev.pasteboxreduce.api.response.PasteboxUrlResponse;
import ru.kosarev.pasteboxreduce.service.PasteboxService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class PasteboxController {
    private final PasteboxService pasteboxService;

    @GetMapping("/")
    public Collection<PasteboxResponse> getPublicPasteList() {
        return pasteboxService.getFirstPublicPasteboxes();
    }

    @GetMapping("/{hash}")
    public PasteboxResponse getByHash(@PathVariable String hash) {
        return pasteboxService.getByHash(hash);
    }

    @PostMapping("/")
    public PasteboxUrlResponse add(@RequestBody PasteboxRequest request){
        return pasteboxService.create(request);
    }
}
