package zenika.cfm;

import io.vertx.core.Vertx;
import zenika.cfm.verticle.HttpServerVerticle;

public class Application {

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new HttpServerVerticle());
    }
}
