package src.Exceptions;

public class ItemDuplicated extends Exception{
    public ItemDuplicated(String msg){
        super(msg);
    }

    public ItemDuplicated(){
        super("El item ya se encuentra registrado");
    }
}
