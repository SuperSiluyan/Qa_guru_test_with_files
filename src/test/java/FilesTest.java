import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FilesTest {

    ClassLoader cl = FilesTest.class.getClassLoader();

    @Test
    void parsingPdfFromZipFile() throws Exception {
        try (InputStream stream = cl.getResourceAsStream("files.zip");
             ZipInputStream zis = new ZipInputStream(stream)) {

            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                String name = entry.getName();
                if (name.contains(".pdf")) {
                    PDF pdf = new PDF(zis);
                    Assertions.assertEquals("Chromium", pdf.creator);
                }
            }
        }
    }

    @Test
    void parsingCsvFromZipFile() throws Exception {
        try (InputStream stream = cl.getResourceAsStream("files.zip");
             ZipInputStream zis = new ZipInputStream(stream)) {

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String name = entry.getName();
                if (name.contains(".csv")) {
                    CSVReader csvReader = new CSVReader(new InputStreamReader(zis));
                    List<String[]> content = csvReader.readAll();
                    Assertions.assertEquals(3, content.size());
                    final String[] firstRow = content.get(0);
                    Assertions.assertArrayEquals(new String[]{"batman", " uses technology"}, firstRow);
                }
            }
        }
    }

    @Test
    void parsingXlsFromZipFile() throws Exception {
        try (InputStream stream = cl.getResourceAsStream("files.zip");
             ZipInputStream zis = new ZipInputStream(stream)) {

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String name = entry.getName();
                if (name.contains(".xlsx")) {
                    XLS xls = new XLS(zis);
                    Assertions.assertEquals("Cайт", xls.excel.getSheetAt(0).getRow(0).getCell(0).getStringCellValue());
                }
            }
        }
    }
}