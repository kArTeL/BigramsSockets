import java.io.Serializable;

public class MBigram implements Serializable
{
  private String bigram;
  private int count;

  public String getBigram()
  {
    return this.bigram;
  }
  public int getCount()
  {
    return this.count;
  }

  public void setBigram(String bigram)
  {
    this.bigram = bigram;
  }
  public void setCount(int count)
  {
    this.count = count;
  }

  public MBigram(String bigram, int count)
  {
    this.bigram = bigram;
    this.count  = count;
  }
}
