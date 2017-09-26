import java.io.Serializable;

/**
 * Created by koosh on 21/5/17.
 */
public class beanClass implements Serializable {
    private String docno;
    private String text;
    private int docLen;

    public beanClass(){}

    public void setID(String id){
        this.docno= id;
    }
    public String getID(){
        return this.docno;
    }


    public void setText(String text){
        this.text = text;
    }
    public String getText(){
        return this.text;
    }

    public void setLen(int len){
        this.docLen = len;
    }
    public int getLen(){
        return this.docLen;
    }
}

