import java.lang.reflect.Array;
import java.util.AbstractList;

public class OpenArrayDeque<T> extends AbstractList<T>{

  private T[] deque;
  private Class<T> type;
  private int head, size;

  public OpenArrayDeque(Class<T> t){
    //f = new Factory<T>(t);
    type = t;
    deque = newArray(t, 1);
    size = 0;
    head = 0;
  }

  @SuppressWarnings("unchecked")
  protected T[] newArray(Class<T> t, int i){
    return (T[])Array.newInstance(t, i);
  }

  public T get(int i){
    if(i < 0 || i > size()) throw new IndexOutOfBoundsException();
    return deque[(head+i) % deque.length];
  }

  public T set(int i, T x){
    if(i < 0 || i > size()) throw new IndexOutOfBoundsException();
    T y = get(i);
    deque[(head+i) % deque.length] = x;
    return y;
  }

  public void add(int i, T x){
    if(i < 0 || i > size()) throw new IndexOutOfBoundsException();

    if(size+1 > deque.length) resize();

    if(i < size/2){
      if(head == 0){
        head = deque.length - 1;
      }else{
        head -= 1;
      }
      for(int j = 0; j < i; j++){
        set(j, get(j+1));
      }
    }else{
      for(int j = size; j > i; j--){
        set(j, get(j-1));
      }
    }
    set(i, x);
    size++;
  }

  public T remove(int i){
    if(i < 0 || i > size) throw new IndexOutOfBoundsException();

    T x = get(i);
    if(i < size/2){
      for(int j = i; j > 0; j--){
        set(j, get(j-1));
      }
      head = (head+1) % deque.length;
    }else{
      for(int j = i; j < size()-1; j++){
        set(j, get(j+1));
      }
    }
    size--;
    if(deque.length > 3*size()) resize();
    return x;
  }

  public int size(){
    return this.size;
  }

  private void resize(){
    T[] temp = newArray(type, Math.max(2*size, 1));
    for(int i = 0; i < size(); i++){
      temp[i] = get(i);
    }
    deque = temp;
    head = 0;
  }


  public static void main(String[] args){
    OpenArrayDeque<Integer> mad = new OpenArrayDeque<Integer>(Integer.class);
    for(int i = 0; i < 100; i++){
      mad.add(i, i);
      System.out.println(mad.size());
    }
  }

}
