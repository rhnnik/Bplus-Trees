import java.util.*;
import java.lang.*;
import java.io.*;


public class BtreeNode {
			int maxdegree;
			int size;
			BtreeNode parent,previous,next;
			//PriorityQueue<KeyValuePair> pq = new PriorityQueue();
			ArrayList<KeyValuePair> al;
			ArrayList<BtreeNode> child;
						
			BtreeNode(int m,BtreeNode Node)
			{
				maxdegree = m;
				al = new ArrayList<KeyValuePair>();
				child = new ArrayList<BtreeNode>();
				parent= Node;
			}
			
			
			
			public void add(float Key,String Value,BtreeNode Node)
			{
				if(Node.child.isEmpty())
				{
						//System.out.println("Key Value is = " + Key);
						KeyValuePair kvp = new KeyValuePair(Key,Value);
						Node.al.add(kvp);
				
						Collections.sort(Node.al,new Comparator<KeyValuePair>(){
						public int compare(KeyValuePair k1,KeyValuePair k2)
						{
							if(k1.key > k2.key) return 1;
							if(k1.key < k2.key) return -1;
							return 0;
						}
						});
						
						if(Node.al.size()==Node.maxdegree)
						{ 
							split(Node);
						}
					
				}
				else
				{
				
					int i=0;
					while(i<Node.al.size())
					{
						KeyValuePair k1 = Node.al.get(i);
						if(Key < k1.key) break;
						i++;
					}
					add(Key,Value,Node.child.get(i));
					
				}
				
			}
			public static void split(BtreeNode Node)
			{
				//Case when Parent is null and child is empty: i.e. root is leafnode
				if(Node.parent==null && Node.child.isEmpty())
				{
					
					BtreeNode[] temp = cutNode(Node);
					Node.child.add(0,temp[0]);
					Node.child.add(1,temp[1]);
					Node.child.get(0).next = Node.child.get(1);
					Node.child.get(0).previous = null;
					Node.child.get(1).next = null;
					Node.child.get(1).previous = Node.child.get(0); 
				}
				else if(Node.parent==null && !Node.child.isEmpty())
				{
					BtreeNode[] temp = cutNode2(Node);
					Node.child.add(temp[0]);
					temp[0].parent = Node;
					temp[1].parent = Node;
					Node.child.add(temp[1]);
				}
				else if(Node.parent!=null && !Node.child.isEmpty())
				{
					BtreeNode Parent = cutNode3(Node);
					Parent.child.remove(Node);
					if(Parent.al.size()==Parent.maxdegree)
					{
						split(Parent);
					}
				}
				else
				{
					BtreeNode Parent = cutNode4(Node);
					Parent.child.remove(Node);
					if(Parent.al.size() == Parent.maxdegree)
					{
						split(Parent);
					}
				}
			}
			public static void add1(float Key,String Value,BtreeNode child,BtreeNode parent)
			{
				//to setup links for next and previous nodes after every insertion
				
				KeyValuePair kvp = new KeyValuePair(Key,Value);
				
				parent.al.add(kvp);
				int index = parent.al.indexOf(kvp);
				BtreeNode[] temp = cutNode1(child,kvp);
				BtreeNode previous = child.previous;
				previous.next = temp[0];
				temp[0].previous = previous;
				temp[0].next = temp[1];
				temp[1].previous = temp[0];
				temp[1].next = null;
				parent.child.set(index,temp[0]);
				parent.child.add(temp[1]);
				
			}
			
			public static BtreeNode[] cutNode(BtreeNode Node)
			{
				//split when root is full at initialization
				int MidIndex = Node.al.size()/2;
				int length = Node.al.size();
				BtreeNode temp1 = new BtreeNode(Node.maxdegree,Node);
				int i=0;
				while(i<MidIndex)
				{
					KeyValuePair alo = Node.al.remove(0);
					
					temp1.add(alo.key,alo.value,temp1);
					//System.out.println(alo.key);
					i++;
				}
				BtreeNode temp2 = new BtreeNode(Node.maxdegree,Node);
				while(i<length)
				{
					KeyValuePair alo;
					if(i==MidIndex) alo = Node.al.get(0);
					else alo = Node.al.remove(1);
					//System.out.println(alo.key);
					temp2.add(alo.key,alo.value,temp2);
					i++;
				}
				
				BtreeNode[] temp = new BtreeNode[2];
				temp[0] = temp1;
				temp[1] = temp2;
				
				return temp;
			}
			public static BtreeNode[] cutNode1(BtreeNode child,KeyValuePair kvp)
			{
				
				BtreeNode previous = child.previous;
				KeyValuePair kvp1 = child.al.get(2);
				int index = child.al.indexOf(kvp1);
				int length = child.al.size();
				BtreeNode temp1 = new BtreeNode(child.maxdegree,child.parent);
				BtreeNode temp2 = new BtreeNode(child.maxdegree,child.parent);
				int i=0;
				
				while(i<index)
				{
					KeyValuePair alo = child.al.remove(0);
					
					temp1.add(alo.key,alo.value,temp1);
					//System.out.println(alo.key);
					i++;
				}
				while(i<length)
				{
					KeyValuePair alo = child.al.remove(0);
					//System.out.println(alo.key);
					temp2.add(alo.key,alo.value,temp2);
					i++;
				}
				
				BtreeNode[] temp = new BtreeNode[2];
				temp[0] = temp1;
				temp[1] = temp2;
				
				return temp;
			
			}
			public static BtreeNode[] cutNode2(BtreeNode Node)
			{
				//split when node has no parent but atleast one child
				
				int MidIndex = Node.al.size()/2;
				
				int length = Node.al.size();
				BtreeNode temp1 = new BtreeNode(Node.maxdegree,Node);
				int i=0,j=0;
				while(i<MidIndex)
				{
					KeyValuePair alo = Node.al.remove(0);
					temp1.add(alo.key,alo.value,temp1);
										//System.out.println(alo.key);
					i++;
				}
				while(j<=MidIndex)
				{
					BtreeNode ChildNode = Node.child.get(0);
					temp1.child.add(ChildNode);
					ChildNode.parent = temp1;
					Node.child.remove(0);
					j++;
				}
				
				BtreeNode temp2 = new BtreeNode(Node.maxdegree,Node);
				while(Node.al.size()!=1)
				{
					KeyValuePair alo = Node.al.remove(1);
					//System.out.println(alo.key);
					temp2.add(alo.key,alo.value,temp2);
					i++;
				}
				while(j<=length)
				{
					BtreeNode ChildNode = Node.child.get(0);
					temp2.child.add(ChildNode);
					ChildNode.parent= temp2;
					Node.child.remove(0);
					j++;
				}
				
				
				BtreeNode[] temp = new BtreeNode[2];
				temp[0] = temp1;
				temp[1] = temp2;
				
				return temp;
				
			}
			public static BtreeNode cutNode3(BtreeNode Node)
			{	
				//Split when node has a parent and atleast one child
				BtreeNode Parent = Node.parent;
				int index = Parent.child.indexOf(Node);
				
				int MidIndex = Node.al.size()/2;
				
				int length = Node.al.size();
				
				BtreeNode temp1 = new BtreeNode(Node.maxdegree,Node.parent);
				Node.parent.child.add(index,temp1);
				int i=0,j=0;
				while(i<MidIndex)
				{
					KeyValuePair alo = Node.al.remove(0);
					temp1.add(alo.key,alo.value,temp1);
								
					i++;
				}
				while(j<=MidIndex)
				{
					BtreeNode ChildNode = Node.child.get(0);
					temp1.child.add(ChildNode);
					ChildNode.parent = temp1;
					Node.child.remove(0);
					j++;
				}
				KeyValuePair alo1 = Node.al.remove(0);
				Node.parent.al.add(index,alo1);
				BtreeNode temp2 = new BtreeNode(Node.maxdegree,Node.parent);
				
				try
				{
					Node.parent.child.add(index+1,temp2);
				}catch(Exception e){ Node.parent.child.add(temp2);}
				
				while(Node.al.size()!=0)
				{
					KeyValuePair alo = Node.al.remove(0);
					//System.out.println(alo.key);
					temp2.add(alo.key,alo.value,temp2);
					i++;
				}
				while(j<=length)
				{
					BtreeNode ChildNode = Node.child.get(0);
					temp2.child.add(ChildNode);
					ChildNode.parent= temp2;
					Node.child.remove(0);
					j++;
				}
				
				return Parent;
			}
			public static BtreeNode cutNode4(BtreeNode Node)
			{
				//System.out.println("CutNode4" + Node.al.get(1).key);
				BtreeNode parent = Node.parent;
				BtreeNode previous = Node.previous;
				BtreeNode next = Node.next;
				
				int index = parent.child.indexOf(Node);
				
				int MidIndex = Node.al.size()/2;
				
				int length = Node.al.size();
				
				BtreeNode temp1 = new BtreeNode(Node.maxdegree,Node.parent);
				parent.child.add(index,temp1);
				
				int i=0;
				while(i<MidIndex)
				{
					KeyValuePair alo = Node.al.remove(0);
					temp1.add(alo.key,alo.value,temp1);
					//System.out.println(alo.key);
					i++;
				}
				parent.al.add(index,Node.al.get(0));
				BtreeNode temp2 = new BtreeNode(Node.maxdegree,Node.parent);
				try{
					parent.child.add(index+1,temp2);
				}catch(Exception e){ parent.child.add(temp2);}
				
				while(Node.al.size()!=0)
				{
					KeyValuePair alo = Node.al.remove(0);
					//System.out.println(alo.key);
					temp2.add(alo.key,alo.value,temp2);
					i++;
				}
				
				if(previous!=null) previous.next= temp1;
				temp1.previous = previous;
				temp1.next = temp2;
				temp2.previous = temp1;
				temp2.next= next;
				if(next!=null) next.previous=temp2;
				//System.out.println("Yoo" + next.al.get(0).key);
				return parent;
			}
			
			
			
			public static void find(BtreeNode Node,float Key) throws IOException
			{
				
				
				System.out.println("In Find" + Key);
				BufferedWriter out1 = new BufferedWriter(new FileWriter("C:\\Users\\ROHAN\\workspace\\Filexxx.txt", true));
				while(!Node.child.isEmpty())
				{
					ArrayList<KeyValuePair> al = Node.al;
					int i=0,lastIndex=0;
					KeyValuePair kvp = al.get(0);
					while(i<al.size() && Key >= al.get(i).key)
					{
						i++;
					}
					Node = Node.child.get(i);
						
					
				}
				//We get a Leaf Node that contains the Key
				
				ArrayList<KeyValuePair> al1= Node.al;
				int i=0;
				boolean flag = true;
				
				while(i<al1.size())
				{
						//System.out.println("Key = " + al1.get(i).key + "," + "Value = " + al1.get(i).value);
						//out1.write(al1.get(i).value + "\n");
						out1.write(al1.get(i).value);
						i++;
				
				}
				//if(flag) System.out.println("Key Not Found");
				//out1.close();
				
				
					
				out1.close();	
				
				
				
			}
			
			public static BtreeNode findNode(BtreeNode Node,float Key) 
			{
				
				//BufferedWriter out = new BufferedWriter(new FileWriter("output_file1.txt"));
				
				while(!Node.child.isEmpty())
				{
					ArrayList<KeyValuePair> al = Node.al;
					int i=0,lastIndex=0;
					KeyValuePair kvp = al.get(0);
					while(i<al.size() && Key >= al.get(i).key)
					{
						i++;
					}
					Node = Node.child.get(i);
					
				}
				//out.close();
				return Node;
			}
			
			public static void findRange(BtreeNode Node,float Key1,float Key2) throws IOException 
			{
				//System.out.println("In FInd Range = " + Key1 + "=" + Key2);
				BtreeNode First = findNode(Node,Key1);   //first Node with the Key
				BtreeNode Second = findNode(Node,Key2);  //second Node with the Key
				
				BufferedWriter out1 = new BufferedWriter(new FileWriter("output_file.txt", true));
				//out1.write("yoooooo");	
				//out1.close();
				
				if(First.previous!=null) First = First.previous;
				if(Second.next!=null) Second = Second.next;
				int flag = 0;
				
				while(First != Second.next)
				{
					ArrayList<KeyValuePair> al1= First.al;
					int i=0;
					
					
					while(i<al1.size())
					{
						//System.out.println(al1.get(i).key);
						if(al1.get(i).key >= Key1 && al1.get(i).key <= Key2)
						{
							//System.out.println("SKey = " + al1.get(i).key + "," + "Value = " + al1.get(i).value);
							flag=1;
							if(treesearch.isrange==true){
								out1.write("(" + al1.get(i).key + "," + al1.get(i).value + ")");
							}
							else {out1.write(al1.get(i).value);}
							
							//
						}
						i++;
					}
					if(flag==0)
					{
						out1.write("Null");
						//System.out.println("Null");
					}
					out1.newLine();
					First = First.next;
				}
				
				out1.close();
				
			}
			
}
