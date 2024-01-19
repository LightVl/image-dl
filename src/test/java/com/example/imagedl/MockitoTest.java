package com.example.imagedl;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MockitoTest extends AbstractTestClass {

    @Test
    public void testSerJson() throws JsonProcessingException {
        XmlRiverClient mockDataService = Mockito.mock(XmlRiverClient.class);
        List<ImageLink> testImage = new ArrayList<>();
        testImage.add(new ImageLink (1, "https://habrastorage.org/r/w1560/getpro/habr/upload_files/06a/22a/3b4/06a22a3b409f64e848e43b7a23cda9d0.png"));
        Mockito.when(mockDataService.getImages(Mockito.anyString())).thenReturn(testImage);
        List<ImageLink> Images = mockDataService.getImages(Mockito.anyString());
        //TODO: каким-то образом также проверить imagecontroller
        assertEquals(ImageController.jsonSerializer(Images),"[{\"id\":1,\"url\":\"https://habrastorage.org/r/w1560/getpro/habr/upload_files/06a/22a/3b4/06a22a3b409f64e848e43b7a23cda9d0.png\"}]");
    }

//    @Test
//    public void testException() throws IOException {
//        ImageController mockDataService = Mockito.mock(ImageController.class);
//        Mockito.when(mockDataService.getImage(Mockito.anyString(), Mockito.anyInt())).thenThrow(new IOException());
//        String error = mockDataService.getImage(Mockito.anyString(), Mockito.anyInt());
//        System.out.print(error);
//        assertEquals(1,1);
//    }
}
