package com.example.imagedl;

import com.example.imagedl.controller.ImageController;
import com.example.imagedl.controller.XmlRiverClient;
import com.example.imagedl.model.ImageLink;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

public class MockitoTest extends AbstractTestClass {

    @Test
    public void testSerJson() throws JsonProcessingException {
        XmlRiverClient mockDataService = Mockito.mock(XmlRiverClient.class);
        List<ImageLink> testImage = new ArrayList<>();
        testImage.add(new ImageLink (1, "https://habrastorage.org/r/w1560/getpro/habr/upload_files/06a/22a/3b4/06a22a3b409f64e848e43b7a23cda9d0.png"));
        Mockito.when(mockDataService.getImages(Mockito.anyString())).thenReturn(testImage);
        assertEquals("[{\"id\":1,\"url\":\"https://habrastorage.org/r/w1560/getpro/habr/upload_files/06a/22a/3b4/06a22a3b409f64e848e43b7a23cda9d0.png\"}]", ImageController.jsonSerializer(mockDataService.getImages(Mockito.anyString())));
    }
}
