package ru.kosarev.pasteboxreduce;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.kosarev.pasteboxreduce.api.response.PasteboxResponse;
import ru.kosarev.pasteboxreduce.exception.EntityNotFoundException;
import ru.kosarev.pasteboxreduce.repository.PasteboxEntity;
import ru.kosarev.pasteboxreduce.repository.PasteboxRepository;
import ru.kosarev.pasteboxreduce.service.PasteboxService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PasteboxServiceTest {
    @Autowired
    private PasteboxService pasteboxService;

    @MockBean
    private PasteboxRepository pasteboxRepository;

    @Test
    public void notExistHash(){
        when(pasteboxRepository.getByHash(anyString())).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> pasteboxService.getByHash("testpasteboxreduce"));
    }

    @Test
    public void getExistHash(){
        PasteboxEntity entity = new PasteboxEntity();
        entity.setHash("test12");
        entity.setData("testtesttesttesttest");
        entity.setPublic(true);

        when(pasteboxRepository.getByHash("test12")).thenReturn(entity);

        PasteboxResponse expected = new PasteboxResponse("testtesttesttesttest", true);
        PasteboxResponse actual = pasteboxService.getByHash("test12");

        assertEquals(expected, actual);
    }

}
