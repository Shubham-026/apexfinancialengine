    package storage;
    import java.io.FileWriter;
    import java.io.IOException;

    public class CSVHandler {

        public boolean resetFile(){
            try {
                FileWriter writer = new FileWriter("src/dataset/dataset.csv");
                writer.write("id,type,amount,category,description,timestamp\n");
                writer.close();
                return true;
            } catch (IOException e) {   
                System.out.println("Unable to Clear the file");
                System.out.println("Error: " + e.getMessage());
                System.out.println("Some error occured!!!");
                return false;
            }
        }
    }
        
