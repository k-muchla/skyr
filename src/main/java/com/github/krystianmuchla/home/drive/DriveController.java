package com.github.krystianmuchla.home.drive;

import com.github.krystianmuchla.home.exception.http.UnauthorizedException;
import com.github.krystianmuchla.home.html.Script;
import com.github.krystianmuchla.home.html.Style;
import com.github.krystianmuchla.home.http.Controller;
import com.github.krystianmuchla.home.http.RequestReader;
import com.github.krystianmuchla.home.http.ResponseWriter;
import com.github.krystianmuchla.home.id.user.User;
import com.sun.net.httpserver.HttpExchange;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.github.krystianmuchla.home.html.Attribute.attrs;
import static com.github.krystianmuchla.home.html.Group.group;
import static com.github.krystianmuchla.home.html.Html.document;
import static com.github.krystianmuchla.home.html.Tag.div;

public class DriveController extends Controller {
    public DriveController() {
        super("/drive");
    }

    @Override
    protected void get(final HttpExchange exchange) throws IOException {
        final User user;
        try {
            user = session(exchange).user();
        } catch (final UnauthorizedException exception) {
            ResponseWriter.writeLocation(exchange, "/id/sign_in");
            ResponseWriter.write(exchange, 302);
            return;
        }
        final var filter = RequestReader.readQuery(exchange, DriveFilterRequest::new);
        final var list = DriveService.listDirectory(user.id(), filter.dir());
        ResponseWriter.writeHtml(exchange, 200, html(list));
    }

    private String html(final List<File> list) {
        return document(
            List.of(
                Style.BACKGROUND,
                Style.LEFT_TOP
            ),
            List.of(Script.DRIVE),
            List.of(),
            div(attrs("class", "background"),
                div(attrs("id", "list", "class", "left-top list"),
                    group(list.stream().map(row -> {
                        final var clazz = row.isDirectory() ? "dir" : "file";
                        return div(attrs("class", clazz), row.getName());
                    }))
                )
            )
        );
    }
}
