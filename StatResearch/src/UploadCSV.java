import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class UploadCSV {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        System.out.println("**********************************Welcome*********************************");
        System.out.println("Text to CSV conversion");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the file path: ");
        String path = scanner.nextLine();
        Path new_path = Paths.get(path);
        System.out.println("File path: "+new_path.toString());

        System.out.println("Enter the output file name: ");
        String output_filename = scanner.nextLine();
        if(!(new File("./"+output_filename+".csv").exists())) {
            Read read = new Read(new_path.toString());
            Print print = new Print();

            try {
                print.setFileName(output_filename + ".csv");
                read.setPrint(print);
                read.exec(1);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("Finished");
        }else{
            System.out.println("File with "+output_filename+" already exists.");
        }
    }
}

class Print {

    protected String fileName;

    protected FileWriter writer;

    public Print() {
    }

    public Print(String fileName) throws IOException {
        this.fileName = fileName;
        this.writer = new FileWriter(this.fileName);
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) throws IOException {
        this.fileName = fileName;
        this.writer = new FileWriter(this.fileName);
    }

    public void close() throws IOException {
        this.writer.flush();
        this.writer.close();
    }

    public void addRow(String[] c) throws IOException {
        int l = c.length;
        for (int i = 0; i < l; i++) {
            this.writer.append(c[i]);
            if (i != (l - 1)) {
                this.writer.append(",");
            }
        }
        this.writer.append('\n');
    }
}

class Read {

    protected String fileName;

    protected BufferedReader bufferedReader;

    protected Print print;

    public Read(String fileName) {
        this.fileName = fileName;
    }

    public Read(Print print, String fileName) {
        this.print = print;
        this.fileName = fileName;
    }

    public void setPrint(Print print) {
        this.print = print;
    }

    public Print getPrint() {
        return this.print;
    }

    public void exec(Integer type) {
        String sCurrentLine = "";
        try {
            this.bufferedReader = new BufferedReader(
                    new FileReader(this.fileName));

            while ((sCurrentLine = this.bufferedReader.readLine()) != null) {
                String[] columns = sCurrentLine.split("\\s+");
                int length = columns.length;
                if(length > 7){
                    columns= sCurrentLine.split(":");
                }
                if (type == 1) {
                    this.print.addRow(columns);
                }
            }
            this.print.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (this.bufferedReader != null) {
                    this.bufferedReader.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
