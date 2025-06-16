package src.Exceptions;

public class ItemNotFound extends Exception{
    public ItemNotFound(String msg){
        super(msg);
    }

    public ItemNotFound(){
        super("El item no existe");
    }
}
