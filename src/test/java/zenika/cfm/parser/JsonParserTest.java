package zenika.cfm.parser;

import io.vertx.core.buffer.Buffer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

public class JsonParserTest {


    private Buffer content;

    @BeforeEach
    public void setUp() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream stream = classLoader.getResourceAsStream("gilded_rose.json");

        byte[] data = new byte[1024];

        content = Buffer.buffer();
        while (((stream.read(data, 0, data.length))) != -1) {
            content.appendBytes(data);
        }
    }

    @Test
    public void jsonPath() {
        JsonParser jsonParser = new JsonParser();
        jsonParser.readScratchProject(content);
    }
}