package zenika.cfm;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.impl.Utils;
import io.vertx.ext.web.FileUpload;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static io.vertx.core.http.HttpMethod.POST;

public class HttpServerVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        Router router = Router.router(vertx);

        router.route().handler(
            BodyHandler.create()
            .setUploadsDirectory("/tmp")
        );
        router.route("/upload")
            .method(POST)
            .blockingHandler(event -> {
                for (FileUpload f : event.fileUploads()) {
                    // do whatever you need to do with the file (it is already saved
                    // on the directory you wanted...
                    System.out.println("Filename: " + f.fileName());
                    System.out.println("Size: " + f.size());

                    ZipFile zipFile = null;
                    try {
                        zipFile = new ZipFile(f.uploadedFileName());
                        Enumeration<? extends ZipEntry> entries = zipFile.entries();

                        while(entries.hasMoreElements()){
                            ZipEntry entry = entries.nextElement();
                            if (entry.getName().equals("project.json")) {
                                Parser parser = new Parser();
                                InputStream stream = zipFile.getInputStream(entry);
                                byte[] data = new byte[1024];

                                Buffer buffer = Buffer.buffer();
                                while (((stream.read(data, 0, data.length))) != -1) {
                                    buffer.appendBytes(data);
                                }

                                parser.readScratchProject(buffer);
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                event.response().end();
            });

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8888, res -> {
                if (res.succeeded()) {
                    startFuture.complete();
                } else {
                    startFuture.fail(res.cause());
                }
            });
    }
}
