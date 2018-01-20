import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;



public class treesearch {
	
	static boolean isrange = false;
	public static void main(String[] args) throws NumberFormatException, IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(args[0]));		
		int m = Integer.parseInt(br.readLine());
		BtreeNode root = Initialize(m);
		String line = "";
		int count = 0;
		
		float minValue = Integer.MAX_VALUE;
		float maxValue = Integer.MIN_VALUE;
		while((line=br.readLine())!=null)
		{
			
			if(line.contains("Insert"))
			{
				String[] line1 = line.split(",");
				//System.out.println(line1[0]);
				String line2 = line1[0].substring(7,line1[0].length());
				String key = line2;
				String value = line1[1].substring(0,line1[1].length()-1);
				//System.out.println("Key Value is = " + key + "Value is = " + value);
				Insert(root,key,value);
				float val1 = Float.parseFloat(key);
				float val2 = Float.parseFloat(key);
				if(val1 >= maxValue) maxValue = val1;
				if(val2 <= minValue) minValue = val2;
				count++;
			}
			else if(line.contains("Search") && !line.contains(","))
			{
				isrange = false;
				String key = line.substring(7,line.length()-1);
				Search(root,key,key);
				
				
			}
			else if(line.contains("Search") && line.contains(","))
			{
				isrange = true;
				String[] line1 = line.split(",");
				String key1 = line1[0].substring(7,line1[0].length());
				String key2 = line1[1].substring(0,line1[1].length()-1);
				Search(root,key1,key2);
				
				
				//System.out.print("isrange");
			}
			
		}
		//System.out.println(count);
		//System.out.println(minValue);
		//System.out.println(maxValue);
		//Search(root,"-271.28","-271.28");
		//Search(root,Float.toString(minValue),Float.toString(maxValue));
		//String temp = br.readLine();
		/*if(temp.contains("Insert"))
		{
			String[] temp1 = temp.split(",");
			String[] temp2 = temp1[0].split("(");
			String key = temp2[1];
			String value = temp1[1].substring(0,temp1[1].length()-2);
			
		}*/
		/*Insert(root,"1.0","Value1");
		//System.out.println("1");
		Insert(root,"2.0","Value10");
		//System.out.println("2");
		Insert(root,"3.0","Value2");
		//System.out.println("3");
		Insert(root,"4.0","Value2");
		Insert(root,"4.0","Value3");
		//System.out.println("4");
		Insert(root,"5.0","Value3");
		//System.out.println("5");
		Insert(root,"6.0","Value4");
		Insert(root,"7.0","Value5");
		Insert(root,"8.0","Value6");
		Insert(root,"9.0","Value7");
		Insert(root,"10.0","Value11");
		Insert(root,"11.0","Value11");
		Insert(root,"12.0","Value12");
		Insert(root,"13.0","Value13");
		Insert(root,"14.0","Value14");
		Insert(root,"15.0","Value15");
		Insert(root,"16.0","Value16");
		Insert(root,"17.0","Value17");
		Insert(root,"18.0","Value18");
		Insert(root,"19.0","Value19");
		//Insert(root,"37.56","Value2");
		//System.out.println("Search for Key = 4.0");
		//Search(root,"4.0","5.0");
		
		System.out.println("Search Range for Key between 1 to 4");
		Search(root,"1.0","19.0");
		 */
	//	System.out.println(root.child.get(2).al.get(2).key);
	br.close();
	}
	
	public static BtreeNode Initialize(int m)
	{
		return(new BtreeNode(m,null));
	}
	public static void Insert(BtreeNode root, String key1, String Value)
	{
		float Key = Float.parseFloat(key1);
		root.add(Key,Value,root);
		 
	}
	public static void Search(BtreeNode root,String key) throws IOException 
	{
		float Key = Float.parseFloat(key);
		BtreeNode.find(root,Key);
		
		
	}
	public static void Search(BtreeNode root,String key1,String key2) throws IOException
	{
		float Key1= Float.parseFloat(key1);
		float Key2 = Float.parseFloat(key2);
		BtreeNode.findRange(root,Key1,Key2);
	}
}
