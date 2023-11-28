package expert.os.harperdb;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ServerTest {

    private final Container container = Container.INSTANCE;

    @Test
    void shouldGetHost() {
        Server server = getServer();

        Assertions.assertNotNull(server);
    }

    @Test
    void shouldCreateSchema() {
        Server server = getServer();

        boolean schema = server.createSchema("test");
        Assertions.assertTrue(schema);
    }

    @Test
    void shouldNotCreateSchemaDuplicated() {
        Server server = getServer();

        boolean schema = server.createSchema("duplicated");
        Assertions.assertTrue(schema);
        Assertions.assertFalse(server.createSchema("duplicated"));
    }

    @Test
    void shouldReturnNPEWhenSchemaIsNull() {
        Server server = getServer();

        Assertions.assertThrows(NullPointerException.class, () -> server.createTable(null));
        Assertions.assertThrows(NullPointerException.class, () -> server.createTable("schema").table(null));
        Assertions.assertThrows(NullPointerException.class, () -> server.createTable("schema").table("table").id(null));
    }

    @Test
    void shouldReturnNPEWhenCreateDatabaseIsNull() {
        Server server = getServer();

        Assertions.assertThrows(NullPointerException.class, () -> server.createDatabase(null));
    }

    @Test
    void shouldCreateDatabase() {
        Server server = getServer();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(server.createDatabase("database")).isTrue();
            softly.assertThat(server.createDatabase("database")).isFalse();
        });
    }

    @Test
    void shouldCreateTable() {
        Server server = getServer();

        server.createSchema("test");
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(server.createTable("test").table("table").id("id").execute()).isTrue();
            softly.assertThat(server.createTable("test").table("table").id("id").execute()).isFalse();
            softly.assertThat(server.createTable("test").table("table").id("id").execute("databaseA")).isTrue();
            softly.assertThat(server.createTable("test").table("table").id("id").execute("databaseA")).isFalse();
        });
    }

    @Test
    void shouldReturnNPEWhenTemplateIsNull() {
        Server server = getServer();

        Assertions.assertThrows(NullPointerException.class, () -> server.template(null));
    }

    @Test
    void shouldCreateTemplate() {
        Server server = getServer();

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(server.template("test")).isNotNull();
        });
    }

    private Server getServer() {
        return ServerBuilder.of(container.host())
                .withCredentials(container.user(), container.password());
    }

}