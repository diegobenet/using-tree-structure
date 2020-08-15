/**************************************************************

Autor: Diego Elizondo Benet
Fecha: 21 de Marzo 2020
Doy mi palabra que he realizado esta activvalorad con integrvalorad academica.
***************************************************************/
import java.io.*;
import java.util.*;

//clase NODO
class Nodo{ 
		String palabra; 
		Nodo izq;
      Nodo der; 
      int frec;

		public Nodo(String pal){ 
			palabra = pal;
         frec = 1;
		} 
	} 

//clase arbol ABB
class ABB { 
   PrintWriter writer;
	Nodo raiz; 
   String resultado;
   
	ABB(){ 
	   raiz = null;
      try{
         writer = new PrintWriter("resultado.txt", "UTF-8");
      }catch(Exception e){}
	} 

	// metodo para empezar agregando recursivamente 
	public void agregar(String palabra) { 
	   raiz = insercion(raiz, palabra); 
	}
	
	//metodo para agregar palabra recursivamente
	public Nodo insercion(Nodo raiz, String palabra){
      
		if(raiz == null){ 
			raiz = new Nodo(palabra); 
			return raiz; 
		} 
		if(raiz.palabra.compareTo(palabra) >= 1){
			raiz.izq = insercion(raiz.izq, palabra); 
      }else if (raiz.palabra.compareTo(palabra) <= -1){ 
			raiz.der = insercion(raiz.der, palabra); 
      }else{
         raiz.frec++;
      }
      
		return raiz; 
	} 
   
   public void del(String palabra){
      raiz = eliminar(raiz, palabra);
   }
   
   //metodo para eliminar un nodo con un valor 
   public Nodo eliminar(Nodo raiz, String valor){
      if (raiz.palabra.compareTo(valor) <= -1)
         raiz.der = eliminar(raiz.der, valor);
      else if(raiz.palabra.compareTo(valor) >= 1)
         raiz.izq = eliminar(raiz.izq, valor);
      else{
         //si es hoja
         if(raiz.izq==null && raiz.der==null){
            raiz = null;
            return null;
         }else if(raiz.izq==null || raiz.der==null){
            Nodo temp;
            if(raiz.izq==null)
                temp = raiz.der;
            else
                temp = raiz.izq;
            raiz = null;
            return temp;
         }else{
            Nodo temp = encontrarMin(raiz.der);
            raiz.palabra = temp.palabra;
            raiz.der = eliminar(raiz.der, temp.palabra);
         }
      }
      return raiz;
   }
   
   //metodo para encontrar el minimo
   public static Nodo encontrarMin(Nodo raiz){
        if(raiz==null)
            return null;
        else if(raiz.izq != null) // node with minimum value will have no left child
            return encontrarMin(raiz.izq); // left most element will be minimum
        return raiz;
    }
    
   public void prof(){
      int x = profundidad(raiz);
      System.out.println("prof: "+x);
   }
   //metodo para calcular profundidad
   public int profundidad(Nodo raiz){
      if(raiz == null)
         return 0;
      else{
         int pd = profundidad(raiz.der);
         int pi = profundidad(raiz.izq);
         if(pd > pi) return pd+1;
            else return pi+1;
      }
    }
    
    public void hojas(){
      int x = contarHojas(raiz);
      System.out.println("hojas: "+x);
    }
    
    //metodo para contar hojas
    public int contarHojas(Nodo raiz){
      if(raiz == null) return 0;
      if(raiz.der == null && raiz.izq == null) return 1;
      else{
         return contarHojas(raiz.izq)+contarHojas(raiz.der);
      }
         
    }
    
	public void inorden(){ 
      try{
         inordenRecursivo(raiz); 
         writer.close();
      }catch(Exception e){}
      System.out.println("\n---\narchivo reporte.txt generado exitosamente");
	} 

	//metodo para recorrer el arbor en inroden, recursivamente con la raiz de parametro
	public void inordenRecursivo(Nodo raiz) { 
		if (raiz != null) { 
			inordenRecursivo(raiz.izq); 
			System.out.println(raiz.palabra + " ["+raiz.frec+"]"); 
         try{
            writer.println(raiz.palabra + " ["+raiz.frec+"]");
         }catch(Exception e){}
			inordenRecursivo(raiz.der);
		} 
	} 

	//CLASE MAIN
	public static void main(String[] args) throws FileNotFoundException { 
      System.out.println("Ingresar nombre del archivo de origen. (ej. 'origen')");
      String origen = Lectura.readString();
      
      ABB parrafo = new ABB();
      
      Scanner scan = new Scanner(new File(origen+".txt"));
      //agregar todas las palabras al ABB
      while(scan.hasNext()){
         String str = scan.next().replaceAll("[^a-zA-Z\u00C0-\u00FF]*$","");    //elimina todos los caracteres especiales (puntos, comas, espacios, guiones)
         str = str.toLowerCase();                                                //convierte palabras en minusculas
         if(!str.equals("")){
            parrafo.agregar(str);            //agrega  la palabra en el ABB
         }                                    
      }
      parrafo.inorden();
      parrafo.prof();
      parrafo.hojas();
      System.out.println("que palabra deseas eliminar de ahi?");
      String elim = Lectura.readString();
      parrafo.del(elim);
      parrafo.prof();
      parrafo.hojas();
      
      
      //desplegar en inorden
		parrafo.inorden(); 
	} 
} 
//Inicio LECTURA
class Lectura{
	public static int readInt(){
		String num;
		DataInputStream w=new DataInputStream(System.in);
			try{
				num=w.readLine();
				}
				catch(IOException e){num="0";}
				int num2=Integer.parseInt(num);
			return num2;
	}
	
	public static float readFloat(){
		String num;
		DataInputStream w=new DataInputStream(System.in);
			try{
				num=w.readLine();
				}
				catch(IOException e){num="0";}
				float num2=Float.parseFloat(num);
			return num2;
	}
	
	public static byte readByte(){
		String num;
		DataInputStream w=new DataInputStream(System.in);
			try{
				num=w.readLine();
				}
				catch(IOException e){num="0";}
				byte num2=Byte.parseByte(num);
			return num2;
    }
    
    public static boolean readBoolean(){
		String num;
		DataInputStream w=new DataInputStream(System.in);
			try{
				num=w.readLine();
				}
				catch(IOException e){num="0";}
				boolean num2=Boolean.getBoolean(num);
			return num2;
	}
	
	public static double readDouble(){
		String num;
		DataInputStream w=new DataInputStream(System.in);
			try{
				num=w.readLine();
				}
				catch(IOException e){num="0";}
				double num2=Double.parseDouble(num);
			return num2;
	}
	
	public static long readLong(){
		String num;
		DataInputStream w=new DataInputStream(System.in);
			try{
				num=w.readLine();
				}
				catch(IOException e){num="0";}
				long num2=Long.parseLong(num);
			return num2;
	}
	
	public static short readShort(){
		String num;
		DataInputStream w=new DataInputStream(System.in);
			try{
				num=w.readLine();
				}
				catch(IOException e){num="0";}
				short num2=Short.parseShort(num);
			return num2;
	}
	
	public static char readChar(){
		String num;
		DataInputStream w=new DataInputStream(System.in);
			try{
				num=w.readLine();
				}
				catch(IOException e){num="0";}
				char num2=num.charAt(0);
			return num2;
	}
	public static String readString(){
		String num;
		DataInputStream w=new DataInputStream(System.in);
			try{
				num=w.readLine();
				}
				catch(IOException e){num="0";}
			return num;
	}
}

// FIN CLASE LECTURA