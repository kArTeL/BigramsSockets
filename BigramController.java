import java.util.*;
import java.io.*;

/**
 * Bigram Controller, this contain all the main operations:
 * SearchBigram
 * CreateBigramTable
 * PrintBigram
 * @author Neil
 * @version 1.0
 */
public class BigramController
{
    private Hashtable table;
    private MBigramCount[] fullResults;
    private String fileName;
    private Boolean resultAreCollected;
    private Enumeration elements, keys;

    public BigramController(String fileName)    {
        this.fileName = fileName;
        this.table = new Hashtable();
        this.resultAreCollected = false;
    }


  public  int searchBigram(String bigram)  {
    int bigramCount = 0;
    Object item = this.table.get(bigram);
    if (item != null)
        bigramCount = ((int[])item)[0];
    return bigramCount;
  }

  ///Print bigrams in console and into a file named Result.txt.
  public void printInfile(String fileName)
  {
      BufferedWriter output = null;
      try {
          File file = new File(fileName);
          output = new BufferedWriter(new FileWriter(file));
          for(int i=0; i < this.fullResults.length; i++)
          {
              String bigramString = this.fullResults[i].bigram + "-"+this.fullResults[i].count;
              output.write(bigramString + "\n");
          }

          output.close();
      }
      catch ( IOException e ) {
          e.printStackTrace();
      }

  }
  public void printBigramsInConsole() {
      if (!this.resultAreCollected)
      {
        this.collectResults();
      }
      for(int i=0; i < this.fullResults.length; i++)
      {
          String bigramString = this.fullResults[i].bigram + "-"+this.fullResults[i].count;
          System.out.println(bigramString);
      }

  }

   private void addToBigram(String bigram)  {
       Object item = this.table.get(bigram);
        if (item != null)
            ((int[])item)[0]++;
        else
        {
            int[] count = {1};
            this.table.put(bigram,count);
        }
    }


  //Read the file @FileName and create the table with all the Bigrams.
   public void createBigramTable()  {
       String bigram;
       String line = "";
       StringTokenizer tokens;
       String token1 = "";
       String token2 = "";
       Boolean isFirstLine = true;
       try {
            Scanner sc = new Scanner(new File(this.fileName));

            line = line + sc.nextLine();
            while(sc.hasNextLine())
            {

              line = LineCleaner.clean(line);
              tokens = new StringTokenizer(line);


              //  Si hay palabras en la linea
              if (tokens.hasMoreTokens())
              {

                  //Si es la primera linea y la primera palabra, crear el bigrama que empieza con I.
                  if (isFirstLine)
                  {
                      token1 = tokens.nextToken();
                      token1 = token1.toLowerCase();
                      bigram = "I" + " " + token1;

                      isFirstLine = false;
                      this.addToBigram(bigram);
                  }
              }

              //Ahora tenemos que recorrer el arreglo de palabras dentro de la oración.
              while(tokens.hasMoreTokens()) {
                  token2 = tokens.nextToken();
                  token2 = token2.toLowerCase();
                  bigram = token1 + " " + token2;

                  this.addToBigram(bigram);
                  token1 = token2; // step forward
              }
              line = sc.nextLine();

            }
            bigram = token2 + " " + "F";
            this.addToBigram(bigram);
            sc.close();
        }
        catch (FileNotFoundException ex)
        {
            System.out.println("No existe el archivo" + this.fileName+ "dentro del folder");
        }


    }


    public void collectResults() {

        this.keys = this.table.keys();
        this.elements = this.table.elements();
        int index = 0;
        this.fullResults = new MBigramCount[this.table.size()];

        while(this.keys.hasMoreElements()){
            this.elements.hasMoreElements();
            this.fullResults[index++] =
            new MBigramCount(
                              ((int[])elements.nextElement())[0],
                              (String)keys.nextElement());
        }
        Comparator comp = new Comparator(){
            public int compare(Object o1, Object o2){
                int i1 = ((MBigramCount)o1).count;
                int i2 = ((MBigramCount)o2).count;
                if(i1 == i2) return 0;
                return ((i1 > i2) ? -1 : +1);
            }
        }; // Comparator comp

        Arrays.sort(this.fullResults, comp);
        this.resultAreCollected = true;
    } // collectResults




}
