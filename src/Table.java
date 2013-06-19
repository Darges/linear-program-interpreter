
public class Table {
    String id;
    int  value;
    Table tail;    
    Table(String i,int v, Table t)
    {   
    	 id = i;
    	 value = v;
    	 tail = t;
    }
    
    public static Table update(Table t, String key, int value)
    {
	  	Table t1 = new Table(key, value, t);  	  
	  	return t1;  
    }
    
    public static int lookup(Table t, String key)
    {
    	while((t!=null) && (t.id != key))
    	{
    		t = t.tail;
    	}
    	if(t!=null)
    	{
    		return t.value;
    	}
    	else
    	{
    		System.out.printf("Can't find the key %s.\n", key);
    		return 0;
    	} 	
    }
    
}
