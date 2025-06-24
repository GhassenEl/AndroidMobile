package tn.esprit.myapplication.model;


import android.content.Context;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;

public class LocalHttpServer extends NanoHTTPD {

    private final Context context;

    public LocalHttpServer(Context context, int port) {
        super(port);
        this.context = context;
    }

    @Override
    public Response serve(IHTTPSession session) {
        try {
            File htmlFile = new File(context.getFilesDir(), "facture.html");
            FileInputStream fis = new FileInputStream(htmlFile);
            return newFixedLengthResponse(Response.Status.OK, "text/html", fis, htmlFile.length());
        } catch (Exception e) {
            return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "text/plain", "Erreur: " + e.getMessage());
        }
    }
}
