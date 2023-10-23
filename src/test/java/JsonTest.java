import models.ModelJsonNew;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;

public class JsonTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void fileToListOfPojos() throws Exception {
        File file = new File("src/test/resources/person.json");
        ModelJsonNew person = objectMapper.readValue(file, ModelJsonNew.class);

        Assertions.assertEquals("privet", person.getMessage());
        Assertions.assertTrue(person.isSuccess());
        Assertions.assertEquals("Кристофер МакКуорри", person.getPerson().getName());
    }
}


