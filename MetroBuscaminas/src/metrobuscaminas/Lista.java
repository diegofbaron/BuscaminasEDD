/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metrobuscaminas;

/**
 *
 * @author diego
 */
public class Lista {
    private int size;
    private Nodo pFirst;
    private Nodo pLast;

    public Lista() {
        this.size = 0;
        this.pFirst = null;
        this.pLast = null;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Nodo getpFirst() {
        return pFirst;
    }

    public void setpFirst(Nodo pFirst) {
        this.pFirst = pFirst;
    }

    public Nodo getpLast() {
        return pLast;
    }

    public void setpLast(Nodo pLast) {
        this.pLast = pLast;
    }
    
    public void InsertarInicio(int clave){
        Nodo NuevoNodo = new Nodo(clave); //creacion del nodo
        Nodo aux = this.pFirst;
        this.pFirst = NuevoNodo;
        this.pFirst.setpNext(aux);
        size ++;
    }
    public void EliminarPrimero(){
        if (this.pFirst != null){
            this.pFirst = pFirst.getpNext();
            size --; 
        }
        else{
            System.out.println("La lista esta vacia");
        }
    }
    public void EliminarValor(int clave){
        Nodo aux = this.pFirst;
        if (this.pFirst != null){
            if (this.pFirst.getClave() == clave){
               this.pFirst = pFirst.getpNext();
            
            }
            else{
                while (aux.getpNext()!= null){
                    if (aux.getpNext().getClave() == clave){
                        
                        aux.setpNext(aux.getpNext().getpNext());
                    
                    }
                    aux = aux.getpNext();
                }
                        
            } 
            size --;
        }
        else{
            System.out.println("La lista esta vacia");
        }
 
    
    } 
    public Nodo Busqueda(int clave){
        Nodo aux = this.pFirst;
        while(aux != null){
            if (aux.getClave() == clave){
            
                return aux;
                
            }
            aux = aux.getpNext();
        }
     
        return null; 
    }
}
    