package tn.esprit.myapplication.Controller;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import tn.esprit.myapplication.R;
import tn.esprit.myapplication.model.LocalHttpServer;
import tn.esprit.myapplication.model.PanierManager;
import tn.esprit.myapplication.model.Product;

public class InvoiceActivity extends AppCompatActivity {

    private TextView textDate, textProducts, textTotal;
    private String currentDate;
    private LocalHttpServer server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        textDate = findViewById(R.id.textDate);
        textProducts = findViewById(R.id.textProducts);
        textTotal = findViewById(R.id.textTotal);
        ImageView qrImage = findViewById(R.id.qrImage);

        List<Product> produits = PanierManager.getInstance().getProduits();
        StringBuilder builder = new StringBuilder();
        double total = 0;

        for (Product p : produits) {
            builder.append("- ").append(p.getName()).append(" : ").append(p.getPrice()).append(" DT\n");
            total += p.getPrice();
        }

        currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        textDate.setText("Date : " + currentDate);
        textProducts.setText(builder.toString());
        textTotal.setText("Total : " + String.format(Locale.getDefault(), "%.2f", total) + " DT");

        // ✅ 1. Génère HTML
        String html = genererHtml(produits, total);

        try {
            // ✅ 2. Sauvegarde HTML
            File htmlFile = new File(getFilesDir(), "facture.html");
            FileOutputStream fos = new FileOutputStream(htmlFile);
            fos.write(html.getBytes());
            fos.close();

            // ✅ 3. Démarre serveur local sur port 8888
            server = new LocalHttpServer(this, 8888);
            server.start();

            // ✅ 4. Détecte IP locale (évite 127.0.0.1)
            String ip = getDeviceIpAddress();
            String url = "http://" + ip + ":8888";

            // ✅ 5. QR vers page HTML
            Bitmap qrBitmap = generateQrCode(url);
            qrImage.setImageBitmap(qrBitmap);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erreur serveur HTML: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private String getDeviceIpAddress() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface netInterface = interfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (!address.isLoopbackAddress() && address.getHostAddress().contains(".")) {
                        return address.getHostAddress(); // ✅ retourne une IP du style 192.168.X.X
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "127.0.0.1"; // Fallback si aucun résultat
    }


    private String genererHtml(List<Product> produits, double total) {
        StringBuilder html = new StringBuilder();
        html.append("<html><head><meta charset='UTF-8'><style>")
                .append("body{font-family:sans-serif;padding:20px;background:#f9f9f9;}")
                .append("h1{text-align:center;color:#333;}")
                .append("table{width:100%;border-collapse:collapse;margin-top:20px;}")
                .append("th,td{padding:10px;border:1px solid #ccc;text-align:left;}")
                .append("</style></head><body>");
        html.append("<h1>Facture Professionnelle</h1>");
        html.append("<p><strong>Date:</strong> ").append(currentDate).append("</p>");
        html.append("<table><tr><th>Produit</th><th>Prix</th></tr>");
        for (Product p : produits) {
            html.append("<tr><td>").append(p.getName()).append("</td><td>")
                    .append(String.format(Locale.getDefault(), "%.2f", p.getPrice()))
                    .append(" DT</td></tr>");
        }
        html.append("</table>");
        html.append("<h2>Total TTC : ").append(String.format(Locale.getDefault(), "%.2f", total)).append(" DT</h2>");
        html.append("<p style='text-align:center;'>Merci pour votre visite ! 🍽️</p>");
        html.append("</body></html>");
        return html.toString();
    }

    private Bitmap generateQrCode(String text) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix matrix = writer.encode(text, BarcodeFormat.QR_CODE, 400, 400);
            Bitmap bitmap = Bitmap.createBitmap(400, 400, Bitmap.Config.RGB_565);
            for (int x = 0; x < 400; x++) {
                for (int y = 0; y < 400; y++) {
                    bitmap.setPixel(x, y, matrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }
}
