package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;

public class StaticServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();

        if (uri.contains("..")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String[] parts = uri.split("\\+");
        File[] files = new File[parts.length];

        for (int i = 0; i < files.length; i++) {
            if (parts[i].isEmpty()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            files[i] = findFile(parts[i]);
            if (files[i] == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }

        String mime = getServletContext().getMimeType(files[0].getName());
        if (mime != null) {
            response.setContentType(mime);
        }

        try (OutputStream outputStream = response.getOutputStream()) {
            for  (File file : files) {
                Files.copy(file.toPath(), outputStream);
            }
        }
    }

    private File findFile(String uri) {
        File file = findInSrcDir(uri);
        if (file == null || !file.exists()) {
            file = new File(getServletContext().getRealPath("/static" + uri));
        }

        if (!file.exists() || !file.isFile()) {
            return null;
        }

        return file;
    }

    private File findInSrcDir(String uri) {
        File root = getProjectRoot();
        if (root == null || !root.exists() || !root.isDirectory()) {
            return null;
        }
        File dir = new File(root, "/src/main/webapp/static");
        if (!dir.isDirectory()) {
            return null;
        }

        File file = new File(dir, uri);
        try {
            String canonicalTarget = file.getCanonicalPath();
            String canonicalBase = dir.getCanonicalPath();
            if (canonicalTarget.startsWith(canonicalBase) && file.isFile()) {
                return file;
            }
        } catch (final IOException ignored) {
            // ignored
        }

        return null;
    }

    private File getProjectRoot() {
        try {
            File dir = new File(StaticServlet.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI());

            if (dir.isFile()) {
                dir = dir.getParentFile();
            }

            while (dir != null) {
                File src = new File(dir, "src/main/webapp");
                if (src.isDirectory()) {
                    return dir;
                }
                dir = dir.getParentFile();
            }
        } catch (final URISyntaxException ignored) {
            // ignored
        }
        return null;
    }
}
